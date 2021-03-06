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
import com.javacowboy.owl.survey.data.model.SurveyType;
import com.javacowboy.owl.survey.data.model.USFSData;

public class Parser {
	
	final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	private OwlData owlData;
	private USFSData usfsData;
	private Mapper mapper;
	
	private static final int maxOffsetIncrements = 20;
	private int offsetLineNumber = 0; //sometimes there's a couple extra lines in files
	private int offsetAlteredCount = 0; //used to stop infinite loops
	
	public void parse(File file, long recordNumber) throws FileNotFoundException, IOException {
		logger.info("Parsing file: " + file.getName());
		owlData = new OwlData(file, recordNumber);
		usfsData = new USFSData();
		mapper = new Mapper();
		HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(file));
		SurveyType surveyType = determineSurveyType(wordDoc);
		owlData.setSurveyType(surveyType);
		usfsData.setSurveyType(surveyType);
		parseHeader(surveyType, wordDoc, 1);//parse the header on page 1
		parseBody(surveyType, wordDoc);
	}
	
	private SurveyType determineSurveyType(HWPFDocument wordDoc) {
		//Parse the header.  Day has Pac Name: and Pac Number:.  Night has Pac Name and Number:
		HeaderStories headerStories = new HeaderStories(wordDoc);
		Range range = headerStories.getRange();
		for(int i=0; i<range.numSections(); i++) {
			Section section = range.getSection(i);
			resetOffset(0);
			for(int j=0; j<section.numParagraphs(); j++) {
				Paragraph para = section.getParagraph(j);
				String line = getAsLine(para, j);
				if(line.contains(NightMapping.HeaderField.PAC_NAME_NUMBER.getLabelInDocument())) {
					System.out.println("Survey Type: " + SurveyType.NIGHT.toString()); 
					System.out.println();
					return SurveyType.NIGHT;
				}else if(line.contains(DayMapping.HeaderField.PAC_NAME.getLabelInDocument())) {
					System.out.println("Survey Type: " + SurveyType.DAY.toString()); 
					System.out.println();
					return SurveyType.DAY;
				}
				offsetLineNumber++;//increment offset with paragraph number
			}
		}
		throw new UnsupportedOperationException("Could not determine Night or Day Survey");
	}

	private void parseHeader(SurveyType surveyType, HWPFDocument wordDoc, int pageNumber) {
		System.out.println("Parsing header from page " + pageNumber);
		HeaderStories headerStore = new HeaderStories(wordDoc);
//        String header = headerStore.getHeader(pageNumber);
//        System.out.println("Header Is: "+header);
		Range range = headerStore.getRange();
		for(int i=0; i<range.numSections(); i++) {
			System.out.println("Section " + i);
			Section section = range.getSection(i);
			resetOffset(0);
			for(int j=0; j<section.numParagraphs(); j++) {
//				System.out.println("Paragraph " + j);
				Paragraph para = section.getParagraph(j);
				String line = getAsLine(para, j);
				handleHeaderLine(surveyType, line, i, j);
				offsetLineNumber++;//increment offset with paragraph number
			}
		}
	}

	private void parseBody(SurveyType surveyType, HWPFDocument wordDoc) {
		System.out.println("Parsing body");
		Range range = wordDoc.getRange();
		for(int i=0; i<range.numSections(); i++) {
			System.out.println("Section " + i);
			Section section = range.getSection(i);
			resetOffset(0);
			for(int j=0; j<section.numParagraphs(); j++) {
				Paragraph para = section.getParagraph(j);
				String line = getAsLine(para, j);
				handleBodyLine(surveyType, line, i, j);
				offsetLineNumber++;//increment offset with paragraph number
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
				System.out.println("Paragraph " + paraIndex + " (offset: " + offsetLineNumber + ") is in a table, skipping: " + run.text());
			}else {
				CharacterRun run = para.getCharacterRun(i);
				String text = run.text();
				line.append(text);
			}
		}
		System.out.println("Run from paragraph " + paraIndex + " (offset: " + offsetLineNumber + ")");
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

	private void handleHeaderLine(SurveyType surveyType, String line, int sectionNumber, int paragraphNumber) {
		List<DocumentField> expectedFields = surveyType == SurveyType.NIGHT ? NightMapping.getHeaderFieldsInLine(sectionNumber, offsetLineNumber) : DayMapping.getHeaderFieldsInLine(sectionNumber, offsetLineNumber);
		if(!expectedFields.isEmpty()) {
			System.out.println("Searching for: " + expectedFields);
			parseLineForFields(expectedFields, line, paragraphNumber);
		}
	}
	
	private void handleBodyLine(SurveyType surveyType, String line, int sectionNumber, int paragraphNumber) {
		List<DocumentField> expectedFields = surveyType == SurveyType.NIGHT ? NightMapping.getBodyFieldsInLine(sectionNumber, offsetLineNumber) : DayMapping.getBodyFieldsInLine(sectionNumber, offsetLineNumber);
		if(!expectedFields.isEmpty()) {
			System.out.println("Searching for: " + expectedFields);
			parseLineForFields(expectedFields, line, paragraphNumber);
		}
	}
	
	private void parseLineForFields(List<DocumentField> expectedFields, String line, int paragraphNumber) {
		boolean gotExpectedValue = false;
		for(DocumentField field : expectedFields) {
			String fieldValue = parseForValue(field, expectedFields, line);
			if(fieldValue != null) {
				gotExpectedValue = true;
				resetOffset(paragraphNumber);
				mapper.map(field, fieldValue, owlData, usfsData);
			}else if(field.isInTableCell()) {
				gotExpectedValue = true; //whether the value is empty or not, we searched the cell
				//TODO: if end of row, add the details to the data
			}
		}
		if(!expectedFields.isEmpty() && !gotExpectedValue) {
			setOffset(paragraphNumber);
		}
		System.out.println();
	}

	private void setOffset(int paragraphNumber) {
		//if we haven't already rolled the offset too many times, adjust the offset
		if(offsetAlteredCount < maxOffsetIncrements) {
			offsetLineNumber--;
			offsetAlteredCount++;
		}else {
			resetOffset(paragraphNumber);
		}
	}

	private void resetOffset(int paragraphNumber) {
		offsetLineNumber = paragraphNumber;
		offsetAlteredCount = 0;
	}

	private String parseForValue(DocumentField field, List<DocumentField> otherFieldsInLine, String line) {
		// Night Survey #:  2	Outing #:  1	Survey Aborted?  N	Survey Completed?  Y   	% Survey Area Covered:  100
		//say we're searching for Survey Aborted?
		String label = field.getLabelInDocument();
		if(line != null && line.contains(label)) {
			line = line.substring(line.indexOf(label) + label.length());
			//now we have "  N	Survey Completed?  Y   	% Survey Area Covered:  100"
			for(DocumentField otherField : otherFieldsInLine) {
				//chop off everything so we're left with " N "
				String otherLabel = otherField.getLabelInDocument();
				if(line.contains(otherLabel)) {
					line = line.substring(0, line.indexOf(otherLabel));
				}
			}
			System.out.println("Found: " + line.trim());
			return line.trim();
		}
		return null;
	}

	//getters
	public OwlData getOwlData() {
		return owlData;
	}

	public USFSData getUsfsData() {
		return usfsData;
	}

}
