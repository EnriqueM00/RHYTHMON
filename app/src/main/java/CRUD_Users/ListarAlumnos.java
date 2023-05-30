package CRUD_Users;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhythmon.AdapterAlumnos;
import com.example.rhythmon.R;

import java.util.ArrayList;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Alumno;

// CLASE "ListarAlumnos"
public class ListarAlumnos extends AppCompatActivity {

    //Variables globales
    private ArrayList<Alumno> listaAlumnos; // Declaramos el ArrayList de Alumno (usado como lista)
    private RecyclerView rvAlumnos; // Declaramos un RecyclerView donde mostraremos los Alumnos
    private TextView tvTotal; // Declaramos el TextView para mostrar cuantos Alumnos hay en total
    private BBDD_Helper helper = new BBDD_Helper(this); // Declaramos un BBDD_Helper (gestionar BD)
    private int codCentro; // Declaramos codCentro

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alumnos);
        // Declaramos un Bundle que igualaremos al Intent de pasar de una Activity a otra y recogemos los extras (datos) que le
        // pasamos de la otra ventana
        Bundle b = getIntent().getExtras();
        // Le damos el valor de la clave codCentro a la variable global
        codCentro = b.getInt("codCentro");
        rvAlumnos = findViewById(R.id.rvAlumnos);
        tvTotal = findViewById(R.id.tvTotalAlumnos);
        // Seteamos un manejador de layout al RecyclerView con los parametros (layaut actual, como va a ser el manejador, si el layout esta a la inversa)
        rvAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // Inicializamos el ArrayList
        listaAlumnos = new ArrayList<Alumno>();
        // llamamos al metodo listar() para cargar los datos de la BD en el ArrayList
        listar();
        // Declaramos e Inicializamos el AdapterAlumnos pasandole el ArrayList que rellenamos antes
        AdapterAlumnos adapter = new AdapterAlumnos(listaAlumnos);
        // Seteamos el adapter al RecyclerView para que se muestren como queremos los datos
        rvAlumnos.setAdapter(adapter);
        // Seteamos el TextView con el tamaño de los alumnos
        tvTotal.setText("Hay un total de: " + Integer.toString(listaAlumnos.size()) + " Alumnos." );
    }

    // Metodo listar() rellenamos el ArrayList con los datos de la BD
    public void listar(){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder leer en la BD
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos un Array de Strings con los campos del Select que queremos recoger
        String[] projection = {
                Estructura_BBDD.ID_USER_ALUMNO,
                Estructura_BBDD.NOMBRE_ALUMNO,
                Estructura_BBDD.APELLIDOS_ALUMNO,
                Estructura_BBDD.CONTRASEÑA_ALUMNO,
                Estructura_BBDD.ANIOS,
                Estructura_BBDD.COD_CENTRO_ALUMNO
        };
        // Declaramos la condición de la sentencia del select
        String selection = Estructura_BBDD.COD_CENTRO_ALUMNO + " LIKE ?";
        // Declaramos el argumento (codCentro) de la sentencia (el ? de arriba)
        String[] selectionArgs = {String.valueOf(codCentro)};
        // Controlamos el cursor por si falla con un try - catch
        try {
            // Declaramos un cursor que le vamos a igualar a una sentencia pasandole los datos que hemos declarado anteriormente
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_ALUMNOS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            //Bucle while. Mientras que no sea el último hace...
            while (!c.isLast()){
                //Corremos una posición hacia delante al cursor
                c.moveToNext();
                // Añadimos un nuevo Alumno al ArrayList con las columnas obtenidas
                listaAlumnos.add(new Alumno(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4),c.getInt(5)));
            }
            // Cerramos el cursor
            c.close();
        }catch (Exception e){
            // Mostramos un mensaje de error si no hay ningún Alumno
            Toast.makeText(this, "No hay ningún alumno.", Toast.LENGTH_SHORT).show();
        }
    }

    /*
     *
     * @param view
     *
    */
    // Metodo para volver a la pagina anterior y cerrar la actual
    public void volverListarAlumnos(View view){ finish(); }
}