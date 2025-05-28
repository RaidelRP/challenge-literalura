package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Idioma;
import com.alurachallenge.literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibroRepository extends JpaRepository<Libro, Long> {
    Optional<Libro> findFirstByTituloContainingIgnoreCase(String titulo);

    Optional<Libro> findFirstByTitulo(String titulo);

    List<Libro> findAllByIdioma(Idioma lang);

    @Query("SELECT l FROM Libro l ORDER BY l.cantidadDescargas DESC LIMIT 10")
    List<Libro> topLibrosDescargados();
}
