package com.alurachallenge.literalura.model;

public enum Idioma {
    ES("es", "español"),
    EN("en", "inglés"),
    FR("fr", "francés"),
    PT("pt", "prtugués"),
    DE("de", "alemán"),
    IT("it", "italiano");

    private final String codigo;
    private final String nombre;

    Idioma(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public static Idioma getIdiomaFromCodigo(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.codigo.equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        return null;
    }

    public static Idioma getIdiomaFromNombre(String nombre) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.nombre.equalsIgnoreCase(nombre)) {
                return idioma;
            }
        }
        return null;
    }
}
