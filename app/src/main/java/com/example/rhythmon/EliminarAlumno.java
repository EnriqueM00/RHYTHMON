package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class EliminarAlumno extends AppCompatActivity {

    private ArrayList<Alumno> listaAlumnos;
    private Spinner spinnerAlumnos;
    private BBDD_Helper helper = new BBDD_Helper(this);
    private Button btnEliminar;
    private int codCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_alumno);
        Bundle b = getIntent().getExtras();
        codCentro = b.getInt("codCentro");
        listaAlumnos = new ArrayList<Alumno>();
        listar();
        spinnerAlumnos =  findViewById(R.id.spinnerDeleteAlumnos);
        ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
        spinnerAlumnos.setAdapter(adapter);
        btnEliminar = findViewById(R.id.btnEliminarAlumnos);
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLiteDatabase db = helper.getWritableDatabase();
                String selection = Estructura_BBDD.COD_ALUMNO + " LIKE ?";
                String[] selectionArgs = { String.valueOf(listaAlumnos.get(spinnerAlumnos.getSelectedItemPosition()).getCodAlumno()) };
                db.delete(Estructura_BBDD.TABLE_ALUMNOS, selection, selectionArgs);
                Toast.makeText(EliminarAlumno.this, "Registro borrado con exito.", Toast.LENGTH_SHORT).show();

                listaAlumnos.clear();
                listar();
                ArrayAdapter<Alumno> adapter = new ArrayAdapter<Alumno>(EliminarAlumno.this, android.R.layout.simple_spinner_dropdown_item, listaAlumnos);
                spinnerAlumnos.setAdapter(adapter);
            }
        });
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

    public void volverEliminarAlumnos(View view){ finish(); }
}