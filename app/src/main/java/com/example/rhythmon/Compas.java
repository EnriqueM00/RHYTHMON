package com.example.rhythmon;

import java.util.List;

public class Compas {

    private List<Integer> figuras; //Cada figura es un int (redonda=4, blanca=3, negra=2, corcheas=1)

    public Compas(List<Integer> figuras){
        this.figuras = figuras;
    }

    private void setFiguras(List<Integer> figurasSetear){ this.figuras = figurasSetear; }

    protected List<Integer> getFiguras(){ return figuras;}

}
