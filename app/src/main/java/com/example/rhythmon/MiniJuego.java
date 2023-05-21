package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class MiniJuego extends AppCompatActivity {

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
    private List<Integer> valoresCompas1;
    private List<Integer> valoresCompas2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_juego);
        Compas compas1 = generarCompas();
        Compas compas2 = generarCompas();
        valoresCompas1 = traducirCompases(compas1);
        valoresCompas2 = traducirCompases(compas2);
    }



    public List<Integer> traducirCompases(Compas c){
        List<Integer> valores = c.getFiguras();
        List<Integer> valoresDrawable = new ArrayList<Integer>();
        for (int i : valores){
            valoresDrawable.add(map.get(i));
        }
        return valoresDrawable;
    }

    public List<Integer> generarValores(){
        List<Integer> compas = new ArrayList<Integer>();
        Random r = new Random();
        int valor = 0;
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

            if (suma > COMPAS_LENGTH){
                return true;
            }
            else if (suma < COMPAS_LENGTH){
                return false;
            }
            else if (suma == COMPAS_LENGTH){
                return true;
            }
        }
        return false;
    }

    public Compas generarCompas(){
        List<Integer> compas = generarValores();

        Compas c = new Compas(compas);
        return c;
    }
}