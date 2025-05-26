package com.alurachallenge.literalura.main;

import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.Datos;
import com.alurachallenge.literalura.model.DatosLibro;
import com.alurachallenge.literalura.model.Libro;
import com.alurachallenge.literalura.repository.LibroRepository;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.util.Conversor;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner;
    private final ConsumoAPI consumoAPI;
    private final String BASE_URL = "https://gutendex.com/books/";

    private final LibroRepository libroRepository;

    public Principal(LibroRepository libroRepository) {
        scanner = new Scanner(System.in);
        consumoAPI = new ConsumoAPI();
        this.libroRepository = libroRepository;
    }

    public void menu() {
        buscarLibro();
    }

    private void buscarLibro() {
        var titulo = scanner.nextLine();

        var url = String.format("%s?search=%s", BASE_URL, Conversor.formatoBusqueda(titulo));
        var json = consumoAPI.obtenerDatos(url);

        var busqueda = Conversor.convertirDatosFromJson(json, Datos.class);

        Optional<DatosLibro> libroBuscado = busqueda.libros().stream()
                .filter(l -> l.titulo().toLowerCase().contains(titulo.toLowerCase()))
                .findFirst();

        if (libroBuscado.isPresent()) {
            DatosLibro datosLibro = libroBuscado.get();
            System.out.printf("Libro encontrado: %s%n", datosLibro);

            var autores = datosLibro.autores()
                    .stream()
                    .map(Autor::new)
                    .toList();
            var libro = new Libro(datosLibro);

            libro.setAutores(autores);
            libroRepository.save(libro);
        } else
            System.out.printf("No se ha encontrado el libro a partir del título: %s%n", titulo);
    }
}
