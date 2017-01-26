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
public class OwlData {
	
	private String spp;
	private String mountainRange;
	private String month;
	private Integer day;
	private Integer year;
	private String sex;
	private String age;
	private Integer utmE;
	private Integer utmN;
	private String utmZone;
	
	public String getSpp() {
		return spp;
	}
	public void setSpp(String spp) {
		this.spp = spp;
	}
	public String getMountainRange() {
		return mountainRange;
	}
	public void setMountainRange(String mountainRange) {
		this.mountainRange = mountainRange;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public Integer getDay() {
		return day;
	}
	public void setDay(Integer day) {
		this.day = day;
	}
	public Integer getYear() {
		return year;
	}
	public void setYear(Integer year) {
		this.year = year;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Integer getUtmE() {
		return utmE;
	}
	public void setUtmE(Integer utmE) {
		this.utmE = utmE;
	}
	public Integer getUtmN() {
		return utmN;
	}
	public void setUtmN(Integer utmN) {
		this.utmN = utmN;
	}
	public String getUtmZone() {
		return utmZone;
	}
	public void setUtmZone(String utmZone) {
		this.utmZone = utmZone;
	}

}
