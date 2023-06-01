package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Alumno;
import MiniJuego.Preconfig;

// CLASE "InicioCentro"
public class InicioUsuario extends AppCompatActivity {

    //Variables globales
    private ArrayList<Alumno> listaAlumnos; //Declaramos un ArrayList de Alumno (usearemos como lista)
    private EditText etIDUser, etPassword; //Declaramos los EditText que referencian los campos necesarios para el Inicio de Sesión
    private Button btnLogueo; //Declaramos el boton
    private BBDD_Helper helper = new BBDD_Helper(this); // Creamos un objeto BBDD_Helper (gestionar BD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);
        // Inicializamos el ArrayList
        listaAlumnos = new ArrayList<Alumno>();
        // Llamamos al metodo volcado para rellenar el ArrayList
        volcado();
        etIDUser = findViewById(R.id.etUserAlumno);
        etPassword = findViewById(R.id.etPasswordAlumno);
        btnLogueo = findViewById(R.id.btnLogueoAlumno);

        //Seteamos un escuchador de Clicks para "btnLogueo"
        btnLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            // ACCIÓN BOTON:
            // Comprueba que los datos introducidos en los campos corresponden con alguno de la base de datos
            // e inicia la Activity correspondiente (Precondig)
            public void onClick(View v) {
                // Declaramos dos Strings donde guardaremos los datos introducidos por el Usuario
                String user = etIDUser.getText().toString();
                String password = etPassword.getText().toString();
                boolean usuarioEncontrado = false;
                // Hacemos for each para recorrer los elementos de la listaAlumnos
                for (Alumno a : listaAlumnos){
                    // Comprueba si el usuario introducido corresponde con alguno de la BD
                    if (a.getId_user().equals(user)){
                        // Comprueba si el password corresponde con el elemento que acabamos de comprobar
                        if (a.getContraseña().equals(password)){
                            // Creamos un Intent que va de esta Activity a Preconfig
                            Intent i = new Intent(InicioUsuario.this, Preconfig.class);
                            Bundle b = new Bundle();
                            // Pasamos el codAlumno al Bundle
                            b.putInt("codAlumno",a.getCodAlumno());
                            i.putExtras(b);
                            startActivity(i);
                            usuarioEncontrado = true;

                            etIDUser.setText("");
                            etPassword.setText("");
                            finish();
                        }
                        // Si no es esa la contraseña se sale del bucle por si otro usuario tuviera la misma contraseña
                        else{
                            // Muestra un mensaje de aviso de que la contraseña es incorrecta
                            Toast.makeText(InicioUsuario.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                            // Salimos del bucle
                            break;
                        }
                    }

                }
                if (!usuarioEncontrado) {
                    Toast.makeText(InicioUsuario.this, "Usuario incorrecto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Metodo donde rellenamos el ArrayList con los datos de la BD
    private void volcado(){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder leer en la BD
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos un Array de Strings con los campos del Select que queremos recoger
        String[] projection = {
                Estructura_BBDD.COD_ALUMNO,
                Estructura_BBDD.ID_USER_ALUMNO,
                Estructura_BBDD.NOMBRE_ALUMNO,
                Estructura_BBDD.APELLIDOS_ALUMNO,
                Estructura_BBDD.CONTRASEÑA_ALUMNO,
                Estructura_BBDD.ANIOS,
                Estructura_BBDD.COD_CENTRO_ALUMNO
        };
        // Controlammos el cursor por si falla con un try - catch
        try{
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_ALUMNOS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            //Bucle while. Mientras que no sea el ultimo...
            while(!c.isLast()){
                // Corremos una posición hacia delante al cursor
                c.moveToNext();
                // Añadimos un nuevo Alumno al ArrayList con las columnas obtenidas
                listaAlumnos.add(new Alumno(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6)));
            }
            // Cerramos el cursor
            c.close();
        }catch (Exception e){
            // Mostramos un mensaje de error si no hay ningun Alumno
            Toast.makeText(InicioUsuario.this, "No hay ningún usuario.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *
     * @param view
     *
     */
    // Metodo para volver a la pagina anterior y cerrar la actual
    public void volverInicioUsuario(View view){
        Intent i = new Intent(this, IniciarSesion.class);
        startActivity(i);
        finish();
    }
}