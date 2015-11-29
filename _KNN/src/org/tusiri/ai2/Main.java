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

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


public class Main {
		
	public static int NATRIBUT = 0;
	public static String HEADER = "";
	public static ArrayList<String> listAttribute = new ArrayList<String>();
	public static void main(String args[]) throws IOException{
		String file;
		
		//File Path in windows OS
		//file = "C:\\TubesAI2\\dataset\\CarEvaluation\\car.arff";
		file = "C:\\Users\\Wilhelm\\tugasBesarAI\\TubesAI\\dataset\\CarEvaluation\\car.arff";
		
		//Fila Path in Linux OS
		//file = "/home/alberttriadrian/Documents/Albert/TubesIF/Ai2/dataset/dataTest/test.data";
		ArffLoader arffloader=new ArffLoader();
		
		File filedata = new File(file);
		arffloader.setFile(filedata);

		Instances data = arffloader.getDataSet();
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
		/*
		kNN kn = new kNN(listDatum,3);
		kn.nFold(10);
		*/
		
		
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
	}
	
}
