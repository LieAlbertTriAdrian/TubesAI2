import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;



public class ReadCSV {

  public static void main(String[] args) {

	ReadCSV obj = new ReadCSV();
	obj.run();

  } 

  public void run() {
	String csvFile = "src/zoo.data";
	BufferedReader br = null;
	String line = "";
	String cvsSplitBy = ",";
	ArrayList<ArrayList<String>> animalList = new ArrayList<ArrayList<String>>();
	
	
	try {

		br = new BufferedReader(new FileReader(csvFile));
		while ((line = br.readLine()) != null) {

			
			
		        // use comma as separator
			String[] instance = line.split(cvsSplitBy);
			ArrayList<String> animals = new ArrayList<String>(Arrays.asList(instance));
			animalList.add(animals);	
		}
		

	} catch (FileNotFoundException e) {
		e.printStackTrace();
	} catch (IOException e) {
		e.printStackTrace();
	} finally {
		if (br != null) {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	System.out.println("Done");
  }

}