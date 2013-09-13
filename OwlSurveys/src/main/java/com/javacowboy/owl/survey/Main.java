package com.javacowboy.owl.survey;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.javacowboy.owl.survey.data.Processor;
import com.javacowboy.owl.survey.ui.UI;

public class Main {

	public static void main(String[] args) {
		Main instance = new Main();
		instance.run();
	}

	private void run() {
		UI.createAndShowGui(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				UI.disableRunButton();
				Processor processor = new Processor();
				processor.process();
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				UI.enableRunButton();
			}
		});
	}
}
