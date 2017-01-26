package com.javacowboy.moors.owl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javacowboy.moors.owl.model.AzgfdData;
import com.javacowboy.moors.owl.model.OwlData;
import com.javacowboy.moors.owl.spreadsheet.ExcelReader;
import com.javacowboy.moors.owl.spreadsheet.ExcelWriter;
import com.javacowboy.moors.owl.transform.DataTransform;

@SpringBootApplication
public class OwlAzgfdApplication implements CommandLineRunner {
	
	@Inject private ExcelReader excelReader;
	@Inject private ExcelWriter excelWriter;
	@Inject private DataTransform dataTransform;

	public static void main(String[] args) {
		SpringApplication.run(OwlAzgfdApplication.class, args);
	}

	@Override
	public void run(String...args) {
		File outSheet = getOutputSheet();
		List<File> inputSheets = getInputSheets();
		int fileNumber = 0;
		for(File inSheet : inputSheets) {
			fileNumber++;
			System.out.println("Parsing file: " + inSheet.getName());
			List<OwlData> inData = excelReader.readSheet(inSheet);
			List<AzgfdData> outData = dataTransform.convert(inData);
			if(fileNumber == 1) {
				System.out.println("Generating file: " + outSheet.getName());
				excelWriter.createSheet(outSheet, outData);
			}else {
				System.out.println("Appending data to file: " + outSheet.getName());
				excelWriter.appendToSheet(outSheet, outData);
			}
		}
	}

	private File getOutputSheet() {
		File output = new File("azgfd_output.xlsx");
		output.delete();
		return output;
	}

	private List<File> getInputSheets() {
		List<File> list = new ArrayList<>();
		File dataDir = new File("data");
		if(dataDir.exists()) {
			list = getExcelFiles(dataDir);
		}
		return list;
	}
	
	private List<File> getExcelFiles(File dir) {
		List<File> list = new ArrayList<>();
		if(dir.isDirectory()) {
			for(File file : dir.listFiles()) {
				if(file.isDirectory()) {
					list.addAll(getExcelFiles(file));
				}else if(file.isFile() && file.getName().toLowerCase().endsWith(".xlsx") && file.getName().toLowerCase().contains("database")) {
					list.add(file);
				}
			}
		}
		return list;
	}
}
