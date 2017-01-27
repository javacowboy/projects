package com.javacowboy.moors.owl.model;

public enum Lifestage {

	A("Adult"), 
	U("Unknown"), 
	S("Subadult"), 
	Y("Nestling"), 
	N("Nest")
	;
	
	private String value;
	private Lifestage(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	public static Lifestage getByName(String name) {
		for(Lifestage e : Lifestage.values()) {
			if(e.name().toLowerCase().equals(name.toLowerCase())) {
				return e;
			}
		}
		return null;
	}
}
