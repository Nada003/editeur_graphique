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

        this.setMinimumSize(new Dimension(600, 300));
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());

        JPanel titleBar = new JPanel();
        titleBar.setBackground(Color.DARK_GRAY);
        titleBar.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton closeButton = new JButton("✖");
        closeButton.setFocusable(false);
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED);
        closeButton.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeButton.addActionListener(e -> this.dispose());
        titleBar.add(closeButton);

        enableDragging(this, titleBar);

        this.add(titleBar, BorderLayout.NORTH);
        this.add(getMainPopupView(currentName, currentAttributes, currentFunctions), BorderLayout.CENTER);
        this.setVisible(true);
    }

    private JPanel getMainPopupView(String currentName, String[] currentAttributes, String[] currentFunctions) {
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(Color.WHITE);

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 10, 5, 10);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1.0;

        // Class Name Field
        c.gridx = 0; c.gridy = 0;
        main.add(new JLabel("Class Name:"), c);

        nameField = new JTextField(20);
        nameField.setText(currentName);
        c.gridx = 1;
        main.add(nameField, c);

        // Attributes Field
        c.gridx = 0; c.gridy = 1;
        main.add(new JLabel("Attributes:"), c);

        attributesField = new JTextArea(3, 20);
        attributesField.setText(String.join(",", currentAttributes));
        attributesField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        c.gridx = 1;
        main.add(new JScrollPane(attributesField), c);

        // Functions Field
        c.gridx = 0; c.gridy = 2;
        main.add(new JLabel("Functions:"), c);

        functionsField = new JTextArea(3, 20);
        functionsField.setText(String.join(",", currentFunctions));
        functionsField.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        c.gridx = 1;
        main.add(new JScrollPane(functionsField), c);

        // Save Button
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

    private void saveData() {
        String className = nameField.getText().trim();
        String attributes = attributesField.getText().trim();
        String functions = functionsField.getText().trim();

        if (className.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Class name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


        String[] attArray = attributes.isEmpty() ? new String[]{} : attributes.split(",");
        String[] funcArray = functions.isEmpty() ? new String[]{} : functions.split(",");


        callback.accept(className, new String[]{String.join(",", attArray), String.join(",", funcArray)});

        // Close the dialog
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
