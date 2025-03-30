package ui.custom_graphics.ui_elements;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.util.function.BiConsumer;

public class UMLComponentParamPopup extends JDialog {
    private JTextField nameField;
    private JTextArea attributesField;
    private JTextArea functionsField;
    private final BiConsumer<String, String[]> callback;

    public UMLComponentParamPopup(BiConsumer<String, String[]> callback, String currentName, String[] currentAttributes, String[] currentFunctions) {
        super((Frame) null, "Edit UML Component", true);
        this.callback = callback;

        this.setMinimumSize(new Dimension(500, 300));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());

        JPanel titleBar = createTitleBar();
        this.add(titleBar, BorderLayout.NORTH);
        this.add(getMainPopupView(currentName, currentAttributes, currentFunctions), BorderLayout.CENTER);

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

        enableDragging(this, titleBar);
        return titleBar;
    }

    private JPanel getMainPopupView(String currentName, String[] currentAttributes, String[] currentFunctions) {
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Color.WHITE);
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 10, 8, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        nameField = new JTextField(currentName, 20);
        attributesField = new JTextArea(String.join(",", currentAttributes), 3, 20);
        functionsField = new JTextArea(String.join(",", currentFunctions), 3, 20);

        attributesField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        functionsField.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        addLabeledComponent(main, "Class Name:", nameField, c, 0);
        addLabeledComponent(main, "Attributes:", new JScrollPane(attributesField), c, 1);
        addLabeledComponent(main, "Functions:", new JScrollPane(functionsField), c, 2);

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(new Color(0, 150, 0));
        saveButton.setForeground(Color.WHITE);
        saveButton.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));
        saveButton.addActionListener(e -> saveData());

        c.gridx = 1; c.gridy = 3;
        c.anchor = GridBagConstraints.EAST;
        main.add(saveButton, c);

        return main;
    }

    private void addLabeledComponent(JPanel panel, String labelText, Component component, GridBagConstraints c, int y) {
        c.gridx = 0; c.gridy = y;
        panel.add(new JLabel(labelText), c);
        c.gridx = 1;
        panel.add(component, c);
    }

    private void saveData() {
        String className = nameField.getText().trim();
        String attributes = attributesField.getText().trim();
        String functions = functionsField.getText().trim();

        if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Class name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        callback.accept(className, new String[]{attributes, functions});
        this.dispose();
    }

    private static void enableDragging(JDialog dialog, JPanel titleBar) {
        final Point[] mouseDownCompCoords = {null};

        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords[0] = e.getPoint();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                dialog.setLocation(currCoords.x - mouseDownCompCoords[0].x, currCoords.y - mouseDownCompCoords[0].y);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new UMLComponentParamPopup((name, data) -> {
            System.out.println("Saved Class: " + name);
            System.out.println("Attributes: " + data[0]);
            System.out.println("Functions: " + data[1]);
        }, "ExampleClass", new String[]{"att1", "att2"}, new String[]{"func1", "func2"}));
    }
}