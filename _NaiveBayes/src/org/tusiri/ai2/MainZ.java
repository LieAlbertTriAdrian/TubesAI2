package org.tusiri.ai2;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class MainZ {
	public static void main (String args[]) throws IOException{
		try {
		    PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("myfile.txt", true)));
		    out.println("the text");
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
	}
}
