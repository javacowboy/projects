package com.javacowboy.owl.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Constants {
	
	public static String propertiesFile = "resources/config/application.xml";
	
	public enum Property {
		INPUT_DIRECTORY("input.directory"),
		OUTPUT_DIRECTORY("output.directory"),
		OUTPUT_DATA_FILE("output.data.filename"),
		OUTPUT_USFS_FILE("output.usfs.filename");
		
		private String propertyKey;
		protected String propertyValue;
		private Property(String propertyKey) {
			this.propertyKey = propertyKey;
		}
		
		public String getPropertyKey() {
			return propertyKey;
		}
		
		public String getPropertyValue() {
			Constants.init();
			return propertyValue;
		}
		
		public void setPropertyValue(String propertyValue) {
			this.propertyValue = propertyValue;
			Constants.writeProperties();
		}
		
	}
	
	static final Logger logger = Logger.getLogger(Constants.class.getSimpleName());
	
	static {
		logger.info("Reading properties: " + propertiesFile);
		try {
			Properties properties = new Properties();
			properties.loadFromXML(new FileInputStream(propertiesFile));
			//don't call setters or it'll trigger a properties file write
			Property.INPUT_DIRECTORY.propertyValue = (properties.getProperty(Property.INPUT_DIRECTORY.getPropertyKey(), null));
			Property.OUTPUT_DIRECTORY.propertyValue = (properties.getProperty(Property.OUTPUT_DIRECTORY.getPropertyKey(), null));
			Property.OUTPUT_DATA_FILE.propertyValue = properties.getProperty(Property.OUTPUT_DATA_FILE.getPropertyKey(), "output.csv");
			Property.OUTPUT_USFS_FILE.propertyValue = properties.getProperty(Property.OUTPUT_USFS_FILE.getPropertyKey(), "usfs.csv");
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Properties file not found at: " + propertiesFile, e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Problem reading properties file: " + propertiesFile, e);
		}
	}
	
	//a method to call to make sure properties get read in
	protected static void init() {
		
	}
	
	protected static Integer toInteger(Properties properties, String name, Integer defaultValue) {
		try {
			return Integer.valueOf(properties.getProperty(name));
		}catch(NumberFormatException e) {
			return defaultValue;
		}
	}
	
	protected static void writeProperties() {
		logger.info("Writing properties: " + propertiesFile);
		try {
			Properties properties = new Properties();
			for(Property prop : Property.values()) {
				if(prop.getPropertyValue() != null) {
					properties.setProperty(prop.getPropertyKey(), prop.getPropertyValue());
				}
			}

			File file = new File(propertiesFile);
			FileOutputStream fileOut = new FileOutputStream(file);
			properties.storeToXML(fileOut, "Favorite Things");
			fileOut.close();
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Properties file not found at: " + propertiesFile, e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error writing to properties file: " + propertiesFile, e);
		}

	}

}
