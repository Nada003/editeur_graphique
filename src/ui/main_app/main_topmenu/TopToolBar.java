package ui.main_app.main_topmenu;

import ui.main_app.history.UserAction;
import ui.main_app.main_board.MainBoard;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;

public class TopToolBar extends JToolBar {
    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;
    private Color currentColor = Color.BLACK;
    private JButton toggleGridButton;

    public TopToolBar(MainBoard board, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow){
        super(JToolBar.HORIZONTAL);
        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;
        this.setBackground(Color.WHITE);

        JButton undoButton = new JButton("↩");
        undoButton.addActionListener(e -> undoAction());
        JButton redoButton = new JButton("↪");
        redoButton.addActionListener(e -> redoAction());
        JButton boldButton = new JButton("𝐁");
        JButton italicButton = new JButton("𝘐");
        JButton underlineButton = new JButton("U̲");
        JButton colorButton = new JButton("🎨");
        toggleGridButton = new JButton("Grille");
        JButton textButton = new JButton("📝 Texte"); // Bouton pour ajouter du texte

        String[] fontSizes = {"8pt", "10pt", "12pt", "14pt", "16pt"};
        JComboBox<String> fontSizeBox = new JComboBox<>(fontSizes);

        JSlider thicknessSlider = new JSlider(1, 10, 2);

        this.add(undoButton);
        this.add(redoButton);
        this.addSeparator();
        this.add(fontSizeBox);
        this.add(boldButton);
        this.add(italicButton);
        this.add(underlineButton);
        this.addSeparator();
        this.add(colorButton);
        this.add(thicknessSlider);
        this.add(toggleGridButton);
        this.addSeparator();
        this.add(textButton);

        // Action pour le choix des couleurs
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
            }
        });

        // Action pour afficher/masquer la grille
        toggleGridButton.addActionListener(e -> {
            board.toggle();
        });

        // Action pour ajouter du texte (Tu peux modifier cette partie)
        textButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Ajout d'un texte (fonction à implémenter) !");
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
