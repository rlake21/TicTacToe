package com.csmmobileapps.lakeborener.tictactoe;

/** Single Board class
 * Created by jess on 3/24/15.
 */
public class Board {
    private Cell[][] theBoard;

    //constructor creates a new board full of empty cells
    public Board() {
        theBoard = new Cell[3][3];
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                theBoard[i][j] = new Cell();
            }
        }
    }

    //get the whole board
    public Cell[][] getBoard() {
        return theBoard;
    }

    public int[] getCellTuple(int row, int col) {
        int[] returnTuple = new int[2];
        returnTuple[0] = row;
        returnTuple[1] = col;
        return returnTuple;
    }

    //or just get a cell
    public char getCell(int row, int col) {
        return theBoard[row][col].getState();
    }

    //set a cell
    public void setCell(int row, int col, char state) {
        theBoard[row][col].setState(state);
    }

    public char getCharAtLoc(int row, int col){
        return theBoard[row][col].getState();
    }
}