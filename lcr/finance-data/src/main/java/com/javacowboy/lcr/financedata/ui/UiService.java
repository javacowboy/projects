package com.javacowboy.lcr.financedata.ui;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

@Service
public class UiService {
	
	private static final Logger logger = Logger.getLogger(UiService.class.getName());

	public void launchBrowser(String url) {
		logger.info("Attempting to launch: " + url);
		String os = System.getProperty("os.name").toLowerCase();
		Runtime rt = Runtime.getRuntime();
		try {
			if(os.contains("windows")) {
				rt.exec( "rundll32 url.dll,FileProtocolHandler " + url);
			}else if(os.contains("mac")) {
				rt.exec( "open " + url);
			}else {
				logger.info("Failed to launch browser.  Unsupport OS: " + os);
				return;
			}
			logger.info("Launched Browser");
		}catch(IOException e) {
			logger.log(Level.WARNING, "Failed to launch browser", e);
		}
	}
}
