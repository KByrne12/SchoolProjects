#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <sys/types.h>
#include <sys/wait.h>
#include <signal.h>
//#include <"Project2.h">

#define BUFFERLENGTH 1000



int main ()
{
	char buffer[BUFFERLENGTH+1];
	int id;
	int pipeToServer[2], pipeToInterface[2];
	char input[100];
	int spot = 0;
	int j = 0;
	int k = 0;
	char first[3];
	int status = 0;
	int error;
	char param[2][50];
	
	pid_t cpid;

	

	
	

	//creating pipes	
	error = pipe(pipeToServer);

	if( error == -1)
	{
		printf("pipe 1 not created, error num: %d \n", errno);
		exit(1);
	}
	
	error = pipe(pipeToInterface);
	
	if( error == -1)
	{
		printf("pipe 2 not created, error num: %d \n", errno);
		exit(1);
	}
	

	

		
	cpid = fork();
	if (cpid == 0)				//child server
	{
		close (pipeToServer[1]);
		close (pipeToInterface[0]);
		
		
		if ( error == -1)
		{
			printf("error when reading from pipe: %d \n", errno);
			exit(2);
		}
		
		
		sprintf(param[0], "%d", pipeToServer[0]);
		sprintf(param[1], "%d", pipeToInterface[1]);
		
		execl("./Project2Server", "Project2Server", param[0] , param[1] , (char*)NULL);
		
		
		
		
	} else if ( cpid > 0) 				//parent interface
	{
		close (pipeToServer[0]);
		close (pipeToInterface[1]);

		printf("mpg,id  list,id  exit\n");
		printf("Enter one of the following commands:");
		
		scanf( "%s", input );

		//send command to child
		error = write(pipeToServer[1], input, strlen(input)+1); 
		
		if (error == -1)
		{
			printf("Error on write to pipe: %d \n", errno);
			exit(0);
		}
		
		sleep(1);
		
		//read output from server
		error = read(pipeToInterface[0], buffer, BUFFERLENGTH);
		strncpy(first,input,4);
		buffer[BUFFERLENGTH] = '\0';

		

		//different outputs depending on command
		if (strcmp(first, "mpg,") == 0)									//mpg
		{
			float output = atof(buffer);
			printf("The average mpg is %f\n",output);
		}
		else if (strcmp(first, "list") == 0)							//list
		{
			printf("List:\n");
			printf("%s\n", buffer);
			for(int i = 0; i < 3; i++)
			{
			error = read(pipeToInterface[0], buffer, BUFFERLENGTH);
			printf("%s\n",buffer);
			}
		}
		else if (strcmp(buffer,"-1") == 0)								//exit
		{
			printf("Exited due to exit command.\n");
		}

		
	}
	else
	{
		exit(4);
	}
	

	return 0;
}

