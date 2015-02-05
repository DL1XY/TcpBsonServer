package de.darc.dl1xy.tcpbsonserver.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyReader {

	Properties auto;
	public PropertyReader() {
		
	}

	public Properties readProps(String filename)
	{
		auto = new Properties();
		try 
		{
			auto.load(new FileInputStream(filename));
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return auto;
	}	
}
