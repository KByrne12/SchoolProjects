package com.company;

public class MonthExpenses {

    private String monthYear;
    private float total = 0;
    private float eachTotal = 0;
    private float utilities = 0;
    private float rent = 0;
    private float misc = 0;
    private float internet = 0;
    private float food = 0;

    public MonthExpenses(){}
    public MonthExpenses(String date){
        monthYear = date;
    }

    //private to prevent unintended breaking of totals
    private void setTotal(float total){
        this.total = total;
    }
    private void setEachTotal(float eTotal){
        eachTotal = eTotal;
    }

    public void setUtilities(float util){
        utilities = util;
        calcTotals();
    }

    //sets monthYear to specified key value
    public void addMonth(String str){
        monthYear = str;
    }

    //multiple Utilities bills, running total
    public void addUtilities(float util){
        utilities += util;
        calcTotals();
    }

    //Rent is one bill, sets to value
    public void addRent(float rent) {
        this.rent = rent;
        calcTotals();
    }

    //add value to Misc total, running total
    public void addMisc(float misc){
        this.misc += misc;
        calcTotals();
    }

    //internet is one bill, sets to value
    public void setInternet(float inter){
        internet = inter;
        calcTotals();
    }

    //add value onto Food total, running total
    public void addFood(float val){
        food += val;
        calcTotals();
    }

    //calculates total and total/2 for end of grid
    private void calcTotals(){
        total = 0;
        eachTotal = 0;
        total += utilities + rent + misc + + food + internet;
        eachTotal = total/2;
    }

    //output a line on the grid
    public void outputGrid(){
        System.out.print("|");
        System.out.format("%15s|",monthYear);
        outputVal(rent);
        outputVal(utilities);
        outputVal(internet);
        outputVal(food);
        outputVal(misc);
        outputVal(total);
        outputVal(eachTotal);
        System.out.println();
    }

    //output float to fit grid
    private void outputVal(float value){ System.out.format("$%14.2f|",value); }

    //getter functions
    public String getMonthYear(){
        return monthYear;
    }
    public float getRent(){ return rent; }
    public float getUtilities(){ return utilities; }
    public float getInternet(){ return internet; }
    public float getFood(){ return food; }
    public float getMisc(){ return misc; }
    public float getTotal(){ return total; }
    public float getEachTotal(){ return eachTotal; }

}
