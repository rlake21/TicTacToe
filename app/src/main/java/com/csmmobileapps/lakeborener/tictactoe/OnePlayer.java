package com.csmmobileapps.lakeborener.tictactoe;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

public class OnePlayer extends ActionBarActivity {

    private Game mGame;
    private Button mButtons[];
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
        mButtons = new Button[9];
        mButtons[0] = (Button) findViewById(R.id.one);
        mButtons[1] = (Button) findViewById(R.id.two);
        mButtons[2] = (Button) findViewById(R.id.three);
        mButtons[3] = (Button) findViewById(R.id.four);
        mButtons[4] = (Button) findViewById(R.id.five);
        mButtons[5] = (Button) findViewById(R.id.six);
        mButtons[6] = (Button) findViewById(R.id.seven);
        mButtons[7] = (Button) findViewById(R.id.eight);
        mButtons[8] = (Button) findViewById(R.id.nine);

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
