package com.javacowboy.owl.survey.data.parser;

import java.io.File;
import java.util.logging.Logger;

import com.javacowboy.owl.survey.data.model.OwlData;
import com.javacowboy.owl.survey.data.model.USFSData;

public class Parser {
	
	final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	private OwlData owlData;
	private USFSData usfsData;
	
	public void parse(File file, long recordNumber) {
		logger.info("Parsing file: " + file.getName());
		owlData = new OwlData(file, recordNumber);
		usfsData = new USFSData();
	}

	public OwlData getOwlData() {
		return owlData;
	}

	public USFSData getUsfsData() {
		return usfsData;
	}

}
