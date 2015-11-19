package org.tusiri.ai2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NaiveBayes {
	
	public static class AtrKelasCount{
		public String atribut;
		public String kelas;
		public int count;
	}
	
	public static class KelasCount{
		public String kelas;
		public int count;
	}
	
	
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
		
	
	List<String> attribute = new ArrayList<String>(Arrays.asList("buying", "maint","doors","persons","lug_boot","safety"));
	private ArrayList<Car> data;
	private ArrayList<String> listKelas;

	public NaiveBayes(ArrayList<Car> data){
		/*ArrayList<Car> listCar = new ArrayList<Car>(data.size());
		for (Car car : data){
			listCar.add(new Car(car));
		}
		this.data = listCar;*/
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
	
	public boolean isValueFound(ArrayList<AtrKelasCount> l, String s){
		boolean found = false;
			for(int i=0;i<l.size();i++){
				if(l.get(i).atribut.equals(s)){
					found = true;
					break;
				}
			}
		return found;
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
	
	public void addCount(ArrayList<AtrKelasCount> l,String atribut,String kelas){
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
	
	public void countAppearance(){
		int nAtribut = Main.NATRIBUT;
		int i;
		for(i=0;i<nAtribut;i++){
			int x=0;
			ArrayList<AtrKelasCount> l = new ArrayList<AtrKelasCount>();
			for (int j=0;j<data.size();j++){
				//Jika value atribut belum pernah ditemukan sebelumnya,
				//Tambahkan ke l, Object AtrKelasCount sebanyak jumlah kelas, dengan nilai count 0
				if(!(isValueFound(l,data.get(j).getAtr(i)))){
					for (int k=0;k<listKelas.size();k++){
						AtrKelasCount akc = new AtrKelasCount();
						akc.atribut = data.get(j).getAtr(i);
						akc.kelas = listKelas.get(k);
						akc.count = 0;
						l.add(akc);
					}
				}
				//Tambahkan data saat ini
				addCount(l,data.get(j).getAtr(i),data.get(j).getKelas());
			}
			int total=0;
			
			System.out.println("\n" + attribute.get(i) + " Class" + " Count");
			for(int m=0;m<l.size();m++){
				System.out.print(l.get(m).atribut+" ");
				System.out.print(l.get(m).kelas+" ");
				System.out.println(l.get(m).count);
				total += l.get(m).count;
			}
			System.out.println(total);
		}
	
	}

	public void constructProbabilityMatrix(){
		int nAtribut = Main.NATRIBUT;
		System.out.println();
		int i;
		for(i=0;i<nAtribut;i++){
			int x=0;
			ArrayList<AtrKelasCountProb> l = new ArrayList<AtrKelasCountProb>();
			for (int j=0;j<data.size();j++){
				//Jika value atribut belum pernah ditemukan sebelumnya,
				//Tambahkan ke l, Object AtrKelasCount sebanyak jumlah kelas, dengan nilai count 0
				if(!(isValueFoundProb(l,data.get(j).getAtr(i)))){
					for (int k=0;k<listKelas.size();k++){
						AtrKelasCountProb akc = new AtrKelasCountProb();
						akc.atribut = data.get(j).getAtr(i);
						akc.kelas = listKelas.get(k);
						akc.count = 0;
						akc.probability = 0.0;
						l.add(akc);
					}
				}
				//Tambahkan data saat ini
				addCountProb(l,data.get(j).getAtr(i),data.get(j).getKelas());
			}
			int total=0;
			
			System.out.println("\n" + attribute.get(i) + " Class" + " Count");
			for(int m=0;m<l.size();m++){
				System.out.print(l.get(m).atribut+" ");
				System.out.print(l.get(m).kelas+" ");
				System.out.print(l.get(m).count+" ");
				double penyebut = 0;
				for (int z =0;z< l.size();z++){
					if (l.get(z).kelas.equals(l.get(m).kelas)){
						penyebut += l.get(z).count;
					}
				}
				if (penyebut != 0.0){
					l.get(m).probability = l.get(m).count / penyebut;
					System.out.println(l.get(m).probability);	
				}
				else{
					System.out.println(penyebut);
				}
				total += l.get(m).count;
			}
			System.out.println(total);
		}
	}
	
	
	public void countClassAppearance(){
		ArrayList<KelasCount> l = new ArrayList<KelasCount>();

		for (int k=0;k<listKelas.size();k++){
			KelasCount kc = new KelasCount();
			kc.kelas = listKelas.get(k);
			kc.count = 0;
			l.add(kc);
		}
		
		for (int j= 0; j < data.size(); j++){
			for (int k =0; k < l.size(); k++){
				if (data.get(j).getKelas().equals(l.get(k).kelas)){
					l.get(k).count++;
				}
			}
		}
		
		for (int m=0;m<l.size();m++){
			System.out.print("Kelas : " + l.get(m).kelas + ", ");
			System.out.println("Count : " + l.get(m).count);
		}
			
	}	
	
	public void countClassProbAppearance(){
		ArrayList<KelasCountProb> l = new ArrayList<KelasCountProb>();

		for (int k=0;k<listKelas.size();k++){
			KelasCountProb kcp = new KelasCountProb();
			kcp.kelas = listKelas.get(k);
			kcp.count = 0;
			kcp.probability = 0.0;
			l.add(kcp);
		}
		
		for (int j= 0; j < data.size(); j++){
			for (int k =0; k < l.size(); k++){
				if (data.get(j).getKelas().equals(l.get(k).kelas)){
					l.get(k).count++;
				}
			}
		}
		
		double penyebut = 0.0;
		for (int z = 0; z < l.size(); z++){
			penyebut += l.get(z).count;
		}
		
		for (int m=0;m<l.size();m++){
			System.out.print("Kelas : " + l.get(m).kelas + ", ");
			System.out.print("Count : " + l.get(m).count + ", ");
			l.get(m).probability = l.get(m).count / penyebut;
			System.out.println("Probability : " + l.get(m).probability );
		}
		
	}
		
	
	
	public void process(){
		ArrayList<String> listKelas = getListKelas();
		System.out.println(listKelas);
		//countAppearance();
		constructProbabilityMatrix();
		countClassProbAppearance();
	}
}
