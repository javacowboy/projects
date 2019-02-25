package com.javacowboy.lcr.financedata.api.process;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.javacowboy.lcr.financedata.api.process.csv.CsvService;
import com.javacowboy.lcr.financedata.api.process.html.HtmlService;
import com.javacowboy.lcr.financedata.api.process.model.BudgetDto;
import com.javacowboy.lcr.financedata.ui.UiController;

@Controller
@RequestMapping("/api/process")
public class ProcessController {
	
	private CsvService csvService;
	private HtmlService htmlService;
	
	@Autowired
	public ProcessController(CsvService csvService, HtmlService htmlService) {
		this.csvService = csvService;
		this.htmlService = htmlService;
	}

	@RequestMapping(value="/file", method=RequestMethod.GET)
	public String processFile(@RequestParam("inputFile") String inputFileName, @RequestParam("outputFile") String outputFileName) {
		List<BudgetDto> rows = htmlService.parseHtml(inputFileName);
		csvService.write(rows, outputFileName);
		return UiController.COMPLETE_PAGE;
	}
}
