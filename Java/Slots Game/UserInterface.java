package com.company;


import java.util.Scanner;

public class UserInterface {

    Scanner in = new Scanner(System.in);

    public void DisplayOptions()
    {
        System.out.println("1: Spin the Wheel");
        System.out.println("2: Change your bet");
        System.out.println("3: Cash Out");
    }

    public int getInt()
    {
        return in.nextInt();
    }

    public void DisplayLoss(int losses)
    {
        System.out.printf("You lost: $%d\n",losses);
        System.out.println("Better luck next time!");
    }

    public void DisplayWin(int winnings)
    {
        System.out.printf("Your winnings: $%d\n", winnings);
        System.out.println("Thanks for playing!");
    }


}
