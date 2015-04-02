package com.csmmobileapps.lakeborener.tictactoe;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

/**
 * Created by ryanlake21 on 4/2/15.
 */
public class GameChoiceScreen extends Activity{

    private Button mInceptionButton;
    private Button mNormalButton;
    private Button mExitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.game_choice_screen);

        ((Button) findViewById(R.id.inceptionGameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameChoiceScreen.this,HomeMenuScreen.class);
                intent.putExtra("inceptionGame",true);
                startActivityForResult(intent, 0);
            }
        });

        ((Button) findViewById(R.id.normalGameButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GameChoiceScreen.this,HomeMenuScreen.class);
                intent.putExtra("inceptionGame",false);
                startActivityForResult(intent, 0);
            }
        });
        ((Button) findViewById(R.id.choiceExitGame)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GameChoiceScreen.this.finish();
            }
        });


        mInceptionButton = (Button) findViewById(R.id.inceptionGameButton);
        mNormalButton = (Button) findViewById(R.id.normalGameButton);
        mExitButton = (Button) findViewById(R.id.choiceExitGame);

        mInceptionButton.setEnabled(true);
        mNormalButton.setEnabled(true);
        mExitButton.setEnabled(true);

    }

}

