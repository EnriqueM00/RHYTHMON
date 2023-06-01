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
import Clases_BBDD.Centro;

// CLASE "InicioCentro"
public class InicioCentro extends AppCompatActivity {

    //Variables globales
    private ArrayList<Centro> listaCentros; // Declaramos un ArrayList (usaremos como lista)
    private EditText etIDUser, etPassword; // Declaramos los EditText que referencian los campos necesarios para el Inicio de Sesion
    private Button btnLogueo; // Declaramos el boton
    private BBDD_Helper helper = new BBDD_Helper(this); // Creamos un objeto BBDD_Helper (gestionar BD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_centro);
        // Inicializamos el ArrayList
        listaCentros = new ArrayList<Centro>();
        // Llamamos al metodo volcado para llenar el ArrayList
        volcado();
        etIDUser = findViewById(R.id.etUserCentro);
        etPassword = findViewById(R.id.etPasswordCentro);
        btnLogueo = findViewById(R.id.btnLogueoCentro);

        //Seteamos un escuchador de Clicks para "btnLogueo"
        btnLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            // ACCIÓN BOTON:
            // Comprueba que los datos introducidos en los campos corresponden con alguno de la base de datos
            // e inicia una Activity dependiendo quien se loguee (GestionCentros -> root; GestionCentro -> 1 centro)
            public void onClick(View v) {
                // Declaramos dos Strings donde guardaremos los datos introducidos por el Usuario
                String user = etIDUser.getText().toString();
                String password = etPassword.getText().toString();
                boolean centroEncontrado = false;
                // Hacemos un for each para recorrer los elementos de la listaCentros
                for (Centro c : listaCentros) {
                    // Comprobamos si el user introducido corresponde con alguno de la BD
                    if (c.getId_user().equals(user)) {
                        // Comprobamos si el password corresponde con el elemento que acabamos de comprobar
                        if (c.getContraseña().equals(password)) {
                            // Comprobamos si el usuario que esta Iniciando sesion es el root o es un centro normal
                            if (c.getId_user().equals("root") && c.getContraseña().equals("root1234")) {
                                // Creamos un intent que va de esta Activity a GestionCentros
                                Intent i = new Intent(InicioCentro.this, GestionCentros.class);
                                startActivity(i);
                                centroEncontrado = true;
                                // Vaciamos los campos utilizados
                                etIDUser.setText("");
                                etPassword.setText("");
                                finish();
                            } else {
                                // Creamos un intent que va de esta Activity a GestionCentro
                                Intent i = new Intent(InicioCentro.this, GestionCentro.class);

                                Bundle b = new Bundle();
                                // Introducimos en el Bundle el codCentro del Centro
                                b.putInt("codCentro", c.getCodCentro());
                                i.putExtras(b);

                                startActivity(i);
                                centroEncontrado = true;
                                // Vaciamos los campos utilizados
                                etIDUser.setText("");
                                etPassword.setText("");
                                finish();
                            }
                        }
                        // Si no es esa la contraseña se sale del bucle por si otro usuario tuviera la misma contraseña
                        else{
                            // Muestra un mensaje de aviso de que la contraseña es incorrecta
                            Toast.makeText(InicioCentro.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                            // Salimos del bucle
                            break;
                        }
                    }
                }
                if (!centroEncontrado){
                    // Cuando termina el Bucle mostramos un mensaje de error, quiere decir que no llegamos a encontrar un Usuario
                    Toast.makeText(InicioCentro.this, "Usuario incorrecto.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // Metodo volcado donde rellenamos el ArrayList con los datos de la BD
    private void volcado(){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder leer en la BD
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos un Array de Strings con los campos del Select que queremos recoger
        String[] projection = {
                Estructura_BBDD.COD_CENTRO,
                Estructura_BBDD.NOMBRE_CENTRO,
                Estructura_BBDD.ID_USER_CENTRO,
                Estructura_BBDD.CONTRASEÑA_CENTRO,
                Estructura_BBDD.CIUDAD_CENTRO,
                Estructura_BBDD.NUM_ALUMNOS
        };
        // Controlamos el cursor por si falla con un Try - Catch
        try{
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_CENTROS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            // Bucle while. Mientras que no sea el ultimo...
            while(!c.isLast()){
                // Corremos una posición hacia delante al cursos
                c.moveToNext();
                // Añadimos un nuevo Centro al ArrayList con las columnas obtenidas
                listaCentros.add(new Centro(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)));
            }
            // Cerramos el cursor
            c.close();

        }catch (Exception e){
            // Mostramos un mensaje de error si no hay ningún Centro
            Toast.makeText(InicioCentro.this, "No hay ningún centro.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *
     * @param view
     *
    */
    // Metodo para volver a la pagina anterior y cerrar la actual
    public void volverInicioCentro(View view){
        Intent i = new Intent(this, IniciarSesion.class);
        startActivity(i);
        finish();
    }
}