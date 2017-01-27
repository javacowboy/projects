package com.javacowboy.moors.owl.model;

/*
 * We don’t have a column for county, but the state report does, so here are the conversions
	Mt. Graham database = Pinaleno Mountains = Graham
	Tumacori database = Atascosa/Pajarito Mountains = Santa Cruz
	Huachuacas database = Huachuca Mountains = Cochise
	Catalinas database = Catalina Mountains = Pima
 */
public enum Area {
	
	Graham("Graham"),
	Tumacacori("Santa Cruz"),
	Huachuca("Cochise"),
	Catalina("Pima")
	;
	
	private String county;
	private Area(String county) {
		this.county = county;
	}
	
	public String getCounty() {
		return county;
	}
	
	public static Area getByArea(String name) {
		for(Area e : Area.values()) {
			if(name != null && name.toLowerCase().contains(e.name().toLowerCase())) {
				return e;
			}
		}
		return null;
	}

}
