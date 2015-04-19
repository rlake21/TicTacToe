package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;

/**
 * Created by ryanlake21 on 4/18/15.
 */




public class InceptionGame {

    private Board[][] inceptionBoard;
    private int cpuDifficulty;
    public final static char PLAYER_ONE = 'X';
    public final static char PLAYER_TWO = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public InceptionGame(int difficulty){
        inceptionBoard = new Board[3][3];
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

    public int checkForFrameWinner(int frame, int frameTurnCount){ //for each frame, just like normal game
        char winnerChar = EMPTY;
        Board checkingFrame = frameToBoard(frame);
        if (checkingFrame.getCell(1, 1) != EMPTY) {
            char checkMaster = checkingFrame.getCell(1, 1);
            char checkVert0 = checkingFrame.getCell(0, 1);
            char checkVert1 = checkingFrame.getCell(2, 1);
            char checkHor0 = checkingFrame.getCell(1, 0);
            char checkHor1 = checkingFrame.getCell(1, 2);
            char checkDiag00 = checkingFrame.getCell(0, 2);
            char checkDiag01 = checkingFrame.getCell(2, 0);
            char checkDiag10 = checkingFrame.getCell(2, 2);
            char checkDiag11 = checkingFrame.getCell(0, 0);
            if ((checkMaster == checkVert0 && checkVert0 == checkVert1) ||
                (checkMaster == checkHor0 && checkHor0 == checkHor1) ||
                (checkMaster == checkDiag00 && checkDiag00 == checkDiag01) ||
                (checkMaster == checkDiag10 && checkDiag10 == checkDiag11)) {
                winnerChar = checkMaster;
            }
        }

        if (checkingFrame.getCell(0, 0) != EMPTY) {
            char checkMaster = checkingFrame.getCell(0, 0);
            char checkRight0 = checkingFrame.getCell(0, 1);
            char checkRight1 = checkingFrame.getCell(0, 2);
            char checkDown0 = checkingFrame.getCell(1, 0);
            char checkDown1 = checkingFrame.getCell(2, 0);
            if ((checkMaster == checkRight0 && checkRight0 == checkRight1) ||
                (checkMaster == checkDown0 && checkDown0 == checkDown1)) {
                winnerChar = checkMaster;
            }
        }

        if (checkingFrame.getCell(2, 2) != EMPTY) {
            char checkMaster = checkingFrame.getCell(2, 2);
            char checkUp0 = checkingFrame.getCell(1, 2);
            char checkUp1 = checkingFrame.getCell(0, 2);
            char checkLeft0 = checkingFrame.getCell(2, 1);
            char checkLeft1 = checkingFrame.getCell(2, 0);
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

        else if( winnerChar == EMPTY && frameTurnCount > 8 ) {
            return 1;
        }

        else {
            return 0;
        }

    }

    public Board frameToBoard(int frame){
        switch( frame ) {
            case 1:
                return inceptionBoard[0][0];
            case 2:
                return inceptionBoard[0][1];
            case 3:
                return inceptionBoard[0][2];
            case 4:
                return inceptionBoard[1][0];
            case 5:
                return inceptionBoard[1][1];
            case 6:
                return inceptionBoard[1][2];
            case 7:
                return inceptionBoard[2][0];
            case 8:
                return inceptionBoard[2][1];
            case 9:
                return inceptionBoard[2][2];
            default:
                return null;
        }
    }

    public int boardToFrame(int row, int col) {
        return row * 3 + col;
    }

    public void resetTile(int frame, int tile){
        //set this tile's state to empty, for undo use
    }

}
