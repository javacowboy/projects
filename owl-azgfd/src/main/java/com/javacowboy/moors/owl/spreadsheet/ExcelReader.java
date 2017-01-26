package com.javacowboy.moors.owl.spreadsheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Component;

import com.javacowboy.moors.owl.model.OwlData;

@Component
public class ExcelReader {
	
	public List<OwlData> readSheet(File excelFile) {
		try {
			FileInputStream fileInputStream = new FileInputStream(excelFile);
			Workbook workbook = new XSSFWorkbook(fileInputStream);
			Sheet dataSheet = workbook.getSheetAt(0);
			Iterator<Row> rowIterator = dataSheet.iterator();
			while(rowIterator.hasNext()) {
				Row row = rowIterator.next();
				Iterator<Cell> cellIterator = row.iterator();
				while(cellIterator.hasNext()) {
					Cell cell = cellIterator.next();
					if (cell.getCellType() == CellType.STRING.getCode()) {
                        System.out.println(cell.getStringCellValue());
                    } else if (cell.getCellType() == CellType.NUMERIC.getCode()) {
                        System.out.println(cell.getNumericCellValue());
                    }
				}
			}
			workbook.close();
		} catch (IOException e) {
			System.err.println("Error reading datasheet: " + excelFile.getName());
			e.printStackTrace();
		}
		return null;
	}

}
