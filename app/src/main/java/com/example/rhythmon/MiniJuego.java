package com.example.rhythmon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MiniJuego extends AppCompatActivity {

    private final int COMPAS_LENGTH = 4;
    private final int VALOR_MAXIMO = 9;
    private final int VALOR_MINIMO = -4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mini_juego);
        Compas compas1 = generarCompas();
        Compas compas2 = generarCompas();
    }

    public List<Integer> generarValores(){
        List<Integer> compas = new ArrayList<Integer>();
        Random r = new Random();
        for (int i = 0; i<COMPAS_LENGTH; i++){
            int valor = r.nextInt(VALOR_MAXIMO)-VALOR_MINIMO;
            compas.add(valor);
        }
        return compas;
    }

    public boolean compasLleno(List<Integer> compas){
        int suma = 0;

        for (int figura : compas){
            int traduccion = traducirValor(figura);
            suma += traduccion;

            if (suma > COMPAS_LENGTH){
                return false;
            }
            else if (suma == COMPAS_LENGTH){
                return true;
            }
        }
        return false;
    }

    public int traducirValor(int figura){
        switch (figura){
            case 1:
            case -2:
            case 2:{
                return 1;
                break;
            }
            case -3:
            case 3:{
                return 2;
                break;
            }
            case -4:
            case 4: {
                return 4;
                break;
            }
            default:
                return 0;
        }
    }

    public Compas generarCompas(){
        List<Integer> compas = generarValores();

        while (!compasLleno(compas)){
            compas = generarValores();
        }
        Compas c = new Compas(compas);
        return c;
    }
}