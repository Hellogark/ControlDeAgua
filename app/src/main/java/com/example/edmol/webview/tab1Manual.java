package com.example.edmol.webview;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class tab1Manual extends Fragment{
    RelativeLayout display2;
    String fondoActual;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.tab1manual, container, false);

        display2 = (RelativeLayout) v.findViewById(R.id.Fondo);
        display2.setBackgroundColor(getResources().getColor(R.color.colorFondo1));
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