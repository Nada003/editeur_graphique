package ui.main_app.main_menu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.connect_components.Relation;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationModel;
import ui.custom_graphics.uml_components.connect_components.aggregations.AggregationRender;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationModel;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationRender;
import ui.custom_graphics.uml_components.connect_components.composition.CompositionModel;
import ui.custom_graphics.uml_components.connect_components.composition.CompositionRender;
import ui.custom_graphics.uml_components.connect_components.dependency.DependencyModel;
import ui.custom_graphics.uml_components.connect_components.dependency.DependencyRender;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationModel;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationRender;
import ui.custom_graphics.uml_components.connect_components.realization.RealizationModel;
import ui.custom_graphics.uml_components.connect_components.realization.RealizationRender;
import ui.custom_graphics.uml_components.sequence_diagrame.ClasslifelineModel;
import ui.custom_graphics.uml_components.sequence_diagrame.ClasslifelineRender;
import ui.custom_graphics.uml_components.sequence_diagrame.MsgretourModel;
import ui.custom_graphics.uml_components.sequence_diagrame.MsgretourRender;
import ui.custom_graphics.uml_components.sequence_diagrame.MsgsynchroneModel;
import ui.custom_graphics.uml_components.sequence_diagrame.MsgsynchroneRender;
import ui.custom_graphics.uml_components.shapes.RectangleModel;
import ui.custom_graphics.uml_components.shapes.RectangleRender;
import ui.main_app.history.UserAction;
import ui.main_app.main_board.MainBoard;
import utils.UML_diagrame;
import utils.custom_list.WatchedList;
import utils.models.JButtonHelper;

public class DynamicPanelConnect extends JPanel {
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);
    private static final Color HOVER_COLOR = new Color(232, 240, 254);
    private static final Color BORDER_COLOR = new Color(218, 220, 224);
    private static final Color TEXT_COLOR = new Color(60, 64, 67);
    private static final Color ACTIVE_COLOR = new Color(209, 231, 252);

    private final JButton associationButton;
    private final JButton generalizationButton;
    private final JButton aggregationButton;
    private final JButton compositionButton;
    private final JButton dependencyButton;
    private final JButton realizationButton;
    private final JButton extendButton;
    private final JButton includeButton;
    private final JButton lifelineButton;
    private final JButton activationbarButton;
     private final JButton msgsynchroneButton;
      private final JButton msgretourButton;


    public DynamicPanelConnect(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, UML_diagrame currentDiagramme) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 10, 15, 10));

        JLabel titleLabel = new JLabel("Types de Relations");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 5, 15, 5));
        add(titleLabel);

        associationButton = createRelationshipButton(currentDiagramme == UML_diagrame.diagrameCasUtilisation ? "Association" :
                (currentDiagramme == UML_diagrame.diagrameClass ? "Relation" : ""), "src/assets/association.png", "Connecter les classes avec une simple association");

        generalizationButton=createRelationshipButton(currentDiagramme == UML_diagrame.diagrameClass ? "héritage" : 
        (currentDiagramme == UML_diagrame.diagrameSequence ? "Message Asynchronisé" : ""), "src/assets/generalization.png", "Créer une relation d'héritage");
        aggregationButton = createRelationshipButton("Agrégation", "src/assets/aggregation.png", "créer une relation 'a-un'");
        compositionButton = createRelationshipButton("Composition", "src/assets/composition.png", "Créer une forte 'contient' relation");
        dependencyButton = createRelationshipButton("Dépendence", "src/assets/dependency.png", "Créer une 'dépend de' relation");

        realizationButton = createRelationshipButton(currentDiagramme == UML_diagrame.diagrameClass ? "Réalisation" : "", "src/assets/realization.png", "Connecter la classe à l'implémentation de l'interface");

        extendButton = createRelationshipButton(currentDiagramme == UML_diagrame.diagrameCasUtilisation ? "Extend <<extend>>" : "", "src/assets/realization.png", "Connecter la classe à l'implémentation de l'interface");

        includeButton = createRelationshipButton(currentDiagramme == UML_diagrame.diagrameCasUtilisation ? "include <<include>>" : "", "src/assets/realization.png", "Connecter la classe à l'implémentation de l'interface");

        lifelineButton = createRelationshipButton("Lingne De Vie", "src/assets/lifeline.png", "Connecter la classe à l'implémentation de l'interface");
        activationbarButton = createRelationshipButton("Barre d'Activation", "src/assets/rectangle.png","");
        msgretourButton = createRelationshipButton("Message De Retour", "src/assets/msgderetour.png", "retourner un message");
        msgsynchroneButton = createRelationshipButton("Message Synchronisé", "src/assets/msgsynchrone.png", "");
        
        JButtonHelper[] buttons = {new JButtonHelper(associationButton, UML_diagrame.diagrameClass),
                new JButtonHelper(associationButton, UML_diagrame.diagrameCasUtilisation),
                new JButtonHelper(generalizationButton, UML_diagrame.diagrameSequence),
                new JButtonHelper(msgretourButton, UML_diagrame.diagrameSequence),
                new JButtonHelper(msgsynchroneButton, UML_diagrame.diagrameSequence),

                new JButtonHelper(generalizationButton, UML_diagrame.diagrameClass),
                new JButtonHelper(aggregationButton, UML_diagrame.diagrameClass),
                new JButtonHelper(compositionButton, UML_diagrame.diagrameClass),
                new JButtonHelper(dependencyButton, UML_diagrame.diagrameClass),
                new JButtonHelper(realizationButton, UML_diagrame.diagrameClass),
                new JButtonHelper(extendButton, UML_diagrame.diagrameCasUtilisation),
                new JButtonHelper(includeButton, UML_diagrame.diagrameCasUtilisation),

                
                new JButtonHelper(lifelineButton, UML_diagrame.diagrameSequence),
                new JButtonHelper(activationbarButton, UML_diagrame.diagrameSequence),
        };

        for (JButtonHelper button : buttons) {
            if (button.umlDiagrame == currentDiagramme )  {
                add(button.button);
                add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }

        add(Box.createVerticalGlue());

         msgsynchroneButton.addActionListener(e -> {
            resetButtonStates();
            msgsynchroneButton.setBackground(ACTIVE_COLOR);
            MsgsynchroneModel model = new MsgsynchroneModel("");
            MsgsynchroneRender render = new MsgsynchroneRender(model);
            mainFlow.addElement(new UserAction("Ajouter message synchrone", render));
            components.addElement(render);
        });

        activationbarButton.addActionListener(e -> {
            resetButtonStates();
            activationbarButton.setBackground(ACTIVE_COLOR);
            RectangleModel model = new RectangleModel("");
            RectangleRender render = new RectangleRender(model);
            mainFlow.addElement(new UserAction("Ajouter rectangle", render));
            components.addElement(render);
        });

        lifelineButton.addActionListener(e -> {
            resetButtonStates();
            lifelineButton.setBackground(ACTIVE_COLOR);
            ClasslifelineModel model = new ClasslifelineModel("");
            ClasslifelineRender render = new ClasslifelineRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });
         msgretourButton.addActionListener(e -> {
            resetButtonStates();
            msgretourButton.setBackground(ACTIVE_COLOR);
            MsgretourModel model = new MsgretourModel("");
            MsgretourRender render = new MsgretourRender(model);
            mainFlow.addElement(new UserAction("Ajouter message de retour", render));
            components.addElement(render);
        });

        associationButton.addActionListener(e -> {
            resetButtonStates();
            associationButton.setBackground(ACTIVE_COLOR);
            AssociationModel model = new AssociationModel("");
            AssociationRender render = new AssociationRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });

        generalizationButton.addActionListener(e -> {
            resetButtonStates();
            generalizationButton.setBackground(ACTIVE_COLOR);
            GeneralizationModel model = new GeneralizationModel("");
            GeneralizationRender render = new GeneralizationRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });

        aggregationButton.addActionListener(e -> {
            resetButtonStates();
            aggregationButton.setBackground(ACTIVE_COLOR);
            AggregationModel model = new AggregationModel("");
            AggregationRender render = new AggregationRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });

        compositionButton.addActionListener(e -> {
            resetButtonStates();
            compositionButton.setBackground(ACTIVE_COLOR);
            CompositionModel model = new CompositionModel("");
            CompositionRender render = new CompositionRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });

        dependencyButton.addActionListener(e -> {
            resetButtonStates();
            dependencyButton.setBackground(ACTIVE_COLOR);
            DependencyModel model = new DependencyModel("");
            DependencyRender render = new DependencyRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });

        extendButton.addActionListener(e -> {
            resetButtonStates();
            realizationButton.setBackground(ACTIVE_COLOR);
            RealizationModel model = new RealizationModel("");
            RealizationRender render = new RealizationRender(model);
            mainFlow.addElement(new UserAction("Ajouter extend", render));
            components.addElement(render);
        });
        includeButton.addActionListener(e -> {
            resetButtonStates();
            realizationButton.setBackground(ACTIVE_COLOR);
            RealizationModel model = new RealizationModel("");
            RealizationRender render = new RealizationRender(model);
            mainFlow.addElement(new UserAction("Ajouter extend", render));
            components.addElement(render);
        });

        realizationButton.addActionListener(e -> {
            resetButtonStates();
            realizationButton.setBackground(ACTIVE_COLOR);
            RealizationModel model = new RealizationModel("");
            RealizationRender render = new RealizationRender(model);
            MainBoard.setRelation(new Relation(render, components));
        });

    }

    private void resetButtonStates() {
        associationButton.setBackground(Color.WHITE);
        generalizationButton.setBackground(Color.WHITE);
        aggregationButton.setBackground(Color.WHITE);
        compositionButton.setBackground(Color.WHITE);
        dependencyButton.setBackground(Color.WHITE);
        realizationButton.setBackground(Color.WHITE);
        extendButton.setBackground(Color.WHITE);
        activationbarButton.setBackground(Color.white);
        msgretourButton.setBackground(Color.white);
        msgsynchroneButton.setBackground(Color.white);
        lifelineButton.setBackground(Color.white);
    }

    private JButton createRelationshipButton(String text, String iconPath, String tooltip) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(TEXT_COLOR);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        button.setFocusPainted(false);
        button.setBorderPainted(true);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER_COLOR),
                new EmptyBorder(8, 12, 8, 12)
        ));
        button.setToolTipText(tooltip);
        button.setBackground(Color.WHITE);
        button.setHorizontalAlignment(SwingConstants.LEFT);

        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(24, 24, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setIconTextGap(10);
        } catch (Exception e) {
            System.out.println("Failed to load icon: " + iconPath);
        }

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(HOVER_COLOR);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(Color.WHITE);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(SECONDARY_COLOR);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (button.getBackground() != ACTIVE_COLOR) {
                    button.setBackground(HOVER_COLOR);
                }
            }
        });

        return button;
    }
}