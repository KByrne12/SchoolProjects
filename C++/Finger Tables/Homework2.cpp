#include <stdio.h>
#include <vector>
#include <iostream>
#include <fstream>
#include <math.h>
#include <stdlib.h>

using namespace std;

struct fNode
{
	int nodeID;
	vector<int> succ;
};

int main(int argc, char* argv[])
{
	//Variables
	int m = 20;
	int search = atoi(argv[1]);
	int found;
	ifstream infile("nodeIDs.txt");
	int num,position,value,current;
	fNode temp;
	vector<fNode> fingTable;
	
	//read file of IDs and assign them into the vector list
	while (infile>>num)
	{
		temp.nodeID = num;
		fingTable.push_back(temp);
	}
	//step through the vector list
	for(int i = 0; i < fingTable.size(); i++)
	{
		//for each of the m spots
		for (int j = 0; j < m; j++)
		{
			//calculate value to search for in IDs to find successor
			value = fingTable[i].nodeID + (pow(2,j));
			
			//Circle around if value goes higher than the max node value
			if(value > fingTable[fingTable.size()-1].nodeID)
				value -= fingTable[fingTable.size()-1].nodeID;
			//catch if value is less than the first node value	
			if (value <= fingTable[0].nodeID)
				fingTable[i].succ.push_back(fingTable[0].nodeID);
			else
			{
				//calculate each finger's node reference
				for (int k = 1; k < fingTable.size(); k++)
				{
					if (value >= fingTable[k].nodeID)
					{
						current = k+1;	
					}		
				}
				fingTable[i].succ.push_back(fingTable[(current)].nodeID);
			}		
		}
	}
	//search for requested finger
	for (int i = 0; i < fingTable.size(); i++)
	{
		if (fingTable[i].nodeID == search)
		{
			found = i;
			break;
		}
	}
	//output finger table for requested finger
	cout << "Finger table for: " << fingTable[found].nodeID << endl;
	for (int i = 0; i < m; i++)
		cout << i+1 << ": " << fingTable[found].succ[i] << endl;

return 0;
}

