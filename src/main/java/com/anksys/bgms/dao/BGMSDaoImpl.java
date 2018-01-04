package com.anksys.bgms.dao;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.anksys.bgms.model.BGMSRole;
import com.anksys.bgms.model.BGMSUser;
import com.anksys.bgms.model.Bank;
import com.anksys.bgms.model.Contract;
import com.anksys.bgms.model.Contractor;
import com.anksys.bgms.security.EncodeDecode;

/**
 * 
 * @author hsingh36
 * @version 1.0
 */
public class BGMSDaoImpl implements BGMSDao {

	private HibernateTemplate hibernateTemplate;
	private EncodeDecode encodeDecode;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	public EncodeDecode getEncodeDecode() {
		return encodeDecode;
	}

	public void setEncodeDecode(EncodeDecode encodeDecode) {
		this.encodeDecode = encodeDecode;
	}

	/**
	 * Checks for User login.
	 * 
	 * @param userName
	 * @param password
	 * @return boolean
	 * @throws BadPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws InvalidKeyException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public boolean loginCheck(String userName, String password)
			throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		List<BGMSUser> userList = hibernateTemplate.find(
				"from BGMSUser as U where U.userName =? ", userName);
		if (!userList.isEmpty()) {
			for (BGMSUser user : userList) {
				if (encodeDecode.checkPassword(password,user.getPassword())) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Register Executer with role BGMS_EXECUTER by ADMIN, return true for
	 * success.
	 * 
	 * @param userID
	 * @param password
	 * @param role
	 * @return boolean
	 */
	@Override
	public boolean registerExecuter(String userName, String password,
			String role) throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException {
		BGMSRole userRole = (BGMSRole.BGMS_ADMIN.toString().equals(role)) ? BGMSRole.BGMS_ADMIN
				: BGMSRole.BGMS_EXECUTER;

		BGMSUser bgmsUser = new BGMSUser(userName,encodeDecode.hashPassword(password), userRole);
		bgmsUser.setEnabled(true);
		hibernateTemplate.save(bgmsUser);

		return true;
	}

	/**
	 * Register Contractor Details.
	 * 
	 */
	@Override
	public boolean contractorRegistration(Contractor contractor)
			throws Exception {

		hibernateTemplate.save(contractor);
		return true;
	}

	/**
	 * Register Bank-branches.
	 */
	@Override
	public boolean bankRegistration(Bank bank) throws Exception {
		hibernateTemplate.save(bank);
		return true;
	}
	
	@Override @SuppressWarnings("unchecked")
	public boolean extendValidityDate(Contract contract) throws DataAccessException {
		
		List<Contract> listOfContract = hibernateTemplate.find("from Contract where contractId=?",contract.getContractId());
		if(listOfContract.isEmpty()){
			return false;
		}
		Contract contractEntityToUpdate = listOfContract.get(0);
		if(contractEntityToUpdate.getValidUpto().getTime()>contract.getExtendedUpto().getTime()){
			return false;
		}
		contractEntityToUpdate.setExtendedUpto(contract.getExtendedUpto());
		hibernateTemplate.update(contractEntityToUpdate);
		return true;
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Contract> getContractListBasedOnSearch(String contractId) {
		DetachedCriteria criteria  = DetachedCriteria.forClass(Contract.class);
		criteria.add(Restrictions.like("contractId", '%'+contractId+'%'));
		return hibernateTemplate.findByCriteria(criteria);
		
	}

	@Override @SuppressWarnings("unchecked")
	public List<Contractor> getAllContractors() {
		List<Contractor> contractorList =  hibernateTemplate.find("from Contractor ORDER BY contractorName ASC");
		return contractorList;
	}
	
	@Override @SuppressWarnings("unchecked")
	public List<Contractor> getAllContractorBasedOnName(String contractorName) {
		DetachedCriteria criteriaForContractor = DetachedCriteria.forClass(Contractor.class);
		criteriaForContractor.add(Restrictions.like("contractorName",'%'+contractorName+'%' ));
		List<Contractor> contractorList = hibernateTemplate.findByCriteria(criteriaForContractor);
		return contractorList;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Bank> getAllBanks() {
		List<Bank> bankList = hibernateTemplate.find("from Bank ORDER by bankName ASC");
		return bankList;
	}
	/**
	 * Register Contract Details in BGMS.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public boolean contractRegistration(String contractId, String contractorName,
			long bankId, String guaranteeAmount, Date validFrom, Date validUpto)
			throws Exception {
		
		List<Contractor> contractorList = hibernateTemplate.find("from Contractor where contractorName = ? ",contractorName);
		
		List<Bank> bankList = hibernateTemplate.find("from Bank where bankId = ?",bankId);
		Contract contract = new Contract(contractId,guaranteeAmount,validFrom,validUpto);
		if(contractorList.isEmpty() || bankList.isEmpty()){
			return false;
		}else{
			contract.setContractor(contractorList.get(0));
			contract.setBank(bankList.get(0));
			hibernateTemplate.save(contract);
		}
		return true;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Contract> getAllContracts() {
		return hibernateTemplate.find("from Contract");

	}
	
	@Override @SuppressWarnings("unchecked")
	public BGMSUser getUserByUsername(String userName) {
		List<BGMSUser> userList = hibernateTemplate.find("from BGMSUser where userName=?",userName);
		BGMSUser user = null;
		if(!userList.isEmpty()){
			user = userList.get(0);
		}
		return user;
	}
	
	

}
