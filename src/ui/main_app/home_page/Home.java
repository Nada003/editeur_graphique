package ui.main_app.home_page;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import utils.UML_diagrame;

public class Home extends JPanel {
    // Modern color palette
    private static final Color PRIMARY_COLOR = new Color(13, 110, 253);  // Vibrant blue
    private static final Color SECONDARY_COLOR = new Color(248, 249, 250);  // Very light gray
    private static final Color ACCENT_COLOR = new Color(25, 135, 84);  // Green accent
    private static final Color DANGER_COLOR = new Color(220, 53, 69);  // Red accent
    private static final Color TEXT_COLOR = new Color(33, 37, 41);  // Dark gray for text
    private static final Color LIGHT_TEXT_COLOR = new Color(108, 117, 125);  // Light gray for secondary text
    private static final Color HOVER_COLOR = new Color(233, 236, 239);  // Light gray for hover
    private static final Color CARD_BG_COLOR = new Color(255, 255, 255);  // White for cards
    private static final Color SELECTED_CARD_BG_COLOR = new Color(209, 231, 255);  // Light blue for selected card
    private static final Color GRID_COLOR = new Color(208, 215, 222);  // Color for grid lines

    // Modern font
    private static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 32);
    private static final Font APP_TITLE_FONT = new Font("Segoe UI", Font.BOLD, 22);
    private static final Font SUBTITLE_FONT = new Font("Segoe UI", Font.BOLD, 18);
    private static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);
    private static final Font REGULAR_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    private static final Font SMALL_FONT = new Font("Segoe UI", Font.PLAIN, 12);
    private static final Font CREDIT_FONT = new Font("Segoe UI", Font.ITALIC, 12);

    // Diagram types
   /* private static final String CLASS_DIAGRAM = "Class Diagrams";
    private static final String SEQUENCE_DIAGRAM = "Sequence Diagrams";
    private static final String USE_CASE_DIAGRAM = "Use Case Diagrams";*/
    private static UML_diagrame currentDiagrame= UML_diagrame.diagrameClass;


    // Selected diagram type - start with Class Diagram selected
    //private String currentDiagrame = CLASS_DIAGRAM;

    // Diagram cards panel
    private JPanel diagramCardsPanel;
    private JPanel classDiagramCard;
    private JPanel sequenceDiagramCard;
    private JPanel useCaseDiagramCard;

    // Main document button
    private JButton documentButton;
    private Icon classDiagramIcon;
    private Icon sequenceDiagramIcon;
    private Icon useCaseDiagramIcon;

    // Instruction labels and animation
    private JLabel instructionLabel;
    private JLabel selectionInstructionLabel;
    private Timer pulseTimer;
    private float pulseAlpha = 1.0f;
    private boolean pulseIncreasing = false;

    // Main panels
    private JPanel homePanel;
    private JPanel openPanel;
    private JPanel recentProjectsPanel;
    private CardLayout mainCardLayout;
    private JPanel mainPanel;

    // Current size tracking for responsive design
    private int currentWidth = 0;
    private int currentHeight = 0;

    public Home(ActionListener openDocument) {
        this.setLayout(new BorderLayout());
        this.setBackground(SECONDARY_COLOR);

        // Create icons with initial sizes - they will be recreated when resized
        updateDiagramIcons(180, 220);

        // Add the main title bar with "UML Designer"
        JPanel titleBar = createTitleBar();
        this.add(titleBar, BorderLayout.NORTH);

        // Create main panel with card layout
        mainCardLayout = new CardLayout();
        mainPanel = new JPanel(mainCardLayout);
        this.add(mainPanel, BorderLayout.CENTER);

        // Create home and open panels
        homePanel = createHomePanel(openDocument);
        openPanel = createOpenPanel();
        recentProjectsPanel = createRecentProjectsPanel(openDocument);

        // Add panels to main panel
        mainPanel.add(homePanel, "HomePanel");
        mainPanel.add(openPanel, "OpenPanel");
        mainPanel.add(recentProjectsPanel, "RecentProjectsPanel");

        // Show home panel by default
        mainCardLayout.show(mainPanel, "HomePanel");

        // Add footer with creator credit
        JPanel footerPanel = createFooterPanel();
        this.add(footerPanel, BorderLayout.SOUTH);

        // Setup pulsing animation for instruction
        setupPulsingAnimation();

        // Add component listener to handle resizing
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                handleResize();
            }
        });
    }

    private void setupPulsingAnimation() {
        pulseTimer = new Timer(50, e -> {
            // Update alpha value for pulsing effect
            if (pulseIncreasing) {
                pulseAlpha += 0.05f;
                if (pulseAlpha >= 1.0f) {
                    pulseAlpha = 1.0f;
                    pulseIncreasing = false;
                }
            } else {
                pulseAlpha -= 0.05f;
                if (pulseAlpha <= 0.5f) {
                    pulseAlpha = 0.5f;
                    pulseIncreasing = true;
                }
            }

            // Update instruction labels if they exist
            if (instructionLabel != null) {
                instructionLabel.setForeground(new Color(
                    PRIMARY_COLOR.getRed(),
                    PRIMARY_COLOR.getGreen(),
                    PRIMARY_COLOR.getBlue(),
                    (int)(pulseAlpha * 255)
                ));
            }

            if (selectionInstructionLabel != null) {
                selectionInstructionLabel.setForeground(new Color(
                    ACCENT_COLOR.getRed(),
                    ACCENT_COLOR.getGreen(),
                    ACCENT_COLOR.getBlue(),
                    (int)(pulseAlpha * 255)
                ));
            }
        });
        pulseTimer.start();
    }

    private void handleResize() {
        // Get current size
        int width = this.getWidth();
        int height = this.getHeight();

        // Only update if size changed significantly (prevents excessive updates)
        if (Math.abs(width - currentWidth) > 50 || Math.abs(height - currentHeight) > 50) {
            currentWidth = width;
            currentHeight = height;

            // Scale icons based on current size
            int iconWidth = Math.max(120, Math.min(180, width / 6));
            int iconHeight = Math.max(160, Math.min(220, height / 3));

            // Update diagram icons
            updateDiagramIcons(iconWidth, iconHeight);

            // Update the document button icon
            updateSelectedDiagramIcon();

            // Adjust font sizes based on screen size
            adjustFontSizes(width, height);

            // Update card panel layout
            updateCardPanelLayout(width);

            // Ensure instruction label is visible
            ensureInstructionLabelVisibility();

            // Repaint the component
            revalidate();
            repaint();
        }
    }

    private void ensureInstructionLabelVisibility() {
        if (instructionLabel != null) {
            // Make sure instruction is visible regardless of window size
            instructionLabel.setVisible(true);

            // Adjust font size for better visibility on smaller screens
            float scaleFactor = Math.max(0.7f, Math.min(1.0f, currentWidth / 1200f));
            float newSize = 18 * scaleFactor;
            instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, (int)newSize));

            // Add padding around the instruction for better visibility
            instructionLabel.setBorder(new EmptyBorder(10, 10, 10, 10));
        }

        if (selectionInstructionLabel != null) {
            // Make sure selection instruction is visible regardless of window size
            selectionInstructionLabel.setVisible(true);

            // Adjust font size for better visibility on smaller screens
            float scaleFactor = Math.max(0.7f, Math.min(1.0f, currentWidth / 1200f));
            float newSize = 16 * scaleFactor;
            selectionInstructionLabel.setFont(new Font("Segoe UI", Font.BOLD, (int)newSize));

            // Add padding around the instruction for better visibility
            selectionInstructionLabel.setBorder(new EmptyBorder(5, 10, 15, 10));
        }
    }

    private void updateDiagramIcons(int width, int height) {
        classDiagramIcon = createClassDiagramLarge(width, height);
        sequenceDiagramIcon = createSequenceDiagramLarge(width, height);
        useCaseDiagramIcon = createUseCaseDiagramLarge(width, height);
    }

    private void updateSelectedDiagramIcon() {
        if (documentButton != null) {
            switch (currentDiagrame) {
                case UML_diagrame.diagrameClass:
                    documentButton.setIcon(classDiagramIcon);
                    break;
                case UML_diagrame.diagrameSequence:
                    documentButton.setIcon(sequenceDiagramIcon);
                    break;
                case UML_diagrame.diagrameCasUtilisation:
                    documentButton.setIcon(useCaseDiagramIcon);
                    break;
            }

            // Add a colored border to indicate selection
            documentButton.setBorder(new SelectedButtonBorder(PRIMARY_COLOR, 3));
        }
    }

    private void adjustFontSizes(int width, int height) {
        // Adjust font sizes based on screen width
        float scaleFactor = Math.max(0.7f, Math.min(1.0f, width / 1200f));

        // Update instruction label font if it exists
        if (instructionLabel != null) {
            float newSize = 18 * scaleFactor;
            instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, (int)newSize));
        }

        // Update selection instruction label font if it exists
        if (selectionInstructionLabel != null) {
            float newSize = 16 * scaleFactor;
            selectionInstructionLabel.setFont(new Font("Segoe UI", Font.BOLD, (int)newSize));
        }
    }

    private void updateCardPanelLayout(int width) {
        if (diagramCardsPanel != null) {
            // For smaller screens, switch to vertical layout
            if (width < 800) {
                diagramCardsPanel.setLayout(new GridLayout(3, 1, 0, 16));
                diagramCardsPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
            } else {
                diagramCardsPanel.setLayout(new GridLayout(1, 3, 24, 0));
                diagramCardsPanel.setBorder(new EmptyBorder(20, 60, 40, 60));
            }
            diagramCardsPanel.revalidate();
        }
    }

    private JPanel createTitleBar() {
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(PRIMARY_COLOR);
        titleBar.setBorder(new EmptyBorder(14, 24, 14, 24));

        // Create a panel for the title with an icon
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        titlePanel.setBackground(PRIMARY_COLOR);

        // Add UML icon
        JLabel iconLabel = new JLabel(createUmlIcon(28, 28));
        titlePanel.add(iconLabel);

        // Add title text
        JLabel titleLabel = new JLabel("Editeur Graphique");
        titleLabel.setFont(APP_TITLE_FONT);
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);

        titleBar.add(titlePanel, BorderLayout.WEST);

        // Add navigation buttons
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        navPanel.setBackground(PRIMARY_COLOR);

        JButton homeButton = createNavButton("Acceuil", e -> mainCardLayout.show(mainPanel, "HomePanel"));
        JButton openButton = createNavButton("Ouvrir", e -> mainCardLayout.show(mainPanel, "OpenPanel"));
        JButton recentButton = createNavButton("Projetc Récents", e -> mainCardLayout.show(mainPanel, "RecentProjectsPanel"));

        navPanel.add(homeButton);
        navPanel.add(openButton);
        navPanel.add(recentButton);

        titleBar.add(navPanel, BorderLayout.EAST);

        return titleBar;
    }

    private JButton createNavButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setForeground(new Color(220, 220, 220));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    private JPanel createFooterPanel() {
        JPanel footerPanel = new JPanel(new BorderLayout());
        footerPanel.setBackground(new Color(33, 37, 41));
        footerPanel.setBorder(new EmptyBorder(10, 24, 10, 24));

        JLabel creditLabel = new JLabel("Créer par...");
        creditLabel.setFont(CREDIT_FONT);
        creditLabel.setForeground(Color.WHITE);
        footerPanel.add(creditLabel, BorderLayout.EAST);

      

        return footerPanel;
    }

    private JPanel createHomePanel(ActionListener openDocument) {
        JPanel homePanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw modern grid background
                drawModernGridBackground(g, this.getWidth(), this.getHeight());
            }
        };
        homePanel.setBackground(SECONDARY_COLOR);

        // Create a welcome panel with centered content using GridBagLayout for responsiveness
        JPanel welcomePanel = new JPanel(new GridBagLayout());
        welcomePanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome title with UML styling
        JLabel welcomeTitle = new JLabel("Bienvenue sur notre Editeur graphique UML", SwingConstants.CENTER);
        welcomeTitle.setFont(TITLE_FONT);
        welcomeTitle.setForeground(TEXT_COLOR);
        gbc.insets = new Insets(40, 40, 10, 40);
        welcomePanel.add(welcomeTitle, gbc);

        // Welcome subtitle
        JLabel welcomeSubtitle = new JLabel("Créer des diagrammes UML professionels", SwingConstants.CENTER);
        welcomeSubtitle.setFont(SUBTITLE_FONT);
        welcomeSubtitle.setForeground(LIGHT_TEXT_COLOR);
        gbc.insets = new Insets(0, 40, 30, 40);
        welcomePanel.add(welcomeSubtitle, gbc);

        // Create a panel for the document button with instructions
        JPanel documentPanel = new JPanel();
        documentPanel.setLayout(new BoxLayout(documentPanel, BoxLayout.Y_AXIS));
        documentPanel.setOpaque(false);
        documentPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Create the document button with Class Diagram icon (default)
        documentButton = new JButton();
        documentButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        documentButton.setIcon(classDiagramIcon);
        documentButton.setBorderPainted(true);
        documentButton.setContentAreaFilled(false);
        documentButton.setFocusPainted(false);
        documentButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        documentButton.setBorder(new SelectedButtonBorder(PRIMARY_COLOR, 3));

        // Add action listener
        documentButton.addActionListener(e -> {
            if (currentDiagrame != null) {
                // Open the selected diagram type
                openDocument.actionPerformed(e);
            } else {
                // Open generic diagram
                openDocument.actionPerformed(e);
            }
        });

        // Instruction text with interactive styling
        instructionLabel = new JLabel("Cliquer sur le diagramme ci-dessus pour commencer", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        instructionLabel.setForeground(PRIMARY_COLOR);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Add tooltip to make it more interactive
        instructionLabel.setToolTipText("Cliquer ici pour débuteur le travail avec le diagramme sélectionné");

        documentPanel.add(documentButton);
        documentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        documentPanel.add(instructionLabel);

        gbc.insets = new Insets(0, 40, 20, 40);
        gbc.fill = GridBagConstraints.NONE;
        welcomePanel.add(documentPanel, gbc);

        // Add selection instruction label
        selectionInstructionLabel = new JLabel("Selectionner un type de diagramme de votre choix", SwingConstants.CENTER);
        selectionInstructionLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        selectionInstructionLabel.setForeground(ACCENT_COLOR);
        selectionInstructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        selectionInstructionLabel.setBorder(new EmptyBorder(5, 0, 15, 0));

        gbc.insets = new Insets(0, 40, 0, 40);
        welcomePanel.add(selectionInstructionLabel, gbc);

        // Add welcome panel to home panel with weight to make it expand
        homePanel.add(welcomePanel, BorderLayout.CENTER);

        // Add a decorative bottom panel with UML diagram types
        diagramCardsPanel = new JPanel(new GridLayout(1, 3, 24, 0));
        diagramCardsPanel.setOpaque(false);
        diagramCardsPanel.setBorder(new EmptyBorder(20, 60, 40, 60));

        // Create diagram type cards
        classDiagramCard = createDiagramTypeCard(UML_diagrame.diagrameClass,
                "Modeliser la structure de votre systeme avec des classes, des attributs et des méthodes",
                createClassDiagramPreview());

        sequenceDiagramCard = createDiagramTypeCard(UML_diagrame.diagrameSequence,
                "Visualiser les intéractions entre objets dans un order séquentiel",
                createSequenceDiagramPreview());

        useCaseDiagramCard = createDiagramTypeCard(UML_diagrame.diagrameCasUtilisation,
                "Représenter les interacations de l'utilisateur avec le systeme et ses fonctionnalités",
                createUseCaseDiagramPreview());

        // Add diagram type cards to panel
        diagramCardsPanel.add(classDiagramCard);
        diagramCardsPanel.add(sequenceDiagramCard);
        diagramCardsPanel.add(useCaseDiagramCard);

        // Set initial selection
        updateCardSelection(UML_diagrame.diagrameClass);

        // Add diagram cards panel to home panel
        homePanel.add(diagramCardsPanel, BorderLayout.SOUTH);

        return homePanel;
    }

    private void drawModernGridBackground(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw subtle grid - adjust grid size based on panel width
        int gridSize = 20;
        g2d.setColor(GRID_COLOR);

        // Draw vertical lines
        for (int x = gridSize; x < width; x += gridSize) {
            g2d.drawLine(x, 0, x, height);
        }

        // Draw horizontal lines
        for (int y = gridSize; y < height; y += gridSize) {
            g2d.drawLine(0, y, width, y);
        }

        g2d.dispose();
    }

    private JPanel createDiagramTypeCard(UML_diagrame title, String description, Icon previewIcon) {
        // Use GridBagLayout for better control over component placement
        JPanel card = new JPanel(new GridBagLayout());
        card.setBackground(CARD_BG_COLOR);
        card.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(24, 24, 24, 24)
        ));
        card.setName(title.value);  // Set name for identification

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Add preview icon
        JLabel iconLabel = new JLabel(previewIcon);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.insets = new Insets(0, 0, 16, 0);
        card.add(iconLabel, gbc);

        // Add title
        JLabel titleLabel = new JLabel(title.value, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        gbc.insets = new Insets(0, 0, 12, 0);
        card.add(titleLabel, gbc);

        // Add description
        JTextArea descLabel = new JTextArea(description);
        descLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        descLabel.setForeground(LIGHT_TEXT_COLOR);
        descLabel.setLineWrap(true);
        descLabel.setWrapStyleWord(true);
        descLabel.setEditable(false);
        descLabel.setBackground(CARD_BG_COLOR);
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        gbc.insets = new Insets(0, 0, 0, 0);
        card.add(descLabel, gbc);

        // Make the card clickable
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Add interactive effects
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (!title.equals(currentDiagrame)) {
                    // Change background color
                    card.setBackground(HOVER_COLOR);
                    descLabel.setBackground(HOVER_COLOR);

                    // Add a subtle scale effect
                    card.setBorder(BorderFactory.createCompoundBorder(
                            new HoverShadowBorder(),
                            new EmptyBorder(24, 24, 24, 24)
                    ));

                    // Change title color to indicate interactivity
                    titleLabel.setForeground(PRIMARY_COLOR);

                    // Add a subtle transform effect (slight scale)
                    card.setSize(card.getWidth() + 2, card.getHeight() + 2);
                    card.revalidate();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (!title.equals(currentDiagrame)) {
                    // Reset background color
                    card.setBackground(CARD_BG_COLOR);
                    descLabel.setBackground(CARD_BG_COLOR);

                    // Reset border
                    card.setBorder(BorderFactory.createCompoundBorder(
                            new ShadowBorder(),
                            new EmptyBorder(24, 24, 24, 24)
                    ));

                    // Reset title color
                    titleLabel.setForeground(TEXT_COLOR);

                    // Reset size
                    card.setSize(card.getWidth() - 2, card.getHeight() - 2);
                    card.revalidate();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                // Add pressed effect
                if (!title.equals(currentDiagrame)) {
                    card.setBackground(new Color(220, 230, 240));
                    descLabel.setBackground(new Color(220, 230, 240));
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // Reset from pressed effect if not selected
                if (!title.equals(currentDiagrame)) {
                    card.setBackground(HOVER_COLOR);
                    descLabel.setBackground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                selectDiagramType(title);

                // Update the selection instruction to show the user what to do next
                selectionInstructionLabel.setText("Mainetenant cliquer sur le diagramme ci-dessus pour commencer");

                // Add a visual feedback animation
                Timer feedbackTimer = new Timer(50, event -> {
                    card.setBackground(new Color(200, 230, 255));
                    descLabel.setBackground(new Color(200, 230, 255));
                });
                feedbackTimer.setRepeats(false);
                feedbackTimer.setInitialDelay(100);
                feedbackTimer.start();
            }
        });

        return card;
    }

    private void selectDiagramType(UML_diagrame diagramType) {
        // Update selected diagram type
        currentDiagrame = diagramType;

        // Update instruction label with interactive message
        instructionLabel.setText("Cliquer sur le ci_dessus diargamme pour commencer");

        // Reset pulsing animation
        pulseAlpha = 1.0f;
        pulseIncreasing = false;

        // Update document button icon based on selected diagram type
        updateSelectedDiagramIcon();

        // Update card selection without moving them
        updateCardSelection(diagramType);
    }

    private void updateCardSelection(UML_diagrame diagramType) {
        // Reset all cards
        classDiagramCard.setBackground(CARD_BG_COLOR);
        ((JTextArea)findComponentByClass(classDiagramCard, JTextArea.class)).setBackground(CARD_BG_COLOR);
        classDiagramCard.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(24, 24, 24, 24)
        ));
        ((JLabel)findComponentByClass(classDiagramCard, JLabel.class)).setForeground(TEXT_COLOR);

        sequenceDiagramCard.setBackground(CARD_BG_COLOR);
        ((JTextArea)findComponentByClass(sequenceDiagramCard, JTextArea.class)).setBackground(CARD_BG_COLOR);
        sequenceDiagramCard.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(24, 24, 24, 24)
        ));
        ((JLabel)findComponentByClass(sequenceDiagramCard, JLabel.class)).setForeground(TEXT_COLOR);

        useCaseDiagramCard.setBackground(CARD_BG_COLOR);
        ((JTextArea)findComponentByClass(useCaseDiagramCard, JTextArea.class)).setBackground(CARD_BG_COLOR);
        useCaseDiagramCard.setBorder(BorderFactory.createCompoundBorder(
                new ShadowBorder(),
                new EmptyBorder(24, 24, 24, 24)
        ));
        ((JLabel)findComponentByClass(useCaseDiagramCard, JLabel.class)).setForeground(TEXT_COLOR);

        // Highlight selected card
        JPanel selectedCard = null;
        switch (diagramType) {
            case UML_diagrame.diagrameClass:
                selectedCard = classDiagramCard;
                break;
            case UML_diagrame.diagrameSequence:
                selectedCard = sequenceDiagramCard;
                break;
            case UML_diagrame.diagrameCasUtilisation:
                selectedCard = useCaseDiagramCard;
                break;
        }

        if (selectedCard != null) {
            // Set background color
            selectedCard.setBackground(SELECTED_CARD_BG_COLOR);

            // Set text area background
            JTextArea textArea = (JTextArea)findComponentByClass(selectedCard, JTextArea.class);
            if (textArea != null) {
                textArea.setBackground(SELECTED_CARD_BG_COLOR);
            }

            // Set title color
            JLabel titleLabel = (JLabel)findComponentByClass(selectedCard, JLabel.class);
            if (titleLabel != null) {
                titleLabel.setForeground(PRIMARY_COLOR);
            }

            // Add a special border for selected card
            selectedCard.setBorder(BorderFactory.createCompoundBorder(
                    new SelectedShadowBorder(),
                    new EmptyBorder(24, 24, 24, 24)
            ));
        }
    }

    private Component findComponentByClass(Container container, Class<?> componentClass) {
        for (Component component : container.getComponents()) {
            if (componentClass.isInstance(component)) {
                return component;
            } else if (component instanceof Container) {
                Component found = findComponentByClass((Container) component, componentClass);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private JPanel createOpenPanel() {
        JPanel openPanel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw subtle grid background
                drawSubtleGrid(g, this.getWidth(), this.getHeight());
            }
        };
        openPanel.setBackground(SECONDARY_COLOR);

        // Use a responsive border that adjusts with panel size
        openPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = openPanel.getWidth();
                int height = openPanel.getHeight();

                // Adjust border based on panel size
                int borderSize = Math.max(20, Math.min(40, Math.min(width, height) / 20));
                openPanel.setBorder(new EmptyBorder(borderSize, borderSize, borderSize, borderSize));
            }
        });

        // Use GridBagLayout for header to make it responsive
        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Ouvrir un Diagramme", SwingConstants.LEFT);
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        gbc.insets = new Insets(0, 0, 16, 0);
        headerPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Selectionner un fichier pour poursuivre votre travail");
        subtitleLabel.setFont(SUBTITLE_FONT);
        subtitleLabel.setForeground(LIGHT_TEXT_COLOR);
        gbc.insets = new Insets(0, 0, 32, 0);
        headerPanel.add(subtitleLabel, gbc);

        openPanel.add(headerPanel, BorderLayout.NORTH);

        // File list with responsive height
        DefaultListModel<File> listModel = new DefaultListModel<>();
        JList<File> fileList = new JList<>(listModel);
        fileList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileList.setFont(BUTTON_FONT);
        fileList.setCellRenderer(new ModernListCellRenderer());
        fileList.setBackground(CARD_BG_COLOR);

        // Adjust row height based on panel size
        openPanel.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int height = openPanel.getHeight();
                int rowHeight = Math.max(50, Math.min(70, height / 10));
                fileList.setFixedCellHeight(rowHeight);
            }
        });

        JScrollPane scrollPane = new JScrollPane(fileList);
        scrollPane.setBorder(new ShadowBorder());
        scrollPane.setBackground(CARD_BG_COLOR);
        openPanel.add(scrollPane, BorderLayout.CENTER);

        // Button panel with responsive padding
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.setBorder(new EmptyBorder(24, 0, 0, 0));

        JButton openFileButton = new JButton("Parcourir les diagrammes");
        openFileButton.setFont(BUTTON_FONT);
        openFileButton.setForeground(Color.WHITE);
        openFileButton.setBackground(PRIMARY_COLOR);
        openFileButton.setBorder(new RoundedBorder(8, PRIMARY_COLOR));
        openFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        openFileButton.setFocusPainted(false);

        // Create a custom UML file icon
        Icon umlFileIcon = createUmlFileIcon(16, 16);
        openFileButton.setIcon(umlFileIcon);

        openFileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnValue = fileChooser.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                JOptionPane.showMessageDialog(null, "Diagramme ouvert: " + selectedFile.getName());
            }
        });

        openFileButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                openFileButton.setBackground(new Color(10, 88, 202));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                openFileButton.setBackground(PRIMARY_COLOR);
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                openFileButton.setBackground(new Color(7, 66, 152));
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                openFileButton.setBackground(new Color(10, 88, 202));
            }
        });

        buttonPanel.add(openFileButton);
        openPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Add just one example file
        listModel.addElement(new File("ClassDiagram.uml"));

        return openPanel;
    }

    private JPanel createRecentProjectsPanel(ActionListener openDocument) {
        JPanel panel = new JPanel(new BorderLayout(15, 15)) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw subtle grid background
                drawSubtleGrid(g, this.getWidth(), this.getHeight());
            }
        };
        panel.setBackground(SECONDARY_COLOR);
        panel.setBorder(new EmptyBorder(30, 30, 30, 30));

        // Header panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        headerPanel.setBorder(new EmptyBorder(0, 0, 20, 0));

        JLabel titleLabel = new JLabel("Projets recents");
        titleLabel.setFont(TITLE_FONT);
        titleLabel.setForeground(TEXT_COLOR);
        headerPanel.add(titleLabel, BorderLayout.WEST);

        JButton newProjectButton = new JButton("Nouveau Projet");
        newProjectButton.setFont(BUTTON_FONT);
        newProjectButton.setForeground(Color.WHITE);
        newProjectButton.setBackground(PRIMARY_COLOR);
        newProjectButton.setBorder(new RoundedBorder(8, PRIMARY_COLOR));
        newProjectButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        newProjectButton.setFocusPainted(false);
        newProjectButton.addActionListener(openDocument);

        newProjectButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                newProjectButton.setBackground(new Color(10, 88, 202));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                newProjectButton.setBackground(PRIMARY_COLOR);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                newProjectButton.setBackground(new Color(7, 66, 152));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                newProjectButton.setBackground(new Color(10, 88, 202));
            }
        });

        headerPanel.add(newProjectButton, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);

        // Projects grid - just one example
        JPanel projectsGrid = new JPanel(new GridLayout(1, 1, 20, 20));
        projectsGrid.setOpaque(false);

        // Add just one sample project
        projectsGrid.add(createProjectCard("Diagramme de CLasse", "Derniere modification: Aujourd'hui", UML_diagrame.diagrameClass, openDocument));

        JScrollPane scrollPane = new JScrollPane(projectsGrid);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createProjectCard(String title, String lastEdited, UML_diagrame diagramType, ActionListener openAction) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(CARD_BG_COLOR);
        card.setBorder(new ShadowBorder());
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Preview panel
        JPanel previewPanel = new JPanel(new BorderLayout());
        previewPanel.setBackground(new Color(245, 247, 250));
        previewPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(222, 226, 230)));
        previewPanel.setPreferredSize(new Dimension(0, 120));

        // Add appropriate icon based on diagram type
        Icon previewIcon = null;
        switch (diagramType) {
            case UML_diagrame.diagrameClass:
                previewIcon = createClassDiagramPreview();
                break;
            case UML_diagrame.diagrameSequence:
                previewIcon = createSequenceDiagramPreview();
                break;
            case UML_diagrame.diagrameCasUtilisation:
                previewIcon = createUseCaseDiagramPreview();
                break;
        }

        JLabel previewLabel = new JLabel(previewIcon);
        previewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        previewPanel.add(previewLabel, BorderLayout.CENTER);

        // Info panel
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(CARD_BG_COLOR);
        infoPanel.setBorder(new EmptyBorder(12, 16, 12, 16));

        JLabel titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel dateLabel = new JLabel(lastEdited);
        dateLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dateLabel.setForeground(LIGHT_TEXT_COLOR);
        dateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dateLabel.setBorder(new EmptyBorder(4, 0, 0, 0));

        infoPanel.add(titleLabel);
        infoPanel.add(dateLabel);

        card.add(previewPanel, BorderLayout.CENTER);
        card.add(infoPanel, BorderLayout.SOUTH);

        // Add hover effect
        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBorder(new HoverShadowBorder());
                card.setBackground(HOVER_COLOR);
                infoPanel.setBackground(HOVER_COLOR);
                titleLabel.setForeground(PRIMARY_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBorder(new ShadowBorder());
                card.setBackground(CARD_BG_COLOR);
                infoPanel.setBackground(CARD_BG_COLOR);
                titleLabel.setForeground(TEXT_COLOR);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                card.setBackground(new Color(220, 230, 240));
                infoPanel.setBackground(new Color(220, 230, 240));
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                card.setBackground(HOVER_COLOR);
                infoPanel.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                openAction.actionPerformed(new ActionEvent(card, ActionEvent.ACTION_PERFORMED, title));
            }
        });

        return card;
    }

    private static void drawSubtleGrid(Graphics g, int width, int height) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw grid - adjust grid size based on panel width
        int gridSize = 20;
        g2d.setColor(GRID_COLOR);

        // Draw vertical lines
        for (int x = gridSize; x < width; x += gridSize) {
            g2d.drawLine(x, 0, x, height);
        }

        // Draw horizontal lines
        for (int y = gridSize; y < height; y += gridSize) {
            g2d.drawLine(0, y, width, y);
        }

        g2d.dispose();
    }

    // Create a UML icon for the title bar
    private static Icon createUmlIcon(int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw "UML" text
                g2d.setColor(Color.WHITE);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, height * 2/3));
                FontMetrics fm = g2d.getFontMetrics();
                String text = "UML";
                int textX = x + (width - fm.stringWidth(text)) / 2;
                int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
                g2d.drawString(text, textX, textY);

                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }

    // Create a UML file icon
    private static Icon createUmlFileIcon(int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw file shape
                g2d.setColor(Color.WHITE);
                g2d.fillRect(x, y, width * 3/4, height);

                // Draw folded corner
                int[] xPoints = {x + width * 3/4, x + width * 3/4, x + width};
                int[] yPoints = {y, y + height/4, y + height/4};
                g2d.fillPolygon(xPoints, yPoints, 3);

                // Draw "UML" text
                g2d.setColor(PRIMARY_COLOR);
                g2d.setFont(new Font("Segoe UI", Font.BOLD, height/3));
                g2d.drawString("UML", x + 1, y + height * 2/3);

                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }

    // Create a class diagram large icon
    private static Icon createClassDiagramLarge(int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw paper shadow
                g2d.setColor(new Color(220, 220, 220));
                g2d.fillRoundRect(x + 8, y + 8, width - 16, height - 16, 16, 16);

                // Draw paper background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(x, y, width - 2, height - 2, 16, 16);

                // Draw paper border
                g2d.setColor(new Color(230, 230, 230));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(x, y, width - 2, height - 2, 16, 16);

                // Draw a class diagram
                int margin = width / 8;

                // Class 1 (top)
                int class1Width = width - 2 * margin;
                int class1Height = height / 4;
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(x + margin, y + margin, class1Width, class1Height);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(x + margin, y + margin, class1Width, class1Height);

                // Class 1 compartments
                g2d.drawLine(x + margin, y + margin + class1Height/3,
                             x + margin + class1Width, y + margin + class1Height/3);
                g2d.drawLine(x + margin, y + margin + 2*class1Height/3,
                             x + margin + class1Width, y + margin + 2*class1Height/3);

                // Class 2 (bottom left)
                int class2Width = (width - 2 * margin - 20) / 2;
                int class2Height = height / 4;
                int class2Y = y + height - margin - class2Height;
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(x + margin, class2Y, class2Width, class2Height);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(x + margin, class2Y, class2Width, class2Height);

                // Class 2 compartments
                g2d.drawLine(x + margin, class2Y + class2Height/3,
                             x + margin + class2Width, class2Y + class2Height/3);
                g2d.drawLine(x + margin, class2Y + 2*class2Height/3,
                             x + margin + class2Width, class2Y + 2*class2Height/3);

                // Class 3 (bottom right)
                int class3Width = class2Width;
                int class3Height = class2Height;
                int class3X = x + margin + class2Width + 20;
                int class3Y = class2Y;
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(class3X, class3Y, class3Width, class3Height);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(class3X, class3Y, class3Width, class3Height);

                // Class 3 compartments
                g2d.drawLine(class3X, class3Y + class3Height/3,
                             class3X + class3Width, class3Y + class3Height/3);
                g2d.drawLine(class3X, class3Y + 2*class3Height/3,
                             class3X + class3Width, class3Y + 2*class3Height/3);

                // Draw inheritance lines
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(1.5f));

                // Middle point for inheritance
                int middleX = x + width / 2;
                int middleY = y + margin + class1Height + (class2Y - (y + margin + class1Height)) / 2;

                // Line from Class 1 to middle
                g2d.drawLine(middleX, y + margin + class1Height, middleX, middleY);

                // Line from middle to Class 2
                g2d.drawLine(middleX, middleY, x + margin + class2Width/2, middleY);
                g2d.drawLine(x + margin + class2Width/2, middleY, x + margin + class2Width/2, class2Y);

                // Line from middle to Class 3
                g2d.drawLine(middleX, middleY, class3X + class3Width/2, middleY);
                g2d.drawLine(class3X + class3Width/2, middleY, class3X + class3Width/2, class3Y);

                // Draw inheritance arrows
                int arrowSize = Math.max(6, Math.min(10, width / 20));

                // Arrow for Class 2
                int[] xPoints2 = {x + margin + class2Width/2, x + margin + class2Width/2 - arrowSize/2, x + margin + class2Width/2 + arrowSize/2};
                int[] yPoints2 = {class2Y, class2Y - arrowSize, class2Y - arrowSize};
                g2d.fillPolygon(xPoints2, yPoints2, 3);

                // Arrow for Class 3
                int[] xPoints3 = {class3X + class3Width/2, class3X + class3Width/2 - arrowSize/2, class3X + class3Width/2 + arrowSize/2};
                int[] yPoints3 = {class3Y, class3Y - arrowSize, class3Y - arrowSize};
                g2d.fillPolygon(xPoints3, yPoints3, 3);

                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }

    // Create a sequence diagram large icon
    private static Icon createSequenceDiagramLarge(int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw paper shadow
                g2d.setColor(new Color(220, 220, 220));
                g2d.fillRoundRect(x + 8, y + 8, width - 16, height - 16, 16, 16);

                // Draw paper background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(x, y, width - 2, height - 2, 16, 16);

                // Draw paper border
                g2d.setColor(new Color(230, 230, 230));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(x, y, width - 2, height - 2, 16, 16);

                // Draw a sequence diagram
                int margin = width / 8;

                // Define lifeline positions
                int lifeline1X = x + margin + 20;
                int lifeline2X = x + width/2;
                int lifeline3X = x + width - margin - 20;

                // Draw object boxes
                int boxWidth = width/5;
                int boxHeight = height/8;

                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(lifeline1X - boxWidth/2, y + margin, boxWidth, boxHeight);
                g2d.fillRect(lifeline2X - boxWidth/2, y + margin, boxWidth, boxHeight);
                g2d.fillRect(lifeline3X - boxWidth/2, y + margin, boxWidth, boxHeight);

                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(lifeline1X - boxWidth/2, y + margin, boxWidth, boxHeight);
                g2d.drawRect(lifeline2X - boxWidth/2, y + margin, boxWidth, boxHeight);
                g2d.drawRect(lifeline3X - boxWidth/2, y + margin, boxWidth, boxHeight);

                // Draw lifelines
                g2d.setColor(new Color(200, 200, 200));
                g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f, 5.0f}, 0.0f));
                g2d.drawLine(lifeline1X, y + margin + boxHeight, lifeline1X, y + height - margin);
                g2d.drawLine(lifeline2X, y + margin + boxHeight, lifeline2X, y + height - margin);
                g2d.drawLine(lifeline3X, y + margin + boxHeight, lifeline3X, y + height - margin);

                // Draw activation bars
                int barWidth = Math.max(8, Math.min(12, width / 20));
                g2d.setColor(new Color(240, 248, 255));
                g2d.setStroke(new BasicStroke(1.0f));

                // Activation bar for lifeline 1
                int activation1Start = y + margin + boxHeight + 20;
                int activation1End = y + height - margin - 20;
                g2d.fillRect(lifeline1X - barWidth/2, activation1Start, barWidth, activation1End - activation1Start);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(lifeline1X - barWidth/2, activation1Start, barWidth, activation1End - activation1Start);

                // Activation bar for lifeline 2
                int activation2Start = y + margin + boxHeight + 40;
                int activation2End = y + height - margin - 40;
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(lifeline2X - barWidth/2, activation2Start, barWidth, activation2End - activation2Start);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(lifeline2X - barWidth/2, activation2Start, barWidth, activation2End - activation2Start);

                // Activation bar for lifeline 3
                int activation3Start = y + margin + boxHeight + 60;
                int activation3End = y + height - margin - 60;
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(lifeline3X - barWidth/2, activation3Start, barWidth, activation3End - activation3Start);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(lifeline3X - barWidth/2, activation3Start, barWidth, activation3End - activation3Start);

                // Draw messages
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(1.5f));

                // Message 1: User -> Controller
                int message1Y = activation1Start + 20;
                g2d.drawLine(lifeline1X + barWidth/2, message1Y, lifeline2X - barWidth/2, message1Y);
                drawArrowhead(g2d, lifeline1X + barWidth/2, lifeline2X - barWidth/2, message1Y);

                // Message 2: Controller -> Database
                int message2Y = activation2Start + 30;
                g2d.drawLine(lifeline2X + barWidth/2, message2Y, lifeline3X - barWidth/2, message2Y);
                drawArrowhead(g2d, lifeline2X + barWidth/2, lifeline3X - barWidth/2, message2Y);

                // Message 3: Database -> Controller (return)
                int message3Y = activation3End - 30;
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f, 5.0f}, 0.0f));
                g2d.drawLine(lifeline3X - barWidth/2, message3Y, lifeline2X + barWidth/2, message3Y);
                drawArrowhead(g2d, lifeline3X - barWidth/2, lifeline2X + barWidth/2, message3Y);

                // Message 4: Controller -> User (return)
                int message4Y = activation2End - 20;
                g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{5.0f, 5.0f}, 0.0f));
                g2d.drawLine(lifeline2X - barWidth/2, message4Y, lifeline1X + barWidth/2, message4Y);
                drawArrowhead(g2d, lifeline2X - barWidth/2, lifeline1X + barWidth/2, message4Y);

                g2d.dispose();
            }

            private void drawArrowhead(Graphics2D g2d, int x1, int x2, int y) {
                int arrowSize = 8;
                int direction = (x2 > x1) ? 1 : -1;

                int[] xPoints = {x2, x2 - direction * arrowSize, x2 - direction * arrowSize};
                int[] yPoints = {y, y - arrowSize/2, y + arrowSize/2};
                g2d.fillPolygon(xPoints, yPoints, 3);
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }

    // Create a use case diagram large icon
    private static Icon createUseCaseDiagramLarge(int width, int height) {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Draw paper shadow
                g2d.setColor(new Color(220, 220, 220));
                g2d.fillRoundRect(x + 8, y + 8, width - 16, height - 16, 16, 16);

                // Draw paper background
                g2d.setColor(Color.WHITE);
                g2d.fillRoundRect(x, y, width - 2, height - 2, 16, 16);

                // Draw paper border
                g2d.setColor(new Color(230, 230, 230));
                g2d.setStroke(new BasicStroke(1.5f));
                g2d.drawRoundRect(x, y, width - 2, height - 2, 16, 16);

                // Draw a use case diagram
                int margin = width / 8;

                // Draw system boundary
                g2d.setColor(new Color(200, 200, 200));
                g2d.drawRoundRect(x + margin + 30, y + margin, width - 2*margin - 60, height - 2*margin, 20, 20);

                // Draw use cases
                int useCaseWidth = width/3;
                int useCaseHeight = height/8;

                // Use case 1
                int useCase1X = x + width/2;
                int useCase1Y = y + margin + 50;
                g2d.setColor(new Color(255, 240, 245));
                g2d.fillOval(useCase1X - useCaseWidth/2, useCase1Y, useCaseWidth, useCaseHeight);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawOval(useCase1X - useCaseWidth/2, useCase1Y, useCaseWidth, useCaseHeight);

                // Use case 2
                int useCase2X = x + width/2;
                int useCase2Y = y + margin + 50 + useCaseHeight + 20;
                g2d.setColor(new Color(255, 240, 245));
                g2d.fillOval(useCase2X - useCaseWidth/2, useCase2Y, useCaseWidth, useCaseHeight);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawOval(useCase2X - useCaseWidth/2, useCase2Y, useCaseWidth, useCaseHeight);

                // Use case 3
                int useCase3X = x + width/2;
                int useCase3Y = y + margin + 50 + 2*(useCaseHeight + 20);
                g2d.setColor(new Color(255, 240, 245));
                g2d.fillOval(useCase3X - useCaseWidth/2, useCase3Y, useCaseWidth, useCaseHeight);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawOval(useCase3X - useCaseWidth/2, useCase3Y, useCaseWidth, useCaseHeight);

                // Draw actors
                int actorSize = Math.max(15, Math.min(20, width / 15));

                // Actor 1 (left)
                int actor1X = x + margin;
                int actor1Y = y + height/2 - actorSize;
                drawActor(g2d, actor1X, actor1Y, actorSize);

                // Actor 2 (right)
                int actor2X = x + width - margin;
                int actor2Y = y + height/2 - actorSize;
                drawActor(g2d, actor2X, actor2Y, actorSize);

                // Draw connections
                g2d.setColor(PRIMARY_COLOR);
                g2d.setStroke(new BasicStroke(1.5f));

                // User connections
                g2d.drawLine(actor1X + actorSize/2, actor1Y + actorSize, useCase1X - useCaseWidth/2, useCase1Y + useCaseHeight/2);
                g2d.drawLine(actor1X + actorSize/2, actor1Y + actorSize, useCase2X - useCaseWidth/2, useCase2Y + useCaseHeight/2);

                // Admin connections
                g2d.drawLine(actor2X - actorSize/2, actor2Y + actorSize, useCase1X + useCaseWidth/2, useCase1Y + useCaseHeight/2);
                g2d.drawLine(actor2X - actorSize/2, actor2Y + actorSize, useCase2X + useCaseWidth/2, useCase2Y + useCaseHeight/2);
                g2d.drawLine(actor2X - actorSize/2, actor2Y + actorSize, useCase3X + useCaseWidth/2, useCase3Y + useCaseHeight/2);

                g2d.dispose();
            }

            private void drawActor(Graphics2D g2d, int x, int y, int size) {
                // Head
                g2d.setColor(new Color(240, 240, 240));
                g2d.fillOval(x - size/4, y, size/2, size/2);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawOval(x - size/4, y, size/2, size/2);

                // Body
                g2d.drawLine(x, y + size/2, x, y + size*3/2);

                // Arms
                g2d.drawLine(x - size/2, y + size, x + size/2, y + size);

                // Legs
                g2d.drawLine(x, y + size*3/2, x - size/2, y + size*2);
                g2d.drawLine(x, y + size*3/2, x + size/2, y + size*2);
            }

            @Override
            public int getIconWidth() {
                return width;
            }

            @Override
            public int getIconHeight() {
                return height;
            }
        };
    }

    // Create a class diagram preview icon
    private static Icon createClassDiagramPreview() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = 120;
                int height = 80;

                // Draw background
                g2d.setColor(new Color(248, 249, 250));
                g2d.fillRect(x, y, width, height);

                // Draw class boxes
                int classWidth = width / 3;
                int classHeight = height / 3;

                // Class 1
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(x + 10, y + 10, classWidth, classHeight);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(x + 10, y + 10, classWidth, classHeight);

                // Class 2
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(x + width - classWidth - 10, y + 10, classWidth, classHeight);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(x + width - classWidth - 10, y + 10, classWidth, classHeight);

                // Class 3
                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(x + width/2 - classWidth/2, y + height - classHeight - 10, classWidth, classHeight);
                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(x + width/2 - classWidth/2, y + height - classHeight - 10, classWidth, classHeight);

                // Draw relationships
                g2d.setColor(PRIMARY_COLOR);

                // Inheritance
                g2d.drawLine(x + 10 + classWidth/2, y + 10 + classHeight,
                             x + width/2, y + height - classHeight - 10);

                // Association
                g2d.drawLine(x + width - classWidth - 10 + classWidth/2, y + 10 + classHeight,
                             x + width/2, y + height - classHeight - 10);

                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return 120;
            }

            @Override
            public int getIconHeight() {
                return 80;
            }
        };
    }

    // Create a sequence diagram preview icon
    private static Icon createSequenceDiagramPreview() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = 120;
                int height = 80;

                // Draw background
                g2d.setColor(new Color(248, 249, 250));
                g2d.fillRect(x, y, width, height);

                // Draw lifelines
                int lifeline1X = x + width/4;
                int lifeline2X = x + width/2;
                int lifeline3X = x + 3*width/4;

                // Draw object boxes
                int boxWidth = width/5;
                int boxHeight = height/6;

                g2d.setColor(new Color(240, 248, 255));
                g2d.fillRect(lifeline1X - boxWidth/2, y + 5, boxWidth, boxHeight);
                g2d.fillRect(lifeline2X - boxWidth/2, y + 5, boxWidth, boxHeight);
                g2d.fillRect(lifeline3X - boxWidth/2, y + 5, boxWidth, boxHeight);

                g2d.setColor(PRIMARY_COLOR);
                g2d.drawRect(lifeline1X - boxWidth/2, y + 5, boxWidth, boxHeight);
                g2d.drawRect(lifeline2X - boxWidth/2, y + 5, boxWidth, boxHeight);
                g2d.drawRect(lifeline3X - boxWidth/2, y + 5, boxWidth, boxHeight);

                // Draw lifelines
                g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, new float[]{2.0f, 2.0f}, 0.0f));
                g2d.drawLine(lifeline1X, y + 5 + boxHeight, lifeline1X, y + height - 5);
                g2d.drawLine(lifeline2X, y + 5 + boxHeight, lifeline2X, y + height - 5);
                g2d.drawLine(lifeline3X, y + 5 + boxHeight, lifeline3X, y + height - 5);

                // Draw messages
                g2d.setStroke(new BasicStroke(1.0f));
                g2d.setColor(PRIMARY_COLOR);

                // Message 1
                g2d.drawLine(lifeline1X, y + 30, lifeline2X, y + 30);
                drawArrowhead(g2d, lifeline1X, lifeline2X, y + 30);

                // Message 2
                g2d.drawLine(lifeline2X, y + 45, lifeline3X, y + 45);
                drawArrowhead(g2d, lifeline2X, lifeline3X, y + 45);

                // Message 3
                g2d.drawLine(lifeline3X, y + 60, lifeline1X, y + 60);
                drawArrowhead(g2d, lifeline3X, lifeline1X, y + 60);

                g2d.dispose();
            }

            private void drawArrowhead(Graphics2D g2d, int x1, int x2, int y) {
                int arrowSize = 5;
                int direction = (x2 > x1) ? 1 : -1;

                int[] xPoints = {x2, x2 - direction * arrowSize, x2 - direction * arrowSize};
                int[] yPoints = {y, y - arrowSize/2, y + arrowSize/2};
                g2d.fillPolygon(xPoints, yPoints, 3);
            }

            @Override
            public int getIconWidth() {
                return 120;
            }

            @Override
            public int getIconHeight() {
                return 80;
            }
        };
    }

    // Create a use case diagram preview icon
    private static Icon createUseCaseDiagramPreview() {
        return new Icon() {
            @Override
            public void paintIcon(Component c, Graphics g, int x, int y) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int width = 120;
                int height = 80;

                // Draw background
                g2d.setColor(new Color(248, 249, 250));
                g2d.fillRect(x, y, width, height);

                // Draw system boundary
                g2d.setColor(new Color(200, 200, 200));
                g2d.drawRoundRect(x + width/4, y + 5, width/2, height - 10, 10, 10);

                // Draw use cases
                g2d.setColor(new Color(255, 240, 245));
                g2d.fillOval(x + width/3, y + 15, width/3, height/5);
                g2d.fillOval(x + width/3, y + height - 15 - height/5, width/3, height/5);

                g2d.setColor(PRIMARY_COLOR);
                g2d.drawOval(x + width/3, y + 15, width/3, height/5);
                g2d.drawOval(x + width/3, y + height - 15 - height/5, width/3, height/5);

                // Draw actor
                int actorX = x + 10;
                int actorY = y + height/2;

                // Head
                g2d.setColor(PRIMARY_COLOR);
                g2d.fillOval(actorX - 3, actorY - 10, 6, 6);

                // Body
                g2d.drawLine(actorX, actorY - 4, actorX, actorY + 4);

                // Arms
                g2d.drawLine(actorX - 5, actorY, actorX + 5, actorY);

                // Legs
                g2d.drawLine(actorX, actorY + 4, actorX - 3, actorY + 10);
                g2d.drawLine(actorX, actorY + 4, actorX + 3, actorY + 10);

                // Draw connections
                g2d.drawLine(actorX + 5, actorY, x + width/3, y + 15 + height/10);
                g2d.drawLine(actorX + 5, actorY, x + width/3, y + height - 15 - height/5 + height/10);

                g2d.dispose();
            }

            @Override
            public int getIconWidth() {
                return 120;
            }

            @Override
            public int getIconHeight() {
                return 80;
            }
        };
    }

    // Custom renderer for the file list
    private static class ModernListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

            if (value instanceof File) {
                File file = (File) value;
                label.setText(file.getName());

                // Use custom UML file icon
                label.setIcon(createUmlFileIcon(24, 24));

                // Add file info
                String info = "Derniere modification: Aujourd'hui";
                label.setText("<html><b>" + file.getName() + "</b><br><font size='2' color='gray'>" + info + "</font></html>");

                label.setBorder(new EmptyBorder(12, 16, 12, 16));
            }

            if (isSelected) {
                label.setBackground(HOVER_COLOR);
                label.setForeground(TEXT_COLOR);
            } else {
                label.setBackground(CARD_BG_COLOR);
            }

            return label;
        }
    }

    // Custom rounded border for modern look
    private static class RoundedBorder extends EmptyBorder {
        private final int radius;
        private final Color color;

        public RoundedBorder(int radius, Color color) {
            super(radius, radius, radius, radius);
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(color);
            g2.draw(new RoundRectangle2D.Double(x, y, width - 1, height - 1, radius, radius));
            g2.dispose();
        }
    }

    // Custom shadow border for cards
    private static class ShadowBorder extends EmptyBorder {
        public ShadowBorder() {
            super(5, 5, 5, 5);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw shadow
            for (int i = 0; i < 4; i++) {
                g2.setColor(new Color(0, 0, 0, 8 - i * 2));
                g2.drawRoundRect(x + i, y + i, width - 1 - i*2, height - 1 - i*2, 12, 12);
            }

            // Draw border
            g2.setColor(new Color(230, 230, 230));
            g2.drawRoundRect(x, y, width - 1, height - 1, 12, 12);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }
    }

    // Custom hover shadow border for cards
    private static class HoverShadowBorder extends EmptyBorder {
        public HoverShadowBorder() {
            super(5, 5, 5, 5);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw shadow
            for (int i = 0; i < 6; i++) {
                g2.setColor(new Color(0, 0, 0, 10 - i * 1));
                g2.drawRoundRect(x + i, y + i, width - 1 - i*2, height - 1 - i*2, 12, 12);
            }

            // Draw border
            g2.setColor(new Color(200, 200, 200));
            g2.drawRoundRect(x, y, width - 1, height - 1, 12, 12);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }
    }

    // Custom selected shadow border for cards
    private static class SelectedShadowBorder extends EmptyBorder {
        public SelectedShadowBorder() {
            super(5, 5, 5, 5);
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw glow
            for (int i = 0; i < 5; i++) {
                g2.setColor(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 10 - i * 2));
                g2.drawRoundRect(x + i, y + i, width - 1 - i*2, height - 1 - i*2, 12, 12);
            }

            // Draw border
            g2.setColor(PRIMARY_COLOR);
            g2.drawRoundRect(x, y, width - 1, height - 1, 12, 12);

            g2.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(5, 5, 5, 5);
        }
    }

    // Custom border for selected button
    private static class SelectedButtonBorder extends EmptyBorder {
        private final Color color;
        private final int thickness;

        public SelectedButtonBorder(Color color, int thickness) {
            super(thickness, thickness, thickness, thickness);
            this.color = color;
            this.thickness = thickness;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Draw glow effect
            for (int i = 0; i < thickness; i++) {
                g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(),
                                     100 - i * 20));
                g2.drawRoundRect(x + i, y + i, width - 1 - i*2, height - 1 - i*2, 12, 12);
            }

            g2.dispose();
        }
    }

    public static UML_diagrame getCurrentDiagrame() {
        return currentDiagrame;
    }
}