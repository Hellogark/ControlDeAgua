package com.example.edmol.webview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class ajustes extends AppCompatActivity {
    private ArrayList<coloresItem> mColoresList;
    private coloresAdapter cAdapter;
    private ArrayList<tamanoItem> mTamanoList;
    private tamanoAdapter eAdapter;
    TextView display;
    RelativeLayout display2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);

        display = (TextView) findViewById(R.id.txtControl);
        display2 = (RelativeLayout) findViewById(R.id.Fondo);
        colorList();
        tamanoList();


        Spinner spinnerColores = findViewById(R.id.spinnerColor);
        Spinner spinnerTamano = findViewById(R.id.spinnerTamano);

        cAdapter = new coloresAdapter(this, mColoresList);
        eAdapter = new tamanoAdapter(this, mTamanoList);


        spinnerColores.setAdapter(cAdapter);
        spinnerTamano.setAdapter(eAdapter);

        spinnerColores.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                coloresItem clickItem = (coloresItem) parent.getItemAtPosition(position);
                String clickNombre = clickItem.getColoresNombres();

                if (position == 0) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
                }
                if (position == 1) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
                }
                if (position == 2) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3));
                }
                if (position == 3) {
                    display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTamano.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tamanoItem clickItem = (tamanoItem) parent.getItemAtPosition(position);
                String clickNombre = clickItem.getTamanoNombres();

                if (position == 0) {
                    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
                }
                if (position == 1) {
                    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
                }
                if (position == 2) {
                    display.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void colorList(){
        mColoresList = new ArrayList<>();
        mColoresList.add(new coloresItem("Fondo 1", R.drawable.paleta_colores));
        mColoresList.add(new coloresItem("Fondo 2", R.drawable.paleta_colores));
        mColoresList.add(new coloresItem("Fondo 3", R.drawable.paleta_colores));
        mColoresList.add(new coloresItem("Fondo 4", R.drawable.paleta_colores));
    }

    private void tamanoList(){
        mTamanoList = new ArrayList<>();
        mTamanoList.add(new tamanoItem("Pequeño", R.drawable.tamano_texto));
        mTamanoList.add(new tamanoItem("Mediano", R.drawable.tamano_texto));
        mTamanoList.add(new tamanoItem("Grande", R.drawable.tamano_texto));
    }
}