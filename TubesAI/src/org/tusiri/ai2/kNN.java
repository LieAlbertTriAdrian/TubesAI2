package org.tusiri.ai2;

import java.util.ArrayList;
import java.util.Collections;

public class kNN {

	private int k;
	private ArrayList<Instance> InstanceList;
	
	
	public kNN(){
		InstanceList = new ArrayList<Instance>();
	}
	
	public kNN(ArrayList<Instance> CList, int _k){
		k = _k;
		InstanceList = new ArrayList<Instance>();
		for(Instance object: CList){
			InstanceList.add(object);
		};
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
					jarak++;
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
				double Jarak = HitungJarak(A, e);
				String Label = e.getKelas();
				distance dst = new distance(ID,Jarak,Label);
				System.out.println(.getJarak());
				arrayJarak.add(dst);
			}
		}	
		for (distance e:arrayJarak){
			System.out.println(e.getJarak());
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
		}
	    if(iMin != j) {
	    	distance tempDistance = new distance();
	    	tempDistance = arrayJarak.get(j);
	    	arrayJarak.remove(j);
	    	arrayJarak.add(j,arrayJarak.get(iMin));
	    	arrayJarak.remove(iMin);
	    	arrayJarak.add(iMin,tempDistance);
	    }
		
		
	}
	
	public void FullSet(){
		ArrayList<distance> arrayJarak = new ArrayList<distance>();
		arrayJarak = HitungJarakFull(InstanceList.get(1));
		for (distance e: arrayJarak){
			System.out.println(e.getID() + " Jarak :"+e.getJarak());
		}

		//SORT arrayJarak berdasar jarak, asscending
		sortArrayJarak(arrayJarak);
		for(distance e:arrayJarak){
			System.out.println(e.getJarak());			
		}

		
	}
	
	public void nFold(int fold){
		
	}
	
}
