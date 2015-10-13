package ca.gatin.todoapi.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import ca.gatin.todoapi.database.DatabaseQueries;

/**
 * @author RGatin
 * @since 11-Oct-2015
 * 
 *        Methods for dealing with property files
 */
public class PropertiesUtil {

	private String dbProperties = "db.properties";
	private static PropertiesUtil instance;
	private PropertiesUtil(){}

	public static synchronized PropertiesUtil getInstance() {
		if (instance == null) {
			instance = new PropertiesUtil();
		}
		return instance;
	}

	/**
	 * Return corresponding properties based on Build Configuration (local or
	 * deploy)
	 * 
	 * @return Properties
	 */
	public Properties getDBProperties() {
		Properties props = new Properties();
		FileInputStream fis = null;

		ClassLoader classLoader = getClass().getClassLoader();
		File file = new File(classLoader.getResource(dbProperties).getFile());

		try {
			fis = new FileInputStream(file);
			props.load(fis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}
}