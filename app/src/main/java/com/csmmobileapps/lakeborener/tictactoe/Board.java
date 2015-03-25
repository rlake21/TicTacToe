package com.csmmobileapps.lakeborener.tictactoe;

/** Single Board class
 * Created by jess on 3/24/15.
 */
public class Board {
    private Cell[][] theBoard;

    //constructor creates a new board full of empty cells
    public Board() {
        theBoard = new Cell[3][3];
    }

    //get the whole board
    public Cell[][] getBoard() {
        return theBoard;
    }

    //or just get a cell
    public char getCell(int row, int col) {
        return theBoard[row][col].getState();
    }

    //set a cell TODO: update draw when a cell is set
    public void setCell(int row, int col, char state) {
        theBoard[row][col].setState(state);
    }

    public char getCharAtLoc(int row, int col){
        return theBoard[row][col].getState();
    }
}