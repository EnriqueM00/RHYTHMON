package Clases_BBDD;

// CLASE CENTRO
public class Centro {

    // Variables globales
    private int numAlumnos, codCentro; //Declaramos los enteros
    private String nombre, id_user,contraseña, ciudad; // Declaramos las cadenas

    // TENEMOS CONSTRUCTOR SOBRECARGADO
    // Constructor de la clase (todo menos codCentro)
    public Centro (String nombre, String id_user,String contraseña, String ciudad, int numAlumnos){
        this.nombre = nombre;
        this.id_user = id_user;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
        this.numAlumnos = numAlumnos;
    }
    // Constructor de la clase (todos los atributos)
    public Centro (int codCentro, String nombre, String id_user,String contraseña, String ciudad, int numAlumnos){
        this.codCentro = codCentro;
        this.nombre = nombre;
        this.id_user = id_user;
        this.contraseña = contraseña;
        this.ciudad = ciudad;
        this.numAlumnos = numAlumnos;
    }

    // GETTERS Y SETTERS
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

    // Metodo toString hecho para mostrarse en los ArrayList, Spinners, ItemList...
    public String toString(){
        return getNombre() + " \t " + getId_user() + " - " + getContraseña() + "\t" + getCiudad() + "\t" + getNumAlumnos();
    }

}