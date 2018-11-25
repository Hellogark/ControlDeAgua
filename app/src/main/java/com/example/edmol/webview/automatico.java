package com.example.edmol.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class automatico extends AppCompatActivity {
    EditText textLitros;
    Button btnAutomatico;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_automatico);
        textLitros = (EditText) findViewById(R.id.textLitros);
        btnAutomatico = (Button) findViewById(R.id.btnAutomatico);
        btnAutomatico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable litros = textLitros.getText();
                Toast.makeText(automatico.this, "Estan saliendo "+litros+" litros.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
