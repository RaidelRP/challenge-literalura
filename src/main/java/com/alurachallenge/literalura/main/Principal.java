package com.alurachallenge.literalura.main;

import com.alurachallenge.literalura.model.Datos;
import com.alurachallenge.literalura.model.DatosLibro;
import com.alurachallenge.literalura.model.Idioma;
import com.alurachallenge.literalura.service.ConsumoAPI;
import com.alurachallenge.literalura.service.LibroService;
import com.alurachallenge.literalura.util.Conversor;

import java.util.Optional;
import java.util.Scanner;

public class Principal {
    private final Scanner scanner;
    private final ConsumoAPI consumoAPI;
    private final String BASE_URL = "https://gutendex.com/books/";

    private final LibroService libroService;

    public Principal(LibroService libroService) {
        scanner = new Scanner(System.in);
        consumoAPI = new ConsumoAPI();
        this.libroService = libroService;
    }

    public void menu() {
        System.out.println("*******************************");
        System.out.println("*  BIENVENIDO A LITERALURA    *");
        System.out.println("*******************************");
        System.out.println("Seleccione una opción para continuar: ");
        opciones();
        int opcion = leerEntero();
        while (opcion != 0) {
            switch (opcion) {
                case 1:
                    listarLibros();
                    break;
                case 2:
                    listarAutores();
                    break;
                case 3:
                    buscarLibro();
                    break;
                case 4:
                    listarLibrosPorIdioma();
                    break;
                case 5:
                    buscarAutoresPorAnno();
                    break;
                case 6:
                    topLibrosDescargados();
                    break;
                default:
                    System.out.println("Opción no válida");
                    break;
            }
            System.out.println("Seleccione una opción para continuar: ");
            opciones();
            opcion = leerEntero();
        }
        System.out.println("Finalizando programa");
    }

    private int leerEntero() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("El valor introducido no es un entero válido. Intente nuevamente: ");
            return leerEntero();
        }
    }

    private void opciones() {
        System.out.println("1. Listar libros");
        System.out.println("2. Listar autores");
        System.out.println("3. Buscar libro");
        System.out.println("4. Listar libros por idioma");
        System.out.println("5. Buscar autores por año");
        System.out.println("6. Libros más descargados");
        System.out.println("0. Salir");
    }

    private void listarLibros() {
        System.out.println("*******************************");
        System.out.println("\tA continuación se muestran todos los libros que se han consultado que se encuentran en la base de datos\n");
        libroService.listarLibros();
        System.out.println("*******************************\n");
    }

    private void listarAutores() {
        System.out.println("*******************************");
        System.out.println("\tA continuación se muestran todos los autores de los libros que se han consultado que se encuentran en la base de datos\n");
        libroService.listarAutores();
        System.out.println("*******************************\n");
    }

    private void listarLibrosPorIdioma() {
        System.out.println("*******************************");
        System.out.println("\tA continuación se muestran los idiomas en los que pueden ser buscados los libros. Tanto el código como el nombre son válidos para la búsqueda\n");
        System.out.println(Idioma.listarIdiomas());
        System.out.println("Selecciona un idioma para buscar:");
        String idioma = scanner.nextLine();
        libroService.listarLibrosPorIdioma(idioma);
        System.out.println("*******************************\n");
    }

    private void buscarAutoresPorAnno() {
        System.out.println("*******************************");
        System.out.println("\tA continuación se muestran los autores que estaban vivos en un año específico\n");
        System.out.println("Escriba un año para buscar:");
        int anno = leerEntero();
        libroService.buscarAutoresPorAnno(anno);
        System.out.println("*******************************\n");
    }

    private void topLibrosDescargados() {
        System.out.println("*******************************");
        System.out.println("\tA continuación se muestran los 10 libros más descargados\n");
        libroService.topLibrosDescargados();
        System.out.println("*******************************\n");
    }

    private void buscarLibro() {
        System.out.println("*******************************");
        System.out.println("\tA continuación deberá introducir parte del título de un libro para buscarlo en el sitio de Gutendex\n");
        System.out.println("Escriba el título a buscar: ");
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
            libroService.insertarLibro(datosLibro);
        } else
            System.out.printf("No se ha encontrado el libro a partir del título: \"%s\"%n", titulo);

        System.out.println("*******************************\n");
    }
}
