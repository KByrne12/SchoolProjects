#include "Project4.h"

//runs Project code and calls kill commands from command line
int main(int argc, char *argv[])
{
	pid_t pid1;
	pid_t result;
	int error;
	int status;
	
	
	pid1 = fork();

	if(pid1 == 0)
	{
		execl("./Project4","Project4",argv[1], (char *)NULL);		
	}
	else
	{		

		sleep(5);
		error = kill(pid1,MYSIGNAL);
		if (error == -1)
		{
			printf("Error within kill");
			exit(3);
		}
		
		sleep(20);
		
		error = kill(pid1,MYSIGNAL);
		if (error == -1)
		{
			printf("Error within kill");
			exit(3);
		}
		
		
		sleep(60);
	
	
	return 0;

	}
}
