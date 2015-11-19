package org.tusiri.ai2;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {
	
	public static final int NATRIBUT = 6;

	public static void main(String args[]) throws IOException{
		FileInputStream fstream = new FileInputStream("C:\\Users\\Wilhelm\\tubesAI\\TubesAI\\dataset\\CarEvaluation\\car.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		ArrayList<Instance> listCar = new ArrayList<Instance>();
		int ID = 1;
		while ((strLine = br.readLine()) != null)   {
			String[] data = strLine.split(",");
			
			Instance car = new Instance(ID);
			for(int i=0;i<data.length-1;i++){
				car.addAtr(data[i]);
			}
			car.setKelas(data[data.length-1]);
			listCar.add(car);
		}
		br.close();
		
		/*
		//Analysis Naive Bayes
		NaiveBayes nb = new NaiveBayes(listCar);
		nb.process();*/
		
		//Analisis KNN
			
		kNN kn = new kNN(listCar);
		//System.out.println(kn.HitungJarak(kn.getInstanceList().get(0), kn.getInstanceList().get(2)));
		
	}
	
}
