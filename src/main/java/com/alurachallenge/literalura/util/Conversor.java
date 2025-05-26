package com.alurachallenge.literalura.util;

import com.alurachallenge.literalura.dto.AutorDTO;
import com.alurachallenge.literalura.dto.LibroDTO;
import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.Libro;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Conversor {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertirDatosFromJson(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static LibroDTO libroDTOFromLibro(Libro libro) {
        return new LibroDTO(libro.getId(), libro.getTitulo(), libro.getTema(), libro.getIdioma(), libro.getResumen(), libro.getCantidadDescargas());
    }

    public static AutorDTO autorDTOFromAutor(Autor autor) {
        return new AutorDTO(autor.getNombre(), autor.getNacimiento(), autor.getMuerte());
    }

    public static List<LibroDTO> listaLibrosDTO(List<Libro> libros) {
        return libros.stream()
                .map(Conversor::libroDTOFromLibro)
                .toList();
    }

    public static List<AutorDTO> listaAutoresDTO(List<Autor> autores) {
        return autores.stream()
                .map(Conversor::autorDTOFromAutor)
                .toList();
    }

    public static String formatoBusqueda(String texto) {
        return texto.toLowerCase().replace(" ", "+");
    }

    public static String acotarTexto(String texto) {
        int max = Math.min(texto.length(), 255);
        return texto.substring(0, max);
    }
}
