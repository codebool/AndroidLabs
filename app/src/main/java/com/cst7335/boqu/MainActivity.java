package com.cst7335.boqu;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

//        setContentView(R.layout.activity_main_linear);
        setContentView(R.layout.activity_main_grid);
//        setContentView(R.layout.activity_main_relative);

        Button clickButton = findViewById(R.id.button);
        clickButton.setOnClickListener(v -> {
            String message = getResources().getString(R.string.toast_message);
            Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        });

        Switch switchButton = findViewById(R.id.switch_button);
        switchButton.setOnCheckedChangeListener((cb, isChecked) -> {
            String switchState = isChecked ? "on" : "off";
            String info = getResources().getString(R.string.switch_mode);
            Snackbar snackbar = Snackbar.make(findViewById(R.id.main), info + switchState, Snackbar.LENGTH_LONG);
            snackbar.setAction("Undo", v -> cb.setChecked(!isChecked));
            snackbar.show();
        });
    }
}