package com.cst7335.mydomain;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity {
    // Declare GameView and DatabaseHelper
    private GameView gameView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // Call the super class onCreate to complete the creation of activity like the view hierarchy
        super.onCreate(savedInstanceState);

        // Initialize the database helper
        databaseHelper = new DatabaseHelper(this);
        // Initialize GameView
        gameView = new GameView(this);
        // Set the content view to the game view
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
