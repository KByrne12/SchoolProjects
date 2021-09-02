package com.company;

import java.io.*;
import java.sql.*;
import java.util.StringTokenizer;

//sql admin:
    //mainMaster
    //TrueLogin1!
    //uNpn: US-ASHBURN-AD1
    //VCN: HomeVCN - VCN-2021-07-21T16:17:38


import static java.lang.System.exit;

public class Crossword {
    private BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private Model model = new Model();
    private View view = new View();
    private boolean done = false;


    public Crossword(){}

    public void runCrossword (){
        System.out.println("Crossword is running!");
        createGame();
        Model.setView(view);
        view.setModel(model);
        view.show();
        playGame();
    }

    public void createGame(){
        try(
                Connection conn = DriverManager.getConnection("jdbc:Mysql://127.0.0.1:3306/crossword","mainMaster","TrueLogin1!");
                Statement stmt = conn.createStatement();
                )
        {
            String strSelect = "SELECT * FROM crossword.words";
            ResultSet rSet = stmt.executeQuery(strSelect);

            String word, clue;
            while(rSet.next()){
                word = rSet.getString("Word");
                word.toLowerCase();
                clue = rSet.getString("Clue");
                model.attemptAddWord(word,clue);
            }
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

    public void playGame(){
        while (!done){
            int word = getCommand();                                                                                    // selection of word to guess
            String guess = getToken("Guess: ");                                                                  // input of guess
            model.guess(guess.toLowerCase(),word);                                                                      // check model with given selection / guess
            view.refresh();                                                                                             // refresh the model
            if(model.isGameDone())                                                                                      // check if all words have been discovered
                done = true;                                                                                            // end game if all words discovered
        }
    }

    // used for int input for selecting word
    public int getCommand() {
        do {
            try {
                int value = Integer.parseInt(getToken("Which word would you like to try?\n"));
                if (value > 0 ) {
                    value -= 1;
                    return value;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Invalid, try again");
            }
        } while (true);
    }

    // used for string input for making guesses
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
}
