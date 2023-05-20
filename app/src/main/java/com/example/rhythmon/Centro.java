package com.example.rhythmon;

public class Centro {

    private int numAlumnos, codCentro;
    private String nombre, id_user,contraseña, ciudad;

    public Centro (String nombre, String id_user,String contraseña, String ciudad, int numAlumnos){
        this.nombre = nombre;
        this.id_user = id_user;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
        this.numAlumnos = numAlumnos;
    }

    public Centro (int codCentro, String nombre, String id_user,String contraseña, String ciudad, int numAlumnos){
        this.codCentro = codCentro;
        this.nombre = nombre;
        this.id_user = id_user;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
        this.numAlumnos = numAlumnos;
    }

    public int getCodCentro(){ return codCentro; }

    public String getNombre(){ return nombre; }

    public String getId_user(){ return id_user; }

    public String getContraseña(){ return contraseña; }

    public String getCiudad(){ return ciudad; }

    public int getNumAlumnos(){ return numAlumnos; }

    public void setCodCentro(int codCentroSetear){ this.codCentro = codCentroSetear; }

    public void setNombre(String nombreSetear){ this.nombre = nombreSetear; }

    public void setId_user(String id_userSetear){ this.id_user = id_userSetear; }

    public void setContraseña(String contraseñaSetear){ this.contraseña = contraseñaSetear; }

    public void setCiudad(String ciudadSetear){ this.ciudad = ciudadSetear; }

    public void setNumAlumnos(int numAlumnosSetear){ this.numAlumnos = numAlumnosSetear; }

    public String toString(){
        return getNombre() + " \t " + getId_user() + " - " + getContraseña() + "\t" + getCiudad() + "\t" + getNumAlumnos();
    }

}