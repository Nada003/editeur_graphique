package ui.main_app.main_topmenu;

import ui.custom_graphics.uml_components.text_and_comments.CommentRender;
import ui.main_app.history.UserAction;
import ui.main_app.main_board.MainBoard;
import utils.CommentClickedHandel;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class TopToolBar extends JToolBar {
    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;
    private final MainBoard board;
    private boolean bool = false;
    private Color currentColor = Color.BLACK;
    private JButton toggleGridButton;

    private final MouseListener textModeMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTextField textField = new JTextField();
            textField.setBounds(e.getX(), e.getY(), 100, 30);
            textField.setFont(new Font("Arial", Font.PLAIN, 14));

            textField.addActionListener(ev -> textField.transferFocus());

            textField.addFocusListener(new FocusAdapter() {
                @Override
                public void focusLost(FocusEvent f) {
                    CommentRender label = new CommentRender(textField.getText());
                    label.setClickListener(new CommentClickedHandel(board, label));
                    label.setPositionX(e.getX());
                    label.setPositionY(e.getY());
                    mainFlow.addElement(new UserAction("ajouter text \"" + textField.getText() + "\"", label));

                    board.components.addElement(label);
                    board.remove(textField);
                    board.repaint();

                    // Désactive automatiquement le mode texte après ajout
                    disactivateTextMode();
                    bool = false;
                }
            });

            board.add(textField);
            textField.requestFocus();
            board.repaint();
        }
    };

    public TopToolBar(MainBoard board, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow) {
        super(JToolBar.HORIZONTAL);
        this.board = board;
        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

        setFloatable(false);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

        add(createUndoRedoGroup());
        add(createSeparator());
        add(createFontGroup());
        add(createSeparator());
        add(createStyleGroup());
        add(createSeparator());
        add(createGridAndTextGroup());
        add(Box.createHorizontalGlue()); // pousse ce qui suit à droite
        add(createGridButtonGroup());   // bouton "G" à droite
    }

    private JComboBox<String> createFlatComboBox(String[] items, Dimension size) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(size);
        comboBox.setBackground(Color.WHITE);
        comboBox.setForeground(Color.BLACK);
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 13));
        comboBox.setFocusable(false);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                          boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                c.setFont(new Font("SansSerif", Font.PLAIN, 13));
                return c;
            }
        });

        return comboBox;
    }

    private JPanel createUndoRedoGroup() {
        JPanel panel = createGroupPanel();
        panel.add(createStyledButton("↩", e -> undoAction()));
        panel.add(createStyledButton("↪", e -> redoAction()));
        return panel;
    }

    private JPanel createFontGroup() {
        JPanel panel = createGroupPanel();

        String[] fontSizes = {"8pt", "10pt", "12pt", "14pt", "16pt"};
        String[] fonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();

        JComboBox<String> sizeBox = createFlatComboBox(fontSizes, new Dimension(60, 28));
        JComboBox<String> fontBox = createFlatComboBox(fonts, new Dimension(140, 28));

        JButton boldBtn = createStyledButton("𝐁", e -> {
            CommentRender selected = board.getSelectedComment();
            if (selected != null) selected.toggleBold();
        });

        JButton italicBtn = createStyledButton("𝐼", e -> {
            CommentRender selected = board.getSelectedComment();
            if (selected != null) selected.toggleItalic();
        });

        JButton underlineBtn = createStyledButton("𝐔", e -> {
            CommentRender selected = board.getSelectedComment();
            if (selected != null) selected.toggleUnderline();
        });

        sizeBox.addActionListener(e -> {
            CommentRender selected = board.getSelectedComment();
            if (selected != null)
                selected.setFontSize(Integer.parseInt(((String) sizeBox.getSelectedItem()).replace("pt", "")));
        });

        fontBox.addActionListener(e -> {
            CommentRender selected = board.getSelectedComment();
            if (selected != null)
                selected.setCurrentFont((String) fontBox.getSelectedItem());
        });

        panel.add(sizeBox);
        panel.add(fontBox);
        panel.add(boldBtn);
        panel.add(italicBtn);
        panel.add(underlineBtn);

        return panel;
    }

    private JPanel createStyleGroup() {
        JPanel panel = createGroupPanel();

        JButton colorBtn = new JButton("🎨");
        colorBtn.setFont(new Font("SansSerif", Font.BOLD, 16));
        colorBtn.setForeground(currentColor);
        colorBtn.setBackground(Color.WHITE);
        colorBtn.setBorder(BorderFactory.createEmptyBorder());
        colorBtn.setFocusPainted(false);
        colorBtn.setContentAreaFilled(false);
        colorBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        colorBtn.setPreferredSize(new Dimension(40, 28));

        colorBtn.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
                colorBtn.setForeground(currentColor);
                CommentRender selected = board.getSelectedComment();
                if (selected != null) {
                    selected.setTextColor(currentColor);
                }
            }
        });

        panel.add(colorBtn);
        return panel;
    }

    private JPanel createGridAndTextGroup() {
        JPanel panel = createGroupPanel();

        JButton textBtn = createStyledButton("T", e -> {
            if (!bool) {
                activateTextMode();
            } else {
                disactivateTextMode();
            }
            bool = !bool;
        });

        panel.add(textBtn);
        return panel;
    }

    private JPanel createGridButtonGroup() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 4, 4));
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));

        toggleGridButton = createStyledButton("𝐆", e -> board.toggleGrid());
        panel.add(toggleGridButton);
        return panel;
    }

    private JPanel createGroupPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 4, 4));
        panel.setOpaque(true);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(3, 3, 3, 3));
        return panel;
    }

    private JButton createStyledButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(true);
        button.setOpaque(true);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(36, 28));
        button.setBorder(BorderFactory.createEmptyBorder());

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(230, 240, 255));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }
        });

        button.addActionListener(action);
        return button;
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(1, 32));
        separator.setForeground(new Color(180, 180, 180));
        return separator;
    }

    private void activateTextMode() {
        board.addMouseListener(textModeMouseListener);
    }

    private void disactivateTextMode() {
        board.removeMouseListener(textModeMouseListener);
    }

    private void undoAction() {
        if (mainFlow.getList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune action à annuler.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        var e = mainFlow.getList().getLast();
        mainFlow.removeElement(e);
        undoFlow.addElement(e);
    }

    private void redoAction() {
        if (undoFlow.getList().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Aucune action à rétablir.", "Information", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        var e = undoFlow.getList().getLast();
        undoFlow.removeElement(e);
        mainFlow.addElement(e);
    }
}
