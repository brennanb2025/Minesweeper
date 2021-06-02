package com.example.uscart.s2lab2_minesweeper;


import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * Created by uscart on 3/8/18.
 */

public class RunActivity extends AppCompatActivity {

    private int rows;
    private int cols;
    private LinearLayout row;
    //private Drawable buttonDrawable;
    private LinearLayout layout;
    private String diff;
    private Button[][] buttonList;
    private int[][] boardFilled;
    private Button boardButton;
    private Game game;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_run);
        Button SettingsButton = findViewById(R.id.settingsButton);
        SettingsButton.setOnClickListener(new RunActivity.settingsBtnListener());

        Button ResetButton = findViewById(R.id.resetBtn);
        ResetButton.setOnClickListener(new RunActivity.resetBtnListener());

        Bundle extras = getIntent().getExtras(); //get variables from settings class
        if (extras != null) {
            if (extras.getString("Rows") != null && extras.getString("Columns").toString() != null && extras.getString("Spinner").toString() != null) {
                rows = Integer.parseInt(extras.getString("Rows").toString());
                cols = Integer.parseInt(extras.getString("Columns").toString());
                diff = extras.getString("Spinner").toString();
            }

            game = new Game(diff, rows, cols);
            boardFilled = game.checkAround(game.board); //makes the board w/ button's values
            layout = findViewById(R.id.GameBoard);
            buttonList = new Button[cols][rows]; //makes the board of buttons

            int btnCnt = 0;

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT); //layout parameters

            for (int i = 0; i < boardFilled.length; i++) { //make the rows

                row = new LinearLayout(this);
                row.setOrientation(LinearLayout.HORIZONTAL);
                //row.setPadding(2,2,2,2);

                layoutParams.weight = 100.0f / cols;
                row.setLayoutParams(layoutParams);
                for (int j = 0; j < boardFilled[0].length; j++) { //make the buttons to put in the rows


                    boardButton = new Button(this);

                    boardButton.setOnClickListener(new RunActivity.boardBtnListener());
                    boardButton.setTag(boardFilled[i][j] + " " + i + " " + j); //for later access
                    boardButton.setId(btnCnt);

                    boardButton.setLayoutParams(layoutParams);

                    row.addView(boardButton); //add the buttons to the row
                    boardButton.setText(" ");
                    buttonList[i][j] = boardButton;


                    btnCnt+=1;
                }
                layout.addView(row); //add the rows to the large linearlayout
            }

        } else { //if you haven't set the settings, go to the settings activity.
            Intent intent = new Intent(RunActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }


    private class boardBtnListener implements View.OnClickListener {
        public void onClick(View v) {
            if(game.board.getDiff() != null) {
                if(game.board.getDiff().equals("Impossible")) { //if on impossible difficulty
                    int x = Integer.parseInt(v.getTag().toString().split(" ")[1]);
                    int y = Integer.parseInt(v.getTag().toString().split(" ")[2]); //get the coordinates of the button
                    buttonList[x][y].setTag(-1 + " " + x + " " + y); //set the tag to -1
                    boardFilled[x][y] = -1; //set boardfilled's value of the coordinates to -1
                }
            }

            if(Integer.parseInt(v.getTag().toString().split(" ")[0]) == -1) { //if this button has a text of -1 -->> you lost.
                Toast.makeText(getApplicationContext(), "YOU LOST!!!", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < boardFilled.length; i++) {
                    for(int j = 0; j < boardFilled[0].length; j++) {
                        buttonList[i][j].setText(Integer.toString(boardFilled[i][j])); //reveal everything
                        buttonList[i][j].setEnabled(false);
                    }
                }
            }
            ((Button) v).setText(String.valueOf(Integer.parseInt(v.getTag().toString().split(" ")[0])));

            game.showAround(buttonList, boardFilled, Integer.parseInt(v.getTag().toString().split(" ")[1]),
                    Integer.parseInt(v.getTag().toString().split(" ")[2]), game); //call recursive method, show buttons around it

            int blankCounter = 0;
            int bombCounter = 0;
            for(int i = 0; i < buttonList.length; i++) {
                for(int j = 0; j < buttonList[0].length; j++) {
                    if(buttonList[i][j].getText().toString().equals(" ")) { //if there is nothing there
                        blankCounter++;
                    }
                    if(boardFilled[i][j] == -1) {
                        bombCounter++;
                    }
                }
            }
            if(blankCounter == bombCounter) { //if the number of empty spaces is the same as the number of bombs, you won.
                Toast.makeText(getApplicationContext(), "YOU WON!!!", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < buttonList.length; i++) {
                    for(int j = 0; j < buttonList[0].length; j++) {
                        buttonList[i][j].setText(Integer.toString(boardFilled[i][j]));
                        buttonList[i][j].setEnabled(false);
                    }
                }
            }




        }
    }


    private class settingsBtnListener implements View.OnClickListener { //Switches Screen to RunScreen1
        public void onClick(View v) {
            Intent intent = new Intent(RunActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
    }

    private class resetBtnListener implements View.OnClickListener {
        public void onClick(View v) {

            for(int i = 0; i < buttonList.length; i++) {
                for(int j = 0; j < buttonList[0].length; j++) {
                    buttonList[i][j].setText(" ");
                }
            }
            game = new Game(diff, rows, cols);
            finish();
            startActivity(getIntent());
        }
    }




}
