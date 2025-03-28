package ui.main_app.main_topmenu;

import ui.main_app.history.UserAction;
import ui.main_app.main_board.MainBoard;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TopToolBar extends JToolBar {
    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;
    private Color currentColor = Color.BLACK;
    private JButton toggleGridButton;
    private MainBoard board;

    public TopToolBar(MainBoard board, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow) {
        super(JToolBar.HORIZONTAL);
        this.board = board; // Initialisation de la référence à MainBoard
        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;
        this.setBackground(Color.WHITE);

        // Création des boutons
        JButton undoButton = new JButton("↩");
        undoButton.addActionListener(e -> undoAction());

        JButton redoButton = new JButton("↪");
        redoButton.addActionListener(e -> redoAction());

        JButton boldButton = new JButton("𝐁");
        JButton italicButton = new JButton("𝘐");
        JButton underlineButton = new JButton("U̲");
        JButton colorButton = new JButton("🎨");
        toggleGridButton = new JButton("Grille");
        JButton textButton = new JButton("📝 Texte");

        // ComboBox pour la taille de police
        String[] fontSizes = {"8pt", "10pt", "12pt", "14pt", "16pt"};
        JComboBox<String> fontSizeBox = new JComboBox<>(fontSizes);

        // Récupération des polices système
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] fontNames = ge.getAvailableFontFamilyNames();
        JComboBox<String> fontBox = new JComboBox<>(fontNames);

        // Slider pour l'épaisseur
        JSlider thicknessSlider = new JSlider(1, 10, 2);

        // Ajout des composants à la barre d'outils
        this.add(undoButton);
        this.add(redoButton);
        this.addSeparator();
        this.add(fontSizeBox);
        this.add(fontBox);
        this.add(boldButton);
        this.add(italicButton);
        this.add(underlineButton);
        this.addSeparator();
        this.add(colorButton);
        this.add(thicknessSlider);
        this.add(toggleGridButton);
        this.addSeparator();
        this.add(textButton);

        fontSizeBox.addActionListener(e -> {
            String selectedSize = (String) fontSizeBox.getSelectedItem();
            int fontSize = Integer.parseInt(selectedSize.replace("pt", ""));
            board.setFontSize(fontSize);
            board.repaint();
        });
        // Action pour le choix des couleurs
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
            }
        });

        // Action pour afficher/masquer la grille
        toggleGridButton.addActionListener(e -> board.toggleGrid());

        // Action pour activer le mode texte
        textButton.addActionListener(e -> activateTextMode());

        // Styles : Gras, Italique, Souligné
        boldButton.addActionListener(e -> {
            board.toggleBold();
            board.repaint();
        });

        italicButton.addActionListener(e -> {
            board.toggleItalic();
            board.repaint();
        });

        underlineButton.addActionListener(e -> {
            board.toggleUnderline();
            board.repaint();
        });

        // Appliquer la police sélectionnée
        fontBox.addActionListener(e -> {
            String selectedFont = (String) fontBox.getSelectedItem();
            board.setCurrentFont(selectedFont);
        });
    }

    private void activateTextMode() {
        board.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTextField textField = new JTextField();
                textField.setBounds(e.getX(), e.getY(), 100, 30);
                textField.setFont(new Font(board.getCurrentFont(), Font.PLAIN, 14));

                board.setLayout(null); // Important pour le positionnement
                board.add(textField);
                board.repaint();
                textField.requestFocus();

                textField.addActionListener(ev -> {
                    board.addText(textField.getText(), e.getX(), e.getY());
                    board.remove(textField);
                    board.repaint();
                });
            }
        });
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
