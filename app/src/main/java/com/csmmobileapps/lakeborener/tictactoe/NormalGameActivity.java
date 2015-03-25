package com.csmmobileapps.lakeborener.tictactoe;

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
    private TextView mTurnInfo;
    private TextView mHumanCount;
    private TextView mCompCount;
    private TextView mTieCount;
    private int mHumanIncriment = 0;
    private int mCompIncriment = 0;
    private int mTieIncriment = 0;
    private boolean mHumanGoesFirst = true;
    private boolean mGameOver = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Initialize button array
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

        //Initialize text fields
        mTurnInfo = (TextView) findViewById(R.id.TurnInfo);
        mHumanCount = (TextView) findViewById(R.id.humanCount);
        mCompCount = (TextView) findViewById(R.id.compCount);
        mTieCount  = (TextView) findViewById(R.id.tieCount);

        //Initialize scores
        mHumanCount.setText(Integer.toString(mHumanIncriment));
        mCompCount.setText(Integer.toString(mCompIncriment));
        mTieCount.setText(Integer.toString(mTieIncriment));

        //Initialize game
        mGame = new Game();
        startNewGame();

    }

    private void startNewGame(){

        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                mButtons[i][j].setText(mGame.getEmptyChar());
                mButtons[i][j].setEnabled(true);
                mButtons[i][j].setOnClickListener(new ButtonClickListener(i,j));
            }
        }

        if (mHumanGoesFirst){
            mTurnInfo.setText(R.string.human_turn);
            mHumanGoesFirst = false;
        } else{
            int move[] = mGame.computerMove();
            mTurnInfo.setText(R.string.comp_turn);
            setMove(move[0], move[1], mGame.getCompChar());
            mHumanGoesFirst = true;
        }
    }

    private void setMove(int row, int col, char player){
        mGame.makeMove(row,col,player);
        mButtons[row][col].setEnabled(false);
        mButtons[row][col].setText(player);
        if (player == mGame.getCompChar()){
            mButtons[row][col].setTextColor(Color.BLUE);
        } else{
            mButtons[row][col].setTextColor(Color.RED);
        }
    }

    private class ButtonClickListener implements View.OnClickListener{

        int row, col;

        public ButtonClickListener(int r, int c){
            this.row = r;
            this.col = c;
        }

        public void onClick(View view){
            if (!mGameOver){
                if (mButtons[row][col].isEnabled()){
                    setMove(row,col,mGame.getHumanChar());
                    int win = mGame.checkForWinner();

                    if (win == 0){
                        int move[] = mGame.computerMove();
                        mTurnInfo.setText(R.string.comp_turn);
                        setMove(move[0], move[1], mGame.getCompChar());
                        win = mGame.checkForWinner();
                    }

                    if (win == 0){
                        mTurnInfo.setText(R.string.human_turn);
                    } else if (win == 1){
                        mTurnInfo.setText(R.string.outcome_tie);
                        mTieIncriment++;
                        mTieCount.setText(Integer.toString(mTieIncriment));
                        mGameOver = true;
                    }else if (win == 2){
                        mTurnInfo.setText(R.string.outcome_human);
                        mHumanIncriment++;
                        mHumanCount.setText(Integer.toString(mHumanIncriment));
                        mGameOver = true;
                    }else if (win == 3){
                        mTurnInfo.setText(R.string.outcome_computer);
                        mCompIncriment++;
                        mCompCount.setText(Integer.toString(mCompIncriment));
                        mGameOver = true;
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

