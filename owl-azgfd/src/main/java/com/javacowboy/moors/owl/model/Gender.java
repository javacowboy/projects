package com.javacowboy.moors.owl.model;

public enum Gender {

	//can you make sex = M = Male and sex = F = Female and sex = U = Unknown
	M("Male"),
	F("Female"),
	U("Unknown");
	
	private String value;
	private Gender(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Gender getByName(String name) {
		for(Gender e : Gender.values()) {
			if(e.name().toLowerCase().equals(name.toLowerCase())) {
				return e;
			}
		}
		return null;
	}
}
