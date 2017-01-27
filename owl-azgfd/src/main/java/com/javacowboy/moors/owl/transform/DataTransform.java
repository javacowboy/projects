package com.javacowboy.moors.owl.transform;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.javacowboy.moors.owl.model.AzgfdData;
import com.javacowboy.moors.owl.model.OwlData;
import com.javacowboy.moors.owl.model.Species;

@Component
public class DataTransform {
	
	private static final SimpleDateFormat inFormat = new SimpleDateFormat("yyyy-MMMMM-dd");
	
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
		out.setSex(in.getSex());
		out.setStage(in.getAge());
		out.setDate(getDate(in));
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

}
