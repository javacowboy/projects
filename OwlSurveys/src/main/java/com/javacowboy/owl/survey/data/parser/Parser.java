package com.javacowboy.owl.survey.data.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.HeaderStories;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;
import org.apache.poi.hwpf.usermodel.Table;
import org.apache.poi.hwpf.usermodel.TableCell;
import org.apache.poi.hwpf.usermodel.TableRow;

import com.javacowboy.owl.survey.data.model.OwlData;
import com.javacowboy.owl.survey.data.model.USFSData;
import com.javacowboy.owl.survey.data.parser.NightMapping.BodyField;
import com.javacowboy.owl.survey.data.parser.NightMapping.HeaderField;

public class Parser {
	
	final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	private OwlData owlData;
	private USFSData usfsData;
	
	public void parse(File file, long recordNumber) throws FileNotFoundException, IOException {
		logger.info("Parsing file: " + file.getName());
		owlData = new OwlData(file, recordNumber);
		usfsData = new USFSData();
		HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(file));
		parseHeader(wordDoc, 1);//parse the header on page 1
		parseBody(wordDoc);
	}
	
	private void parseHeader(HWPFDocument wordDoc, int pageNumber) {
		System.out.println("Parsing header from page " + pageNumber);
		HeaderStories headerStore = new HeaderStories(wordDoc);
//        String header = headerStore.getHeader(pageNumber);
//        System.out.println("Header Is: "+header);
		Range range = headerStore.getRange();
		for(int i=0; i<range.numSections(); i++) {
			System.out.println("Section " + i);
			Section section = range.getSection(i);
			for(int j=0; j<section.numParagraphs(); j++) {
//				System.out.println("Paragraph " + j);
				Paragraph para = section.getParagraph(j);
				String line = getAsLine(para, j);
				handleHeaderLine(line, i, j);
				System.out.println();
			}
		}
	}

	private void parseBody(HWPFDocument wordDoc) {
		System.out.println("Parsing body");
		Range range = wordDoc.getRange();
		for(int i=0; i<range.numSections(); i++) {
			System.out.println("Section " + i);
			Section section = range.getSection(i);
			for(int j=0; j<section.numParagraphs(); j++) {
				Paragraph para = section.getParagraph(j);
				String line = getAsLine(para, j);
				handleBodyLine(line, i, j);
				System.out.println();
			}
		}
	}

	private String getAsLine(Paragraph para, int paraIndex) {
		StringBuilder line = new StringBuilder();
		for(int i=0; i<para.numCharacterRuns(); i++) {
			/*if(para.isInTable()) {//table
				handleTable(section.getTable(para));
			}*/
			//it seems like the last run contains something funky.
			//ignoring the last run sometimes misses text, but so far only in column headers
			if(para.isInTable() && i == para.numCharacterRuns() - 1) {
				CharacterRun run = para.getCharacterRun(i);
				System.out.println("Paragraph " + paraIndex + " is in a table, skipping: " + run.text());
			}else {
				CharacterRun run = para.getCharacterRun(i);
				String text = run.text();
				line.append(text);
			}
		}
		System.out.println("Run from paragraph " + paraIndex);
		System.out.println(line.toString().trim().isEmpty() ? "[EMPTY]" : line.toString().trim());
		return line.toString();
	}

	private void handleTable(Table table) {
		
		for (int tableIdx=0; tableIdx<table.numRows(); tableIdx++) {  
            TableRow row = table.getRow(tableIdx);  
            System.out.println("row "+(tableIdx+1)+", is table header: "+row.isTableHeader());  
            for (int colIdx=0; colIdx<row.numCells(); colIdx++) {  
            	TableCell cell = row.getCell(colIdx); 
                System.out.println("column "+(colIdx+1)+", text="+cell.getParagraph(0).text());
                System.out.println("num paras: " + cell.numParagraphs());
                System.out.println("num sections: " + cell.numSections());
                System.out.println("num runs: " + cell.numCharacterRuns());
                CharacterRun run = cell.getCharacterRun(cell.numCharacterRuns() -1);
            }  
        }  
	}

	private void handleHeaderLine(String line, int sectionNumber, int paragraphNumber) {
		List<HeaderField> expectedFields = NightMapping.getHeaderFieldsInLine(sectionNumber, paragraphNumber);
		if(!expectedFields.isEmpty()) {
			System.out.println("Searching for: " + expectedFields);
		}
	}
	
	private void handleBodyLine(String line, int sectionNumber, int paragraphNumber) {
		List<BodyField> expectedFields = NightMapping.getBodyFieldsInLine(sectionNumber, paragraphNumber);
		if(!expectedFields.isEmpty()) {
			System.out.println("Searching for: " + expectedFields);
		}
	}
	
	//getters
	public OwlData getOwlData() {
		return owlData;
	}

	public USFSData getUsfsData() {
		return usfsData;
	}

}
