package org.tusiri.ai2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;

public class Analysis {
	private String type;
	private String training;
	private File file;
	private int NATRIBUT = 0;
	private String HEADER = "";
	private ArrayList<String> listAttribute = new ArrayList<String>();
	private ArrayList<Boolean> listAttributeNumeric = new ArrayList<Boolean>();
	private double totalInstances;
	private int success;
	private int failed;
	
	public double getTotalInstances(){return totalInstances;}
	public int getSuccess(){return success;}
	public int getFailed(){return failed;}
	
	public Analysis(String type, String training, File file){
		this.type = type;
		this.training = training;
		this.file = file;
	}
	
	static void shuffleArray(ArrayList<Datum> al){
	    // If running on Java 6 or older, use `new Random()` on RHS here
	    Random rnd = ThreadLocalRandom.current();
	    for (int i = al.size() - 1; i > 0; i--){
	    	int index = rnd.nextInt(i + 1);
	    	//Simple swap
	    	Datum instance = al.get(index);
	    	al.set(index,al.get(i));
	    	al.set(i,instance);
	    }
	}
	
	public void analyze(){
		
		ArffLoader arffloader=new ArffLoader();
  		
		try {
			arffloader.setFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Instances data;
		try {
			data = arffloader.getDataSet();
			NATRIBUT = data.numAttributes()-1;
			System.out.println("NATRIBUT = " + data.numAttributes());
			for (int k = 0; k< NATRIBUT; k++){
				String dataAttribute = data.attribute(k).toString();
				Boolean isNumeric = data.attribute(k).isNumeric();
				String[] s = dataAttribute.split(" ");
				listAttribute.add(s[1]);
				System.out.println(dataAttribute);
				listAttributeNumeric.add(isNumeric);
				HEADER += (s[1] + " ");
			}

			//Initiate list of Datum
			ArrayList<Datum> listDatum = new ArrayList<Datum>();
			for(int i = 0; i < data.numInstances();i++){
				Instance instance = data.instance(i);
		    
				Datum datum = new Datum(i);
				for(int j=0;j<NATRIBUT;j++){
					if(listAttributeNumeric.get(j)){
						String[] s = instance.toString().split(",");
						datum.addAtr(s[j]);
						//System.out.println("jancok = " + instance.toString());
					} else {
						datum.addAtr(instance.stringValue(j));
					}
				}
				datum.setKelas(instance.stringValue(NATRIBUT));
				listDatum.add(datum);
			}
		
		
			//Naive Bayes Analysis
		
		if(type.equals("NaiveBayes")){
			NaiveBayes nb = new NaiveBayes(listDatum,NATRIBUT,listAttribute,listAttributeNumeric);
			if(training.equals("FullTraining")){
				nb.process(true);
			} else {//10 Fold
				nb.process10Fold();
			}
			
		} else {//knn
			kNN knn = new kNN(listDatum,3);
			if(training.equals("FullTraining")){
				knn.fullSet();
			} else {//10 Fold
				knn.nFold(10);
			}
		}
		
		
		
		
		double totalInstances = success + failed;
		this.totalInstances = totalInstances;
		this.success = success;
		this.failed = failed;
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
