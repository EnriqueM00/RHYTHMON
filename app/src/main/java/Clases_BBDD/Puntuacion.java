package Clases_BBDD;

// CLASE PUNTUACION
public class Puntuacion {

    //variables globales
    private int codPuntuacion, codAlumno;  //Declaramos los enteros que necesitemos
    private double puntuacion; //Declaramos un double para la puntuación

    // Constructor de la clase
    public Puntuacion (int codPuntuacion, double puntuacion, int codAlumno){
        this.codPuntuacion = codPuntuacion;
        this.puntuacion = puntuacion;
        this.codAlumno = codAlumno;
    }

    // GETTERS Y SETTERS
    public int getCodPuntuacion(){ return codPuntuacion; }

    public double getPuntuacion(){ return puntuacion; }

    public int getCodAlumno(){ return codAlumno; }

    public void setCodPuntuacion(int codPuntuacionSetear){ this.codPuntuacion = codPuntuacionSetear; }

    public void setPuntuacion(double puntuacionSetear){ this.puntuacion = puntuacionSetear; }

    public void setCodAlumno(int codAlumnoSetear) { this.codAlumno = codAlumnoSetear; }
}
