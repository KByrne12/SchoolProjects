package pack;

public class LRU 
{
	int[] pages;
	
	public LRU(int[] array)
	{
		pages = new int[array.length];
		pages = array;
	}
	
	int CalcFaults(int size) 
	{		
		int total = 0;
		int[] current = new int[size];
		int[] age = new int[size];
		boolean check;
		int highest = 0;
		
		System.out.println("size: " + size);
		//start of looped program
		for (int j = 0; j < pages.length; j++)
		{
		check = false;

		for (int i = 0;i<current.length;i++)
		{
			age[i]++;
		}
		for (int i = 0; i< current.length; i++ )
		{		
			if (current[i] == pages[j])
			{
				check = true;
				age[i] = 0;
			}
		}
		if (check == false)
		{
			total++;
			highest = 0;
			for (int i = 1; i< size; i++)
			{

				if (age[highest] < age[i])
				{
					highest = i;
				}
				
			}
			current[highest] = pages[j];
			age[highest] = 0;
		}
		}
		return total;
	}

}
