package org.tusiri.ai2;

import java.util.ArrayList;

public class Car {
	private String kelas;
	private ArrayList<String> atr;
	
	public Car(){
		atr = new ArrayList<String>();
	}
	
	public Car(Car car){
		kelas = car.kelas;
		atr = new ArrayList<String>();
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