package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GestionCentro extends AppCompatActivity {

    private int codCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_centro);
        Bundle b = getIntent().getExtras();
        codCentro = b.getInt("codCentro");
    }

    public void abrirListarAlumnos(View view){
        Intent i = new Intent(this, ListarAlumnos.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
    }

    public void abrirAddAlumnos(View view){
        Intent i = new Intent(this, AddAlumnos.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
    }

    public void abrirActualizarAlumnos(View view){
        Intent i = new Intent(this, ActualizarAlumnos.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
    }

    public void abrirEliminarAlumnos(View view){
        Intent i = new Intent(this, EliminarAlumno.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
    }

    public void volverGestionCentro(View view){ finish(); }
}