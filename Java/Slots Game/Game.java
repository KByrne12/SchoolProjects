package com.company;




import java.util.Random;


public class Game {

    private static final int NUM_SYMBOLS = 10;
    private static final int SLOT_COUNT = 5;
    private Player p;

    UserInterface UI = new UserInterface();
    Random rand = new Random();
    private int[] spot = new int[SLOT_COUNT];

    //constructor for player coming to machine
    public Game(Player p1)
    {
        p = p1;
    }

    //Spinning slot machine
    public void spin()
    {
        for (int i = 0; i < SLOT_COUNT; i++)
        {
            spot[i] = Math.abs(rand.nextInt()%NUM_SYMBOLS);
            System.out.printf("[%d] ",spot[i]);
        }
        System.out.println();
    }

    //Check for win conditions and calculate return multiplier
    public int calcWin()
    {
        int[] duplicates = new int[NUM_SYMBOLS];
        int sets = 0;
        int pend = 0;
        for (int i = 0; i < SLOT_COUNT; i++)
        {
            duplicates[spot[i]]++;
        }
        for(int i = 0; i < NUM_SYMBOLS; i++)
        {
            if (duplicates[i] > 1)
            {
                sets++;
                if( pend < duplicates[i] && duplicates[i] > 2)
                    pend = duplicates[i];
            }
        }
        if (pend > 3)
        {
            pend *= pend;
        }
        if(sets > 1)
        {
            if (pend == 0)
                pend = 2;
            else
                pend *=2;
        }
        return (pend);
    }

    //update player's winnings at end of game
    public void updatePlayer(Player p,int remaining)
    {
        p.setTotalMoney(remaining);
    }

    //play the game
    public int play(int ante)
    {
        spin();
        int multiplier = calcWin();

        return ((ante * multiplier));
    }

    //check difference in starting money and ending money
    //displays message according to result
    public void checkTotalWin(int availMoney)
    {
        int winnings;
        winnings = availMoney-p.getTotalMoney();
        if (winnings > 0) {
            UI.DisplayWin(winnings);
        } else {
            UI.DisplayLoss(Math.abs(winnings));
        }
    }

    //begins game when called from main
    public void StartGame()
    {
        int command;
        int availMoney = p.getTotalMoney();
        int currentAnte = 1;
        int winnings;
        UI.DisplayOptions();
        System.out.printf("Current money: %d\n",availMoney);
        System.out.printf("Current bet: %d\n",currentAnte);
        while ((command = UI.getInt()) != 3 && availMoney > 0)
        {
            switch(command) {
                case 1:
                    if (availMoney >= currentAnte) {
                        availMoney -= currentAnte;
                        winnings = play(currentAnte);
                        availMoney += winnings;
                    }
                    else{
                        System.out.println("You do not have enough for this bet.");
                        System.out.println("Please change your ante by pressing 2.");
                    }
                    break;
                case 2:
                    System.out.println("Enter your new bet amount: ");
                    currentAnte = UI.getInt();
                    break;
                default:
                    System.out.println("Invalid input");
            }
            if (availMoney == 0) {
                System.out.println("You have run out of funds.");
                break;
            }
            UI.DisplayOptions();
            System.out.printf("Current money: $ %d\n",availMoney);
            System.out.printf("Current bet: %d\n",currentAnte);
        }


        checkTotalWin(availMoney);
        updatePlayer(p,availMoney);


    }



}
