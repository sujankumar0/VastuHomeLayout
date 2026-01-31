package app;

import javax.swing.*;
import java.awt.*;
import java.util.*;

public class HomeLayoutRecommenderImproved extends JFrame {

    private JPanel inputPanel, resultsPanel, visualizationPanel;
    private JTextField plotLengthField, plotWidthField;
    private JSpinner bedroomSpinner;
    private JCheckBox masterBathroomCheck;
    private JComboBox<String> styleCombo;
    private JButton recommendButton;

    private ArrayList<Layout> allLayouts = new ArrayList<>();
    private ArrayList<Layout> recommendedLayouts = new ArrayList<>();

    private JList<String> resultsList;
    private LayoutCanvas canvas;
    private JTextArea messageArea;
    private JLabel areaLabel;

    private double currentPlotLength, currentPlotWidth;

    public HomeLayoutRecommenderImproved() {

        setTitle("Vastu Home Layout - Perfect Fit");
        setSize(1400, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        createInputPanel();
        createResultsPanel();
        createVisualizationPanel();

        add(inputPanel, BorderLayout.WEST);
        add(resultsPanel, BorderLayout.CENTER);
        add(visualizationPanel, BorderLayout.EAST);

        setVisible(true);
    }

    // ================= INPUT PANEL =================
    private void createInputPanel() {

        inputPanel = new JPanel();
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.Y_AXIS));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Plot Details"));
        inputPanel.setPreferredSize(new Dimension(300, 800));

        JPanel plotPanel = new JPanel(new GridLayout(2, 2));
        plotPanel.add(new JLabel("Length (ft):"));
        plotLengthField = new JTextField("30");
        plotPanel.add(plotLengthField);
        plotPanel.add(new JLabel("Width (ft):"));
        plotWidthField = new JTextField("20");
        plotPanel.add(plotWidthField);
        inputPanel.add(plotPanel);

        areaLabel = new JLabel("Area: 600 sqft");
        areaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(areaLabel);

        plotLengthField.getDocument().addDocumentListener(new SimpleDocListener(this::updateArea));
        plotWidthField.getDocument().addDocumentListener(new SimpleDocListener(this::updateArea));

        inputPanel.add(Box.createVerticalStrut(10));

        inputPanel.add(new JLabel("Bedrooms (BHK):"));
        bedroomSpinner = new JSpinner(new SpinnerNumberModel(2, 1, 4, 1));
        inputPanel.add(bedroomSpinner);

        masterBathroomCheck = new JCheckBox("Master Bathroom");
        inputPanel.add(masterBathroomCheck);

        styleCombo = new JComboBox<>(new String[]{"Vastu", "Modern"});
        inputPanel.add(styleCombo);

        inputPanel.add(Box.createVerticalStrut(20));

        recommendButton = new JButton("Generate Layouts");
        recommendButton.setAlignmentX(CENTER_ALIGNMENT);
        recommendButton.addActionListener(e -> generateRecommendations());
        inputPanel.add(recommendButton);

        inputPanel.add(Box.createVerticalStrut(10));

        messageArea = new JTextArea(8, 25);
        messageArea.setEditable(false);
        messageArea.setBackground(new Color(240, 240, 240));
        inputPanel.add(new JScrollPane(messageArea));

        updateArea();
    }

    // ================= RESULTS PANEL =================
    private void createResultsPanel() {

        resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Recommended Layouts"));

        resultsList = new JList<>();
        resultsList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !recommendedLayouts.isEmpty()) {
                int index = resultsList.getSelectedIndex();
                if (index >= 0) {
                    canvas.setCurrentLayoutWithDimensions(
                            recommendedLayouts.get(index),
                            currentPlotLength,
                            currentPlotWidth
                    );
                }
            }
        });

        resultsPanel.add(new JScrollPane(resultsList));
    }

    // ================= VISUALIZATION =================
    private void createVisualizationPanel() {

        visualizationPanel = new JPanel(new BorderLayout());
        visualizationPanel.setBorder(BorderFactory.createTitledBorder("Floor Plan"));
        visualizationPanel.setPreferredSize(new Dimension(650, 800));

        canvas = new LayoutCanvas();
        canvas.setPreferredSize(new Dimension(600, 600));
        visualizationPanel.add(canvas);
    }

    // ================= MAIN LOGIC =================
    private void generateRecommendations() {

        try {
            currentPlotLength = Double.parseDouble(plotLengthField.getText().trim());
            currentPlotWidth = Double.parseDouble(plotWidthField.getText().trim());

            int bedrooms = (Integer) bedroomSpinner.getValue();
            boolean needMasterBath = masterBathroomCheck.isSelected();
            String style = (String) styleCombo.getSelectedItem();

            // 🔥 CRITICAL FIX — generate layouts NOW
            initializeLayouts(bedrooms);

            recommendedLayouts.clear();
            for (Layout layout : allLayouts) {
                if (layout.bedrooms == bedrooms) {
                    recommendedLayouts.add(layout);
                }
            }

            DefaultListModel<String> model = new DefaultListModel<>();
            for (int i = 0; i < recommendedLayouts.size(); i++) {
                Layout l = recommendedLayouts.get(i);
                model.addElement((i + 1) + ". " + l.name);
            }

            resultsList.setModel(model);

            if (!recommendedLayouts.isEmpty()) {
                resultsList.setSelectedIndex(0);
                canvas.setCurrentLayoutWithDimensions(
                        recommendedLayouts.get(0),
                        currentPlotLength,
                        currentPlotWidth
                );
                messageArea.setText("Layout generated successfully.");
            } else {
                messageArea.setText("No matching layouts found.");
            }

        } catch (Exception ex) {
            messageArea.setText("Error: " + ex.getMessage());
        }
    }

    private void updateArea() {
        try {
            double l = Double.parseDouble(plotLengthField.getText().trim());
            double w = Double.parseDouble(plotWidthField.getText().trim());
            areaLabel.setText("Area: " + (int) (l * w) + " sqft");
        } catch (Exception e) {
            areaLabel.setText("Area: --");
        }
    }

    // ================= LAYOUT GENERATION =================
    private void initializeLayouts(int bhk) {

        allLayouts.clear();

        Layout layout = new Layout(
                bhk + "BHK Dynamic Fit",
                "Vastu",
                1500,
                bhk,
                bhk == 3 ? 2 : 1
        );

        final int TOTAL_W = 100;

        if (bhk == 2) {
            // ================= 2BHK FULL-FIT =================

            int y = 0;

            // Living (big)
            layout.addRoom(new Room("Living", 0, y, 100, 35, "Living"));
            y += 35;

            // Kitchen + Dining + Bath
            layout.addRoom(new Room("Kitchen", 0, y, 20, 25, "Kitchen"));
            layout.addRoom(new Room("Dining", 20, y, 55, 25, "Dining"));
            layout.addRoom(new Room("Bath", 75, y, 25, 25, "Bathroom"));
            y += 25;

            // Bedrooms (fill remaining space)
            layout.addRoom(new Room("Bedroom 1", 0, y, 50, 40, "Bedroom"));
            layout.addRoom(new Room("Bedroom 2", 50, y, 50, 40, "Bedroom"));
        }
        else {
            // ================= 3BHK FULL-FIT =================

            int y = 0;

            // Living (largest)
            layout.addRoom(new Room("Living", 0, y, 100, 30, "Living"));
            y += 30;

            // Kitchen + Dining + Common Bath
            layout.addRoom(new Room("Kitchen", 0, y, 20, 20, "Kitchen"));
            layout.addRoom(new Room("Dining", 20, y, 55, 20, "Dining"));
            layout.addRoom(new Room("Bath", 75, y, 25, 20, "Bathroom"));
            y += 20;

         // Bedrooms row with passage
            layout.addRoom(new Room("Bedroom 1", 0, y, 45, 25, "Bedroom"));
            layout.addRoom(new Room("Passage", 45, y, 10, 25, "Passage"));
            layout.addRoom(new Room("Bedroom 2", 55, y, 45, 25, "Bedroom"));
            y += 25;


            // Master bedroom with attached bath
            layout.addRoom(new Room("Master Bedroom", 0, y, 70, 25, "Bedroom"));
            layout.addRoom(new Room("Attached Bath", 70, y, 30, 25, "Bathroom"));
        }

        allLayouts.add(layout);
    }

    // ================= HELPER =================
    private static class SimpleDocListener implements javax.swing.event.DocumentListener {
        Runnable r;
        SimpleDocListener(Runnable r) { this.r = r; }
        public void insertUpdate(javax.swing.event.DocumentEvent e) { r.run(); }
        public void removeUpdate(javax.swing.event.DocumentEvent e) { r.run(); }
        public void changedUpdate(javax.swing.event.DocumentEvent e) { r.run(); }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HomeLayoutRecommenderImproved::new);
    }
}
