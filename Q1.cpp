//ch8499fk

#include <pthread.h>
#include <time.h>
#include <string.h>
#include <stdlib.h>
#include <cstdlib>
#include <iostream>
#include <ctime>
#include <errno.h>


pthread_mutex_t lock;

using namespace std;

struct arguments
{
	int ID;
	int INC;
	time_t begin;
};

//in: IDs and size of increments
//task: calculate triplets of pythagrean thereom possible in the set
void *calculation(void *input)
{
	pthread_mutex_lock(&lock);								//lock on calculation
	time_t current,start;									//time stamps creation for elapsed time
	int a,b,c;												//variables for pythagrean thereom
	int total = 0;
	struct arguments arg = *(struct arguments *) input;		//converting thread variable back to structure
	int ID = arg.ID;					
	int INC = arg.INC;
	start = time(&start);
	int intervalHigh = (ID+1) * INC;
	int intervalLow= (ID) * INC;
	
	
	for ( c = intervalLow; c < intervalHigh; c++ )			//calculate total amount of triplets in increment
	{
		for (b = 1; b < c; b++)
		{
			for(a = 1; a < c; a++)
			{
				if ((a*a) + (b*b) == (c*c))
				{
					total++;
				}
			}
		}
	}
	
	current = time(&current);								//current time elapsed calculation
	time_t time = current - start;
	//output string
	cout << "ID = " << ID << ", total count = " << total << ", elapsed time= " << time << " seconds" << endl;
	
	pthread_mutex_unlock(&lock);							//unlock calculation for next thread
	return NULL;
}

//set up arguments for threads and start threading
//at end of program notify of completion
int main(int argc,char *argv[])
{
	int INCR = atoi(argv[2]);								//size of increments
	int threadCount = atoi(argv[1]);						//number of threads
	
	struct arguments args [3];								//structure for argmunets sent to thread
	pthread_t thread[threadCount];							//creation of threads
	time_t start = time(&start); 							//create time of program start
	int error;												//variable for checking for errors
	
	for (int i = 0; i < threadCount; i++)					//threads start and args is filled in
	{	
	args[i].ID = i;
	args[i].INC = INCR;
	//args[i].begin = start;
	error =pthread_create(&thread[i],NULL,&calculation,(void *) &args[i]);
	if(error)
	{
		cout << "Thread failed to be created." << endl;
		exit(i);
	}
	}
	
	
	for (int i = 0; i < threadCount ; i++)					//joining threads
	{
		pthread_join(thread[i],NULL);
	}
	
	cout << ("Threads complete\n");							//notify of completion
	
	return 0;
	
}





