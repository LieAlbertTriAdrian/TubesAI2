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
		String file;
		//Ivan Andrianto's File of dataset
		file = "C:\\Users\\Ivan\\Downloads\\TubesAI2-master\\TubesAI2-master\\dataset\\Car Evaluation\\car.data";
		
		//Albert Tri Adrian's File of dataset
		//file = "/home/alberttriadrian/Documents/Albert/TubesIF/Ai2/dataset/CarEvaluation/car.data";

		//Albert Tri Adrian's File of dataset (Testing)
		//file = "/home/alberttriadrian/Documents/Albert/TubesIF/Ai2/dataset/dataTest/test.data";

		
		FileInputStream fstream = new FileInputStream(file);
		BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
		String strLine;
		ArrayList<Car> listCar = new ArrayList<Car>();
		while ((strLine = br.readLine()) != null)   {
			String[] data = strLine.split(",");
					
			Car car = new Car();
			for(int i=0;i<NATRIBUT;i++){
				car.addAtr(data[i]);
			}
			car.setKelas(data[NATRIBUT]);
			listCar.add(car);
		}
		br.close();
		
		//Analysis Naive Bayes
		NaiveBayes nb = new NaiveBayes(listCar);
		nb.process();
		Car testInstance = listCar.get(0);
		//Instance yang mmau dites
		/*Car testInstance = new Car();
		testInstance.addAtr("sunny");
		testInstance.addAtr("cool");
		testInstance.addAtr("high");
		testInstance.addAtr("true");*/

		String result = nb.getClassResult(testInstance);
		System.out.println("hasil");
		System.out.println(result);
		

		//Analisis KNN
		
		
	}
	
}

