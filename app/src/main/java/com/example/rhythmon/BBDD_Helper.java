package com.example.rhythmon;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// Clase BBDD_Helper
public class BBDD_Helper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1; //Declaramos la version de la base de datos (final porque no la vamos a cambiar)
    public static final String DATABASE_NAME = "Rhythmon.db";

    // Constructor BBDD_Helper
    public BBDD_Helper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Crear BD
    public void onCreate(SQLiteDatabase db){
        db.execSQL(Estructura_BBDD.SQL_CREATE_TABLE1);
        db.execSQL(Estructura_BBDD.SQL_CREATE_TABLE2);
        db.execSQL(Estructura_BBDD.SQL_CREATE_TABLE3);
        db.execSQL(Estructura_BBDD.SQL_INSERT_CENTRO);
        db.execSQL(Estructura_BBDD.SQL_INSERT_ALUMNO);
    }

    // Actualizar BD
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL(Estructura_BBDD.SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    // Cambiar versión BD
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion){
       onUpgrade(db, oldVersion, newVersion);
    }

}