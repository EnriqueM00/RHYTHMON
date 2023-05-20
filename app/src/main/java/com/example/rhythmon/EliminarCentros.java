package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EliminarCentros extends AppCompatActivity {

    private ArrayList<Centro> listaCentros;
    private Spinner spinnerCentros;
    private BBDD_Helper helper = new BBDD_Helper(this);
    private Button btnEliminar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_centros);
        listaCentros = new ArrayList<Centro>();
        listar();
        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_dropdown_item, listaCentros);
        spinnerCentros = findViewById(R.id.spinnerDeleteCentros);
        spinnerCentros.setAdapter(adapter);
        btnEliminar = findViewById(R.id.btnEliminarCentros);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listaCentros.get(spinnerCentros.getSelectedItemPosition()).getId_user().equals("root")){
                    Toast.makeText(EliminarCentros.this, "No tienes permisos para eliminar al Administrador.", Toast.LENGTH_SHORT).show();
                }
                else {
                    SQLiteDatabase db = helper.getWritableDatabase();
                    String selection = Estructura_BBDD.COD_CENTRO + " LIKE ?";
                    String[] selectionArgs = { String.valueOf(listaCentros.get(spinnerCentros.getSelectedItemPosition()).getCodCentro()) };
                    db.delete(Estructura_BBDD.TABLE_CENTROS, selection, selectionArgs);
                    Toast.makeText(EliminarCentros.this, "Registro borrado con exito.", Toast.LENGTH_SHORT).show();

                    listaCentros.clear();
                    listar();
                    ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(EliminarCentros.this, android.R.layout.simple_spinner_dropdown_item, listaCentros);
                    spinnerCentros.setAdapter(adapter);
                }
            }
        });
    }

    public void listar(){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                Estructura_BBDD.COD_CENTRO,
                Estructura_BBDD.NOMBRE_CENTRO,
                Estructura_BBDD.ID_USER_CENTRO,
                Estructura_BBDD.CONTRASEÑA_CENTRO,
                Estructura_BBDD.CIUDAD_CENTRO,
                Estructura_BBDD.NUM_ALUMNOS
        };

        String selection = Estructura_BBDD.COD_CENTRO;

        try {
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_CENTROS,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null
            );

            while (!c.isLast()){
                c.moveToNext();
                listaCentros.add(new Centro(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getString(4),c.getInt(5)));
            }
        }catch (Exception e){
            Toast.makeText(this, "Ocurrió un error al encontrar centros.", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverEliminarCentros(View view){ finish(); }
}