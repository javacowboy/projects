package com.javacowboy.owl.survey.data.writer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {
	
	public void appendToFile(File file, String data, boolean withBreak){
		append(file, data, withBreak);
	}

	protected void append(File file, String data, boolean withBreak) {
		try {
			file.getParentFile().mkdirs();
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)));
		    out.println(data);
		    out.close();
		} catch (IOException e) {
		    e.printStackTrace();
		}
	}

}
