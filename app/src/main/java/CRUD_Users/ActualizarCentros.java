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
import com.example.rhythmon.GestionCentros;
import com.example.rhythmon.R;

import java.util.ArrayList;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Centro;

// CLASE "ActualizarCentros"
public class ActualizarCentros extends AppCompatActivity {

    //Variables globales
    private ArrayList<Centro> listaCentro; // Declaramos el ArrayList de Centro (usaremos como una lista)
    private EditText nombre, id_user, contraseña, ciudad, numAlumnos; // Declaramos los EditText que referencian los campos que pueden actualizarse
    private Button btnActualizar; // Declaramos el Boton
    private Spinner spinnerCentros;// Declaramos el Spinner (cargaremos ahi la lista de centros para seleccionar cual vamos a actualizar)
    private BBDD_Helper helper = new BBDD_Helper(this); //Declaramos un BBDD_Helper (gestionar BD)


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_centros);
        // Inicializamos el Arraylist
        listaCentro = new ArrayList<Centro>();
        // Inicializamos todos los elementos necesarios para trabajar con la clase{
        nombre = findViewById(R.id.etNombreActualizarCentros);
        id_user = findViewById(R.id.etIDUserActualizarCentros);
        contraseña = findViewById(R.id.etPasswordActualizarCentro);
        ciudad = findViewById(R.id.etCiudadActualizarCentro);
        numAlumnos = findViewById(R.id.etNumAlumActualizarCentro);
        btnActualizar = findViewById(R.id.btnActualizarCentros);
        //}
        // Declaramos el Spinner donde cargaremos la lista de centros
        spinnerCentros = findViewById(R.id.spinnerCentrosUpdate);
        // Llamamos al metodo lista para volvar los datos de los centros en el ArrayList
        listar();
        // Inicializamos un ArrayAdapter para la lista de centros
        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_dropdown_item, listaCentro);
        // Le pasamos el adapter al Spinner
        spinnerCentros.setAdapter(adapter);

        // Seteamos un escuchador de click al "btnActualizar"
        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Instanciamos un objeto ContentValues para añadir los valores que se van a actualizar y referenciarlos con una columna
                ContentValues values = new ContentValues();
                // Comprobamos que campos estan reyenos para ver cuales actualizaremos
                if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    // Mostramos un mensaje de error si no ha rellenado ningun campo
                    Toast.makeText(ActualizarCentros.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    // Ponemos los valores a actualizar en el objeto para que se relacione con una columna de la tabla q actualizaremos y con el valor a actualizar
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    // Llamamos al metodo actualizar y le introduciomos objeto ContentValues (values)
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (nombre.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()){
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (id_user.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                    actualizar(values);
                }
                else if (contraseña.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                    actualizar(values);
                }
                else if (ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                    values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                    values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                    actualizar(values);
                }
                else {
                    if (nombre.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        actualizar(values);
                    }
                    else if (id_user.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        actualizar(values);
                    }
                    else if (contraseña.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        actualizar(values);
                    }
                    else if (ciudad.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        actualizar(values);
                    }
                    else if (numAlumnos.getText().toString().isEmpty()) {
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        actualizar(values);
                    }
                    // Si estan todos se actualizan todos
                    else {
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        actualizar(values);
                    }
                }
                // Vaciamos los textos
                nombre.setText("");
                id_user.setText("");
                contraseña.setText("");
                ciudad.setText("");
                numAlumnos.setText("");
            }
        });
    }

    /*
     *
     * @param values
     *
     */
    // METODO PARA ACTUALIZAR LOS CENTROS EN LA TABLA CENTROS. ACTUALIZA EL SPINNER Y LA LISTA DE CENTROS.
    public void actualizar(ContentValues values){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder escribir en la BD
        SQLiteDatabase db = helper.getWritableDatabase();
        // Declaramos la condición de la sentencia de actualización
        String selection = Estructura_BBDD.COD_CENTRO + " LIKE ?";
        // Declaramos el argumento (codCentro) de la sentencia (el ? de arriba)
        String[] selectionArgs = { String.valueOf(listaCentro.get(spinnerCentros.getSelectedItemPosition()).getCodCentro()) };
        // Hacemos update pasando los parametros necesarios (tabla, columnas y valores, condición, argumento condición)
        db.update(Estructura_BBDD.TABLE_CENTROS, values, selection, selectionArgs);
        // Muestra un mensaje de aviso
        Toast.makeText(this, "Registro actualizado com exito.", Toast.LENGTH_SHORT).show();
        // Limpiamos / Vaciamos el ArrayList
        listaCentro.clear();
        // Llamamos al metodo "listar()" Volcamos los datos de la BD de nuevo en el ArrayList (listaCentro)
        listar();
        // Inicializamos otra vez el ArrayAdapter de Centro para poder mostrar en el Spinner la listaCentro (ArrayList<Centro>)
        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_dropdown_item, listaCentro);
        // Seteamos el adapter al Spinner
        spinnerCentros.setAdapter(adapter);
    }

    public void listar(){
        // Creamos un objeto SQLiteDatabase y le decimos que es de lectura
        SQLiteDatabase db = helper.getReadableDatabase();

        // Inicializamos las columnas que se van a sacar (SELECT)
        String[] projection = {
                Estructura_BBDD.COD_CENTRO,
                Estructura_BBDD.NOMBRE_CENTRO,
                Estructura_BBDD.ID_USER_CENTRO,
                Estructura_BBDD.CONTRASEÑA_CENTRO,
                Estructura_BBDD.CIUDAD_CENTRO,
                Estructura_BBDD.NUM_ALUMNOS
        };
        // Inicializamos la condicion de busqueda que sera el ID (WHERE)
        String selection = Estructura_BBDD.COD_CENTRO;

        try {
            //Inicializamos el cursos para que vaya recorriendo los diferentes IDs y se nos vaya parando en cada uno
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_CENTROS,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null
            );
            // Mientras que no sea el ultimo va avanzando en la lista
            while (!c.isLast()){
                // El cursor se mueve al siguiente elemento
                c.moveToNext();
                // Añadimos los datos al arrayList global
                listaCentro.add(new Centro(c.getInt(0),c.getString(1),c.getString(2), c.getString(3),c.getString(4),c.getInt(5)));
            }
            // Cerramos el cursor
            c.close();

        }catch (Exception e){
            // Mostramos un mensaje de error si no encontro centros
            Toast.makeText(this, "Ocurrió un error al encontrar centros.", Toast.LENGTH_SHORT).show();
        }
    }

    // Metodo para volver a la otra página y cerrar la actual
    public void volverActualizarCentros(View view){
        Intent i = new Intent(this, GestionCentros.class);
        startActivity(i);
        finish();
    }
}