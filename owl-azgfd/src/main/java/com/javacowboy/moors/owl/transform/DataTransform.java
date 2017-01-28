package com.javacowboy.moors.owl.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.javacowboy.moors.owl.model.Area;
import com.javacowboy.moors.owl.model.AzgfdData;
import com.javacowboy.moors.owl.model.Gender;
import com.javacowboy.moors.owl.model.Lifestage;
import com.javacowboy.moors.owl.model.OwlData;
import com.javacowboy.moors.owl.model.Species;

@Component
public class DataTransform {
	
	private static final SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MMMMM-dd");
	private static final int DEFAULT_NUMBER = 1;
	private static final String DEFAULT_DISPOSITION = "Not Handled";
	private static final String DEFAULT_DATUM = "NAD83";
	private static final String MNT_RANGE_SUFFIX = ", Coronado National Forest";
		
	
	//The # column will always be = 1, the disposition column will always say Not Handled, and the Datum column will always be NAD83
	//Mountain range = other locality data and we will add a comma and text saying Coronado National forest (ie, Huachuca Mountains, Coronado National Forest)
	//Age = Lifestage and we will write it out, so Age = A would be Lifestate = Adult, U= Unknown, S= Subadult, Y= Nestling, Nest = Nest
	//Also, the age = -- and sex = -- are likely because Obs type = NEST…..I forgot to have you grab all the Obs type = NEST and put them as lifestage = NEST and then leave the age and sex as –
	public AzgfdData convert(OwlData in) {
		AzgfdData out = new AzgfdData();
		Species species = Species.getByName(in.getSpp());
		if(species != null) {
			out.setCommonName(species.getCommonName());
			out.setScientificName(species.getLatinName());
		}
		out.setEast(in.getUtmE());
		out.setNorth(in.getUtmN());
		out.setZone(in.getUtmZone());
		out.setSex(getSex(in.getSex()));
		if(in.getAge() != null && in.getAge().trim().equals(("--"))) {
			if(in.getObsType() != null && in.getObsType().toLowerCase().equals("nest")) {
				out.setStage(Lifestage.N.getValue());
			}else {
				System.err.println("Could not translate age: " + in.getAge());
				out.setStage(in.getAge());
			}
		}else {
			out.setStage(getLifeStage(in.getAge()));
		}
		out.setDate(getDate(in));
		out.setNumber(DEFAULT_NUMBER);
		out.setDisposition(DEFAULT_DISPOSITION);
		out.setDatum(DEFAULT_DATUM);
		out.setLocation(getLocation(in));
		out.setCounty(getCounty(in.getArea()));
		return out;
	}

	public List<AzgfdData> convert(List<OwlData> inData) {
		List<AzgfdData> outData = new ArrayList<>();
		if(inData != null) {
			for(OwlData data : inData) {
				outData.add(convert(data));
			}
		}
		return outData;
	}
	
	private Date getDate(OwlData in) {
		String dateString = in.getYear() + "-" + in.getMonth() + "-" + in.getDay();
		try {
			return inFormat.parse(dateString);
		} catch (ParseException e) {
			System.err.println("Error parsing date: " + dateString);
			e.printStackTrace();
		}
		return null;
	}
	
	private String getLocation(OwlData in) {
		String value = "";
		if(in.getPacName() != null) {
			value += in.getPacName() + " PAC, ";
		}
		if(in.getMountainRange() != null) {
			value += in.getMountainRange();
			if(!in.getMountainRange().toLowerCase().endsWith("mountains")) {
				value += " Mountains";
			}
			value += MNT_RANGE_SUFFIX;
		}
		return value;
	}
	
	private String getSex(String sex) {
		Gender gender = Gender.getByName(sex);
		if(gender != null) {
			return gender.getValue();
		}
		System.err.println("Could not translate sex: " + sex);
		return sex;
	}
	
	private String getLifeStage(String age) {
		Lifestage stage = Lifestage.getByName(age);
		if(stage != null) {
			return stage.getValue();
		}
		System.err.println("Could not translate age: " + age);
		return age;
	}
	
	private String getCounty(String area) {
		Area a = Area.getByArea(area);
		if(a != null) {
			return a.getCounty();
		}
		System.err.println("Could not translate county: " + area);
		return area;
	}

}
