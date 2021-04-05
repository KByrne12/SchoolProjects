//Tic Tac Toe Game in C++

#include <stdio.h>	//printf
#include <string.h>	//strlen
#include <sys/socket.h>	//socket
#include <arpa/inet.h>	//inet_addr
#include <unistd.h>

#include <iostream>
#include <stdlib.h>

#define SERVERPORT 31200
#define SERVERPORTSTR "31200"
#define SERVERIP "199.17.28.75"
#define SERVERNAME "ahscentos"
#define BUFL 100
#include <signal.h>

using namespace std;

//Array for the board

//bool draw = false;

//Function to show the current status of the gaming board
// 1 | 2 | 3
// 4 | 5 | 6
// 7 | 8 | 9


void display_board(char board[]){

    //Rander Game Board LAYOUT

    cout<<"Computer[X]\t Human[O]\n\n";
    cout<<"\t\t     |     |     \n";
    cout<<"\t\t  "<<board[0]<<"  |  "<<board[1]<<"  |  "<<board[2]<<" \n";
    cout<<"\t\t_____|_____|_____\n";
    cout<<"\t\t     |     |     \n";
    cout<<"\t\t  "<<board[3]<<"  |  "<<board[4]<<"  |  "<<board[5]<<" \n";
    cout<<"\t\t_____|_____|_____\n";
    cout<<"\t\t     |     |     \n";
    cout<<"\t\t  "<<board[6]<<"  |  "<<board[7]<<"  |  "<<board[8]<<" \n";
    cout<<"\t\t     |     |     \n";
}

//Function to get the player input and update the board

void player_turn(char board[]){
    //Taking input from user
    //updating the board according to choice and reassigning the turn Start

	//Variable Declaration
	char choice;
	int position; 

	printf("Pick your move: ");
    cin>> choice;

    //switch case to get which row and column will be update
	
    switch(choice){
        case '1': position = 0; break;
        case '2': position = 1; break;
        case '3': position = 2; break;
        case '4': position = 3; break;
        case '5': position = 4; break;
        case '6': position = 5; break;
        case '7': position = 6; break;
        case '8': position = 7; break;
        case '9': position = 8; break;
        default:
            cout<<"Invalid Move\n";
    }
	
    
	if(board[position] != 'X' && board[position] != 'O'){
        //updating the position for 'X' symbol if
        //it is not already occupied
        board[position] = 'X';
    }
	else {
        //if input position already filled
        cout<<"Box already filled!n Please choose another!!\n\n";
        player_turn(board);
    }
    /* Ends */
    display_board(board);
}


//Program Main Method

int main(int argc , char *argv[])
{
    char board[9] = {'1','2','3','4','5','6','7','8','9'};

  
	int err;
	int cSocket;
	struct sockaddr_in sAddr;
	struct sockaddr_in cAddr;
	int cSocLen;
	char buf[BUFL];
	char found[BUFL];
	
	memset (&cAddr, 0, sizeof (struct sockaddr_in));
	cAddr.sin_family = AF_INET;
	cAddr.sin_port = htons (SERVERPORT);
	cAddr.sin_addr.s_addr = inet_addr(SERVERIP);
	
	cSocket = socket (AF_INET, SOCK_STREAM, 0);
	if (cSocket == -1)
	{
		perror ("socClient: socket creation failed");
		exit(1);
	}
	
	err = connect (cSocket, (struct sockaddr*)&cAddr, sizeof(struct sockaddr_in));
	if (err == -1)
	{
		perror ("socClient: connect failed");
		exit(2);
	}
	
    cout<<"\t\t\tT I C K -- T A C -- T O E -- G A M E\t\t\t";
    while(1){
        display_board(board);
        player_turn(board);
		err = send(cSocket, board, sizeof(board),0);
			if (err == -1)
			{
				perror ("Send failed.");
				exit(2);
			}
		err = recv(cSocket, board, sizeof(board),0);
			if (err == -1)
			{
				perror ("receive failed.");
				exit(2);
			}
	/*	found = string(board).find("GameOver");
		if (found != string::npos)
		{
			printf("exiting");
			break;
		}
	*/	
    }
    
	printf("Client is exiting.\n");
  	return 0;

}
