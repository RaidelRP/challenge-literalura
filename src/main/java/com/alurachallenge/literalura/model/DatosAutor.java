package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") int nacimiento,
        @JsonAlias("death_year") int muerte) {

    @Override
    public String toString() {
        var partes = nombre.split(", ");
        StringBuilder resultado = new StringBuilder();
        for (int i = partes.length - 1; i >= 0; i--) {
            resultado.append(partes[i]).append(i > 0 ? " " : "");
        }
        return resultado.toString();
    }
}
