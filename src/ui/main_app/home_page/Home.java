package ui.main_app.home_page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.Arrays;

public class Home extends JPanel{

    public Home(ActionListener openDocument) {
        this.setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new CardLayout());
        mainPanel.setPreferredSize(new Dimension(500, 400)); // Taille uniforme des panels
        this.add(mainPanel, BorderLayout.CENTER);

        mainPanel.add(createHomePanel(openDocument), "HomePanel");
        mainPanel.add(createOpenPanel(), "OpenPanel");
        this.add(createSidePanel(mainPanel), BorderLayout.WEST);
    }

    private static JPanel createSidePanel(JPanel mainPanel) {
        JPanel sidePanel = new JPanel(new GridLayout(3, 1));
        sidePanel.setBackground(Color.DARK_GRAY);

        String[] sideOptions = {"Accueil", "Nouveau", "Ouvrir"};
        for (String option : sideOptions) {
            JButton button = new JButton(option);
            button.setBackground(Color.LIGHT_GRAY);
            button.setForeground(Color.WHITE);

            button.addActionListener(e -> {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                if (option.equals("Accueil")) {
                    cl.show(mainPanel, "HomePanel");
                } else if (option.equals("Ouvrir")) {
                    cl.show(mainPanel, "OpenPanel");
                }
            });

            sidePanel.add(button);
        }
        return sidePanel;
    }

    private static JPanel createHomePanel(ActionListener openDocument) {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.LIGHT_GRAY);
        homePanel.setPreferredSize(new Dimension(500 , 400)); // Uniformisation de la taille

        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel splitPanel = new JPanel(new GridLayout(2, 1));

        JPanel blankDocumentPanel = new JPanel(new BorderLayout());
        blankDocumentPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("Nouveau", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        blankDocumentPanel.add(titleLabel, BorderLayout.NORTH);

        JPanel documentButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton documentButton = new JButton("Document");

        documentButton.addActionListener(openDocument);
        documentButtonPanel.add(documentButton);
        blankDocumentPanel.add(documentButtonPanel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton recentButton = new JButton("Récent");
        JButton pinnedButton = new JButton("Épinglé");
        buttonPanel.add(recentButton);
        buttonPanel.add(pinnedButton);

        splitPanel.add(blankDocumentPanel);
        splitPanel.add(buttonPanel);
        contentPanel.add(splitPanel, BorderLayout.CENTER);

        homePanel.add(contentPanel, BorderLayout.CENTER);
        return homePanel;
    }

    private static JPanel createOpenPanel() {
        JPanel openPanel = new JPanel(new BorderLayout());
        openPanel.setBackground(Color.WHITE);
        openPanel.setPreferredSize(new Dimension(500, 400)); // Uniformisation de la taille

        DefaultListModel<File> listModel = new DefaultListModel<>();
        JList<File> fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fileList);
        openPanel.add(scrollPane, BorderLayout.CENTER);

        JButton openFileButton = new JButton("Ouvrir un fichier");
        openFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Fichier ouvert : " + selectedFile.getName());
            }
        });

        openPanel.add(openFileButton, BorderLayout.SOUTH);
        loadFiles(listModel);
        return openPanel;
    }

    private static void loadFiles(DefaultListModel<File> listModel) {
        File folder = new File("C:/Your/Desired/Directory");
        File[] files = folder.listFiles();

        if (files != null) {
            Arrays.sort(files);
            for (File file : files) {
                if (file.isFile()) {
                    listModel.addElement(file);
                }
            }
        }
    }
}
