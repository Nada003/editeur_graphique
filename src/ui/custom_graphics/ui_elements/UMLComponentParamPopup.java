package ui.custom_graphics.ui_elements;

import java.awt.*;
import java.awt.event.*;
import java.util.function.BiConsumer;
import javax.swing.*;
import javax.swing.border.*;

public class UMLComponentParamPopup extends JDialog {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(13, 110, 253);
    private static final Color DANGER_COLOR = new Color(220, 53, 69);
    private static final Color SUCCESS_COLOR = new Color(25, 135, 84);
    private static final Color BACKGROUND_COLOR = new Color(248, 249, 250);
    private static final Color TEXT_COLOR = new Color(33, 37, 41);
    private static final Color BORDER_COLOR = new Color(222, 226, 230);

    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 16);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);

    private JTextField nameField;
    private JSpinner attributeCountSpinner;
    private JSpinner functionCountSpinner;
    private JPanel attributesPanel;
    private JPanel functionsPanel;
    private final BiConsumer<String, String[]> callback;
    private Point mouseDownCompCoords;

    public UMLComponentParamPopup(BiConsumer<String, String[]> callback, String currentName, String[] att, String[] functions) {
        super((Frame) null, "Edit UML Component", true);
        this.callback = callback;

        setupWindow();
        setupContent(currentName, att, functions);

        makeDraggable(this);
        this.setVisible(true);
    }

    private void setupWindow() {
        this.setMinimumSize(new Dimension(600, 500));
        this.setLocationRelativeTo(null);
        this.setResizable(true);
        this.setUndecorated(true);
        this.setLayout(new BorderLayout());
        this.setBackground(BACKGROUND_COLOR);
        this.getRootPane().setBorder(BorderFactory.createCompoundBorder(
            new ShadowBorder(),
            BorderFactory.createLineBorder(BORDER_COLOR)
        ));
    }

    private void setupContent(String currentName, String[] attributes, String[] methods) {
        this.add(createTitleBar(), BorderLayout.NORTH);
        this.add(createMainContent(currentName, attributes, methods), BorderLayout.CENTER);
        this.add(createButtonPanel(), BorderLayout.SOUTH);
    }

    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(PRIMARY_COLOR);
        titleBar.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

        JLabel titleLabel = new JLabel("Edit UML Component");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titleBar.add(titleLabel, BorderLayout.WEST);

        JButton closeButton = createCloseButton();
        titleBar.add(closeButton, BorderLayout.EAST);

        return titleBar;
    }

    private JButton createCloseButton() {
        JButton closeButton = new JButton("×");
        closeButton.setFont(new Font("Arial", Font.BOLD, 18));
        closeButton.setForeground(Color.WHITE);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorderPainted(false);
        closeButton.setFocusPainted(false);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setForeground(new Color(255, 200, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setForeground(Color.WHITE);
            }
        });

        closeButton.addActionListener(e -> this.dispose());
        return closeButton;
    }

    private JScrollPane createMainContent(String currentName, String[] attributes, String[] methods) {
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // ✅ Corrigé ici : assignation de nameField
        nameField = createStyledTextField(currentName);
        mainPanel.add(createFormGroup("Class Name", nameField));
        mainPanel.add(Box.createVerticalStrut(15));

        JPanel countsPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        countsPanel.setOpaque(false);

        attributeCountSpinner = createStyledSpinner(attributes != null ? attributes.length : 0);
        functionCountSpinner = createStyledSpinner(methods != null ? methods.length : 0);

        countsPanel.add(createFormGroup("Attributes Count", attributeCountSpinner));
        countsPanel.add(createFormGroup("Methods Count", functionCountSpinner));

        mainPanel.add(countsPanel);
        mainPanel.add(Box.createVerticalStrut(15));

        JButton generateButton = createStyledButton("Generate Fields", PRIMARY_COLOR);
        generateButton.addActionListener(e -> generateFields());
        mainPanel.add(generateButton);
        mainPanel.add(Box.createVerticalStrut(20));

        attributesPanel = new JPanel();
        functionsPanel = new JPanel();
        setupFieldsPanel(attributesPanel, "Attributes");
        setupFieldsPanel(functionsPanel, "Methods");

        mainPanel.add(createScrollableSection("Attributes", attributesPanel));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createScrollableSection("Methods", functionsPanel));

        return new JScrollPane(mainPanel);
    }

    private JPanel createFormGroup(String label, JComponent component) {
        JPanel group = new JPanel();
        group.setLayout(new BoxLayout(group, BoxLayout.Y_AXIS));
        group.setOpaque(false);

        JLabel titleLabel = new JLabel(label);
        titleLabel.setFont(REGULAR_FONT);
        titleLabel.setForeground(TEXT_COLOR);

        group.add(titleLabel);
        group.add(Box.createVerticalStrut(5));
        group.add(component);

        return group;
    }

    private JTextField createStyledTextField(String text) {
        JTextField field = new JTextField(text);
        field.setFont(REGULAR_FONT);
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return field;
    }

    private JSpinner createStyledSpinner(int value) {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, 0, 100, 1));
        spinner.setFont(REGULAR_FONT);
        ((JSpinner.DefaultEditor) spinner.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
        spinner.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        return spinner;
    }

    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(REGULAR_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(color);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(color.darker());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(color);
            }
        });

        return button;
    }

    private void setupFieldsPanel(JPanel panel, String type) {
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    private JPanel createScrollableSection(String title, JPanel content) {
        JPanel section = new JPanel(new BorderLayout());
        section.setOpaque(false);

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(REGULAR_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        section.add(titleLabel, BorderLayout.NORTH);

        JScrollPane scrollPane = new JScrollPane(content);
        scrollPane.setPreferredSize(new Dimension(0, 150));
        scrollPane.setBorder(BorderFactory.createLineBorder(BORDER_COLOR));
        section.add(scrollPane, BorderLayout.CENTER);

        return section;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));

        JButton cancelButton = createStyledButton("Cancel", DANGER_COLOR);
        cancelButton.addActionListener(e -> dispose());

        JButton saveButton = createStyledButton("Save Changes", SUCCESS_COLOR);
        saveButton.addActionListener(e -> saveData());

        buttonPanel.add(cancelButton);
        buttonPanel.add(Box.createHorizontalStrut(10));
        buttonPanel.add(saveButton);

        return buttonPanel;
    }

    private void generateFields() {
        int attributeCount = (Integer) attributeCountSpinner.getValue();
        int functionCount = (Integer) functionCountSpinner.getValue();

        attributesPanel.removeAll();
        functionsPanel.removeAll();

        for (int i = 0; i < attributeCount; i++) {
            JTextField field = createStyledTextField("");
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
            attributesPanel.add(field);
            attributesPanel.add(Box.createVerticalStrut(5));
        }

        for (int i = 0; i < functionCount; i++) {
            JTextField field = createStyledTextField("");
            field.setMaximumSize(new Dimension(Integer.MAX_VALUE, field.getPreferredSize().height));
            functionsPanel.add(field);
            functionsPanel.add(Box.createVerticalStrut(5));
        }

        attributesPanel.revalidate();
        attributesPanel.repaint();
        functionsPanel.revalidate();
        functionsPanel.repaint();
    }

    private void saveData() {
        String className = nameField.getText().trim();
        if (className.isEmpty()) {
            showError("Class name cannot be empty");
            return;
        }

        String[] attributes = getTextFromFields(attributesPanel);
        String[] functions = getTextFromFields(functionsPanel);

        callback.accept(className, new String[]{String.join("\n", attributes), String.join("\n", functions)});
        this.dispose();
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private String[] getTextFromFields(JPanel panel) {
        return java.util.Arrays.stream(panel.getComponents())
                .filter(c -> c instanceof JTextField)
                .map(c -> ((JTextField) c).getText().trim())
                .filter(s -> !s.isEmpty())
                .toArray(String[]::new);
    }

    private void makeDraggable(Component component) {
        component.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                mouseDownCompCoords = e.getPoint();
            }
        });

        component.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point currCoords = e.getLocationOnScreen();
                setLocation(currCoords.x - mouseDownCompCoords.x,
                        currCoords.y - mouseDownCompCoords.y);
            }
        });
    }

    private static class ShadowBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            for (int i = 0; i < 4; i++) {
                g2.setColor(new Color(0, 0, 0, 20 - i * 4));
                g2.drawRoundRect(x + i, y + i, width - 2 * i - 1, height - 2 * i - 1, 10, 10);
            }

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(4, 4, 4, 4);
        }
    }
}
