package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;

/**
 * Created by ryanlake21 on 4/18/15.
 */



//so yea, just a dummy class for now, do what you can in here, once its done i think everything
    //should work...might have to change some stuff for the hint and undo options
public class InceptionGame {

    private int cpuDifficulty;
    public final static char PLAYER_ONE = 'X';
    public final static char PLAYER_TWO = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public InceptionGame(int difficulty){
        cpuDifficulty = difficulty;
        rand = new Random();
    }

    public char getPlayerOneChar(){
        return PLAYER_ONE;
    }
    public char getPlayerTwoChar(){
        return PLAYER_TWO;
    }
    public char getHumanChar(){
        return PLAYER_ONE;
    }
    public char getCompChar(){
        return PLAYER_TWO;
    }
    public char getEmptyChar(){
        return EMPTY;
    }

    public boolean makeMove(int frame, int tile, char state){
        //return true if valid move
        return false;
    }
    public void clearBoard(){}
    public int[] computerMove(int validFrame){
        int[] move = new int[2];     //use makemove to ensure that computer chooses valid move in frame

        int tile = rand.nextInt()%9;


        move[0] = validFrame;
        move[1] = tile;
        return move;
    }
    public int checkForWinner(int turnNumber){//for full game
        //i was also thinking of passing a frameState array which holds the values (X, O or ' ')
        //for each of the frames, it might make it easier to check for win this way, let me know
        return 0;
    }
    public int checkForFrameWinner(int frame, int frameTurnCount){return 0;}//for each frame, just like normal game
    public void resetTile(int frame, int tile){
        //set this tile's state to empty, for undo use
    }

}
