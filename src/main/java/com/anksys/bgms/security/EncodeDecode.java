package com.anksys.bgms.security;

import org.springframework.security.crypto.bcrypt.BCrypt;

/**
 * 
 * @author HarshVardhan
 * @version 1.0
 */
public class EncodeDecode {

	/**
	 * Generate Hash for Password before save.
	 * 
	 * @param password
	 * @return String
	 */
	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	/**
	 * Check Password for match
	 * 
	 * @param encryptedPassword
	 * @return boolean
	 */
	public boolean checkPassword(String plainPassword, String hashedPassword) {

		return BCrypt.checkpw(plainPassword, hashedPassword);
	}

}
