package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;

public class Game {

    private Board daBoard;
    private int cpuDifficulty;
    public final static char HUMAN = 'X';
    public final static char COMPUTER = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public Game(){
        daBoard = new Board();
        cpuDifficulty = 0;
    }

    public boolean makeMove(int row, int col, char state) {
        if( daBoard.getCell(row, col) == EMPTY ) { //valid move, change state of the cell
            daBoard.setCell(row, col, state);
            return true;
        }

        else { //invalid move,TODO: handle with an error message
            return false;
        }
    }

    public int computerMove(){





        return 0;
    }


}











