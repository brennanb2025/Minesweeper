package com.example.uscart.s2lab2_minesweeper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button PlayButton = findViewById(R.id.playButton);
        PlayButton.setOnClickListener(new playBtnListener());
    }



    private class playBtnListener implements View.OnClickListener { //goes to RunActivity
        public void onClick(View v) {
            Intent intent = new Intent(MainActivity.this, RunActivity.class);
            startActivity(intent);
        }
    }





}

