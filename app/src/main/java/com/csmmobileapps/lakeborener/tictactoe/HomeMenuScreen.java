package com.csmmobileapps.lakeborener.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by ryanlake21 on 3/26/15.
 */
public class HomeMenuScreen  extends Activity{

    private Button mOnePlayerButton;
    private Button mTwoPlayerButton;
    private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.home_layout);

        ((Button) findViewById(R.id.onePlayerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuScreen.this,DifficultyChoiceScreen.class);
                intent.putExtra("singlePlayer",true);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.twoPlayerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenuScreen.this,NormalGameActivity.class);
                intent.putExtra("singlePlayer",false);
                intent.putExtra("chosenDifficulty",0);//--------------change to 2 later
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.exitGameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HomeMenuScreen.this.finish();
            }
        });





        mOnePlayerButton = (Button) findViewById(R.id.onePlayerButton);
        mTwoPlayerButton = (Button) findViewById(R.id.twoPlayerButton);
        mExitButton = (Button) findViewById(R.id.exitGameButton);

        mOnePlayerButton.setEnabled(true);
        mTwoPlayerButton.setEnabled(true);
        mExitButton.setEnabled(true);

    }

}

