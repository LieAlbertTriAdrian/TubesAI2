package org.tusiri.ai2;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;


public class Main10Fold {
	
	
	public static final int NATRIBUT = 6;
	public static final int NFOLD = 10;
	public static void main(String args[]) throws IOException{
		System.out.println("10-FOLD");
		String file;
		//Ivan Andrianto's File of dataset
		//file = "C:\\Users\\Ivan\\Downloads\\TubesAI2-master\\TubesAI2-master\\dataset\\Car Evaluation\\car.data";
		
		//Albert Tri Adrian's File of dataset
		file = "/home/alberttriadrian/Documents/Albert/TubesIF/Ai2/dataset/CarEvaluation/car.data";
	 
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
			car.setKelas(data[6]);
			listCar.add(car);
		}
		br.close();
		int dataSize = listCar.size();
		int dataPerFold = dataSize/NFOLD;
		
		for(int i=0;i<NFOLD;i++){
			System.out.println("FOLD ke-" + i);
			int count = 0;
			ArrayList<Car> listTest = new ArrayList<Car>();
			ArrayList<Car> listTraining = new ArrayList<Car>();
			for(int j=0;j<dataSize;j++){
				if(((j >= i*dataPerFold) && (j < (i+1)*dataPerFold)) || ((i==NFOLD-1) && (j > NFOLD * dataPerFold))){
					//Masukkan ke data test
					for(int l=0;l<NATRIBUT;l++){
						listTest.add(listCar.get(j));
					}
					
				} else {
					//Masukkan ke data training
					listTraining.add(listCar.get(j));
				}
				
			}
			
			System.out.println("zz" + listTraining.size());
			NaiveBayes nb = new NaiveBayes(listTraining);
			nb.process();
			for(int k=0;k<listTest.size();k++){
				Car instance = listTest.get(k);
				String result = nb.getClassResult(instance);
				System.out.println(result);
			}
			listTest.clear();	
			listTraining.clear();
		}
	}
}
