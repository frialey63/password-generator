/*
 * Copyright URL
 */
package com.ukcloud.misc.security;

import static com.ukcloud.test.util.Assert.*;

import java.util.Arrays;

import com.ukcloud.misc.security.PasswordGeneratorImpl.RandomInteger;

/**
 * This class is the test suite for the PasswordGeneratorImpl.
 * 
 * The individual test methods in this class as public, this is not required but it compatible with upgrade to JUnit.
 * @author Paul Parlett
 *
 */
public class PasswordGeneratorImplTest {

	private static final char[] UPPER = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
	
	private static final char[] LOWER = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z' };
	
	private static final char[] NUMERIC = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	private static final char[] SPECIAL = { '!', '$', '%', '&', '*', '@', '^' };
	
	static {
		/*
		 * Sanity checks for the reference data.
		 */
		
		{
			assertEquals(26, UPPER.length, "ERROR: incorrect count of uppercase characters");
			
			int code = UPPER[0];
			for (char c : UPPER) {
				assertTrue(Character.isUpperCase(c), "ERROR: not an uppercase character");
				assertEquals(code++, c, "the uppercase characters are non-sequential");
			}
		}
		
		{
			assertEquals(26, LOWER.length, "ERROR: incorrect count of lowercase characters");
			
			int code = LOWER[0];
			for (char c : LOWER) {
				assertTrue(Character.isLowerCase(c), "ERROR: not a lowercase character");
				assertEquals(code++, c, "the lowercase characters are non-sequential");
			}
		}
		
		{
			assertEquals(10, NUMERIC.length, "ERROR: incorrect count of number characters");
			
			int code = NUMERIC[0];
			for (char c : NUMERIC) {
				assertTrue(Character.isDigit(c), "ERROR: not a number character");
				assertEquals(code++, c, "the number characters are non-sequential");
			}
		}
		
		{
			assertEquals(7, SPECIAL.length, "ERROR: incorrect count of special characters");
			
			assertTrue(Arrays.binarySearch(SPECIAL, '!') >= 0, "! not in special characters");
			assertTrue(Arrays.binarySearch(SPECIAL, '$') >= 0, "! not in special characters");
			assertTrue(Arrays.binarySearch(SPECIAL, '%') >= 0, "! not in special characters");
			assertTrue(Arrays.binarySearch(SPECIAL, '&') >= 0, "! not in special characters");
			assertTrue(Arrays.binarySearch(SPECIAL, '*') >= 0, "! not in special characters");
			assertTrue(Arrays.binarySearch(SPECIAL, '@') >= 0, "! not in special characters");
			assertTrue(Arrays.binarySearch(SPECIAL, '^') >= 0, "! not in special characters");
		}
		
	}
	
	private static final boolean contains(String string, char[] charArray) {
		for (int i = 0; i < string.length(); i++) {
			final char c = string.charAt(i);
			
			for (int j = 0; j < charArray.length; j++) {
				if (c == charArray[j]) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	private static final boolean containsUpper(String string) {
		return contains(string, UPPER);
	}
	
	private static final boolean containsLower(String string) {
		return contains(string, LOWER);
	}
	
	private static final boolean containsNumeric(String string) {
		return contains(string, NUMERIC);
	}
	
	private static final boolean containsSpecial(String string) {
		return contains(string, SPECIAL);
	}
	
	private static class RandomIntegerStub implements RandomInteger {

		final int bound;
		
		int nextInt;
		
		RandomIntegerStub(int bound) {
			super();
			this.bound = bound;
		}

		@Override
		public int nextInt(int ignored) {
			return nextInt++ % bound;
		}
	}
	
	/**
	 * Test generating a password with illegal length.
	 */
	public void generatePasswordIllegalLength() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		
		try {
			impl.generatePassword(0, true, true, true, true);
			fail("password has illegal length");
		} catch (IllegalArgumentException e) {
			// ignore, expected
		}
	}

	/**
	 * Test generating a password with illegal character categories.
	 */
	public void generatePasswordIllegalCharacterCategories() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		
		try {
			impl.generatePassword(10, false, false, false, false);
			fail("password has illegal character categories");
		} catch (IllegalArgumentException e) {
			// ignore, expected
		}
	}

	/**
	 * Test generating a password with upper only.
	 */
	public void generatePasswordUpperOnly() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		String password = impl.generatePassword(10, true, false, false, false);
		
		assertEquals(10, password.length(), "password wrong length");
		assertTrue(containsUpper(password), "password does not contain an upper");
		assertFalse(containsLower(password), "password contains a lower");
		assertFalse(containsNumeric(password), "password contains a numeric");
		assertFalse(containsSpecial(password), "password contains a special");
	}

	/**
	 * Test generating a password with lower only.
	 */
	public void generatePasswordLowerOnly() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		String password = impl.generatePassword(10, false, true, false, false);
		
		assertEquals(10, password.length(), "password wrong length");
		assertFalse(containsUpper(password), "password contains an upper");
		assertTrue(containsLower(password), "password does not contain a lower");
		assertFalse(containsNumeric(password), "password contains a numeric");
		assertFalse(containsSpecial(password), "password contains a special");
	}

	/**
	 * Test generating a password with number only.
	 */
	public void generatePasswordNumberOnly() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		String password = impl.generatePassword(10, false, false, true, false);
		
		assertEquals(10, password.length(), "password wrong length");
		assertFalse(containsUpper(password), "password contains an upper");
		assertFalse(containsLower(password), "password contains a lower");
		assertTrue(containsNumeric(password), "password does not contain a numeric");
		assertFalse(containsSpecial(password), "password contains a special");
	}

	/**
	 * Test generating a password with special only.
	 */
	public void generatePasswordSpecialOnly() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		String password = impl.generatePassword(10, false, false, false, true);
		
		assertEquals(10, password.length(), "password wrong length");
		assertFalse(containsUpper(password), "password contains an upper");
		assertFalse(containsLower(password), "password contains a lower");
		assertFalse(containsNumeric(password), "password contains a numeric");
		assertTrue(containsSpecial(password), "password does not contain a special");
	}

	/**
	 * Test generating a password with special only.
	 */
	public void generatePasswordAll() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl();
		String password = impl.generatePassword(10, true, true, true, true);
		
		assertEquals(10, password.length(), "password wrong length");
		assertTrue(containsUpper(password) || containsLower(password) || containsNumeric(password) || containsSpecial(password), 
				"password does not contain an upper or lower or numeric or special");
	}

	/**
	 * Test generating passwords includes full range of uppers.
	 */
	public void generatePasswordUpperRange() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl(new RandomIntegerStub(UPPER.length));
		
		for (int i = 0; i < UPPER.length; i++) {
			String password = impl.generatePassword(1, true, false, false, false);
			assertEquals(UPPER[i], password.charAt(0), "incorrect upper character");
		}
	}

	/**
	 * Test generating passwords includes full range of lowers.
	 */
	public void generatePasswordLowerRange() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl(new RandomIntegerStub(LOWER.length));
		
		for (int i = 0; i < LOWER.length; i++) {
			String password = impl.generatePassword(1, false, true, false, false);
			assertEquals(LOWER[i], password.charAt(0), "incorrect upper character");
		}
	}

	/**
	 * Test generating passwords includes full range of numbers.
	 */
	public void generatePasswordNumberRange() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl(new RandomIntegerStub(NUMERIC.length));
		
		for (int i = 0; i < NUMERIC.length; i++) {
			String password = impl.generatePassword(1, false, false, true, false);
			assertEquals(NUMERIC[i], password.charAt(0), "incorrect upper character");
		}
	}

	/**
	 * Test generating passwords includes full range of specials.
	 */
	public void generatePasswordSpecialRange() {
		final PasswordGeneratorImpl impl = new PasswordGeneratorImpl(new RandomIntegerStub(SPECIAL.length));
		
		for (int i = 0; i < SPECIAL.length; i++) {
			String password = impl.generatePassword(1, false, false, false, true);
			assertEquals(SPECIAL[i], password.charAt(0), "incorrect upper character");
		}
	}

	/**
	 * This method is the entry point for the program
	 * @param args The program arguments
	 */
	public static void main(String[] args) {
		final PasswordGeneratorImplTest test = new PasswordGeneratorImplTest();
		
		System.out.println("Started test...");
		
		test.generatePasswordIllegalLength();
		test.generatePasswordIllegalCharacterCategories();
		
		test.generatePasswordUpperOnly();
		test.generatePasswordLowerOnly();
		test.generatePasswordNumberOnly();
		test.generatePasswordSpecialOnly();
		
		test.generatePasswordAll();
		
		test.generatePasswordUpperRange();
		test.generatePasswordLowerRange();
		test.generatePasswordNumberRange();
		test.generatePasswordSpecialRange();
		
		System.out.println("done.");
	}
}
