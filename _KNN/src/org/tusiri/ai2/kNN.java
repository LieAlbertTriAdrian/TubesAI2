package org.tusiri.ai2;

import java.util.ArrayList;
import java.util.Collections;

public class kNN {

	private int k;
	private ArrayList<Instance> InstanceList;
	private ArrayList<Instance> InstanceListNew;
	
	
	public ArrayList<Instance> getInstanceListNew() {
		return InstanceListNew;
	}

	public void setInstanceListNew(ArrayList<Instance> instanceListNew) {
		InstanceListNew = instanceListNew;
	}

	public kNN(){
		InstanceList = new ArrayList<Instance>();
		InstanceListNew= new ArrayList<Instance>();
	}
	
	public kNN(ArrayList<Instance> CList, int _k){
		k = _k;
		InstanceList = new ArrayList<Instance>();
		for(Instance object: CList){
			InstanceList.add(object);
		};
		InstanceListNew= new ArrayList<Instance>();
	}
	
	public ArrayList<Instance> getInstanceList(){
		return InstanceList;
	};
	
	
	
	public void standardizeTrainingSetNumeric() {
		Boolean isNumeric = false; // ASUMSI AWAL
		for (Instance e : getInstanceList()){
			int numAttributes = e.getListAtr().size();
			for(int i = 0; i<numAttributes; i++){
				//Mengidentifikasi apakah nominal atau numeric. Standarkan data jika numeric
				if (!isNumeric){ //nominal data
					//DO Nothing
				} else{ //numeric data, nilai harus distandardkan
					//Later
				}
			}
		}
	}

	
	public double HitungJarak(Instance A, Instance B){
		int numAttributes = A.getListAtr().size();
		Boolean isNumeric = false; //ASUMSI AWAL
		double jarak = 0;
		for(int i = 0; i<numAttributes; i++){
			
			//Mengidentifikasi apakah nominal atau numeric. GUnakan eucledian untuk numeric data.
			if (!isNumeric){ //nominal data
				if(!A.getAtr(i).equals(B.getAtr(i))){
					jarak=jarak+1;
				}
			} else{ //numeric data, gunakan eucledian distance
				double eucledianDist;
				double valueA = Double.parseDouble(A.getAtr(i));
				double valueB = Double.parseDouble(B.getAtr(i));
				double AminusB = valueA-valueB;
				eucledianDist = (Math.pow(AminusB,2));
				jarak = jarak + eucledianDist;
			}
		}
		return jarak;
	}
	
	
	public ArrayList<distance> HitungJarakFull(Instance A){
		//Inisialisasi Array Jarak
		ArrayList<distance> arrayJarak = new ArrayList<distance>();
		
		//Hitung Jarak dari Instance A ke Instance lain
		for (Instance e : getInstanceList()){
			if (A.getId() != e.getId()){
				int ID = e.getId();
				Double Jarak = HitungJarak(A, e);
				String Label = e.getKelas();
				distance dst = new distance(ID,Jarak,Label);
				arrayJarak.add(dst);
				//System.out.println(e.getId() + "    jarak  " +dst.getJarak());
			}
		}
		return arrayJarak;	
	}
	
	
	public void sortArrayJarak(ArrayList<distance> arrayJarak){
		//melakukan sorting arrayJarak berdasarkan nilai distance (ascending)
		int n = arrayJarak.size();
		int i,j;
		int iMin=0;
		for (j = 0; j < n-1; j++) {
			iMin = j;
			 for ( i = j+1; i < n; i++) {
			        /* if this element is less, then it is the new minimum */
			        if (arrayJarak.get(i).getJarak() < arrayJarak.get(iMin).getJarak()) {
			            /* found new minimum; remember its index */
			            iMin = i;
			        }
			 }
			 if(iMin != j) {
			    	distance tempDistance = new distance();
			    	tempDistance = arrayJarak.get(j);
			    	arrayJarak.set(j,arrayJarak.get(iMin));
			    	arrayJarak.set(iMin,tempDistance);
			    }
		}
	}
	
	public void classifyInstance(Instance I,int iAwal, int nElement){
		//Mengubah kelas/label dari Instance menjadi laber Baru hasil KNN
		ArrayList<distance> arrayJarak = new ArrayList<distance>();
		ArrayList<distance> arrayKNearest  = new ArrayList<distance>();
		
		arrayJarak = HitungJarakFull(I);

		for (distance e: arrayJarak){
			//System.out.println(e.getID() + " Jarak :"+e.getJarak());
		}
		
		//remove distance for k-fold
		for(int i = 0; i<nElement-1; i++){
			arrayJarak.remove(iAwal);//seperti queue, indeks akan maju ke depan
		}

		//SORT arrayJarak berdasar jarak, asscending
		sortArrayJarak(arrayJarak);
		for (int i =0; i<k ; i++){
			arrayKNearest.add(arrayJarak.get(i));
		}

		
		//Tampilkan K-nearest label dan ID
		/*for (distance e: arrayKNearest){
			System.out.println("ID: " +e.getID() + "    label   :" +		e.getLabel());
		}*/
		
		//Count number of every class
		ArrayList<voteCandidate> arrayCandidate= new ArrayList<voteCandidate>();
		voteCandidate candidateFirst = new voteCandidate(arrayKNearest.get(0).getLabel(), 1);  // inisialisasi candidate 1
		arrayCandidate.add(candidateFirst);
		
		for (int i =1 ; i<arrayKNearest.size();i++){
			boolean found = false;
			int j= 0;
			while (!found && j<arrayCandidate.size()){
				if(arrayKNearest.get(i).getLabel().equals(arrayCandidate.get(j).getLabel())){
					found = true;
					arrayCandidate.get(j).setVote(arrayCandidate.get(j).getVote()+1);
				}
				else{
					j++;
				}
			}
			if(!found){
				voteCandidate candidateNew = new voteCandidate(arrayKNearest.get(i).getLabel(), 1);
				arrayCandidate.add(candidateNew);
			}
		}
		
		
		//vote for majority
		voteCandidate Max = arrayCandidate.get(0);
		for(int i = 1; i < arrayCandidate.size();i++){
			if(arrayCandidate.get(i).getVote() > Max.getVote()){
				Max = arrayCandidate.get(i);
			}
		}
		//System.out.println("Label sebelumnya "+I.getKelas()+" ID --" +Max.getLabel());
		
		//memasukan Instance dengan label baru ke InstanceListNew
		Instance newInstance = new Instance(Max.getLabel(),I.getId());
		for(String e: I.getListAtr()){
			newInstance.addAtr(e);
		}
		
		InstanceListNew.add(newInstance);
		
	}
	
	public void fullSet(){
		for (Instance e : InstanceList){
			classifyInstance(e,0,-1);
		}
		analisisAkurasi();

	}
	
	public void nFold(int fold){
		int nElement = InstanceList.size() /fold;
		int sisa = InstanceList.size() % fold;
		System.out.println(InstanceList.size());
		System.out.println(nElement);
		System.out.println(InstanceList.size() % fold);
		int awal = 0;
		int akhir;
		if(sisa>0){
			akhir = nElement+1;
			sisa--;
		}
		else{
			akhir = nElement;
		}
		for(int i = 0; i< InstanceList.size();i++){
			classifyInstance(InstanceList.get(i),awal,akhir);
			if((i%akhir)==0){
				System.out.println(awal);
				awal = i+1;
				sisa--;
				if(sisa>0){
					akhir = nElement+1;
				}
				else{
					akhir = nElement;
				}
			}
		}
		analisisAkurasi();
		
	}

	
	public void analisisAkurasi(){
		int jumlahInstance = InstanceList.size();
		Double jumlahSalah = 0.00;
		Double jumlahBenar = 0.00;
		Double akurasi;
		
		
		
		//Penghitungan salah dan benar
		for (int i =0;i<InstanceList.size();i++){
			if(InstanceList.get(i).getKelas().equals(InstanceListNew.get(i).getKelas())){
				jumlahBenar=jumlahBenar+1;
			}
			else{
				jumlahSalah=jumlahSalah+1;
			}
		}
		
		//Tampilan analisis
		/*for (int i =0;i<InstanceList.size();i++){
			System.out.println("Kelas Awal: "+InstanceList.get(i).getKelas() + " menjadi   -->" + InstanceListNew.get(i).getKelas());
		}*/
		
		System.out.println("Jumlah klasifikasi benar: "+ jumlahBenar);
		System.out.println("Jumlah klasifikasi salah: " +jumlahSalah);
		akurasi = jumlahBenar/(double)jumlahInstance*100;
		System.out.println("Persentase eror: "+ akurasi +"%");	
	}
}
