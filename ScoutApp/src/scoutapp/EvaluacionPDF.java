/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package scoutapp;

/**
 *
 * @author fernando.pedridomarino
 */
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class EvaluacionPDF {
    
    private String nombreJugador;
    private String posicionJugador;
    private int edadJugador;

    public EvaluacionPDF(String nombre, String posicion, int edad) {
        this.nombreJugador = nombre;
        this.posicionJugador = posicion;
        this.edadJugador = edad;
    }

    EvaluacionPDF() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public void exportarInformePDF() {
        // Ruta del escritorio en Linux Mint
        String rutaPDF = System.getProperty("user.home") + "/Escritorio/informe_evaluacion.pdf";

        try (PDDocument document = new PDDocument()) {
            // Crear una nueva página
            PDPage page = new PDPage();
            document.addPage(page);

            // Cargar una fuente personalizada desde un archivo TTF
            File fontFile = new File("resources/fonts/LiberationSans-Regular.ttf");
            PDType0Font customFont = PDType0Font.load(document, fontFile);

            // Crear un flujo de contenido para escribir en la página
            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(customFont, 16); // Usar la fuente personalizada
                contentStream.beginText();
                contentStream.setLeading(20f);
                contentStream.newLineAtOffset(50, 750);

                // Título
                contentStream.showText("Informe de Evaluación");
                contentStream.newLine();
                contentStream.newLine();

                // Información del jugador proporcionada por el usuario
                contentStream.setFont(customFont, 12); // Cambiar tamaño de fuente
                contentStream.showText("Jugador: " + nombreJugador);
                contentStream.newLine();
                contentStream.showText("Posición: " + posicionJugador);
                contentStream.newLine();
                contentStream.showText("Edad: " + edadJugador);
                contentStream.newLine();
                contentStream.newLine();
                contentStream.showText("Observaciones: Este jugador tiene un buen rendimiento.");
                contentStream.newLine();

                contentStream.endText();
            }

            // Guardar el documento
            document.save(rutaPDF);
            System.out.println("PDF generado con éxito: " + rutaPDF);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Solicitar datos del jugador al usuario
        Scanner scanner = new Scanner(System.in);

        System.out.print("Introduce el nombre del jugador: ");
        String nombre = scanner.nextLine();

        System.out.print("Introduce la posición del jugador: ");
        String posicion = scanner.nextLine();

        System.out.print("Introduce la edad del jugador: ");
        int edad = scanner.nextInt();

        // Crear instancia de EvaluacionPDF con los datos del usuario
        EvaluacionPDF evaluacion = new EvaluacionPDF(nombre, posicion, edad);

        // Exportar el informe al cerrar la aplicación
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Generando el PDF antes de cerrar la aplicación...");
            evaluacion.exportarInformePDF();
        }));

        System.out.println("Presiona Ctrl+C para cerrar la aplicación y generar el PDF.");
    }
}