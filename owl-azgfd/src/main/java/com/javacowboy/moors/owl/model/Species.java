package com.javacowboy.moors.owl.model;

public enum Species {

	STOC("Mexican Spotted Owl", "Strix occidentalis lucida"),
	BUVI("Great Horned Owl", "Bubo virginianus"),
	ASOT("Long-eared Owl", "Asio otus"),
	OTFL("Flammulated Owl", "Otus flammeolus"),
	OTTR("Whiskered Screech Owl", "Otus trichopsis"),
	OTKE("Western Screech Owl", "Otus kennicottii"),
	MIWH("Elf Owl", "Micrathene whitneyi"),
	GLGN("Northern Pygmy Owl", "Glaucidium gnoma"),
	;
	
	private String latinName;
	private String commonName;
	private Species(String commonName, String latinName) {
		this.commonName = commonName;
		this.latinName = latinName;
	}
	public String getLatinName() {
		return latinName;
	}
	public String getCommonName() {
		return commonName;
	}
	
	public static Species getByName(String name) {
		for(Species e : Species.values()) {
			if(e.name().toLowerCase().equals(name.toLowerCase())) {
				return e;
			}
		}
		return null;
	}
}
