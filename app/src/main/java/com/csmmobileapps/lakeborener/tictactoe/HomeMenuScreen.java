package com.csmmobileapps.lakeborener.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.util.Log;

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
                Log.d("DEBUG","Player one button pressed");
                Intent intent = new Intent(HomeMenuScreen.this,NormalGameActivity.class);
                intent.putExtra("singlePlayer",true);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.twoPlayerButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("DEBUG","Player two button pressed");
                Intent intent = new Intent(HomeMenuScreen.this,NormalGameActivity.class);
                intent.putExtra("singlePlayer",false);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.exitGameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("debug","Exit button pressed");
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

