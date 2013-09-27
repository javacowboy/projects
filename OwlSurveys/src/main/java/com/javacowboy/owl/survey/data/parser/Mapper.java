package com.javacowboy.owl.survey.data.parser;

import com.javacowboy.owl.survey.data.model.OwlData;
import com.javacowboy.owl.survey.data.model.USFSData;
import com.javacowboy.owl.survey.data.parser.NightMapping.BodyField;
import com.javacowboy.owl.survey.data.parser.NightMapping.HeaderField;

public class Mapper {
	
	Converter converter = new Converter();
	
	public void map(DocumentField documentField, String fieldValue, OwlData owlData, USFSData usfsData) {
		HeaderField nightHeader = HeaderField.get(documentField.getLabelInDocument());
		BodyField bodyField = BodyField.get(documentField.getLabelInDocument());
		if(nightHeader != null) {
			mapNightHeaderValue(nightHeader, fieldValue, owlData, usfsData);
		}else if(bodyField != null) {
			mapNightBodyValue(bodyField, fieldValue, owlData, usfsData);
		}
	}

	private void mapNightHeaderValue(HeaderField nightHeader, String fieldValue, OwlData owlData, USFSData usfsData) {
		switch (nightHeader) {
		case DATE:
			converter.convertDate(fieldValue, owlData);
			break;
		case DISTRICT:
			owlData.setDistrict(fieldValue);
			break;
		case FOREST:
			owlData.setForest(fieldValue);
			break;
		case MOUNTAIN_RANGE:
			
			break;
		case OBSERVERS:
			converter.convertObservers(fieldValue, owlData);
			break;
		case PAC_NAME_NUMBER:
			
			break;
		case QUAD_MAP:
			
			break;
		case RESULT:
			
			break;
		default:
			throw new UnsupportedOperationException("Mapping for " + nightHeader + " is not supported.");
		}
	}

	private void mapNightBodyValue(BodyField bodyField, String fieldValue, OwlData owlData, USFSData usfsData) {
		// TODO Auto-generated method stub
		
	}

}
