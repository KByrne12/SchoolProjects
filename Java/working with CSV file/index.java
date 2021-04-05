package base;

import java.io.*;
import java.util.Scanner;

public class index {

	public static void main(String[] args) {
		
		File file = new File("C:\\Users\\ksaso\\Desktop\\Important Info\\School Files\\SCSU\\CSCI331\\Copy of us_postal_codes.csv");
		int[] indexPrimary = null;
		
		
		Scanner sc = null;
		try {
			sc = new Scanner(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		generateIndexPrimary(sc,indexPrimary);

	}
	
	public static void generateIndexPrimary(Scanner sc, int[] indexPrimary)
	{
		int i = 0;
		while(sc.hasNext())
		{
			indexPrimary[h] = sc.nextInt();
			h++;
			sc.nextLine();
		}
		for (int j = 0; j < 10; j++)
		{
			System.out.println(indexPrimary[j]);
		}

		return;
	}

}
