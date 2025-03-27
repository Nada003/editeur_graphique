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
    private Color currentColor = Color.BLACK;
    private MainBoard board;

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

        board = new MainBoard(components);
        main.add(board, BorderLayout.CENTER); // Ajout correct du canvas


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
            JButton textButton = new JButton("📝 Texte"); // Bouton pour ajouter du texte

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
            toolBar.addSeparator();
            toolBar.add(textButton);


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


            return toolBar;
        }



    private void getComponentFromFile(File file) {
        var interpreter = new MyInterpreter();
        interpreter.interpretFile(file, components.getList());
    }
}
