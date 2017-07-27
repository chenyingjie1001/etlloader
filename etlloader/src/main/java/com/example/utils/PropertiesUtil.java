package com.example.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class PropertiesUtil {

	public static Properties properties;
	static {
		properties = new Properties();
		InputStream in = null;
		InputStream input = null;
		try {
			in = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.properties");
			input = PropertiesUtil.class.getClassLoader().getResourceAsStream("etlloader.properties");
			properties.load(in);
			properties.load(input);
			in.close();
			input.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
