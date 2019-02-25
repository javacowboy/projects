package com.javacowboy.lcr.financedata.api.process.html;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import com.javacowboy.lcr.financedata.api.process.model.BudgetDto;

@Service
public class HtmlService {
	
	private static final Logger logger = Logger.getLogger(HtmlService.class.getName());

	public List<BudgetDto> parseHtml(String inputFileName) {
		File html = getHtmlFile(inputFileName);
		return parse(html);
	}

	private List<BudgetDto> parse(File html) {
		List<BudgetDto> list = new ArrayList<>();
		try {
			Document root = Jsoup.parse(html, "UTF-8");
			Element table = getTableElement(root);
			Elements dataRows = getDataRows(table);
			dataRows.forEach(row -> list.add(mapRow(row, new BudgetDto())));
		} catch (IOException e) {
			logger.log(Level.SEVERE, "Error parsing HTML", e);
		}
		return list;
	}

	private BudgetDto mapRow(Element row, BudgetDto dto) {
		Elements cells = getCells(row);
		for(int i=0; i<cells.size(); i++) {
			mapCell(i, cells.get(i), dto);
		}
		return dto;
	}

	private void mapCell(int index, Element cell, BudgetDto dto) {
		switch(index) {
		case 1:
			dto.setDate(parseDate(cell));
			break;
		case 2:
			dto.setRefNumber(parseRefNumber(cell));
			break;
		case 3:
			dto.setCategory(parseCategory(cell));
			break;
		case 4:
			dto.setPayee(parsePayee(cell));
			break;
		case 5:
			dto.setPaymentType(parsePaymentType(cell));
			break;
		case 6:
			dto.setPurpose(parsePurpose(cell));
			break;
		case 7:
			dto.setStatus(parseStatus(cell));
			break;
		case 8:
			dto.setAmount(parseAmount(cell));
			break;
		default:
			break;
		}
	}

	private String parseDate(Element cell) {
		return parseCell(cell);
	}

	private String parseRefNumber(Element cell) {
		return parseCell(cell);
	}

	private String parseCategory(Element cell) {
		return parseCell(cell);
	}

	private String parsePayee(Element cell) {
		return parseCell(cell);
	}

	private String parsePaymentType(Element cell) {
		return parseCell(cell);
	}

	private String parsePurpose(Element cell) {
		return parseCell(cell);
	}

	private String parseStatus(Element cell) {
		return parseCell(cell);
	}

	private Double parseAmount(Element cell) {
		String value = parseCell(cell);
		try {
			return Double.valueOf(value.replace("$", ""));
		}catch(NumberFormatException e) {
			return null;
		}
	}
	
	private String parseCell(Element cell) {
		//General method for parsing the text from the first p tag in the cell
		if(cell != null) {
			Elements paragraphs = cell.getElementsByTag("p");
			if(paragraphs != null && !paragraphs.isEmpty()) {
				Element paragraph = paragraphs.first();
				if(paragraph != null) {
					return paragraph.text();
				}
			}
		}
		return null;
	}

	private Elements getCells(Element row) {
		return row.getElementsByClass("divCell");
	}

	private Elements getDataRows(Element table) {
		//The first row is a header row, but has class: divHeading
		return table.getElementsByClass("divRow");
	}

	private Element getTableElement(Document root) {
		return root.getElementsByClass("tableContainer").first();
	}

	private File getHtmlFile(String inputFileName) {
		File file = new File(inputFileName);
		return file;
	}

}
