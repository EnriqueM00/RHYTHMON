package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddAlumnos extends AppCompatActivity {

    private EditText nombre, apellidos, id_user, contraseña, anios;
    private int codCentro;
    private Button btnAdd;
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_alumnos);
        Bundle b = getIntent().getExtras();
        codCentro = b.getInt("codCentro");

        nombre = findViewById(R.id.etNombreAddAlumno);
        apellidos = findViewById(R.id.etApellidosAddAlumno);
        id_user = findViewById(R.id.etIDUserAddAlumno);
        contraseña = findViewById(R.id.etPasswordAddAlumno);
        anios = findViewById(R.id.etAniosAddAlumno);

        btnAdd = findViewById(R.id.btnAddAlumno);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();

                if (nombre.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    Toast.makeText(AddAlumnos.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
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

    public void volverAddAlumnos(View view){ finish(); }
}