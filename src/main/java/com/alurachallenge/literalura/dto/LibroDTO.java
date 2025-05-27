package com.alurachallenge.literalura.dto;

import com.alurachallenge.literalura.model.Idioma;

public record LibroDTO(Long id, String titulo, String tema, Idioma idioma, String resumen, int cantidadDescargas) {
}
