package com.javacowboy.fos;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Scanner;

import org.apache.commons.io.FileUtils;

import com.javacowboy.fos.service.Processor;

public class Main {
	
	static final String FOS_IMPORT_FILE = "./BSA Friends of Scouting Import.csv";
	static final String LOG_FILE = "./log.txt";
	protected File logFile;
	protected Scanner scanner;
	
	public static void main(String[] args) {
		Main instance = new Main();
		instance.run();
	}

	protected void run() {
		File ldsExportFile = selectFile();
		if(ldsExportFile == null) {
			log("An LDS MLS export file was not selected.  Put this program in the same directory as the exported csv file and run it again.");
			System.exit(1);
		}
		File fosImportFile = getImportFile();
		process(ldsExportFile, fosImportFile);
	}

	protected File selectFile() {
		File[] csvFiles = listCsvFiles();
		if(csvFiles != null) {
			if(csvFiles.length == 1) {
				return csvFiles[0];
			}
			for(File file : csvFiles) {
				String userResponse = promptUserForCorrectFile(file);
				if(userResponse.toLowerCase().startsWith("y")) {
					return file;
				}
			}
		}
		return null;
	}

	protected File getImportFile() {
		return new File(FOS_IMPORT_FILE);
	}
	
	protected void process(File ldsExportFile, File fosImportFile) {
		log(String.format("Processing file: %s", ldsExportFile.getName()));
		log(String.format("Creating file: %s", fosImportFile.getName()));
		fosImportFile.getParentFile().mkdirs();
		Processor processor = new Processor();
		try {
			processor.process(ldsExportFile, fosImportFile);
		} catch (IOException e) {
			log("Error while converting LDS file format to BSA file format.");
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	protected String promptUserForCorrectFile(File file) {
		if(scanner == null) {
			scanner = new Scanner(System.in);
		}
		System.out.println("Is this the correct LDS MLS export file: " + file.getName() + "? [yes/no]");
		return scanner.nextLine();
	}
	
	protected File[] listCsvFiles() {
		File directory = new File(".");
		return directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".csv") && !name.equals(new File(FOS_IMPORT_FILE).getName());
			}
		});
	}
	
	protected void log(String message) {
		if(logFile == null) {
			logFile = new File(LOG_FILE);
			logFile.delete();
		}
		try {
			FileUtils.write(logFile, message + "\n", "UTF-8", true);
		} catch (IOException e) {
			System.out.println(message);
			e.printStackTrace();
		}
	}

}
