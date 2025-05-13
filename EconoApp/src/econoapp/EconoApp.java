/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package econoapp;

/**
 *
 * @author fernando.pedridomarino
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 * Clase principal de la aplicación de previsión económica para clubes de fútbol.
 * Permite calcular balances, registrar ingresos y gastos, y generar un reporte en PDF.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 * Clase principal de la aplicación de previsión económica para clubes de fútbol.
 * Permite calcular balances, registrar ingresos y gastos, y generar un reporte en PDF.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType0Font;

/**
 * Clase principal de la aplicación de previsión económica para clubes de fútbol.
 * Permite calcular balances, registrar ingresos y gastos, y generar un reporte en PDF.
 */
public class EconoApp {
    // Componentes de la interfaz
    private JTextField txtNombreClub, txtPatrocinios, txtNumJugadores, txtCuotaJugador, txtSubvenciones;
    private JTextField txtNumEntrenadoresTitulares, txtSalarioEntrenadorTitular;
    private JTextField txtNumEntrenadoresSegundos, txtSalarioEntrenadorSegundo;
    private JTextField txtPrecioArbitro, txtNumEquipos, txtPartidosPorEquipo;
    private JTextField txtGastosMaterial;
    private JLabel lblResultado;
    private JButton btnCalcular, btnExportarPdf;

    /**
     * Constructor de la aplicación.
     * Configura la interfaz gráfica y los eventos de los botones.
     */
    public EconoApp() {
            // Mostrar un JDialog de agradecimiento al iniciar
        mostrarDialogoDeAgradecimiento();
        // Crear la ventana principal
        JFrame frame = new JFrame("Previsión Económica - Club de Fútbol");
        frame.setSize(800, 750); // Ajustar tamaño para incluir nuevos campos
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridLayout(17, 2, 10, 10));

        // Campo para el nombre del club
        frame.add(new JLabel("Nombre del club:"));
        txtNombreClub = new JTextField();
        frame.add(txtNombreClub);

        // Campos de ingresos
        frame.add(new JLabel("Ingresos por patrocinios:"));
        txtPatrocinios = new JTextField();
        frame.add(txtPatrocinios);

        frame.add(new JLabel("Número de jugadores:"));
        txtNumJugadores = new JTextField();
        frame.add(txtNumJugadores);

        frame.add(new JLabel("Cuota por jugador:"));
        txtCuotaJugador = new JTextField();
        frame.add(txtCuotaJugador);

        frame.add(new JLabel("Ingresos por subvenciones:"));
        txtSubvenciones = new JTextField();
        frame.add(txtSubvenciones);

        // Campos de gastos - Entrenadores titulares
        frame.add(new JLabel("Número de entrenadores titulares:"));
        txtNumEntrenadoresTitulares = new JTextField();
        frame.add(txtNumEntrenadoresTitulares);

        frame.add(new JLabel("Salario por entrenador titular:"));
        txtSalarioEntrenadorTitular = new JTextField();
        frame.add(txtSalarioEntrenadorTitular);

        // Campos de gastos - Segundos entrenadores
        frame.add(new JLabel("Número de segundos entrenadores:"));
        txtNumEntrenadoresSegundos = new JTextField();
        frame.add(txtNumEntrenadoresSegundos);

        frame.add(new JLabel("Salario por segundo entrenador:"));
        txtSalarioEntrenadorSegundo = new JTextField();
        frame.add(txtSalarioEntrenadorSegundo);

        // Campos de gastos - Árbitros
        frame.add(new JLabel("Precio por árbitro por partido:"));
        txtPrecioArbitro = new JTextField();
        frame.add(txtPrecioArbitro);

        frame.add(new JLabel("Número de equipos:"));
        txtNumEquipos = new JTextField();
        frame.add(txtNumEquipos);

        frame.add(new JLabel("Partidos en casa por equipo (aprox.):"));
        txtPartidosPorEquipo = new JTextField();
        frame.add(txtPartidosPorEquipo);

        // Otros gastos
        frame.add(new JLabel("Gastos en material:"));
        txtGastosMaterial = new JTextField();
        frame.add(txtGastosMaterial);

        // Botón para calcular balance
        btnCalcular = new JButton("Calcular Balance");
        frame.add(btnCalcular);

        lblResultado = new JLabel("");
        frame.add(lblResultado);

        // Botón para exportar a PDF
        btnExportarPdf = new JButton("Exportar a PDF");
        frame.add(btnExportarPdf);

        // Acción del botón calcular
        btnCalcular.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calcularBalance();
            }
        });

        // Acción del botón exportar
        btnExportarPdf.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarAPdf();
            }
        });

        // Mostrar la ventana
        frame.setVisible(true);
    }

    /**
     * Método para calcular el balance económico.
     * Calcula los ingresos, gastos y balance final, y los muestra en la interfaz.
     */
    private void calcularBalance() {
        try {
            // Obtener datos de entrada
            double patrocinios = Double.parseDouble(txtPatrocinios.getText());
            int numJugadores = Integer.parseInt(txtNumJugadores.getText());
            double cuotaJugador = Double.parseDouble(txtCuotaJugador.getText());
            double subvenciones = Double.parseDouble(txtSubvenciones.getText());

            int numEntrenadoresTitulares = Integer.parseInt(txtNumEntrenadoresTitulares.getText());
            double salarioEntrenadorTitular = Double.parseDouble(txtSalarioEntrenadorTitular.getText());

            int numEntrenadoresSegundos = Integer.parseInt(txtNumEntrenadoresSegundos.getText());
            double salarioEntrenadorSegundo = Double.parseDouble(txtSalarioEntrenadorSegundo.getText());

            double precioArbitro = Double.parseDouble(txtPrecioArbitro.getText());
            int numEquipos = Integer.parseInt(txtNumEquipos.getText());
            int partidosPorEquipo = Integer.parseInt(txtPartidosPorEquipo.getText());

            double gastosMaterial = Double.parseDouble(txtGastosMaterial.getText());

            // Calcular ingresos totales
            double ingresosTotales = patrocinios + (numJugadores * cuotaJugador) + subvenciones;

            // Calcular gastos totales
            double gastosEntrenadoresTitulares = numEntrenadoresTitulares * salarioEntrenadorTitular;
            double gastosEntrenadoresSegundos = numEntrenadoresSegundos * salarioEntrenadorSegundo;
            double gastosArbitros = precioArbitro * numEquipos * partidosPorEquipo; // Nuevo cálculo
            double gastosTotales = gastosEntrenadoresTitulares + gastosEntrenadoresSegundos + gastosArbitros + gastosMaterial;

            // Calcular balance
            double balance = ingresosTotales - gastosTotales;

            // Mostrar resultados
            lblResultado.setText("Balance Final: " + balance + " €");

            // Guardar el resultado en un campo oculto para usar en el PDF
            lblResultado.putClientProperty("balance", balance);

            if (balance < 0) {
                lblResultado.setText(lblResultado.getText() + " (Déficit: Necesitas más ingresos)");
            }
        } catch (NumberFormatException ex) {
            lblResultado.setText("Por favor, introduce valores numéricos válidos.");
        }
    }
    
    /**
     * Método para mostrar un JDialog de agradecimiento al iniciar la aplicación.
     */
    private void mostrarDialogoDeAgradecimiento() {
        JDialog dialogo = new JDialog();
        dialogo.setTitle("Bienvenida");
        dialogo.setSize(400, 200);
        dialogo.setLocationRelativeTo(null); // Centrar el JDialog en la pantalla
        dialogo.setLayout(new BorderLayout());

        JLabel lblMensaje = new JLabel(
            "<html><div style='text-align: center;'>Gracias por usar esta app.<br>Todos los derechos son de @fernandopedridodev</div></html>",
            SwingConstants.CENTER
        );
        lblMensaje.setFont(new Font("Arial", Font.PLAIN, 14));

        dialogo.add(lblMensaje, BorderLayout.CENTER);

        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialogo.dispose(); // Cierra el JDialog
            }
        });

        dialogo.add(btnCerrar, BorderLayout.SOUTH);

        dialogo.setModal(true); // Bloquear interacción con la ventana principal hasta cerrar el JDialog
        dialogo.setVisible(true);
    }


    /**
     * Método para exportar el balance calculado a un archivo PDF.
     * Incluye los datos ingresados por el usuario y genera un reporte completo.
     */
    private void exportarAPdf() {
        try {
            // Obtener datos ingresados por el usuario
            String nombreClub = txtNombreClub.getText();
            double balance = (double) lblResultado.getClientProperty("balance");
            String contenido = String.format(
                "Club: %s\n" +
                "Patrocinios: %.2f €\n" +
                "Número de jugadores: %d\n" +
                "Cuota por jugador: %.2f €\n" +
                "Subvenciones: %.2f €\n" +
                "Entrenadores titulares: %d, Salario: %.2f €\n" +
                "Segundos entrenadores: %d, Salario: %.2f €\n" +
                "Precio árbitro por partido: %.2f €\n" +
                "Número de equipos: %d\n" +
                "Partidos en casa por equipo: %d\n" +
                "Gastos material: %.2f €\n" +
                "Balance Final: %.2f €",
                nombreClub, Double.parseDouble(txtPatrocinios.getText()), Integer.parseInt(txtNumJugadores.getText()),
                Double.parseDouble(txtCuotaJugador.getText()), Double.parseDouble(txtSubvenciones.getText()),
                Integer.parseInt(txtNumEntrenadoresTitulares.getText()), Double.parseDouble(txtSalarioEntrenadorTitular.getText()),
                Integer.parseInt(txtNumEntrenadoresSegundos.getText()), Double.parseDouble(txtSalarioEntrenadorSegundo.getText()),
                Double.parseDouble(txtPrecioArbitro.getText()), Integer.parseInt(txtNumEquipos.getText()),
                Integer.parseInt(txtPartidosPorEquipo.getText()), Double.parseDouble(txtGastosMaterial.getText()),
                balance
            );

            // Crear un archivo PDF
            PDDocument document = new PDDocument();
            PDPage page = new PDPage();
            document.addPage(page);

            // Ruta al archivo de la fuente TTF
            File fontFile = new File("src/resources/Roboto_SemiCondensed-ExtraLight.ttf");
            PDType0Font font = PDType0Font.load(document, fontFile);

            // Escribir contenido en el PDF
            PDPageContentStream contentStream = new PDPageContentStream(document, page);
            contentStream.beginText();
            contentStream.setFont(font, 12); // Aplicar la fuente personalizada
            contentStream.setLeading(14.5f);
            contentStream.newLineAtOffset(25, 750);

            contentStream.showText("Reporte de Previsión Económica:");
            contentStream.newLine();
            contentStream.newLine();
            for (String line : contenido.split("\n")) {
                contentStream.showText(line);
                contentStream.newLine();
            }
            contentStream.endText();
            contentStream.close();

            // Usar JFileChooser para seleccionar ubicación de guardado
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Guardar como...");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Archivos PDF", "pdf"));

            int userSelection = fileChooser.showSaveDialog(null);

            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();

                // Garantizar que el archivo tenga la extensión .pdf
                String filePath = selectedFile.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".pdf")) {
                    filePath += ".pdf";
                }

                // Guardar el PDF en la ubicación seleccionada
                document.save(filePath);
                JOptionPane.showMessageDialog(null, "PDF generado exitosamente en:\n" + filePath);
            }

            document.close();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error al generar el PDF: " + ex.getMessage());
        }
    }

    /**
     * Método principal para iniciar la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        new EconoApp();
    }
}