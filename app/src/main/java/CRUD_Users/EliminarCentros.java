package CRUD_Users;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rhythmon.R;

import java.util.ArrayList;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Centro;

// CLASE "EliminarCentros"
public class EliminarCentros extends AppCompatActivity {

    //Variables globales
    private ArrayList<Centro> listaCentros; //Declaramos un ArrayList de Centros (usaremos como lista para los datos de Centro de la BD)
    private Spinner spinnerCentros; // Declaramos el Spinner
    private BBDD_Helper helper = new BBDD_Helper(this); // Declaramos e inicializamos un objeto BBDD_Helper (gestionar BD)
    private Button btnEliminar; // Declaramos el boton

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_centros);
        // Inicializamos el ArrayList
        listaCentros = new ArrayList<Centro>();
        // Llamamos al metodo listar() para rellenar el ArrayList
        listar();
        // Creamos un ArrayAdapter para la clase Centro
        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_dropdown_item, listaCentros);
        spinnerCentros = findViewById(R.id.spinnerDeleteCentros);
        // Seteamos el adapter al Spinner
        spinnerCentros.setAdapter(adapter);
        btnEliminar = findViewById(R.id.btnEliminarCentros);
        // Seteamos un escuchador de Clicks al "btnEliminar"
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            // ACCIÓN BOTON:
            // Eliminamos el Centro que hemos seleccionado en el Spinner
            public void onClick(View view) {
                // Comprobamos que no sea el administrador "root"
                if (listaCentros.get(spinnerCentros.getSelectedItemPosition()).getId_user().equals("root")){
                    // Si ha seleccionado ese no hacemo nada y mostramos un mesaje de error
                    Toast.makeText(EliminarCentros.this, "No tienes permisos para eliminar al Administrador.", Toast.LENGTH_SHORT).show();
                }
                else {
                    // Declaramos e inicializamos un objeto SQLiteDatabase y le decimos que vamos a escribir en la BD
                    SQLiteDatabase db = helper.getWritableDatabase();
                    // Declaramos el argumento de la sentencia
                    String selection = Estructura_BBDD.COD_CENTRO + " LIKE ?";
                    // Declaramos el parametro para el argumento
                    String[] selectionArgs = { String.valueOf(listaCentros.get(spinnerCentros.getSelectedItemPosition()).getCodCentro()) };
                    // Utilizamos el metodo delete() de la clase SQLiteDatabase que hereda nuestro Helper
                    db.delete(Estructura_BBDD.TABLE_CENTROS, selection, selectionArgs);
                    // Mostramos un mensaje de exito
                    Toast.makeText(EliminarCentros.this, "Registro borrado con exito.", Toast.LENGTH_SHORT).show();
                    // Limpiamos / Vaciamos el ArrayList
                    listaCentros.clear();
                    // Llamamos al metodo listar() para rellenar la lista con los cambios realizados
                    listar();
                    // Declaramos e inicializamos otra vez el Adapter para el Spinner
                    ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(EliminarCentros.this, android.R.layout.simple_spinner_dropdown_item, listaCentros);
                    // Seteamos el adapter al Spinner
                    spinnerCentros.setAdapter(adapter);
                }
            }
        });
    }

    // Metodo para rellenar el ArrayList con los registros de la BD (tabla Centro)
    public void listar(){
        // Declaramos e inicializamos el objeto SQLiteDatabase y le decimos que va a leer la BD
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos los campos que va a recoger el SELECT
        String[] projection = {
                Estructura_BBDD.COD_CENTRO,
                Estructura_BBDD.NOMBRE_CENTRO,
                Estructura_BBDD.ID_USER_CENTRO,
                Estructura_BBDD.CONTRASEÑA_CENTRO,
                Estructura_BBDD.CIUDAD_CENTRO,
                Estructura_BBDD.NUM_ALUMNOS
        };

        // Controlamos la ejecuación de la sentencia con Try - Catch
        try {
            // Declaramos e inicializamos un cursor con el metodo query() que nos devuelve un cursor
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_CENTROS,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // Cuando no sea el ultimo que haga...
            while (!c.isLast()){
                // Movemos el cursor a la siguiente posición
                c.moveToNext();
                // Añadimos al ArrayList un nuevo Centro con las columnas obtenidas por el cursor
                listaCentros.add(new Centro(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getString(4),c.getInt(5)));
            }
            // Cerramos el cursor
            c.close();

        }catch (Exception e){
            // Si algo sale mal mostramos un mensaje de error
            Toast.makeText(this, "Ocurrió un error al encontrar centros.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    *
    * @param view
    *
    * */
    // Metodo para volver a la pagina anterior y cerrar la actual
    public void volverEliminarCentros(View view){ finish(); }
}