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
        sidePanel.setBackground(Color.darkGray);

        String[][] sideOptions = {{"acceuil","src/assets/acceuil.png"},
                {"nouveau","src/assets/nouveau.png"},
                {"ouvrir", "src/assets/folder.png"}};

        for (String[] option : sideOptions) {
            ImageIcon iconoption = new ImageIcon(option[1]);
            JButton button = new JButton(iconoption);

            button.setBackground(Color.darkGray);

            button.setFocusPainted(false);
            button.setContentAreaFilled(false);

            button.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setBorder(BorderFactory.createMatteBorder(0, 4, 0, 0, Color.BLACK));
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    button.setBorder(BorderFactory.createEmptyBorder());
                }
            });

            button.addActionListener(e -> {
                CardLayout cl = (CardLayout) mainPanel.getLayout();
                if (option[0].equals("acceuil")) {
                    cl.show(mainPanel, "HomePanel");
                } else if (option[0].equals("ouvrir")) {
                    cl.show(mainPanel, "OpenPanel");
                }
            });

            sidePanel.add(button);
        }
        return sidePanel;
    }

    private static JPanel createHomePanel(ActionListener openDocument) {
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.setBackground(Color.darkGray);
        homePanel.setPreferredSize(new Dimension(500 , 400));

        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel splitPanel = new JPanel(new GridLayout(2, 1));

        JPanel blankDocumentPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Nouveau", SwingConstants.LEFT);


        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.add(titleLabel);
        titlePanel.add(Box.createHorizontalStrut(5));
        titlePanel.add(Box.createVerticalStrut(10)); // Ajoute un espace de 20 pixels
        blankDocumentPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel documentButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton documentButton = new JButton();
        ImageIcon icondoc = new ImageIcon("src/assets/new.png");
        documentButton.setBorderPainted(false);
        documentButton.setContentAreaFilled(false);
        documentButton.setFocusPainted(false);

        documentButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                documentButtonPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                documentButtonPanel.setBorder(BorderFactory.createEmptyBorder());
            }
        });




        documentButton.addActionListener(openDocument);
        documentButtonPanel.add(documentButton);
        blankDocumentPanel.add(documentButtonPanel, BorderLayout.WEST);
        documentButton.setIcon(icondoc);
        documentButton.setBorderPainted(false);
        documentButton.setFocusPainted(false);
        documentButton.setContentAreaFilled(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton recentButton = new JButton("Récent");
        JButton pinnedButton = new JButton("Épinglé");

        recentButton.setFont(new Font("Arial", Font.BOLD, 18));
        pinnedButton.setFont(new Font("Arial", Font.BOLD, 18));

        recentButton.setMargin(new Insets(10, 20, 10, 20));
        pinnedButton.setMargin(new Insets(10, 20, 10, 20));

        recentButton.setPreferredSize(new Dimension(115, 50));
        pinnedButton.setPreferredSize(new Dimension(115, 50));

        recentButton.setContentAreaFilled(false);
        pinnedButton.setContentAreaFilled(false);
        recentButton.setFocusPainted(false);
        pinnedButton.setFocusPainted(false);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 10));

        recentButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                recentButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                recentButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        pinnedButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pinnedButton.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                pinnedButton.setBorder(BorderFactory.createEmptyBorder());
            }
        });

        buttonPanel.add(recentButton);
        buttonPanel.add(pinnedButton);

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.Y_AXIS));

        containerPanel.add(blankDocumentPanel);
        containerPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Adds 20 pixels of vertical space
        containerPanel.add(buttonPanel);

        splitPanel.add(containerPanel);
        contentPanel.add(splitPanel, BorderLayout.CENTER);

        homePanel.add(contentPanel, BorderLayout.CENTER);
        return homePanel;
    }

    private static JPanel createOpenPanel() {
        JPanel openPanel = new JPanel(new BorderLayout());
        openPanel.setBackground(Color.WHITE);
        openPanel.setPreferredSize(new Dimension(500, 400));

        DefaultListModel<File> listModel = new DefaultListModel<>();
        JList<File> fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(fileList);
        openPanel.add(scrollPane, BorderLayout.CENTER);

        JButton openFileButton = new JButton("Ouvrir un fichier");
        ImageIcon iconopen = new ImageIcon("src/assets/ouvrir.png");
        openFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Fichier ouvert : " + selectedFile.getName());
            }
        });

        openFileButton.setIcon(iconopen);
        openFileButton.setBorderPainted(false);
        openFileButton.setFocusPainted(false);
        openFileButton.setContentAreaFilled(false);

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
