package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListarAlumnos extends AppCompatActivity {

    private ArrayList<Alumno> listaAlumnos;
    private RecyclerView rvAlumnos;
    private TextView tvTotal;
    private BBDD_Helper helper = new BBDD_Helper(this);
    private int codCentro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_alumnos);
        Bundle b = getIntent().getExtras();
        codCentro = b.getInt("codCentro");
        rvAlumnos = findViewById(R.id.rvAlumnos);
        tvTotal = findViewById(R.id.tvTotalAlumnos);
        rvAlumnos.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        listaAlumnos = new ArrayList<Alumno>();
        listar();
        AdapterAlumnos adapter = new AdapterAlumnos(listaAlumnos);
        rvAlumnos.setAdapter(adapter);
        tvTotal.setText("Hay un total de: " + Integer.toString(listaAlumnos.size()) + " Alumnos." );
    }

    public void listar(){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
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
                listaAlumnos.add(new Alumno(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getInt(4),c.getInt(5)));
            }
            c.close();
        }catch (Exception e){
            Toast.makeText(this, "No hay ningún alumno.", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverListarAlumnos(View view){ finish(); }
}