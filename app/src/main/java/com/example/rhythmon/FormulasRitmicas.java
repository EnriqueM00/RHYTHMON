package com.example.rhythmon;

public enum FormulasRitmicas {

    //Silencios (s) valor negativo
    BLANCA( "blanca"),
    sBLANCA( "silencio_blanca"),
    NEGRA( "negra"),
    sNEGRA( "silencio_negra"),
    CORCHEAS( "corcheas"),
    REDONDA( "redonda"),
    sREDONDA( "silencio_redonda");

    private final String NOMBRE_IMG_XML;

    FormulasRitmicas( String nombreImgXML){

        this.NOMBRE_IMG_XML = nombreImgXML;
    }

    public String getNombreImgXML(){ return NOMBRE_IMG_XML; }


    public static FormulasRitmicas findByNombreImgXML(String nombreImgXML){
        for (FormulasRitmicas f : FormulasRitmicas.values()){
            if (f.getNombreImgXML().equals(nombreImgXML)){
                return f;
            }
        }
        return null;
    }
}
