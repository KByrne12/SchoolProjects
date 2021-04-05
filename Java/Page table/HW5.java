package pack;

import java.io.*;
import java.util.Random;

public class HW5 {
	public static int[] pages = new int[10000];

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Random rand = new Random();
		
		int LRUFaults;
		int FIFOFaults;
		int OptimalFaults;
		
		for (int i = 0; i < pages.length; i++)
		{
			pages[i] = rand.nextInt(10);
		}

		LRU least = new LRU(pages);
		FIFO first = new FIFO(pages);
		Optimal opt = new Optimal(pages);
		for (int i = 1; i <9; i++)
		{
		LRUFaults = least.CalcFaults(i);
		FIFOFaults = first.CalcFaults(i);
		OptimalFaults = opt.CalcFaults(i);
		
		System.out.println("Least Recently Used: " + LRUFaults);
		System.out.println("First In First Out : " + FIFOFaults);
		System.out.println("Optimal            : " + OptimalFaults);
		System.out.println("_________");
		}
		
	}

}
