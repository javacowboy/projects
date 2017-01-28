package com.javacowboy.moors.owl.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.javacowboy.moors.owl.model.InputColumn;
import com.javacowboy.moors.owl.model.OwlData;

@Component
public class ExcelReader {
	
	public List<OwlData> readSheet(File excelFile) {
		List<OwlData> list = new ArrayList<>();
		try {
			FileInputStream fileInputStream = new FileInputStream(excelFile);
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet dataSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = dataSheet.iterator();
			int rowNumber = 0;
			Map<Integer, InputColumn> headerMap = null;
			while(rowIterator.hasNext()) {
				rowNumber++;
				Row row = rowIterator.next();
				if(rowNumber == 1) {
					headerMap = processHeader(row);
				}else {
					OwlData rowData = processRow(row, headerMap);
					//don't include negative data where SPP equals nothing. 
					//don't include where UTM E or N equals a "?" -- ? doesn't parse to a number, so it'll be null
					if(rowData.getSpp() != null && !rowData.getSpp().trim().isEmpty()
							&& rowData.getUtmE() != null && rowData.getUtmN() != null) {
						rowData.setArea(excelFile.getName());
						list.add(rowData);
					}
				}
			}
			workbook.close();
		} catch (IOException e) {
			System.err.println("Error reading datasheet: " + excelFile.getName());
			e.printStackTrace();
		}
		return list;
	}

	private Map<Integer, InputColumn> processHeader(Row row) {
		Map<Integer, InputColumn> map = new HashMap<>();
		Iterator<Cell> cellIterator = row.iterator();
		while(cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String name = cell.getStringCellValue();
			InputColumn column = InputColumn.getByName(name);
			if(column != null) {
				map.put(cell.getColumnIndex(), column);
			}
		}
		return map;
	}

	private OwlData processRow(Row row, Map<Integer, InputColumn> headerMap) {
		OwlData data = new OwlData();
		Iterator<Cell> cellIterator = row.iterator();
		while(cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			if(headerMap.containsKey(cell.getColumnIndex())) {
				processCell(cell, data, headerMap.get(cell.getColumnIndex()));
			}
		}
		return data;
	}

	private void processCell(Cell cell, OwlData data, InputColumn inputColumn) {
		switch(inputColumn) {
		case AGE:
			data.setAge(getString(cell));
			break;
		case DAY:
			data.setDay(getInteger(cell));
			break;
		case MONTH:
			data.setMonth(getString(cell));
			break;
		case MOUNTAIN:
			data.setMountainRange(getString(cell));
			break;
		case OBS_TYPE:
			data.setObsType(getString(cell));
			break;
		case SEX:
			data.setSex(getString(cell));
			break;
		case SPP:
			data.setSpp(getString(cell));
			break;
		case UTM_E:
			data.setUtmE(getInteger(cell));
			break;
		case UTM_N:
			data.setUtmN(getInteger(cell));
			break;
		case UTM_ZONE:
			data.setUtmZone(getString(cell));
			break;
		case YEAR:
			data.setYear(getInteger(cell));
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("deprecation")
	private Integer getInteger(Cell cell) {
		if(cell.getCellType() == CellType.NUMERIC.getCode()) {
			Double number = cell.getNumericCellValue();
			if(number != null) {
				return number.intValue();
			}
		}else if(cell.getCellType() == CellType.STRING.getCode()) {
			String number = cell.getStringCellValue();
			try {
				return Integer.valueOf(number);
			}catch(NumberFormatException e) {
				System.err.println("Could not convert to number: " + number);
			}
		}
		return null;
	}
	
	@SuppressWarnings("deprecation")
	private String getString(Cell cell) {
		if(cell.getCellType() == CellType.NUMERIC.getCode()) {
			return String.valueOf(cell.getNumericCellValue());
		}else if(cell.getCellType() == CellType.STRING.getCode()) {
			return cell.getStringCellValue();
		}
		return null;
	}

}
