package com.example.edmol.webview;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class home extends AppCompatActivity {
    private ImageView control, ajustes;
    private Button btnManual, btnAutomatico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        control = (ImageView) findViewById(R.id.controlAgua);
        control.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openControl();
            }
        });

        ajustes = (ImageView) findViewById(R.id.imgAjustes);
        ajustes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAjustes();
            }
        });

        btnManual = (Button) findViewById(R.id.btnManual);
        btnManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openManual();
            }
        });

        btnAutomatico = (Button) findViewById(R.id.btnAutomatico);
        btnAutomatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAutomatico();
            }
        });
    }

    public void openControl() {
        Intent c = new Intent(this, control.class);
        startActivity(c);
    }

    public void openAjustes(){
        Intent a = new Intent(this, ajustes.class);
        startActivity(a);
    }

    public void openManual(){
        Intent manual = new Intent(this, manual.class);
        startActivity(manual);
    }

    public  void openAutomatico(){
        Intent automatico = new Intent(this, automatico.class);
        startActivity(automatico);
    }
}