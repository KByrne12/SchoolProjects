#include "Project1.h"
#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <math.h>

#define NUMLENGTH 32

void DisplayOptions();
void ChangeBase(unsigned long num);
unsigned long OneCompliment(unsigned long num);


int main ()
{
	int option = 0;
	unsigned long num;
	bool changed = false;
	
	printf("Please Input An Integer Number: ");							//request input from user
	scanf("%d", &num);
	printf("\n");

	while(option != 5)													//loop for display menu with exit condition of 5
	{
	DisplayOptions();
	printf("Please select an option: ");
	scanf("%d",&option);
	printf("\n");
	switch(option){
		case 1:															//prints current number in requested base
			ChangeBase(num);
			break;
		case 2:															//exchange odd bits of current number
			printf("Before: %d \n",num);
			num = XODD(num);
			printf("After: %d \n\n",num);
			break;
		case 3:															//exchange even bits of current number
			printf("Before: %d \n",num);
			num = XEVEN(num);
			printf("After: %d \n\n", num);
			break;
		case 4:															//takes ones compliment of current number
			printf("Before: %d \n", num);
			num = OneCompliment(num);
			printf("After: %d \n\n", num);
			break;
		case 5:															//ends the program
			printf("Ending the program.\n\n");
			break;
		default:														//if no option selected. asks to try again.
			printf("Invalid input, please try again. \n\n");
	}
	}
	printf("Final number: %d\n\n", num);
	return 0;
}
void ChangeBase(unsigned long num)										//shows inputed number in requested base
{
	unsigned long newNum = 0;
	unsigned long temp[NUMLENGTH];
	int counter = 0;
	int i;
	int base = BASE;
	switch(base){														//checks for requested base
		case 2:
				for(counter = 0; counter < NUMLENGTH; counter++)
				{
					temp[counter] = num%2;
					num /= 2;
					newNum += (temp[counter]);
				}
				printf("The number written in Binary is: ");
				for (counter = NUMLENGTH-1; counter >= 0; counter --)
				{
					printf("%d", temp[counter]);
				}
				printf("\n\n");
			break;
		case 8:
			printf("The number written in Octal is: %o\n\n",num);
			break;
		case 16:
			printf("The number written in Hexadecimal is: %X\n\n",num);
			break;
	
		default:
			printf("No new base specified.\n");
	}
		
	return;
}


void DisplayOptions()													//prints option menu
{
	printf("1: Display number in requested base \n");
	printf("2: Exchange odd numbered bytes(1 with 3)\n");
	printf("3: Exchange even numbered bytes(0 with 2)\n");
	printf("4: take the one's complement of the number\n");
	printf("5: end the program\n");
	
	return;
}


unsigned long OneCompliment(unsigned long num)							//returns the Ones compliment
{
	unsigned long compliment;
	compliment = ~num;
	
	return compliment;
}
