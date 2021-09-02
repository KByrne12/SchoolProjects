package com.company;

import java.awt.*;
import java.util.Enumeration;
import java.util.Vector;

public class Model {
    private Vector<Word> wordList = null;

    private static View view = null;
    private static Context context = Context.getInstance();
    private int number = 1;

    // constructor
    public Model(){ wordList = new Vector(); }

    public static void setUI(Context context){
        Model.context = context;
        Word.setContext(context);
    }

    // set reference to view
    public static void setView(View view){
        Model.view = view;
    }

    // return list of words for view to draw
    public Enumeration getWords(){ return wordList.elements(); }

    // add word to wordList
    public void addWord(Word word){
        wordList.add(word);
        view.refresh();
    }

    // check given guess with the list of words
    // marks the word as discovered if correct for the view to update
    public void guess(String guess, int position){
        String word = ((Word) wordList.elementAt(position)).getWord();
        if(word.compareTo(guess) == 0){
            System.out.println(guess + " is correct.");
            ((Word) wordList.elementAt(position)).setDiscovered();
        } else {
            System.out.println(guess + " is incorrect.");
        }
    }

    // offset[0] = which letter on new word
    // offset[1] = which letter on old word
    public void checkSpot(String newWord, String clue){

        int[] offset = new int[2];
        //context = Context.getInstance();
        Point referencePoint;
        Word tempWord;
        offset[0] = 0;
        offset[1] = 0;

        for(Word words : wordList){
            String check = words.getWord();
            for(int i = 0; i < newWord.length(); i++){                                                                  // i = letter in new word
                for(int j = 0; j < check.length(); j++){                                                                // j = letter in current word from wordList
                    if(newWord.charAt(i) == check.charAt(j) && words.isSpotAvailable(j)){                               // if available and letter is the same
                        offset[0] = i;
                        offset[1] = j;
                        referencePoint = words.getCharPosition(offset[1]);
                        int i1 = (offset[0] + newWord.length()) * context.getBoxSize();
                        if (words.getDirection()) {
                            if (((referencePoint.y + i1) < (context.getGameSize()[0]-context.getBoxSize()) &&
                                    (referencePoint.y - (offset[0] * context.getBoxSize())) > context.getBoxSize() )){  // check bounds for within game screen
                                tempWord = new Word(newWord, clue, referencePoint.x,
                                        referencePoint.y - (offset[0] * context.getBoxSize()), false, number);
                                if(checkWords(tempWord, referencePoint)){
                                    tempWord.setConnected(offset[0]);                                                   // set newWord position as connected / unavailable
                                    words.setConnected(offset[1]);                                                      // set connected word as connected / unavailable
                                    number++;                                                                           // number for clue / word assignment
                                    try{
                                        addWord(tempWord);
                                    } catch (NullPointerException e){}
                                    return;
                                }
                            }
                        } else {
                            if (((referencePoint.x + i1) < (context.getGameSize()[1]-context.getBoxSize()) &&
                                    (referencePoint.x - (offset[0] * context.getBoxSize())) > context.getBoxSize() )){  // check bounds for within game screen
                                tempWord = new Word(newWord, clue, referencePoint.x - (offset[0] * context.getBoxSize()),
                                        referencePoint.y, true, number);
                                if(checkWords(tempWord,referencePoint)){
                                    tempWord.setConnected(offset[0]);                                                   // set newWord position as connected / unavailable
                                    words.setConnected(offset[1]);                                                      // set connected word as connected / unavailable
                                    number++;                                                                           // number for clue / word assignment
                                    try{
                                        addWord(tempWord);
                                    } catch (NullPointerException e){}
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    // i = new word letter
    // j = old word letter
    public boolean checkWords(Word check, Point overlap) {
        boolean available = true;
        Point positionOld, positionNew;
        char charOld, charNew;

        // true = horizontal
        // false = vertical
        if (check.getDirection()) {
            for (Word words : wordList) {
                //if words are parallel and far apart, skip checking the word
                if(words.getDirection() && Math.abs(words.getCharPosition(0).x - check.getCharPosition(0).x ) <= context.getBoxSize()*2)
                    continue;
                // if words is perpendicular but doesn't share a height with checked word, skip checking the word
                if(!words.getDirection() && check.getCharPosition(0).x - (context.getBoxSize()*2) >= words.getCharPosition(0).x)
                    continue;
                if(!words.getDirection() && check.getCharPosition(check.getWord().length()-1).x + (context.getBoxSize()*2) <= words.getCharPosition(0).x)
                    continue;
                for (int i = 0; i < check.getWord().length(); i++) {
                    positionNew = check.getCharPosition(i);
                    for (int j = 0; j < words.getWord().length(); j++) {
                        positionOld = words.getCharPosition(j);
                        if (!(overlap.x == positionNew.x && overlap.y == positionNew.y)) {                              // skip over the overlapped space
                            if (positionNew.x == positionOld.x && positionNew.y - context.getBoxSize() == positionOld.y ) {                // if there is a letter above it
                                available = false;                                                                      // mark space unavailable for this word
                                break;
                            }
                            if (positionNew.x == positionOld.x && positionNew.y + context.getBoxSize() == (positionOld.y )) {              // if there is a letter below it
                                available = false;                                                                      // mark space unavailable for this word
                                break;
                            }
                        }
                    }

                    positionNew = check.getCharPosition(0);
                    for (int j = 0; j < words.getWord().length(); j++) {
                        positionOld = words.getCharPosition(j);
                        if (positionNew.x - context.getBoxSize() == positionOld.x && positionNew.y == positionOld.y) {                    // if the space before the first letter is taken
                            available = false;                                                                          // mark it as unavailable for this word
                            break;
                        }
                    }

                    positionNew = check.getCharPosition(check.getWord().length() - 1);
                    for (int j = 0; j < words.getWord().length(); j++) {
                        positionOld = words.getCharPosition(j);
                        if (positionNew.x + context.getBoxSize() == positionOld.x && positionNew.y == positionOld.y) {                    // if the space after the last letter is taken
                            available = false;                                                                          // mark it as unavailable for this word
                            break;
                        }
                    }

                }
            }
        } else {
            for (Word words : wordList) {
                //if words is parallel to check and far apart, skip checking the word
                if(!words.getDirection() && Math.abs(words.getCharPosition(0).y - check.getCharPosition(0).y) <= (context.getBoxSize()*2))
                    continue;
                // if words is perpendicular but doesn't share a height with check, skip checking the word
                if(words.getDirection() && check.getCharPosition(0).y - (context.getBoxSize()*2) >= words.getCharPosition(0).y)
                    continue;
                if(words.getDirection() && check.getCharPosition(check.getWord().length()-1).y + (context.getBoxSize()*2) < words.getCharPosition(0).y)
                    continue;

                for (int i = 0; i < check.getWord().length(); i++) {
                    positionNew = check.getCharPosition(i);
                    for (int j = 0; j < words.getWord().length(); j++) {
                        positionOld = words.getCharPosition(j);
                        if (!(overlap.x == positionNew.x && overlap.y == positionNew.y)) {                              // skip over the overlapped space
                            if (positionNew.x - context.getBoxSize() == (positionOld.x) && positionNew.y == positionOld.y) {              // if there is a letter to the left
                                available = false;                                                                      // mark position as unavailable for this word
                                break;
                            }
                            if (positionNew.x + context.getBoxSize() == (positionOld.x) && positionNew.y == positionOld.y) {              // if there is a letter to the right
                                available = false;                                                                      // mark position as unavailable for this word
                                break;
                            }
                        }
                    }

                    positionNew = check.getCharPosition(0);
                    for (int j = 0; j < words.getWord().length(); j++) {
                        positionOld = words.getCharPosition(j);
                        if (positionNew.x == positionOld.x && positionNew.y - context.getBoxSize() == positionOld.y) {                    // if the space before the first letter is taken
                            available = false;                                                                          // mark it as unavailable for this word
                            break;
                        }
                    }

                    positionNew = check.getCharPosition(check.getWord().length() - 1);
                    for (int j = 0; j < words.getWord().length(); j++) {
                        positionOld = words.getCharPosition(j);
                        if (positionNew.x == positionOld.x && positionNew.y + context.getBoxSize() == positionOld.y) {                    // if the space after the last letter is taken
                            available = false;                                                                          // mark it as unavailable for this word
                            break;
                        }
                    }
                }
            }
        }

        // check for letters sharing positions
        for (Word words : wordList) {
            for (int i = 0; i < check.getWord().length(); i++) {
                positionNew = check.getCharPosition(i);
                for (int j = 0; j < words.getWord().length(); j++) {
                    positionOld = words.getCharPosition(j);
                    if ((positionNew.x == positionOld.x && positionNew.y == positionOld.y)) {
                        charOld = words.getCharAt(j);
                        charNew = check.getCharAt(i);
                        if (!(charOld == charNew)) {
                            available = false;
                            break;
                        }
                    }
                }
            }
        }
        return available;
    }


    public boolean isGameDone(){
        boolean done = true;
        for(Word word: wordList) {
            if (!word.isDiscovered())
                done = false;
        }
        return done;
    }

    // start algorithm for adding words to model
    // if model is empty start with 80,80, horizontal
    public void attemptAddWord(String word, String clue){
        context = Context.getInstance();
        Word tempWord;
        if (wordList.isEmpty()) {                                                                                       // if the model is empty
            tempWord = new Word(word, clue, 80, 80, true, number);                                        // create a word at 80, 80, horizontal
            number++;                                                                                                   // number for clue / word assignment
            try{
                addWord(tempWord);
            } catch (NullPointerException e){}
        } else {
            checkSpot(word,clue);                                                                                       // check availability / attempt adding
        }
    }
}
