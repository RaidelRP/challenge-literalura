package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.model.DatosLibro;
import com.alurachallenge.literalura.model.Idioma;
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

    public void listarLibros() {
        var libros = libroRepository.findAll();
        libros.forEach(System.out::println);
    }

    public void listarLibrosPorIdioma(String idioma) {
        Idioma lang = Idioma.getIdiomaFromCodigo(idioma);
        if (lang == null) {
            lang = Idioma.getIdiomaFromNombre(idioma);
        }
        if (lang != null) {
            var libros = libroRepository.findAllByIdioma(lang);
            if (!libros.isEmpty()) {
                libros.forEach(System.out::println);
            } else {
                System.out.printf("No se han encontrado libros en %s%n", lang.getNombre());
            }
        } else {
            System.out.println("El idioma buscado no existe o no está escrito correctamente");
        }
    }

    public void listarAutores() {
        autorService.listarAutores();
    }

    public void buscarAutoresPorAnno(int anno) {
        autorService.buscarAutoresPorAnno(anno);
    }

    public void topLibrosDescargados() {
        var libros = libroRepository.topLibrosDescargados();
        libros.forEach(System.out::println);
    }

    public void buscarLibrosPorAutor(String nombre) {
        var libros = libroRepository.findAll();
        var autores = autorService.buscarAutoresPorNombre(nombre);
        autores.stream()
                .forEach(a -> libros.stream() // Por cada autor que coincida el nombre
                        .filter(l -> libroContieneAutor(l, a))  // Filtrar de todos los libros los que contengan cada autor
                        .forEach(System.out::println)
                );
    }

    public boolean libroContieneAutor(Libro libro, Autor autor) {
        var autores = libro.getAutores();
        for (Autor a : autores) {
            if (a.getNombre().equals(autor.getNombre())) {
                return true;
            }
        }
        return false;
    }
}
