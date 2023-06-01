package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import CRUD_Users.ActualizarCentros;
import CRUD_Users.AddCentros;
import CRUD_Users.EliminarCentros;

// CLASE "GestionCentros"
public class GestionCentros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_centros);
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "AddCentros"
    public void añadirCentros(View view){
        Intent i = new Intent(this, AddCentros.class);
        startActivity(i);
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "ActualizarCentros"
    public void actualizarCentros(View view){
        Intent i = new Intent(this, ActualizarCentros.class);
        startActivity(i);
        finish();
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "EliminarCentros"
    public void eliminarCentros(View view){
        Intent i = new Intent(this, EliminarCentros.class);
        startActivity(i);
        finish();
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para volver a la Activity anterior y cerrar la actual
    public void volverGestionCentros(View view){
        Intent i = new Intent(this, InicioCentro.class);
        startActivity(i);
        finish();
    }
}