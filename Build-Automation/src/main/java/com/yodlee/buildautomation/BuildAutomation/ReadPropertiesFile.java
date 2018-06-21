package com.yodlee.buildautomation.BuildAutomation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadPropertiesFile {
	public static Properties getProperties(String fileName){
		Properties properties = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream(fileName);
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(input!=null){
				try{
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return properties;
	}
	
	public static void setProperties(String fileName,String key, String value) {
		try {
			File file = new File(fileName);
			FileOutputStream fileOutput = new FileOutputStream(file);
			Properties properties = new Properties();
			properties.setProperty(key, value);
			properties.store(fileOutput, "Program has run "+value+" times");
			fileOutput.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}