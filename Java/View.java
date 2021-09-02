package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class View extends JFrame{

    private Context context;
    private static Model model;
    private JPanel crossPanel;

    public static void setModel(Model model){View.model = model;}

    private class CrossPanel extends JPanel{
        private MouseListener currentMouseListener;

        public CrossPanel() {setLayout(null);}

        public void paintComponent(Graphics g){
            super.paintComponent(g);
            (Context.getInstance()).setGraphics(g);
            g.setColor(Color.WHITE);
            Enumeration enumeration = model.getWords();                                                                 // grab wordList from model
            while(enumeration.hasMoreElements()){
                ((Word) enumeration.nextElement()).render();                                                            // draw boxes for each letter
            }
            enumeration = model.getWords();                                                                             // grab wordList from model
            while(enumeration.hasMoreElements()){
                ((Word) enumeration.nextElement()).renderWord();                                                        // draw letters for discovered words
            }

            g.drawLine(context.getGameSize()[0],0,context.getGameSize()[0],context.getGameSize()[1]);                                                                    //separate clues from words

            enumeration = model.getWords();                                                                             // grab wordList from model
            while(enumeration.hasMoreElements()){
                ((Word) enumeration.nextElement()).renderClue();                                                        // draw clues
            }
        }

        public void addMouseListener(MouseListener newListener) {
            removeMouseListener(currentMouseListener);
            currentMouseListener =  newListener;
            super.addMouseListener(newListener);
        }
    }

    public View(){
        super("Crossword program");
        context = Context.getInstance();
        addWindowListener(new WindowAdapter() {                                                                         // close program on screen close
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        });
        model.setUI(Context.getInstance());
        crossPanel = new CrossPanel();
        crossPanel.setBackground(Color.BLACK);                                                                          // set background to black
        this.setContentPane(crossPanel);

        try{
            this.setSize((context.getGameSize()[0] + 400),context.getGameSize()[1]);
        } catch (NullPointerException e){}
                                                                                    // create size of viewable space
    }

    public void refresh(){ crossPanel.repaint(); }                                                                      // redraw the view
}
