package org.tusiri.ai2;

import java.awt.GraphicsEnvironment;

public class test {
	public static void main(String[] args)
	{
		String fonts[]
		        = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

		for (int i = 0; i < fonts.length; i++) {
		    System.out.println(fonts[i]);
		}
	}
}
