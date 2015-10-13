package ca.gatin.todoapi.util;

import org.junit.Test;

/**
 * 
 * @author RGatin
 * @since 12-Oct-2015
 * 
 * Testing Utils
 *
 */
public class UtilTest {
	
	@Test
	public void randomTokenGenerationTest() {
		System.out.println(SessionIdentifierGenerator.nextSessionId());
	}
	
	@Test
	public void createTokenExpireDateTest() {
		System.out.println(TimestampUtils.createTokenExpireDate(1));
	}
}
