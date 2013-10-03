package com.javacowboy.owl.survey.data.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;

import org.junit.Test;

import com.javacowboy.owl.survey.data.model.OwlData;

public class ConverterTest {
	
	@Test
	public void convertDateTest() {
		Converter converter = new Converter();
		OwlData owlData = newOwlData();
		String dateString = "Apr 13, 2001";
		converter.convertDate(dateString, owlData);
		assertAprilThirteen2001(owlData);
		
		dateString = "April 13, 2001";
		converter.convertDate(dateString, owlData);
		assertAprilThirteen2001(owlData);
		
		dateString = "04/13/2001";
		converter.convertDate(dateString, owlData);
		assertAprilThirteen2001(owlData);
		
		dateString = "4/13/2001";
		converter.convertDate(dateString, owlData);
		assertAprilThirteen2001(owlData);
		
		dateString = "13 Apr 2001";
		converter.convertDate(dateString, owlData);
		assertAprilThirteen2001(owlData);
		
		dateString = "13 April 2001";
		converter.convertDate(dateString, owlData);
		assertAprilThirteen2001(owlData);
		
		dateString = "shouldn't parse";
		converter.convertDate(dateString, owlData);
		assertEquals(dateString, owlData.getMonth());
	}
	
	@Test
	public void convertDateEmptyTest() {
		Converter converter = new Converter();
		OwlData owlData = newOwlData();
		String dateString = null;
		converter.convertDate(dateString, owlData);
		assertNull(owlData.getMonth());
		assertNull(owlData.getDay());
		assertNull(owlData.getYear());
	}
	
	@Test
	public void convertObserversTest() {
		Converter converter = new Converter();
		OwlData owlData = newOwlData();
		String input = "homer";
		converter.convertObservers(input, owlData);
		assertEquals(input, owlData.getObserver1());
		
		owlData = newOwlData();
		input = "homer, marge";
		converter.convertObservers(input, owlData);
		assertEquals("homer", owlData.getObserver1());
		assertEquals("marge", owlData.getObserver2());
		
		owlData = newOwlData();
		input = "homer simpson, marge simpson, smithers";
		converter.convertObservers(input, owlData);
		assertEquals("homer simpson", owlData.getObserver1());
		assertEquals("marge simpson", owlData.getObserver2());
		assertEquals("smithers", owlData.getObserver3());

		owlData = newOwlData();
		input = "homer, marge, bart, lisa";
		converter.convertObservers(input, owlData);
		assertEquals("homer", owlData.getObserver1());
		assertEquals("marge", owlData.getObserver2());
		assertEquals("bart", owlData.getObserver3());
		//lisa gets truncated
	}
	
	@Test
	public void convertObserversEmptyTest() {
		Converter converter = new Converter();
		OwlData owlData = newOwlData();
		String input = null;
		converter.convertObservers(input, owlData);
		assertNull(owlData.getObserver1());
		assertNull(owlData.getObserver2());
		assertNull(owlData.getObserver3());
		
		input = "";
		converter.convertObservers(input, owlData);
		assertEquals("", owlData.getObserver1());
		assertNull(owlData.getObserver2());
		assertNull(owlData.getObserver3());
	}

	@Test
	public void convertPacDataTest() {
		//TODO complete this
	}
	
	@Test
	public void convertPacDataEmptyTest() {
		//TODO complete this
	}
	
	//helper methods
	private OwlData newOwlData() {
		return new OwlData(new File("test"), 1L);
	}

	private void assertAprilThirteen2001(OwlData owlData) {
		assertEquals("April", owlData.getMonth());
		assertEquals("13", owlData.getDay());
		assertEquals("2001", owlData.getYear());
	}

}
