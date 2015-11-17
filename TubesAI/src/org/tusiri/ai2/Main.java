package org.tusiri.ai2;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main {
	
	
	public static final int NATRIBUT = 6;

	public static void main(String args[]) throws IOException{
		FileInputStream fstream = new FileInputStream("C:\\Users\\Ivan\\Downloads\\TubesAI2-master\\TubesAI2-master\\dataset\\Car Evaluation\\car.data");
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		ArrayList<Car> listCar = new ArrayList<Car>();
		while ((strLine = br.readLine()) != null)   {
			String[] data = strLine.split(",");
			
			Car car = new Car();
			for(int i=0;i<NATRIBUT;i++){
				car.addAtr(data[i]);
			}
			car.setKelas(data[6]);
			listCar.add(car);
		}
		br.close();
		
		//Analysis Naive Bayes
		NaiveBayes nb = new NaiveBayes(listCar);
		nb.process();
		
		//Analisis KNN
		
		
	}
	
}