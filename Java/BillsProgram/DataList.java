package com.company;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.LinkedList;
import java.util.Scanner;

public class DataList {
    static final int CATEGORIES = 8;
    private static DataList instance;
    //list of months
    LinkedList<MonthExpenses> months = new LinkedList<MonthExpenses>();
    String[] categories = new String[]{"Month", "Rent", "Utilities","Internet","Food","Misc","Total","Each"};

    //instance of Class
    public static DataList instance(){
        if (instance == null){
            instance = new DataList();
        }
        return instance;
    }

    //saves data at end of program back into file
    public void saveMonths() throws IOException {
        File file = new File("C:\\Users\\ksaso\\Desktop\\Important Info\\Bills\\Bill_Record.csv");
        FileWriter writer = new FileWriter(file);
        PrintWriter pWriter = new PrintWriter(writer);
        for(MonthExpenses temp : months){
            pWriter.printf("%s %.2f %.2f %.2f %.2f %.2f\n",temp.getMonthYear(),temp.getRent(),temp.getUtilities(),
                    temp.getInternet(),temp.getFood(),temp.getMisc());
        }
        pWriter.close();
    }

    //creates a new month in system
    public void createMonth(){
        String preMonth = months.getLast().getMonthYear();
        String currentMonth = "";
        String currentYear = "";
        String newDate = "";
        boolean updateYear = false;
        char character;
        for (int i = 0; i < 3; i++){
            character = preMonth.charAt(i);
            currentMonth += character;
        }
        for(int i = 4; i < 8;i++){
            character = preMonth.charAt(i);
            currentYear += character;
        }
        //update
        switch (currentMonth){
            case "Jan":
                newDate = "Feb";
                break;
            case "Feb":
                newDate = "Mar";
                break;
            case "Mar":
                newDate = "Apr";
                break;
            case "Apr":
                newDate = "May";
                break;
            case "May":
                newDate = "Jun";
                break;
            case "Jun":
                newDate = "Jul";
                break;
            case "Jul":
                newDate = "Aug";
                break;
            case "Aug":
                newDate = "Sep";
                break;
            case "Sep":
                newDate = "Oct";
                break;
            case "Oct":
                newDate = "Nov";
                break;
            case "Nov":
                newDate = "Dec";
                break;
            case "Dec":
                newDate = "Jan";
                updateYear = true;
            default:
                System.out.println("Error in database");
                break;
        }
        if(updateYear){
            int value = Integer.parseInt(currentYear);
            value++;
            currentYear = String.valueOf(value);
        }
        newDate += "_" + currentYear;
        MonthExpenses temp = new MonthExpenses(newDate);
        months.add(temp);
    }

    public void ReloadMonths(){
        Connection conn =  DriverManager.getConnection("",)
    }

    //reload data from file back into system
    public void reloadMonths() throws FileNotFoundException {
        File file = new File("C:\\Users\\ksaso\\Desktop\\Important Info\\Bills\\Bill_Record.csv");
        String data;
        Scanner reader = new Scanner(file);


        while (reader.hasNext()){
            MonthExpenses month = new MonthExpenses();
            month.addMonth(reader.next());
            month.addRent(Float.parseFloat(reader.next()));
            month.addUtilities(Float.parseFloat(reader.next()));
            month.setInternet(Float.parseFloat(reader.next()));
            month.addFood(Float.parseFloat(reader.next()));
            month.addMisc(Float.parseFloat(reader.next()));
            months.add(month);
        }
        reader.close();
    }
    //outputs grid of data
    public void output() {
        System.out.print("|");
        for(int i = 0; i < CATEGORIES; i++) {
            System.out.format("%15s|",categories[i]);
        }
        System.out.println();
        System.out.print("_");
        for(int i =0; i < CATEGORIES; i++) { System.out.print("________________"); }
        System.out.println();
        for (MonthExpenses temp : months){ temp.outputGrid(); }
    }

    //returns month based on the "Month" key from data set
    public MonthExpenses getMonth(String entry){
        for (MonthExpenses temp : months){
            if (temp.getMonthYear().equals(entry)){
                return temp;
            }
        }
        return null;
    }
}
