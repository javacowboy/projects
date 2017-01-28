package com.javacowboy.moors.owl.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
	private static final SimpleDateFormat outFormat = new SimpleDateFormat("yyyy-MM-dd");

	public void createSheet(File outFile, List<AzgfdData> outData) {
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(SHEET_DATA);
        int rowNumber = 0;
		rowNumber = createHeader(sheet, rowNumber);
		rowNumber = writeData(sheet, outData, rowNumber);
		workbook.createSheet(SHEET_NOTES);
		workbook.createSheet(SHEET_WATER_LIST);
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
		try {
			FileInputStream fileInputStream = new FileInputStream(outFile);
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet dataSheet = workbook.getSheet(SHEET_DATA);
			int lastRow = dataSheet.getPhysicalNumberOfRows();
			if(!isEmptyRow(dataSheet, lastRow)) {
				lastRow++;
			}
			writeData(dataSheet, outData, lastRow);
			FileOutputStream fileOutputStream = new FileOutputStream(outFile);
			workbook.write(fileOutputStream);
			workbook.close();
		} catch (IOException e) {
			System.err.println("Error appending to file: " + outFile.getName());
			e.printStackTrace();
		}
	}
	
	private boolean isEmptyRow(Sheet dataSheet, int rowNumber) {
		Row row = dataSheet.getRow(rowNumber);
		if(row == null || row.getPhysicalNumberOfCells() == 0) {
			return true;
		}
		return false;
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

	private int writeData(Sheet sheet, List<AzgfdData> outData, int rowNumber) {
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
			case COUNTY:
				writeCell(cell, data.getCounty());
				break;
			case CN:
				writeCell(cell, data.getCommonName());
				break;
			case EAST:
				writeCell(cell, data.getEast());
				break;
			case DATE:
				writeCell(cell, data.getDate());
				break;
			case DATUM:
				writeCell(cell, data.getDatum());
				break;
			case DISPOSITION:
				writeCell(cell, data.getDisposition());
				break;
			case LOCATION:
				writeCell(cell, data.getLocation());
				break;
			case NORTH:
				writeCell(cell, data.getNorth());
				break;
			case NUM:
				writeCell(cell, data.getNumber());
				break;
			case SEX:
				writeCell(cell, data.getSex());
				break;
			case SN:
				writeCell(cell, data.getScientificName());
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
	
	private void writeCell(Cell cell, Date date) {
		String dateString = "";
		if(date != null) {
			dateString = outFormat.format(date);
		}
		writeCell(cell, dateString);
	}

}
