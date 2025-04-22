package ui.main_app.main_topmenu;

import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;

public class MainTopMenu extends JMenuBar {
    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(13, 110, 253);  // Vibrant blue
    private static final Color SECONDARY_COLOR = new Color(248, 249, 250);  // Very light gray
    private static final Color ACCENT_COLOR = new Color(25, 135, 84);  // Green accent
    private static final Color DANGER_COLOR = new Color(220, 53, 69);  // Red accent
    private static final Color TEXT_COLOR = new Color(33, 37, 41);  // Dark gray for text
    private static final Color HOVER_COLOR = new Color(233, 236, 239);  // Light gray for hover

    // Modern font
    private static final Font MENU_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font MENU_ITEM_FONT = new Font("Segoe UI", Font.PLAIN, 13);

    // Menu items with modern icons
    private final JMenu fichier;
    private final JMenu edition;
    private final JMenu afficher;
    private final JMenu aide;
    private final JMenu themeMenu;
    private final JMenu policeMenu;

    // File menu items
    private final JMenuItem ouvrir;
    private final JMenuItem enregistrer;
    private final JMenuItem enregistrerSous;
    private final JMenuItem renommer;
    private final JMenuItem quitter;

    // Edit menu items
    private final JMenuItem annuler;
    private final JMenuItem retablir;
    private final JMenuItem copier;
    private final JMenuItem coller;
    private final JMenuItem couper;
    private final JMenuItem supprimer;

    // View menu items
    private final JMenuItem afficherBarreOutils;
    private final JMenuItem pleinEcran;
    private final JMenuItem modeSombre;
    private final JMenuItem modeClair;

    // Font menu items
    private final JMenuItem petitePolice;
    private final JMenuItem moyennePolice;
    private final JMenuItem grandePolice;

    // Help menu items
    private final JMenuItem aideItem;
    private final JMenuItem info;
    private final JMenuItem tutoriel;

    private final JTextArea textArea = new JTextArea();
    private File currentFile;

    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;

    public MainTopMenu(WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow, File currentFile) {
        this.currentFile = currentFile;
        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

        // Set modern look for menu bar
        this.setBackground(SECONDARY_COLOR);
        this.setBorder(new EmptyBorder(5, 5, 5, 5));

        // Initialize menus with modern styling
        fichier = createMenu("Fichier");
        edition = createMenu("Édition");
        afficher = createMenu("Afficher");
        aide = createMenu("Aide");
        themeMenu = createMenu("Thème");
        policeMenu = createMenu("Police");

        // Initialize menu items with modern icons
        ouvrir = createMenuItem("Ouvrir", createIcon("folder-open", PRIMARY_COLOR));
        enregistrer = createMenuItem("Enregistrer", createIcon("save", ACCENT_COLOR));
        enregistrerSous = createMenuItem("Enregistrer Sous", createIcon("save-as", ACCENT_COLOR));
        renommer = createMenuItem("Renommer", createIcon("pencil", PRIMARY_COLOR));
        quitter = createMenuItem("Quitter", createIcon("power", DANGER_COLOR));

        annuler = createMenuItem("Annuler", createIcon("arrow-counterclockwise", PRIMARY_COLOR));
        retablir = createMenuItem("Rétablir", createIcon("arrow-clockwise", PRIMARY_COLOR));
        copier = createMenuItem("Copier", createIcon("clipboard", PRIMARY_COLOR));
        coller = createMenuItem("Coller", createIcon("clipboard-plus", PRIMARY_COLOR));
        couper = createMenuItem("Couper", createIcon("scissors", PRIMARY_COLOR));
        supprimer = createMenuItem("Supprimer", createIcon("trash", DANGER_COLOR));

        afficherBarreOutils = createMenuItem("Afficher la barre d'outils", createIcon("tools", PRIMARY_COLOR));
        pleinEcran = createMenuItem("Mode plein écran", createIcon("fullscreen", PRIMARY_COLOR));
        modeSombre = createMenuItem("Sombre", createIcon("moon", PRIMARY_COLOR));
        modeClair = createMenuItem("Clair", createIcon("sun", PRIMARY_COLOR));

        petitePolice = createMenuItem("Petite", createIcon("text-size", PRIMARY_COLOR, 12));
        moyennePolice = createMenuItem("Moyenne", createIcon("text-size", PRIMARY_COLOR, 14));
        grandePolice = createMenuItem("Grande", createIcon("text-size", PRIMARY_COLOR, 16));

        aideItem = createMenuItem("Aide", createIcon("question-circle", PRIMARY_COLOR));
        info = createMenuItem("Information sur l'éditeur", createIcon("info-circle", PRIMARY_COLOR));
        tutoriel = createMenuItem("Tutoriel", createIcon("book", PRIMARY_COLOR));

        // Initialize all menus
        initFileMenu();
        initEditMenu();
        initViewModesMenu();
        initFontMenu();
        initHelpMenu();
    }

    private JMenu createMenu(String text) {
        JMenu menu = new JMenu(text);
        menu.setFont(MENU_FONT);
        menu.setForeground(TEXT_COLOR);
        menu.setBorder(new EmptyBorder(5, 10, 5, 10));

        // Add hover effect
        menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menu.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menu.setForeground(TEXT_COLOR);
            }
        });

        return menu;
    }

    private JMenuItem createMenuItem(String text, Icon icon) {
        JMenuItem menuItem = new JMenuItem(text);
        menuItem.setIcon(icon);
        menuItem.setFont(MENU_ITEM_FONT);
        menuItem.setForeground(TEXT_COLOR);
        menuItem.setBorder(new EmptyBorder(8, 5, 8, 10));

        // Add hover effect
        menuItem.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                menuItem.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                menuItem.setBackground(UIManager.getColor("MenuItem.background"));
            }
        });

        return menuItem;
    }

    private Icon createIcon(String iconName, Color color) {
        return createIcon(iconName, color, 16);
    }

    private Icon createIcon(String iconName, Color color, int size) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(color);

                switch (iconName) {
                    case "folder-open":
                        drawFolderOpenIcon(g2d, x, y, size);
                        break;
                    case "save":
                        drawSaveIcon(g2d, x, y, size);
                        break;
                    case "save-as":
                        drawSaveAsIcon(g2d, x, y, size);
                        break;
                    case "pencil":
                        drawPencilIcon(g2d, x, y, size);
                        break;
                    case "power":
                        drawPowerIcon(g2d, x, y, size);
                        break;
                    case "arrow-counterclockwise":
                        drawUndoIcon(g2d, x, y, size);
                        break;
                    case "arrow-clockwise":
                        drawRedoIcon(g2d, x, y, size);
                        break;
                    case "clipboard":
                        drawClipboardIcon(g2d, x, y, size);
                        break;
                    case "clipboard-plus":
                        drawClipboardPlusIcon(g2d, x, y, size);
                        break;
                    case "scissors":
                        drawScissorsIcon(g2d, x, y, size);
                        break;
                    case "trash":
                        drawTrashIcon(g2d, x, y, size);
                        break;
                    case "tools":
                        drawToolsIcon(g2d, x, y, size);
                        break;
                    case "fullscreen":
                        drawFullscreenIcon(g2d, x, y, size);
                        break;
                    case "moon":
                        drawMoonIcon(g2d, x, y, size);
                        break;
                    case "sun":
                        drawSunIcon(g2d, x, y, size);
                        break;
                    case "text-size":
                        drawTextSizeIcon(g2d, x, y, size);
                        break;
                    case "question-circle":
                        drawQuestionCircleIcon(g2d, x, y, size);
                        break;
                    case "info-circle":
                        drawInfoCircleIcon(g2d, x, y, size);
                        break;
                    case "book":
                        drawBookIcon(g2d, x, y, size);
                        break;
                    default:
                        // Default icon if none matches
                        g2d.fillOval(x, y, size, size);
                }

                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return size;
            }

            @Override
            public int getIconHeight() {
                return size;
            }
        };
    }

    // Icon drawing methods
    private void drawFolderOpenIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw folder base
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x, y + height/4, width*3/4, height*3/4);

        // Draw folder top
        int[] xPoints = {x, x + width/3, x + width*2/3, x + width*3/4};
        int[] yPoints = {y + height/4, y, y, y + height/4};
        g2d.drawPolyline(xPoints, yPoints, 4);

        // Draw open flap
        g2d.drawLine(x + width*3/4, y + height/4, x + width, y + height/2);
        g2d.drawLine(x + width, y + height/2, x + width*3/4, y + height);
    }

    private void drawSaveIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw save icon outline
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x, y, width, height);

        // Draw top bar
        g2d.fillRect(x + width/4, y, width/2, height/4);

        // Draw inner rectangle
        g2d.drawRect(x + width/4, y + height/3, width/2, height/2);
    }

    private void drawSaveAsIcon(Graphics2D g2d, int x, int y, int size) {
        // Draw save icon
        drawSaveIcon(g2d, x, y, size);

        // Draw plus sign
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x + size*3/4, y + size/2, x + size, y + size/2);
        g2d.drawLine(x + size*7/8, y + size/3, x + size*7/8, y + size*2/3);
    }

    private void drawPencilIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw pencil body
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x + width/4, y + height*3/4, x + width*3/4, y + height/4);

        // Draw pencil tip
        g2d.drawLine(x + width*3/4, y + height/4, x + width*7/8, y + height/8);
        g2d.drawLine(x + width*7/8, y + height/8, x + width, y + height/4);
        g2d.drawLine(x + width, y + height/4, x + width*3/4, y + height/4);
    }

    private void drawPowerIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw power circle
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x, y, width, height);

        // Draw power line
        g2d.drawLine(x + width/2, y + height/4, x + width/2, y + height*5/8);
    }

    private void drawUndoIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw curved arrow
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawArc(x, y, width*3/4, height*3/4, 0, 270);

        // Draw arrowhead
        int[] xPoints = {x, x + width/4, x};
        int[] yPoints = {y + height/4, y + height/4, y + height/2};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawRedoIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw curved arrow
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawArc(x + width/4, y, width*3/4, height*3/4, 180, 270);

        // Draw arrowhead
        int[] xPoints = {x + width, x + width*3/4, x + width};
        int[] yPoints = {y + height/4, y + height/4, y + height/2};
        g2d.fillPolygon(xPoints, yPoints, 3);
    }

    private void drawClipboardIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw clipboard outline
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x + width/6, y + height/6, width*2/3, height*3/4);

        // Draw clipboard top
        g2d.drawRect(x + width/3, y, width/3, height/6);

        // Draw lines on clipboard
        g2d.drawLine(x + width/3, y + height/3, x + width*2/3, y + height/3);
        g2d.drawLine(x + width/3, y + height/2, x + width*2/3, y + height/2);
        g2d.drawLine(x + width/3, y + height*2/3, x + width*2/3, y + height*2/3);
    }

    private void drawClipboardPlusIcon(Graphics2D g2d, int x, int y, int size) {
        // Draw clipboard
        drawClipboardIcon(g2d, x, y, size);

        // Draw plus sign
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x + size*3/4, y + size/2, x + size, y + size/2);
        g2d.drawLine(x + size*7/8, y + size/3, x + size*7/8, y + size*2/3);
    }

    private void drawScissorsIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw scissors blades
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x + width/2, y + height/2, x + width/6, y + height/6);
        g2d.drawLine(x + width/2, y + height/2, x + width/6, y + height*5/6);

        // Draw scissors handles
        g2d.drawOval(x, y, width/3, height/3);
        g2d.drawOval(x, y + height*2/3, width/3, height/3);
    }

    private void drawTrashIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw trash can
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x + width/6, y + height/4, width*2/3, height*3/4);

        // Draw trash lid
        g2d.drawLine(x, y + height/4, x + width, y + height/4);
        g2d.drawRect(x + width/3, y, width/3, height/4);

        // Draw lines on trash
        g2d.drawLine(x + width/3, y + height/3, x + width/3, y + height*7/8);
        g2d.drawLine(x + width/2, y + height/3, x + width/2, y + height*7/8);
        g2d.drawLine(x + width*2/3, y + height/3, x + width*2/3, y + height*7/8);
    }

    private void drawToolsIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw wrench
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x + width/4, y + height/4, x + width*3/4, y + height*3/4);
        g2d.drawOval(x + width/8, y + height/8, width/4, height/4);
        g2d.drawOval(x + width*5/8, y + height*5/8, width/4, height/4);
    }

    private void drawFullscreenIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw fullscreen corners
        g2d.setStroke(new BasicStroke(1.5f));

        // Top-left
        g2d.drawLine(x, y, x + width/4, y);
        g2d.drawLine(x, y, x, y + height/4);

        // Top-right
        g2d.drawLine(x + width*3/4, y, x + width, y);
        g2d.drawLine(x + width, y, x + width, y + height/4);

        // Bottom-left
        g2d.drawLine(x, y + height*3/4, x, y + height);
        g2d.drawLine(x, y + height, x + width/4, y + height);

        // Bottom-right
        g2d.drawLine(x + width*3/4, y + height, x + width, y + height);
        g2d.drawLine(x + width, y + height*3/4, x + width, y + height);
    }

    private void drawMoonIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw moon
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawArc(x, y, width, height, 60, 220);
        g2d.drawArc(x + width/3, y, width*2/3, height, 300, 240);
    }

    private void drawSunIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw sun center
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x + width/4, y + height/4, width/2, height/2);

        // Draw sun rays
        g2d.drawLine(x + width/2, y, x + width/2, y + height/6);
        g2d.drawLine(x + width/2, y + height*5/6, x + width/2, y + height);
        g2d.drawLine(x, y + height/2, x + width/6, y + height/2);
        g2d.drawLine(x + width*5/6, y + height/2, x + width, y + height/2);

        // Diagonal rays
        g2d.drawLine(x + width/6, y + height/6, x + width/3, y + height/3);
        g2d.drawLine(x + width*2/3, y + height*2/3, x + width*5/6, y + height*5/6);
        g2d.drawLine(x + width*2/3, y + height/3, x + width*5/6, y + height/6);
        g2d.drawLine(x + width/6, y + height*5/6, x + width/3, y + height*2/3);
    }

    private void drawTextSizeIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw "A" letter
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawLine(x + width/4, y + height*3/4, x + width/2, y + height/4);
        g2d.drawLine(x + width/2, y + height/4, x + width*3/4, y + height*3/4);
        g2d.drawLine(x + width/3, y + height*2/3, x + width*2/3, y + height*2/3);
    }

    private void drawQuestionCircleIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw circle
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x, y, width, height);

        // Draw question mark
        g2d.drawArc(x + width/3, y + height/4, width/3, height/3, 180, 270);
        g2d.fillOval(x + width*2/5, y + height*2/3, width/5, height/5);
    }

    private void drawInfoCircleIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw circle
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawOval(x, y, width, height);

        // Draw "i"
        g2d.fillOval(x + width*2/5, y + height/4, width/5, height/5);
        g2d.fillRect(x + width*2/5, y + height/2, width/5, height/3);
    }

    private void drawBookIcon(Graphics2D g2d, int x, int y, int size) {
        int width = size;
        int height = size;

        // Draw book cover
        g2d.setStroke(new BasicStroke(1.5f));
        g2d.drawRect(x + width/6, y + height/6, width*2/3, height*2/3);

        // Draw book spine
        g2d.drawLine(x + width/2, y + height/6, x + width/2, y + height*5/6);

        // Draw book pages
        g2d.drawLine(x + width/6, y + height/3, x + width*5/6, y + height/3);
        g2d.drawLine(x + width/6, y + height/2, x + width*5/6, y + height/2);
        g2d.drawLine(x + width/6, y + height*2/3, x + width*5/6, y + height*2/3);
    }

    private void initFontMenu() {
        petitePolice.addActionListener(e -> setFontSize(10));
        moyennePolice.addActionListener(e -> setFontSize(14));
        grandePolice.addActionListener(e -> setFontSize(18));
        policeMenu.add(petitePolice);
        policeMenu.add(moyennePolice);
        policeMenu.add(grandePolice);
        afficher.add(policeMenu);
    }

    private void initViewModesMenu() {
        afficherBarreOutils.addActionListener(e -> toggleToolBar());
        pleinEcran.addActionListener(e -> toggleFullScreen());
        modeSombre.addActionListener(e -> setTheme(new Color(33, 37, 41), Color.WHITE));
        modeClair.addActionListener(e -> setTheme(Color.WHITE, Color.BLACK));
        themeMenu.add(modeSombre);
        themeMenu.add(modeClair);
        afficher.add(afficherBarreOutils);
        afficher.add(pleinEcran);
        afficher.add(themeMenu);
        this.add(afficher);
    }

    private void initEditMenu() {
        retablir.addActionListener(e -> redoAction());
        copier.addActionListener(e -> copyAction());
        coller.addActionListener(e -> pasteAction());
        couper.addActionListener(e -> cutAction());
        supprimer.addActionListener(e -> deleteAction());
        annuler.addActionListener(e -> undoAction());

        edition.add(annuler);
        edition.add(retablir);
        edition.addSeparator();
        edition.add(copier);
        edition.add(coller);
        edition.add(couper);
        edition.addSeparator();
        edition.add(supprimer);
        this.add(edition);
    }

    private void initFileMenu() {
        ouvrir.addActionListener(e -> openFile());
        enregistrer.addActionListener(e -> saveFile());
        enregistrerSous.addActionListener(e -> saveAsFile());
        renommer.addActionListener(e -> renameFile());
        quitter.addActionListener(e -> System.exit(0));

        fichier.add(ouvrir);
        fichier.addSeparator();
        fichier.add(enregistrer);
        fichier.add(enregistrerSous);
        fichier.add(renommer);
        fichier.addSeparator();
        fichier.add(quitter);

        this.add(fichier);
    }

    private void initHelpMenu() {
        aideItem.addActionListener(e -> showHelp());
        info.addActionListener(e -> showInfo());
        tutoriel.addActionListener(e -> showTutorial());

        aide.add(aideItem);
        aide.add(info);
        aide.add(tutoriel);
        this.add(aide);
    }

    private void setTheme(Color bgColor, Color fgColor) {
        textArea.setBackground(bgColor);
        textArea.setForeground(fgColor);

        // Show confirmation message with modern styling
        JOptionPane optionPane = new JOptionPane(
                "Thème appliqué avec succès !",
                JOptionPane.INFORMATION_MESSAGE
        );
        JDialog dialog = optionPane.createDialog("Thème");
        dialog.setBackground(SECONDARY_COLOR);
        dialog.setVisible(true);
    }

    private void copyAction() {
        textArea.copy();
    }

    private void pasteAction() {
        textArea.paste();
    }

    private void cutAction() {
        textArea.cut();
    }

    private void deleteAction() {
        if (currentFile != null) {
            // Create custom confirmation dialog with modern styling
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(new EmptyBorder(15, 15, 15, 15));
            panel.setBackground(SECONDARY_COLOR);

            JLabel iconLabel = new JLabel(createIcon("trash", DANGER_COLOR, 32));
            panel.add(iconLabel, BorderLayout.WEST);

            JLabel messageLabel = new JLabel("Voulez-vous vraiment supprimer ce fichier ?");
            messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            messageLabel.setForeground(TEXT_COLOR);
            panel.add(messageLabel, BorderLayout.CENTER);

            int confirmation = JOptionPane.showConfirmDialog(
                    this, panel, "Confirmation",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                if (currentFile.delete()) {
                    showSuccessMessage("Fichier supprimé avec succès !");
                    currentFile = null; // Réinitialiser la référence du fichier
                } else {
                    showErrorMessage("Échec de la suppression du fichier.");
                }
            }
        } else {
            showErrorMessage("Aucun fichier sélectionné.");
        }
    }

    private void toggleToolBar() {
        // Implementation would go here
        showInfoMessage("Basculement de la barre d'outils");
    }

    private void toggleFullScreen() {
        // Implementation would go here
        showInfoMessage("Basculement du mode plein écran");
    }

    private void showHelp() {
        // Implementation would go here
        showInfoMessage("Aide affichée");
    }

    private void showInfo() {
        // Create a modern about dialog
        JPanel panel = new JPanel(new BorderLayout(15, 15));
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setBackground(SECONDARY_COLOR);

        JLabel titleLabel = new JLabel("UML Designer");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea infoText = new JTextArea(
                "Version: 1.0.0\n" +
                "Créé par: Équipe de développement\n\n" +
                "Un éditeur UML moderne et intuitif pour créer des diagrammes professionnels."
        );
        infoText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        infoText.setBackground(SECONDARY_COLOR);
        infoText.setEditable(false);
        infoText.setLineWrap(true);
        infoText.setWrapStyleWord(true);
        panel.add(infoText, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                this, panel, "À propos de l'éditeur",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void showTutorial() {
        // Implementation would go here
        showInfoMessage("Tutoriel affiché");
    }

    private void setFontSize(int size) {
        textArea.setFont(new Font(textArea.getFont().getName(), Font.PLAIN, size));
        showInfoMessage("Taille de police modifiée à " + size + "pt");
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Ouvrir un fichier");

        // Style the file chooser
        fileChooser.setBackground(SECONDARY_COLOR);
        fileChooser.setForeground(TEXT_COLOR);

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(currentFile.toPath()));

                // Create a modern dialog to show file content
                JTextArea contentArea = new JTextArea(content);
                contentArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
                contentArea.setEditable(false);
                contentArea.setLineWrap(true);
                contentArea.setWrapStyleWord(true);

                JScrollPane scrollPane = new JScrollPane(contentArea);
                scrollPane.setPreferredSize(new Dimension(500, 300));

                JOptionPane.showMessageDialog(
                        this, scrollPane, "Contenu du fichier: " + currentFile.getName(),
                        JOptionPane.PLAIN_MESSAGE
                );
            } catch (Exception ex) {
                showErrorMessage("Erreur lors de l'ouverture du fichier : " + ex.getMessage());
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try {
                String content = "Contenu modifié du fichier"; // Remplace par le contenu réel
                Files.write(currentFile.toPath(), content.getBytes());
                showSuccessMessage("Fichier enregistré avec succès !");
            } catch (Exception ex) {
                showErrorMessage("Erreur lors de l'enregistrement du fichier : " + ex.getMessage());
            }
        } else {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer sous");

        // Style the file chooser
        fileChooser.setBackground(SECONDARY_COLOR);
        fileChooser.setForeground(TEXT_COLOR);

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile();
        }
    }

    private void renameFile() {
        if (currentFile != null) {
            // Create a modern input dialog
            JPanel panel = new JPanel(new BorderLayout(10, 10));
            panel.setBorder(new EmptyBorder(15, 15, 15, 15));
            panel.setBackground(SECONDARY_COLOR);

            JLabel label = new JLabel("Nouveau nom du fichier :");
            label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            label.setForeground(TEXT_COLOR);
            panel.add(label, BorderLayout.NORTH);

            JTextField textField = new JTextField(currentFile.getName());
            textField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            textField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(206, 212, 218)),
                    BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            panel.add(textField, BorderLayout.CENTER);

            int result = JOptionPane.showConfirmDialog(
                    this, panel, "Renommer le fichier",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String newName = textField.getText();
                if (newName != null && !newName.trim().isEmpty()) {
                    File newFile = new File(currentFile.getParent(), newName);
                    if (currentFile.renameTo(newFile)) {
                        currentFile = newFile;
                        showSuccessMessage("Fichier renommé avec succès !");
                    } else {
                        showErrorMessage("Échec du renommage du fichier.");
                    }
                }
            }
        } else {
            showErrorMessage("Aucun fichier ouvert.");
        }
    }

    private void undoAction() {
        if (mainFlow.getList().isEmpty()) {
            showInfoMessage("Aucune action à annuler.");
            return;
        }
        var e = mainFlow.getList().getLast();
        mainFlow.removeElement(e);
        undoFlow.addElement(e);
        showInfoMessage("Action annulée.");
    }

    private void redoAction() {
        if (undoFlow.getList().isEmpty()) {
            showInfoMessage("Aucune action à rétablir.");
            return;
        }
        var e = undoFlow.getList().getLast();
        undoFlow.removeElement(e);
        mainFlow.addElement(e);
        showInfoMessage("Action rétablie.");
    }

    // Helper methods for showing styled messages
    private void showSuccessMessage(String message) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(SECONDARY_COLOR);

        JLabel iconLabel = new JLabel(createIcon("info-circle", ACCENT_COLOR, 24));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        panel.add(messageLabel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                this, panel, "Succès",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void showErrorMessage(String message) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(SECONDARY_COLOR);

        JLabel iconLabel = new JLabel(createIcon("info-circle", DANGER_COLOR, 24));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        panel.add(messageLabel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                this, panel, "Erreur",
                JOptionPane.PLAIN_MESSAGE
        );
    }

    private void showInfoMessage(String message) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        panel.setBackground(SECONDARY_COLOR);

        JLabel iconLabel = new JLabel(createIcon("info-circle", PRIMARY_COLOR, 24));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel messageLabel = new JLabel(message);
        messageLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        messageLabel.setForeground(TEXT_COLOR);
        panel.add(messageLabel, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(
                this, panel, "Information",
                JOptionPane.PLAIN_MESSAGE
        );
    }
}