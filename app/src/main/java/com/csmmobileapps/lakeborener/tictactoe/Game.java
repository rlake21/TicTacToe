package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;

public class Game {

    private char daBoard[];
    private final static int SIZE = 9;
    public final static char HUMAN = 'X';
    public final static char COMPUTER = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public static int getSIZE(){
        return SIZE;
    }

    public Game(){
        daBoard = new char[SIZE];

        for (int i = 0; i < SIZE; i++){
            daBoard[i] = EMPTY;
        }

        rand = new Random();
    }


    public void setMove(int location, char player){
        daBoard[location] = player;
    }


    public void clearMoves(){
        for (int i = 0; i < SIZE; i++){
            daBoard[i] = EMPTY;
        }
    }


    public int computerMove(){

        int decidedMove = 0;

        for (int i = 0; i < SIZE; i++){
            if (daBoard[i] != HUMAN && daBoard[i] != COMPUTER){
                char temp = daBoard[i];
                daBoard[i] = COMPUTER;
                //if (willWin()){

                //}
            }

        }

        return decidedMove;
    }
}











