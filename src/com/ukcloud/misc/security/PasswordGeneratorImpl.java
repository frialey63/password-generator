/*
 * Copyright URL
 */
package com.ukcloud.misc.security;

import java.security.SecureRandom;
import java.util.Random;
import java.util.logging.Logger;

/**
 * This class provides an implementation of the PasswordGenerator interface.
 * 
 * @author Paul Parlett
 *
 */
public final class PasswordGeneratorImpl implements PasswordGenerator {

	private static final Logger LOGGER = Logger.getLogger(PasswordGeneratorImpl.class.getName()); 
	
	private static final char[] UPPER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	private static final char[] LOWER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	private static final char[] NUMERIC = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	private static final char[] SPECIAL = { '!', '$', '%', '&', '*', '@', '^' };
	
	private final RandomInteger randomInteger;
	
	/**
	 * This interface defines an operation for obtaining a pseudo-random integer.
	 * @author Paul Parlett
	 *
	 */
	interface RandomInteger {
		/**
		 * Returns a pseudo-random, uniformly distributed integer value between 0 (inclusive) and the specified value (exclusive), 
		 * drawn from this random number generator's sequence.
		 * @param bound The upper bound (exclusive)
		 * @return The next pseudo-random integer
		 */
		int nextInt(int bound);
	}
	
	/**
	 * This class provides an implementation of RandomInteger based on java.security.SecureRandom.SecureRandom()
	 * which is seeded to the system current time in millisecs.
	 * @author Paul Parlett
	 *
	 */
	private static class SecureRandomInteger implements RandomInteger {
		private final Random random = new SecureRandom();
		
		{
			random.setSeed(System.currentTimeMillis());
		}
		
		@Override
		public int nextInt(int bound) {
			return random.nextInt(bound);
		}
	}
	
	/**
	 * Construct the PasswordGeneratorImpl using an instance of com.ukcloud.misc.security.PasswordGeneratorImpl.SecureRandomInteger.
	 */
	public PasswordGeneratorImpl() {
		this.randomInteger = new SecureRandomInteger();
	}
	
	// VisibleForTesting
	PasswordGeneratorImpl(RandomInteger randomInteger) {
		this.randomInteger = randomInteger;
	}
	
	/**
	 * {@inheritDoc}}
	 * 
	 * The specified character categories, each represented as an array of the relevant characters, 
	 * are copied into an array of characters representing the "available characters" for password generation.
	 * A random number generator is used to select characters from the array of "available characters"
	 * one at a time, until the "result" character array has been populated with length in number characters.
	 */
	@Override
	public String generatePassword(final int length, final boolean uppercase, final boolean lowercase, final boolean number, final boolean special) 
			throws IllegalArgumentException {
		
		LOGGER.entering(PasswordGeneratorImpl.class.getName(), "generatePassword");
		
		if (length <= 0) {
			throw new IllegalArgumentException("password length must be > 0");
		}
		
		if (!uppercase && !lowercase && !number && !special) {
			throw new IllegalArgumentException("unable to generate password without any charactor category");
		}
		
		LOGGER.fine(String.format("generating password length=%d, uppercase=%b, lowercase=%b, number=%b, special=%b", 
				length, uppercase, lowercase, number, special));
		
		assert UPPER.length == 26 : "incorrect number of uppercase alphabetic characters";;
		assert LOWER.length == 26 : "incorrect number of lowercase alphabetic characters";
		assert NUMERIC.length == 10 : "incorrect number of numeric characters";
		assert SPECIAL.length == 7 : "incorrect number of special characters";
		
		final int numberOfAvailableCharacters = 
				(uppercase ? UPPER.length : 0) + (lowercase ? LOWER.length : 0) + (number ? NUMERIC.length : 0) + (special ? SPECIAL.length : 0);
		
		final char[] availableCharacters = new char[numberOfAvailableCharacters];
		
		int index = 0;
		
		if (uppercase) {
			System.arraycopy(UPPER, 0, availableCharacters, index, UPPER.length);
			index += UPPER.length;
		}
		
		if (lowercase) {
			System.arraycopy(LOWER, 0, availableCharacters, index, LOWER.length);
			index += LOWER.length;
		}
		
		if (number) {
			System.arraycopy(NUMERIC, 0, availableCharacters, index, NUMERIC.length);
			index += NUMERIC.length;
		}
		
		if (special) {
			System.arraycopy(SPECIAL, 0, availableCharacters, index, SPECIAL.length);
			index += SPECIAL.length;
		}
		
		final char[] result = new char[length];
		
		for (int i = 0; i < result.length; i++) {
			result[i] = availableCharacters[randomInteger.nextInt(availableCharacters.length)];
		}
		
		assert result.length == length : "incorrect password length";
		
		LOGGER.exiting(PasswordGeneratorImpl.class.getName(), "generatePassword");
		
		return new String(result);
	}
	
}
