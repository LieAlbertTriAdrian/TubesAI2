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
	
	public ArrayList<Integer> HitungJarakFull(Instance A){
		
		return null;
	}
	
	public void FullSet(){
		
	}
	
	public void nFold(int fold){
		
	}
	
}
