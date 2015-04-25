
package com.csmmobileapps.lakeborener.tictactoe;

import android.util.Log;

import java.util.Random;
import java.util.Stack;

public class Game {

    private Board daBoard;
    private int cpuDifficulty;
    public final static char PLAYER_ONE = 'X';
    public final static char PLAYER_TWO = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public Game(int difficulty){
        daBoard = new Board();
        cpuDifficulty = difficulty;
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
    public Board getBoard(){return daBoard;}

    public boolean makeMove(int row, int col, char state) {
        if( daBoard.getCell(row, col) == EMPTY ) { //valid move, change state of the cell
            daBoard.setCell(row, col, state);
            return true;
        } else { //invalid move
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
        Board workBoard = daBoard;

        Stack<Integer> possMoves = new Stack<Integer>();
        Stack<Integer> winMoves;
        Stack<Integer> blockMoves;
        int movesToTry;
        int turnNumber = 1;
        //find possible moves
        for( int i = 0; i < 3; i++ ) {
            for( int k = 0; k < 3; k++ ) {
                if( workBoard.getCell(i, k) == EMPTY ) {
                    possMoves.push(new Integer(k));
                    possMoves.push(new Integer(i));
                }
            }
        }
        movesToTry = possMoves.size() / 2;
        if( movesToTry == 1 ) { //last move, need turn number to be 9
            turnNumber = 9;
        }

        int[] decidedCell;
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
            decidedCell = daBoard.getCellTuple(rowTry, colTry);

            return decidedCell;
        }

        else if( cpuDifficulty == 1 ) { //cpu mid difficulty attempts to win, then block anything it can.
            //try moves for win
            winMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = ((Integer) winMoves.pop()).intValue();
                int tempCol = ((Integer) winMoves.pop()).intValue();
                workBoard.setCell(tempRow, tempCol, PLAYER_TWO);
                if( checkForWinner(turnNumber) == 3 ) { //this move will cause a win, make it
                    return workBoard.getCellTuple( tempRow, tempCol );
                }
                else { //reset the cell for next try
                    workBoard.setCell(tempRow, tempCol, EMPTY);
                }
            }
            //try moves for simple block
            blockMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = (Integer) blockMoves.pop();
                int tempCol = (Integer) blockMoves.pop();
                workBoard.setCell(tempRow, tempCol, PLAYER_ONE);
                if( checkForWinner(turnNumber) == 2 ) { //this move will cause a win for the human, make it
                    workBoard.setCell(tempRow, tempCol, PLAYER_TWO); //need to set cell back to player_two
                    return workBoard.getCellTuple( tempRow, tempCol );
                }
                else { //reset the cell for next try
                    workBoard.setCell(tempRow, tempCol, EMPTY);
                }
            }
            //no block or win... pick random
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
            decidedCell = daBoard.getCellTuple(rowTry, colTry);

            return decidedCell;

        }

        else if( cpuDifficulty == 2) {
            if( movesToTry == 6 ) { //need to block fork if one is starting
                if( (workBoard.getCell(0, 0) == PLAYER_ONE && workBoard.getCell(2, 2) == PLAYER_ONE)
                 || (workBoard.getCell(0, 2) == PLAYER_ONE && workBoard.getCell(2, 0) == PLAYER_ONE) ) {
                    turnNumber = 4;
                }
            }

            //try moves for win
            winMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = ((Integer) winMoves.pop()).intValue();
                int tempCol = ((Integer) winMoves.pop()).intValue();
                workBoard.setCell(tempRow, tempCol, PLAYER_TWO);
                if( checkForWinner(turnNumber) == 3 ) { //this move will cause a win, make it
                    return workBoard.getCellTuple( tempRow, tempCol );
                }
                else { //reset the cell for next try
                    workBoard.setCell(tempRow, tempCol, EMPTY);
                }
            }
            //try moves for simple block
            blockMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = (Integer) blockMoves.pop();
                int tempCol = (Integer) blockMoves.pop();
                workBoard.setCell(tempRow, tempCol, PLAYER_ONE);
                if( checkForWinner(turnNumber) == 2 ) { //this move will cause a win for the human, make it
                    workBoard.setCell(tempRow, tempCol, PLAYER_TWO); //need to set cell back to player_two
                    return workBoard.getCellTuple( tempRow, tempCol );
                }
                else { //reset the cell for next try
                    workBoard.setCell(tempRow, tempCol, EMPTY);
                }
            }

            //play the center if possible... skip if making the first move since a first move in the corner maximizes human player error
            if( movesToTry < 9 && workBoard.getCell(1, 1) == EMPTY ) {
                makeMove(1, 1, PLAYER_TWO);
                return workBoard.getCellTuple(1, 1);
            }

            //play an opposite corner
            if( workBoard.getCell(2, 2) == PLAYER_ONE && makeMove(0, 0, PLAYER_TWO) ) {
                return workBoard.getCellTuple(0, 0);
            }
            if( workBoard.getCell(2, 0) == PLAYER_ONE && makeMove(0, 2, PLAYER_TWO) ) {
                return workBoard.getCellTuple(0, 2);
            }
            if( workBoard.getCell(0, 2) == PLAYER_ONE && makeMove(2, 0, PLAYER_TWO) ) {
                return workBoard.getCellTuple(2, 0);
            }
            if( workBoard.getCell(0, 0) == PLAYER_ONE && makeMove(2, 2, PLAYER_TWO) ) {
                return workBoard.getCellTuple(2, 2);
            }

            //play an empty corner. Picks one randomly for a more human feeling CPU
            Cell [] corners = new Cell[4];
            boolean corner0 = false;
            boolean corner1 = false;
            boolean corner2 = false;
            boolean corner3 = false;
            corners[0] = workBoard.getBoard()[0][0];
            corners[1] = workBoard.getBoard()[0][2];
            corners[2] = workBoard.getBoard()[2][0];
            corners[3] = workBoard.getBoard()[2][2];

            while( (!corner0 || !corner1 || !corner2 || !corner3) && turnNumber != 4 ) {
                int tempRand = rand.nextInt() % 4;
                if( tempRand == 0 ) {
                    corner0 = true;
                    if( makeMove(0, 0, PLAYER_TWO) ) {
                        return workBoard.getCellTuple(0, 0);
                    }
                }
                else if( tempRand == 1 ) {
                    corner1 = true;
                    if( makeMove(0, 2, PLAYER_TWO) ) {
                        return workBoard.getCellTuple(0, 2);
                    }
                }
                else if( tempRand == 2 ) {
                    corner2 = true;
                    if( makeMove(2, 0, PLAYER_TWO) ) {
                        return workBoard.getCellTuple(2, 0);
                    }
                }
                else if( tempRand == 3 ) {
                    corner3 = true;
                    if( makeMove(2, 2, PLAYER_TWO) ) {
                        return workBoard.getCellTuple(2, 2);
                    }
                }
            }

            //play an empty side... prioritize  east and west over north and south
            if( makeMove(1, 0, PLAYER_TWO) ) {
                return workBoard.getCellTuple(1, 0);
            }
            if( makeMove(1, 2, PLAYER_TWO) ) {
                return workBoard.getCellTuple(1, 2);
            }
            if( makeMove(0, 1, PLAYER_TWO) ) {
                return workBoard.getCellTuple(0, 1);
            }
            if( makeMove(2, 1, PLAYER_TWO) ) {
                return workBoard.getCellTuple(2, 1);
            }
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

