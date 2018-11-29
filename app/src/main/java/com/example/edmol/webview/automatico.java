package com.example.edmol.webview;

import android.support.constraint.ConstraintLayout;
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
    ConstraintLayout display2;

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
