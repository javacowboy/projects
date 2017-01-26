package com.javacowboy.moors.owl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javacowboy.moors.owl.model.AzgfdData;
import com.javacowboy.moors.owl.model.OwlData;
import com.javacowboy.moors.owl.spreadsheet.ExcelReader;
import com.javacowboy.moors.owl.spreadsheet.ExcelWriter;
import com.javacowboy.moors.owl.transform.DataTransform;

@SpringBootApplication
public class OwlAzgfdApplication {
	
	@Inject private ExcelReader excelReader;
	@Inject private ExcelWriter excelWriter;
	@Inject private DataTransform dataTransform;

	public static void main(String[] args) {
//		SpringApplication.run(OwlAzgfdApplication.class, args);
		OwlAzgfdApplication instance = new OwlAzgfdApplication();
		instance.run();
	}

	private void run() {
		File outSheet = getOutputSheet();
		List<File> inputSheets = getInputSheets();
		for(File sheet : inputSheets) {
			System.out.println("Parsing file: " + sheet.getName());
			List<OwlData> inData = excelReader.readSheet(sheet);
			List<AzgfdData> outData = dataTransform.convert(inData);
			excelWriter.appendToSheet(outSheet, outData);
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
				}else if(file.isFile() && file.getName().toLowerCase().endsWith(".xlsx")) {
					list.add(file);
				}
			}
		}
		return list;
	}
}
