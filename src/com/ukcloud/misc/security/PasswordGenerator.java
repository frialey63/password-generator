/*
 * Copyright URL
 */
package com.ukcloud.misc.security;

/**
 * This interface defines the operation for a secure password generator.
 * @author Paul Parlett
 *
 */
public interface PasswordGenerator {

	/**
	 * This operation generates a secure password of the specified length 
	 * and comprising a random combination of the specified character categories. 
	 * 
	 * @param length The length of the returned string
	 * @param uppercase Whether to include uppercase characters A - Z
	 * @param lowercase Whether to include lowercase characters a - z
	 * @param number Whether to include 0 – 9 
	 * @param special Whether to include the following keyboard characters !$%&*@^
	 * @return The secure password
	 */
	String generatePassword(int length, boolean uppercase, boolean lowercase, boolean number, boolean special);
}
