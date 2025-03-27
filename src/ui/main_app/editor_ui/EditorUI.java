package ui.main_app.editor_ui;

import javax.swing.*;
import java.awt.*;

public class EditorUI extends JPanel {
    private JToolBar toolBar;
    private JMenuBar menuBar;
    private JPanel canvas;

    public EditorUI(JPanel canvas) {
        this.canvas = canvas;
        this.setLayout(new BorderLayout());

        menuBar = initMenuBar();
        toolBar = initToolBar();

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(menuBar, BorderLayout.NORTH);
        topPanel.add(toolBar, BorderLayout.CENTER);

        this.add(topPanel, BorderLayout.NORTH);
    }

    private JToolBar initToolBar() {
        JToolBar toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setBackground(Color.WHITE);

        JButton undoButton = new JButton("↩");
        JButton redoButton = new JButton("↪");
        JButton boldButton = new JButton("𝐁");
        JButton italicButton = new JButton("𝘐");
        JButton underlineButton = new JButton("U̲");
        JButton colorButton = new JButton("🎨");
        JButton toggleGridButton = new JButton("Grille");

        String[] fontSizes = {"8pt", "10pt", "12pt", "14pt", "16pt"};
        JComboBox<String> fontSizeBox = new JComboBox<>(fontSizes);
        JSlider thicknessSlider = new JSlider(1, 10, 2);

        toolBar.add(undoButton);
        toolBar.add(redoButton);
        toolBar.addSeparator();
        toolBar.add(fontSizeBox);
        toolBar.add(boldButton);
        toolBar.add(italicButton);
        toolBar.add(underlineButton);
        toolBar.addSeparator();
        toolBar.add(colorButton);
        toolBar.add(thicknessSlider);
        toolBar.add(toggleGridButton);

        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur", Color.BLACK);
            if (newColor != null) {
                canvas.setBackground(newColor);
            }
        });

        toggleGridButton.addActionListener(e -> {


        });

        return toolBar;
    }

    private JMenuBar initMenuBar() {
        JMenuBar menubar = new JMenuBar();
        menubar.setBackground(Color.WHITE);

        JMenu fichier = new JMenu("Fichier");
        JMenu edition = new JMenu("Édition");
        JMenu afficher = new JMenu("Afficher");
        JMenu aide = new JMenu("Aide");

        fichier.add(new JMenuItem("📂 Ouvrir"));
        fichier.add(new JMenuItem("💾 Enregistrer"));
        fichier.add(new JMenuItem("📂 Enregistrer Sous"));
        fichier.add(new JMenuItem("🔤 Renommer"));

        JMenuItem quitter = new JMenuItem("❌ Quitter");
        quitter.addActionListener(e -> System.exit(0));
        fichier.add(quitter);
        fichier.add(new JMenuItem("Fermer"));

        edition.add(new JMenuItem("↩ Annuler"));
        edition.add(new JMenuItem("📥 Copier"));
        edition.add(new JMenuItem("✂️ Coller"));
        edition.add(new JMenuItem("✂️ Couper"));
        edition.add(new JMenuItem("🗑 Supprimer"));

        afficher.add(new JMenuItem("Afficher la barre d'outils"));
        afficher.add(new JMenuItem("Mode plein écran"));

        JMenu themeMenu = new JMenu("Thème");
        themeMenu.add(new JMenuItem("Sombre"));
        themeMenu.add(new JMenuItem("Clair"));

        JMenu policeMenu = new JMenu("Police");
        policeMenu.add(new JMenuItem("Petite"));
        policeMenu.add(new JMenuItem("Moyenne"));
        policeMenu.add(new JMenuItem("Grande"));

        afficher.add(themeMenu);
        afficher.add(policeMenu);

        aide.add(new JMenuItem("❓ Aide"));
        aide.add(new JMenuItem("ℹ Information sur l'éditeur"));
        aide.add(new JMenuItem("📖 Tutoriel"));

        menubar.add(fichier);
        menubar.add(edition);
        menubar.add(afficher);
        menubar.add(aide);

        return menubar;
    }

}
