package com.alurachallenge.literalura.service;

import com.alurachallenge.literalura.model.Autor;
import com.alurachallenge.literalura.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutorService {
    @Autowired
    private AutorRepository autorRepository;

    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Autor insertarAutor(Autor autor) {
        Optional<Autor> a = autorRepository.findFirstByNombre(autor.getNombre());
        return a.orElse(autor);
    }
}
