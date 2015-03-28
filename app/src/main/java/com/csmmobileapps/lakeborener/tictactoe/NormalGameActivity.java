
package com.csmmobileapps.lakeborener.tictactoe;

/** NormalGameActivity
 * Created by ryanlake21 on 3/24/15.
 */


import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.widget.TextView;

public class NormalGameActivity extends ActionBarActivity {

    private Game mGame;
    private Button mButtons[][];
    private Button mUndoButton;
    private Button mHintButton;
    private TextView mTurnInfo;
    private TextView mPlayerOneCount;
    private TextView mPlayerTwoCount;
    private TextView mTieCount;
    private TextView mPlayerOneText;
    private TextView mPlayerTwoText;

    private int mPlayerTwoLastMove[];
    private int mPlayerOneLastMove[];
    private int mDifficulty;
    private int mMoveCounter = 0;
    private int mPlayerOneIncrement = 0;
    private int mPlayerTwoIncrement = 0;
    private int mTieIncrement = 0;
    private boolean mPlayerOneGoesFirst = true;
    private boolean mGameOver = false;
    private boolean mUndoable = false;
    private boolean mHintable = true;
    private boolean mSinglePlayer = false;
    private boolean mPlayerOneTurn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.normal_game_layout);


        //Get gametype and/or difficulty
        boolean mGametype = getIntent().getExtras().getBoolean("singlePlayer");
        mSinglePlayer = mGametype;
        //if (mSinglePlayer){mDifficulty = 2;
        mDifficulty = getIntent().getExtras().getInt("chosenDifficulty");///////////////uncomment this to implement difficulty
        //mDifficulty = 0;


        //Initialize button array and hint/undo buttons
        mButtons = new Button[3][3];
        mButtons[0][0] = (Button) findViewById(R.id.one);
        mButtons[0][1] = (Button) findViewById(R.id.two);
        mButtons[0][2] = (Button) findViewById(R.id.three);
        mButtons[1][0] = (Button) findViewById(R.id.four);
        mButtons[1][1] = (Button) findViewById(R.id.five);
        mButtons[1][2] = (Button) findViewById(R.id.six);
        mButtons[2][0] = (Button) findViewById(R.id.seven);
        mButtons[2][1] = (Button) findViewById(R.id.eight);
        mButtons[2][2] = (Button) findViewById(R.id.nine);
        mUndoButton = (Button) findViewById(R.id.undoButton);
        mHintButton = (Button) findViewById(R.id.hintButton);

        //Initialize text fields
        mTurnInfo = (TextView) findViewById(R.id.TurnInfo);
        mPlayerOneCount = (TextView) findViewById(R.id.humanCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.compCount);
        mTieCount  = (TextView) findViewById(R.id.tieCount);
        mPlayerOneText = (TextView) findViewById(R.id.humanLabel);
        mPlayerTwoText = (TextView) findViewById(R.id.compLabel);

        //Initialize scores
        mPlayerOneCount.setText(Integer.toString(mPlayerOneIncrement));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoIncrement));
        mTieCount.setText(Integer.toString(mTieIncrement));

        //Initialize last move arrays
        mPlayerOneLastMove = new int[2];
        mPlayerTwoLastMove = new int[2];

        //Initialize game
        mGame = new Game(mDifficulty);
        startNewGame(mGametype);
    }

    private void startNewGame(boolean isOnePlayerGame){
        mGame.clearBoard();
        mMoveCounter = 0;
        mGameOver = false;

        //Start button logic
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                mButtons[i][j].setEnabled(true);
                mButtons[i][j].setText(R.string.emptyString);
                mButtons[i][j].setOnClickListener(new ButtonClickListener(i,j,mGame.getEmptyChar()));
            }
        }
        mHintButton.setEnabled(true);
        mHintButton.setOnClickListener(new ButtonClickListener('H', mHintButton.isEnabled()));
        mUndoButton.setEnabled(true);
        mUndoButton.setOnClickListener(new ButtonClickListener('U', mUndoButton.isEnabled()));


        //Start turn logic
        if (mSinglePlayer){

            mPlayerOneText.setText(R.string.human);
            mPlayerTwoText.setText(R.string.computer);

            if (mPlayerOneGoesFirst){
                mTurnInfo.setText(R.string.human_turn);
                mPlayerOneGoesFirst = false;
            } else{
                int[] move = mGame.computerMove();
                mTurnInfo.setText(R.string.comp_turn);
                setMove(move[0], move[1], mGame.getCompChar());
                mPlayerTwoLastMove = move;
                mPlayerOneGoesFirst = true;
                mTurnInfo.setText(R.string.human_turn);
            }

        } else {

            mPlayerOneText.setText(R.string.player_one);
            mPlayerTwoText.setText(R.string.player_two);

            if (mPlayerOneGoesFirst){
                mTurnInfo.setText(R.string.player_one_turn);
                mPlayerOneGoesFirst = false;
            } else{
                mTurnInfo.setText(R.string.player_two_turn);
                mPlayerOneGoesFirst = true;
            }

        }


    }

    private void setMove(int row, int col, char player){
        mMoveCounter++;
        mGame.makeMove(row, col, player);
        if (player == mGame.getEmptyChar()){
            mButtons[row][col].setEnabled(true);
        } else{ mButtons[row][col].setEnabled(false);}

        mButtons[row][col].setText(String.valueOf(player));
        if (player == mGame.getCompChar()){
            mButtons[row][col].setTextColor(Color.BLUE);
            mUndoable = true;
        } else if (player == mGame.getHumanChar()){
            mButtons[row][col].setTextColor(Color.RED);
            mUndoable = true;
        }
    }

    private class ButtonClickListener implements View.OnClickListener{

        int row, col;
        char buttonText;
        boolean enabled;

        public ButtonClickListener(int r, int c, char text){
            this.row = r;
            this.col = c;
            this.buttonText = text;
            this.enabled = false;
        }
        public ButtonClickListener(char text, boolean checkEnabled){
            this.buttonText = text;
            this.enabled = checkEnabled;

        }

        public void onClick(View view){
            if (!mGameOver){
                if (mButtons[row][col].isEnabled() || enabled) {
                    if (buttonText == mGame.getCompChar() || buttonText == mGame.getEmptyChar()
                            || buttonText == mGame.getHumanChar()) {
                        if (!mHintable){ undoHint();}
                        if (mSinglePlayer) { singlePlayerMove(row, col);
                        } else { multiPlayerMove(row,col);}

                    }else if (buttonText == 'H') {
                        if (mHintable) {
                            getHint();
                        } else { mTurnInfo.setText(R.string.noHint);}
                    } else if (buttonText == 'U') {
                        if (mUndoable) {
                            undoMove();
                        } else mTurnInfo.setText(R.string.noUndo);
                    }
                }
            }
        }
    }

    public void getHint(){
        int hintMove[] = mGame.computerMove();
        mButtons[hintMove[0]][hintMove[1]].setText("H");
        mButtons[hintMove[0]][hintMove[1]].setTextColor(Color.GREEN);
        mHintable = false;
    }

    public void undoHint(){
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if (mButtons[i][j].getText() == "H"){
                    mButtons[i][j].setText(R.string.emptyString);
                    mGame.getBoard().getBoard()[i][j].setState(mGame.getEmptyChar());
                }
            }
        }
        mHintable = true;
    }

    public void singlePlayerMove(int row, int col){
        setMove(row, col, mGame.getHumanChar());
        mPlayerOneLastMove[0] = row;
        mPlayerOneLastMove[1] = col;
        int win = mGame.checkForWinner(mMoveCounter);

        //no outcome yet
        if (win == 0) {
            int move[] = mGame.computerMove();
            mTurnInfo.setText(R.string.comp_turn);
            setMove(move[0], move[1], mGame.getCompChar());
            mPlayerTwoLastMove = move;
            win = mGame.checkForWinner(mMoveCounter);
        }

        //still no outcome
        if (win == 0) {
            mTurnInfo.setText(R.string.human_turn);
        } else if (win == 1) { // tie outcome
            mTurnInfo.setText(R.string.outcome_tie);
            mTieIncrement++;
            mTieCount.setText(Integer.toString(mTieIncrement));
            mGameOver = true;
        } else if (win == 2) { // human win outcome
            mTurnInfo.setText(R.string.outcome_human);
            mPlayerOneIncrement++;
            mPlayerOneCount.setText(Integer.toString(mPlayerOneIncrement));
            mGameOver = true;
        } else if (win == 3) { // computer win outcome
            mTurnInfo.setText(R.string.outcome_computer);
            mPlayerTwoIncrement++;
            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoIncrement));
            mGameOver = true;
        }
    }

    public void multiPlayerMove(int row, int col){
        if (mPlayerOneTurn){
            setMove(row, col, mGame.getPlayerOneChar());
            mPlayerOneLastMove[0] = row;
            mPlayerOneLastMove[1] = col;

        } else {
            setMove(row, col, mGame.getPlayerTwoChar());
            mPlayerTwoLastMove[0] = row;
            mPlayerTwoLastMove[1] = col;
        }
        int win = mGame.checkForWinner(mMoveCounter);

        if (win == 0) {
            if (mPlayerOneTurn){
                mTurnInfo.setText(R.string.player_two_turn);
                mPlayerOneTurn = false;
            } else {
                mTurnInfo.setText(R.string.player_one_turn);
                mPlayerOneTurn = true;

            }
        } else if (win == 1) {
            mTurnInfo.setText(R.string.outcome_tie);
            mTieIncrement++;
            mTieCount.setText(Integer.toString(mTieIncrement));
            mGameOver = true;
        } else if (win == 2) {
            mTurnInfo.setText(R.string.outcome_player_one);
            mPlayerOneIncrement++;
            mPlayerOneCount.setText(Integer.toString(mPlayerOneIncrement));
            mGameOver = true;
        } else if (win == 3) {
            mTurnInfo.setText(R.string.outcome_player_two);
            mPlayerTwoIncrement++;
            mPlayerTwoCount.setText(Integer.toString(mPlayerTwoIncrement));
            mGameOver = true;
        }
    }

    public void undoSingleMove(int row, int col){
        mGame.getBoard().getBoard()[row][col].setState(mGame.getEmptyChar());
        mButtons[row][col].setEnabled(true);
        mButtons[row][col].setText(R.string.emptyString);
    }

    public void undoMove(){
        undoHint();
        int rowUndo, colUndo;
        if(mSinglePlayer){
            rowUndo = mPlayerTwoLastMove[0];
            colUndo = mPlayerTwoLastMove[1];
            undoSingleMove(rowUndo, colUndo);
            rowUndo = mPlayerOneLastMove[0];
            colUndo = mPlayerOneLastMove[1];
            undoSingleMove(rowUndo, colUndo);
            mMoveCounter = mMoveCounter - 2;
            mUndoable = false;

        } else {
            if (mPlayerOneTurn) {
                rowUndo = mPlayerTwoLastMove[0];
                colUndo = mPlayerTwoLastMove[1];
                undoSingleMove(rowUndo, colUndo);
                mPlayerOneTurn = false;
                mTurnInfo.setText(R.string.player_two_turn);
            } else {
                rowUndo = mPlayerOneLastMove[0];
                colUndo = mPlayerOneLastMove[1];
                undoSingleMove(rowUndo, colUndo);
                mPlayerOneTurn = true;
                mTurnInfo.setText(R.string.player_one_turn);
            }
            mUndoable = false;
            mMoveCounter--;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch(id){
            case R.id.newGame:
                startNewGame(mSinglePlayer);
                break;
            case R.id.exitGame:
                NormalGameActivity.this.finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }
}

