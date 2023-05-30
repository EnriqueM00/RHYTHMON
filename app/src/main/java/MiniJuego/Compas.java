package MiniJuego;

import java.util.List;

// CLASE COMPAS
public class Compas {

    // Variable global
    private List<Integer> figuras; //Cada figura es un int (redonda=4, blanca=3, negra=2, corcheas=1)

    // Constructor de la clase
    public Compas(List<Integer> figuras){
        this.figuras = figuras;
    }

    // GETTERS Y SETTERS
    private void setFiguras(List<Integer> figurasSetear){ this.figuras = figurasSetear; }

    protected List<Integer> getFiguras(){ return figuras;}
}
