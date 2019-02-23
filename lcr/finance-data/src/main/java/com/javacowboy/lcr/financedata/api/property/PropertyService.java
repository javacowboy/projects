package com.javacowboy.lcr.financedata.api.property;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class PropertyService {

	private Environment environment;
	
	@Autowired
	public PropertyService(Environment environment) {
		this.environment = environment;
	}
	
	public String getHostName() {
		return "localhost";
	}
	
	public Integer getServerPort() {
		return getProperty("local.server.port");
	}
	
	public String getHomePage() {
		return "http://" + getHostName() + ":" + getServerPort();
	}

	private Integer getProperty(String key) {
		return environment.getProperty(key, Integer.class);
	}
}
