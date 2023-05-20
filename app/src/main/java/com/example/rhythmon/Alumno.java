package com.example.rhythmon;

public class Alumno {

    private int anios, codCentro, codAlumno;
    private String id_user, nombre, apellidos, contraseña;

    public Alumno (String id_user, String nombre, String apellidos, String contraseña, int anios, int codCentro){
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.anios = anios;
        this.codCentro = codCentro;
    }

    public Alumno (int codAlumno,String id_user, String nombre, String apellidos, String contraseña, int anios, int codCentro){
        this.codAlumno = codAlumno;
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.anios = anios;
        this.codCentro = codCentro;
    }

    public int getCodAlumno(){ return this.codAlumno;}

    public String getId_user(){ return this.id_user; }

    public String getNombre(){ return this.nombre; }

    public String getApellidos(){ return this.apellidos; }

    public String getContraseña(){ return this.contraseña; }

    public int getAnios(){ return this.anios; }

    public int getCodCentro(){ return this.codCentro; }

    public void setCodCentro(int codCentroSetear) { this.codCentro = codCentroSetear; }

    public void setAnios(int aniosSetear) { this.anios = aniosSetear; }

    public void setContraseña(String contraseñaSetear) { this.contraseña = contraseñaSetear; }

    public void setApellidos(String apellidosSetear) { this.apellidos = apellidosSetear; }

    public void setNombre(String nombreSetear) { this.nombre = nombreSetear; }

    public void setId_user(String id_userSetear){ this.id_user = id_userSetear; }

    public void setCodAlumno(int codAlumnoSetear){ this.codAlumno = codAlumnoSetear; }

    public String toString(){
        return getNombre() + " " + getApellidos() + " \t " + getId_user() + "-" + getContraseña() + " " + getAnios();
    }
}