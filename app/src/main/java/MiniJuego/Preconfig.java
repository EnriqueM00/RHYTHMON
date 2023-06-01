package MiniJuego;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.rhythmon.R;

// CLASE "Preconfig"
public class Preconfig extends AppCompatActivity {

    //Variables globales
    private int codAlumno; //Declaramos un entero para guardar el "codAlumno"
    private ScrollView scrollBinario; //Declaramos un ScrollView
    private TableLayout tableBinario; //Declaramos una tabla
    private Button btnJugar; //Declaramos el boton
    private SeekBar seekBarDictados, seekBarTempo; //Declaramos las SeekBars (los utilizaremos para dar las opciones de configuración)
    private TextView tvSeekBarDictados, tvSeekBarTempo; // Declaramos los TextView donde mostraremos el valor de la posición de las SeekBars
    private final int MIN_VALUE = 60, MAX_VALUE = 120, INTERVALO = 10; //Declaramos las constantes para las SeekBars
    private final int NUM_INTERVALOS = (MAX_VALUE - MIN_VALUE) / INTERVALO; //Declaramos la constante de NUM_INTERVALOS para saber cuantas posiciones
                                                                            // tendra nuestra SeekBar de Tempo


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preconfig);
        // Declaramos un bundle y lo inicializamos con los datos de la Activity anterior
        Bundle b = getIntent().getExtras();
        // Le asignamos el valor de la clave "codAlumno" a nuestra variable codAlumno
        codAlumno = b.getInt("codAlumno");
        // Inicializamos todos los objetos necesarios para trabajar con la clase{
        tvSeekBarTempo = findViewById(R.id.tv_seekBar_tempo);
        seekBarTempo = findViewById(R.id.seekBar_Tempo);
        tableBinario = findViewById(R.id.table_bin);
        btnJugar = findViewById(R.id.btnJugarConfig);
        tvSeekBarDictados = findViewById(R.id.tv_seekBar_dictado);
        seekBarDictados = findViewById(R.id.seekBar_Dictado);
        scrollBinario = findViewById(R.id.scroll_binario);
        //}
        // Seteamos la visibilidad para que sea visible
        scrollBinario.setVisibility(View.VISIBLE);
        // Seteamos el color de fondo que tendra
        scrollBinario.setBackgroundColor(Color.parseColor("#7CBD7F"));

        //Seteamos el texto del TextView Dictados con el valor inicial que siempre tendra
        tvSeekBarDictados.setText("1");

        // Seteamos un escuchador de cambios para "seekBarDictados" (se autogenera el metodo, solo utilizaremos el de Cambio de Progreso)
        seekBarDictados.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Seteamos el texto del TextView de la SeekBar de dictados al progreso que lleve (1,2,3,4 y 5)
                tvSeekBarDictados.setText(Integer.toString(progress + 1));
            }
        });

        //Seteamos el texto del TextView Tempo con el valor inicial que siempre tendra
        tvSeekBarTempo.setText("60");
        // Seteamos la posición maxima que tendra con el numero de intervalos
        seekBarTempo.setMax(NUM_INTERVALOS);

        // Al igual que antes Seteamos un escuchador de cambios para "seekBarTempo"
        seekBarTempo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                // Calculamos el valor que tiene la SeekBar haciendo una simple formula
                int valor = MIN_VALUE + (progress * INTERVALO);
                // Seteamos el texto del TextView con el valor que hemos calculado
                tvSeekBarTempo.setText(Integer.toString(valor));
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });

        // Llamada al metodo "crearImageViewFormulasRitmicas()" (creamos / generamos las imagenes de las Formulas Ritmicas que se van a utilizar)
        crearImageViewFormulasRitmicas();

        // Setamos un escuchador de Clicks al "btnJugar"
        btnJugar.setOnClickListener(new View.OnClickListener() {
            @Override
            // ACCION DEL BOTON:
            // Pasa a la pantalla del Minijuego y transporta la información necesaria para jugar
            public void onClick(View v) {
                int tempo = Integer.parseInt(tvSeekBarTempo.getText().toString());
                int dictados = Integer.parseInt(tvSeekBarDictados.getText().toString());
                Intent i = new Intent(Preconfig.this, MiniJuego.class);
                Bundle b = new Bundle();
                b.putInt("tempo",tempo);
                b.putInt("dictados", dictados);
                b.putInt("codAlumno", codAlumno);
                i.putExtras(b);
                startActivity(i);
                finish();
            }
        });
    }

    // Metodo con el que dibujamos las imagenes de las notas en la TableLayout y las colocamos en filas y columnas con unos margenes y medidas
    private void crearImageViewFormulasRitmicas() {
        // Declaramos e inicializamos un Array de los XML de las Figuras Ritmicas
        ImageView imageViewsFormulas[] = new ImageView[FormulasRitmicas.values().length]; // En el array acedemos a los valores de la Enumeracion de FigurasRitmicas (donde estan los valores de los nombres de cada figura)
        int elementosFilaActualBin = 0; //Declaramos un entero para saber los elementos de la fila donde estemos introduciendo las Imagenes
        TableRow rowBinActual = new TableRow(this); //Creamos un objeto de FilaTabla (TABLE ROW)
        // Seteamos los parametros que tendra la fila en el Layout (el alto se adaptara al contenido, el ancho se adaptara al contenido)
        rowBinActual.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
        // Creamos una variable donde almacenaremos el numero de Imagenes que tendra cada fila. Dividimos el ancho en pixeles por 80 pixeles (valor calculado sumando el ancho de cada imagen y el margen izq. y dch.)
        int formulasPorFila =  (int) (float)(this.getResources().getDisplayMetrics().widthPixels) / convertirDP2PX(80);

        // Hacemos un for que vaya de 0 al tamaño del array de valores de la clase FormulasRitmicas
        for (int i = 0; i < FormulasRitmicas.values().length; i++) {
            FormulasRitmicas f = FormulasRitmicas.values()[i]; //Formulas

            //Dibujar imagenes en TableLayout
            imageViewsFormulas[i] = new ImageView(this); // Creamos una imagen por cada elemento del Array
            // Recogemos el ID que tiene cada imagen para poder dibujarla. parametros usados (cogemos el nombre de la figura, le decimos que es drawable (dibujable), recogemos el nombre de este Pakage)
            int idDrawable = getResources().getIdentifier(f.getNombreImgXML(), "drawable", this.getPackageName());
            // Le damos el ID recogido a la Imagen
            imageViewsFormulas[i].setImageResource(idDrawable);
            // Le demos que escale la imagen redimensionandola para que ocupe el tamaño que le damos mas abajo
            imageViewsFormulas[i].setScaleType(ImageView.ScaleType.CENTER_INSIDE); //FIT_CENTER
            // Seteamos que ajuste la imagen manteniendo la relación de aspecto
            imageViewsFormulas[i].setAdjustViewBounds(true);
            // Creamos los Parametros para la Fila de la imagen que estamos insertando
            TableRow.LayoutParams lp = new TableRow.LayoutParams(convertirDP2PX(50), convertirDP2PX(50)); // Estos parametros son para el alto y el ancho
            int margin = convertirDP2PX(15); //Declaramos una variable para los margenes
            lp.setMargins(margin, margin, margin, margin); // Seteamos los margenes que acabamos de darle (15 por arriba, abajo, a la izq. y a la dch.)
            // Seteamos los Parametros de la imagen para el Layout
            imageViewsFormulas[i].setLayoutParams(lp);
            // Seteamos el color de fondo de la Imagen
            imageViewsFormulas[i].setBackgroundColor(Color.parseColor("#eac316"));


            //Lo añadimos al ScrollView
                rowBinActual.addView(imageViewsFormulas[i]); //Aqui añadimos el objeto creado IV (ImageView)
                elementosFilaActualBin++;
                //Comprobamso si estan todos los elementos posibles en la fila
                if (elementosFilaActualBin == formulasPorFila) { //Añadimos la fila completa a la tabla y creamos otra
                    tableBinario.addView(rowBinActual);
                    rowBinActual = new TableRow(this);
                    rowBinActual.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT));
                    elementosFilaActualBin = 0;
                }
        }

        //Añadimos la última fila si algun elementosFilaActual es mayor que 0
        if (elementosFilaActualBin > 0)
            tableBinario.addView(rowBinActual);
    }

    /*
    *
    *@param dp
    *
    */
    // Metodo convertidor de DP a Pixeles
    public int convertirDP2PX(float dp) {
        Resources r = this.getResources();
        return (int) Math.floor(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    /*
    *
    *@param view
    *
    */
    // Metodo para volver a la pantalla anterior y cerrar la actual
    public void volverPreConfig(View view){ finish(); }
}