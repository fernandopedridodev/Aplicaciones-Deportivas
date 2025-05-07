/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package zaidapp;

/**
 *
 * @author fernando.pedridomarino
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList; // Específico de java.util
import java.util.HashMap;
import java.util.List; // Específico de java.util
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.HashSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class CalorieMenuApp {

    private static final Map<String, Integer> INGREDIENTS = new HashMap<>() {{
        put("Arroz", 130);
        put("Pollo", 165);
        put("Brócoli", 34);
        put("Huevos", 155);
        put("Pasta", 131);
        put("Aguacate", 160);
        put("Carne picada", 250);
        put("Lentejas", 116);
        put("Queso", 402);
        put("Pan integral", 247);
        put("Manzana", 52);
        put("Banana", 89);
        put("Zanahoria", 41);
        put("Tomate", 18);
        put("Aceite de oliva", 884);
        put("Leche entera", 61);
        put("Almendras", 579);
        put("Espinacas", 23);
        put("Salmón", 206);
        put("Atún", 132);
        put("Pepino", 16);
        put("Lácteo descremado", 50);
        put("Pechuga de pavo", 135);
        put("Judías verdes", 31);
        put("Guisantes", 81);
        put("Pimiento rojo", 31);
        put("Patatas", 77);
        put("Calabaza", 26);
        put("Champiñones", 22);
        put("Albahaca", 22);
        put("Nueces", 654);
        put("Hummus", 166);
        put("Yogur natural", 59);
        put("Granola", 471);
        put("Fresas", 32);
        put("Arándanos", 57);
        put("Uvas", 69);
        put("Peras", 57);
        put("Kiwi", 61);
        put("Piña", 50);
        put("Mango", 60);
        put("Cebolla", 40);
        put("Acelga", 19);
        put("Lima", 30);
        put("Coco", 354);
        put("Manteca", 897);
        put("Aceite de coco", 862);
        put("Tocino", 541);
        put("Pechuga de pollo sin piel", 165);
        put("Chía", 486);
        put("Semillas de calabaza", 446);
        put("Semillas de girasol", 584);
    }};
    
    private static final Map<LocalDate, Map<String, Integer>> mealCalendar = new HashMap<>();
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new CalorieMenuApp().createAndShowGUI());
    }

    private void createAndShowGUI() {
        JFrame frame = new JFrame("Calculadora de Calorías");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);

        JTabbedPane tabbedPane = new JTabbedPane();

        // Pestaña 1: Calcular calorías de una comida
        JPanel inputPanel = new JPanel(new GridLayout(0, 2));
        Map<String, JTextField> quantityFields = new HashMap<>();
        for (String ingredient : INGREDIENTS.keySet()) {
            inputPanel.add(new JLabel(ingredient + " (g):"));
            JTextField field = new JTextField("0");
            inputPanel.add(field);
            quantityFields.put(ingredient, field);
        }

        JButton calcButton = new JButton("Calcular Calorías Totales");
        JLabel resultLabel = new JLabel("Total: 0 kcal");
        calcButton.addActionListener(e -> {
            double total = 0;
            for (String ingredient : INGREDIENTS.keySet()) {
                try {
                    double grams = Double.parseDouble(quantityFields.get(ingredient).getText());
                    total += grams * INGREDIENTS.get(ingredient) / 100.0;
                } catch (NumberFormatException ex) {
                    // Ignorar campos mal escritos
                }
            }
            resultLabel.setText(String.format("Total: %.2f kcal", total));
        });

        JPanel calcPanel = new JPanel(new BorderLayout());
        calcPanel.add(new JScrollPane(inputPanel), BorderLayout.CENTER);
        JPanel bottom = new JPanel();
        bottom.add(calcButton);
        bottom.add(resultLabel);
        calcPanel.add(bottom, BorderLayout.SOUTH);

        // Pestaña 2: Asignar ingredientes a calendario
        JPanel calendarPanel = new JPanel(new BorderLayout());
        JPanel calendarTopPanel = new JPanel();
        JTextField dateInput = new JTextField(10);
        JButton addToCalendarButton = new JButton("Añadir a calendario");
        JTextArea calendarArea = new JTextArea(10, 40);
        calendarArea.setEditable(false);

        addToCalendarButton.addActionListener(e -> {
            try {
                LocalDate date = LocalDate.parse(dateInput.getText(), dateFormatter);
                StringBuilder mealPlan = new StringBuilder();

                for (String meal : List.of("Desayuno", "Comida", "Cena", "Merienda")) { // Cambiado a List.of
                    mealPlan.append(meal).append(":\n");
                    int calorieLimit = 400; // Default calorie limit per meal
                    mealPlan.append("Limite de calorías: ").append(calorieLimit).append("\n");

                    // Seleccionar ingredientes sin repetir
                    List<String> selectedIngredients = new ArrayList<>();
                    Set<String> usedIngredients = new HashSet<>();
                    Random random = new Random();

                    while (selectedIngredients.size() < 5) { // max 5 ingredientes por comida
                        List<String> availableIngredients = new ArrayList<>(INGREDIENTS.keySet());
                        availableIngredients.removeAll(usedIngredients);
                        String randomIngredient = availableIngredients.get(random.nextInt(availableIngredients.size()));

                        if (!usedIngredients.contains(randomIngredient)) {
                            selectedIngredients.add(randomIngredient);
                            usedIngredients.add(randomIngredient);
                        }
                    }
                    for (String ingredient : selectedIngredients) {
                        mealPlan.append(ingredient).append(" - ").append(INGREDIENTS.get(ingredient)).append(" kcal/100g\n");
                    }
                    mealPlan.append("\n");
                }
                calendarArea.setText(mealPlan.toString());
                mealCalendar.put(date, new HashMap<>());
            } catch (Exception ex) {
                calendarArea.setText("Error: Formato de fecha incorrecto. Use dd-MM-yyyy.");
            }
        });

        calendarTopPanel.add(new JLabel("Fecha (dd-MM-yyyy):"));
        calendarTopPanel.add(dateInput);
        calendarTopPanel.add(addToCalendarButton);
        calendarPanel.add(calendarTopPanel, BorderLayout.NORTH);
        calendarPanel.add(new JScrollPane(calendarArea), BorderLayout.CENTER);

        // Pestaña 3: Buscar combinaciones según calorías
        JPanel suggestPanel = new JPanel(new BorderLayout());
        JTextField calorieGoal = new JTextField("500");
        JButton suggestButton = new JButton("Sugerir ingredientes");
        JTextArea suggestionArea = new JTextArea();
        suggestionArea.setEditable(false);

        suggestButton.addActionListener(e -> {
            try {
                int goal = Integer.parseInt(calorieGoal.getText());
                List<String> suggestions = getSuggestions(goal);
                suggestionArea.setText(String.join("\n", suggestions));
            } catch (NumberFormatException ex) {
                suggestionArea.setText("Número inválido.");
            }
        });

        JPanel topSuggest = new JPanel();
        topSuggest.add(new JLabel("Calorías objetivo: "));
        topSuggest.add(calorieGoal);
        topSuggest.add(suggestButton);

        suggestPanel.add(topSuggest, BorderLayout.NORTH);
        suggestPanel.add(new JScrollPane(suggestionArea), BorderLayout.CENTER);

        tabbedPane.add("Calcular calorías", calcPanel);
        tabbedPane.add("Asignar a calendario", calendarPanel);
        tabbedPane.add("Sugerencias por calorías", suggestPanel);

        frame.add(tabbedPane);
        frame.setVisible(true);
    }

    private List<String> getSuggestions(int goal) {
        List<String> result = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : INGREDIENTS.entrySet()) {
            if (entry.getValue() <= goal) {
                result.add(entry.getKey() + " - " + entry.getValue() + " kcal / 100g");
            }
        }
        return result;
    }
}
