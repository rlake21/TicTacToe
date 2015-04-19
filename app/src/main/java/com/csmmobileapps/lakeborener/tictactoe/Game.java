
package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;

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
        char topLeft = daBoard.getCell(0,0);
        char topCenter = daBoard.getCell(0,1);
        char topRight = daBoard.getCell(0,2);
        char midLeft = daBoard.getCell(1,0);
        char midCenter = daBoard.getCell(1,1);
        char midRight = daBoard.getCell(1,2);
        char bottomLeft = daBoard.getCell(2,0);
        char bottomCenter = daBoard.getCell(2,1);
        char bottomRight = daBoard.getCell(2,2);

        boolean markBook[] = new boolean[8];
        for( boolean thing : markBook ) thing = false;

        int markCount = 0;

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

        else if( cpuDifficulty == 1 ) { //cpu mid difficulty attempts to block anything it can but can be outsmarted by starting with a corner

            //check horizontal risks
            if( (topLeft == PLAYER_ONE && topRight == PLAYER_ONE && topCenter == EMPTY) ||
                (topCenter == PLAYER_ONE && topRight == PLAYER_ONE && topLeft == EMPTY) ||
                (topLeft == PLAYER_ONE && topCenter == PLAYER_ONE && topRight == EMPTY)   ) {
                markBook[0] = true;
                markCount++;
            }
            if( (midLeft == PLAYER_ONE && midRight == PLAYER_ONE && midCenter == EMPTY) ||
                (midCenter == PLAYER_ONE && midRight == PLAYER_ONE && midLeft == EMPTY) ||
                (midLeft == PLAYER_ONE && midCenter == PLAYER_ONE && midRight == EMPTY)   ) {
                markBook[1] = true;
                markCount++;
            }
            if( (bottomLeft == PLAYER_ONE && bottomRight == PLAYER_ONE && bottomCenter == EMPTY) ||
                (bottomCenter == PLAYER_ONE && bottomRight == PLAYER_ONE && bottomLeft == EMPTY) ||
                (bottomLeft == PLAYER_ONE && bottomCenter == PLAYER_ONE && bottomRight == EMPTY)   ) {
                markBook[2] = true;
                markCount++;
            }
            //check vertical risks
            if( (topLeft == PLAYER_ONE && bottomLeft == PLAYER_ONE && midLeft == EMPTY) ||
                (topLeft == PLAYER_ONE && midLeft == PLAYER_ONE && bottomLeft == EMPTY) ||
                (bottomLeft == PLAYER_ONE && midLeft == PLAYER_ONE && topLeft == EMPTY)   ) {
                markBook[3] = true;
                markCount++;
            }
            if( (topCenter == PLAYER_ONE && bottomCenter == PLAYER_ONE && midCenter == EMPTY) ||
                (topCenter == PLAYER_ONE && midCenter == PLAYER_ONE && bottomCenter == EMPTY) ||
                (bottomCenter == PLAYER_ONE && midCenter == PLAYER_ONE && topCenter == EMPTY)   ) {
                markBook[4] = true;
                markCount++;
            }
            if( (topRight == PLAYER_ONE && bottomRight == PLAYER_ONE && midRight == EMPTY) ||
                (topRight == PLAYER_ONE && midRight == PLAYER_ONE && bottomRight == EMPTY) ||
                (bottomRight == PLAYER_ONE && midRight == PLAYER_ONE && topRight == EMPTY)   ) {
                markBook[5] = true;
                markCount++;
            }
            //check diagonal risks
            if( (topLeft == PLAYER_ONE && bottomRight == PLAYER_ONE && midCenter == EMPTY) ||
                (topLeft == PLAYER_ONE && midCenter == PLAYER_ONE && bottomRight == EMPTY) ||
                (bottomRight == PLAYER_ONE && midCenter == PLAYER_ONE && topLeft == EMPTY)   ) {
                markBook[6] = true;
                markCount++;
            }
            if( (topRight == PLAYER_ONE && bottomLeft == PLAYER_ONE && midCenter == EMPTY) ||
                (topRight == PLAYER_ONE && midCenter == PLAYER_ONE && bottomLeft == EMPTY) ||
                (bottomLeft == PLAYER_ONE && midCenter == PLAYER_ONE && topRight == EMPTY)   ) {
                markBook[7] = true;
                markCount++;
            }
            //process information gained
            boolean valid = false;
            int rowTry = Math.abs(rand.nextInt() % 3);
            int colTry = Math.abs(rand.nextInt() % 3);;
            if( markCount > 1 ) { //pick one randomly
                while( !valid ) {
                    int firstRand = Math.abs(rand.nextInt() % 8);
                    if (markBook[firstRand]) {
                        rowTry = Math.abs(rand.nextInt() % 3);
                        colTry = Math.abs(rand.nextInt() % 3);
                        boolean firstTry = makeMove(rowTry, colTry, PLAYER_TWO);
                        if (!firstTry) {
                            do {
                                rowTry = Math.abs(rand.nextInt() % 3);
                                colTry = Math.abs(rand.nextInt() % 3);
                                firstTry = makeMove(rowTry, colTry, PLAYER_TWO);
                            } while (!firstTry);
                        }
                        valid = true;
                    }
                }
                return daBoard.getCellTuple(rowTry, colTry);
            }

            else if( markCount == 1 ) { //find the one best move
                int count = 0;
                for( boolean theAnswer : markBook ) {
                    if( theAnswer ) {
                        switch (count) {
                            case 0:
                                if( makeMove(0, 0, PLAYER_TWO) ) return daBoard.getCellTuple(0, 0);
                                if( makeMove(0, 1, PLAYER_TWO) ) return daBoard.getCellTuple(0, 1);
                                if( makeMove(0, 2, PLAYER_TWO) ) return daBoard.getCellTuple(0, 2);
                            case 1:
                                if( makeMove(1, 0, PLAYER_TWO) ) return daBoard.getCellTuple(1, 0);
                                if( makeMove(1, 1, PLAYER_TWO) ) return daBoard.getCellTuple(1, 1);
                                if( makeMove(1, 2, PLAYER_TWO) ) return daBoard.getCellTuple(1, 2);
                            case 2:
                                if( makeMove(2, 0, PLAYER_TWO) ) return daBoard.getCellTuple(2, 0);
                                if( makeMove(2, 1, PLAYER_TWO) ) return daBoard.getCellTuple(2, 1);
                                if( makeMove(2, 2, PLAYER_TWO) ) return daBoard.getCellTuple(2, 2);
                            case 3:
                                if( makeMove(0, 0, PLAYER_TWO) ) return daBoard.getCellTuple(0, 0);
                                if( makeMove(1, 0, PLAYER_TWO) ) return daBoard.getCellTuple(1, 0);
                                if( makeMove(2, 0, PLAYER_TWO) ) return daBoard.getCellTuple(2, 0);
                            case 4:
                                if( makeMove(0, 1, PLAYER_TWO) ) return daBoard.getCellTuple(0, 1);
                                if( makeMove(1, 1, PLAYER_TWO) ) return daBoard.getCellTuple(1, 1);
                                if( makeMove(2, 1, PLAYER_TWO) ) return daBoard.getCellTuple(2, 1);
                            case 5:
                                if( makeMove(0, 2, PLAYER_TWO) ) return daBoard.getCellTuple(0, 2);
                                if( makeMove(1, 2, PLAYER_TWO) ) return daBoard.getCellTuple(1, 2);
                                if( makeMove(2, 2, PLAYER_TWO) ) return daBoard.getCellTuple(2, 2);
                            case 6:
                                if( makeMove(0, 0, PLAYER_TWO) ) return daBoard.getCellTuple(0, 0);
                                if( makeMove(1, 1, PLAYER_TWO) ) return daBoard.getCellTuple(1, 1);
                                if( makeMove(2, 2, PLAYER_TWO) ) return daBoard.getCellTuple(2, 2);
                            case 7:
                                if( makeMove(0, 2, PLAYER_TWO) ) return daBoard.getCellTuple(0, 2);
                                if( makeMove(1, 1, PLAYER_TWO) ) return daBoard.getCellTuple(1, 1);
                                if( makeMove(2, 0, PLAYER_TWO) ) return daBoard.getCellTuple(2, 0);
                        }
                    }
                    count++;
                }
            }

            else if( markCount == 0 ) { //pick random
                rowTry = Math.abs(rand.nextInt() % 3);
                colTry = Math.abs(rand.nextInt() % 3);
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

