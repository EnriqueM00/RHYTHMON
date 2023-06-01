package CRUD_Users;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rhythmon.GestionCentro;
import com.example.rhythmon.R;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;

// CLASE "AddAlumnos"
public class AddAlumnos extends AppCompatActivity {

    // Variables globales
    private EditText nombre, apellidos, id_user, contraseña, anios; // Declaramos los EditText que referencian los campos que se van a añadir
    private int codCentro; // Declaramos el codCentro (foreign key) para saber a que centro hay que añadirlo
    private Button btnAdd; // Declaramos el boton
    private BBDD_Helper helper = new BBDD_Helper(this); //Inicializamos un objeto BBDD_Helper (gestionar BD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumnos);
        // Recuperamos los datos de la activity anterior
        Bundle b = getIntent().getExtras();
        // Le asignamos el valor de la clave:codCentro
        codCentro = b.getInt("codCentro");
        // Inicializamos los elementos necesarios para trabajar con la clase{
        nombre = findViewById(R.id.etNombreAddAlumno);
        apellidos = findViewById(R.id.etApellidosAddAlumno);
        id_user = findViewById(R.id.etIDUserAddAlumno);
        contraseña = findViewById(R.id.etPasswordAddAlumno);
        anios = findViewById(R.id.etAniosAddAlumno);
        btnAdd = findViewById(R.id.btnAddAlumno);
        //}
        // seteamos un escuchador de Clicks al boton "btnAdd"
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            // ACCION DEL BOTON:
            // Comprobar si hay algun campo vacio (todas las combinaciones posibles) si es asi nos muestra un aviso de que campos nos faltan por
            // introducir y si no falta ninguno nos añade el Alumno que hayamos facilitado
            public void onClick(View view) {
                // Instanciamos un objeto ContentValues para añadir los valores que se van a actualizar y referenciarlos con una columna
                ContentValues values = new ContentValues();
                // Comprobación de todas las posibles combinaciones de campos vacios y finalización con el caso de que todos estan rellenos
                if (nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    // Muestra un aviso si no has rellenado ningun campo
                    Toast.makeText(AddAlumnos.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    // Muestra un aviso con los campos que no rellenamos
                    Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos, id_user, contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos,id_user, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, id_user, contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos,id_user, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos,contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos, id_user.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, id_user, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: id_user, apellidos, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: id_user, contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos, id_user, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, id_user, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos, contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos, años.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, id_user.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, apellidos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && anios.getText().toString().isEmpty()){
                    Toast.makeText(AddAlumnos.this, "No has rellenado: nombre, años.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: id_user, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos, id_user.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: id_user, años.", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: contraseña, años.", Toast.LENGTH_SHORT).show();
                }
                else if (apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos, años.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (nombre.getText().toString().isEmpty()) {
                        Toast.makeText(AddAlumnos.this, "No has rellenado: nombre.", Toast.LENGTH_SHORT).show();
                    }
                    else if (id_user.getText().toString().isEmpty()) {
                        Toast.makeText(AddAlumnos.this, "No has rellenado: id_user.", Toast.LENGTH_SHORT).show();
                    }
                    else if (contraseña.getText().toString().isEmpty()) {
                        Toast.makeText(AddAlumnos.this, "No has rellenado: contraseña.", Toast.LENGTH_SHORT).show();
                    }
                    else if (apellidos.getText().toString().isEmpty()) {
                        Toast.makeText(AddAlumnos.this, "No has rellenado: apellidos.", Toast.LENGTH_SHORT).show();
                    }
                    else if (anios.getText().toString().isEmpty()) {
                        Toast.makeText(AddAlumnos.this, "No has rellenado: años.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder leer en la BD
                        SQLiteDatabase db = helper.getWritableDatabase();
                        // En este punto, todos los campos están llenos.
                        // Le damos el valor de los EditText a los values que vamos a insertar
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        values.put(Estructura_BBDD.COD_CENTRO_ALUMNO, Integer.toString(codCentro));
                        // Realizamos el insert en la base de datos
                        db.insert(Estructura_BBDD.TABLE_ALUMNOS, null, values);
                        // Mostramos un toast por pantalla para que el usuario sepa que se ha realizado bien
                        Toast.makeText(AddAlumnos.this, "Registro guardado correctamente.", Toast.LENGTH_SHORT).show();
                        // Vaciamos los editText utilizados
                        nombre.setText("");
                        id_user.setText("");
                        contraseña.setText("");
                        apellidos.setText("");
                        anios.setText("");
                    }
                }
            }
        });
    }

    /*
    *
    *
    * @param view
    *
     */
    // Metodo para volver a la pagina anterior y cerrar esta
    public void volverAddAlumnos(View view){
        Intent i = new Intent(this, GestionCentro.class);
        Bundle b = new Bundle();
        b.putInt("codCentro", codCentro);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
}