package com.javacowboy.fos;

import java.io.File;
import java.io.FilenameFilter;

public class Main {
	
	static final String FOS_IMPORT_FILE = "./BSA Friends of Scouting Import.csv";
	
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
		for(File file : listCsvFiles()) {
			String userResponse = promptUserForCorrectFile();
			if(userResponse.toLowerCase().startsWith("y")) {
				return file;
			}
		}
		return null;
	}

	protected File getImportFile() {
		return new File(FOS_IMPORT_FILE);
	}
	
	protected String promptUserForCorrectFile() {
		// TODO Auto-generated method stub
		return null;
	}
	
	protected void process(File ldsExportFile, File fosImportFile) {
		log(String.format("Processing file: %s", ldsExportFile.getName()));
		log(String.format("Creating file: %s", fosImportFile.getName()));
	}
	
	protected File[] listCsvFiles() {
		File directory = new File(".");
		return directory.listFiles(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				return name.toLowerCase().endsWith(".csv");
			}
		});
	}
	
	protected void log(String message) {
		System.out.println(message);
	}

}
