package ca.gatin.todoapi.util;

import java.math.BigInteger;
import java.security.SecureRandom;

/**
 * 
 * @author RGatin
 * @since 12-Oct-2015
 * 
 * Generates Access token String
 * by choosing 130 bits from a cryptographically secure 
 * random bit generator, and encoding them in base-32
 */
public final class SessionIdentifierGenerator {
	private static SecureRandom random = new SecureRandom();

	public static String nextSessionId() {
		return new BigInteger(130, random).toString(32);
	}
}