//Dining philosophers problem solved using threads

#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <pthread.h>
#include <unistd.h>

#define NUMPHIL 5
#define TIMEMAX 5


pthread_mutex_t lockOne;
pthread_mutex_t lockTwo;

//forks, 1 = on table, 0 = taken
int forks[5] = {1,1,1,1,1};
// seats, 0 = open
int seats[5] = {0,0,0,0,0};
int tick = 0;


//philosopher object
//predetermined eat and think times
struct philosopher{
	int ID;				//random ID of philosopher
	int eatTime;		//how long the philosopher eats
	int thinkTime;		//how long the philosopher thinks
	int seat;			//where at the "table they sit
};

//getting permission from coordinator to pick up forks and eat
bool CoordinatorPermission(struct philosopher phil)
{
	//check which seat the philosopher is in
	int seat = -1;
	for(int i = 0; i < NUMPHIL; i++)
	{
		if (seats[i] == phil.ID)
			seat = i;
	}
	
	if (!(seat == -1))
	{
		//forks available initially
		//printf("before forks: 0:%d 1:%d 2:%d 3:%d 4:%d\n", forks[0],forks[1],forks[2],forks[3],forks[4]);
		//if both forks on either side of the philosopher are available,
		if ((forks[seat%5] == 1) && (forks[(seat+1)%5] == 1))
		{
			//pick up forks
			forks[seat%5] = 0;
			forks[(seat+1)%5] = 0;
			//print forks available after taken
			printf("after forks: %d %d %d %d %d\n", forks[0],forks[1],forks[2],forks[3],forks[4]);
			return true;
		}
		else
			return false;
		
	} else {
		printf("Error in finding philosopher %d among seats.(in coordinator)", phil.ID);
		exit(5);
	}
}

//seat is assigned to philosopher at table
//locked so no one "shares" a seat
int AssignSeat(struct philosopher phil)
{
	pthread_mutex_lock(&lockTwo);
	seats[tick] = phil.ID;
	phil.seat = tick;
	tick++;
	pthread_mutex_unlock(&lockTwo);
	return tick-1;
}

using namespace std;

//function for thread to run
//endless loop of philosophers asking permission to eat from coordinator
//eating if coordinator says OK
void *philosopherMethod(void *input)
{
	struct philosopher phil = *(struct philosopher *)input;
	
	phil.seat = AssignSeat(phil);
	printf("philosopher: %d, seat: %d\n",phil.ID, phil.seat);
	sleep(1);
	printf("philosopher %d is thinking\n",phil.ID);
	while(true)
	{
		sleep(phil.thinkTime);
		pthread_mutex_lock(&lockOne);
		if (CoordinatorPermission(phil))
		{	
			printf("philosopher %d is eating\n", phil.ID);
			sleep(phil.eatTime);
			printf("philosopher %d is done eating\n",phil.ID);
			printf("philosopher %d is thinking\n",phil.ID);
			forks[phil.seat%5] = 1;
			forks[(phil.seat+1)%5] = 1;
		} 
		pthread_mutex_unlock(&lockOne);
	}
}

//thread creation and join
int main(int argc,char *argv[])
{
	//5 threads as per assignment
	pthread_t thread[NUMPHIL];
	struct philosopher phil[NUMPHIL];
	int ID;
	int error;
	
	//create threads
	for (int i = 0; i < NUMPHIL; i++)	
	{
		phil[i].ID = rand()%1000 + 1;
		phil[i].eatTime = rand()%TIMEMAX + 1;
		phil[i].thinkTime = rand()%TIMEMAX + 1;
		printf("Id %d = %d, eatTime = %d, thinkTime = %d\n", i, phil[i].ID,phil[i].eatTime,phil[i].thinkTime);
		error = pthread_create(&thread[i], NULL, &philosopherMethod, (void* ) &phil[i]);
		sleep(0.5);
	}
	
	//joining threads
	for (int i = 0; i < NUMPHIL ; i++)
	{
		pthread_join(thread[i],NULL);
	}
	
	return 0;
}
