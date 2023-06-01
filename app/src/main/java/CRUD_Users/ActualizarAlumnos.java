package CRUD_Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rhythmon.GestionCentro;
import com.example.rhythmon.R;

import java.util.ArrayList;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Alumno;

// CLASE "ActualizarAlumnos"
public class ActualizarAlumnos extends AppCompatActivity {

    //Variables globales
    private ArrayList<Alumno> listaAlumnos; // Declaramos un ArrayList de Alumno (usaremos como lista)
    private EditText nombre, apellidos, id_user, contraseña, anios; // Declaramos los EditText que referencian los campos que pueden actualizarse
    private int codCentro; // Declaramos el codCentro (usaremos como foreing Key (clave foranea) para mostrar solo los Alumnos de ese Centro)
    private Button btnActualizar; // Declaramos el Boton
    private Spinner spinnerAlumnos; // Declaramos el Spinner (cargaremos ahi la lista de alumnos para seleccionar cual vamos a actualizar)
    private BBDD_Helper helper = new BBDD_Helper(this); //Declaramos un BBDD_Helper (gestionar BD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_alumnos);
        // Declaramos un Bundle que igualamos al Intent de pasar de una Activity a otra y recogemos los extras (datos) que le pasamos
        // de la otra ventana
        Bundle b = getIntent().getExtras();
        // Le damos el valor del Bundle al int codCentro
        codCentro = b.getInt("codCentro");
        // Inicializamos el Arraylist, pero vacio
        listaAlumnos = new ArrayList<Alumno>();
        // Inicializamos todos los elementos declarados necesarios para trabajar con la clase{
        nombre = findViewById(R.id.etNombreActualizarAlumnos);
        apellidos = findViewById(R.id.etApellidosActualizarAlumnos);
        id_user = findViewById(R.id.etIDUserActualizarAlumnos);
        contraseña = findViewById(R.id.etPasswordActualizarAlumnos);
        anios = findViewById(R.id.etAniosActualizarAlumnos);
        btnActualizar = findViewById(R.id.btnActualizarAlumnos);
        spinnerAlumnos = findViewById(R.id.spinnerAlumnosUpdate);
        //}
        // Llamamos al metodo "listar()" para llenar el ArrayList que mostraremos con el Spinner
        listar();
        // Declaramos e inicializamos un ArrayAdapter para la clase Alumno
        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
        // Le pasamos el adapter por parametro al metodo ".setAdapter()" del Spinner
        spinnerAlumnos.setAdapter(adapter);

        // SETEAMOS UN ESCUCHADOR DE CLICK AL BOTON "btnActualizar"
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instanciamos un objeto ContentValues para añadir los valores que se van a actualizar y referenciarlos con una columna
                ContentValues values = new ContentValues();
                // Comprobamos que campos estan reyenos para ver cuales actualizaremos
                if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()) {
                    // Si no rellena ningun campo mandamos un mensaje para que el usuario sepa que no ha rellenado ningun campo
                    Toast.makeText(ActualizarAlumnos.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    // Ponemos los valores a actualizar en el objeto para que se relacione con una columna de la tabla q actualizaremos y
                    // con el valor a actualizar
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    // Llamamos al metodo actualizar y le introduciomos objeto ContentValues (values)
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && anios.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                    actualizar(values);
                }
                else if (contraseña.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                    actualizar(values);
                }
                else if (apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                    actualizar(values);
                }
                else {
                    if (nombre.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        actualizar(values);
                    }
                    else if (id_user.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        actualizar(values);
                    }
                    else if (contraseña.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        actualizar(values);
                    }
                    else if (apellidos.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        actualizar(values);
                    }
                    else if (anios.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        actualizar(values);
                    }
                    // Si estan todos se actualizan todos
                    else {
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        actualizar(values);
                    }
                }
                // Vaciamos los EditText
                nombre.setText("");
                id_user.setText("");
                contraseña.setText("");
                apellidos.setText("");
                anios.setText("");
            }
        });
    }

    /*
    *
    * @param values
    *
    */
    // METODO PARA ACTUALIZAR LOS ALUMNOS EN LA TABLA ALUMNOS. ACTUALIZA EL SPINNER Y LA LISTA DE ALUMNOS.
    public void actualizar(ContentValues values){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder escribir en la BD
        SQLiteDatabase db = helper.getWritableDatabase();
        // Declaramos la condición de la sentencia de actualización
        String selection = Estructura_BBDD.COD_ALUMNO + " LIKE ?";
        // Declaramos el argumento (codAlumno) de la sentencia (el ? de arriba)
        String[] selectionArgs = { String.valueOf(listaAlumnos.get(spinnerAlumnos.getSelectedItemPosition()).getCodAlumno()) };
        // Hacemos update pasando los parametros necesarios (tabla, columnas y valores, condición, argumento condición)
        db.update(Estructura_BBDD.TABLE_ALUMNOS, values, selection, selectionArgs);
        // Mostramos un mensaje de aviso
        Toast.makeText(this, "Registro actualizado com exito.", Toast.LENGTH_SHORT).show();
        // Limpiamos / vaciamos el Arraylist
        listaAlumnos.clear();
        // Llamamos al metodo "listar()" Volcamos los datos de la BD de nuevo en el ArrayList (listaAlumnos)
        listar();
        // Volvemos a inicializar el adapter al Spinner para que pueda mostrar la lista de alumnos
        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
        // Le pasamos el adapter por al Spinner
        spinnerAlumnos.setAdapter(adapter);
    }

    // Metodo listar() rellenamos el ArrayList con los datos de la BD
    public void listar(){
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
            // Bucle while. Mientras que no sea el último hace...
            while (!c.isLast()){
                // Corremos una posición hacia delante al cursor
                c.moveToNext();
                // Añadimos un nuevo Alumno al arraylist con las columnas obtenidas
                listaAlumnos.add(new Alumno(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6)));
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
    // Metodo para volver a la pagina anterior y cerrar esta
    public void volverActualizarAlumnos(View view){
        Intent i = new Intent(this, GestionCentro.class);
        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}