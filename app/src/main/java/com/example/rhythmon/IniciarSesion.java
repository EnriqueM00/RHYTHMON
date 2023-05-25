package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class IniciarSesion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

    }

    public void inicioCentro(View view){
        Intent i = new Intent(this,InicioCentro.class);
        startActivity(i);
    }

    public void inicioUsuario(View view){
        Intent i = new Intent(this,InicioUsuario.class);
        startActivity(i);
    }

    public void salir(View view){
        finish();
    }
}