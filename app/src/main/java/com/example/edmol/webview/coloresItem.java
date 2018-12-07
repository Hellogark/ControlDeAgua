package com.example.edmol.webview;

public class coloresItem {
    private String mColoresNombre;
    private int mColoresImg;
    String fondoSeleccionado = "";

    public coloresItem(String coloresNombre, int coloresImg){
        mColoresNombre = coloresNombre;
        mColoresImg = coloresImg;
    }

    public String getColoresNombres(){
        return mColoresNombre;
    }

    public int getColoresImg(){
        return mColoresImg;
    }
}