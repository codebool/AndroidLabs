package com.cst7335.mydomain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

// GameView class extends SurfaceView and implements Runnable, SurfaceHolder.Callback
public class GameView extends SurfaceView implements Runnable, SurfaceHolder.Callback {
    // Declare gameThread, isPlaying, player, platforms, and isGameOver
    private Thread gameThread;
    private boolean isPlaying = true;
    private Player player;
    private List<Platform> platforms = new ArrayList<>();
    private boolean isGameOver = false;

    // GameView constructor
    public GameView(Context context) {
        super(context); // Call the super class constructor
        player = new Player(); // Initialize the player
        platforms = new ArrayList<>(); // Initialize the platforms
        platforms.add(new Platform(500, 600)); // Add a platform to the platforms list

        resume();  // Start the game loop as soon as GameView is created
        // Add the callback to the surface holder to intercept events
        getHolder().addCallback(this);
    }

    // Override the run method
    @Override
    public void run() {
        // Run the game loop
        while (isPlaying) {
            update(); // Update the game state
            draw(); // Draw the game state
            sleep(); // Sleep to cap the frame rate
        }
    }

    // Override the surfaceCreated method
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        resume();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // Handle surface changes if necessary
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        pause();
    }

    // Update the game state
    private void update() {
        // Update player and platforms
        player.update();
        // Iterate over platforms and update them
        Iterator<Platform> iterator = platforms.iterator();
        // Iterate over platforms and update them
        while (iterator.hasNext()) {
            // Get the next platform
            Platform platform = iterator.next();
            // Update the platform
            platform.update();
            // Check for collision with the player
            if (player.collidesWith(platform) && player.isAbove(platform)) {
                player.y = platform.y - 100;  // Position player on top of the platform
                player.velocityY = 0;         // Stop downward motion
            }
            // Remove platform if it's off the screen
            if (platform.x + 200 < 0) {
                iterator.remove(); // Remove the platform
            }
        }
        // Generate new platforms periodically
        if (Math.random() < 0.02) { // 2% chance every frame to spawn a platform
            float yPosition = (float) (400 + Math.random() * 200);  // Random height between 400 and 600
            platforms.add(new Platform(getWidth(), yPosition)); // Add a new platform
        }

        // Handle game over if player falls off the screen
        if (player.y > getHeight()) {
            gameOver(); // Game over
        }
    }

    private void gameOver() {
        isPlaying = false; // Stop the game loop
        isGameOver = true; // Set game over flag
    }

    // Draw the game state
    private void draw() {
        // Lock the canvas to draw on it
        if (getHolder().getSurface().isValid()) {
            // Get the canvas to draw on
            Canvas canvas = getHolder().lockCanvas();

            // Clear screen
            canvas.drawColor(Color.WHITE);

            // Draw player
            canvas.drawRect(player.x, player.y, player.x + 100, player.y + 100, new Paint(Color.RED));

            // Draw platforms
            Paint platformPaint = new Paint(Color.GREEN);
            for (Platform platform : platforms) {
                canvas.drawRect(platform.x, platform.y, platform.x + 200, platform.y + 50, platformPaint);
            }

            // Draw game over text
            if (isGameOver) {
                // Draw game over text
                Paint gameOverPaint = new Paint();
                // Set text size, color, and position
                gameOverPaint.setTextSize(50);
                // Set text size, color, and position
                gameOverPaint.setColor(Color.BLACK);
                // Set text size, color, and position
                canvas.drawText("Game Over! Tap to restart.", getWidth() / 2 - 250, getHeight() / 2, gameOverPaint);
            }
            // Unlock the canvas to draw on it
            getHolder().unlockCanvasAndPost(canvas);
        }
    }

    // Player class with x, y, velocityY, GRAVITY, and JUMP_STRENGTH
    public class Player {
        public float x = 100, y = 500; // Default starting position
        public float velocityY = 0; // Vertical velocity
        private static final float GRAVITY = 1f; // Gravity
        private static final float JUMP_STRENGTH = -15f; // Jump strength

        // Update the player position
        public void update() {
            y += velocityY; // Update the player position
            velocityY += GRAVITY; // Apply gravity
        }

        // Check for collision with a platform
        public boolean collidesWith(Platform platform) {
            // Check if player is within the bounds of the platform
            return x < platform.x + 200 &&
                    x + 100 > platform.x &&
                    y + 100 > platform.y &&
                    y < platform.y + 50;
        }

        // Check if player is above a platform
        public boolean isAbove(Platform platform) {
            // Check if player is above the platform
            return x + 100 > platform.x &&
                    x < platform.x + 200 &&
                    y + 100 <= platform.y;
        }

        // Make the player jump
        public void jump() {
            velocityY = JUMP_STRENGTH;
        }
    }

    // Platform class with x, y, and SPEED
    public class Platform {
        // Declare x, y, and SPEED
        public float x, y;
        private static final float SPEED = 5f;

        // Platform constructor
        public Platform(float x, float y) {
            this.x = x;
            this.y = y;
        }

        // Update the platform position
        public void update() {
            x -= SPEED;
        }
    }


    // Handle touch events
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Handle touch events for jumping and restarting the game
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // Check if game is over
            if (isGameOver) {
                // Restart the game
                restartGame();
            } else {
                // Make the player jump
                player.jump();
                // Check if the game is not already playing
                if (!isPlaying) {
                    // Start the game
                    isPlaying = true;
                }
            }
        }
        return true;
    }

    // Restart the game
    private void restartGame() {
        player = new Player(); // Reset the player
        platforms.clear(); // Clear the platforms
        platforms.add(new Platform(500, 600)); // Add a new platform
        isPlaying = true; // Set playing flag
        isGameOver = false; // Reset game over flag
    }

    // Sleep method to cap the frame rate
    private void sleep() {
        // Cap the game loop to roughly 60 frames per second
        try {
            Thread.sleep(17);  // Cap the game loop to roughly 60 frames per second
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Resume the game
    public void resume() {
        isPlaying = true; // Set playing flag
        gameThread = new Thread(this); // Create a new game thread
        gameThread.start(); // Start the game thread
    }

    // Pause the game
    public void pause() {
        // Stop the game loop
        try {
            // Stop the game loop
            isPlaying = false;
            // Stop the game loop
            gameThread.join();
            // Stop the game loop
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
