package com.javacowboy.owl.survey.data.parser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.CharacterRun;
import org.apache.poi.hwpf.usermodel.Paragraph;
import org.apache.poi.hwpf.usermodel.Range;
import org.apache.poi.hwpf.usermodel.Section;

import com.javacowboy.owl.survey.data.model.OwlData;
import com.javacowboy.owl.survey.data.model.USFSData;

public class Parser {
	
	final Logger logger = Logger.getLogger(this.getClass().getSimpleName());
	
	private OwlData owlData;
	private USFSData usfsData;
	
	public void parse(File file, long recordNumber) throws FileNotFoundException, IOException {
		logger.info("Parsing file: " + file.getName());
		owlData = new OwlData(file, recordNumber);
		usfsData = new USFSData();
		HWPFDocument wordDoc = new HWPFDocument(new FileInputStream(file));
		Range range = wordDoc.getRange();
		for(int i=0; i<range.numSections(); i++) {
			System.out.println("Section " + i);
			Section section = range.getSection(i);
			for(int j=0; j<section.numParagraphs(); j++) {
				System.out.println("Paragraph " + j);
				Paragraph para = section.getParagraph(j);
				StringBuilder line = new StringBuilder();
				for(int k=0; k<para.numCharacterRuns(); k++) {
//					System.out.println("Run " + k);
					CharacterRun run = para.getCharacterRun(k);
					String text = run.text();
//					System.out.println(text);
					line.append(text);
				}
				System.out.println("Run from paragraph " + j);
				System.out.println(line.toString());
			}
		}
	}

	public OwlData getOwlData() {
		return owlData;
	}

	public USFSData getUsfsData() {
		return usfsData;
	}

}
