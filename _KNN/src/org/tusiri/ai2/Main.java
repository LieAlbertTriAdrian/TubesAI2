package org.tusiri.ai2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


public class Main extends JPanel implements ActionListener {
	static private final String newline = "\n";
    JButton openButton, pickButton;
    JTextArea log;
    JFileChooser fc;
	public static int NATRIBUT = 0;
	public static String HEADER = "";
	public static ArrayList<String> listAttribute = new ArrayList<String>();
	
	public Main(){
		super(new BorderLayout());
		
		//Create the log panel first
		log = new JTextArea(5,20);
        log.setMargin(new Insets(5,5,5,5));
        log.setEditable(false);
        JScrollPane logScrollPane = new JScrollPane(log);
		
        //Create a file chooser
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        //Create the open button.
        openButton = new JButton("Open a File...",createImageIcon("images/Open16.gif"));
        openButton.addActionListener(this);

        //create the pick method button
        pickButton = new JButton("Pick a method...",createImageIcon("images/Save16.gif"));
        pickButton.addActionListener(this);

        //For layout purposes, put the buttons in a separate panel
        JPanel buttonPanel = new JPanel(); //use FlowLayout
        buttonPanel.add(openButton);
        buttonPanel.add(pickButton);

        //Add the buttons and the log to this panel.
        add(buttonPanel, BorderLayout.PAGE_START);
        add(logScrollPane, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {
		//Handle open button action.
        if (e.getSource() == openButton) {
            int returnVal = fc.showOpenDialog(Main.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
            	ArffLoader arffloader=new ArffLoader();
                File file = fc.getSelectedFile();
                try {
					arffloader.setFile(file);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
                Instances data;
				
                try {
					data = arffloader.getDataSet();
					NATRIBUT = data.numAttributes()-1;
					for (int k = 0; k< NATRIBUT; k++){
	        			String dataAttribute = data.attribute(k).toString();
	        			String[] s = dataAttribute.split(" ");
	        			listAttribute.add(s[1]);
	        			HEADER += (s[1] + " ");
	        		}
					//Initiate list of Datum
					ArrayList<Datum> listDatum = new ArrayList<Datum>();
					
					//Get data from arff file.
					//System.out.println("NATRIBUT = " + NATRIBUT);
					for(int i = 0; i < data.numInstances();i++){
						Instance instance = data.instance(i);
					    //System.out.println("Instance:" + instance.stringValue(0));
					    
					    Datum datum = new Datum(i);
					    for(int j=0;j<NATRIBUT;j++){
							datum.addAtr(instance.stringValue(j));
						}
					    datum.setKelas(instance.stringValue(NATRIBUT));
					    listDatum.add(datum);
					 }

					
					//Naive Bayes Analysis
					NaiveBayes nb = new NaiveBayes(listDatum,NATRIBUT,listAttribute);
					nb.process();
					//
					
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
        		
                
                /*
        		int success = 0;
        		int failed = 0;
        		for(int k=0;k<listDatum.size();k++){
        			Datum instance = listDatum.get(k);
        			//System.out.print("Instance : ");
        			//instance.printDatum();
        			//System.out.print("Naive Bayes Result : ");
        			String result = nb.getClassResult(instance);
        			//System.out.println(result);
        			//System.out.print("Status : ");
        			if (result.equals(instance.getKelas())){
        				success++;
        				//System.out.println("Success");
        			}
        			else{
        				failed++;
        				//System.out.println("failed");
        			}
        		}
        		
        		System.out.println("\n=============Summary==========");
        		double totalInstances = success + failed;
        		System.out.println("Success : " + success + "("+success /totalInstances * 100 +"%)");
        		System.out.println("Failed : " + failed + "("+failed / totalInstances * 100 +"%)");
        		
        	*/
        		
                log.append("Opening: " + file.getName() + "." + newline);
            }
            log.setCaretPosition(log.getDocument().getLength());

        //Handle pick button action.
        } else if (e.getSource() == pickButton) {
            //not implemented yet
        }
	}
        
        /** Returns an ImageIcon, or null if the path was invalid. */
        protected static ImageIcon createImageIcon(String path) {
            java.net.URL imgURL = Main.class.getResource(path);
            if (imgURL != null) {
                return new ImageIcon(imgURL);
            } else {
                System.err.println("Couldn't find file: " + path);
                return null;
            }
        }

        /**
         * Create the GUI and show it.  For thread safety,
         * this method should be invoked from the
         * event dispatch thread.
         */
        private static void createAndShowGUI() {
            //Create and set up the window.
            JFrame frame = new JFrame("FileChooserDemo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            //Add content to the window.
            frame.add(new Main());

            //Display the window.
            frame.pack();
            frame.setVisible(true);
        }

        public static void main(String[] args) {
            //Schedule a job for the event dispatch thread:
            //creating and showing this application's GUI.
            SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    //Turn off metal's use of bold fonts
                    UIManager.put("swing.boldMetal", Boolean.FALSE); 
                    createAndShowGUI();
                }
            });
        }	
	}
