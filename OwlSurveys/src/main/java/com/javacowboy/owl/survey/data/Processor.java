package com.javacowboy.owl.survey.data;

import java.io.File;
import java.util.logging.Logger;

import com.javacowboy.owl.survey.data.model.OwlData;
import com.javacowboy.owl.survey.data.model.USFSData;
import com.javacowboy.owl.survey.data.parser.Parser;
import com.javacowboy.owl.survey.data.writer.Writer;
import com.javacowboy.owl.util.Constants.Property;

public class Processor {

	final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	Parser parser = new Parser();
	Writer writer = new Writer();
	
	long fileCounter = 0;

	public void process() {
		// UILogger.log("Processing");
		logger.info("Processing");
		File inputDir = new File(Property.INPUT_DIRECTORY.getPropertyValue());
		File outputDir = new File(Property.OUTPUT_DIRECTORY.getPropertyValue());
		File dataFile = new File(outputDir, Property.OUTPUT_DATA_FILE.getPropertyValue());
		File usfsFile = new File(outputDir, Property.OUTPUT_USFS_FILE.getPropertyValue());

		delete(dataFile);
		delete(usfsFile);
		writer.appendToFile(dataFile, OwlData.getHeader(), false);
		writer.appendToFile(usfsFile, USFSData.getHeader(), false);
		logger.info("Searching directory: " + inputDir.getPath());
		handlePath(inputDir, dataFile, usfsFile);

	}

	void handlePath(File path, File dataFile, File usfsFile) {
		if (path.isDirectory()) {
			for (File file : path.listFiles()) {
				handlePath(file, dataFile, usfsFile);
			}
		} else if (path.isFile() && path.getName().contains(".doc")) {
			handleFile(path, dataFile, usfsFile);
		}
	}

	void handleFile(File file, File dataFile, File usfsFile) {
		fileCounter++;
		parser.parse(file, fileCounter);
		OwlData owlData = parser.getOwlData();
		USFSData usfsData = parser.getUsfsData();
		writer.appendToFile(dataFile, owlData.toString(), true);
		writer.appendToFile(usfsFile, usfsData.toString(), true);
	}

	void delete(File file) {
		logger.info("Deleting: " + file.getPath());
		if (file.exists()) {
			file.delete();
		}
	}

}
