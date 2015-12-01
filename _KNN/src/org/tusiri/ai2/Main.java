package org.tusiri.ai2;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class Main {
		
	static String type = "NaiveBayes";
	static String training = "FullTraining";
	static File selectedFile = null;

	public static void main(String args[]) throws IOException{
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		JFrame frame = new JFrame("Tubes AI");
		frame.setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton buttonSelect = new JButton("Select File");
		final JLabel filePath = new JLabel("", JLabel.LEFT);
		JButton buttonAnalyze = new JButton("Analyze");
		JLabel typeGroupLabel = new JLabel("Type", JLabel.CENTER);    
		final JLabel result = new JLabel("", JLabel.CENTER);      
		final JTextArea textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane( textArea );
		
		
		final JRadioButton nbButton = new JRadioButton("Naive Bayes", true);
	    final JRadioButton knnButton = new JRadioButton("KNN");
	    
	    ButtonGroup typeGroup = new ButtonGroup();
	    typeGroup.add(nbButton);
	    typeGroup.add(knnButton);
	    
	    final JRadioButton ftButton = new JRadioButton("Full Training", true);
	    final JRadioButton tenFoldButton = new JRadioButton("10-Fold");

	    ButtonGroup trainingGroup = new ButtonGroup();
	    trainingGroup.add(ftButton);
	    trainingGroup.add(tenFoldButton);
	    
	    nbButton.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 type = "NaiveBayes";
	         }           
	      });
	    
	    knnButton.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 type = "KNN";
	         }           
	      });
	    
	    
	    ftButton.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 training = "FullTraining";
	         }           
	      });
	    
	    tenFoldButton.addItemListener(new ItemListener() {
	         public void itemStateChanged(ItemEvent e) {         
	        	 training = "tenFold";
	         }           
	      });
	      
	    buttonSelect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				JFileChooser fileChooser = new JFileChooser("C:\\TubesAI2\\dataset\\CarEvaluation");//biar ga lama cari2 folder
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					filePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
	    });
	    buttonAnalyze.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		if(selectedFile != null){
	    			textArea.setText("");
	    			Analysis a = new Analysis(type,training,selectedFile);
					a.analyze();
					result.setText("<html>Success : " + a.getSuccess() + "("+a.getSuccess() /a.getTotalInstances() * 100 +"%)<br>" +
							"Failed : " + a.getFailed() + "("+a.getFailed() / a.getTotalInstances() * 100 +"%)</html>" );
				      
					System.out.println(selectedFile.getName());
	    		}
	    	}
	    });
		
		frame.add( scroll );
		MessageConsole mc = new MessageConsole(textArea);
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(100);
		
		buttonSelect.setBackground(Color.BLACK);
		buttonSelect.setForeground(Color.WHITE);
		buttonSelect.setFont(new Font("Arial", Font.PLAIN, 12));
		
		frame.setResizable(false);
	    frame.add(buttonSelect);
	    frame.add(filePath);
	    
	    frame.add(buttonAnalyze);
	    
	    frame.add(scroll);
	    frame.add(result);
	    
	    frame.add(nbButton);
	    frame.add(knnButton);
	    frame.add(typeGroupLabel);
	    
	    frame.add(ftButton);
	    frame.add(tenFoldButton);
	    frame.add(typeGroupLabel);
	    
	    buttonSelect.setBounds(30,30,100,30);
	    filePath.setBounds(140,30,400,30);
	    
	    buttonAnalyze.setBounds(330,115,100,30);
	    result.setBounds(30,170,300,50);
	    
	    nbButton.setBounds(30,100,100,30);
	    knnButton.setBounds(30,130,100,30);
	    
	    ftButton.setBounds(170,100,100,30);
	    tenFoldButton.setBounds(170,130,100,30);
	    
	    scroll.setBounds(30,250,430,200);
	    frame.add(new JButton("Button 1"));
	    frame.add(new JButton("Button 2"));
	    frame.pack();
	    frame.setSize(500,500);
	    frame.setVisible(true);
	}
}
