package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// CLASE "InicioSesion"
public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "InicioCentro"
    public void inicioCentro(View view){
        Intent i = new Intent(this,InicioCentro.class);
        startActivity(i);
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "InicioUsuario"
    public void inicioUsuario(View view){
        Intent i = new Intent(this,InicioUsuario.class);
        startActivity(i);
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para volver a la Activity anterior y cerrar la actual
    public void volverInicioSesion(View view){
        finish();
    }
}