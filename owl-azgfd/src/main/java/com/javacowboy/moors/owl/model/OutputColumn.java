package com.javacowboy.moors.owl.model;

public enum OutputColumn {
	
	SN("Scientific Name"),	
	CN("Common Name"),
	NUM("#"),
	DATE("Date"),	
	COUNTY("County"),	
	WATER("Body of Water"),
	EAST("easting or latitude"),	
	NORTH("northing or longitude"),
	ZONE("UTM Zone"),	
	DATUM("Datum"),	
	STAGE("Lifestage"),	
	SEX("sex"),	
	DISPOSITION("Dispotition"),	
	MUSEUM("Museum"),	
	MARKED("Marked"),	
	TAG("Field Tag"),	
	HABITAT("Habitat Description"),	
	LOCATION("Other locality data"),	
	COMMENT("Comments (reproductive status, behavior, etc.)");
	
	private String columnName;
	private OutputColumn(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnName() {
		return columnName;
	}
}
