package BBDD;

// Clase Estructura_BBDD
public class Estructura_BBDD {

    //Variables globales (todo es final porque no cambia nunca)

    // Nombre Tabla BD
    public static final String TABLE_CENTROS = "Centro";
    public static final String TABLE_ALUMNOS = "Alumno";
    public static final String TABLE_PUNTUACION = "Puntuacion";

    // Campos (columnas) Tabla Centro
    public static final String COD_CENTRO = "cod_centro";
    public static final String ID_USER_CENTRO = "id_user";
    public static final String NOMBRE_CENTRO = "nombre";
    public static final String CONTRASEÑA_CENTRO = "contraseña";
    public static final String CIUDAD_CENTRO = "ciudad";
    public static final String NUM_ALUMNOS = "num_alumnos";

    // Campos (columnas) Tabla Alumno
    public static final String COD_ALUMNO = "cod_alumno";
    public static final String ID_USER_ALUMNO = "id_user";
    public static final String NOMBRE_ALUMNO = "nombre";
    public static final String APELLIDOS_ALUMNO = "apellidos";
    public static final String CONTRASEÑA_ALUMNO = "contraseña";
    public static final String ANIOS = "años";
    public static final String COD_CENTRO_ALUMNO = "cod_centro";

    //Campos (columnas) Tabla Puntuaciones
    public static final String COD_PUNTUACION = "cod_puntuacion";
    public static final String PUNTUACION = "puntuacion";
    public static final String COD_ALUMNO_PUNTUACION = "cod_alumno";

    // Crear Tablas
    public static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + Estructura_BBDD.TABLE_CENTROS + " (" +
                    Estructura_BBDD.COD_CENTRO + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.ID_USER_CENTRO + " TEXT NOT NULL," +
                    Estructura_BBDD.NOMBRE_CENTRO + " TEXT," +
                    Estructura_BBDD.CONTRASEÑA_CENTRO + " TEXT NOT NULL," +
                    Estructura_BBDD.CIUDAD_CENTRO + " TEXT," +
                    Estructura_BBDD.NUM_ALUMNOS + " INTEGER UNSIGNED);";

    public static final String SQL_CREATE_TABLE2 =
            "CREATE TABLE " + Estructura_BBDD.TABLE_ALUMNOS + " (" +
                    Estructura_BBDD.COD_ALUMNO + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    Estructura_BBDD.ID_USER_ALUMNO + " TEXT NOT NULL," +
                    Estructura_BBDD.NOMBRE_ALUMNO + " TEXT," +
                    Estructura_BBDD.APELLIDOS_ALUMNO + " TEXT," +
                    Estructura_BBDD.CONTRASEÑA_ALUMNO + " TEXT NOT NULL," +
                    Estructura_BBDD.ANIOS + " INTEGER UNSIGNED," +
                    Estructura_BBDD.COD_CENTRO_ALUMNO + " INTEGER NOT NULL," +
                    "FOREIGN KEY ("+Estructura_BBDD.COD_CENTRO_ALUMNO+") " +
                    "REFERENCES " + Estructura_BBDD.TABLE_CENTROS + " (" + Estructura_BBDD.COD_CENTRO +") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE);";

    public static final String SQL_CREATE_TABLE3 =
            "CREATE TABLE " + Estructura_BBDD.TABLE_PUNTUACION + " (" +
                    Estructura_BBDD.COD_PUNTUACION + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    Estructura_BBDD.PUNTUACION + " REAL UNSIGNED," +
                    Estructura_BBDD.COD_ALUMNO_PUNTUACION + " INTEGER NOT NULL," +
                    "FOREIGN KEY (" + Estructura_BBDD.COD_ALUMNO_PUNTUACION + ") " +
                    "REFERENCES " + Estructura_BBDD.TABLE_ALUMNOS + " (" + Estructura_BBDD.COD_ALUMNO + ") " +
                    "ON UPDATE CASCADE " +
                    "ON DELETE CASCADE);";

    public static final String SQL_INSERT_CENTRO =
            "INSERT INTO " + Estructura_BBDD.TABLE_CENTROS +" (" + Estructura_BBDD.COD_CENTRO + "," + Estructura_BBDD.ID_USER_CENTRO + "," + Estructura_BBDD.NOMBRE_CENTRO + "," + Estructura_BBDD.CONTRASEÑA_CENTRO + ","+
                    Estructura_BBDD.CIUDAD_CENTRO + "," + Estructura_BBDD.NUM_ALUMNOS + ")" +
                    " VALUES (0,'root','admin','root1234','X',0);";

    public static final String SQL_INSERT_ALUMNO=
            "INSERT INTO " + Estructura_BBDD.TABLE_ALUMNOS +" (" + Estructura_BBDD.COD_ALUMNO + "," + Estructura_BBDD.ID_USER_ALUMNO + "," + Estructura_BBDD.NOMBRE_ALUMNO + "," + Estructura_BBDD.APELLIDOS_ALUMNO + ","+
                    Estructura_BBDD.CONTRASEÑA_ALUMNO + "," + Estructura_BBDD.ANIOS +  "," + Estructura_BBDD.COD_CENTRO_ALUMNO + ")" +
                    " VALUES (0,'prueba','prueba','prueba','root1234','0',0);";

    // Borrar tablas (si existen)
    public static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Estructura_BBDD.TABLE_PUNTUACION + ";"
                    + "DROP TABLE IF EXISTS" + Estructura_BBDD.TABLE_ALUMNOS + ";"
                    + "DROP TABLE IF EXISTS" + Estructura_BBDD.TABLE_CENTROS + ";" ;
}
