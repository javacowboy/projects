package com.javacowboy.owl.survey.data.model;

public class DetailData {
	String routeNumber;
	String method;
	String callMethod;
	String start;
	String end;
	String total;
	String moon;
	String wind;
	protected Integer minWind;
	protected Integer maxWind;
	String cloud;
	String water;
	String temp;
	String obsType;
	String sex;
	String age;
	String species;
	String time;
	String bearing;
	String distance;
	String utmE;
	String utmN;

	public DetailData() {
	}

	// Copy constructor for weather fields (add all fields if needed)
	public DetailData(DetailData detailData) {
		if (detailData != null) {
			System.out.println("Copying existing DetailData");
			this.wind = detailData.wind;
			this.minWind = detailData.minWind;
			this.maxWind = detailData.maxWind;
			this.cloud = detailData.cloud;
			this.water = detailData.water;
			this.temp = detailData.temp;
		}
	}

	public void setWind(String wind) {
		this.wind = wind;
		String value = wind;
		if (value != null && value.contains("-")) {
			value = value.replace("?", "--");
			value = value.replace("--", "-");
			if (value.split("-").length > 0) {
				String first = value.split("-")[0];
				if (isNumber(first)) {
					Integer firstInt = Integer.valueOf(first);
					// the wind could be stronger at the begin or end of survey,
					// let setter take care of it
					setMinWind(firstInt);
					setMaxWind(firstInt);
				}

				String second = value.split("-")[1];
				if (isNumber(second)) {
					Integer secondInt = Integer.valueOf(second);
					// the wind could be stronger at the begin or end of survey,
					// let setter take care of it
					setMinWind(secondInt);
					setMaxWind(secondInt);
				}
			}
		}
	}

	public void setMinWind(Integer value) {
		if (minWind == null || (value.intValue() < minWind.intValue())) {
			System.out.println("Setting min wind: " + value + " current: "
					+ minWind + " wind: " + wind);
			minWind = value;
		}
	}

	public void setMaxWind(Integer value) {
		if (maxWind == null || (value.intValue() > maxWind.intValue())) {
			System.out.println("Setting max wind: " + value + " current: "
					+ maxWind + " wind: " + wind);
			maxWind = value;
		}
	}

	public String getMinWindString() {
		if (minWind != null) {
			return minWind.toString();
		}
		return wind;
	}

	public String getMaxWindString() {
		if (maxWind != null) {
			return maxWind.toString();
		}
		return wind;
	}

	boolean isNumber(String value) {
		try {
			Integer.parseInt(String.valueOf(value));
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
