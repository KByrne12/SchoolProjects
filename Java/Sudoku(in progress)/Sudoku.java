package com.company;
import java.util.Random;

public class Sudoku {
    private int difficulty;
    private int given[];
    public int board[][];
    Random rnd = new Random();


    Sudoku (int diff)
    {
        difficulty = diff*diff;
        given = new int[3*diff];
    }

    public void CreateBoard()
    {
        board = new int[difficulty][difficulty];
        for (int i = 0; i < difficulty; i++)
        {
            for (int j = 0; j < difficulty; j++)
            {
                board[i][j] = 0;
            }
        }
        boolean duplicates = true;

        for (int i = 0; i < given.length; i++)
        {
            while(duplicates)
            {
                duplicates = false;
                given[i] = rnd.nextInt(difficulty)+1;
                for (int j = 0; j < i; j++)
                {
                    if (given[i] ==given[j])
                    {
                        duplicates = true;
                    }
                }
            }
            duplicates = true;
        }

        for (int i = 0; i < given.length; i++)
        {
            System.out.print(given[i]);
        }
        System.out.println();

        for (int i = 0; i < given.length; i++)
        {
            board[rnd.nextInt(difficulty)][rnd.nextInt(difficulty)] = given[i];
        }
    }

    public void PrintBoard() {
        for (int i = 0; i < difficulty; i++)
        {
            for (int j = 0; j < difficulty; j++)
            {
                System.out.printf("[%d]",board[i][j]);
            }
            System.out.println();
        }
    }

    public boolean CheckRow(int row)
    {
        for (int i = 0; i < difficulty)
        {
            for (int j = i+1; j < difficulty; j++)
            {
                if (board[row][i] == board[row][j])
                    return false;
            }
        }
        return true;
    }

    public boolean CheckColumn(int column)
    {
        for (int i = 0; i < difficulty)
        {
            for (int j = i+1; j < difficulty; j++)
            {
                if (board[i][column] == board[j][column])
                    return false;
            }
        }
        return true;
    }
    public boolean CheckSquare(int square)
    {

    }

    public boolean CheckBoard(int row, int column)
    {
            return (CheckRow(row) && CheckColumn(column));
    }

    public void MakeMove()
    {

    }

    Public void PlayGame()
    {
        //Create board

        //loop
        //display board
        //Player input
        //check board
        //update board / print error if wrong
    }


}
