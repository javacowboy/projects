package com.javacowboy.lcr.financedata.ui;

import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javacowboy.lcr.financedata.api.property.PropertyService;

@Controller
@RequestMapping("/")
public class UiController {
	
	private static final Logger logger = Logger.getLogger(UiController.class.getName());
	
	private PropertyService propertyService;
	private UiService uiService;
	
	@Autowired
	public UiController(PropertyService propertyService, UiService uiService) {
		this.propertyService = propertyService;
		this.uiService = uiService;
	}
	
	private static final String INDEX_PAGE = "/index.html";
	public static final String COMPLETE_PAGE = "/complete.html";

	@GetMapping
	public String index() {
		return INDEX_PAGE;
	}
	
	/*
	 * On startup, auto open a browser.
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void applicationReady() {
		logger.info("Server started, launching browser");
		uiService.launchBrowser(propertyService.getHomePage());
	}

}
