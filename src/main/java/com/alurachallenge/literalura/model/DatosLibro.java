package com.alurachallenge.literalura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autores,
        @JsonAlias("subjects") List<String> tema,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("summaries") List<String> resumen,
        @JsonAlias("download_count") int cantidadDescargas) {

    @Override
    public String toString() {
        return String.format("\"%s\", %s. %d descargas", titulo(), autores(), cantidadDescargas());
    }
}
