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
import javax.swing.JTextField;


public class Main {
		
	static String type = "NaiveBayes";
	static String training = "FullTraining";
	static File selectedFile = null;
	static int knumber = 1;
	public static void main(String args[]) throws IOException{
		final CustomDialog customDialog;
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		JDialog.setDefaultLookAndFeelDecorated(true);
		final JFrame frame = new JFrame("Tubes AI");
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
				JFileChooser fileChooser = new JFileChooser("F:\\KULIAH\\SEMESTER 5\\IF3170 Inteligensi Buatan\\TubesAI2\\dataset\\CarEvaluation");//biar ga lama cari2 folder
				int returnValue = fileChooser.showOpenDialog(null);
				if (returnValue == JFileChooser.APPROVE_OPTION) {
					selectedFile = fileChooser.getSelectedFile();
					filePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
				}
			}
	    });
	    customDialog = new CustomDialog(frame, "tusiri");
        customDialog.pack();
	    buttonAnalyze.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent ae) {
	    		String s;
	    		if(selectedFile != null){
	    			if (type.equals("KNN"))
	    			{
	    	        	customDialog.setLocationRelativeTo(frame);
	    	            customDialog.setVisible(true);
	    	            s = customDialog.getValidatedText();
	    	            knumber = Integer.parseInt(s);
	    			}
	    			textArea.setText("");
	    			Analysis a = new Analysis(type,training,selectedFile);
					a.analyze();  
					System.out.println(selectedFile.getName());
	    		}
	    	}
	    });
	    
	    //Setting interface for filePath
	    filePath.setFont(new Font("Candara", Font.PLAIN, 14));
	    filePath.setFont(filePath.getFont().deriveFont(filePath.getFont().getStyle() | Font.BOLD));
	    filePath.setForeground(Color.WHITE);
	    
	    nbButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
	    nbButton.setForeground(Color.WHITE);
	    nbButton.setBackground(new Color(94,100,114));
	    
	    knnButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
	    knnButton.setForeground(Color.WHITE);
	    knnButton.setBackground(new Color(94,100,114));
	    
	    ftButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
	    ftButton.setForeground(Color.WHITE);
	    ftButton.setBackground(new Color(94,100,114));
	    
	    tenFoldButton.setFont(new Font("Trebuchet MS", Font.PLAIN, 13));
	    tenFoldButton.setForeground(Color.WHITE);
	    tenFoldButton.setBackground(new Color(94,100,114));
	    
	    frame.getContentPane().setBackground(new Color(94,100,114));
		
	    frame.add( scroll );
		MessageConsole mc = new MessageConsole(textArea);
		
		mc.redirectOut();
		mc.redirectErr(Color.RED, null);
		mc.setMessageLines(300);
			
		buttonSelect.setBackground(new Color(39,35,58));
		buttonSelect.setForeground(Color.WHITE);
		buttonSelect.setFont(new Font("Dotum", Font.PLAIN, 14));
		
		buttonAnalyze.setBackground(new Color(21,39,35));
		buttonAnalyze.setForeground(Color.WHITE);
		buttonAnalyze.setFont(new Font("Dotum", Font.PLAIN, 14));
		
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
	    
	    scroll.setBounds(25,180,440,270);
	    frame.add(new JButton("Button 1"));
	    frame.add(new JButton("Button 2"));
	    frame.pack();
	    frame.setSize(500,500);
	    frame.setVisible(true);
	}
}
