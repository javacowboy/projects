package com.javacowboy.lcr.financedata.api.process;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacowboy.lcr.financedata.api.process.csv.CsvService;
import com.javacowboy.lcr.financedata.api.process.html.FileService;
import com.javacowboy.lcr.financedata.api.process.html.HtmlService;
import com.javacowboy.lcr.financedata.api.process.model.BudgetDto;
import com.javacowboy.lcr.financedata.api.process.scrape.ScrapeService;
import com.javacowboy.lcr.financedata.ui.UiController;

@Controller
@RequestMapping("/api/process")
public class ProcessController {
	
	private CsvService csvService;
	private FileService fileService;
	private HtmlService htmlService;
	private ScrapeService scrapeService;
	
	@Autowired
	public ProcessController(CsvService csvService, FileService fileService, HtmlService htmlService, 
			ScrapeService scrapeService) {
		this.csvService = csvService;
		this.fileService = fileService;
		this.htmlService = htmlService;
		this.scrapeService = scrapeService;
	}

	@RequestMapping(value="/file", method=RequestMethod.GET)
	public String processFile(@RequestParam("inputFile") String inputFileName, @RequestParam("outputFile") String outputFileName) {
		List<BudgetDto> rows = htmlService.parseHtml(inputFileName);
		csvService.write(rows, outputFileName);
		return UiController.COMPLETE_PAGE;
	}
	
	@RequestMapping(value="/scrape", method=RequestMethod.GET)
	public String scrape(@RequestParam("scrapeUrl") String scrapeUrl, 
			@RequestParam("inputFile") String inputFileName, @RequestParam("outputFile") String outputFileName, 
			@RequestParam("username") String username, @RequestParam("password") String password) throws IOException {
		String html = scrapeService.scrape(scrapeUrl, username, password);
		fileService.save(html, inputFileName);
		processFile(inputFileName, outputFileName);
		return UiController.COMPLETE_PAGE;
	}
}
