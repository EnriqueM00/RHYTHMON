package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import CRUD_Users.ActualizarAlumnos;
import CRUD_Users.AddAlumnos;
import CRUD_Users.EliminarAlumno;
import CRUD_Users.ListarAlumnos;

// CLASE "GestionCentro"
public class GestionCentro extends AppCompatActivity {

    // Varibles globales
    private int codCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gestion_centro);
        // Declaramos un Bundle y le asignamos los Extras que pasamos de la otra activity
        Bundle b = getIntent().getExtras();
        // Asignamos a nuestra variable global el valor de la clave "codCentro"
        codCentro = b.getInt("codCentro");
    }

    /*
    *
    * @param view
    *
    */
    // Metodo para abrir "ListarAlumnos"
    public void abrirListarAlumnos(View view){
        // Declaramos e inicializamos un objeto Intent donde le pasamos como parametro el Context de la actual Activity y a el de donde queremos ir
        Intent i = new Intent(this, ListarAlumnos.class);
        // Creamos un nuevo Bundle para pasar información de una Activity a otra
        Bundle b = new Bundle();
        // Añadimos al bundle la clave y valor de la clave
        b.putInt("codCentro", codCentro);
        // Añadimos el Bundle como un extra al Intent
        i.putExtras(b);
        // Empezamos la nueva activity con el Intent
        startActivity(i);
        finish();
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "AddAlumnos"
    public void abrirAddAlumnos(View view){
        Intent i = new Intent(this, AddAlumnos.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
        finish();
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "ActualizarAlumnos"
    public void abrirActualizarAlumnos(View view){
        Intent i = new Intent(this, ActualizarAlumnos.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
        finish();
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para abrir "EliminarAlumnos"
    public void abrirEliminarAlumnos(View view){
        Intent i = new Intent(this, EliminarAlumno.class);

        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);

        startActivity(i);
        finish();
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para volver a la activity anterior y cerrar la actual
    public void volverGestionCentro(View view){
        Intent i = new Intent(this, InicioCentro.class);
        startActivity(i);
        finish();
    }
}