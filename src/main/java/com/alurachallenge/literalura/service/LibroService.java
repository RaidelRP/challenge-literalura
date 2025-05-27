package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.DatosLibro;
import com.alurachallenge.literalura.model.Libro;
import com.alurachallenge.literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private AutorService autorService;

    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public void insertarLibro(DatosLibro datosLibro) {
        var titulo = datosLibro.titulo();
        //var libroBD = libroRepository.findOneByTituloContainingIgnoreCase(titulo);
        var libroBD = libroRepository.findFirstByTitulo(titulo); // Buscar libro en la base de datos
        if (libroBD.isEmpty()) { // Si no se encuentra en la BD, insertarlo
            var autores = datosLibro.autores().stream().map(Autor::new) // Crear autor desde DatosAutor con el constructor
                    .map(a -> autorService.insertarAutor(a)) // Guardar Autor en BD o obtener desde la BD
                    .toList();
            var libro = new Libro(datosLibro);

            libro.setAutores(autores);
            libroRepository.save(libro);
        } else System.out.println("El libro buscado ya existe en la base de datos");
    }
}
