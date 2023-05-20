package com.example.rhythmon;

public class Puntuacion {

    private int codPuntuacion, codAlumno;
    private double puntuacion;

    public Puntuacion (int codPuntuacion, double puntuacion, int codAlumno){
        this.codPuntuacion = codPuntuacion;
        this.puntuacion = puntuacion;
        this.codAlumno = codAlumno;
    }

    public int getCodPuntuacion(){ return codPuntuacion; }

    public double getPuntuacion(){ return puntuacion; }

    public int getCodAlumno(){ return codAlumno; }

    public void setCodPuntuacion(int codPuntuacionSetear){ this.codPuntuacion = codPuntuacionSetear; }

    public void setPuntuacion(double puntuacionSetear){ this.puntuacion = puntuacionSetear; }

    public void setCodAlumno(int codAlumnoSetear) { this.codAlumno = codAlumnoSetear; }
}
