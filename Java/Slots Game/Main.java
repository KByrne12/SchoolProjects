package com.company;


public class Main {

    public static void main(String[] args) {
        Player P1 = new Player("Joe", 100);
        Game playGame = new Game(P1);
        playGame.StartGame();
    }
}
