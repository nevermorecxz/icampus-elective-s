package com.irengine.campus.web.rest.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadProperties {

	public static String readProperties(String str) throws IOException {
		Properties prop = new Properties();
		InputStream in = new BufferedInputStream(new FileInputStream("configuration.properties"));
		prop.load(in);
		String str2=prop.getProperty(str);
		in.close();
		return str2;
	}
	
}
