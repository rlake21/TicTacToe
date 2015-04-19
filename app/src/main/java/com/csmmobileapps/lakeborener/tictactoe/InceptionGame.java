package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;
import android.util.Log;

/**
 * Created by ryanlake21 on 4/18/15.
 */


public class InceptionGame {

    private Board[][] inceptionBoard;
    private Board inceptionOuterGame;
    private int cpuDifficulty;
    public final static char PLAYER_ONE = 'X';
    public final static char PLAYER_TWO = 'O';
    public final static char EMPTY = ' ';
    private Random rand;

    public InceptionGame(int difficulty){
        clearBoard();
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
        int[] cellToTry = tileToCellTuple(tile);
        String decidedMove = Integer.toString(cellToTry[0])+" "+Integer.toString(cellToTry[1]);
        Log.e("makeMoveDebug",decidedMove);
        if( frameToBoard(frame).getCell(cellToTry[0], cellToTry[1]) == EMPTY ) { //valid move
            frameToBoard(frame).setCell(cellToTry[0], cellToTry[1], state);
            return true;
        }
        else {
            return false;
        }
    }
    public void clearBoard(){
        inceptionBoard = new Board[3][3];
        inceptionOuterGame = new Board();

        for (int i = 0; i < 3; i++){
            for (int j = 0; j< 3; j++){
                inceptionBoard[i][j] = new Board();
            }
        }
    }
    public int[] computerMove(int validFrame){
        int[] move = new int[2];
        move[0] = validFrame;

        //CPU difficulty 0 is default for now...TODO: make it super smart
        int tile = rand.nextInt()%9;
        boolean firstTry = makeMove(validFrame, tile, PLAYER_TWO);
        if (!firstTry) {
            do {
                tile = rand.nextInt()%9;
                firstTry = makeMove(validFrame, tile, PLAYER_TWO);
            } while (!firstTry);
        }

        move[1] = tile;
        return move;
    }

    public int checkForWinner(Board outerGame, int turnNumber){//for full game. Sets this class' inceptionOuterGame to
                                                              // the passed outer game value and calls checkForFrameWinner on it.
        inceptionOuterGame = outerGame;
        return checkForFrameWinner(10, turnNumber);
    }

    public int checkForFrameWinner(int frame, int frameTurnCount){ //for each frame, when called with
                                                                  // checkFrameWinner(10, int totalTurnCount) it finds winner of the total game.
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
        return 3 if computer/P2 win
        */
        if( winnerChar == PLAYER_ONE) {
            return 2;
        }

        else if( winnerChar == PLAYER_TWO) {
            return 3;
        }

        else if( frame < 10 && winnerChar == EMPTY && frameTurnCount > 8 ) { //logic for individual frames of the game tie
            return 1;
        }

        else if( frame == 10 && winnerChar == EMPTY && frameTurnCount > 80 ) { //logic for the "master" outer frame tie
            return 1;
        }

        else {
            return 0;
        }

    }

    public Board frameToBoard(int frame){
        switch( frame ) {
            case 0:
                return inceptionBoard[0][0];
            case 1:
                return inceptionBoard[0][1];
            case 2:
                return inceptionBoard[0][2];
            case 3:
                return inceptionBoard[1][0];
            case 4:
                return inceptionBoard[1][1];
            case 5:
                return inceptionBoard[1][2];
            case 6:
                return inceptionBoard[2][0];
            case 7:
                return inceptionBoard[2][1];
            case 8:
                return inceptionBoard[2][2];
            case 10:
                return inceptionOuterGame;
            default:
                return null;
            /*
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
            case 10:
                return inceptionOuterGame;
            default:
                return null;*/
        }
    }

    public int boardToFrame(int row, int col) {
        return row * 3 + col;
    }

    public int[] tileToCellTuple(int tile) {
        int[] cellTuple = new int[2];
        switch( tile ) {
            case 0:
                cellTuple[0] = 0;
                cellTuple[1] = 0;
                break;
            case 1:
                cellTuple[0] = 0;
                cellTuple[1] = 1;
                break;
            case 2:
                cellTuple[0] = 0;
                cellTuple[1] = 2;
                break;
            case 3:
                cellTuple[0] = 1;
                cellTuple[1] = 0;
                break;
            case 4:
                cellTuple[0] = 1;
                cellTuple[1] = 1;
                break;
            case 5:
                cellTuple[0] = 1;
                cellTuple[1] = 2;
                break;
            case 6:
                cellTuple[0] = 2;
                cellTuple[1] = 0;
                break;
            case 7:
                cellTuple[0] = 2;
                cellTuple[1] = 1;
                break;
            case 8:
                cellTuple[0] = 2;
                cellTuple[1] = 2;
                break;
            default:
                return null;
        /*
            case 1:
                cellTuple[0] = 0;
                cellTuple[1] = 0;
                break;
            case 2:
                cellTuple[0] = 0;
                cellTuple[1] = 1;
                break;
            case 3:
                cellTuple[0] = 0;
                cellTuple[1] = 2;
                break;
            case 4:
                cellTuple[0] = 1;
                cellTuple[1] = 0;
                break;
            case 5:
                cellTuple[0] = 1;
                cellTuple[1] = 1;
                break;
            case 6:
                cellTuple[0] = 1;
                cellTuple[1] = 2;
                break;
            case 7:
                cellTuple[0] = 2;
                cellTuple[1] = 0;
                break;
            case 8:
                cellTuple[0] = 2;
                cellTuple[1] = 1;
                break;
            case 9:
                cellTuple[0] = 2;
                cellTuple[1] = 2;
                break;
            default:
                return null;*/
        }
        return cellTuple;
    }

    public int cellTupleToTile(int[] cellTuple ) {
        return cellTuple[0] * 3 + cellTuple[1];
    }

    public void resetTile(int frame, int tile){
        int[] cellTuple = tileToCellTuple(tile);
        frameToBoard(frame).setCell(cellTuple[0], cellTuple[1], EMPTY);
    }

}
