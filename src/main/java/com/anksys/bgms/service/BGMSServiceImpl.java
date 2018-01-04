package com.anksys.bgms.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.dao.DataAccessException;

import com.anksys.bgms.dao.BGMSDao;
import com.anksys.bgms.model.Bank;
import com.anksys.bgms.model.Contract;
import com.anksys.bgms.model.Contractor;

public class BGMSServiceImpl implements BGMSService {

	private BGMSDao bgmsDao;

	public BGMSDao getBgmsDao() {
		return bgmsDao;
	}

	public void setBgmsDao(BGMSDao bgmsDao) {
		this.bgmsDao = bgmsDao;
	}

	/**
	 * Check user login for application.
	 * 
	 * @param userName
	 * @param password
	 * @return boolean
	 */
	@Override
	public boolean loginCheck(String userName, String password)	throws InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException {
		return bgmsDao.loginCheck(userName, password);

	}

	/**
	 * Register Executer with role BGMS_EXECUTER by ADMIN and return success
	 * 
	 * @param userID
	 * @param password
	 * @param role
	 * @return boolean
	 */
	@Override
	public boolean registerExecuter(String userID, String password, String role) throws InvalidKeyException, NoSuchAlgorithmException,NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException {
		return bgmsDao.registerExecuter(userID, password, role);

	}

	/**
	 * Register Contract Details.
	 * 
	 */
	@Override
	public boolean contractorRegistration(Contractor contractor) throws Exception {
		return bgmsDao.contractorRegistration(contractor);

	}

	/**
	 * Register Bank-Branches.
	 */
	@Override
	public boolean bankRegistration(Bank bank) throws Exception {
		return bgmsDao.bankRegistration(bank);
	}
	
	@Override
	public boolean extendValidityDate(Contract contract) throws DataAccessException {
		
		return bgmsDao.extendValidityDate(contract);
	}
	
	@Override
	public List<Contract> getContractListBasedOnSearch(String contractId) {
		
		return bgmsDao.getContractListBasedOnSearch(contractId);
	}
	
	@Override
	public List<Contractor> getAllContractors() {
		
		return bgmsDao.getAllContractors();
	}

	@Override
	public List<Bank> getAllBanks() {
		return bgmsDao.getAllBanks();
	}
	
	@Override
	public boolean contractRegistration(String contractId, String contractorName, long bankId, String guaranteeAmount, Date validFrom, Date validUpto) throws Exception {
		return bgmsDao.contractRegistration(contractId, contractorName, bankId, guaranteeAmount, validFrom, validUpto);
	}	
	
	@Override
	public List<Contract> getAllContracts() {
		return bgmsDao.getAllContracts();
	}
	
	@Override
	public List<Contractor> getAllContractorBasedOnName(String contractorName) {
		return bgmsDao.getAllContractorBasedOnName(contractorName);
	}

	
}