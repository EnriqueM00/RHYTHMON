package MiniJuego;

// Enumeración FormulasRitmicas (enum porque solo les enumeramos)
public enum FormulasRitmicas {

    //Silencios (s) valor negativo
    BLANCA( "blanca"),
    sBLANCA( "silencio_blanca"),
    NEGRA( "negra"),
    sNEGRA( "silencio_negra"),
    CORCHEAS( "corcheas"),
    REDONDA( "redonda"),
    sREDONDA( "silencio_redonda");

    // Variable global
    private final String NOMBRE_IMG_XML; // Declaramos una cadena para el nombre de las imagenes XML de las notas (final porque nunca cambia)

    // Constructor de la clase
    FormulasRitmicas(String nombreImgXML){
        this.NOMBRE_IMG_XML = nombreImgXML;
    }

    // GETTER (setter no hace falta ya que le asignamos el valor una vez en la construccion)
    public String getNombreImgXML(){ return NOMBRE_IMG_XML; }

}
