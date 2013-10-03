package com.javacowboy.owl.survey.data.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.javacowboy.owl.survey.data.model.OwlData;
import com.javacowboy.owl.survey.data.model.USFSData;

public class Converter {
	
	public void convertDate(String dateString, OwlData owlData) {
		if(dateString == null) {
			return;
		}
		if(dateString.contains(",")){
	  		splitDate(dateString, "MMM dd, yyyy", owlData);
	  	}else if(dateString.contains("/")){
	  		splitDate(dateString, "MM/d/yyyy", owlData);
	  	}else if(dateString.split(" ").length == 3){
	  		splitDate(dateString, "dd MMM yyyy", owlData);
	  	}else{
	    	owlData.setMonth(dateString);
	    }
	}
	
	public void convertObservers(String value, OwlData owlData) {
		if(value != null && value.contains(",")){
	  		String[] observers = value.split(",");
	  		for(int i=0; i<observers.length && i<3; i++){
	  			if(i == 0){
	  				owlData.setObserver1(observers[0].trim());
	  			}
	  			if(i == 1){
	  				owlData.setObserver2(observers[1].trim());
	  			}
	  			if(i == 2){
	  				owlData.setObserver3(observers[2].trim());
	  			}
	  		}
	  	}else{
	    	owlData.setObserver1(value);
	    }
	}
	
	public void convertPacData(String value, OwlData owlData, USFSData usfsData) {
		System.out.println("Splitting PAC: " + value);
		String number = "";
		for(char c : value.toCharArray()){
			if(isNumber(c)){
				number += c;
			}
		}
		if(!number.isEmpty()){
			owlData.setPacNumber(number);
	        usfsData.setPacNumber(number);
		}
		//one worker puts the pac number in parens ().  remove the parens and the number from the name
		owlData.setPacName(value.replace(number, "").replace("()", "").trim());
	    usfsData.setPacName(owlData.getPacName());
	}

	protected void splitDate(String value, String datePattern, OwlData owlData){
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(datePattern);
			Date date = formatter.parse(value);
			//println("Was: " + value + " Now: " + date)
			if(date != null){
				formatter = new SimpleDateFormat("MMMM");
				owlData.setMonth(formatter.format(date));
				formatter = new SimpleDateFormat("d");
				owlData.setDay(formatter.format(date));
				formatter = new SimpleDateFormat("yyyy");
				owlData.setYear(formatter.format(date));
			}
		}catch(ParseException e) {
			System.out.println("!!Error!!: " + value + " doesn't match pattern: " + datePattern);
			owlData.setMonth(value);
		}
	}
	
	protected boolean isNumber(char value){
        try{
            Integer.parseInt(String.valueOf(value));
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

}
