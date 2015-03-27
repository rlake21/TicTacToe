
package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;

public class Game {

    private Board daBoard;
    private int cpuDifficulty;
    public final static char PLAYER_ONE = 'X';
    public final static char PLAYER_TWO = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public Game(){
        daBoard = new Board();
        cpuDifficulty = 0;
        rand = new Random();
    }

    public char getHumanChar(){
        return PLAYER_ONE;
    }
    public char getPlayerOneChar(){
        return PLAYER_ONE;
    }
    public char getPlayerTwoChar(){
        return PLAYER_TWO;
    }
    public char getCompChar(){
        return PLAYER_TWO;
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

    public void clearBoard(){
        for (int i =0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                daBoard.setCell(i,j,EMPTY);
            }
        }
    }

    public int[] computerMove(){
        int decidedMove[] = new int[2];
        if( cpuDifficulty == 0 ) { //make a random valid move
            int rowTry = Math.abs(rand.nextInt() % 3);
            int colTry = Math.abs(rand.nextInt() % 3);
            boolean firstTry = makeMove(rowTry, colTry, PLAYER_TWO);
            if (!firstTry) {
                do {
                    rowTry = Math.abs(rand.nextInt() % 3);
                    colTry = Math.abs(rand.nextInt() % 3);
                    firstTry = makeMove(rowTry, colTry, PLAYER_TWO);
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

    public int checkForWinner(int turnNumber) {
        char winnerChar = EMPTY;
        if (daBoard.getCell(1, 1) != EMPTY) {
            char checkMaster = daBoard.getCell(1, 1);
            char checkVert0 = daBoard.getCell(0, 1);
            char checkVert1 = daBoard.getCell(2, 1);
            char checkHor0 = daBoard.getCell(1, 0);
            char checkHor1 = daBoard.getCell(1, 2);
            char checkDiag00 = daBoard.getCell(0, 2);
            char checkDiag01 = daBoard.getCell(2, 0);
            char checkDiag10 = daBoard.getCell(2, 2);
            char checkDiag11 = daBoard.getCell(0, 0);
            if ((checkMaster == checkVert0 && checkVert0 == checkVert1) ||
                (checkMaster == checkHor0 && checkHor0 == checkHor1) ||
                (checkMaster == checkDiag00 && checkDiag00 == checkDiag01) ||
                (checkMaster == checkDiag10 && checkDiag10 == checkDiag11)) {
                winnerChar = checkMaster;
            }
        }

        if (daBoard.getCell(0, 0) != EMPTY) {
            char checkMaster = daBoard.getCell(0, 0);
            char checkRight0 = daBoard.getCell(0, 1);
            char checkRight1 = daBoard.getCell(0, 2);
            char checkDown0 = daBoard.getCell(1, 0);
            char checkDown1 = daBoard.getCell(2, 0);
            if ((checkMaster == checkRight0 && checkRight0 == checkRight1) ||
                (checkMaster == checkDown0 && checkDown0 == checkDown1)) {
                winnerChar = checkMaster;
            }
        }

        if (daBoard.getCell(2, 2) != EMPTY) {
            char checkMaster = daBoard.getCell(2, 2);
            char checkUp0 = daBoard.getCell(1, 2);
            char checkUp1 = daBoard.getCell(0, 2);
            char checkLeft0 = daBoard.getCell(2, 1);
            char checkLeft1 = daBoard.getCell(2, 0);
            if ((checkMaster == checkUp0 && checkUp0 == checkUp1) ||
                (checkMaster == checkLeft0 && checkLeft0 == checkLeft1)) {
                winnerChar = checkMaster;
            }

        }

        /*
        return 0 if no win and no tie (still empty space on un-won board)
        return 1 if tie
        return 2 if human win
        return 3 if computer win
*/
        if( winnerChar == PLAYER_ONE) {
            return 2;
        }

        else if( winnerChar == PLAYER_TWO) {
            return 3;
        }

        else if( winnerChar == EMPTY && turnNumber > 8 ) {
            return 1;
        }

        else {
            return 0;
        }

    }


}











