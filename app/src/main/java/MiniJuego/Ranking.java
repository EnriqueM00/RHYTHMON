package MiniJuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhythmon.MainActivity;
import com.example.rhythmon.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

import AdapterS.AdapterPuntuaciones;
import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;
import Clases_BBDD.Alumno;
import Clases_BBDD.Puntuacion;
import MiniJuego.Preconfig;

public class Ranking extends AppCompatActivity {

    private ArrayList<Alumno> listaAlumnos;
    private ArrayList<Puntuacion> listaPuntuaciones;
    private RecyclerView rvPuntuación;
    private TextView tvAlumno;
    private String nombre, apellidos;
    private int codAlumno;
    private BBDD_Helper helper = new BBDD_Helper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        Bundle b = getIntent().getExtras();
        codAlumno = b.getInt("codAlumno");
        listaAlumnos = new ArrayList<Alumno>();
        listaPuntuaciones = new ArrayList<Puntuacion>();
        listarAlumnos();
        listarPuntuaciones();
        recogerAlumno();
        ArrayList<Double> listaPuntos = obtenerValoresMayores(listaPuntuaciones, 8);
        tvAlumno = findViewById(R.id.tvAlumno);
        tvAlumno.setText(nombre + "  " + apellidos);
        rvPuntuación = findViewById(R.id.rvPuntuacion);
        rvPuntuación.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdapterPuntuaciones adapter = new AdapterPuntuaciones(listaPuntos);
        rvPuntuación.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        rvPuntuación.setAdapter(adapter);
    }

    private void recogerAlumno(){
        for (Alumno a: listaAlumnos){
            if (a.getCodAlumno() == codAlumno){
                nombre = a.getNombre();
                apellidos = a.getApellidos();
            }
        }
    }

    private void listarAlumnos(){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder leer en la BD
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos un Array de Strings con los campos del Select que queremos recoger
        String[] projection = {
                Estructura_BBDD.COD_ALUMNO,
                Estructura_BBDD.ID_USER_ALUMNO,
                Estructura_BBDD.NOMBRE_ALUMNO,
                Estructura_BBDD.APELLIDOS_ALUMNO,
                Estructura_BBDD.CONTRASEÑA_ALUMNO,
                Estructura_BBDD.ANIOS,
                Estructura_BBDD.COD_CENTRO_ALUMNO
        };
        // Declaramos la condición de la sentencia del select
        String selection = Estructura_BBDD.COD_ALUMNO + " LIKE ?";
        // Declaramos el argumento (codCentro) de la sentencia (el ? de arriba)
        String[] selectionArgs = {String.valueOf(codAlumno)};
        // Controlamos el cursor por si falla con un try - catch
        try {
            // Declaramos un cursor que le vamos a igualar a una sentencia pasandole los datos que hemos declarado anteriormente
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_ALUMNOS,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            //Bucle while. Mientras que no sea el último hace...
            while (!c.isLast()){
                //Corremos una posición hacia delante al cursor
                c.moveToNext();
                // Añadimos un nuevo Alumno al ArrayList con las columnas obtenidas
                listaAlumnos.add(new Alumno(c.getInt(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getInt(5),c.getInt(6)));
            }
            // Cerramos el cursor
            c.close();
        }catch (Exception e){
            // Mostramos un mensaje de error si no hay ningún Alumno
            Toast.makeText(this, "No hay ningún alumno.", Toast.LENGTH_SHORT).show();
        }
    }

    private void listarPuntuaciones(){
        // Creamos un objeto SQLiteDatabase que igualaremos a al BBDD_Helper para poder leer en la BD
        SQLiteDatabase db = helper.getReadableDatabase();
        // Declaramos un Array de Strings con los campos del Select que queremos recoger
        String[] projection = {
                Estructura_BBDD.COD_PUNTUACION,
                Estructura_BBDD.PUNTUACION,
                Estructura_BBDD.COD_ALUMNO_PUNTUACION
        };
        // Declaramos la condición de la sentencia del select
        String selection = Estructura_BBDD.COD_ALUMNO_PUNTUACION + " LIKE ?";
        // Declaramos el argumento (codCentro) de la sentencia (el ? de arriba)
        String[] selectionArgs = {String.valueOf(codAlumno)};
        // Controlamos el cursor por si falla con un try - catch
        try {
            // Declaramos un cursor que le vamos a igualar a una sentencia pasandole los datos que hemos declarado anteriormente
            Cursor c = db.query(
                    Estructura_BBDD.TABLE_PUNTUACION,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            //Bucle while. Mientras que no sea el último hace...
            while (!c.isLast()){
                //Corremos una posición hacia delante al cursor
                c.moveToNext();
                // Añadimos un nuevo Alumno al ArrayList con las columnas obtenidas
                listaPuntuaciones.add(new Puntuacion(c.getInt(0),c.getDouble(1),c.getInt(2)));
            }
            // Cerramos el cursor
            c.close();
        }catch (Exception e){
            // Mostramos un mensaje de error si no hay ningún Alumno
            Toast.makeText(this, "No hay ninguna puntuación.", Toast.LENGTH_SHORT).show();
        }
    }

    private ArrayList<Double> obtenerValoresMayores(ArrayList<Puntuacion> lista, int cantidad) {
        ArrayList<Double> listaDouble = new ArrayList<>();
        for (Puntuacion p : lista){
            listaDouble.add(p.getPuntuacion());
        }
        PriorityQueue<Double> queue = new PriorityQueue<>(cantidad);

        for (Double valor : listaDouble) {
            if (queue.size() < cantidad || valor > queue.peek()) {
                if (queue.size() == cantidad) {
                    queue.poll();  // Eliminar el valor más pequeño si se alcanza la capacidad máxima
                }
                queue.offer(valor);
            }
        }

        ArrayList<Double> valoresMayores = new ArrayList<>(cantidad);
        Double[] arrayOrdenado = queue.toArray(new Double[0]);
        Arrays.sort(arrayOrdenado, Collections.reverseOrder());
        for (Double valor : arrayOrdenado) {
            valoresMayores.add(valor);
        }

        return valoresMayores;
    }


    public void volverAJugar(View view){
        Intent i = new Intent(this, Preconfig.class);
        Bundle b = new Bundle();
        b.putInt("codAlumno", codAlumno);
        i.putExtras(b);
        startActivity(i);
        finish();
    }

    public void volverPantallaInicio(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}