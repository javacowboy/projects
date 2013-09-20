package com.javacowboy.owl.survey.data.model;

public enum SurveyType {
	DAY("Daytime"), NIGHT("Nighttime");

	private String text;

	private SurveyType(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}
}
