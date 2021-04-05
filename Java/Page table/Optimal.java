package pack;

public class Optimal 
{
	
	int[] pages;
	
	public Optimal(int[] array)
	{
		pages = new int[array.length];
		pages = array;
	}
	
	
	int CalcFaults(int size)
	{
		
		
	int total = 0;
	int[] current = new int[size];
	int[] distance = new int[size];
	boolean check;
	int far;
	
	
	for (int j = 0; j < pages.length; j++)
	{
	check = false;
	
	for (int i = 0; i< current.length; i++ )
	{		
		if (current[i] == pages[j])
		{
			check = true;
		}
	}
	
	
	
	if (check == false)
	{
		
		for(int pos = 0; pos < current.length;pos++)
		{
			distance[pos] = 10000;
			for(int step = 1; step < pages.length-j;step++)
			{

				if(current[pos] == pages[j+step])
				{
					distance[pos] = step;
					break;
				}
			}
			//System.out.print("Distance " + distance[pos] + " ");
		}
		//System.out.println();
		far = 0;
		for(int val = 1; val < current.length; val ++)
		{
			if(distance[far] < distance[val])
			{
				far = val;
			}
			
		}
		current[far] = pages[j];
		total++;
		
		
	}
	
	}
	return total;
	}
}
