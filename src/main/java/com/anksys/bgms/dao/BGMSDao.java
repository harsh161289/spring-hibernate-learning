package com.anksys.bgms.dao;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import com.anksys.bgms.model.BGMSUser;
import com.anksys.bgms.model.Bank;
import com.anksys.bgms.model.Contract;
import com.anksys.bgms.model.Contractor;

/**
 * 
 * @author hsingh36
 * @version 1.0
 */

public interface BGMSDao {

	boolean loginCheck(String userName, String password) throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException ;

	boolean registerExecuter(String userID, String password, String role)
			throws InvalidKeyException, NoSuchAlgorithmException,
			NoSuchPaddingException, IllegalBlockSizeException,
			BadPaddingException;
	
	boolean contractorRegistration(Contractor contractor)throws Exception;
	
	boolean bankRegistration(Bank bank) throws Exception;
	
	boolean contractRegistration(String contractId, String contractorName, long bankId, String guaranteeAmount, Date validFrom, Date validUpto) throws Exception;
	
	boolean extendValidityDate(Contract contract);
	
	List<Contract> getContractListBasedOnSearch(String contractId);
	
	List<Contractor> getAllContractors();
	
	List<Contractor> getAllContractorBasedOnName(String contractorName);
	
	List<Bank> getAllBanks();
	
	List<Contract> getAllContracts();
	
	BGMSUser getUserByUsername(String userName);
}
