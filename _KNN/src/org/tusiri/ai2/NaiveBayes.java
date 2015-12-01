package org.tusiri.ai2;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class NaiveBayes {

	public static class AtrKelasCountProb{
		public String atribut;
		public String kelas;
		public int count;
		public double probability;
	}

	public static class KelasCountProb{
		public String kelas;
		public int count;
		public double probability;
	}
	
	public static class KelasMean{
		public String kelas;
		public double mean;
	}
	
	public static class KelasStdDev{
		public String kelas;
		public double stdDev;
	}
	
	private int nAtribut;
	private ArrayList<String> listAttribute = new ArrayList<String>();
	private ArrayList<Boolean> listAttributeNumeric = new ArrayList<Boolean>();
	private ArrayList<ArrayList<KelasMean>> listMean = new ArrayList<ArrayList<KelasMean>>();//MENYIMPAN MEAN
	private ArrayList<ArrayList<KelasStdDev>> listStdDev = new ArrayList<ArrayList<KelasStdDev>>();//MENYIMPAN STD DEV
	
	private ArrayList<Datum> data;
	private ArrayList<String> listKelas;
	
	private int success;
	private int failed; 
	
	private ArrayList<AtrKelasCountProb> listAtrKelasCountProb;
	private ArrayList<KelasCountProb> listKelasCountProb;	
	private ArrayList<ArrayList<AtrKelasCountProb>> listAtrKelasCountProbMatrix = new ArrayList<ArrayList<AtrKelasCountProb>>();

	
	static void shuffleArray(ArrayList<Datum> al){
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = al.size() - 1; i > 0; i--){
	    	int index = rnd.nextInt(i + 1);
	    	//Simple swap
	    	Datum instance = al.get(index);
	    	al.set(index,al.get(i));
	    	al.set(i,instance);
	    }
	}
	
	private void countMean(){
		for(int i=0; i<nAtribut; i++){
			if(listAttributeNumeric.get(i)){
				ArrayList<KelasMean> listKelasMean = new ArrayList<KelasMean>();
				for(int j=0; j<listKelas.size(); j++){
					
					double sum=0;
					double count=0;
					for(int k=0; k<data.size(); k++){
						if((data.get(k).getKelas().equals(listKelas.get(j)))){
							sum += Double.parseDouble(data.get(k).getAtr(i));
							count += 1;
						}
					}
					
					sum /= count;
					//System.out.println("sum = " + sum);
					KelasMean km = new KelasMean();
					km.kelas = listKelas.get(j);
					km.mean = sum;
					listKelasMean.add(km);
				}
				listMean.add(listKelasMean);
			} else {
				listMean.add(null);
			}
		}
	}
	
	private double getMean(int atr, String kelas){
		
		ArrayList<KelasMean> listKelasMean = listMean.get(atr);
		boolean found = false;
		int i = 0;
		double mean = 0;
		while(!found){
			if(listKelasMean.get(i).kelas.equals(kelas)){
				found = true;
				mean = listKelasMean.get(i).mean;
				break;
			}
			i++;
		}
		return mean;
	}
	
	private void countStdDev(){
		for(int i=0; i<nAtribut; i++){
			if(listAttributeNumeric.get(i)){
				ArrayList<KelasStdDev> listKelasStdDev = new ArrayList<KelasStdDev>();
				for(int j=0; j<listKelas.size(); j++){
					double sum=0;
					double count=0;
					double mean = getMean(i,listKelas.get(j));
					for(int k=0; k<data.size(); k++){
						if((data.get(k).getKelas().equals(listKelas.get(j)))){
							sum += Math.pow(Double.parseDouble(data.get(k).getAtr(i))-mean, 2);
							count += 1;
						}
					}
					double x = count-1;
					sum /= x;
					KelasStdDev ks = new KelasStdDev();
					ks.kelas = listKelas.get(j);
					ks.stdDev = sum;
					System.out.println("std dev " + ks.stdDev);
					listKelasStdDev.add(ks);
				}
				listStdDev.add(listKelasStdDev);
			} else {
				listStdDev.add(null);
			}
			
		}
	}
	
	private double getStdDev(int atr, String kelas){
		ArrayList<KelasStdDev> listKelasStdDev = listStdDev.get(atr);
		boolean found = false;
		int i = 0;
		double stdDev = 0;
		while(!found){
			if(listKelasStdDev.get(i).kelas.equals(kelas)){
				found = true;
				stdDev = listKelasStdDev.get(i).stdDev;
				break;
			}
			i++;
		}
		return stdDev;
	}
	
	public int getSuccess(){return success;}
	public int getFailed(){return failed;}
		
	public NaiveBayes(ArrayList<Datum> data, int nAtribut,ArrayList<String> listAttribute,ArrayList<Boolean> listAttributeNumeric) throws FileNotFoundException{
		this.data = data;
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("output.txt", true)));
			out.println("aaaa");
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		this.nAtribut = nAtribut;
		this.listAttribute = listAttribute;
		this.listAttributeNumeric = listAttributeNumeric;
	}
	
	public ArrayList<String> getListKelas(){
		//Mengembalikan list yang berisi daftar kelas yang mungkin untuk sebuah dataset
		ArrayList<String> listKelas = new ArrayList<String>();
		for(int i=0;i<data.size();i++){
			if(!(listKelas.contains(data.get(i).getKelas()))){
				listKelas.add(data.get(i).getKelas());
			}
		}
		this.listKelas = listKelas;
		return listKelas;
	}
	
	
	public boolean isValueFoundProb(ArrayList<AtrKelasCountProb> l, String s){
		boolean found = false;
			for(int i=0;i<l.size();i++){
				if(l.get(i).atribut.equals(s)){
					found = true;
					break;
				}
			}
		return found;
	}	
	
	public void addCountProb(ArrayList<AtrKelasCountProb> l,String atribut,String kelas){
		//Asumsi, elemen l dengan atribut = atribut dan kelas = kelas pasti ada
		int i=0;
		boolean notFound = true;
		while(i<l.size() && notFound){
			if(l.get(i).atribut.equals(atribut) && l.get(i).kelas.equals(kelas)){
				++l.get(i).count;
				notFound = false;
			}
			i++;
		}
	}	
	
	public void constructProbabilityMatrix(){
		int i;
		for(i=0;i<nAtribut;i++){
			int x=0;
			listAtrKelasCountProb = new ArrayList<AtrKelasCountProb>();
			for (int j=0;j<data.size();j++){
				//Jika value atribut belum pernah ditemukan sebelumnya,
				//Tambahkan ke l, Object AtrKelasCount sebanyak jumlah kelas, dengan nilai count 0
				if(!(isValueFoundProb(listAtrKelasCountProb,data.get(j).getAtr(i)))){
					for (int k=0;k<listKelas.size();k++){
						AtrKelasCountProb akc = new AtrKelasCountProb();
						akc.atribut = data.get(j).getAtr(i);
						akc.kelas = listKelas.get(k);
						akc.count = 0;
						akc.probability = 0.0;
						listAtrKelasCountProb.add(akc);
					}
				}
				//Tambahkan data saat ini
				addCountProb(listAtrKelasCountProb,data.get(j).getAtr(i),data.get(j).getKelas());
			}
			int total=0;
			
			/* JANGAN DIHAPUS
			System.out.println();System.out.println();
			System.out.println("\n\n" + listAttribute.get(i));
			System.out.print("Atribut		");
			for(int l=0;l<listKelas.size();l++){
				System.out.print(listKelas.get(l) + "		");
			}
			*/
			
			
			//System.out.println("\n" + attribute.get(i) + " Class" + " Count");
			for(int m=0;m<listAtrKelasCountProb.size();m++){
				if(m % listKelas.size() == 0){
					/* JANGAN DIHAPUS
					System.out.println();
					System.out.print(listAtrKelasCountProb.get(m).atribut+"		");
					*/
				}
				//System.out.print(listAtrKelasCountProb.get(m).kelas+" ");
				System.out.print(listAtrKelasCountProb.get(m).count+"		");
				double penyebut = 0;
				for (int z =0;z< listAtrKelasCountProb.size();z++){
					if (listAtrKelasCountProb.get(z).kelas.equals(listAtrKelasCountProb.get(m).kelas)){
						penyebut += listAtrKelasCountProb.get(z).count;
					}
				}
				if (penyebut != 0.0){
					//Ubah di sini, kalo atribut numerik
					listAtrKelasCountProb.get(m).probability = listAtrKelasCountProb.get(m).count / penyebut;
				}
				else{
					//System.out.println(penyebut);
				}
				total += listAtrKelasCountProb.get(m).count;
			}
			listAtrKelasCountProbMatrix.add(listAtrKelasCountProb);
		}
		
	}
	
	public void countClassProbAppearance(){
		listKelasCountProb = new ArrayList<KelasCountProb>();

		for (int k=0;k<listKelas.size();k++){
			KelasCountProb kcp = new KelasCountProb();
			kcp.kelas = listKelas.get(k);
			kcp.count = 0;
			kcp.probability = 0.0;
			listKelasCountProb.add(kcp);
		}
		
		for (int j= 0; j < data.size(); j++){
			for (int k =0; k < listKelasCountProb.size(); k++){
				if (data.get(j).getKelas().equals(listKelasCountProb.get(k).kelas)){
					listKelasCountProb.get(k).count++;
				}
			}
		}
		
		double penyebut = 0.0;
		for (int z = 0; z < listKelasCountProb.size(); z++){
			penyebut += listKelasCountProb.get(z).count;
		}
		
		for (int m=0;m<listKelasCountProb.size();m++){
			//System.out.print("Kelas : " + listKelasCountProb.get(m).kelas + ", ");
			//System.out.print("Count : " + listKelasCountProb.get(m).count + ", ");
			listKelasCountProb.get(m).probability = listKelasCountProb.get(m).count / penyebut;
			//System.out.println("Probability : " + listKelasCountProb.get(m).probability );
		}
		
	}
		
	public String NaiveBayesAnalysis(Datum instance){
		ArrayList<String> listKelas = getListKelas();
		ArrayList<Double> values = new ArrayList<Double>();
		
		for (int i = 0; i < listKelas.size(); i++){
				Double value = 0.0;
				int j = 0;
				boolean found = false;
				while (j < listKelas.size() && !found){
					if (listKelas.get(i).equals(listKelasCountProb.get(j).kelas)){
						found = true;
						value += listKelasCountProb.get(j).probability;
						//System.out.println(value);
						for (int k = 0 ; k< listAtrKelasCountProbMatrix.size(); k++){//jumlah atribut
							for (int z = 0; z < listAtrKelasCountProbMatrix.get(k).size();z++){
								/*System.out.print(listAtrKelasCountProbMatrix.get(k).get(z).kelas + " ");
								System.out.println(listAtrKelasCountProbMatrix.get(k).get(z).atribut);*/
													
								if (listKelas.get(i).equals(listAtrKelasCountProbMatrix.get(k).get(z).kelas)
										&& 
									instance.getListAtr().get(k).equals(listAtrKelasCountProbMatrix.get(k).get(z).atribut) ){
									//if(!listAttributeNumeric.get(k))
										value *= listAtrKelasCountProbMatrix.get(k).get(z).probability;
									//else{
										//double pangkat = - (Math.pow((Double.parseDouble(instance.getListAtr().get(k)) - getStdDev(k,listKelas.get(i))),2) /  (2 * Math.pow(getStdDev(k,listKelas.get(i)),2)));
										//System.out.println("pangkat = " + pangkat);
										//value *= (1/((Math.sqrt(2*Math.PI))*getStdDev(k,listKelas.get(i)))) * (Math.pow(2.718,pangkat));
									//}
								}
							}
						}
					}
					j++;
					
				}
				values.add(value);
		}
		
		Double maxValue = Collections.max(values);
		int maxValueIndex = 0;
		int y = 0;
		boolean found = false;
		while (y < values.size() && !found){
			if (values.get(y) == maxValue){
				maxValueIndex = y;
				found = true;
			}else{
				y++;	
			}
		}

		return listKelas.get(y);
		
	}
	
	public void process(boolean needCheck){
		ArrayList<String> listKelas = getListKelas();
		System.out.println(listKelas);
		constructProbabilityMatrix();
		countClassProbAppearance();
		countMean();
		countStdDev();
		if(needCheck){
			for(int k=0;k<data.size();k++){
				Datum instance = data.get(k);
				String result = getClassResult(instance);
				if (result.equals(instance.getKelas())){success++;}
				else{failed++;}
			}
			printResult();
		}
	}
	
	public void process10Fold() throws FileNotFoundException{
		final int NFOLD = 10;
		shuffleArray(data);
		int dataSize = data.size();
		int dataPerFold = dataSize/NFOLD;
		int sisa = dataSize % NFOLD;
		System.out.println(sisa);
		int dataPerFoldLebih = dataPerFold + 1;//Jika bukan kelipatan 10
		for(int i=0;i<NFOLD;i++){
			System.out.println("FOLD ke-" + i);
			int count = 0;
			ArrayList<Datum> listTest = new ArrayList<Datum>();
			ArrayList<Datum> listTraining = new ArrayList<Datum>();
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
					//Masukkan ke data test
					listTest.add(data.get(j));
				} else {
					//Masukkan ke data training
					listTraining.add(data.get(j));
				}
				
			}
			System.out.println(listTraining.size());
			NaiveBayes nb = new NaiveBayes(listTraining,nAtribut,listAttribute,listAttributeNumeric);
			nb.process(false);
			
			for(int k=0;k<listTest.size();k++){
				Datum instance = listTest.get(k);
				String result = nb.getClassResult(instance);
				if (result.equals(instance.getKelas())){success++;}
				else{failed++;}
			}
			listTest.clear();	
			listTraining.clear();
		}
		printResult();
	}
	
	public void printResult(){
		System.out.println("\n=============Summary==========");
		System.out.println("success = " + success);
		System.out.println("failed = " + failed);
		System.out.println("totalInstances = " + data.size());
	}
	
	public String getClassResult(Datum instance){
		String classResult = NaiveBayesAnalysis(instance);
		return classResult;
	}
}
