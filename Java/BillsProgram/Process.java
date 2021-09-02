package com.company;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

import static java.lang.System.exit;

public class Process {

    //input
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    //instance of data for function calls
    private DataList months = DataList.instance();


    public void billingProcess() {
        try{
            months.reloadMonths();
        }catch (FileNotFoundException e) {
            exit(0);
        }
        boolean done = false;
        generalInterface();
        while(!done){
            switch(getCommand()){
                case 1:
                    months.output();
                    break;
                case 2:
                    addBills();
                    System.out.println("Complete.");
                    break;
                case 3:
                    months.createMonth();
                    System.out.println("Complete.");
                    break;
                case 4:
                    generalInterface();
                    break;
                case 0:
                    done = true;
                    try{months.saveMonths();} catch (IOException e) {exit(1);}
                    System.out.println("Thank you.");
                    break;
            }
        }
    }

    //convert string input to an integer for commands
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Enter Command: Enter 4 for help"));
                if (value >= 0 ) {
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Enter a number");
            }
        } while (true);
    }

    //reading a string input
    public String getToken(String prompt) {
        do {
            try {
                System.out.println(prompt);
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

    //input values
    public static void generalInterface(){
        System.out.println("What would you like to do?");
        System.out.println("1: View History");
        System.out.println("2: Add bills");
        System.out.println("3: Add Month");
        System.out.println("0: Exit");
    }

    // start BillsUI
    public void addBills(){
        BillsUI billsUI = new BillsUI();
        billsUI.billsProcess();
    }

    public void run(){

    }
}
