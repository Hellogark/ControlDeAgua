package com.example.edmol.webview;

import android.app.Activity;
import android.content.Intent;
import android.opengl.Visibility;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelmuenzer.android.scrollablennumberpicker.ScrollableNumberPicker;

import org.w3c.dom.Text;

public class tab2Automatico extends Fragment {
    RelativeLayout display2;
    String fondoActual;
    RadioButton rbLitros, rbCubicos, rbGalones;
    EditText agua;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab2automatico, container, false);
        display2 = (RelativeLayout) v.findViewById(R.id.Fondo);
        agua = (EditText) v.findViewById(R.id.etAgua);
        rbLitros = (RadioButton) v.findViewById(R.id.rbLitros);
        rbGalones = (RadioButton) v.findViewById(R.id.rbGalones);
        rbCubicos = (RadioButton) v.findViewById(R.id.rbCubicos);

        rbLitros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*litros.setVisibility(View.VISIBLE);
                galones.setVisibility(View.INVISIBLE);
                cubicos.setVisibility(View.INVISIBLE);*/
            }
        });

        rbGalones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*litros.setVisibility(View.INVISIBLE);
                galones.setVisibility(View.VISIBLE);
                cubicos.setVisibility(View.INVISIBLE);*/
            }
        });

        rbCubicos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*litros.setVisibility(View.INVISIBLE);
                galones.setVisibility(View.INVISIBLE);
                cubicos.setVisibility(View.VISIBLE);*/
            }
        });
        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                String fondoSeleccionado = data.getStringExtra("fondoSeleccionado").toString();
                fondoActual = fondoSeleccionado;
                switch (fondoActual) {
                    case "colorFondo1":
                        display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
                        break;
                    case "colorFondo2":
                        display2.setBackgroundColor(getResources().getColor(R.color.colorFondo2));
                        break;
                    case "colorFondo3":
                        display2.setBackgroundColor(getResources().getColor(R.color.colorFondo3));
                        break;
                    case "colorFondo4":
                        display2.setBackgroundColor(getResources().getColor(R.color.colorFondo4));
                        break;
                }
            }

        }
    }
}