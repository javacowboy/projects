package com.javacowboy.fos.schema;

public enum BsaSchema implements Schema {
	//this is also the expected order of the columns in the import file
	LAST_NAME("last_name"),
	FIRST_NAME("first_name"),
	SPOUSE_NAME("spouse_first"),
	ADDRESS("addresss"),
	CITY("city"),
	STATE("state"),
	ZIP("zip"),
	PHONE("phone"),
	EMAIL("email");
	
	private String columnName;
	private BsaSchema(String columnName) {
		this.columnName = columnName;
	}
	
	@Override
	public String getColumnName() {
		return columnName;
	}
}
