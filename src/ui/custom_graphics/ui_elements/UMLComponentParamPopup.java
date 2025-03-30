package ui.custom_graphics.ui_elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;

public class UMLComponentParamPopup extends JDialog {
    private JTextField nameField;
    private JTextField attributeCountField;
    private JTextField functionCountField;
    private JPanel attributesPanel;
    private JPanel functionsPanel;
    private final BiConsumer<String, String[]> callback;
    private int mouseX, mouseY;

    public UMLComponentParamPopup(BiConsumer<String, String[]> callback, String currentName, String[] att, String[] functions) {
        super((Frame) null, "Edit UML Component", true);
        this.callback = callback;

        this.setMinimumSize(new Dimension(500, 400));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());

        JPanel titleBar = createTitleBar();
        this.add(titleBar, BorderLayout.NORTH);
        this.add(getMainPopupView(currentName), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(50, 50, 50));
        titleBar.setPreferredSize(new Dimension(getWidth(), 30));

        JLabel titleLabel = new JLabel(" Edit UML Component ");
        titleLabel.setForeground(Color.WHITE);
        titleBar.add(titleLabel, BorderLayout.WEST);

        JButton closeButton = new JButton("✖");
        closeButton.setFocusable(false);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(new Color(200, 50, 50));
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeButton.addActionListener(e -> this.dispose());
        titleBar.add(closeButton, BorderLayout.EAST);

        titleBar.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX = e.getX();
                mouseY = e.getY();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = getLocation().x + e.getX() - mouseX;
                int y = getLocation().y + e.getY() - mouseY;
                setLocation(x, y);
            }
        });

        return titleBar;
    }

    private JPanel getMainPopupView(String currentName) {
        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setBackground(Color.WHITE);

        nameField = new JTextField(currentName, 20);
        attributeCountField = new JTextField(5);
        functionCountField = new JTextField(5);
        attributesPanel = new JPanel();
        functionsPanel = new JPanel();
        attributesPanel.setLayout(new GridLayout(0, 1));
        functionsPanel.setLayout(new GridLayout(0, 1));

        JScrollPane attributesScrollPane = new JScrollPane(attributesPanel);
        attributesScrollPane.setPreferredSize(new Dimension(450, 100));
        JScrollPane functionsScrollPane = new JScrollPane(functionsPanel);
        functionsScrollPane.setPreferredSize(new Dimension(450, 100));

        JButton generateFieldsButton = new JButton("Generate Fields");
        generateFieldsButton.addActionListener(e -> generateFields());

        main.add(new JLabel("Class Name:"));
        main.add(nameField);
        main.add(new JLabel("Number of Attributes:"));
        main.add(attributeCountField);
        main.add(new JLabel("Number of Functions:"));
        main.add(functionCountField);
        main.add(generateFieldsButton);
        main.add(new JLabel("Attributes:"));
        main.add(attributesScrollPane);
        main.add(new JLabel("Functions:"));
        main.add(functionsScrollPane);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 150, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> saveData());
        main.add(saveButton);

        return main;
    }

    private void generateFields() {
        try {
            int attributeCount = Integer.parseInt(attributeCountField.getText().trim());
            int functionCount = Integer.parseInt(functionCountField.getText().trim());

            attributesPanel.removeAll();
            functionsPanel.removeAll();

            for (int i = 0; i < attributeCount; i++) {
                attributesPanel.add(new JTextField(20));
            }
            for (int i = 0; i < functionCount; i++) {
                functionsPanel.add(new JTextField(20));
            }

            attributesPanel.revalidate();
            attributesPanel.repaint();
            functionsPanel.revalidate();
            functionsPanel.repaint();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveData() {
        String className = nameField.getText().trim();
        if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Class name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String[] attributes = getTextFromFields(attributesPanel);
        String[] functions = getTextFromFields(functionsPanel);

        callback.accept(className, new String[]{String.join("\n", attributes), String.join("\n", functions)});
        this.dispose();
    }

    private String[] getTextFromFields(JPanel panel) {
        return java.util.Arrays.stream(panel.getComponents())
                .filter(c -> c instanceof JTextField)
                .map(c -> ((JTextField) c).getText().trim())
                .toArray(String[]::new);
    }
}
