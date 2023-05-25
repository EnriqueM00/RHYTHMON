package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class ActualizarCentros extends AppCompatActivity {

    private ArrayList<Centro> listaCentro;
    private EditText nombre, id_user, contraseña, ciudad, numAlumnos;
    private Button btnActualizar;
    private Spinner spinnerCentros;
    private BBDD_Helper helper = new BBDD_Helper(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_centros);
        listaCentro = new ArrayList<Centro>();
        nombre = findViewById(R.id.etNombreActualizarCentros);
        id_user = findViewById(R.id.etIDUserActualizarCentros);
        contraseña = findViewById(R.id.etPasswordActualizarCentro);
        ciudad = findViewById(R.id.etCiudadActualizarCentro);
        numAlumnos = findViewById(R.id.etNumAlumActualizarCentro);
        btnActualizar = findViewById(R.id.btnActualizarCentros);
        spinnerCentros = findViewById(R.id.spinnerCentrosUpdate);
        listar();
        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_dropdown_item, listaCentro);
        spinnerCentros.setAdapter(adapter);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ContentValues values = new ContentValues();

                if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && ciudad.getText().toString().isEmpty()
                        && numAlumnos.getText().toString().isEmpty()) {
                    Toast.makeText(ActualizarCentros.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && ciudad.getText().toString().isEmpty() && numAlumnos.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
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
                    else {
                        values.put(Estructura_BBDD.NOMBRE_CENTRO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_CENTRO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_CENTRO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.CIUDAD_CENTRO, ciudad.getText().toString());
                        values.put(Estructura_BBDD.NUM_ALUMNOS, numAlumnos.getText().toString());
                        actualizar(values);
                    }
                }
                nombre.setText("");
                id_user.setText("");
                contraseña.setText("");
                ciudad.setText("");
                numAlumnos.setText("");
            }
        });
    }


    public void actualizar(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = Estructura_BBDD.COD_CENTRO + " LIKE ?";

        String[] selectionArgs = { String.valueOf(listaCentro.get(spinnerCentros.getSelectedItemPosition()).getCodCentro()) };

        db.update(Estructura_BBDD.TABLE_CENTROS, values, selection, selectionArgs);

        Toast.makeText(this, "Registro actualizado com exito.", Toast.LENGTH_SHORT).show();

        listaCentro.clear();

        listar();

        ArrayAdapter<Centro> adapter = new ArrayAdapter<Centro>(this, android.R.layout.simple_spinner_dropdown_item, listaCentro);

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
            c.close();
        }catch (Exception e){
            Toast.makeText(this, "Ocurrió un error al encontrar centros.", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverActualizarCentros(View view){ finish(); }
}