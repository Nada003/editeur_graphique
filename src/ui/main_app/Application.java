package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.main_board.MainBoard;
import ui.main_app.main_menu.MainMenu;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Application extends JFrame {
    private static final WatchedList<UMLComponent> components = new WatchedList<>();
    private JToolBar toolBar;
    private JButton toggleGridButton;
    private boolean showGrid = false;
    private Color currentColor = Color.BLACK;
    private DrawingPanel canvas;

    public Application() {
        this.setTitle("UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        this.setJMenuBar(initMenuBar());
        this.add(initToolBar(), BorderLayout.NORTH);
        this.add(initMain(), BorderLayout.CENTER);
        this.pack();
        this.setVisible(true);
    }

    private JPanel initMain() {
        JPanel main = new JPanel();
        main.setLayout(new BorderLayout());


        main.setLayout(new BoxLayout(main, BoxLayout.X_AXIS));
        main.add(new MainMenu(components));
        main.add(new MainBoard(components));

        canvas = new DrawingPanel();
        canvas.setPreferredSize(new Dimension(600, 600));

        main.add(canvas, BorderLayout.CENTER); // Ajout correct du canvas


        return main;
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

    private JToolBar initToolBar() {
        toolBar = new JToolBar(JToolBar.HORIZONTAL);
        toolBar.setBackground(Color.WHITE);

        JButton undoButton = new JButton("↩");
        JButton redoButton = new JButton("↪");
        JButton boldButton = new JButton("𝐁");
        JButton italicButton = new JButton("𝘐");
        JButton underlineButton = new JButton("U̲");
        JButton colorButton = new JButton("🎨");
        toggleGridButton = new JButton("Grille");

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

        // Action Listener pour le choix des couleurs
        colorButton.addActionListener(e -> {
            Color newColor = JColorChooser.showDialog(this, "Choisir une couleur", currentColor);
            if (newColor != null) {
                currentColor = newColor;
            }
        });

        // Action Listener pour afficher/masquer la grille
        toggleGridButton.addActionListener(e -> {
            showGrid = !showGrid;
            canvas.repaint();
        });

        return toolBar;
    }

    private class DrawingPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (showGrid) {
                dessinerGrille(g);
            }
        }

        private void dessinerGrille(Graphics g) {
            g.setColor(Color.LIGHT_GRAY);
            for (int i = 0; i < getWidth(); i += 20) {
                g.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += 20) {
                g.drawLine(0, j, getWidth(), j);
            }
        }
    }

    private void getComponentFromFile(File file) {
        var interpreter = new MyInterpreter();
        interpreter.interpretFile(file, components.getList());
    }
}
