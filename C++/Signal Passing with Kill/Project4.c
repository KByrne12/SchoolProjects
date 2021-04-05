
#include "Project4.h"


int signalOp;

//signal handler for alarm
void alarmHandle (int sig)
{
		signalOp = sig;
		time_t newTime = time(NULL);
		printf("[%d] Alarm Complete\n",newTime);
}

//signal handler for SIGUSR1 signal
void sigHandle(int sig)
{
	signalOp = sig;
	time_t newTime = time(NULL);
	printf("\n[%d] SIGUSR1 signal received, adding an alarm.\n",newTime,sig);
	
}



int main(int argc, char *argv[])
{
	//variables
	int error;
	int n = atoi(argv[1]);
	int aTime,i,j,k,temp,position,test;
	time_t ctime,ntime;
	ctime = time(NULL);
	printf("Current time: %d\n",ctime);
	srand((unsigned) time(&ctime));
	int offset[n+2];
	int organized[n+2];
	int timeStamps[n+2];
	
	//establish alarm times
	for(i = 0; i < n; i++)
	{
		offset[i] = (rand() % MAX_TIME)+1;
		for (j = 0; j <i; j++)
		{
			if (offset[i]==offset[j])
				offset[i]++;
		}
		organized[i] = offset[i];
		timeStamps[i] = ctime + offset[i];
		printf("[%d,%d]\n",i,timeStamps[i]);
		
	}
	//organize alarm times in order for alarm scheduling
	for (i = 0; i < n; i++)
	{
		for (j = i+1; j <n; j++)
		{
			if (organized[i] > organized[j])
			{
				temp = organized[i];
				organized[i] = organized[j];
				organized[j] = temp;
			}
		}
	}	
	
	//create sigaction for alarms
	struct sigaction alrm_act;
	alrm_act.sa_handler = (void(*)(int)) alarmHandle;
	sigemptyset(&alrm_act.sa_mask);
	alrm_act.sa_flags = 0;
	error = sigaction (SIGALRM, &alrm_act, NULL);
	if (error == -1)
	{
		printf("Error in setting handler(alarm)");
		exit(1);
	}
	//create sigaction for SIGUSR1
	struct sigaction sig_act;
	sig_act.sa_handler = (void(*)(int)) sigHandle;
	sigemptyset(&sig_act.sa_mask);
	sig_act.sa_flags = 0;
	error = sigaction (MYSIGNAL, &sig_act, NULL);
	if (error == -1)
	{
		printf("Error in setting handler(sig)");
		exit(1);
	}
	
	for (i = 0; i < n; i++)
	{
		//find position of alarm that is next to go off	
		for (j = 0; j < n; j++)
		{
			if (organized[i] == offset[j])
			{
				position = j;
				break;
			}
		}
		ntime = time(NULL);
		printf("[%d] Waiting for alarm: %d at time: %d\n",ntime, position, ctime + offset[position] );
		
		//set up time until next alarm
		if (i == 0)
			aTime = organized[0];
		else
			aTime = organized[i] - organized[i-1];
			
		//create alarm
		error = alarm(aTime);
		
		//wait for signal
		error = pause();
		
		
		//if signal was from SIGUSR1, create a new alarm and add it to list
		if (signalOp == 10)
		{
			//printf("Signal received, adding an alarm.\n");
			ntime = time(NULL) + ((rand() % MAX_TIME)+1);
			temp = ntime - ctime;
			organized[n] = temp;
			offset[n] = temp;
			n++;
			//reorganize alarms incase new alarm happens in the middle of already established alarms
			printf("New List of Alarms: \n");
			for (k = 0; k < n; k++)
			{
				for (j = k+1; j<n; j++)
				{
					if (organized[k] > organized[j])
					{
						temp = organized[k];
						organized[k] = organized[j];
						organized[j] = temp;
					}
				}
				printf("[%d,%d]\n",k,(ctime+offset[k]));
			}
			//wait for next signal due to still waiting for alarm
			pause();
		}
	}
	ntime = time(NULL);
	printf("[%d] All Alarms complete\n", ntime);
	return 0;
}
