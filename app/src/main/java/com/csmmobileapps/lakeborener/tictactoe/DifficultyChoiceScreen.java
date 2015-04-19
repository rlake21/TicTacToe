package com.csmmobileapps.lakeborener.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by ryanlake21 on 3/27/15.
 */
public class DifficultyChoiceScreen extends Activity {

    private Button mZeroDifficulty;
    private Button mOneDifficulty;
    private Button mTwoDifficulty;
    private Button mBackButton;
    private boolean inceptionGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.difficulty_layout);
        inceptionGame = getIntent().getExtras().getBoolean("inceptionGame");

        ((Button) findViewById(R.id.zeroDifficultyButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inceptionGame) {
                    Intent intent = new Intent(DifficultyChoiceScreen.this, InceptionGameActivity.class);
                    intent.putExtra("chosenDifficulty",0);
                    intent.putExtra("singlePlayer",true);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(DifficultyChoiceScreen.this, NormalGameActivity.class);
                    intent.putExtra("chosenDifficulty",0);
                    intent.putExtra("singlePlayer",true);
                    startActivityForResult(intent, 0);
                }
            }
        });

        ((Button) findViewById(R.id.oneDifficultyButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inceptionGame) {
                    Intent intent = new Intent(DifficultyChoiceScreen.this, InceptionGameActivity.class);
                    intent.putExtra("chosenDifficulty",1);
                    intent.putExtra("singlePlayer",true);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(DifficultyChoiceScreen.this, NormalGameActivity.class);
                    intent.putExtra("chosenDifficulty",1);
                    intent.putExtra("singlePlayer",true);
                    startActivityForResult(intent, 0);
                }
            }
        });

        ((Button) findViewById(R.id.twoDifficultyButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //////remove this line to reenable the 2 difficulty setting (also set visibility in layout)
                findViewById(R.id.twoDifficultyButton).setEnabled(false);

                if (inceptionGame) {
                    Intent intent = new Intent(DifficultyChoiceScreen.this, InceptionGameActivity.class);
                    intent.putExtra("chosenDifficulty", 2);
                    intent.putExtra("singlePlayer", true);
                    startActivityForResult(intent, 0);
                } else {
                    Intent intent = new Intent(DifficultyChoiceScreen.this, NormalGameActivity.class);
                    intent.putExtra("chosenDifficulty", 2);
                    intent.putExtra("singlePlayer", true);
                    startActivityForResult(intent, 0);
                }
            }
        });

        ((Button) findViewById(R.id.backButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DifficultyChoiceScreen.this.finish();
            }
        });


        mZeroDifficulty = (Button) findViewById(R.id.zeroDifficultyButton);
        mOneDifficulty = (Button) findViewById(R.id.oneDifficultyButton);
        mTwoDifficulty = (Button) findViewById(R.id.twoDifficultyButton);
        mBackButton = (Button) findViewById(R.id.backButton);

        mZeroDifficulty.setEnabled(true);
        mOneDifficulty.setEnabled(true);
        mTwoDifficulty.setEnabled(true);
        mBackButton.setEnabled(true);

    }

}