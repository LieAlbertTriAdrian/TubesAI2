package org.tusiri.ai2;

import java.util.ArrayList;

public class NaiveBayes {
	
	public static class AtrKelasCount{
		public String atribut;
		public String kelas;
		public int count;
	}
	
	private ArrayList<Instance> data;
	private ArrayList<String> listKelas;
	
	public NaiveBayes(ArrayList<Instance> data){
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
	
	public void countAppearance(){
		int nAtribut = Main.NATRIBUT;
		for(int i=0;i<nAtribut;i++){
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
			for(int m=0;m<l.size();m++){
				System.out.print(l.get(m).atribut+" ");
				System.out.print(l.get(m).kelas+" ");
				System.out.println(l.get(m).count);
				total += l.get(m).count;
			}
			System.out.println(total);
		}
	}
	
	public void process(){
		ArrayList<String> listKelas = getListKelas();
		System.out.println(listKelas);
		countAppearance();
	}
}
