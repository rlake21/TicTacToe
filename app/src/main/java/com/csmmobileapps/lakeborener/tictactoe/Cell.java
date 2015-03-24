package com.csmmobileapps.lakeborener.tictactoe;

/** Single Cell Class
 * Created by jess on 3/24/15.
 */
public class Cell {

    private char theState;

    //constructor creates an empty cell
    public Cell() {
        theState = ' ';
    }

    public char getState() {
        return theState;
    }

    public void setState(char newState) {
        theState = newState;
    }

}
