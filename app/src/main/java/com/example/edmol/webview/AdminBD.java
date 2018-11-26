package com.example.edmol.webview;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AdminBD extends SQLiteOpenHelper{
    final static int bd_version = 1;
    final static String bd_nombre = "bd_registros";
    final static String tb_nombre = "tb_registros";
    final static String campo_id = "id";
    final static String campo_fecha = "fecha";
    final static String campo_litros_consumidos = "litros_consumidos";
    final static String campo_modo = "modo"; //MANUAL O AUTOMATICO
    final static String campo_caudal = "caudal";
    final static String crear_tabla = "CREATE TABLE "+tb_nombre+" ("+campo_id+" INTEGER PRIMARY KEY AUTOINCREMENT, "
            +campo_fecha+" TEXT, "+campo_litros_consumidos+" TEXT, "+campo_modo+" TEXT, "+campo_caudal+" TEXT);";
    final static String consulta_tabla = "SELECT * FROM "+tb_nombre+";";

    public AdminBD(Context context) {
        super(context, bd_nombre, null, bd_version);
    }

    @Override
    public void onCreate(SQLiteDatabase bd) {
        bd.execSQL(crear_tabla);
    }

    @Override
    public void onUpgrade(SQLiteDatabase bd, int oldVersion, int newVersion) {

    }

    public void insertarRegistro(SQLiteDatabase bd, String fecha, String litros_consumidos, String modo, String caudal) {
        final String insertar = "INSERT INTO "+tb_nombre+" ("+campo_fecha+", "
                +campo_litros_consumidos+", "+campo_modo+", "+campo_caudal+") VALUES ('"+fecha+"','"
                +litros_consumidos+"','"+modo+"','"+caudal+"');";
        bd.execSQL(insertar);
    }

    public Cursor consulta(SQLiteDatabase bd) {
        Cursor cursor = bd.rawQuery(consulta_tabla, null);
        return cursor;
    }

    public void modificarRegistro(SQLiteDatabase bd, int id, String fecha, String litros_consumidos, String modo, String caudal) {
        final String modificar = "UPDATE "+tb_nombre+" SET "+campo_fecha+ "='"+fecha+"', "
                +campo_litros_consumidos+"='"+litros_consumidos+"', "
                +campo_modo+"='"+modo+"', "
                +campo_caudal+"='"+caudal+"' WHERE "+campo_id+"="+ id + ";";
        bd.execSQL(modificar);
    }

    public void eliminarRegistro(SQLiteDatabase bd, int id) {
        final String eliminar = "DELETE FROM "+tb_nombre+" WHERE "+campo_id+"="+id+";";
        bd.execSQL(eliminar);
    }
}
