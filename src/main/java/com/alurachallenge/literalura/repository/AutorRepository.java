package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {
    Optional<Autor> findFirstByNombre(String nombre);

    boolean existsByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE :anno BETWEEN a.nacimiento AND a.muerte")
    List<Autor> autoresPorAnno(int anno);
}
