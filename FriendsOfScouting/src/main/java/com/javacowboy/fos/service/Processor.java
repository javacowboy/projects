package com.javacowboy.fos.service;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import net.quux00.simplecsv.CsvReader;
import net.quux00.simplecsv.CsvWriter;

import com.javacowboy.fos.schema.BsaSchema;
import com.javacowboy.fos.schema.LdsSchema;

public class Processor {
	
	SchemaService schemaService;
	
	public Processor() {
		this.schemaService = new SchemaService();
	}
	
	public void process(File ldsCsvFile, File bsaCsvFile) throws IOException {
		CsvReader reader = new CsvReader(new FileReader(ldsCsvFile));
		CsvWriter writer = new CsvWriter(new FileWriter(bsaCsvFile));
		Iterator<List<String>> iterator = reader.iterator();
		List<LdsSchema> ldsColumns = schemaService.determineLdsColumnOrder(iterator.next());
		List<BsaSchema> bsaColumns = schemaService.getBsaColumnOrder();
		writeHeaderRow(writer, bsaColumns);
		while(iterator.hasNext()) {
			writeBsaLine(writer, bsaColumns, ldsColumns, iterator.next());
		}
		reader.close();
		writer.close();
	}

	protected void writeHeaderRow(CsvWriter writer, List<BsaSchema> bsaColumns) {
		writer.writeNext(schemaService.toListOfStrings(bsaColumns));
	}

	protected void writeBsaLine(CsvWriter writer, List<BsaSchema> bsaColumns, List<LdsSchema> ldsColumns, List<String> ldsDataRow) {
		List<String> bsaDataRow = schemaService.mapData(bsaColumns, ldsColumns, ldsDataRow);
		writer.writeNext(bsaDataRow);
	}

}
