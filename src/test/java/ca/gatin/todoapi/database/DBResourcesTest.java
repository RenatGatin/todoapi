package ca.gatin.todoapi.database;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;

import org.junit.Test;

public class DBResourcesTest {

	/**
	 * It is just a console output test, no Assertion!
	 */
	@Test
	public void readFromResourcesTest() {
		System.out.println(getFile("db.properties"));
	}

	private String getFile(String fileName) {
		Properties props = new Properties();
        FileInputStream fis = null;
        String result = "";
        
        ClassLoader classLoader = getClass().getClassLoader();
    	File file = new File(classLoader.getResource(fileName).getFile());
        
		try {
			fis = new FileInputStream(file);
			props.load(fis);
			
			result = props.getProperty("DB_HOST");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}

}
