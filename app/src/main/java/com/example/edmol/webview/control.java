package com.example.edmol.webview;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class control extends AppCompatActivity {
    ConstraintLayout display2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control);
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
