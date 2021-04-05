package com.company;

public class Main {

    public static void main(String[] args) {
        Sudoku game = new Sudoku(3);
        game.CreateBoard();
        game.PrintBoard();
    }
}
