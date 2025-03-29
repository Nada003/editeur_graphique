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
    private Color currentColor = Color.BLACK;
    private JButton toggleGridButton;
    private MainBoard board;
    private boolean bool = false;
    private MouseListener textModeMouseListener = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JTextField textField = new JTextField();
            textField.setBounds(e.getX(), e.getY(), 100, 30);
            textField.setFont(new Font("Arial", Font.PLAIN, 14));

            textField.addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {

                }

                @Override
                public void focusLost(FocusEvent f) {
                    var label = new CommentRender(textField.getText());
                    label.setClickListener(new CommentClickedHandel(board,label));
                    label.setPositionX(e.getX());
                    label.setPositionY(e.getY());
                    board.components.addElement(label);
                    board.remove(textField);
                    board.revalidate();
                    board.repaint();
                }
            });
            board.add(textField);
            board.repaint();
            textField.requestFocus();


        }
    };


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
            CommentRender selecedComment =  board.getSelectedComment();
            if (selecedComment == null) return;

            String selectedSize = (String) fontSizeBox.getSelectedItem();
            assert selectedSize != null;
            int fontSize = Integer.parseInt(selectedSize.replace("pt", ""));
            selecedComment.setFontSize(fontSize);
            selecedComment.repaint();
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
        textButton.addActionListener(e -> {
            if (!bool) {
                activateTextMode();
            } else {
               disactivateTextMode();
            }
            bool = !bool;
        });

        // Styles : Gras, Italique, Souligné
        boldButton.addActionListener(e -> {
            CommentRender selecedComment =  board.getSelectedComment();
            if (selecedComment == null) return;
            selecedComment.toggleBold();
            selecedComment.repaint();
        });

        italicButton.addActionListener(e -> {
            CommentRender selecedComment =  board.getSelectedComment();
            if (selecedComment == null) return;
            selecedComment.toggleItalic();
            selecedComment.repaint();
        });

        underlineButton.addActionListener(e -> {
            CommentRender selecedComment =  board.getSelectedComment();
            if (selecedComment == null) return;
            selecedComment.toggleUnderline();
            selecedComment.repaint();
        });

        // Appliquer la police sélectionnée
        fontBox.addActionListener(e -> {
            CommentRender selecedComment =  board.getSelectedComment();
            if (selecedComment == null) return;
            String selectedFont = (String) fontBox.getSelectedItem();
            selecedComment.setCurrentFont(selectedFont);
        });
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
