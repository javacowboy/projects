package com.javacowboy.fos.service;

import java.util.ArrayList;
import java.util.List;

import com.javacowboy.fos.schema.BsaSchema;
import com.javacowboy.fos.schema.LdsSchema;
import com.javacowboy.fos.schema.Schema;

public class SchemaService {

	public List<LdsSchema> determineLdsColumnOrder(List<String> csvHeaderRow) {
		List<LdsSchema> list = new ArrayList<>();
		for(String header : csvHeaderRow) {
			for(LdsSchema column : LdsSchema.values()) {
				if(column.getColumnName().equals(header)) {
					list.add(column);
					break;
				}
			}
		}
		return list;
	}
	
	public List<BsaSchema> getBsaColumnOrder() {
		List<BsaSchema> list = new ArrayList<>();
		for(BsaSchema column : BsaSchema.values()) {
			list.add(column);
		}
		return list;
	}
	
	public List<String> toListOfStrings(List<? extends Schema> columns) {
		List<String> list = new ArrayList<>();
		for(Schema column : columns) {
			list.add(column.getColumnName());
		}
		return list;
	}

	public List<String> mapData(List<BsaSchema> bsaColumns, List<LdsSchema> ldsColumns, List<String> ldsDataRow) {
		List<String> bsaDataRow = new ArrayList<>();
		for(BsaSchema bsaColumn : bsaColumns) {
			String value = null;
			switch (bsaColumn) {
			case ADDRESS:
				value = getLdsValue(ldsColumns, ldsDataRow, LdsSchema.ADDRESS_1, LdsSchema.ADDRESS_2);
				break;
			case CITY:
				value = getLdsValue(ldsColumns, ldsDataRow, LdsSchema.CITY);
				break;
			case EMAIL:
				value = getLdsValue(ldsColumns, ldsDataRow, LdsSchema.EMAIL);
				break;
			case FIRST_NAME:
				value = getFirstName(ldsColumns, ldsDataRow, LdsSchema.PREFERRED_NAME);
				break;
			case LAST_NAME:
				value = getLastName(ldsColumns, ldsDataRow, LdsSchema.PREFERRED_NAME);
				break;
			case PHONE:
				value = getLdsValue(ldsColumns, ldsDataRow, LdsSchema.PHONE);
				break;
			case SPOUSE_NAME:
				value = getFirstName(ldsColumns, ldsDataRow, LdsSchema.SPOUSE_NAME);
				break;
			case STATE:
				value = getLdsValue(ldsColumns, ldsDataRow, LdsSchema.STATE);
				break;
			case ZIP:
				value = getLdsValue(ldsColumns, ldsDataRow, LdsSchema.ZIP);
				break;
			default:
				break;
			}
			bsaDataRow.add(value);
		}
		return bsaDataRow;
	}
	
	public String getFirstName(List<LdsSchema> ldsColumns, List<String> ldsDataRow, LdsSchema fullName) {
		return getName(getLdsValue(ldsColumns, ldsDataRow, fullName), 1);
	}

	public String getLastName(List<LdsSchema> ldsColumns, List<String> ldsDataRow, LdsSchema fullName) {
		return getName(getLdsValue(ldsColumns, ldsDataRow, fullName), 0);
	}

	public String getName(String fullName, int namePortionIndex) {
		String[] splitName = fullName.split(",");
		String name = "";
		if(namePortionIndex <= splitName.length - 1) {
			name = splitName[namePortionIndex];
		}
		return name;
	}

	public String getLdsValue(List<LdsSchema> ldsColumns, List<String> ldsDataRow, LdsSchema... fieldsToCombine) {
		String value = "";
		for(LdsSchema fieldToFind : fieldsToCombine) {
			int columnIndex = determineColumnIndex(ldsColumns, fieldToFind);
			if(columnIndex > -1) {
				value += ldsDataRow.get(columnIndex);
				value += " ";
			}
		}
		return value.trim();
	}

	public int determineColumnIndex(List<LdsSchema> ldsColumns, LdsSchema fieldToFind) {
		for(int i=0; i<ldsColumns.size(); i++) {
			if(ldsColumns.get(i).equals(fieldToFind)) {
				return i;
			}
		}
		return -1;
	}
	
}
