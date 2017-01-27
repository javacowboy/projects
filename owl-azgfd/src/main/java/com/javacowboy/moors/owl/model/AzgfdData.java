package com.javacowboy.moors.owl.model;

import java.util.Date;

/*
 * SN("Scientific Name"),	
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
	DISPOSITION("Disposition"),	
	MUSEUM("Museum"),	
	MARKED("Marked"),	
	TAG("Field Tag"),	
	HABITAT("Habitat Description"),	
	LOCALE("Other locality data"),	
	COMMENT("Comments (reproductive status, behavior, etc.)");
	
	the # column will always be = 1, the disposition column will always say Not Handled, and the Datum column will always be NAD83
 
	
 */
public class AzgfdData {
	
	private String scientificName;
	private String commonName;
	private Integer number;
	private Date date;
	private String county;
	private String water;
	private Integer east;
	private Integer north;
	private String zone;
	private String datum;
	private String stage;
	private String sex;
	private String disposition;
	private String museum;
	private String marked;
	private String tag;
	private String habitat;
	private String location;
	private String comment;
	
	public String getScientificName() {
		return scientificName;
	}
	public void setScientificName(String scientificName) {
		this.scientificName = scientificName;
	}
	public String getCommonName() {
		return commonName;
	}
	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getWater() {
		return water;
	}
	public void setWater(String water) {
		this.water = water;
	}
	public Integer getEast() {
		return east;
	}
	public void setEast(Integer east) {
		this.east = east;
	}
	public Integer getNorth() {
		return north;
	}
	public void setNorth(Integer north) {
		this.north = north;
	}
	public String getZone() {
		return zone;
	}
	public void setZone(String zone) {
		this.zone = zone;
	}
	public String getDatum() {
		return datum;
	}
	public void setDatum(String datum) {
		this.datum = datum;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getDisposition() {
		return disposition;
	}
	public void setDisposition(String disposition) {
		this.disposition = disposition;
	}
	public String getMuseum() {
		return museum;
	}
	public void setMuseum(String museum) {
		this.museum = museum;
	}
	public String getMarked() {
		return marked;
	}
	public void setMarked(String marked) {
		this.marked = marked;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public String getHabitat() {
		return habitat;
	}
	public void setHabitat(String habitat) {
		this.habitat = habitat;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
}
