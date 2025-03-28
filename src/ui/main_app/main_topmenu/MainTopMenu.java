package ui.main_app.main_topmenu;

import ui.main_app.history.UserAction;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;

public class MainTopMenu extends JMenuBar {
    private static final  JMenuItem ouvrir = new JMenuItem("📂 Ouvrir");
    private static final JMenu fichier = new JMenu("Fichier");
    private static final JMenu edition = new JMenu("Édition");
    private static final JMenu afficher = new JMenu("Afficher");
    private static final JMenu aide = new JMenu("Aide");
    private static final JMenu themeMenu = new JMenu("Thème");
   private static final JMenu policeMenu = new JMenu("Police");
   private static final JMenuItem enregistrer = new JMenuItem("💾 Enregistrer");
   private static final JMenuItem enregistrerSous = new JMenuItem("📂 Enregistrer Sous");
   private static final JMenuItem renommer = new JMenuItem("🔤 Renommer");
   private static final JMenuItem quitter = new JMenuItem("❌ Quitter");
   private static final JMenuItem annuler = new JMenuItem("↩ Annuler");
   private static final JMenuItem retablir = new JMenuItem("↪ Rétablir");
   private static final JMenuItem copier = new JMenuItem("📥 Copier");
   private static final JMenuItem coller = new JMenuItem("✂️ Coller");
   private static final JMenuItem couper = new JMenuItem("✂️ Couper");
   private static final JMenuItem supprimer = new JMenuItem("🗑 Supprimer");
   private static final JMenuItem afficherBarreOutils = new JMenuItem("Afficher la barre d'outils");
   private static final JMenuItem pleinEcran = new JMenuItem("Mode plein écran");
   private static final JMenuItem modeSombre = new JMenuItem("Sombre");
   private static final JMenuItem modeClair = new JMenuItem("Clair");
   private static final JMenuItem petitePolice = new JMenuItem("Petite");
   private static final JMenuItem moyennePolice = new JMenuItem("Moyenne");
   private static final JMenuItem grandePolice = new JMenuItem("Grande");
   private static final JMenuItem aideItem = new JMenuItem("❓ Aide");
   private static final JMenuItem info = new JMenuItem("ℹ Information sur l'éditeur");
   private static final JMenuItem tutoriel = new JMenuItem("📖 Tutoriel");
   private final JTextArea textArea = new JTextArea();
   private File currentFile;

    private final WatchedList<UserAction> mainFlow;
    private final WatchedList<UserAction> undoFlow;


    public MainTopMenu(WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow, File currentFile) {
        this.setBackground(Color.WHITE);
        this.currentFile = currentFile;
        this.mainFlow = mainFlow;
        this.undoFlow = undoFlow;

        initFileMenu();
        initEditMenu();
        initViewModesMenu();
        initFontMenu();
        initHelpMenu();
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
        modeSombre.addActionListener(e -> setTheme(Color.DARK_GRAY, Color.WHITE));
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
        edition.add(annuler);
        edition.add(retablir);
        edition.add(copier);
        edition.add(coller);
        edition.add(couper);
        edition.add(supprimer);
        this.add(edition);
    }

    private void initFileMenu() {
        ouvrir.addActionListener(e -> openFile());
        enregistrer.addActionListener(e -> saveFile());
        enregistrerSous.addActionListener(e -> saveAsFile());
        renommer.addActionListener(e -> renameFile());
        quitter.addActionListener(e -> System.exit(0));
        annuler.addActionListener(e -> undoAction());
        fichier.add(ouvrir);
        fichier.add(enregistrer);
        fichier.add(enregistrerSous);
        fichier.add(renommer);
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
            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Voulez-vous vraiment supprimer ce fichier ?", "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if (confirmation == JOptionPane.YES_OPTION) {
                if (currentFile.delete()) {
                    JOptionPane.showMessageDialog(this, "Fichier supprimé avec succès !");
                    currentFile = null; // Réinitialiser la référence du fichier
                } else {
                    JOptionPane.showMessageDialog(this, "Échec de la suppression du fichier.",
                            "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun fichier sélectionné.",
                    "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleToolBar() {}

    private void toggleFullScreen() {}

    private void showHelp() {}

    private void showInfo() {}

    private void showTutorial() {}

    private void setFontSize(int size) {
        textArea.setFont(new Font(textArea.getFont().getName(), Font.PLAIN, size));
    }

    private void openFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                String content = new String(Files.readAllBytes(currentFile.toPath()));
                JOptionPane.showMessageDialog(this, "Contenu du fichier :\n" + content);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ouverture du fichier : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {
        if (currentFile != null) {
            try {
                String content = "Contenu modifié du fichier"; // Remplace par le contenu réel
                Files.write(currentFile.toPath(), content.getBytes());
                JOptionPane.showMessageDialog(this, "Fichier enregistré avec succès !");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du fichier : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveAsFile();
        }
    }

    private void saveAsFile() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile();
        }
    }

    private void renameFile() {
        if (currentFile != null) {
            String newName = JOptionPane.showInputDialog(this, "Nouveau nom du fichier :", currentFile.getName());
            if (newName != null && !newName.trim().isEmpty()) {
                File newFile = new File(currentFile.getParent(), newName);
                if (currentFile.renameTo(newFile)) {
                    currentFile = newFile;
                    JOptionPane.showMessageDialog(this, "Fichier renommé avec succès !");
                } else {
                    JOptionPane.showMessageDialog(this, "Échec du renommage du fichier.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Aucun fichier ouvert.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
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
