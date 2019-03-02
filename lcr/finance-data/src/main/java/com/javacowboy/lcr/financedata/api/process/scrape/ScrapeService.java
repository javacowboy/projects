package com.javacowboy.lcr.financedata.api.process.scrape;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/*
 * Service for downloading a URL to an HTML file
 */
@Service
public class ScrapeService {
	
	private Navigation navigation;
	private Driver driver;
	
	@Autowired
	public ScrapeService(Driver driver, Navigation navigation) {
		this.driver = driver;
		this.navigation = navigation;
	}

	public String scrape(String scrapeUrl, String username, String password) {
		Navigation.setUsername(username);
		Navigation.setPassword(password);
		navigation.navigate(getDriver(), scrapeUrl);
		return getDriver().getPageSource();
	}

	private WebDriver getDriver() {
		return driver.getDriver();
	}

}
