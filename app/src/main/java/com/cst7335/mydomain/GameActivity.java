package com.cst7335.mydomain;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    private GameView gameView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);
        // Initialize GameView
        gameView = new GameView(this);
        setContentView(gameView);
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    public void storeHighScore(int score) {
        databaseHelper.saveScore(score);
    }

    public int retrieveHighScore() {
        return databaseHelper.getTopScore();
    }
}
