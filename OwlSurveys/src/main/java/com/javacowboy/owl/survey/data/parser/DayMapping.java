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
		DATE("Date:", 0, 12),
		DISTRICT("District:", 0, 12),
		FOREST("Forest:", 0, 12),
		PAC_NAME("PAC Name:", 0, 13),
		PAC_NUMBER("PAC number:", 0, 13),
		PREDAWN("Survey Started Predawn?", 0, 13);
		
		private int sectionNumber;
		private int[] paragraphNumbers;
		private String labelInDocument;
		
		private HeaderField(String labelInDocument, int sectionNumber, int ... paragraphNumbers) {
			this.sectionNumber = sectionNumber;
			this.paragraphNumbers = paragraphNumbers;
			this.labelInDocument = labelInDocument;
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
		HABITAT("General habitat:", 0, 2),//
		MOUNTAIN_RANGE("Mountain Range:", 0, 1),//
		OBSERVERS("Observers:", 0, 0),//
		QUAD_MAP("Quad map name(s):", 0, 2),//
		RESULT("Survey Results:", 0, 0),//
		SUN_TIME("Sunrise or Sunset Time:", 0, 4),//
		VISIT("Visit#:", 0, 3),//
		//TODO: switch these all to day tables
		//6,7,8 & 10,11,12 are call point table headers
		//14,15,16 & 18,19,20
		//22,23,24 & 26,27,28
		//30,31,32 & 34,35,36
		//38,39,40 & 42,43,44
		CALL_POINT("", 0, 14,18,22,26,30,34,38,42),
		UTME("", 0, 15,19,23,27,31,35,39,43),
		UTMN("", 0, 16,20,24,28,32,36,40,44),
		//48,49,50,51,53,58,63 are time table headers
		//54,55,56 & 59,60,61 & 64,65,66
		TIME_START("", 0, 54,59,64),
		TIME_END("", 0, 55,60,65),
		TIME_TOTAL("", 0, 56,61,66),
		//69-91 are data table headers
		//93-112 & 114-133 & 135-154 & 156-175 & 177-196 & 198-217
		DATA_ROUTE_NUMBER("", 0, 93,114,135,156,177,198),
		DATA_CALL_METHOD("", 0, 94,115,136,157,178,199),
		DATA_METHOD("", 0, 95,116,137,158,179,200),
		DATA_START("", 0, 96,117,138,159,180,201),
		DATA_END("", 0, 97,118,139,160,181,202),
		DATA_TOTAL("", 0, 98,119,140,161,182,203),
		DATA_MOON("", 0, 99,120,141,162,183,204),
		DATA_WIND("", 0, 100,121,142,163,184,205),
		DATA_CLOUD("", 0, 101,122,143,164,185,206),
		DATA_WATER("", 0, 102,123,144,165,186,207),
		DATA_TEMP("", 0, 103,124,145,166,187,208),
		DATA_OBSERVE_TYPE("", 0, 104,125,146,167,188,209),
		DATA_SEX("", 0, 105,126,147,168,189,210),
		DATA_AGE("", 0, 106,127,148,169,190,211),
		DATA_SPECIES("", 0, 107,128,149,170,191,212),
		DATA_TIME("", 0, 108,129,150,171,192,213),
		DATA_BEARING("", 0, 109,130,151,172,193,214),
		DATA_DISTANCE("", 0, 110,131,152,173,194,215),
		DATA_UTME("", 0, 111,132,153,174,195,216),
		DATA_UTMN("", 0, 112,133,154,175,196,217);
		
		private int sectionNumber;
		private int[] paragraphNumbers;
		private String labelInDocument;
		
		private BodyField(String labelInDocument, int sectionNumber, int ... paragraphNumbers) {
			this.sectionNumber = sectionNumber;
			this.paragraphNumbers = paragraphNumbers;
			this.labelInDocument = labelInDocument;
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
