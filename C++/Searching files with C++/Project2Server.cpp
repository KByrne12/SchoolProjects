#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <errno.h>

//#include <"Project2.h">

#define ELEMENTS 12
#define BUFFERLENGTH 1000


struct record
{
	char id[8];
	int odometer;
	float gallons;
};




int main (int argc, char *argv[])
{
	FILE *fileIn;
	int spot = 0;
	int error;
	char buffer[BUFFERLENGTH+1];
	int miles;
	float gallons,total = 0;
	char param[100];
	char command[BUFFERLENGTH];
	char* commandID;
	char rVal[BUFFERLENGTH];
	
	int rstream,wstream;

	rstream = atoi(argv[1]);
	wstream = atoi(argv[2]);
	
	read(rstream, command, BUFFERLENGTH);
	printf("command: %s\n", command);
	
	if (strcmp(command,"exit") == 0)
	{
		printf("Exiting due to exit command.\n");
		write(wstream,"-1",BUFFERLENGTH);
		exit(9);
	}
	
	int num = 0;
	
	int position;
	
	fileIn = fopen("Data.txt", "r");
	
	struct record gData[ELEMENTS];
	struct record gDataUpdated[3][4];
	struct record calc[4];
	int redPos,greenPos,numPos = 0;
	char * posChar;
	
	//assign values from data to array of structs
	for (int i = 0; i < ELEMENTS; i ++)
	{
		fscanf(fileIn,"%d", &error);
		fscanf(fileIn, "%s", &gData[i].id);
		fscanf(fileIn, "%d", &gData[i].odometer);
		fscanf(fileIn, "%f", &gData[i].gallons);
	
	}
	int stepper = 0;
	for (int i = 0; i < 3; i++)
	{
		for(int j = 0; j < 4; j++)
		{
			gDataUpdated[i][j] = gData[stepper];
			stepper++;
		}
	}
	
	printf("Elements: \n");
	
	//output array of elements
	for (int j = 0; j < ELEMENTS; j++)
	{
		printf(" %d : %s  %d  %f\n", j, gData[j].id,gData[j].odometer,gData[j].gallons);
	}



		//value for which car information is wanted from
		if (strstr(command,"red") != NULL)
		{
			position = 0;
		}
		else if(strstr(command,"green") != NULL)
		{
			position = 1;
		}
		else if(strstr(command,"987654") != NULL) 
		{
			position = 2;
		}
		
		
		//check for command, compute values
		if (strncmp(command , "mpg",2) == 0)
		{
			for (int i = 0; i < 3; i ++)
			{
				miles = gDataUpdated[position][i+1].odometer - gDataUpdated[position][i].odometer;
				gallons = gDataUpdated[position][i+1].gallons;
				total += miles/gallons;
			}
			sprintf(rVal, "%f",total/3);
			write(wstream,rVal,BUFFERLENGTH+1);
		}
		else if(strncmp(command , "list",4) == 0)
		{
			char temp[50];
			for(int i = 0; i < 4; i++)
			{
				sprintf(temp, "id: %s ", gDataUpdated[position][i].id);
				strcat(rVal,temp);
				sprintf(temp,"Odometer: %d ",gDataUpdated[position][i].odometer);
				strcat(rVal,temp);
				sprintf(temp,"Gallons: %f ",gDataUpdated[position][i].gallons);
				strcat(rVal,temp);
				
				//printf(" %d : %s  %d  %f\n", i, gDataUpdated[position][i].id,gDataUpdated[position][i].odometer,gDataUpdated[position][i].gallons);
				write(wstream, rVal,BUFFERLENGTH);
				strcpy(rVal,"");
			}
		} 
		else
		{
			printf("Invalid command: exiting\n");
			exit(5);
		}






	return 0;
}

