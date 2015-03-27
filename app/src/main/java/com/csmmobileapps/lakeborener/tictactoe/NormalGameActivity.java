
package com.csmmobileapps.lakeborener.tictactoe;

/** NormalGameActivity
 * Created by Ryan on 3/24/15.
 */


import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class NormalGameActivity extends ActionBarActivity {

    private Game mGame;
    private Button mButtons[][];
    private Button mUndoButton;
    private Button mHintButton;
    private TextView mTurnInfo;
    private TextView mHumanCount;
    private TextView mCompCount;
    private TextView mTieCount;

    private int mCompLastMove[];
    private int mHumanLastMove[];
    private int mMoveCounter = 0;
    private int mHumanIncrement = 0;
    private int mCompIncrement = 0;
    private int mTieIncrement = 0;
    private boolean mHumanGoesFirst = true;
    private boolean mGameOver = false;
    private boolean mUndoable = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
        mHumanCount = (TextView) findViewById(R.id.humanCount);
        mCompCount = (TextView) findViewById(R.id.compCount);
        mTieCount  = (TextView) findViewById(R.id.tieCount);

        //Initialize scores
        mHumanCount.setText(Integer.toString(mHumanIncrement));
        mCompCount.setText(Integer.toString(mCompIncrement));
        mTieCount.setText(Integer.toString(mTieIncrement));

        //Initialize last move arrays
        mHumanLastMove = new int[2];
        mCompLastMove = new int[2];

        //Initialize game
        mGame = new Game();
        startNewGame();

    }

    private void startNewGame(){
        //mGame.clearBoard();

        //Start button logic
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                mButtons[i][j].setEnabled(true);
                mButtons[i][j].setText(" ");
                mButtons[i][j].setOnClickListener(new ButtonClickListener(i,j,mGame.getEmptyChar()));
            }
        }
        mHintButton.setEnabled(true);
        mHintButton.setOnClickListener(new ButtonClickListener('H'));
        mUndoButton.setEnabled(true);
        mUndoButton.setOnClickListener(new ButtonClickListener('U'));

        //Start turn logic
        if (mHumanGoesFirst){
            mTurnInfo.setText(R.string.human_turn);
            mHumanGoesFirst = false;
        } else{
            int move[] = mGame.computerMove();
            mTurnInfo.setText(R.string.comp_turn);
            setMove(move[0], move[1], mGame.getCompChar());
            mCompLastMove = move;
            mHumanGoesFirst = true;
        }
    }

    private void setMove(int row, int col, char player){
        mMoveCounter++;
        mGame.makeMove(row,col,player);
        mButtons[row][col].setEnabled(false);
        mButtons[row][col].setText(String.valueOf(player));
        if (player == mGame.getCompChar()){
            mButtons[row][col].setTextColor(Color.BLUE);
        } else{
            mButtons[row][col].setTextColor(Color.RED);
            mUndoable = true;
        }
    }

    private class ButtonClickListener implements View.OnClickListener{

        int row, col;
        char buttonText;

        public ButtonClickListener(int r, int c, char text){
            this.row = r;
            this.col = c;
            this.buttonText = text;
        }
        public ButtonClickListener(char text){
            this.buttonText = text;
        }

        public void onClick(View view){
            if (!mGameOver){
                if (buttonText == mGame.getCompChar() || buttonText == mGame.getEmptyChar()
                        || buttonText == mGame.getHumanChar()) {
                    if (mButtons[row][col].isEnabled()) {
                        setMove(row, col, mGame.getHumanChar());
                        mHumanLastMove[0] = row;
                        mHumanLastMove[1] = col;
                        int win = mGame.checkForWinner(mMoveCounter);

                        if (win == 0) {
                            int move[] = mGame.computerMove();
                            mTurnInfo.setText(R.string.comp_turn);
                            setMove(move[0], move[1], mGame.getCompChar());
                            mCompLastMove = move;
                            win = mGame.checkForWinner(mMoveCounter);
                        }

                        if (win == 0) {
                            mTurnInfo.setText(R.string.human_turn);
                        } else if (win == 1) {
                            mTurnInfo.setText(R.string.outcome_tie);
                            mTieIncrement++;
                            mTieCount.setText(Integer.toString(mTieIncrement));
                            mGameOver = true;
                        } else if (win == 2) {
                            mTurnInfo.setText(R.string.outcome_human);
                            mHumanIncrement++;
                            mHumanCount.setText(Integer.toString(mHumanIncrement));
                            mGameOver = true;
                        } else if (win == 3) {
                            mTurnInfo.setText(R.string.outcome_computer);
                            mCompIncrement++;
                            mCompCount.setText(Integer.toString(mCompIncrement));
                            mGameOver = true;
                        }
                    }
                }else{
                    if (buttonText == 'H'){
                        //hint logic
                    } else if (buttonText == 'U'){
                        if (mUndoable) {
                            //undo logic
                        } else mTurnInfo.setText(R.string.noUndo);
                    }
                }
            }
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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

