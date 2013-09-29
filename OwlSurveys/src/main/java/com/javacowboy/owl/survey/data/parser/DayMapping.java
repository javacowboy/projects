package com.javacowboy.owl.survey.data.parser;

import java.util.ArrayList;
import java.util.List;

public class DayMapping { 
	
	public static List<DocumentField> getHeaderFieldsInLine(int sectionNumber, int paraNumber) {
		List<DocumentField> list = new ArrayList<DocumentField>();
		for(HeaderField e : HeaderField.values()) {
			if(e.getSectionNumber() == sectionNumber) {
				for(int paraNum : e.getParagraphNumbers()) {
					if(paraNum == paraNumber) {
						list.add(e);
						break;
					}
				}
			}
		}
		return list;
	}

	public static List<DocumentField> getBodyFieldsInLine(int sectionNumber, int paraNumber) {
		List<DocumentField> list = new ArrayList<DocumentField>();
		for(BodyField e : BodyField.values()) {
			if(e.getSectionNumber() == sectionNumber) {
				for(int paraNum : e.getParagraphNumbers()) {
					if(paraNum == paraNumber) {
						list.add(e);
						break;
					}
				}
			}
		}
		return list;
	}
	
	public enum HeaderField implements DocumentField {
		DATE("Date:", 0, 10),
		DISTRICT("District:", 0, 10),
		FOREST("Forest:", 0, 10),
		PAC_NAME("PAC Name:", 0, 11),
		PAC_NUMBER("PAC number:", 0, 11),
		PREDAWN("Survey Started Predawn?", 0, 11);
		
		private int sectionNumber;
		private int[] paragraphNumbers;
		private String labelInDocument;
		private boolean inTableCell; 
		private boolean endRowTableCell;
		
		private HeaderField(String labelInDocument, int sectionNumber, int ... paragraphNumbers) {
			this(labelInDocument, false, false, sectionNumber, paragraphNumbers);
		}
		
		private HeaderField(String labelInDocument, boolean inTableCell, boolean endRowTableCell, int sectionNumber, int ... paragraphNumbers) {
			this.sectionNumber = sectionNumber;
			this.paragraphNumbers = paragraphNumbers;
			this.labelInDocument = labelInDocument;
			this.inTableCell = inTableCell;
			this.endRowTableCell = endRowTableCell;
		}

		public int getSectionNumber() {
			return sectionNumber;
		}

		public int[] getParagraphNumbers() {
			return paragraphNumbers;
		}

		@Override
		public String getLabelInDocument() {
			return labelInDocument;
		}
		
		@Override
		public boolean isInTableCell() {
			return inTableCell;
		}

		@Override
		public boolean isEndRowTableCell() {
			return endRowTableCell;
		}

		public static HeaderField get(String labelInDocument) {
			for(HeaderField e : values()) {
				if(e.getLabelInDocument().equals(labelInDocument)) {
					return e;
				}
			}
			return null;
		}
		
	}
	
	public enum BodyField implements DocumentField {
		EVIDENCE_USED("Evidence used:", 0, 183),
		HABITAT("General habitat:", 0, 2),
		MICE_COUNT("Number Used:", 0, 132),
		MICE_USED("Mousing Used?", 0, 132),
		MOUNTAIN_RANGE("Mountain Range:", 0, 1),
		NEST_LOCATED("Nest located?", 0, 183),
		OBSERVERS("Observers:", 0, 0),
		OTHER_RAPTORS("Other raptors seen or heard during survey:", 0, 185),
		QUAD_MAP("Quad map name(s):", 0, 2),
		RESULT("Survey Results:", 0, 0),
		ROOST_LOCATED("Day roost located?", 0, 183),
		SUN_TIME("Sunrise or Sunset Time:", 0, 4),
		VISIT("Visit#:", 0, 3),
		
		//WEIRD hidden table starting at 6,7 ish in MORMON CANYON file???
		//34 is wind, 38 is clouds, 42 is precip, 46 is temp
		//35,36 is wind values
		//39, 40 is cloud values
		//43, 44 is precip values
		//47, 48 is temperature values
		WEATHER_WIND_START("", true , 0, 35),
		WEATHER_WIND_END("", true, 0, 36),
		WEATHER_CLOUD_START("", true, 0, 39),
		WEATHER_CLOUD_END("", true, 0, 40),
		WEATHER_PRECIP_START("", true, 0, 43),
		WEATHER_PRECIP_END("", true, 0, 44),
		WEATHER_TEMP_START("", true, 0, 47),
		WEATHER_TEMP_END("", true, true, 0, 48),
		
		//58-64 are data table headers
		//66-72, 74-80, (7 rows total)
		DATA_SPECIES("", true, 0, 66,74,82,90,98,106,114),
		DATA_SEX("", true, 0, 67),
		DATA_AGE("", true, 0, 68),
		DATA_TYPE("", true, 0, 69),
		DATA_TIME("", true, 0, 70),
		DATA_UTME("", true, 0, 71),
		DATA_UTMN("", true, true, 0, 72),
		
		//137-140 are mice table headers
		//142-145, 147-150 (6 rows total)
		MICE_MOUSE_NUMBER("", true, 0, 142,147,152,157,162,167),
		MICE_MOUSE_MALE("", true, 0, 143),
		MICE_MOUSE_FEMALE("", true, 0, 144),
		MICE_MOUSE_UNKNOWN("", true, true, 0, 145);
		
		private int sectionNumber;
		private int[] paragraphNumbers;
		private String labelInDocument;
		private boolean inTableCell;
		private boolean endRowTableCell;
		
		private BodyField(String labelInDocument, int sectionNumber, int ... paragraphNumbers) {
			this(labelInDocument, false, false, sectionNumber, paragraphNumbers);
		}
		
		private BodyField(String labelInDocument, boolean inTableCell, int sectionNumber, int ... paragraphNumbers) {
			this(labelInDocument, inTableCell, false, sectionNumber, paragraphNumbers);
		}
		
		private BodyField(String labelInDocument, boolean inTableCell, boolean endRowTableCell, int sectionNumber, int ... paragraphNumbers) {
			this.sectionNumber = sectionNumber;
			this.paragraphNumbers = paragraphNumbers;
			this.labelInDocument = labelInDocument;
			this.inTableCell = inTableCell;
			this.endRowTableCell = endRowTableCell;
		}

		public int getSectionNumber() {
			return sectionNumber;
		}

		public int[] getParagraphNumbers() {
			return paragraphNumbers;
		}

		@Override
		public String getLabelInDocument() {
			return labelInDocument;
		}
		
		@Override
		public boolean isInTableCell() {
			return inTableCell;
		}

		@Override
		public boolean isEndRowTableCell() {
			return endRowTableCell;
		}

		public static BodyField get(String labelInDocument) {
			for(BodyField e : values()) {
				if(e.getLabelInDocument().equals(labelInDocument)) {
					return e;
				}
			}
			return null;
		}
	}
}
