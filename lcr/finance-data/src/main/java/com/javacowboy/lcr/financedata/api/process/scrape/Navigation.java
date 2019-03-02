package com.javacowboy.lcr.financedata.api.process.scrape;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public class Navigation {
	
	private static final Logger logger = Logger.getLogger(Navigation.class.getName());
	
	private static String username;
	private static String password;

	public void navigate(WebDriver driver, String url) {
		driver.get(url);
		String expectedHost = getHostname(url);
		String actualHost = getHostname(driver.getCurrentUrl());
		if(!actualHost.equals(expectedHost)) {
			//TODO: should check that it's the SSO login URL
			doLogin(driver, username, password);
			actualHost = getHostname(driver.getCurrentUrl());
			if(!actualHost.equals(expectedHost)) {
				logger.severe("Could not navigate to: " + url);
			}
		}
	}

	private void doLogin(WebDriver driver, String username, String password) {
		driver.findElement(By.id("IDToken1")).sendKeys(username);
		driver.findElement(By.id("IDToken2")).sendKeys(password);
		driver.findElement(By.id("login-submit-button")).click();
	}

	private String getHostname(String url) {
		try {
			URL destination = new URL(url);
			return destination.getHost();
		} catch (MalformedURLException e) {
			logger.log(Level.WARNING, "Could not determine host", e);
		}
		return null;
	}

	public static void setUsername(String username) {
		Navigation.username = username;
	}

	public static void setPassword(String password) {
		Navigation.password = password;
	}
	
}
