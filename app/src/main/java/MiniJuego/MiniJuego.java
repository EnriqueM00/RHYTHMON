package MiniJuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.ContentValues;
import android.content.Intent;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.media.SoundPool;

import com.example.rhythmon.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import BBDD.BBDD_Helper;
import BBDD.Estructura_BBDD;

// CLASE "MiniJuego"
public class MiniJuego extends AppCompatActivity {

    // Variables globales
    private Handler masterHandler; // Declaramos un manejador de hilos
    private ConstraintLayout layoutActual; // Declaramos un ConstraintLayout (hace referencia al Layout actual)
    private ImageView ivLineaCompas,ivLineaGuia; //Declaramos las Imagenes que utilizamos en toda la clase
    private SoundPool sp; // Declaramos un objeto SoundPool (utilizaremos para reproducir un sonido al pulsar el pulasdor)
    private ImageButton btnPlay, btnPulsador; //Declaramos los botones
    private Long tiempoInicioDictado; //Declaramos un long (hará referencia al tiempo de inicio de cada Dictado)
    private double puntuacion = 0, puntuacionMax = 0, puntSobre10 = 0; //Declaramos e inicializamos los doubles que necesitaremos para la puntuación
    private int codAlumno, tempo, dictados, duraciónPulso, idSonido, contador, dictadoActual = 1; //Declaramos los enteros necesarios
    private final int PARTES_COMPAS = 4; //Declaramos e inicializamos una constante de tipo entero (referenciando a las partes de 1 compas)
    private final int COMPAS_LENGTH = 4; //Declaramos e inicializamos una constante de tipo entero (referenciando a la longitud de 1 compas)
    private final int[] VALORES = {1, -2, 2, -3, 3, -4, 4}; // Declaramos e inicializamos un array que es una constante (referencia a los valores de las notas)
    // Declaramos e inicializamos con los valores necesarios un Hash Map
    private final Map<Integer, Integer> MAP = new HashMap<Integer, Integer>() {{ //Le pasamos la clave y su valor (parecido a los Diccionarios de Python)
        put(1, R.drawable.corcheas);
        put(-2,R.drawable.silencio_negra);
        put(2,R.drawable.negra);
        put(-3,R.drawable.silencio_blanca);
        put(3,R.drawable.blanca);
        put(-4,R.drawable.silencio_redonda);
        put(4,R.drawable.redonda);
    }};
    private List<Long> listaTiemposPulsador = new ArrayList<>(); //Declaramos e inicializamos una Lista de longs (utilizaremos para recoger el tiempo en el que se hacen las pulsaciones)
    private Compas compas1, compas2; //Declaramos 2 objetos de la clase Compas
    private CheckBox checkBoxLineaGuia; //Declaramos un CheckBox
    private List<ImageView> listaFigurasDraw1; //Declaramos una Lista de Imagenes donde guardaremos las imagenes que pintemos en el compas
    private TextView tvTempo, tvDictados, tvCuentaAtras, tvPuntuacion; //Declaramos los TextView necesarios
    private BBDD_Helper helper = new BBDD_Helper(this); // Declaramos e inicializamos un objeto BBDD_Helper (gestionar BD)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_juego);
        // Inicializamos el manejador de Hilos
        masterHandler = new Handler();
        // Inicializamos el SoundPool
        sp = new SoundPool.Builder().build();
        // Le asignamos a nuestra variable el ID del sonido (corto) que vamos a utilizar
        idSonido = sp.load(this, R.raw.golpe_metronomo, 1);
        // Declaramos e inicializamos un Bundle para recoger los datos de la anterior Activity
        Bundle b = getIntent().getExtras();
        // Asignamos los valores de las respectivas claves a las respectivas variables
        codAlumno = b.getInt("codAlumno");
        tempo = b.getInt("tempo");
        dictados = b.getInt("dictados");
        // Asignamos el tiempo que dura una pulsación (dividiendo 1 minuto entre el tempo(pulsaciones por minuto o BPM)) a nuestra variable
        duraciónPulso = 60000 / tempo; // el resultado en milisegundos

        tvDictados = findViewById(R.id.tvNumDictados);
        tvTempo = findViewById(R.id.tv_Tempo);
        tvCuentaAtras = findViewById(R.id.tvCuentaAtras);
        tvPuntuacion = findViewById(R.id.tvPuntuacion);
        checkBoxLineaGuia = findViewById(R.id.checkBoxLineaAyuda);

        tvTempo.setText("=  " + String.valueOf(tempo));
        tvDictados.setText("Nº " + String.valueOf(dictadoActual));

        layoutActual = findViewById(R.id.layoutCompas);
        ivLineaCompas = findViewById(R.id.ivBarraCompas);
        ivLineaGuia = findViewById(R.id.ivLineaGuia);

        btnPlay = findViewById(R.id.btnPlayMinijuego);
        btnPulsador = findViewById(R.id.btnPulsador);
        btnPulsador.setEnabled(false);
        // Inicializamos los compases llamando al metodo "generarCompas()" que nos devuelve un objeto del tipo Compas
        compas1 = generarCompas();
        compas2 = generarCompas();

        // Este metodo sirve para que carguen las vistas y recursos antes de llamar al metodo "dibujarFigurasRitmicas()", así podemos recabar
        // información sobre los ejex X e Y de los componentes para poder pintar el dictado, gracias a un escuchador en el Layout
        layoutActual.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        // Igualamos la Lista de Imagenes llamando al metodo "dibujarFigurasRitmicas()" que nos devuelve un ArrayList de ImageView
                        listaFigurasDraw1 = dibujarFigurasRitmicas(compas1, compas2);
                        layoutActual.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });
    }
    // Medodo para añadir la nota al respectivo Alumno que haya realizado la actividad (inserción en la BD)
    private void añadirNota(){
        DecimalFormat df = new DecimalFormat("#.00");
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        puntSobre10 = 10 * (puntuacion / puntuacionMax);

        values.put(Estructura_BBDD.PUNTUACION, String.valueOf(df.format(puntSobre10)));
        values.put(Estructura_BBDD.COD_ALUMNO_PUNTUACION, String.valueOf(codAlumno));

        db.insert(Estructura_BBDD.TABLE_PUNTUACION, null, values);
    }
    /*
    *
    * @param view
    *
    */
    // Metodo que al pulsar el "btnPulsador" reproduzca el sonido de metronomo y añada el tiempo en el que es pulsado a la "listaTiemposPulsador"
    public void btnPulsador(View view){
        sp.play(idSonido, 1, 1, 1, 0, 1.2F);
        listaTiemposPulsador.add(System.currentTimeMillis());
    }
    /*
     *
     * @param view
     *
     */
    // Metodo que al pulsar el "btnPlay" hace una cuenta atras gracias al manejador de hilos e incrementa el dictado actual
    public void btnPlay(View view){
        contador = 3;
        masterHandler.post(hiloCuenta); //Imprime el 3
        masterHandler.postDelayed(hiloCuenta, duraciónPulso); //Imprime el 2
        masterHandler.postDelayed(hiloCuenta, 2 * duraciónPulso); //Imprime el 1
        masterHandler.postDelayed(hiloCuenta, 3 * duraciónPulso); //Incica que empieza con el ultimo pulso
        dictadoActual++;
    }
    // Metodo que comprueba el dictado realizado, da una puntuación al alumno dependiendo de factores como el tiempo y el nº de pulsaciones
    private void comprobarDictado(){
        // Declaramos y creamos un formato para los decimales, solo mostrara los 2 primeros decimales
        DecimalFormat df = new DecimalFormat("#.00");
        // CREAMOS LA LISTA DE VALORES DE TIEMPO QUE DEBERIAN HABER TOMADO LAS PULSACIONES
        List<Long> pulsacionesPerfectas = new ArrayList<>();
        long tiempo = 0;
        for (int figura :compas1.getFiguras()){
            if (figura > 0){
                pulsacionesPerfectas.add(tiempo);
                if (figura == 1){
                    pulsacionesPerfectas.add(tiempo + (duraciónPulso/2));
                }
            }
            tiempo += (traducirValor(figura) * duraciónPulso);
        }
        for (int figura :compas2.getFiguras()){
            if (figura > 0){
                pulsacionesPerfectas.add(tiempo);
                if (figura == 1){
                    pulsacionesPerfectas.add(tiempo + (duraciónPulso/2));
                }
            }
            tiempo += (traducirValor(figura) * duraciónPulso);
        }

        // Comprobamos si no se dio ninguna pulsacion y si las pulsaciones son mayores de 0 (por si son todo silencios)
        if (listaTiemposPulsador.size() == 0 && pulsacionesPerfectas.size() > 0){
            dictadoActual--;
            return; //El alumno solo observo el dictado
        }

        // REDUCIMOS TIEMPO DE LAS PULSACIONES, QUITANDO EL VALOR INICIAL
        for (int i = 0; i<listaTiemposPulsador.size(); i++)
            listaTiemposPulsador.set(i, (listaTiemposPulsador.get(i) - tiempoInicioDictado) );

        if (pulsacionesPerfectas.size()==0){
            puntuacionMax += 100;
        }
        else{
            puntuacionMax += ((100 + (200 * 0.5)) * pulsacionesPerfectas.size()) + 100;
        }

        List<Long> listaTiemposPulsadorAprox = new ArrayList<>();
        // RECORREMOS LAS DOS LISTAS COMPARANDO LA LONGITUD DE LAS LISTAS Y SU RESPECTIVOS VALORES, HACIENDO UN BAREMO DE LA PRECISION DE LAS PULSACIONES
        if (pulsacionesPerfectas.size() == listaTiemposPulsador.size()){
            puntuacion += 100;
            listaTiemposPulsadorAprox = listaTiemposPulsador;
        }
        // SI HAY MENOS PULSACIONES HACEMOS UNA APROXIMACIÓN A LA PULSACIÓN PERFECTA PARA QUE EL TAMAÑO DE LAS LISTAS SEAN IGUALES
        else if (pulsacionesPerfectas.size() < listaTiemposPulsador.size()){
            int numeroErrores = Math.abs((pulsacionesPerfectas.size() - listaTiemposPulsador.size()));
            puntuacion -= (numeroErrores * 50);
            for (long tPulsacion : pulsacionesPerfectas){
                long mejorDiferencia = 8 * duraciónPulso;
                long tiempoEscogido = 0;
                for (long tPulsacionAlumno : listaTiemposPulsador){
                    if (mejorDiferencia > Math.abs((tPulsacion - tPulsacionAlumno))){
                        // Evitamos coger un tiempo si ya lo hemos cogido
                        if (!listaTiemposPulsadorAprox.contains(tPulsacionAlumno)){
                            mejorDiferencia = Math.abs((tPulsacion - tPulsacionAlumno));
                            tiempoEscogido = tPulsacionAlumno;
                        }

                    }
                }
                listaTiemposPulsadorAprox.add(tiempoEscogido);
            }
        }
        else {
            int numeroErrores = Math.abs((pulsacionesPerfectas.size() - listaTiemposPulsador.size()));
            puntuacion -= (numeroErrores * 75); //Es mas grave dar de menos que de mas
            for (long tPulsacion : pulsacionesPerfectas){
                long mejorDiferencia = 8 * duraciónPulso;
                long tiempoEscogido = 0;
                for (long tPulsacionAlumno : listaTiemposPulsador){
                    if (mejorDiferencia > Math.abs((tPulsacion - tPulsacionAlumno))){
                        // Debemos coger tiempos ya escogido
                        mejorDiferencia = Math.abs((tPulsacion - tPulsacionAlumno));
                        tiempoEscogido = tPulsacionAlumno;
                    }
                }
                listaTiemposPulsadorAprox.add(tiempoEscogido);
            }
        }

        int difernciaPulsaciones;
        // Valoramos cada pulsación comparando con las PulsacionesPerfectas
        for (int i  = 0; i < listaTiemposPulsadorAprox.size(); i++){
            difernciaPulsaciones = (int) Math.abs(listaTiemposPulsadorAprox.get(i) - pulsacionesPerfectas.get(i));
            if (difernciaPulsaciones <= 200){
                puntuacion += 100 + ((200 - difernciaPulsaciones) * 0.5);
            }
            else if (difernciaPulsaciones <= 400){
                difernciaPulsaciones -= 200;
                puntuacion += 50 + ((200 - difernciaPulsaciones) * 0.25);
            }
            else if (difernciaPulsaciones <= 600){
                difernciaPulsaciones -= 400;
                puntuacion += 25 + ((200 - difernciaPulsaciones) * 0.05);
            }
        }

        // Actualizamos puntuación
        tvPuntuacion.setText("Puntuación: " + String.valueOf(df.format(puntuacion)));

        // Limpiamos los ArrayLists
        pulsacionesPerfectas.clear();
        listaTiemposPulsador.clear();
        listaTiemposPulsadorAprox.clear();

        //Comprobamos el numero de Dictados que llevamos
        if ((dictadoActual - 1) == dictados){
            // Llamada al metodo Insertar Puntuacion
            añadirNota();
            Intent i = new Intent(this, Ranking.class);
            Bundle b = new Bundle();
            b.putInt("codAlumno", codAlumno);
            i.putExtras(b);
            startActivity(i);
        }
        // Si el numero del dictado actual no es igual al numero de dictados vaciamos los compases y les llenamos de nuevo
        else {
            reconstruirCompases();
        }
    }

    // Metodo para vaciar los compases y rellenarles
    private void reconstruirCompases(){
        //Recorremos el ArrayList de ImageView que creamos antes
        for (ImageView iv :listaFigurasDraw1){
            layoutActual.removeView(iv); //Eliminamos las imagenes del Layout
        }
        compas1 = generarCompas(); //Les asignamos otros compases nuevos a nuestras variables globales
        compas2 = generarCompas();
        tvDictados.setText("Nº " + String.valueOf(dictadoActual)); //Actualizamos el numero del dictado en el que estamos
        listaFigurasDraw1 = dibujarFigurasRitmicas(compas1,compas2); //Guardamos la lista de Imagenes que acabamos de pintar de nuevo
    }

    // Metodo que genera una "animación" (desplazamiento) para la barra de ayuda
    private void moverBarra(){
        //Calculamos el tamaño que tiene la linea del compas desde donde empieza hasta donde termina (barraGruesa comienzo, barraDoble fin)
        float tamañoDictado = (findViewById(R.id.ivBarraDoble).getX() - findViewById(R.id.ivBarraGruesa).getX());
        // Creamos un objeto TranslateAnimation con el que haremos la animación
        TranslateAnimation moverDerecha = new TranslateAnimation( 0 , tamañoDictado, 0, 0);
        moverDerecha.setDuration(duraciónPulso * 8);//Duracion 2 compases
        moverDerecha.setFillAfter(true); // Seteamos el FillAfter a true para que se quede donde a terminado la animación
        moverDerecha.setInterpolator(new LinearInterpolator()); // Seteamos un Interpolator para que la velocidad sea constante
        ivLineaGuia.startAnimation(moverDerecha); //Hacemos que ImageView va a empezar la animación
    }

    // Metodo que activa o desactiva la funcionalidad de los botones a nuestro gusto para que no se puedan usar en la ejecución del juego ya que
    // daría problemas si no
    private void cambiarHabilitacionBotones(){  //Boton Play, Pulsador y CheckBox. Play y Checkbox van a la vez. Y al contrario que Pulsador
        if (checkBoxLineaGuia.isEnabled()){
            checkBoxLineaGuia.setEnabled(false);
            btnPlay.setEnabled(false);
        }
        else {
            checkBoxLineaGuia.setEnabled(true);
            btnPlay.setEnabled(true);
            btnPulsador.setEnabled(false);
        }
    }

    // Metodo que hace la cuenta atras y empieza el juego
    private void cuentaAtras(){
        // Si el contador esta en 4 empieza el juego
        if (contador == 3){
            cambiarHabilitacionBotones(); //Desactivamos los botonos de play y el checkBox
            masterHandler.postDelayed(new Runnable() { //Utilizamos el manejador de hilos para que empiece con retraso los 2 metodos siguiente
                @Override                               // porque tenemos que esperar a que termine la cuenta atras y el dictado
                public void run() {
                    // Llamada al metodo "cambiarHabilitacionBotones()"
                    cambiarHabilitacionBotones();
                    // Llamada al metodo "comprobarDictado()"
                    comprobarDictado();
                }
            },duraciónPulso * 11); //Por 11 sale de por 8 tiempos del compas + 3 de espera
        }
        if (contador == 0){
            btnPulsador.setEnabled(true);
            tvCuentaAtras.setText("");
            masterHandler.removeCallbacks(hiloCuenta); //Mirar a ver si hay alguna llamada pendiente para borrarla en el manejador
            if (checkBoxLineaGuia.isChecked()){
                checkBoxLineaGuia.setEnabled(false);
                moverBarra();
            }
            tiempoInicioDictado = System.currentTimeMillis();
        }
        else{
            tvCuentaAtras.setText("\n " + contador);
            sp.play(idSonido, 1, 1, 1, 0, 1.2F); //Reproduccioón del sonido

        }
        contador--;
    }

    Runnable hiloCuenta = new Runnable() { //Metodo Runnable abre un hilo para el metodo cuenta atras
        @Override
        public void run() {
            cuentaAtras();
        }
    };
    /*
    *
    *@param c, @param c2
    *
    */
    //Metodo que dibuja las figuras ritmicas en el compas, retorna una lista de ImageView (imagenes)
    private List<ImageView> dibujarFigurasRitmicas( Compas c, Compas c2){
        //Declaramos e inicializamos 2 listas donde guardaremos los IDs de las imagenes
        List<Integer> idImagenesCompas1 = traducirCompases(c);
        List<Integer> idImagenesCompas2 = traducirCompases(c2);
        // Declaramos e inicializamos una Lista de ImageView que pintaremos mas adelante
        List<ImageView> ivNotasPintadas = new ArrayList<>();
        // Recogemos en una variable la X de donde empieza el compas
        float dpInicioX = findViewById(R.id.ivBarraGruesa).getX();
        float dpInicial = dpInicioX;
        // Declaramos una variable donde guardaremos el tamaño de un compas
        double tamañoUnCompas = (findViewById(R.id.ivBarraDoble).getX() - dpInicioX) / 2;
        // Declaramos una variable donde guardaremos el tamaño de un tiempo en el compas
        double tamañoUnaParte = tamañoUnCompas / PARTES_COMPAS;
        // Recorremos la primera lista
        for (int i = 0; i < idImagenesCompas1.size();i++){
            // Declaramos una variable para guardar el ID de cada ImageView
            int figuraDrawable = idImagenesCompas1.get(i);
            // Declaramos una variable para guardar el valor de cada Figura (para hacer retoques en la posicón dependiendo de que figura sea)
            int valorFigura = c.getFiguras().get(i);
            // Creamos un objeto ImageView donde le pasamos la Activity donde nos encontramos (this)
            ImageView ivFigura = new ImageView(this);
            // Seteamos la imagen dibujable que recogemos gracias al ID de la imagen
            ivFigura.setImageDrawable(getResources().getDrawable(figuraDrawable));
            // Seteamos la escala de la Imagen para que se redimensione en relación al tamaño
            ivFigura.setScaleType(ImageView.ScaleType.FIT_CENTER);
            // Declaramos un objeto LayoutParams para pasarle los parametros de tamaño que tendra cada nota
            RelativeLayout.LayoutParams lpFigura;

            switch (valorFigura)
            {
                case 1:{
                    lpFigura = new RelativeLayout.LayoutParams(convertirDP2PX(40), convertirDP2PX(60));
                    break;
                }
                case -3:
                case -4:{
                    lpFigura = new RelativeLayout.LayoutParams(convertirDP2PX(40), convertirDP2PX(20));
                    break;
                }
                default:{
                    lpFigura = new RelativeLayout.LayoutParams(convertirDP2PX(20), convertirDP2PX(40));
                    break;
                }
            }
            // Llamamos a "requestLayout()" para solicitar una nueva disposición de las vistas en el Layout
            layoutActual.requestLayout();
            // Añadimos la vista (view) al Layout pasandole la imagen y los parametros
            layoutActual.addView(ivFigura, lpFigura);
            // Comprobamos que si es la primera figura o no
            if (dpInicioX == dpInicial){ // Si es asi le daremos 10 mas de margen que al resto de figuras
                ivFigura.setX((dpInicioX + convertirDP2PX(20)));
            }
            else { // Si no es la primera figura le daremos 10 de margen
                ivFigura.setX(dpInicioX + convertirDP2PX(10));
            }
            // Hacemos un switch para que en cada caso de las figuras las coloque en una posición de Y especial para cada una
            switch (valorFigura){
                case 1:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(15));
                    break;
                }
                case -2:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(3));
                    break;
                }
                case 2:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(7));
                    break;
                }
                case 3:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(5));
                    break;
                }
                case -3:{
                }
                case -4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(16));
                    break;
                }
                case 4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(9.5F));
                }
            }
            // Añadimos a la lista de imagenes pintadas la imagen que hemos pintado
            ivNotasPintadas.add(ivFigura);
            // Declaramos una variable donde sumamos lo que ocupa la figura por el tamaño de un tiempo del compas
            double espacioOcupadoNota = traducirValor(valorFigura) * tamañoUnaParte;
            // Sumamos al dpInicioX el espacio que ocupa la figura para que empiece en su posición correcta
            dpInicioX += espacioOcupadoNota;
        }
        // Realizamos el mismo procedimiento pero con la otra lista
        for (int i = 0; i < idImagenesCompas2.size();i++){
            int figuraDrawable = idImagenesCompas2.get(i);
            int valorFigura = c2.getFiguras().get(i);

            ImageView ivFigura = new ImageView(this);
            ivFigura.setImageDrawable(getResources().getDrawable(figuraDrawable));
            ivFigura.setScaleType(ImageView.ScaleType.FIT_CENTER);

            RelativeLayout.LayoutParams lpFigura;

            switch (valorFigura)
            {
                case 1:{
                    lpFigura = new RelativeLayout.LayoutParams(convertirDP2PX(40), convertirDP2PX(60));
                    break;
                }
                case -3:
                case -4:{
                    lpFigura = new RelativeLayout.LayoutParams(convertirDP2PX(40), convertirDP2PX(20));
                    break;
                }
                default:{
                    lpFigura = new RelativeLayout.LayoutParams(convertirDP2PX(20), convertirDP2PX(40));
                    break;
                }
            }
            layoutActual.requestLayout();
            layoutActual.addView(ivFigura, lpFigura);
            if (dpInicioX == dpInicial){
                ivFigura.setX((dpInicioX + convertirDP2PX(20)));
            }
            else {
                ivFigura.setX(dpInicioX + convertirDP2PX(10));
            }
            switch (valorFigura){
                case 1:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(15));
                    break;
                }
                case -2:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(3));
                    break;
                }
                case 2:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(7));
                    break;
                }
                case 3: {
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(5));
                    break;
                }
                case -3:{
                }
                case -4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(16));
                    break;
                }
                case 4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(9.5F));
                }
            }

            ivNotasPintadas.add(ivFigura);
            double espacioOcupadoNota = traducirValor(valorFigura) * tamañoUnaParte;
            dpInicioX += espacioOcupadoNota;
        }
        //Devolvemos la lista de ImageView
        return ivNotasPintadas;
    }

    /*
    *
    *@param c
    *
    */
    // Metodo que pasandole un objeto compas nos traduce los valores a IDs de imagenes, retorna una  Lista de enteros
    private List<Integer> traducirCompases(Compas c){
        List<Integer> valores = c.getFiguras();
        List<Integer> valoresDrawable = new ArrayList<>();
        for (int i : valores){
            valoresDrawable.add(MAP.get(i));
        }
        return valoresDrawable;
    }
    // Metodo para generar los valores aleatoriamente, tenemos un array con los valores posibles y coge aleatoriamente posiciones del array (descartamos posibles fallos)
    private List<Integer> generarValores(){
        List<Integer> compas = new ArrayList<>();
        Random r = new Random();
        int valor;
        int suma = 0;
        for (int i = 0; suma<COMPAS_LENGTH ; i++ ){
            valor = r.nextInt(VALORES.length);
            int traduccion = traducirValor(VALORES[valor]);
            if (suma + traduccion <= COMPAS_LENGTH) {
                compas.add(VALORES[valor]);
                suma += traduccion;
                if (suma == COMPAS_LENGTH) {
                    if (compasLleno(compas)) {
                        break;
                    }
                }
            }
        }
        return compas;
    }
    /*
    *
    *@param figura
    *
    */
    // Metodo para traducir los valores de las notas en tiempos en el compas, devuelve un entero
    private int traducirValor(int figura){
        switch (figura){
            // Los cases que no tienen corchetes es para ahorrar lineas de codigo y como los valores tienen la misma traduccion tienen el mismo return
            case 1: // Corcheas
            case -2: // Silencio Negra
            case 2:{ // Negra
                return 1;
            }
            case -3: // Silencio de Blanca
            case 3:{ // Blanca
                return 2;
            }
            case -4: //Silencio de Redonda
            case 4: { // Redonda
                return 4;
            }
            default:
                return 0;
        }
    }
    /*
    *
    *@param compas
    *
    */
    //Metodo para comprobar si el compas esta lleno o no, devuelve un booleano
    private boolean compasLleno(List<Integer> compas){
        int suma = 0;
        for (int figura : compas){
            int traduccion = traducirValor(figura);
            suma += traduccion;

            return suma >= COMPAS_LENGTH;
        }
        return false;
    }
    // Metodo para generar los compases, devuelve un objeto Compas
    private Compas generarCompas(){
        List<Integer> compas = generarValores();
        Compas c = new Compas(compas);
        return c;
    }
    /*
    *
    *@param dp
    *
    */
    // Metodo para convertir los DP (medida que utiliza Android Studio) a PX pixeles
    private int convertirDP2PX(float dp) {
        Resources r = this.getResources();
        return (int) Math.floor(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
    /*
    *
    * @param view
    *
    */
    // Metodo para volver a la pagina anterior y cerrar la actual
    public void volverMinijuego(View view){ finish(); }
}