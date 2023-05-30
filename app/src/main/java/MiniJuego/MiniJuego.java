package MiniJuego;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.res.Resources;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import Clases_BBDD.Puntuacion;

public class MiniJuego extends AppCompatActivity {

    private Handler masterHandler; //Manejador de hilos
    private ConstraintLayout layoutActual;
    private ImageView ivLineaCompas,ivLineaGuia;
    private SoundPool sp;
    private ImageButton btnPlay, btnPulsador;
    private Long tiempoInicioDictado;
    private double puntuacion = 0;
    private int codAlumno, tempo, dictados, duraciónPulso, idSonido, contador, dictadoActual;
    private final int PARTES_COMPAS = 4;
    private final int COMPAS_LENGTH = 4;
    private final int[] valores = {1, -2, 2, -3, 3, -4, 4};
    private final Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{
        put(1, R.drawable.corcheas);
        put(-2,R.drawable.silencio_negra);
        put(2,R.drawable.negra);
        put(-3,R.drawable.silencio_blanca);
        put(3,R.drawable.blanca);
        put(-4,R.drawable.silencio_redonda);
        put(4,R.drawable.redonda);
    }};
    private List<Long> listaTiemposPulsador = new ArrayList<>();
    private Compas compas1, compas2;
    private CheckBox checkBoxLineaGuia;
    private List<ImageView> listaFigurasDraw1;
    private TextView tvTempo, tvDictados, tvCuentaAtras, tvPuntuacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_juego);

        masterHandler = new Handler();
        sp = new SoundPool.Builder().build();
        idSonido = sp.load(this, R.raw.golpe_metronomo, 1);
        //sp.play(idSonido, 1, 1, 1, 0, 0);

        Bundle b = getIntent().getExtras();
        codAlumno = b.getInt("codAlumno");
        tempo = b.getInt("tempo");
        dictados = b.getInt("dictados");

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

        compas1 = generarCompas();
        compas2 = generarCompas();



        layoutActual.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        listaFigurasDraw1 = dibujarFigurasRitmicas(compas1, compas2);
                        layoutActual.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    }
                });



    }

    public void btnPulsador(View view){
        sp.play(idSonido, 1, 1, 1, 0, 0);
        listaTiemposPulsador.add(System.currentTimeMillis());
    }

    public void btnPlay(View view){
        contador = 3;
        masterHandler.post(hiloCuenta);
        masterHandler.postDelayed(hiloCuenta, duraciónPulso); //Imprime el 2
        masterHandler.postDelayed(hiloCuenta, 2 * duraciónPulso); //Imprime el 1
        masterHandler.postDelayed(hiloCuenta, 3 * duraciónPulso); //Imprime el 0
        dictadoActual++;
    }

    private void comprobarDictado(){
        // Comprobamos si no se dio ninguna pulsacion
        if (listaTiemposPulsador.size() == 0){
            dictadoActual--;
            return; //El alumno solo observo el dictado
        }

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

        // REDUCIMOS TIEMPO DE LAS PULSACIONES, QUITANDO EL VALOR INICIAL
        for (int i = 0; i<listaTiemposPulsador.size(); i++)
            listaTiemposPulsador.set(i, (listaTiemposPulsador.get(i) - tiempoInicioDictado) );


        List<Long> listaTiemposPulsadorAprox = new ArrayList<>();
        // RECORREMOS LAS DOS LISTAS COMPARANDO LA LONGITUD DE LAS LISTAS Y SU RESPECTIVOS VALORES, HACIENDO UN BAREMO DE LA PRECISION DE LAS PULSACIONES
        if (pulsacionesPerfectas.size() == listaTiemposPulsador.size()){
            puntuacion += 100;
            listaTiemposPulsadorAprox = listaTiemposPulsador;
        }
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
                puntuacion += 50 + ((200 - difernciaPulsaciones) * 0.25);
            }
            else if (difernciaPulsaciones <= 600){
                puntuacion += 25 + ((200 - difernciaPulsaciones) * 0.05);
            }
        }

        // Actualizamos puntuación
        tvPuntuacion.setText("Puntuación: " + String.valueOf(puntuacion));

        pulsacionesPerfectas.clear();
        listaTiemposPulsador.clear();
        listaTiemposPulsadorAprox.clear();

        if (dictadoActual == dictados){
            // Llamada al metodo Insertar Puntuacion
            Intent i = new Intent(this, Puntuacion.class);
            startActivity(i);
        }
        else {
            vaciarCompas();
        }

    }

    private void vaciarCompas(){
        for (ImageView iv :listaFigurasDraw1){
            layoutActual.removeView(iv);
        }
        compas1 = generarCompas();
        compas2 = generarCompas();

        listaFigurasDraw1 = dibujarFigurasRitmicas(compas1,compas2);
    }

    private void moverBarra(){
        float tamañoDictado = (findViewById(R.id.ivBarraDoble).getX() - findViewById(R.id.ivBarraGruesa).getX());
        TranslateAnimation moverDerecha = new TranslateAnimation( 0 , tamañoDictado, 0, 0);
        //Desde la posicion donde está hasta el inicio de la clave de sol
        moverDerecha.setDuration(duraciónPulso * 8);//Duracion 2 compases
        moverDerecha.setFillAfter(true);//Tiene que estar
        moverDerecha.setInterpolator(new LinearInterpolator());
        ivLineaGuia.startAnimation(moverDerecha);
    }


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

    private void cuentaAtras(){
        if (contador == 3){
            cambiarHabilitacionBotones();
            masterHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cambiarHabilitacionBotones();
                    comprobarDictado();
                }
            },duraciónPulso * 11); //Por 11 por 8 del compas + 3 de espera
        }
        sp.play(idSonido, 1, 1, 1, 0, 0);
        if (contador == 0){
            btnPulsador.setEnabled(true);
            tvCuentaAtras.setText("");
            masterHandler.removeCallbacks(hiloCuenta);
            if (checkBoxLineaGuia.isChecked()){
                checkBoxLineaGuia.setEnabled(false);
                moverBarra();
            }
            tiempoInicioDictado = System.currentTimeMillis();
        }
        else{
            tvCuentaAtras.setText("\n " + contador);
        }
        contador--;
    }

    Runnable hiloCuenta = new Runnable() { //
        @Override
        public void run() {
            cuentaAtras();
        }
    };


    private List<ImageView> dibujarFigurasRitmicas( Compas c, Compas c2){
        List<Integer> idImagenesCompas1 = traducirCompases(c);
        List<Integer> idImagenesCompas2 = traducirCompases(c2);
        List<ImageView> ivNotasPintadas = new ArrayList<>();
        float dpInicioX = findViewById(R.id.ivBarraGruesa).getX();
        float dpInicial = dpInicioX;
        double tamañoUnCompas = (findViewById(R.id.ivBarraDoble).getX() - dpInicioX) / 2;
        double tamañoUnaParte = tamañoUnCompas / PARTES_COMPAS;

        for (int i = 0; i < idImagenesCompas1.size();i++){
            int figuraDrawable = idImagenesCompas1.get(i);
            int valorFigura = c.getFiguras().get(i);

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
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(11));
                    break;
                }
                case -2:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(3));
                    break;
                }
                case 2:
                case 3:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(3));
                    break;
                }
                case -3:{
                }
                case -4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(16));
                    break;
                }
                case 4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(12));
                }
            }

            ivNotasPintadas.add(ivFigura);
            double espacioOcupadoNota = traducirValor(valorFigura) * tamañoUnaParte;
            dpInicioX += espacioOcupadoNota;
        }
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
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(11));
                    break;
                }
                case -2:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(3));
                    break;
                }
                case 2:
                case 3:{
                    ivFigura.setY(ivLineaCompas.getY() - convertirDP2PX(3));
                    break;
                }
                case -3:{
                }
                case -4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(16));
                    break;
                }
                case 4:{
                    ivFigura.setY(ivLineaCompas.getY() + convertirDP2PX(12));
                }
            }

            ivNotasPintadas.add(ivFigura);
            double espacioOcupadoNota = traducirValor(valorFigura) * tamañoUnaParte;
            dpInicioX += espacioOcupadoNota;
        }
        return ivNotasPintadas;
    }


    public List<Integer> traducirCompases(Compas c){
        List<Integer> valores = c.getFiguras();
        List<Integer> valoresDrawable = new ArrayList<>();
        for (int i : valores){
            valoresDrawable.add(map.get(i));
        }
        return valoresDrawable;
    }

    public List<Integer> generarValores(){
        List<Integer> compas = new ArrayList<>();
        Random r = new Random();
        int valor;
        int suma = 0;
        for (int i = 0; suma<COMPAS_LENGTH ; i++ ){
            valor = r.nextInt(valores.length);
            int traduccion = traducirValor(valores[valor]);
            if (suma + traduccion <= COMPAS_LENGTH) {
                compas.add(valores[valor]);
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

    public int traducirValor(int figura){
        switch (figura){
            case 1:
            case -2:
            case 2:{
                return 1;
            }
            case -3:
            case 3:{
                return 2;
            }
            case -4:
            case 4: {
                return 4;
            }
            default:
                return 0;
        }
    }

    public boolean compasLleno(List<Integer> compas){
        int suma = 0;

        for (int figura : compas){
            int traduccion = traducirValor(figura);
            suma += traduccion;

            return suma >= COMPAS_LENGTH;
        }
        return false;
    }

    public Compas generarCompas(){
        List<Integer> compas = generarValores();

        Compas c = new Compas(compas);
        return c;
    }

    public int convertirDP2PX(float dp) {
        Resources r = this.getResources();
        return (int) Math.floor(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public void volverMinijuego(View view){
        finish();
    }
}