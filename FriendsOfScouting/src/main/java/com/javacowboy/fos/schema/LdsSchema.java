package com.javacowboy.fos.schema;

public enum LdsSchema implements Schema {
	PREFERRED_NAME("Preferred Name"),
	SPOUSE_NAME("Spouse Name"),
	ADDRESS_1("Address - Street 1"),
	ADDRESS_2("Address - Street 2"),
	CITY("Address - City"),
	STATE("Address - State"),
	ZIP("Address - Postal Code"),
	PHONE("Contact Phone Number"),
	EMAIL("Contact E-mail");
	
	private String columnName;
	private LdsSchema(String columnName) {
		this.columnName = columnName;
	}
	
	@Override
	public String getColumnName() {
		return columnName;
	}
}
