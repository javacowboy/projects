package com.javacowboy.moors.owl.transform;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.javacowboy.moors.owl.model.AzgfdData;
import com.javacowboy.moors.owl.model.OwlData;

@Component
public class DataTransform {
	
	public AzgfdData convert(OwlData in) {
		AzgfdData out = new AzgfdData();
		
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

}
