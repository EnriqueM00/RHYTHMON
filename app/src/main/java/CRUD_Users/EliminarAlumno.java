package CRUD_Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rhythmon.GestionCentro;
import com.example.rhythmon.R;

import java.util.ArrayList;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Alumno;

// CLASE "EliminarAlumno"
public class EliminarAlumno extends AppCompatActivity {

    //Variables globales
    private ArrayList<Alumno> listaAlumnos; //Declaramos un ArrayList de Alumnos
    private Spinner spinnerAlumnos; //Declaramos un Spinner
    private BBDD_Helper helper = new BBDD_Helper(this); //Inicializamos un objeto BBDD_Helper (gestionar BD)
    private Button btnEliminar; //Declaramos el boton
    private int codCentro;  //Declaramos codCentro clave foranea

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_alumno);
        // Recuperamos los datos de la activity anterior
        Bundle b = getIntent().getExtras();
        // Le asignamos el valor de la clave codCentro a nuestra variable
        codCentro = b.getInt("codCentro");
        // Inicializamos el ArrayList
        listaAlumnos = new ArrayList<Alumno>();
        // Llamamos al metodo listar() para rellenar la listaAlumnos
        listar();
        // Inicializamos el Spinner
        spinnerAlumnos =  findViewById(R.id.spinnerDeleteAlumnos);
        // Declaramos e inicializamos un ArrayAdapter para la clase Alumno (usaremos para mostrar el ArrayList en el Spinner)
        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
        // Seteamos el adapter al Spinner
        spinnerAlumnos.setAdapter(adapter);
        btnEliminar = findViewById(R.id.btnEliminarAlumnos);

        // Seteamos un escuchador de Clicks al "btnEliminar"
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            // Accion del Boton:
            // Elimina el Alumno con el codAlumno que recoge del Spinner
            public void onClick(View view) {
                // Declaramos e inicializamos el objeto db y le decimos que queremos escribir en la BD
                SQLiteDatabase db = helper.getWritableDatabase();
                // Declaramos e inicializamos el argumento de la sentencia (WHERE)
                String selection = Estructura_BBDD.COD_ALUMNO + " LIKE ?";
                // Declaramos e inicializamos el parametro para el argumento de la sentencia
                String[] selectionArgs = { String.valueOf(listaAlumnos.get(spinnerAlumnos.getSelectedItemPosition()).getCodAlumno()) };
                // Llamamos al metodo .delete() que hereda el helper ya que Extiende de SQLiteDatabase
                db.delete(Estructura_BBDD.TABLE_ALUMNOS, selection, selectionArgs);
                // Mostramos un mensaje de exito
                Toast.makeText(EliminarAlumno.this, "Registro borrado con exito.", Toast.LENGTH_SHORT).show();
                // Limpiamos / vaciamos el ArrayList
                listaAlumnos.clear();
                // Volvemos a llenar la listaAlummnos que ya no tiene el registro eliminado (actulizamos la lista)
                listar();
                // Volvemos a declarar e inicializar el Adapter
                ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(EliminarAlumno.this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
                // Seteamos el Adapter al Spinner
                spinnerAlumnos.setAdapter(adapter);
            }
        });
    }

    // Metodo para rellenar el ArrayList con los datos de la BD
    public void listar(){
        // Declaramos e inicializamos un objeto SQLiteDatabase y le decimos que vamos a acceder a la BD solo para leer
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos los campos que vamos a obtener (campos del SELECT)
        String[] projection = {
                Estructura_BBDD.COD_ALUMNO,
                Estructura_BBDD.ID_USER_ALUMNO,
                Estructura_BBDD.NOMBRE_ALUMNO,
                Estructura_BBDD.APELLIDOS_ALUMNO,
                Estructura_BBDD.CONTRASEÑA_ALUMNO,
                Estructura_BBDD.ANIOS,
                Estructura_BBDD.COD_CENTRO_ALUMNO
        };
        // Declaramos el argumento de la sentencia (WHERE) sera el codCentro para mostrar solo los Alumnos de ese Centro
        String selection = Estructura_BBDD.COD_CENTRO_ALUMNO + " LIKE ?";
        // Declaramos el parametro para el argumento
        String[] selectionArgs = {String.valueOf(codCentro)};

        // Controlamos ejecución de la sentencia con un Try - Catch
        try {
            // Declaramos e inicializamos un cursor con el metodo query() que nos devuelve un cursor
             Cursor c = db.query(
                    Estructura_BBDD.TABLE_ALUMNOS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            // Cuando no sea el ultimo haga...
            while (!c.isLast()){
                // Mueve el cursor a la siguiente posición
                c.moveToNext();
                // Añadimos al ArrayList un nuevo Alumno con las columnas obtenidas por el cursor
                listaAlumnos.add(new Alumno(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6)));
            }
            // Cerramos el cursor
            c.close();

        }catch (Exception e){
            // Si ha habido algun problema mostramos un mensaje de error
            Toast.makeText(this, "No hay ningún alumno.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
    *
    * @param view
    * */
    // Metodo para volver a la activity anterior cerrando la actual
    public void volverEliminarAlumnos(View view){
        Intent i = new Intent(this, GestionCentro.class);
        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}