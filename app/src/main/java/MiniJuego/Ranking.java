package MiniJuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rhythmon.MainActivity;
import com.example.rhythmon.R;

import java.text.DecimalFormat;
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

//CLASE "Ranking"
public class Ranking extends AppCompatActivity {

    //Variables globales
    private DecimalFormat df = new DecimalFormat("#.00");
    private ArrayList<Alumno> listaAlumnos; //Declaramos un ArrayList de Alumno
    private ArrayList<Puntuacion> listaPuntuaciones; //Declaramos un ArrayList de Puntuacion
    private RecyclerView rvPuntuación; //RecyclerView donde imprimiremos una lista de Doubles
    private TextView tvAlumno, tvPuntuacionActual; //Declaramos el TextView donde mostraremos el alumno
    private String nombre, apellidos; //Declaramos dos cadenas
    private double puntuaciónActual; //Declaramos un double
    private int codAlumno;//Declaramos un entero para el "codAlumno"
    private BBDD_Helper helper = new BBDD_Helper(this); //Declaramos e inicializamos un objeto BBDD_Helper (gestor BD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);
        //Declaramos e inicializamos un Bundle con los extras de la Activity anterior
        Bundle b = getIntent().getExtras();
        //Le asignamos el valor de la clave "codAlumno" a nuestra variable
        codAlumno = b.getInt("codAlumno");
        puntuaciónActual = b.getDouble("puntos");
        //Inicializamos los ArrayList de Alumno, Puntuacionez
        listaAlumnos = new ArrayList<Alumno>();
        listaPuntuaciones = new ArrayList<Puntuacion>();
        //Rellenamos los ArrayList. Llamada a los metodos "listarAlumnos()" y "listarPuntuaciones()"
        listarAlumnos();
        listarPuntuaciones();
        //Llamamos al metodo "recogerAlumno()"
        recogerAlumno();
        //Creamos un nuevo ArrayList de Doubles asignandole el ArrayList de doubles que nos devuelve el metodo "obtenerValoresMayores()"
        ArrayList<Double> listaPuntos = obtenerValoresMayores(listaPuntuaciones, 8);
        tvAlumno = findViewById(R.id.tvAlumno);
        tvPuntuacionActual = findViewById(R.id.tvPuntuacionActual);
        //Seteamos el texto del Alumno con el nombre y los apellidos del mismo
        tvAlumno.setText(nombre + "  " + apellidos);
        tvPuntuacionActual.setText("Puntuacón actual:  " + String.valueOf(df.format(puntuaciónActual)).toString());
        rvPuntuación = findViewById(R.id.rvPuntuacion);
        rvPuntuación.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //Declaramos un AdapterPuntuaciones pasandole el ArrayList de Doubles con las Puntuaciones ordenadas
        AdapterPuntuaciones adapter = new AdapterPuntuaciones(listaPuntos);
        //Seteamos la aliniación del RecyclerView
        rvPuntuación.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        //Seteamos el adapter del RecyclerView pasandole el que acabamos de inicializar
        rvPuntuación.setAdapter(adapter);
    }
    //Metodo para recoger el nombre y los apellidos del alumno, comprobando su codAlumno
    private void recogerAlumno(){
        for (Alumno a: listaAlumnos){
            if (a.getCodAlumno() == codAlumno){
                nombre = a.getNombre();
                apellidos = a.getApellidos();
            }
        }
    }
    //Metodo que rellena la lista de alumnos
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
    //Metodo que rellena el ArrayList de Puntuaciones
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
    /*
    *
    * @param lista, cantidad
    */
    //Metodo para escoger las puntuaciones mas altas del alumno y ordenarlas de mayor a menor, retorna una lista de Doubles
    private ArrayList<Double> obtenerValoresMayores(ArrayList<Puntuacion> lista, int cantidad) {
        ArrayList<Double> listaDouble = new ArrayList<>();
        // Obtener las puntuaciones de la lista original y almacenarlas en listaDouble
        for (Puntuacion p : lista) {
            listaDouble.add(p.getPuntuacion());
        }
        PriorityQueue<Double> queue = new PriorityQueue<Double>(cantidad);
        // Iterar sobre las puntuaciones en listaDouble
        for (Double valor : listaDouble) {
            // Verificar si la cola tiene menos elementos que la cantidad deseada o si el valor actual es mayor que el valor más pequeño en la cola
            if (queue.size() < cantidad || valor > queue.peek()) {
                // Si la cola ya alcanzó la capacidad deseada, eliminar el valor más pequeño
                if (queue.size() == cantidad) {
                    queue.poll();
                }
                // Agregamos el valor actual a la cola
                queue.offer(valor);
            }
        }
        ArrayList<Double> valoresMayores = new ArrayList<>(cantidad);
        Double[] arrayOrdenado = queue.toArray(new Double[0]);
        // Ordenamos el array en orden descendente
        Arrays.sort(arrayOrdenado, Collections.reverseOrder());
        // Agregamos los valores ordenados a la lista valoresMayores
        for (Double valor : arrayOrdenado) {
            valoresMayores.add(valor);
        }
        // Devolvemos la lista de los valores más grandes
        return valoresMayores;
    }
    /*
    *
    * @param view
    * */
    //Metodo para volver a jugar, nos lleva a la pantalla Preconfig, cerrando la actual
    public void volverAJugar(View view){
        Intent i = new Intent(this, Preconfig.class);
        Bundle b = new Bundle();
        b.putInt("codAlumno", codAlumno);
        i.putExtras(b);
        startActivity(i);
        finish();
    }
    /*
    *
    * @param view
    * */
    //Metodo que vuelve a la pantalla de inicio, cerrando la actual
    public void volverPantallaInicio(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}