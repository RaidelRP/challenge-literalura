package com.alurachallenge.literalura.model;

import com.alurachallenge.literalura.util.Conversor;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "libro_autor",
            joinColumns = @JoinColumn(name = "libro_id"),
            inverseJoinColumns = @JoinColumn(name = "id_autor"))
    private List<Autor> autores;

    private String tema;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private String resumen;

    private int cantidadDescargas;

    public Libro() {
    }

    public Libro(DatosLibro datos) {
        this.titulo = datos.titulo();
        this.tema = Conversor.acotarTexto(datos.tema().get(0)); // De la API obtiene una lista de temas, en la base de datos se guardará un solo tema, el primero de la lista
        this.resumen = Conversor.acotarTexto(datos.resumen().get(0)); // De lista de resumenes, guardar el primero
        this.cantidadDescargas = datos.cantidadDescargas();
        this.idioma = Idioma.getIdiomaFromCodigo(datos.idioma().get(0)); // Obtener primer idioma de la lista
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public int getCantidadDescargas() {
        return cantidadDescargas;
    }

    public void setCantidadDescargas(int cantidadDescargas) {
        this.cantidadDescargas = cantidadDescargas;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    @Override
    public String toString() {
        return String.format("\"%s\" (%s) por %s. %d descargas", titulo, idioma.getNombre(), autores, cantidadDescargas);
    }
}
