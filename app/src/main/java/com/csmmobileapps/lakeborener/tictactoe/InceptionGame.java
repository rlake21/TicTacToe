package com.csmmobileapps.lakeborener.tictactoe;

import java.util.Random;
import java.util.Stack;

/**
 * Created by ryanlake21 on 4/18/15.
 */


public class InceptionGame {

    private Board[][] inceptionBoard;
    private Board inceptionOuterGame;
    private Board indivFrameCheck;
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
        //get valid moves from the frame
        indivFrameCheck = frameToBoard(validFrame);

        Stack<Integer> possMoves = new Stack<Integer>();
        Stack<Integer> winMoves;
        Stack<Integer> blockMoves;
        int movesToTry;
        int turnNumber = 1;
        //find possible moves
        for( int i = 0; i < 3; i++ ) {
            for( int k = 0; k < 3; k++ ) {
                if( indivFrameCheck.getCell(i, k) == EMPTY ) {
                    possMoves.push(new Integer(k));
                    possMoves.push(new Integer(i));
                }
            }
        }
        movesToTry = possMoves.size() / 2;
        if( movesToTry == 1 ) { //last move, need turn number to be 9
            turnNumber = 9;
        }


        if( cpuDifficulty == 0 ) {
            int tile = Math.abs(rand.nextInt() % 9);
            boolean firstTry = makeMove(validFrame, tile, PLAYER_TWO);
            if (!firstTry) {
                do {
                    tile = Math.abs(rand.nextInt() % 9);
                    firstTry = makeMove(validFrame, tile, PLAYER_TWO);
                } while (!firstTry);
            }
            move[1] = tile;
            return move;
        }

        else if( cpuDifficulty == 1 ) {
            //try moves for win
            winMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = ((Integer) winMoves.pop()).intValue();
                int tempCol = ((Integer) winMoves.pop()).intValue();
                indivFrameCheck.setCell(tempRow, tempCol, PLAYER_TWO);
                if( checkForFrameWinner(9, turnNumber) == 3 ) { //this move will cause a win, make it
                    move[1] = boardToFrame(tempRow, tempCol);
                    return move;
                }
                else { //reset the cell for next try
                    indivFrameCheck.setCell(tempRow, tempCol, EMPTY);
                }
            }
            //try moves for simple block
            blockMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = (Integer) blockMoves.pop();
                int tempCol = (Integer) blockMoves.pop();
                indivFrameCheck.setCell(tempRow, tempCol, PLAYER_ONE);
                if( checkForFrameWinner(9, turnNumber) == 2 ) { //this move will cause a win for the human, make it
                    indivFrameCheck.setCell(tempRow, tempCol, PLAYER_TWO); //need to set cell back to player_two
                    move[1] = boardToFrame(tempRow, tempCol);
                    return move;
                }
                else { //reset the cell for next try
                    indivFrameCheck.setCell(tempRow, tempCol, EMPTY);
                }
            }

            //pick rand otherwise
            int tile = Math.abs(rand.nextInt() % 9);
            boolean firstTry = makeMove(validFrame, tile, PLAYER_TWO);
            if (!firstTry) {
                do {
                    tile = Math.abs(rand.nextInt() % 9);
                    firstTry = makeMove(validFrame, tile, PLAYER_TWO);
                } while (!firstTry);
            }
            move[1] = tile;
            return move;
        }


        else if( cpuDifficulty == 2 ) {
            if( movesToTry == 6 ) { //need to block fork if one is starting
                if( (indivFrameCheck.getCell(0, 0) == PLAYER_ONE && indivFrameCheck.getCell(2, 2) == PLAYER_ONE)
                 || (indivFrameCheck.getCell(0, 2) == PLAYER_ONE && indivFrameCheck.getCell(2, 0) == PLAYER_ONE) ) {
                    turnNumber = 4;
                }
            }

            //try moves for win
            winMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = ((Integer) winMoves.pop()).intValue();
                int tempCol = ((Integer) winMoves.pop()).intValue();
                indivFrameCheck.setCell(tempRow, tempCol, PLAYER_TWO);
                if( checkForFrameWinner(9, turnNumber) == 3 ) { //this move will cause a win, make it
                    move[1] = boardToFrame(tempRow, tempCol);
                    return move;
                }
                else { //reset the cell for next try
                    indivFrameCheck.setCell(tempRow, tempCol, EMPTY);
                }
            }
            //try moves for simple block
            blockMoves = (Stack<Integer>)possMoves.clone();
            for( int i = 0; i < movesToTry; i++ ) {
                int tempRow = (Integer) blockMoves.pop();
                int tempCol = (Integer) blockMoves.pop();
                indivFrameCheck.setCell(tempRow, tempCol, PLAYER_ONE);
                if( checkForFrameWinner(9, turnNumber) == 2 ) { //this move will cause a win for the human, make it
                    indivFrameCheck.setCell(tempRow, tempCol, PLAYER_TWO); //need to set cell back to player_two
                    move[1] = boardToFrame(tempRow, tempCol);
                    return move;
                }
                else { //reset the cell for next try
                    indivFrameCheck.setCell(tempRow, tempCol, EMPTY);
                }
            }

            //play the center if possible... skip if making the first move since a first move in the corner maximizes human player error
            if( movesToTry < 9 && indivFrameCheck.getCell(1, 1) == EMPTY ) {
                makeMove(validFrame, 4, PLAYER_TWO);
                move[1] = 4;
                return move;
            }

            //play an opposite corner
            if( indivFrameCheck.getCell(2, 2) == PLAYER_ONE && makeMove(validFrame, 0, PLAYER_TWO) ) {
                move[1] = 0;
                return move;
            }
            if( indivFrameCheck.getCell(2, 0) == PLAYER_ONE && makeMove(validFrame, 2, PLAYER_TWO) ) {
                move[1] = 2;
                return move;
            }
            if( indivFrameCheck.getCell(0, 2) == PLAYER_ONE && makeMove(validFrame, 6, PLAYER_TWO) ) {
                move[1] = 6;
                return move;
            }
            if( indivFrameCheck.getCell(0, 0) == PLAYER_ONE && makeMove(validFrame, 8, PLAYER_TWO) ) {
                move[1] = 8;
                return move;
            }

            //play an empty corner. Picks one randomly for a more human feeling CPU
            Cell [] corners = new Cell[4];
            boolean corner0 = false;
            boolean corner1 = false;
            boolean corner2 = false;
            boolean corner3 = false;
            corners[0] = indivFrameCheck.getBoard()[0][0];
            corners[1] = indivFrameCheck.getBoard()[0][2];
            corners[2] = indivFrameCheck.getBoard()[2][0];
            corners[3] = indivFrameCheck.getBoard()[2][2];

            while( (!corner0 || !corner1 || !corner2 || !corner3) && turnNumber != 4 ) {
                int tempRand = rand.nextInt() % 4;
                if( tempRand == 0 ) {
                    corner0 = true;
                    if( makeMove(validFrame, 0, PLAYER_TWO) ) {
                        move[1] = 0;
                        return move;
                    }
                }
                else if( tempRand == 1 ) {
                    corner1 = true;
                    if( makeMove(validFrame, 2, PLAYER_TWO) ) {
                        move[1] = 2;
                        return move;
                    }
                }
                else if( tempRand == 2 ) {
                    corner2 = true;
                    if( makeMove(validFrame, 6, PLAYER_TWO) ) {
                        move[1] = 6;
                        return move;
                    }
                }
                else if( tempRand == 3 ) {
                    corner3 = true;
                    if( makeMove(validFrame, 8, PLAYER_TWO) ) {
                        move[1] = 8;
                        return move;
                    }
                }
            }

            //play an empty side... prioritize  east and west over north and south
            if( makeMove(validFrame, 1, PLAYER_TWO) ) {
                move[1] = 1;
                return move;
            }
            if( makeMove(validFrame, 3, PLAYER_TWO) ) {
                move[1] = 3;
                return move;
            }
            if( makeMove(validFrame, 5, PLAYER_TWO) ) {
                move[1] = 5;
                return move;
            }
            if( makeMove(validFrame, 7, PLAYER_TWO) ) {
                move[1] = 7;
                return move;
            }
        }
        return null;
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
            case 9:
                return indivFrameCheck;
            case 10:
                return inceptionOuterGame;
            default:
                return null;
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
