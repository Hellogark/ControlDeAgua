package com.example.edmol.webview;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class manual extends AppCompatActivity {
    ImageView btnManual;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual);
        btnManual = (ImageView) findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            boolean power = false;
            public void onClick(View v) {
                if (!power){
                    btnManual.setImageResource(R.drawable.power_on);
                    power = true;
                } else {
                    btnManual.setImageResource(R.drawable.power_off);
                    power = false;
                }
            }
        });
        /*btnPower.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    btnPower.setImageResource(R.drawable.power_on);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    btnPower.setImageResource(R.drawable.power_off);
                }
                return true;
            }
        });*/
    }
}
