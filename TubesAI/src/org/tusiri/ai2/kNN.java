package org.tusiri.ai2;

import java.util.ArrayList;

public class kNN {

	private int k;
	private ArrayList<Instance> InstanceList;
	
	
	public kNN(){
		InstanceList = new ArrayList<Instance>();
	}
	
	public kNN(ArrayList<Instance> CList){
		InstanceList = new ArrayList<Instance>();
		for(Instance object: CList){
			InstanceList.add(object);
		};
		System.out.println(InstanceList.get(0).getKelas());
	}
	
	public ArrayList<Instance> getInstanceList(){
		return InstanceList;
	};
	
	public int HitungJarak(Instance A, Instance B){
		int numAttributes = A.getListAtr().size();
	
		int jarak = 0;
		for(int i = 0; i<numAttributes; i++){
			if(!A.getAtr(i).equals(B.getAtr(i))){
				jarak++;
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
				int Jarak = HitungJarak(A, e);
				String Label = e.getKelas();
				distance dst = new distance(ID,Jarak,Label);
				arrayJarak.add(dst);
			}
		}	
		return arrayJarak;	
	}
	
	
	
	public void FullSet(){
		ArrayList<distance> arrayJarak = new ArrayList<distance>();
		arrayJarak = HitungJarakFull(InstanceList.get(0));
		for (distance e: arrayJarak){
			//System.out.println(e.getID() + " Jarak :"+e.getJarak());
		}
	}
	
	public void nFold(int fold){
		
	}
	
}
