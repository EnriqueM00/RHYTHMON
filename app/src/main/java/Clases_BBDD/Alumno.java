package Clases_BBDD;

// CLASE ALUMNO
public class Alumno {

    //Variables globales
    private int anios, codCentro, codAlumno; // Declaramos los enteros
    private String id_user, nombre, apellidos, contraseña; // Declaramos las cadenas

    // TENEMOS CONSTRUCTOR SOBRE CARGADO (+ DE 1 CONSTRUCTOR)
    // Constructor de la Clase (todo menos codAlumno)
    public Alumno (String id_user, String nombre, String apellidos, String contraseña, int anios, int codCentro){
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.anios = anios;
        this.codCentro = codCentro;
    }
    // Constructor de la Clase (todos los atributos)
    public Alumno (int codAlumno,String id_user, String nombre, String apellidos, String contraseña, int anios, int codCentro){
        this.codAlumno = codAlumno;
        this.id_user = id_user;
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.contraseña = contraseña;
        this.anios = anios;
        this.codCentro = codCentro;
    }

    // GETTERS Y SETTERS

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

    // Metodo toString hecho para mostrarse en los ArrayList, Spinners, ItemList...
    public String toString(){
        return getNombre() + " " + getApellidos() + " \t " + getId_user() + "-" + getContraseña() + " " + getAnios();
    }
}