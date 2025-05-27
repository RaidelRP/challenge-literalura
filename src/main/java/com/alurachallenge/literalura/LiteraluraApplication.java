package com.alurachallenge.literalura;

import com.alurachallenge.literalura.main.Principal;
import com.alurachallenge.literalura.repository.LibroRepository;
import com.alurachallenge.literalura.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
    @Autowired
    private LibroRepository libroRepository;

    @Autowired
    private LibroService libroService;

    public static void main(String[] args) {
        SpringApplication.run(LiteraluraApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Principal principal = new Principal(libroRepository, libroService);
        principal.menu();
    }
}
