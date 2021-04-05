//#include "soc.h"
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <string.h>
#include <time.h>

#define SERVERPORT 31200
#define SERVERPORTSTR "31200"
#define SERVERIP "199.17.28.75"
#define SERVERNAME "ahscentos"
#define BUFL 100


//Function to get the game status e.g. GAME WON, GAME DRAW GAME IN CONTINUE MODE
// 0 | 1 | 2
// 3 | 4 | 5
// 6 | 7 | 8
bool gameover(char board[]){
    //checking the win for Simple Rows and Simple Column
    for(int i=0; i<3; i++)
    if(board[(3*i)] == board[(3*i)+1] && board[(3*i)+1] == board[(3*i)+2] || board[i] == board[i+3] && board[i] == board[i+6])
    return false;

    //checking the win for both diagonal

    if(board[0] == board[4] && board[0] == board[8] || board[2] == board[4] && board[2] == board[6])
    return false;

    //Checking the game is in continue mode or not
    for(int i=0; i<9; i++)
    if(board[i] != 'X' && board[i] != 'O')
    return true;

    //Checking the if game already draw
    return false;
}


int main(int argc, char *argv[])
{
	int sSocket = atoi(argv[1]);
	int cSocket = atoi(argv[2]);
	char board[9] = {'1','2','3','4','5','6','7','8','9'};
	char gg[9] = "GameOver";
	int err, move;
	char Buf[BUFL];
	char name[BUFL];
	char serverName[BUFL];
	char turn;
	bool gameContinue,draw;
	time_t t;
	srand((unsigned) time(&t));
	
	while(1){
		err = recv(cSocket, board, sizeof(board),0);
		if (err == -1)
		{
			printf ("socServer: read failed");
			exit (1);
		}

		gameContinue = gameover(board);
		if(gameContinue){
			while(1)
			{
				move = rand() % 9;
				if(board[move] != 'X' && board[move] != 'O'){
        		//updating the position for 'X' symbol if
        		//it is not already occupied
        			board[move] = 'O';
        			break;
    			}
    			else
					continue;
			}
			gameContinue = gameover(board);
			if (!gameContinue)
			{
				printf("The Computer has won!");
				err = send(cSocket,gg, sizeof(gg),0);
				break;
			}
	
			err = send(cSocket, board, sizeof(board),0);
		}
		else
		{
			printf("The player has won the game!");
			break;
		}
	}
	err = send(cSocket,gg, sizeof(gg),0);
	return 0;	
}


