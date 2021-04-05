//#include "soc.h"
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <sys/wait.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>

#define SERVERPORT 31200
#define SERVERPORTSTR "31200"
#define SERVERIP "199.17.28.75"
#define SERVERNAME "ahscentos"
#define BUFL 100
#include <signal.h>



int main()
{
	//Variables
	int error;
	int exitCode = 0;
	pid_t cpid;
	int sSocket;
	int cSocket;
	struct sockaddr_in sAddr;
	struct sockaddr_in cAddr;
	unsigned int cSocLen;
	char buf[BUFL];
	char param[BUFL];
	char sSocketPass[BUFL];
	char cSocketPass[BUFL];
	
	
	//socket creation
	printf("Attempting to create socket.\n");
	sSocket = socket(AF_INET , SOCK_STREAM , 0);
	if (sSocket == -1)
	{
		perror("SocServer: socket creation failed");
		exit(2);
	}
	printf("Socket creation successful.\n");
	
	
	// server address
	memset(&sAddr, 0, sizeof(struct sockaddr_in));
	sAddr.sin_family = AF_INET;
	sAddr.sin_port = htons(SERVERPORT);
	//sAddr.sin_port = 0;
	sAddr.sin_addr.s_addr = htonl(INADDR_ANY);
	
	//bind socket to server
	printf("ServerC: attempting to bind.\n");
	error = bind (sSocket, (struct sockaddr*)&sAddr, sizeof(struct sockaddr_in));
	if (error == -1)
	{
		perror("bind address to socket failed");
		exit(3);
	}
	printf("ServerC: bind Successful.\n");
	
	//listen 
	printf("ServerC: attempting Listen.\n");
	error = listen(sSocket,5);
	if (error == -1)
	{
		perror("Listen failed.");
		exit(4);
	}
	printf("ServerC: listen successful.\n");
	
	//add condition to check for how many attempting to connect
	while(1)
	{
		
		printf("ServerC: Waiting for client connection to attempt to accept.\n");
		cSocket = accept(sSocket, (struct sockaddr *)&cAddr, &cSocLen);
		if (cSocket == -1)
		{
			perror("Accept failed.");
			exit(5);
		}
		printf("ServerC: connection received and accept successful.\n");
		sprintf(sSocketPass, "%d", sSocket);
		sprintf(cSocketPass, "%d", cSocket);
	
		cpid = fork();
		
		if (cpid == 0)				//child
		{
			printf("ServerC: initializing serverG for client.\n");
			cpid = execl("./ServerGEdit", "ServerGEdit", sSocketPass, cSocketPass, (char*) NULL);
			if (cpid == -1)
			{
				printf("execl failed: %d\n", errno);
			}
		}
		//else if (cpid > 0)
		//{
			//printf("here\n");
		//}
		/*
		else if(cpid > 0)			//parent
		{
			
			error = waitpid(cpid, &exitCode, 0); //getting and printing server exit code
			if (error == -1)
			{
				printf("Error occurred with waitpid: %d\n", errno);
				exit(3);
			}
			printf("Server exited with code: %d\n", exitCode);
			exit(0);
			
		}
		*/
	
	}
	
	return 0;
}
