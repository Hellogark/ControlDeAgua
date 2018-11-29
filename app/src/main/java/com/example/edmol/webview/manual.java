package com.example.edmol.webview;

import android.annotation.SuppressLint;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class manual extends AppCompatActivity {
    ImageView btnManual;
    ConstraintLayout display2;

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

        display2 = (ConstraintLayout) findViewById(R.id.Fondo);
        String fondoActual;
        fondoActual = getIntent().getExtras().getString("fondoActual");
        switch (fondoActual) {
            case "colorFondo1": display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1)); break;
            case "colorFondo2": display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2)); break;
            case "colorFondo3": display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3)); break;
            case "colorFondo4": display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4)); break;
            default:
                Toast.makeText(this, "Algo malo pasó", Toast.LENGTH_SHORT).show(); break;
        }

    }
}
