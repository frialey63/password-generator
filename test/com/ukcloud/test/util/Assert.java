/*
 * Copyright URL
 */
package com.ukcloud.test.util;

/**
 * This class contains assertion utilities for testing purposes.
 * 
 * @author Paul Parlett
 *
 */
public final class Assert {

	/**
	 * If the condition is not true then print a failure message.
	 * @param condition The condition
	 * @param message The failure message
	 */
	public static void assertTrue(boolean condition, String message) {
		if (!condition) {
			System.err.println("FAILURE: " + message);
		}
	}
	
	/**
	 * If the condition is true then print a failure message.
	 * @param condition The condition
	 * @param message The failure message
	 */
	public static void assertFalse(boolean condition, String message) {
		if (condition) {
			System.err.println("FAILURE: " + message);
		}
	}
	
	/**
	 * If the actual does not equal the expected then print a failure message.
	 * @param expected The expected
	 * @param actual The actual
	 * @param message The failure message
	 */
	public static void assertEquals(int expected, int actual, String message) {
		if (expected != actual) {
			System.err.println("FAILURE: " + message);
		}
	}
	
	/**
	 * If the actual does not equal the expected then print a failure message.
	 * @param expected The expected
	 * @param actual The actual
	 * @param message The failure message
	 */
	public static void assertEquals(char expected, char actual, String message) {
		if (expected != actual) {
			System.err.println("FAILURE: " + message);
		}
	}
	
	/**
	 * Print a failure message
	 * @param message The failure message
	 */
	public static void fail(String message) {
		System.err.println("FAILURE: " + message);
	}
	
	private Assert() {
		// prevent instantiation
	}
	
}
