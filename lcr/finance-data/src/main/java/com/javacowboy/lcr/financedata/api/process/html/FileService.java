package com.javacowboy.lcr.financedata.api.process.html;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;

@Service
public class FileService {

	public File getFile(String filePath) {
		return new File(filePath);
	}

	public void save(String data, String filePath) throws IOException {
		Files.write(Paths.get(filePath), data.getBytes());
	}

}
