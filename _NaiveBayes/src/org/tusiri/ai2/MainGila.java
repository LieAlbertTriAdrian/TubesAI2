package org.tusiri.ai2;

import java.io.File;
import java.io.IOException;

import weka.core.Instance;
import weka.core.Instances;

import java.lang.Iterable;

import weka.core.converters.ArffLoader;

public class MainGila {

	public static void main (String args[]) throws IOException{
		//String file = "C:\\TubesAI2\\dataset\\CarEvaluation\\car.arff";
		String file = "F:\\KULIAH\\SEMESTER 5\\IF3170 Inteligensi Buatan\\TubesAI2\\dataset\\CarEvaluation\\car.arff";
		ArffLoader arffloader=new ArffLoader();
		
		File filedata = new File(file);
		arffloader.setFile(filedata);

		     Instances data = arffloader.getDataSet();
		     for(int i = 0; i < data.numInstances();i++){
		    	 Instance instance = data.instance(i);
		         System.out.println("Instance:" + instance.stringValue(0));
		     }
	}
}
