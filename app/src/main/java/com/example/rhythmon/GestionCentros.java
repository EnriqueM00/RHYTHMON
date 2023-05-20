package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GestionCentros extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_centros);
    }

    public void añadirCentros(View view){
        Intent i = new Intent(this,AddCentros.class);
        startActivity(i);
    }

    public void actualizarCentros(View view){
        Intent i = new Intent(this, ActualizarCentros.class);
        startActivity(i);
    }

    public void eliminarCentros(View view){
        Intent i = new Intent(this, EliminarCentros.class);
        startActivity(i);
    }

    public void volverGestionCentros(View view){ finish(); }
}