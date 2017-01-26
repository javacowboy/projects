package com.javacowboy.moors.owl.spreadsheet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.javacowboy.moors.owl.model.AzgfdData;
import com.javacowboy.moors.owl.model.OutputColumn;

@Component
public class ExcelWriter {
	
	private static final String SHEET_DATA = "Report Form";
	private static final String SHEET_NOTES = "Notes";
	private static final String SHEET_WATER_LIST = "Water body List";

	public void createSheet(File outFile, List<AzgfdData> outData) {
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(SHEET_DATA);
        int rowNumber = 0;
		rowNumber = createHeader(sheet, rowNumber);
		rowNumber = writeData(sheet, outData, rowNumber);
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			workbook.write(fileOutputStream);
			workbook.close();
		} catch (IOException e) {
			System.err.println("Error saving file: " + outFile.getName());
			e.printStackTrace();
		}
	}
	
	public void appendToSheet(File outFile, List<AzgfdData> outData) {
		// TODO Auto-generated method stub
		
	}
	
	private int createHeader(XSSFSheet sheet, int rowNumber) {
		Row row = sheet.createRow(rowNumber++);
		int columnNumber = 0;
		for(OutputColumn column : OutputColumn.values()) {
			Cell cell = row.createCell(columnNumber++);
			cell.setCellValue(column.getColumnName());
		}
		return rowNumber;
	}

	private int writeData(XSSFSheet sheet, List<AzgfdData> outData, int rowNumber) {
		for(AzgfdData data : outData) {
			Row row = sheet.createRow(rowNumber++);
			writeRow(row, data);
		}
		return rowNumber;
	}

	private void writeRow(Row row, AzgfdData data) {
		int columnNumber = 0;
		for(OutputColumn column : OutputColumn.values()) {
			Cell cell = row.createCell(columnNumber++);
			switch(column) {
			case EAST:
				writeCell(cell, data.getEast());
				break;
			case NORTH:
				writeCell(cell, data.getNorth());
				break;
			case SEX:
				writeCell(cell, data.getSex());
				break;
			case STAGE:
				writeCell(cell, data.getStage());
				break;
			case ZONE:
				writeCell(cell, data.getZone());
				break;
			default:
				writeCell(cell, "");
				break;
			}
		}
	}
	
	private void writeCell(Cell cell, String value) {
		if(value == null) {
			value = "";
		}
		cell.setCellValue(value);
	}
	
	private void writeCell(Cell cell, Integer value) {
		if(value == null) {
			writeCell(cell, "");
		}else {
			cell.setCellValue(value);
		}
	}

}
