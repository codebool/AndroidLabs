package com.cst7335.mydomain;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Button button1 = findViewById(R.id.button1);
        button1.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent);
        });

        Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, EntityActivity.class);
            startActivity(intent);
        });


        Button button4 = findViewById(R.id.button4);
        LinearLayout formLayout = findViewById(R.id.formLayout);
        Button submitFormButton = findViewById(R.id.submitFormButton);
        TextView confirmationMessage = findViewById(R.id.confirmationMessage);

        button4.setOnClickListener(v -> formLayout.setVisibility(View.VISIBLE));

        submitFormButton.setOnClickListener(v -> {
            formLayout.setVisibility(View.GONE);
            confirmationMessage.setVisibility(View.VISIBLE);

            new Handler().postDelayed(() -> {
                confirmationMessage.setVisibility(View.GONE);
            }, 3000); // 3000 milliseconds = 3 seconds
        });
    }
}