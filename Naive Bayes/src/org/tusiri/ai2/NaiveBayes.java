package org.tusiri.ai2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
		
	private ArrayList<Car> data;
	private ArrayList<String> listKelas;
	private ArrayList<AtrKelasCountProb> listAtrKelasCountProb;
	private ArrayList<KelasCountProb> listKelasCountProb;	
	private ArrayList<ArrayList<AtrKelasCountProb>> listAtrKelasCountProbMatrix = new ArrayList<ArrayList<AtrKelasCountProb>>();
	
	public NaiveBayes(ArrayList<Car> data){
		this.data = data;
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
		int nAtribut = Main.NATRIBUT;
		//System.out.println();
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
			
			System.out.println("\n" + Main.listAttribute.get(i));
			//System.out.println("\n" + attribute.get(i) + " Class" + " Count");
			for(int m=0;m<listAtrKelasCountProb.size();m++){
				System.out.print(listAtrKelasCountProb.get(m).atribut+" ");
				System.out.print(listAtrKelasCountProb.get(m).kelas+" ");
				System.out.print(listAtrKelasCountProb.get(m).count+" ");
				double penyebut = 0;
				for (int z =0;z< listAtrKelasCountProb.size();z++){
					if (listAtrKelasCountProb.get(z).kelas.equals(listAtrKelasCountProb.get(m).kelas)){
						penyebut += listAtrKelasCountProb.get(z).count;
					}
				}
				if (penyebut != 0.0){
					listAtrKelasCountProb.get(m).probability = listAtrKelasCountProb.get(m).count / penyebut;
					System.out.println();	
				}
				else{
					//System.out.println(penyebut);
				}
				total += listAtrKelasCountProb.get(m).count;
			}
			listAtrKelasCountProbMatrix.add(listAtrKelasCountProb);
			//System.out.println(total);
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
		
	public String NaiveBayesAnalysis(Car instance){
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
						for (int k = 0 ; k< listAtrKelasCountProbMatrix.size(); k++){	
							for (int z = 0; z < listAtrKelasCountProbMatrix.get(k).size();z++){
								/*System.out.print(listAtrKelasCountProbMatrix.get(k).get(z).kelas + " ");
								System.out.println(listAtrKelasCountProbMatrix.get(k).get(z).atribut);*/
													
								if (listKelas.get(i).equals(listAtrKelasCountProbMatrix.get(k).get(z).kelas)
										&& 
									instance.getListAtr().get(k).equals(listAtrKelasCountProbMatrix.get(k).get(z).atribut) ){
										
										/*System.out.println("Kelas :" + listAtrKelasCountProbMatrix.get(k).get(z).kelas);
										System.out.println("Atribute :" + listAtrKelasCountProbMatrix.get(k).get(z).atribut);
										System.out.println("Nilai :" + listAtrKelasCountProbMatrix.get(k).get(z).probability);*/
										value *= listAtrKelasCountProbMatrix.get(k).get(z).probability;
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
	
	public void process(){
		ArrayList<String> listKelas = getListKelas();
		System.out.println(listKelas);
		//countAppearance();
		constructProbabilityMatrix();
		countClassProbAppearance();
	}
	
	public String getClassResult(Car instance){
		String classResult = NaiveBayesAnalysis(instance);
		return classResult;
	}
}
