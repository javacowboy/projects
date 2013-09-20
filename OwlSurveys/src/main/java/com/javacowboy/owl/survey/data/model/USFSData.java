package com.javacowboy.owl.survey.data.model;

import java.util.ArrayList;
import java.util.List;

public class USFSData {
	SurveyType surveyType;
	String pacName;
	String pacNumber;
	List<USFSCallPoint> callPoints = new ArrayList<USFSCallPoint>();

	public static String getHeader() {
		StringBuilder builder = new StringBuilder();
		builder.append("PAC Name");
		builder.append(",");
		builder.append("PAC Number");
		builder.append(",");
		builder.append("Call Point Name");
		builder.append(",");
		builder.append("utmE");
		builder.append(",");
		builder.append("utmN");
		return builder.toString();
	}

	public boolean isNightSurvey() {
		return surveyType == SurveyType.NIGHT;
	}

	public void addCallPoint(String name, String utmE, String utmN) {
		USFSCallPoint callPoint = new USFSCallPoint();
		callPoint.name = name;
		callPoint.utmE = utmE;
		callPoint.utmN = utmN;
		callPoints.add(callPoint);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if (callPoints.isEmpty()) {
			appendBaseData(builder);
		} else {
			// the data must be right aligned in Word which causes it to be backwards when converted to txt
			for (int i = callPoints.size()-1; i >= 0; i--) {
				USFSCallPoint it = callPoints.get(i);
				appendBaseData(builder);
				builder.append(comma());
				builder.append(quote(it.name));
				builder.append(comma());
				builder.append(quote(it.utmE));
				builder.append(comma());
				builder.append(quote(it.utmN));
				builder.append("\r\n");
			}
		}
		return builder.toString();
	}

	protected void appendBaseData(StringBuilder builder) {
		builder.append(quote(pacName));
		builder.append(comma());
		builder.append(quote(pacNumber));
	}

	String comma() {
		return ",";
	}

	String quote(String text) {
		return quote(text, false);
	}

	String quote(String text, boolean preventFormat) {
		if (text == null) {
			text = "";
		}
		text = text.replace("\"", "");
		if (preventFormat) {
			text = preventExcelFormatting(text);
		}
		return "\"" + text + "\"";
	}

	String preventExcelFormatting(String text) {
		if (text == null || text.trim().isEmpty()) {
			return "";
		}
		return "'" + text;
	}
}
