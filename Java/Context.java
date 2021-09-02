package com.company;

import java.awt.*;

public class Context {
    private Word word;
    private Graphics graphics;
    private static Context context;
    private static final int BOXSIZE = 25;
    private static final int[] GAMESIZE = new int[]{800,800};

    protected Context(){}

    public static Context getInstance(){
        if (context == null){
            context = new Context();
        }
        return context;
    }

    public int getBoxSize(){
        return BOXSIZE;
    }

    public int[] getGameSize(){
        return GAMESIZE;
    }

    public void setGraphics(Graphics graphics){this.graphics = graphics;}

    public void drawBox(Point start){
        graphics.fillRect(start.x ,start.y ,BOXSIZE-5,BOXSIZE-5);
    }

    public void drawNumber(Point start, int i){
        graphics.drawString(String.valueOf(i), (int) (start.x+(BOXSIZE*0.3)), (int) (start.y+(BOXSIZE*0.6)));
    }

    public void drawLetter(Point start,char letter){
        graphics.setColor(Color.blue);
        graphics.drawString(String.valueOf(letter),(int) (start.x+(BOXSIZE*0.3)),(int) (start.y+(BOXSIZE*0.6)));
        graphics.setColor(Color.WHITE);
    }

    public void drawClue(Point start,String clue, int number){
        graphics.setColor(Color.white);
        graphics.drawString(String.valueOf(number), start.x ,start.y);                                                  // write number of clue
        graphics.drawString(clue,(int) (start.x+(BOXSIZE*0.6)),start.y);                                                // write clue
        graphics.setColor(Color.white);
    }

}
