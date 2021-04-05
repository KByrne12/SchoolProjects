package com.company;

public class Player {
    private String name;
    private int totalMoney;

    public Player(String nameIn,int moneyIn)
    {
        totalMoney = moneyIn;
        name = nameIn;
    }



    public String getName()
    {
        return name;
    }

    public int getTotalMoney()
    {
        return totalMoney;
    }


    public void setTotalMoney(int newMoney)
    {
        totalMoney = newMoney;
    }

    public void setName(String newName)
    {
        name = newName;
    }
}
