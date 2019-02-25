package com.javacowboy.lcr.financedata.api.process.csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.javacowboy.lcr.financedata.api.process.model.BudgetDto;

@Service
public class CsvService {
	
	private static final Logger logger = Logger.getLogger(CsvService.class.getName());
	private static final String DELIMITER = ",";

	public void write(List<BudgetDto> rows, String outputFileName) {
		File outFile = getFile(outputFileName);
		String data = process(rows);
		write(data, outFile);
	}

	private void write(String data, File outFile) {
		try(PrintWriter writer = new PrintWriter(outFile)) {
			writer.write(getHeaderRow());
			writer.write(data);
		} catch (FileNotFoundException e) {
			logger.log(Level.SEVERE, "Error saving CSV", e);
		}
	}

	private String getHeaderRow() {
		String header = quote("Date") + DELIMITER;
		header += quote("Ref#") + DELIMITER;
		header += quote("Category") + DELIMITER;
		header += quote("Payee") + DELIMITER;
		header += quote("Payment Type") + DELIMITER;
		header += quote("Purpose") + DELIMITER;
		header += quote("Status") + DELIMITER;
		header += quote("Amount");
		header += System.lineSeparator();
		return header;
	}

	private String process(List<BudgetDto> rows) {
		StringBuilder builder = new StringBuilder();
		if(rows != null) {
			rows.forEach(row -> {
				builder.append(toCsv(row));
				builder.append(System.lineSeparator());
			});
		}
		return builder.toString();
	}

	private String toCsv(BudgetDto dto) {
		StringBuilder builder = new StringBuilder();
		builder.append(quote(dto.getDate())).append(DELIMITER);
		builder.append(quote(dto.getRefNumber())).append(DELIMITER);
		builder.append(quote(dto.getCategory())).append(DELIMITER);
		builder.append(quote(dto.getPayee())).append(DELIMITER);
		builder.append(quote(dto.getPaymentType())).append(DELIMITER);
		builder.append(quote(dto.getPurpose())).append(DELIMITER);
		builder.append(quote(dto.getStatus())).append(DELIMITER);
		builder.append(quote(dto.getAmount()));
		return builder.toString();
	}

	private String quote(Object value) {
		return value == null ? null : ("\"" + value.toString() + "\"");
	}

	private File getFile(String outputFileName) {
		return new File(outputFileName);
	}

}
