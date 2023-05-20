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

public class InicioCentro extends AppCompatActivity {

    private ArrayList<Centro> listaCentros;
    private EditText etIDUser, etPassword;
    private Button btnLogueo;
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio_centro);

        listaCentros = new ArrayList<Centro>();
        volcado();
        etIDUser = findViewById(R.id.etUserCentro);
        etPassword = findViewById(R.id.etPasswordCentro);
        btnLogueo = findViewById(R.id.btnLogueoCentro);

        btnLogueo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = etIDUser.getText().toString();
                String password = etPassword.getText().toString();
                if (user.equals("root") && password.equals("root1234")){
                    Intent i = new Intent(InicioCentro.this, GestionCentros.class);
                    startActivity(i);
                    etIDUser.setText("");
                    etPassword.setText("");
                }
                else{
                    for (Centro c : listaCentros){
                        if (c.getId_user().equals(user)){
                            if (c.getContraseña().equals(password)){
                                Intent i = new Intent(InicioCentro.this, GestionCentro.class);

                                Bundle b = new Bundle();
                                b.putInt("codCentro", c.getCodCentro());
                                i.putExtras(b);

                                startActivity(i);

                                etIDUser.setText("");
                                etPassword.setText("");
                            }
                            else{
                                Toast.makeText(InicioCentro.this, "Contraseña incorrecta.", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else {
                            Toast.makeText(InicioCentro.this, "Usuario incorrecto.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private void volcado(){
        SQLiteDatabase db = helper.getReadableDatabase();

        String[] projection = {
                Estructura_BBDD.COD_CENTRO,
                Estructura_BBDD.NOMBRE_CENTRO,
                Estructura_BBDD.ID_USER_CENTRO,
                Estructura_BBDD.CONTRASEÑA_CENTRO,
                Estructura_BBDD.CIUDAD_CENTRO,
                Estructura_BBDD.NUM_ALUMNOS
        };

        String selection = Estructura_BBDD.COD_CENTRO;

        try{
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_CENTROS,
                    projection,
                    selection,
                    null,
                    null,
                    null,
                    null
            );

            while(!c.isLast()){
                c.moveToNext();
                listaCentros.add(new Centro(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5)));
            }

        }catch (Exception e){
            Toast.makeText(InicioCentro.this, "No hay ningún centro.", Toast.LENGTH_SHORT).show();
        }
    }



    public void volverInicioCentro(View view){ finish(); }
}