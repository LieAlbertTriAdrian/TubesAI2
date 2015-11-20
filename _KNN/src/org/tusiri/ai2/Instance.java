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
	
	public Instance(String car, int i){
		kelas = car;
		atr = new ArrayList<String>();
		ID = i;
	}
	
	public void addAtr(String value){
		atr.add(value);
	}
	
	public String getAtr(int i){
		return atr.get(i);
	}
	
	public ArrayList<String> getListAtr(){
		return atr;
	}
	
	public int getId(){
		return ID;
	}
	
	public ArrayList<String> getAllAtributes(){
		return atr;
	}
	
	public String getKelas(){return kelas;}
	public void setKelas(String kelas){this.kelas = kelas;}
}
