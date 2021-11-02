#!/usr/bin/env groovy

import java.util.zip.*
import groovy.xml.XmlSlurper
import groovy.xml.slurpersupport.GPathResult

//vars
TOP_DIR = "C:/Users/Owner/Documents/Contractual work/Mt Graham and Chiricahuas/MT GRAHAM 2021/ALL DATASHEETS MT GRAHAM 2021" //Amanda's computer
//TOP_DIR = "../test/out" //my computer
NEST_DIR = TOP_DIR + "/Nest and Roost forms"
OUT_FILE = new File(NEST_DIR, "MSO NEST AND ROOST DATABASE.csv")

parsingLocationTable = false
parsingDescriptionTable = false
parsingCompositionTable = false


//main
top = new File(NEST_DIR)
if(!top.exists()) {
	println("Nest and Roost directory does not exist: " + NEST_DIR)
}else {
	clean()
	processFiles(top)
}

//methods
void clean() {
	OUT_FILE.delete()
	OUT_FILE.getParentFile().mkdirs()
	writeHeader()
}

void resetParsingVars() {
	parsingLocationTable = false
	parsingDescriptionTable = false
	parsingCompositionTable = false
}

void processFiles(dir) {
	dir.listFiles().each { file ->
		if(file.isFile()) {
			//handle docx files (that aren't currently open -- start with ~)
			if(file.getName().toLowerCase().endsWith(".docx") && !file.getName().startsWith("~")) {
				processDocFile(file)
			}
		}
	}
}

void processDocFile(docxFile) {
	println()
	println("Processing file: " + docxFile.getName())
	println()
	zip = new ZipFile(docxFile)
	header = getDocumentHeader(zip)
	body = getDocumentBody(zip)
	dto = new NestDto()
	dto.originalFile = docxFile.getName()
	parseHeader(header, dto)
	parseBody(body, dto)
	writeResult(dto)
	println("Data written to: " + OUT_FILE.getAbsolutePath())
}

void parseHeader(header, dto) {
	println("Parsing Word header data")
	header.children().each {
		text = it.text()
		parseForest(text, dto)
		parseDistrict(text, dto)
		parsePacInfo(text, dto)
		parseMountainRange(text, dto)
		parseDate(text, dto)
		parseLocationType(text, dto)
	}

}

void parseBody(body, dto) {
	println("Parsing Word body data")
	body.'w:body'.children().each {
		tag = it.name()
		if("tbl".equals(tag)) {
			processTable(it, dto)
		}else {
			text = it.text()
			//check if a table is coming next
			if(text.toLowerCase().contains("location data")) {
				parsingLocationTable = true
			} 
			if(text.toLowerCase().contains("physical description")) {
				parsingDescriptionTable = true
			} 
			if(text.toLowerCase().contains("composition and structure")) {
				parsingCompositionTable = true
			} 
			//parse other fields
			parseTopography(text, dto)
			parseSubstrate(text, dto)
		}
	}
}

void writeHeader() {
	String header = "Forest,District,Mtn. Range,Date measured,PAC Name,PAC Number,Observation type (Nest or Roost),UTME,UTMN,ELEVATION,TOPGRAPHY,SLOPE ASPECT,SLOPE %,SLOPE DEGREES,SUBSTRATE TYPE,SUBSTRATE HEIGHT,NEST ASPECT,NEST OR ROOST HEIGHT (FT),TREE SPECIES,DIAMETER OF TREE,Original File"
	writeToFile(header)
}

void writeResult(dto) {
	//println(dto.toCsv())
	writeToFile(dto.toCsv())
}

void writeToFile(value) {
	OUT_FILE.append(value)
	OUT_FILE.append("\r\n")
}

GPathResult getDocumentBody(zipFile) {
	println("Getting body of Word doc")
	entry = zipFile.getEntry('word/document.xml')
	stream = zipFile.getInputStream(entry)
	wordMl = new XmlSlurper().parse(stream).declareNamespace(w: 'http://schemas.openxmlformats.org/wordprocessingml/2006/main')
	//text = wordMl.'w:body'.children().collect { it.text() }.join('')
	return wordMl
}

GPathResult getDocumentHeader(zipFile) {
	iterator = zipFile.entries().iterator()
	while(iterator.hasNext()) {
		entry = iterator.next()
		if(entry.getName().contains("header")) {
			println("Checking header: " + entry.getName())
			stream = zipFile.getInputStream(entry)
			wordMl = new XmlSlurper().parse(stream)
			if(!wordMl.toString().isBlank()) {
				println("Found data in header: " + entry.getName())
				return wordMl
			}
		}
	}
	throw new Exception("No header found in Word document.")
}

void processTable(table, dto) {
	if(parsingLocationTable) {
		parseLocationTable(table, dto)
	}else if(parsingDescriptionTable) {
		parseDescriptionTable(table, dto)
	}else if(parsingCompositionTable) {
		parseCompositionTable(table, dto)
	}else {
		println("Ignoring table")
		println(table.text())
	}
	resetParsingVars()


//	table.children().each {
//		println("processing table " + it.text())
//		tag = it.name()
//		if("tr".equals(tag)) {
//			processTr(it)
//		}else {
//			println("ignoring:")		
//			println(it.text())
//		}
//	}
}

void parseLocationTable(table, dto) {
	println("Parsing location table")
	//row 2, column 2 = UTM E
	//row 2, column 3 = UTM N
	dto.utme = table.tr[1].tc[1].text()
	dto.utmn = table.tr[1].tc[2].text()

	//	table.tr[1].children().each {
//		println(it.name())
//		println("\t" + it.text())
//	}
}

void parseDescriptionTable(table, dto) {
	println("Parsing description table")
	//row 1, column 1 = elevation
	//row 2, column 1 = slopeAspect
	//row 2, column 2 = slopePercent
	//row 2, column 3 = slopeDegrees
	dto.elevation = extractNumberFromString(table.tr[0].tc[0].text())
	dto.slopeAspect = extractNumberFromString(table.tr[1].tc[0].text())
	dto.slopePercent = extractNumberFromString(table.tr[1].tc[1].text())
	dto.slopeDegrees = extractNumberFromString(table.tr[1].tc[2].text())
}

void parseCompositionTable(table, dto) {
	println("Parsing composition table")

}

String extractNumberFromString(value) {
	if(value == null) {
		return null
	}
	//result = value.replaceAll("[^\\d.]", "")
	result = value.replaceAll("[^0-9.]", "")
	//println(result)
	return result
}

void processTr(tr) {
	tr.children().each {
		println(it.name())
		println(it.text())
	}
}

void parseForest(text, dto) {
	begin = "Forest:"
	end = "District:"
	if(text.contains(begin)){
		println("Parsing forest")
		dto.forest = parse(text, begin, end)
	}
}

void parseDistrict(text, dto) {
	begin = "District:"
	end = "PAC Name and Number:"
	if(text.contains(begin)){
		println("Parsing district")
		dto.district = parse(text, begin, end)
	}
}

void parsePacInfo(text, dto) {
	//sometimes the form has PAC # and sometimes only PAC.  Sometime it has the name an number all merged together.
	//PAC Name and Number also has PAC in it.
	//Parse pac name, drop it's begin from the text, then parse for number
	begin = "PAC Name and Number:"
	end = null
	if(text.contains(begin)){
		println("Parsing PAC name")
		text = parse(text, begin, end)
		end = "PAC"
		if(text.contains(end)) {
			dto.pacName = parse(text, null, end)
		}else {
			dto.pacName = text		
		}
	}
	
	//now search for PAC number
	begin = "PAC"
	end = null
	if(text.contains(begin)){
		println("Parsing PAC number")
		value = parse(text, begin, end)
		dto.pacNumber = value.replace("#", "").trim()
	}
	
	//if PAC number is null, the number could be part of the name
	if(dto.pacNumber == null) {
		text = dto.pacName
		num = extractNumberFromString(text)
		if(num != null) {
			dto.pacNumber = num
			dto.pacName = dto.pacName.replace(num, "").trim()
		}
	}
}

void parseMountainRange(text, dto) {
	begin = "Mountain Range:"
	end = "Quad map name(s):"
	if(text.contains(begin)){
		println("Parsing mountain range")
		dto.mtnRange = parse(text, begin, end)
	}
}

void parseDate(text, dto) {
	begin = "Date:"
	end = "Location Type:"
	if(text.contains(begin)){
		//sometimes the location type is on the next line
		if(!text.contains(end)) {
			end = null
		}
		println("Parsing date")
		dto.dateMeasured = parse(text, begin, end)
	}
}

void parseLocationType(text, dto) {
	begin = "Location Type:"
	end = null
	if(text.contains(begin)){
		println("Parsing location")
		dto.observationType = parse(text, begin, end)
	}
}

void parseTopography(text, dto) {
	begin = "Topography"
	end = null
	if(text.contains(begin)){
		println("Parsing topography")
		text = parse(text, begin, end)
		if(text.contains("(choose one):")) {
			begin = "(choose one):"
			text = parse(text, begin, end)
		}
		dto.topography = text
	}
}

void parseSubstrate(text, dto) {
	begin = "Substrate:"
	end = null
	if(text.contains(begin) && !text.toLowerCase().contains("choose an item")){
		println("Parsing substrate")
		dto.substrateType = parse(text, begin, end)
	}
}

String parse(text, begin, end) {
	start = 0
	if(begin != null) {
		start = text.indexOf(begin) + begin.length()
	}
	stop = text.length()
	if(end != null) {
		stop = text.indexOf(end)
	}
	return text.substring(start, stop).trim()
}

//dto/pojos
public class NestDto {
	String forest
	String district
	String mtnRange
	String dateMeasured
	String pacName
	String pacNumber
	String observationType //nest or roost
	String utme
	String utmn
	String elevation
	String topography
	String slopeAspect
	String slopePercent
	String slopeDegrees
	String substrateType
	String substrateHeight
	String nestAspect
	String height
	String treeSpecies
	String treeDiameter
	String originalFile
	
	String quote(value) {
		if(value == null) {
			value = ""
		}
		return "\"" + value + "\""
	}
	
	String toCsv() {
		var delimiter = ","
		var builder = new StringBuilder()
		builder.append(quote(forest)).append(delimiter)
		builder.append(quote(district)).append(delimiter)
		builder.append(quote(mtnRange)).append(delimiter)
		builder.append(quote(dateMeasured)).append(delimiter)
		builder.append(quote(pacName)).append(delimiter)
		builder.append(quote(pacNumber)).append(delimiter)
		builder.append(quote(observationType)).append(delimiter)
		builder.append(quote(utme)).append(delimiter)
		builder.append(quote(utmn)).append(delimiter)
		builder.append(quote(elevation)).append(delimiter)
		builder.append(quote(topography)).append(delimiter)
		builder.append(quote(slopeAspect)).append(delimiter)
		builder.append(quote(slopePercent)).append(delimiter)
		builder.append(quote(slopeDegrees)).append(delimiter)
		builder.append(quote(substrateType)).append(delimiter)
		builder.append(quote(substrateHeight)).append(delimiter)
		builder.append(quote(nestAspect)).append(delimiter)
		builder.append(quote(height)).append(delimiter)
		builder.append(quote(treeSpecies)).append(delimiter)
		builder.append(quote(treeDiameter)).append(delimiter)
		builder.append(quote(originalFile))
	}
}