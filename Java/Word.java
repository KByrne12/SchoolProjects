package com.company;

import java.awt.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.stream.IntStream;

public class Word implements Serializable {
    private String word = "";
    private String clue = "";
    private int number;
    private int wordLen = 0;
    private Point startPoint;
    private Point numPoint;
    private Point[] charPositions;
    private boolean[] spotConnected;
    private boolean[] available;
    private boolean direction = true;
    private boolean discovered = false;

    protected static Context context;

    public static void setContext(Context context){
        Word.context = context;
    }

    // true = horizontal
    // false = vertical
    public boolean getDirection(){
        return direction;
    }

    // set position as connected
    // neighboring spots arnt available for connection
    public void setConnected(int i){
        spotConnected[i] = true;
        available[i] = false;
        if(i > 0)
            available[i-1] = false;
        if (i < wordLen-1)
            available[i+1] = false;
    }

    // returns Point position of character at position i
    public Point getCharPosition(int i){
        return charPositions[i];
    }

    // returns Char at position i
    public char getCharAt(int i){
        return word.charAt(i);
    }

    // constructor
    public Word(String word,String clue,int x,int y,boolean direction,int number){
        this.word = word;
        this.clue = clue;
        wordLen = word.length();
        startPoint = new Point(x,y);
        this.number = number;
        this.direction = direction;
        charPositions = new Point[wordLen];
        spotConnected = new boolean[wordLen];
        available = new boolean[wordLen];
        try {
            if (direction) {                                                                                            // check direction for algorithm ( horizontal )
                numPoint = new Point((x-context.getBoxSize()),y);                                                                    // number position just before the word
                IntStream.range(0, wordLen).forEach(i -> {                                                              // for each letter in word
                    charPositions[i] = new Point((x + (i * context.getBoxSize())), y);                                               // assign position of letter
                });
            } else {                                                                                                    // check direction for algorithm ( vertical )
                numPoint = new Point(x,(y-context.getBoxSize()));                                                                    // number position just before the word
                IntStream.range(0, wordLen).forEach(i -> {                                                              // for each letter in word
                    charPositions[i] = new Point(x, (y + (i * context.getBoxSize())));                                               // assign position of letter
                });
            }
        } catch (NullPointerException e) {
            System.out.println("Couldnt make " + word);
            }

        Arrays.fill(spotConnected, false);
        Arrays.fill(available, true);
    } // end constructor

    public boolean isSpotAvailable(int i){
        return available[i];
    }

    public boolean isConnected(int i){
        return spotConnected[i];
    }

    public void setDiscovered(){
        discovered = true;
    }

    public boolean isDiscovered(){
        return discovered;
    }

    public String getWord(){
        return word;
    }

    public String getClue(){
        return clue;
    }

    // calls render for clue and gives appropriate height
    public void renderClue(){
        Point drawPoint = new Point(context.getGameSize()[0]+20,(20*number));
        context.drawClue(drawPoint, clue, number);
    }

    // call render for each letter of the word and gives appropriate positions for each letter
    public void renderWord() {
        //Point drawPoint = startPoint;
        if (discovered) {
            for (int i = 0; i < wordLen; i++) {
                context.drawLetter(charPositions[i], word.charAt(i));
            }
        }
    }

    // renders each box for the word and gives appropriate positions for each box
    public void render(){
        context.drawNumber(numPoint, number);
        for (int i = 0; i < wordLen; i++){
            context.drawBox(charPositions[i]);
        }
    }
}
