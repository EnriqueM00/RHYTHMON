package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MiniJuego extends AppCompatActivity {

    private ConstraintLayout layoutActual;
    private ImageView ivLineaCompas;
    private int codAlumno, tempo, dictados;
    private final int PARTES_COMPAS = 4;
    private final int COMPAS_LENGTH = 4;
    private final int[] valores = {1, -2, 2, -3, 3, -4, 4};
    public final Map<Integer, Integer> map = new HashMap<Integer, Integer>() {{
        put(1,R.drawable.corcheas);
        put(-2,R.drawable.silencio_negra);
        put(2,R.drawable.negra);
        put(-3,R.drawable.silencio_blanca);
        put(3,R.drawable.blanca);
        put(-4,R.drawable.silencio_redonda);
        put(4,R.drawable.redonda);
    }};
    private Compas compas1, compas2;
    private List<Integer> idImagenesCompas1, idImagenesCompas2;
    private List<ImageView> listaFigurasDraw1, listaFigurasDraw2;
    private TextView tvTempo, tvDictados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_juego);

        Bundle b = getIntent().getExtras();
        codAlumno = b.getInt("codAlumno");
        tempo = b.getInt("tempo");
        dictados = b.getInt("dictados");

        tvDictados = findViewById(R.id.tvNumDictados);
        tvTempo = findViewById(R.id.tv_Tempo);

        tvTempo.setText("=  " + String.valueOf(tempo));
        tvDictados.setText("Nº " + String.valueOf(dictados));

        layoutActual = findViewById(R.id.layoutCompas);
        ivLineaCompas = findViewById(R.id.ivBarraCompas);

        compas1 = generarCompas();
        compas2 = generarCompas();

        idImagenesCompas1 = traducirCompases(compas1);
        idImagenesCompas2 = traducirCompases(compas2);

        listaFigurasDraw1 = dibujarFigurasRitmicas(idImagenesCompas1, compas1);
        listaFigurasDraw2 = dibujarFigurasRitmicas(idImagenesCompas2, compas2);
    }

    private List<ImageView> dibujarFigurasRitmicas(List<Integer> listaIdsImagenes, Compas c){
        List<ImageView> ivNotasPintadas = new ArrayList<>();
        float dpInicioX = findViewById(R.id.ivBarraGruesa).getX();
        double tamañoUnCompas = (findViewById(R.id.ivBarraDoble).getX() - dpInicioX) / 2;
        double tamañoUnaParte = tamañoUnCompas / PARTES_COMPAS;

        for (int i = 0; i < listaIdsImagenes.size();i++){
            int figuraDrawable = listaIdsImagenes.get(i);
            int valorFigura = c.getFiguras().get(i);

            ImageView ivFigura = new ImageView(this);
            ivFigura.setImageDrawable(getResources().getDrawable(figuraDrawable));
            ivFigura.setScaleType(ImageView.ScaleType.FIT_CENTER);

            RelativeLayout.LayoutParams lpFigura;

            switch (valorFigura)
            {
                case -2:
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
            ivFigura.setX((dpInicioX));
            ivFigura.setY(((ivLineaCompas.getY()-ivLineaCompas.getHeight()) / 2));
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