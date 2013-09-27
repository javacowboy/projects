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

	private void mapNightHeaderValue(HeaderField headerField, String fieldValue, OwlData owlData, USFSData usfsData) {
		switch (headerField) {
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
			owlData.setRange(fieldValue);
			break;
		case OBSERVERS:
			converter.convertObservers(fieldValue, owlData);
			break;
		case PAC_NAME_NUMBER:
			converter.convertPacData(fieldValue, owlData, usfsData);
			break;
		case QUAD_MAP:
			owlData.setQuadMapName(fieldValue);
			break;
		case RESULT:
			owlData.setResult(fieldValue);
			break;
		default:
			throw new UnsupportedOperationException("Mapping for " + headerField + " is not supported.");
		}
	}

	private void mapNightBodyValue(BodyField bodyField, String fieldValue, OwlData owlData, USFSData usfsData) {
		switch (bodyField) {
		case ABORTED:
			owlData.setAborted(fieldValue);
			break;
		case CALL_POINT:
			
			break;
		case COMPLETED:
			owlData.setCompleted(fieldValue);
			break;
		case DATA_AGE:
			
			break;
		case DATA_BEARING:
			
			break;
		case DATA_CALL_METHOD:
			
			break;
		case DATA_CLOUD:
			
			break;
		case DATA_DISTANCE:
			
			break;
		case DATA_END:
			
			break;
		case DATA_METHOD:
			
			break;
		case DATA_MOON:
			
			break;
		case DATA_OBSERVE_TYPE:
		
		break;
		case DATA_ROUTE_NUMBER:
			
			break;
		case DATA_SEX:
			
			break;
		case DATA_SPECIES:
			
			break;
		case DATA_START:
			
			break;
		case DATA_TEMP:
			
			break;
		case DATA_TIME:
			
			break;
		case DATA_TOTAL:
			
			break;
		case DATA_UTME:
			
			break;
		case DATA_UTMN:
			
			break;
		case DATA_WATER:
			
			break;
		case DATA_WIND:
			
			break;
		case MONITOR_TYPE:
			owlData.setMonitorType(fieldValue);
			break;
		case MOON_PHASE:
			
			break;
		case OUTING_NUMBER:
			owlData.setOutingNumber(fieldValue);
			break;
		case PERCENT_COVERED:
			owlData.setAreaCovered(fieldValue);
			break;
		case SUN_TIME:
			owlData.setSunriseTime(fieldValue);
			owlData.setSunsetTime(fieldValue);
			break;
		case SURVEY_NUMBER:
			owlData.setSurveyNumber(fieldValue);
			break;
		case TIME_END:
			
			break;
		case TIME_START:
			
			break;
		case TIME_TOTAL:
			
			break;
		case UTME:
			
			break;
		case UTMN:
			
			break;
		default:
			throw new UnsupportedOperationException("Mapping for " + bodyField + " is not supported.");
		}
	}

}
