package com.company;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.System.exit;

public class BillsUI {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private DataList months;
    private boolean done = false;

    public BillsUI(){ months = DataList.instance(); }

    //process of BillsUI
    //calls for input of month and year
    //calls function for converting to the key's mold
    //calls function for checking if the key exists
    //if no key exists, will ask to try again
    //if key exists, changes to UI for adding in values until specified done
    public void billsProcess(){
        boolean found = false;
        String entry = "";
        MonthExpenses monthTemp = null;
        while(!found) {
            entry = whichMonth();
            System.out.println(entry);
            monthTemp = findMonth(entry);
            if (monthTemp != null)
                found = true;
        }
        while(!done){
            whatChange(monthTemp);
        }
    }

    //returns int value from string input
    // for commands
    public int getCommand() {
        do {
            try {
                generalInterface();
                int value = Integer.parseInt(getToken("What are you adding?\n"));
                if (value >= 0 ) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid, try again");
            }
        } while (true);
    }

    //returns float value from string input
    //for money values
    public float getValue(){
        do{
            try{
                float value = Float.parseFloat(getToken("$"));
                if (value >= 0){
                    return value;
                }
            }catch (NumberFormatException nfe){
                System.out.println("Invalid, try again");
            }
        } while(true);
    }

    //returns string input
    public String getToken(String prompt) {
        do {
            try {
                System.out.print(prompt);
                String line = reader.readLine();
                StringTokenizer tokenizer = new StringTokenizer(line,"\n\r\f");
                if (tokenizer.hasMoreTokens()) {
                    return tokenizer.nextToken();
                }
            } catch (IOException ioe) {
                exit(0);
            }
        } while (true);
    }

    //creates String from inputs to fit the key allowing for minor human error
    //(3 letter month, capitalized)_(4 digit year)
    public String whichMonth(){
        char character;
        String doctoredMonth = "";
        String inMonth = getToken("What is the Month?");
        for(int i =0; i < 3; i++){
            character = inMonth.charAt(i);
            doctoredMonth += character;
        }
        String finalMonth = doctoredMonth.substring(0,1).toUpperCase() + doctoredMonth.substring(1).toLowerCase();
        String inYear = getToken("What is the Year?");
        String entry = finalMonth + "_" + inYear;

        return entry;
    }

    //handles inputs for which value to change
    public void whatChange(MonthExpenses month){
        int value = getCommand();
        float inVal = 0;
        switch(value){
            case 1:
                inVal = getValue();
                month.addRent(inVal);
                System.out.printf("Set Rent to $%.2f\n", inVal );
                break;
            case 2:
                inVal = getValue();
                month.addUtilities(inVal);
                System.out.printf("Added $%.2f to Utilities\n", inVal );
                break;
            case 3:
                inVal = getValue();
                month.setInternet(inVal);
                System.out.printf("Set Internet to $%.2f\n", inVal );
                break;
            case 4:
                inVal = getValue();
                month.addFood(inVal);
                System.out.printf("Added $%.2f to Food\n", inVal );
                break;
            case 5:
                inVal = getValue();
                month.addMisc(inVal);
                System.out.printf("Added $%.2f to Misc\n", inVal );
                break;
            case 0:
                done = true;
                break;
        }
    }
    //calls DataList for a specific month
    public MonthExpenses findMonth(String inVal){
        return months.getMonth(inVal);
    }

    //output for possible inputs
    public void generalInterface(){
        System.out.println("1: Rent");
        System.out.println("2: Utilities");
        System.out.println("3: Internet");
        System.out.println("4: Food");
        System.out.println("5: Misc");
        System.out.println("0: Exit");
    }

}
