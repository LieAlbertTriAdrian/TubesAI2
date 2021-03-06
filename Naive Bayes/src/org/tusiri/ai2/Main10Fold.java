package org.tusiri.ai2;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;


public class Main10Fold {

	  static void shuffleArray(ArrayList<Car> al)
	  {
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = al.size() - 1; i > 0; i--)
	    {
	      int index = rnd.nextInt(i + 1);
	      // Simple swap
	      Car instance = al.get(index);
	      al.set(index,al.get(i));
	      al.set(i,instance);
	    }
	  }
	
	public static final int NATRIBUT = 6;
	public static final int NFOLD = 10;
	public static void main(String args[]) throws IOException, InterruptedException{
		System.out.println("10-FOLD");
		String file;
		//Ivan Andrianto's File of dataset
		file = "C:\\TubesAI2\\dataset\\CarEvaluation\\car.data";
		//file = "C:\\TubesAI2\\dataset\\Zoo\\zoo.data";
		//file = "C:\\TubesAI2\\dataset\\dataTest\\test.data";
		
		//Albert Tri Adrian's File of dataset
		//file = "/home/alberttriadrian/Documents/Albert/TubesIF/Ai2/dataset/CarEvaluation/car.data";
	 
		
		
		
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
		
		
		shuffleArray(listCar);
		for(int i=0;i<10;i++){
			listCar.get(i).printCar();
		}
		
		int dataSize = listCar.size();
		//int dataPerFold = dataSize/NFOLD;
		int dataPerFold = dataSize/NFOLD;
		int sisa = dataSize % NFOLD;
		System.out.println(sisa);
		int dataPerFoldLebih = dataPerFold + 1;//Jika bukan kelipatan 10
		
		int success=0;
		int failed = 0;
		for(int i=0;i<NFOLD;i++){
			System.out.println("FOLD ke-" + i);
			int count = 0;
			ArrayList<Car> listTest = new ArrayList<Car>();
			ArrayList<Car> listTraining = new ArrayList<Car>();
			int start = 0;
			for(int k=0;k<i;k++){
				if(k<sisa){
					start +=dataPerFoldLebih; 
				} else {
					start += dataPerFold;
				}
			}
			System.out.println("start = " + start);
			
			int jumlahData = 0;
			if(i<sisa){
				jumlahData = dataPerFoldLebih;
			} else {
				jumlahData = dataPerFold;
			}
			
			for(int j=0;j<dataSize;j++){
				if(j>=start && j<start+jumlahData){
				//if(((j >= i*dataPerFold) && (j < (i+1)*dataPerFold)) || ((i==NFOLD-1) && (j >= NFOLD * dataPerFold))){
				//if((j >= i*dataPerFold) && (j < (i+1)*dataPerFold)){
					//Masukkan ke data test
					listTest.add(listCar.get(j));
				} else {
					//Masukkan ke data training
					listTraining.add(listCar.get(j));
				}
				
			}
			System.out.println(listTraining.size());
			NaiveBayes nb = new NaiveBayes(listTraining);
			nb.process();
			
			for(int k=0;k<listTest.size();k++){
				
				
				Car instance = listTest.get(k);
				//instance.printCar();
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
				//System.out.println();
			}
			listTest.clear();	
			listTraining.clear();
		}
		System.out.println("=============Summary==========");
		double totalInstances = success + failed;
		System.out.println("Success : " + success + "("+success /totalInstances * 100 +"%)");
		System.out.println("Failed : " + failed + "("+failed / totalInstances * 100 +"%)");
	}
}
