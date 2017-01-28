package com.javacowboy.moors.owl.model;

/*
 * Data columns to grab from our database and what they correspond to in the state report database
 
	SPP = will be grabbed and converted to the common name and scientific name
	Mountain range = other locality data and we will add a comma and text saying Coronado National forest (ie, Huachuca Mountains, Coronado National Forest)
	Month, Day and Year get converted to the date format they use
	Sex = Sex
	Age = Lifestage and we will write it out, so Age = A would be Lifestate = Adult, U= Unknown, S= Subadult, Y= Nestling, Nest = Nest
	UTM E = easting column
	UTM N = northing column
	UTM Zone = UTM zone
 */
public enum InputColumn {
	
	SPP("Spp"),
	MOUNTAIN("Mountain Range"),
	MONTH("Month"),
	DAY("Day"),
	YEAR("Year"),
	SEX("Sex"),
	AGE("Age"),
	OBS_TYPE("Obs. Type"),
	PAC_NAME("PAC Name"),
	UTM_E("UTM E"),
	UTM_N("UTM N"),
	UTM_ZONE("UTM ZONE");
	
	private String columnName;
	private InputColumn(String columnName) {
		this.columnName = columnName;
	}
	
	public String getColumnName() {
		return columnName;
	}

	public static InputColumn getByName(String name) {
		for(InputColumn e : InputColumn.values()) {
			if(e.getColumnName().toLowerCase().equals(name.toLowerCase())) {
				return e;
			}
		}
		return null;
	}

}
