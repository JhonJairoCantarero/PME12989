package com.example.pme12989.procesos;

public class TransaccionesPaises {
    //Nombre de la base de datos
    public static final String NameDatabase = "EXAMEN_MOVIL";

    //Creación de las Tabla Paises de la base de datos
    public static final String tablapaises = "paises";

    //Campos especificos de la  tabla paises
    public static final String idpais = "idpais";
    public static final String nombrepais = "nombrepais";
    public static final String codigopais = "codigopais";

    //Transacciones DDL (data definition lenguage)
    public static final String CreateTablePaises = "CREATE TABLE " + tablapaises +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombrepais TEXT, codigopais TEXT)";

    public static final String DropTablePaises = "DROP TABLE IF EXISTS " + tablapaises;

    public String getNombrepais() {
        return nombrepais;
    }

    public String getCodigopais() {
        return codigopais;
    }



}
