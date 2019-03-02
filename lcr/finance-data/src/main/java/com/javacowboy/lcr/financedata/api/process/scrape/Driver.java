package com.javacowboy.lcr.financedata.api.process.scrape;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.stereotype.Component;

@Component
public class Driver {
	
	private WebDriver driver;

	public WebDriver getDriver() {
		if(driver == null) {
			initWebDriver();
		}
		return driver;
	}

	private void initWebDriver() {
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		this.driver = new RemoteWebDriver(capabilities);
	}
	
	
}
