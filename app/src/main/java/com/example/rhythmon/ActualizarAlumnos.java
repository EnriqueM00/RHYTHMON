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

public class ActualizarAlumnos extends AppCompatActivity {

    private ArrayList<Alumno> listaAlumnos;
    private EditText nombre, apellidos, id_user, contraseña, anios;
    private int codCentro;
    private Button btnActualizar;
    private Spinner spinnerAlumnos;
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar_alumnos);
        Bundle b = getIntent().getExtras();
        codCentro = b.getInt("codCentro");
        listaAlumnos = new ArrayList<Alumno>();
        nombre = findViewById(R.id.etNombreActualizarAlumnos);
        apellidos = findViewById(R.id.etApellidosActualizarAlumnos);
        id_user = findViewById(R.id.etIDUserActualizarAlumnos);
        contraseña = findViewById(R.id.etPasswordActualizarAlumnos);
        anios = findViewById(R.id.etAniosActualizarAlumnos);
        btnActualizar = findViewById(R.id.btnActualizarAlumnos);
        spinnerAlumnos = findViewById(R.id.spinnerAlumnosUpdate);
        listar();
        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
        spinnerAlumnos.setAdapter(adapter);

        btnActualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues values = new ContentValues();

                if (nombre.getText().toString().isEmpty() && id_user.getText().toString().isEmpty()
                        && contraseña.getText().toString().isEmpty() && apellidos.getText().toString().isEmpty()
                        && anios.getText().toString().isEmpty()) {
                    Toast.makeText(ActualizarAlumnos.this, "No has rellenado los campos requeridos.", Toast.LENGTH_SHORT).show();
                }
                else if (id_user.getText().toString().isEmpty() && contraseña.getText().toString().isEmpty()
                        && apellidos.getText().toString().isEmpty() && anios.getText().toString().isEmpty()) {
                    values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
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
                    else {
                        values.put(Estructura_BBDD.NOMBRE_ALUMNO, nombre.getText().toString());
                        values.put(Estructura_BBDD.ID_USER_ALUMNO, id_user.getText().toString());
                        values.put(Estructura_BBDD.CONTRASEÑA_ALUMNO, contraseña.getText().toString());
                        values.put(Estructura_BBDD.APELLIDOS_ALUMNO, apellidos.getText().toString());
                        values.put(Estructura_BBDD.ANIOS, anios.getText().toString());
                        actualizar(values);
                    }
                }
                nombre.setText("");
                id_user.setText("");
                contraseña.setText("");
                apellidos.setText("");
                anios.setText("");
            }
        });
    }

    public void actualizar(ContentValues values){
        SQLiteDatabase db = helper.getWritableDatabase();

        String selection = Estructura_BBDD.COD_ALUMNO + " LIKE ?";

        String[] selectionArgs = { String.valueOf(listaAlumnos.get(spinnerAlumnos.getSelectedItemPosition()).getCodAlumno()) };

        db.update(Estructura_BBDD.TABLE_ALUMNOS, values, selection, selectionArgs);

        Toast.makeText(this, "Registro actualizado com exito.", Toast.LENGTH_SHORT).show();

        listaAlumnos.clear();

        listar();

        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);

        spinnerAlumnos.setAdapter(adapter);
    }

    public void listar(){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                Estructura_BBDD.COD_ALUMNO,
                Estructura_BBDD.ID_USER_ALUMNO,
                Estructura_BBDD.NOMBRE_ALUMNO,
                Estructura_BBDD.APELLIDOS_ALUMNO,
                Estructura_BBDD.CONTRASEÑA_ALUMNO,
                Estructura_BBDD.ANIOS,
                Estructura_BBDD.COD_CENTRO_ALUMNO
        };

        String selection = Estructura_BBDD.COD_CENTRO_ALUMNO + " LIKE ?";

        String[] selectionArgs = {String.valueOf(codCentro)};

        try {
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_ALUMNOS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            while (!c.isLast()){
                c.moveToNext();
                listaAlumnos.add(new Alumno(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6)));
            }
            c.close();

        }catch (Exception e){
            Toast.makeText(this, "No hay ningún alumno.", Toast.LENGTH_SHORT).show();
        }

    }

    public void volverActualizarAlumnos(View view){ finish(); }
}