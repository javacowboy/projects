package com.javacowboy.owl.survey.ui;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;

import com.javacowboy.owl.util.Constants.Property;

public class UI {

	static final String RUN_BUTTON_LABEL = "Run";
	
	static final String APP_TITLE = "Gather Owl Survey Data";
	static final String APP_INSTRUCTIONS = "Select the options below and click " + RUN_BUTTON_LABEL;
	
	static final String INPUT_DIR_BUTTON_LABEL = "Choose Survey Directory";
	static final String INPUT_DIR_LABEL = "Survey Directory:";
	
	static final String OUTPUT_DIR_BUTTON_LABEL = "Choose Output Directory";
	static final String OUTPUT_DIR_LABEL = "Output Directory:";
	static final String OUTPUT_FILE_LABEL = "Output File:";
	static final String OUTPUT_USFS_FILE_LABEL = "USFS File:";
	
	static JButton runButton;
	static JTextArea logArea;
	
	public static void log(String message) {
		if(logArea != null) {
			logArea.append(message + "\n");
		}
	}
	
	public static void disableRunButton() {
		if(runButton != null) {
			runButton.setEnabled(false);
		}
	}
	
	public static void enableRunButton() {
		if(runButton != null) {
			runButton.setEnabled(true);
		}
	}

	public static void createAndShowGui(ActionListener runActionListener) {
		final JFrame frame = new JFrame(APP_TITLE);
		frame.setSize(1000, 750);//width, height
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new GridLayout(6, 1));//rows, columns
		
		instructionSection(frame);
		inputSection(frame);
		outputSection(frame);
		outputFilesSection(frame);
		runSection(frame, runActionListener);
		logSection(frame);
		
		frame.pack();
		frame.setVisible(true); //do this last so all components are visible
	}

	private static void instructionSection(JFrame frame) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel(APP_INSTRUCTIONS);
		panel.add(label);
		frame.add(panel);
	}

	private static void inputSection(final JFrame parent) {
		JPanel inputPanel = new JPanel();
		parent.add(inputPanel);
		
		//label new to currently selected directory
		JLabel inputLabel = new JLabel(INPUT_DIR_LABEL);
		inputPanel.add(inputLabel);
		
		//show currently selected directory
		final JTextComponent selectedDirectoryLabel = new JTextField(Property.INPUT_DIRECTORY.getPropertyValue());
		selectedDirectoryLabel.setEditable(false);
		inputPanel.add(selectedDirectoryLabel);
		
		//directory chooser button
		JButton button = new JButton(INPUT_DIR_BUTTON_LABEL);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String directory = Property.INPUT_DIRECTORY.getPropertyValue();
				JFileChooser fileChooser = createFileChooser(parent, directory);
				Property.INPUT_DIRECTORY.setPropertyValue(fileChooser.getSelectedFile().getPath());
				selectedDirectoryLabel.setText(Property.INPUT_DIRECTORY.getPropertyValue());
			}
		});
		inputPanel.add(button);
	}
	
	private static void outputSection(final JFrame parent) {
		JPanel outputPanel = new JPanel();
		parent.add(outputPanel);
		
		//label new to currently selected directory
		JLabel inputLabel = new JLabel(OUTPUT_DIR_LABEL);
		outputPanel.add(inputLabel);
		
		//show currently selected directory
		final JTextComponent selectedDirectoryLabel = new JTextField(Property.OUTPUT_DIRECTORY.getPropertyValue());
		selectedDirectoryLabel.setEditable(false);
		outputPanel.add(selectedDirectoryLabel);
		
		//directory chooser button
		JButton button = new JButton(OUTPUT_DIR_BUTTON_LABEL);
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String directory = Property.OUTPUT_DIRECTORY.getPropertyValue();
				if(directory == null && Property.INPUT_DIRECTORY.getPropertyValue() != null) {
					File input = new File(Property.INPUT_DIRECTORY.getPropertyValue());
					if(input.exists()) {
						directory = input.getParentFile().getPath();
					}
				}
				JFileChooser fileChooser = createFileChooser(parent, directory);
				Property.OUTPUT_DIRECTORY.setPropertyValue(fileChooser.getSelectedFile().getPath());
				selectedDirectoryLabel.setText(Property.OUTPUT_DIRECTORY.getPropertyValue());
			}
		});
		outputPanel.add(button);
	}
	
	private static void outputFilesSection(JFrame parent) {
		JPanel panel = new JPanel();
		parent.add(panel);
		
		//label and input for output file
		JLabel label = new JLabel(OUTPUT_FILE_LABEL);
		panel.add(label);
		final JTextField output = new JTextField(Property.OUTPUT_DATA_FILE.getPropertyValue());
		output.setColumns(20);
		output.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				Property.OUTPUT_DATA_FILE.setPropertyValue(output.getText());
				output.setText(Property.OUTPUT_DATA_FILE.getPropertyValue());
			}
			@Override
			public void focusGained(FocusEvent e) {
				output.selectAll();
			}
		});
		panel.add(output);
		
		//label and input for usfs file
		label = new JLabel(OUTPUT_USFS_FILE_LABEL);
		panel.add(label);
		final JTextField usfs = new JTextField(Property.OUTPUT_USFS_FILE.getPropertyValue());
		usfs.setColumns(20);
		usfs.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				Property.OUTPUT_USFS_FILE.setPropertyValue(usfs.getText());
				usfs.setText(Property.OUTPUT_USFS_FILE.getPropertyValue());
			}
			@Override
			public void focusGained(FocusEvent e) {
				usfs.selectAll();
			}
		});
		panel.add(usfs);
		
	}
	
	private static void runSection(JFrame parent, ActionListener runActionListener) {
		JPanel runPanel = new JPanel();
		parent.add(runPanel);
		
		JButton button = new JButton(RUN_BUTTON_LABEL);
		runPanel.add(button);
		button.addActionListener(runActionListener);
		
		//global button
		UI.runButton = button;
	}

	private static void logSection(JFrame parent) {
		JPanel panel = new JPanel();
		parent.add(panel);
		
		JTextArea textArea = new JTextArea(10, 80);
		JScrollPane scroll = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		panel.add(scroll);
		
		//global logger area
		UI.logArea = textArea;
		
	}

	protected static JFileChooser createFileChooser(JFrame parent, String defaultLocation) {
		JFileChooser fileChooser;
		if(defaultLocation != null && !defaultLocation.isEmpty()) {
			fileChooser = new JFileChooser(defaultLocation);
		}else {
			fileChooser = new JFileChooser();
		}
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.showOpenDialog(parent);
		return fileChooser;
	}

}
