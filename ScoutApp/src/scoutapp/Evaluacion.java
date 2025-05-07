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

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Evaluacion extends JFrame {

    private Jugador jugador; // El jugador evaluado
    private JTable accionesTable; // Tabla de acciones técnicas
    private JTextArea observacionesArea; // Área de texto para observaciones generales
    private static final List<AccionTecnica> accionesTecnicas = new ArrayList<>(); // Lista de acciones técnicas

    // Bloque estático para inicializar las acciones técnicas
    static {
        accionesTecnicas.add(new AccionTecnica(1, "Control orientado", "Ofensiva", "Control del balón orientado hacia el objetivo."));
        accionesTecnicas.add(new AccionTecnica(2, "Control en carrera", "Ofensiva", "Control del balón mientras se corre."));
        accionesTecnicas.add(new AccionTecnica(3, "Control bajo presión", "Ofensiva", "Control del balón en situaciones de presión del rival."));

        accionesTecnicas.add(new AccionTecnica(4, "Conducción progresiva", "Ofensiva", "Avance con el balón hacia el objetivo."));
        accionesTecnicas.add(new AccionTecnica(5, "Conducción lateral o de retención", "Ofensiva", "Movimiento lateral con el balón para retenerlo."));
        accionesTecnicas.add(new AccionTecnica(6, "Conducción en transición", "Ofensiva", "Conducción rápida durante la transición."));

        accionesTecnicas.add(new AccionTecnica(7, "Pase corto", "Ofensiva", "Pase preciso a corta distancia."));
        accionesTecnicas.add(new AccionTecnica(8, "Pase largo", "Ofensiva", "Pase a larga distancia."));
        accionesTecnicas.add(new AccionTecnica(9, "Pase entre líneas", "Ofensiva", "Pase que atraviesa líneas defensivas."));
        accionesTecnicas.add(new AccionTecnica(10, "Pase en profundidad", "Ofensiva", "Pase hacia adelante para habilitar al atacante."));
        accionesTecnicas.add(new AccionTecnica(11, "Pase de ruptura", "Ofensiva", "Pase que rompe la defensa rival."));
        accionesTecnicas.add(new AccionTecnica(12, "Paredes (1-2)", "Ofensiva", "Ejecución de pases rápidos 1-2 entre jugadores."));
        accionesTecnicas.add(new AccionTecnica(13, "Centros y centros al área", "Ofensiva", "Centros dirigidos a la zona de ataque."));

        accionesTecnicas.add(new AccionTecnica(14, "Tiros a puerta", "Ofensiva", "Intentos de marcar gol."));
        accionesTecnicas.add(new AccionTecnica(15, "Goles", "Ofensiva", "Goles marcados."));
        accionesTecnicas.add(new AccionTecnica(16, "Tiros bloqueados", "Ofensiva", "Tiros bloqueados por defensores."));
        accionesTecnicas.add(new AccionTecnica(17, "Tiros desde fuera del área", "Ofensiva", "Intentos de gol desde larga distancia."));
        accionesTecnicas.add(new AccionTecnica(18, "Remates de cabeza", "Ofensiva", "Remates realizados con la cabeza."));
        accionesTecnicas.add(new AccionTecnica(19, "Remates en el área pequeña", "Ofensiva", "Remates realizados desde el área chica."));

        accionesTecnicas.add(new AccionTecnica(20, "Intentos de regate", "Ofensiva", "Intentos de superar al rival en 1v1."));
        accionesTecnicas.add(new AccionTecnica(21, "Regates exitosos", "Ofensiva", "Regates completados con éxito."));
        accionesTecnicas.add(new AccionTecnica(22, "Duelos 1vs1 ofensivos ganados", "Ofensiva", "Duelos ofensivos ganados en 1v1."));

        accionesTecnicas.add(new AccionTecnica(23, "Recepción al pie", "Ofensiva", "Recepción del balón directamente al pie."));
        accionesTecnicas.add(new AccionTecnica(24, "Recepción al espacio", "Ofensiva", "Recepción del balón en un espacio libre."));
        accionesTecnicas.add(new AccionTecnica(25, "Recepción bajo presión", "Ofensiva", "Recepción del balón mientras se enfrenta presión del rival."));

        // Acciones técnicas defensivas
        accionesTecnicas.add(new AccionTecnica(26, "Entradas exitosas", "Defensiva", "Entradas que recuperan el balón con éxito."));
        accionesTecnicas.add(new AccionTecnica(27, "Entradas fallidas", "Defensiva", "Entradas sin éxito que no recuperan el balón."));
        accionesTecnicas.add(new AccionTecnica(28, "Duelos 1vs1 defensivos ganados", "Defensiva", "Duelos defensivos ganados en 1v1."));
        accionesTecnicas.add(new AccionTecnica(29, "Duelos aéreos ganados", "Defensiva", "Duelos aéreos ganados."));
        accionesTecnicas.add(new AccionTecnica(30, "Duelos perdidos", "Defensiva", "Duelos perdidos en situaciones defensivas."));
        accionesTecnicas.add(new AccionTecnica(31, "Interceptaciones de pase", "Defensiva", "Interceptaciones exitosas de pases."));
        accionesTecnicas.add(new AccionTecnica(32, "Despejes en área propia", "Defensiva", "Despejes realizados en el área propia."));
        accionesTecnicas.add(new AccionTecnica(33, "Despejes tras centro", "Defensiva", "Despejes realizados tras centros al área."));
        accionesTecnicas.add(new AccionTecnica(34, "Acciones de presión alta, media o baja", "Defensiva", "Presión defensiva en diferentes zonas."));
        accionesTecnicas.add(new AccionTecnica(35, "Presiones exitosas", "Defensiva", "Acciones de presión que resultaron en recuperación del balón."));
        accionesTecnicas.add(new AccionTecnica(36, "Coberturas a compañeros", "Defensiva", "Acciones defensivas para cubrir a un compañero."));
        accionesTecnicas.add(new AccionTecnica(37, "Ayudas defensivas", "Defensiva", "Acciones de apoyo defensivo."));

        // Acciones técnicas del portero
        accionesTecnicas.add(new AccionTecnica(38, "Paradas (intervenciones)", "Portero", "Intervenciones exitosas del portero."));
        accionesTecnicas.add(new AccionTecnica(39, "Salidas por alto", "Portero", "Salidas del portero para interceptar balones altos."));
        accionesTecnicas.add(new AccionTecnica(40, "Salidas en 1vs1", "Portero", "Intervenciones del portero en 1v1."));
        accionesTecnicas.add(new AccionTecnica(41, "Blocajes o rechaces", "Portero", "Blocajes o rechazos del balón."));
        accionesTecnicas.add(new AccionTecnica(42, "Juego con los pies (pases cortos/largos)", "Portero", "Pases realizados por el portero."));
        accionesTecnicas.add(new AccionTecnica(43, "Recolocaciones tras despeje", "Portero", "Recolocación del portero tras un despeje."));
        accionesTecnicas.add(new AccionTecnica(44, "Penaltis detenidos", "Portero", "Penaltis atajados por el portero."));

        // Errores técnicos
        accionesTecnicas.add(new AccionTecnica(45, "Pérdidas de balón no forzadas", "Errores", "Errores en la posesión sin presión del rival."));
        accionesTecnicas.add(new AccionTecnica(46, "Malos controles", "Errores", "Errores en el control del balón."));
        accionesTecnicas.add(new AccionTecnica(47, "Fallos en el pase", "Errores", "Errores en la ejecución de pases."));
        accionesTecnicas.add(new AccionTecnica(48, "Fallos en la finalización", "Errores", "Errores en intentos de gol."));
        accionesTecnicas.add(new AccionTecnica(49, "Malas decisiones en salida de balón", "Errores", "Decisiones erróneas al iniciar una jugada."));
        accionesTecnicas.add(new AccionTecnica(50, "Fallos de marcaje", "Errores", "Errores en el marcaje defensivo."));

        // Acciones especiales
        accionesTecnicas.add(new AccionTecnica(51, "Saques de esquina lanzados/recibidos", "Especiales", "Acciones en saques de esquina."));
        accionesTecnicas.add(new AccionTecnica(52, "Faltas directas/indirectas", "Especiales", "Lanzamiento de faltas directas o indirectas."));
        accionesTecnicas.add(new AccionTecnica(53, "Penaltis", "Especiales", "Ejecución de penaltis."));
        accionesTecnicas.add(new AccionTecnica(54, "Cambios de orientación", "Especiales", "Pases largos para cambiar el punto de ataque."));
        accionesTecnicas.add(new AccionTecnica(55, "Pases de seguridad", "Especiales", "Pases realizados para mantener la posesión."));
        accionesTecnicas.add(new AccionTecnica(56, "Retención de balón en situaciones de ventaja", "Especiales", "Mantener el balón para asegurar la ventaja."));

        // Agregar más acciones según sea necesario...
    }

    public Evaluacion(Jugador jugador) {
        this.jugador = jugador;

        setTitle("Evaluación de Jugador: " + jugador.getNombre());
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configurar la tabla de acciones técnicas
        String[] columnNames = {"Acción Técnica", "Valoración"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        accionesTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(accionesTable);

        // Agregar acciones técnicas a la tabla
        for (AccionTecnica accion : accionesTecnicas) {
            tableModel.addRow(new Object[]{accion.getNombre(), 0}); // Valoración inicial: 0
        }

        // Área de observaciones generales
        JPanel observacionesPanel = new JPanel(new BorderLayout());
        observacionesPanel.setBorder(BorderFactory.createTitledBorder("Observaciones Generales"));
        observacionesArea = new JTextArea(5, 20);
        observacionesPanel.add(new JScrollPane(observacionesArea), BorderLayout.CENTER);

        // Panel de botones
        JPanel buttonPanel = new JPanel();
        JButton guardarButton = new JButton("Guardar Evaluación");
        JButton limpiarButton = new JButton("Limpiar");
        JButton exportarPDFButton = new JButton("Exportar a PDF");
        buttonPanel.add(guardarButton);
        buttonPanel.add(limpiarButton);
        buttonPanel.add(exportarPDFButton);

        // Acciones de los botones
        guardarButton.addActionListener(e -> guardarEvaluacion());
        limpiarButton.addActionListener(e -> limpiarEvaluacion());
        exportarPDFButton.addActionListener(e -> exportarEvaluacionPDF());

        // Agregar componentes a la ventana
        add(tableScrollPane, BorderLayout.CENTER);
        add(observacionesPanel, BorderLayout.SOUTH);
        add(buttonPanel, BorderLayout.NORTH);

        setVisible(true);
    }

    private void guardarEvaluacion() {
        JOptionPane.showMessageDialog(this, "Evaluación guardada con éxito.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void limpiarEvaluacion() {
        DefaultTableModel tableModel = (DefaultTableModel) accionesTable.getModel();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tableModel.setValueAt(0, i, 1); // Reiniciar las valoraciones a 0
        }
        observacionesArea.setText(""); // Limpiar observaciones generales
        JOptionPane.showMessageDialog(this, "Evaluación limpiada.", "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void exportarEvaluacionPDF() {
        // Configurar el cuadro de diálogo para guardar el archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona la carpeta y el nombre del archivo");
        fileChooser.setSelectedFile(new File("evaluacion_jugador.pdf")); // Nombre de archivo predeterminado
        int result = fileChooser.showSaveDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String rutaPDF = selectedFile.getAbsolutePath();

            // Crear el PDF
            try (PDDocument document = new PDDocument()) {
                PDPage page = new PDPage();
                document.addPage(page);

                // Cargar la fuente personalizada
                File fontFile = new File("src/scoutapp/resources/fonts/Roboto-Italic.ttf");
                if (!fontFile.exists()) {
                    JOptionPane.showMessageDialog(this, "No se encontró el archivo de fuente: " + fontFile.getAbsolutePath(), "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                PDType0Font customFont = PDType0Font.load(document, fontFile);

                try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                    contentStream.setFont(customFont, 16);
                    contentStream.beginText();
                    contentStream.setLeading(20f);
                    contentStream.newLineAtOffset(50, 750);

                    // Título
                    contentStream.showText("Informe de Evaluación");
                    contentStream.newLine();
                    contentStream.newLine();

                    // Información del jugador
                    contentStream.setFont(customFont, 12);
                    contentStream.showText("Jugador: " + jugador.getNombre());
                    contentStream.newLine();
                    contentStream.showText("Posición: " + jugador.getPosicion());
                    contentStream.newLine();
                    contentStream.showText("Dorsal: " + jugador.getDorsal());
                    contentStream.newLine();
                    contentStream.showText("Edad: " + jugador.getEdad()); // Se asegura que la edad del jugador se extrae correctamente
                    contentStream.newLine();
                    contentStream.showText("Equipo: " + jugador.getEquipo());
                    contentStream.newLine();
                    contentStream.newLine();

                    // Acciones técnicas y valoraciones
                    contentStream.showText("Acciones Técnicas:");
                    contentStream.newLine();
                    DefaultTableModel tableModel = (DefaultTableModel) accionesTable.getModel();
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String accion = (String) tableModel.getValueAt(i, 0);
                        String valoracion = tableModel.getValueAt(i, 1).toString();
                        contentStream.showText("- " + accion + ": " + valoracion);
                        contentStream.newLine();
                    }
                    contentStream.newLine();

                    // Observaciones generales
                    contentStream.showText("Observaciones Generales:");
                    contentStream.newLine();
                    contentStream.showText(observacionesArea.getText());
                    contentStream.newLine();

                    contentStream.endText();
                }

                document.save(rutaPDF);
                JOptionPane.showMessageDialog(this, "Evaluación exportada a PDF con éxito en: " + rutaPDF, "Información", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error al exportar la evaluación a PDF: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
