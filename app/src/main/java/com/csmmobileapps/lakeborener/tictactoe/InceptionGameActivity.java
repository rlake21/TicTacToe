package com.csmmobileapps.lakeborener.tictactoe;

/**
 * Created by ryanlake21 on 4/2/15.
 */

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;


public class InceptionGameActivity extends ActionBarActivity{

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
        setContentView(R.layout.inception_layout);

        //Get gametype and/or difficulty
        mSinglePlayer = getIntent().getExtras().getBoolean("singlePlayer");
        if (!mSinglePlayer){mDifficulty = 1;} //hint difficulty for multiplayer
        else{ mDifficulty = getIntent().getExtras().getInt("chosenDifficulty");}

        initializeButtons();
        initializeTextFields();

        //Initialize last move arrays
        mPlayerOneLastMove = new int[2];
        mPlayerTwoLastMove = new int[2];

        //Initialize game
        mGame = new Game(mDifficulty);
        startNewInceptionGame(mSinglePlayer);

    }

    private void startNewInceptionGame(boolean isOnePlayerGame){
        //mGame.clearBoard();
        mMoveCounter = 0;
        mGameOver = false;

        //Start button logic
        for (int i = 0; i < 9; i++){
            for (int j = 0; j < 9; j++){
                mButtons[i][j].setEnabled(true);
                mButtons[i][j].setText(R.string.emptyString);
                mButtons[i][j].setOnClickListener(new ButtonClickListener(i,j,mGame.getEmptyChar()));
            }
        }
        mHintButton.setEnabled(true);
        mHintButton.setOnClickListener(new ButtonClickListener('H', mHintButton.isEnabled()));
        mUndoButton.setEnabled(true);
        mUndoButton.setOnClickListener(new ButtonClickListener('U', mUndoButton.isEnabled()));


        //Start turn and score logic
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


    private void initializeButtons(){
        mUndoButton = (Button) findViewById(R.id.iUndoButton);
        mHintButton = (Button) findViewById(R.id.iHintButton);

        mButtons = new Button[9][9];

        mButtons[0][0] = (Button) findViewById(R.id.oneOne);
        mButtons[0][1] = (Button) findViewById(R.id.oneTwo);
        mButtons[0][2] = (Button) findViewById(R.id.oneThree);
        mButtons[0][3] = (Button) findViewById(R.id.oneFour);
        mButtons[0][4] = (Button) findViewById(R.id.oneFive);
        mButtons[0][5] = (Button) findViewById(R.id.oneSix);
        mButtons[0][6] = (Button) findViewById(R.id.oneSeven);
        mButtons[0][7] = (Button) findViewById(R.id.oneEight);
        mButtons[0][8] = (Button) findViewById(R.id.oneNine);

        mButtons[1][0] = (Button) findViewById(R.id.twoOne);
        mButtons[1][1] = (Button) findViewById(R.id.twoTwo);
        mButtons[1][2] = (Button) findViewById(R.id.twoThree);
        mButtons[1][3] = (Button) findViewById(R.id.twoFour);
        mButtons[1][4] = (Button) findViewById(R.id.twoFive);
        mButtons[1][5] = (Button) findViewById(R.id.twoSix);
        mButtons[1][6] = (Button) findViewById(R.id.twoSeven);
        mButtons[1][7] = (Button) findViewById(R.id.twoEight);
        mButtons[1][8] = (Button) findViewById(R.id.twoNine);

        mButtons[2][0] = (Button) findViewById(R.id.threeOne);
        mButtons[2][1] = (Button) findViewById(R.id.threeTwo);
        mButtons[2][2] = (Button) findViewById(R.id.threeThree);
        mButtons[2][3] = (Button) findViewById(R.id.threeFour);
        mButtons[2][4] = (Button) findViewById(R.id.threeFive);
        mButtons[2][5] = (Button) findViewById(R.id.threeSix);
        mButtons[2][6] = (Button) findViewById(R.id.threeSeven);
        mButtons[2][7] = (Button) findViewById(R.id.threeEight);
        mButtons[2][8] = (Button) findViewById(R.id.threeNine);

        mButtons[3][0] = (Button) findViewById(R.id.fourOne);
        mButtons[3][1] = (Button) findViewById(R.id.fourTwo);
        mButtons[3][2] = (Button) findViewById(R.id.fourThree);
        mButtons[3][3] = (Button) findViewById(R.id.fourFour);
        mButtons[3][4] = (Button) findViewById(R.id.fourFive);
        mButtons[3][5] = (Button) findViewById(R.id.fourSix);
        mButtons[3][6] = (Button) findViewById(R.id.fourSeven);
        mButtons[3][7] = (Button) findViewById(R.id.fourEight);
        mButtons[3][8] = (Button) findViewById(R.id.fourNine);

        mButtons[4][0] = (Button) findViewById(R.id.fiveOne);
        mButtons[4][1] = (Button) findViewById(R.id.fiveTwo);
        mButtons[4][2] = (Button) findViewById(R.id.fiveThree);
        mButtons[4][3] = (Button) findViewById(R.id.fiveFour);
        mButtons[4][4] = (Button) findViewById(R.id.fiveFive);
        mButtons[4][5] = (Button) findViewById(R.id.fiveSix);
        mButtons[4][6] = (Button) findViewById(R.id.fiveSeven);
        mButtons[4][7] = (Button) findViewById(R.id.fiveEight);
        mButtons[4][8] = (Button) findViewById(R.id.fiveNine);

        mButtons[5][0] = (Button) findViewById(R.id.sixOne);
        mButtons[5][1] = (Button) findViewById(R.id.sixTwo);
        mButtons[5][2] = (Button) findViewById(R.id.sixThree);
        mButtons[5][3] = (Button) findViewById(R.id.sixFour);
        mButtons[5][4] = (Button) findViewById(R.id.sixFive);
        mButtons[5][5] = (Button) findViewById(R.id.sixSix);
        mButtons[5][6] = (Button) findViewById(R.id.sixSeven);
        mButtons[5][7] = (Button) findViewById(R.id.sixEight);
        mButtons[5][8] = (Button) findViewById(R.id.sixNine);

        mButtons[6][0] = (Button) findViewById(R.id.sevenOne);
        mButtons[6][1] = (Button) findViewById(R.id.sevenTwo);
        mButtons[6][2] = (Button) findViewById(R.id.sevenThree);
        mButtons[6][3] = (Button) findViewById(R.id.sevenFour);
        mButtons[6][4] = (Button) findViewById(R.id.sevenFive);
        mButtons[6][5] = (Button) findViewById(R.id.sevenSix);
        mButtons[6][6] = (Button) findViewById(R.id.sevenSeven);
        mButtons[6][7] = (Button) findViewById(R.id.sevenEight);
        mButtons[6][8] = (Button) findViewById(R.id.sevenNine);

        mButtons[7][0] = (Button) findViewById(R.id.eightOne);
        mButtons[7][1] = (Button) findViewById(R.id.eightTwo);
        mButtons[7][2] = (Button) findViewById(R.id.eightThree);
        mButtons[7][3] = (Button) findViewById(R.id.eightFour);
        mButtons[7][4] = (Button) findViewById(R.id.eightFive);
        mButtons[7][5] = (Button) findViewById(R.id.eightSix);
        mButtons[7][6] = (Button) findViewById(R.id.eightSeven);
        mButtons[7][7] = (Button) findViewById(R.id.eightEight);
        mButtons[7][8] = (Button) findViewById(R.id.eightNine);

        mButtons[8][0] = (Button) findViewById(R.id.nineOne);
        mButtons[8][1] = (Button) findViewById(R.id.nineTwo);
        mButtons[8][2] = (Button) findViewById(R.id.nineThree);
        mButtons[8][3] = (Button) findViewById(R.id.nineFour);
        mButtons[8][4] = (Button) findViewById(R.id.nineFive);
        mButtons[8][5] = (Button) findViewById(R.id.nineSix);
        mButtons[8][6] = (Button) findViewById(R.id.nineSeven);
        mButtons[8][7] = (Button) findViewById(R.id.nineEight);
        mButtons[8][8] = (Button) findViewById(R.id.nineNine);
    }
    private void initializeTextFields(){

        //Initialize text fields
        mTurnInfo = (TextView) findViewById(R.id.iTurnInfo);
        mPlayerOneCount = (TextView) findViewById(R.id.iHumanCount);
        mPlayerTwoCount = (TextView) findViewById(R.id.iCompCount);
        mTieCount  = (TextView) findViewById(R.id.iTieCount);
        mPlayerOneText = (TextView) findViewById(R.id.iHumanLabel);
        mPlayerTwoText = (TextView) findViewById(R.id.iCompLabel);

        //Initialize scores
        mPlayerOneCount.setText(Integer.toString(mPlayerOneIncrement));
        mPlayerTwoCount.setText(Integer.toString(mPlayerTwoIncrement));
        mTieCount.setText(Integer.toString(mTieIncrement));
    }

    private void getHint(){}
    private void undoHint(){}
    private void undoMove(){}
    private void singlePlayerMove(int frame, int tile){}
    private void multiPlayerMove(int frame, int tile){}
    private void setMove(int frame, int tile, char player){}



    private class ButtonClickListener implements View.OnClickListener{

        int row, col;
        char buttonText;
        boolean enabled;

        //initialization for move buttons
        public ButtonClickListener(int r, int c, char text){
            this.row = r;
            this.col = c;
            this.buttonText = text;
            this.enabled = false;
        }

        //initialization for hint and undo buttons
        public ButtonClickListener(char text, boolean checkEnabled){
            this.buttonText = text;
            this.enabled = checkEnabled;

        }

        // when clicked, check if: game over, enabled button (for valid move), which button
        public void onClick(View view){
            if (!mGameOver){
                if (mButtons[row][col].isEnabled() || enabled) {
                    if (buttonText == mGame.getCompChar() || buttonText == mGame.getEmptyChar()
                            || buttonText == mGame.getHumanChar()) {
                        // make sure no hints are present upon next move
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

        //set newGame and exitGame option buttons
        switch(id){
            case R.id.newGame:
                startNewInceptionGame(mSinglePlayer);
                break;
            case R.id.exitGame:
                InceptionGameActivity.this.finish();
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
