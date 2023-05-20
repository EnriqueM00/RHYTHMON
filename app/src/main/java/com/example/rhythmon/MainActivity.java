package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirInicio(View view){
        Intent i = new Intent( this,IniciarSesion.class);
        startActivity(i);
    }

    public void salir(View view){
        new AlertDialog.Builder(this)
                .setTitle("SALIR")
                .setMessage("¿Desea realmente salir de la aplicación?")
                .setPositiveButton("Sí", new  DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}