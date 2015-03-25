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

    public char getHumanChar(){
        return HUMAN;
    }
    public char getCompChar(){
        return COMPUTER;
    }
    public char getEmptyChar(){
        return EMPTY;
    }

    public boolean makeMove(int row, int col, char state) {
        if( daBoard.getCell(row, col) == EMPTY ) { //valid move, change state of the cell
            daBoard.setCell(row, col, state);
            return true;
        }

        else { //invalid move,TODO: handle with an error message if human made bad move
            return false;
        }
    }

    public int[] computerMove(){
        int decidedMove[] = new int[2];
        if( cpuDifficulty == 0 ) { //make a random valid move
            int rowTry = rand.nextInt() % 3;
            int colTry = rand.nextInt() % 3;
            boolean firstTry = makeMove(rowTry, colTry, COMPUTER);
            if (!firstTry) {
                do {
                    rowTry = rand.nextInt() % 3;
                    colTry = rand.nextInt() % 3;
                    firstTry = makeMove(rowTry, colTry, COMPUTER);
                } while (!firstTry);
            }
            decidedMove[0] = rowTry;
            decidedMove[1] = colTry;

            return decidedMove;
        }

        else if( cpuDifficulty == 1 ) {

        }

        else if( cpuDifficulty == 2 ) {

        }

        return null;
    }

    public int checkForWinner(){
        /*
        return 0 if no win and no tie (still empty space on un-won board)
        return 1 if tie
        return 2 if human win
        return 3 if computer win

         */
        return 0;
    }


}











