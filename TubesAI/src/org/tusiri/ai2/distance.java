package org.tusiri.ai2;

public class distance {
	int ID;
	Double jarak;
	String label;
	
	public distance(){
		
	}
	
	public distance(int _Id, Double _jarak, String _label){
		ID = _Id;
		jarak = _jarak;
		label = _label;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Double getJarak() {
		return jarak;
	}

	public void setJarak(Double jarak) {
		this.jarak = jarak;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}
	


	
	
		
	
	
}
