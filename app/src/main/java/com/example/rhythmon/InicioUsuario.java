package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class InicioUsuario extends AppCompatActivity {

    private ArrayList<Alumno> listaAlumnos;
    private EditText etIDUser, etPassword;
    private Button btnLogueo;
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_usuario);

        listaAlumnos = new ArrayList<Alumno>();
        volcado();
        etIDUser = findViewById(R.id.etUserAlumno);
        etPassword = findViewById(R.id.etPasswordAlumno);
        btnLogueo = findViewById(R.id.btnLogueoAlumno);

        btnLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etIDUser.getText().toString();
                String password = etPassword.getText().toString();
                for (Alumno a : listaAlumnos){
                    if (a.getId_user().equals(user)){
                        if (a.getContraseña().equals(password)){
                            Intent i = new Intent(InicioUsuario.this, Preconfig.class);
                            startActivity(i);
                        }
                        else{
                            Toast.makeText(InicioUsuario.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(InicioUsuario.this, "Usuario incorrecto.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void volcado(){
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

        String selection = Estructura_BBDD.COD_ALUMNO;

        try{
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_ALUMNOS,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null
            );

            while(!c.isLast()){
                c.moveToNext();
                listaAlumnos.add(new Alumno(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6)));
            }

        }catch (Exception e){
            Toast.makeText(InicioUsuario.this, "No hay ningún usuario.", Toast.LENGTH_SHORT).show();
        }
    }

    public void volverInicioUsuario(View view){ finish(); }
}