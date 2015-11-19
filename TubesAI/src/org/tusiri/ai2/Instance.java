package org.tusiri.ai2;

import java.util.ArrayList;

public class Instance {
	private String kelas;
	private int ID;
	private ArrayList<String> atr;
	
	public Instance(int i){
		atr = new ArrayList<String>();
		ID = i;
	}
	
	public Instance(Instance car, int i){
		kelas = car.kelas;
		atr = new ArrayList<String>();
		ID = i;
	}
	
	public void addAtr(String value){
		atr.add(value);
	}
	
	public ArrayList<String> getListAtr(){
		return atr;
	}
	
	public String getAtr(int index){
		return atr.get(index);
	}
	
	public String getKelas(){return kelas;}
	public void setKelas(String kelas){this.kelas = kelas;}
}
