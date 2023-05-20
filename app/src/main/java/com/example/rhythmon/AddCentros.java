package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddCentros extends AppCompatActivity {

    private EditText nombre, id_user, contraseña, ciudad, numAlumnos;
    private Button btnAdd;
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_centros);

        nombre = findViewById(R.id.etNombreAddCentros);
        id_user = findViewById(R.id.etIDUserAdd);
        contraseña = findViewById(R.id.etPasswordAddCentro);
        ciudad = findViewById(R.id.etCiudadAddCentro);
        numAlumnos = findViewById(R.id.etNumAlumAddCentro);

        btnAdd = findViewById(R.id.btnAddCentro);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();

                ContentValues values = new ContentValues();

                if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, contraseña, ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user, contraseña, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user, contraseña, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user, ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, contraseña, ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, contraseña, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, contraseña, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, contraseña, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, contraseña, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: contraseña, ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, id_user.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (nombre.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()){
                    Toast.makeText(AddCentros.this, "No has rellenado: nombre, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, contraseña.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: id_user, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: contraseña, ciudad.", Toast.LENGTH_SHORT).show();
                }
                else if (contraseña.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: contraseña, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else if (ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(AddCentros.this, "No has rellenado: ciudad, número alumnos.", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (nombre.getText().toString().isEmpty()) {
                        Toast.makeText(AddCentros.this, "No has rellenado: nombre.", Toast.LENGTH_SHORT).show();
                    }
                    else if (id_user.getText().toString().isEmpty()) {
                        Toast.makeText(AddCentros.this, "No has rellenado: id_user.", Toast.LENGTH_SHORT).show();
                    }
                    else if (contraseña.getText().toString().isEmpty()) {
                        Toast.makeText(AddCentros.this, "No has rellenado: contraseña.", Toast.LENGTH_SHORT).show();
                    }
                    else if (ciudad.getText().toString().isEmpty()) {
                        Toast.makeText(AddCentros.this, "No has rellenado: ciudad.", Toast.LENGTH_SHORT).show();
                    }
                    else if (numAlumnos.getText().toString().isEmpty()) {
                        Toast.makeText(AddCentros.this, "No has rellenado: número alumnos.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        // En este punto, todos los campos están llenos.
                        // Le damos el valor de los EditText a los values que vamos a insertar
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        // Realizamos el insert en la base de datos
                        db.insert(Estructura_BBDD.TABLE_CENTROS, null, values);
                        // Mostramos un toast por pantalla para que el usuario sepa que se ha realizado bien
                        Toast.makeText(AddCentros.this, "Registro guardado correctamente.", Toast.LENGTH_SHORT).show();
                        // Vaciamos los editText utilizados
                        nombre.setText("");
                        id_user.setText("");
                        contraseña.setText("");
                        ciudad.setText("");
                        numAlumnos.setText("");
                    }
                }
            }
        });
    }

    public void volverAddCentros(View view){ finish(); }
}