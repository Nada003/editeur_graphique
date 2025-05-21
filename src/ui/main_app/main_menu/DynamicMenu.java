package ui.main_app.main_menu;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassAbstract;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassDetail;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassEnumeration;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassInterface;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.custom_graphics.uml_components.use_case_diagrame.ActorModel;
import ui.custom_graphics.uml_components.use_case_diagrame.ActorRender;
import ui.main_app.history.UserAction;
import utils.UML_diagrame;
import utils.custom_list.WatchedList;
import utils.models.JButtonHelper;

public class DynamicMenu extends JPanel {
    // Modern color scheme
    private static final Color PRIMARY_COLOR = new Color(66, 133, 244);  // Blue
    private static final Color SECONDARY_COLOR = new Color(241, 243, 244);  // Light gray
    private static final Color HOVER_COLOR = new Color(232, 240, 254);  // Light blue
    private static final Color BORDER_COLOR = new Color(218, 220, 224);  // Border gray
    private static final Color TEXT_COLOR = new Color(60, 64, 67);  // Dark gray

   
    private final JButton buttonClassEnum;
    private final JButton buttonClassAbstract;
    private final JButton buttonClassInterface;
    private final JButton buttonClassDetail;
     private final JButton buttonClass;
     private final JButton buttonActor;
   


    public DynamicMenu(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, UML_diagrame currentDiagramme) {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(new EmptyBorder(15, 10, 15, 10));

        // Create title label
        JLabel titleLabel = new JLabel("Les Éléments Du Diagramme");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        titleLabel.setForeground(TEXT_COLOR);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(0, 5, 15, 5));
        add(titleLabel);

        // Create buttons for class diagram elements
        
    
        
        
        buttonClassEnum = createElementButton(currentDiagramme == UML_diagrame.diagrameClass ? "Class Enumarations" : "", "src/assets/containers2.png",
            "Ajouter une classe enumeration");

        buttonClassAbstract = createElementButton(currentDiagramme == UML_diagrame.diagrameClass ? "Class Abstraite" : "", "src/assets/containers2.png",
            "Ajouter une classe abstraite");

            buttonClassInterface = createElementButton(currentDiagramme == UML_diagrame.diagrameClass ? "Interface" : "", "src/assets/containers2.png",
            "Ajouter une interface");
             buttonClassDetail = createElementButton(currentDiagramme == UML_diagrame.diagrameClass ? "Class Details" : "", "src/assets/containers2.png",
            "Ajouter une classe détaillées");
             buttonClass = createElementButton(currentDiagramme == UML_diagrame.diagrameClass ? "Class Standars" : "", "src/assets/containers2.png",
            "Ajouter une classe standard");

            buttonActor = createElementButton(currentDiagramme == UML_diagrame.diagrameCasUtilisation ? "Acteur" : currentDiagramme == UML_diagrame.diagrameSequence ? "acteur" : "", "src/assets/actor.png",
            "Ajouter un acteur standard");


             JButtonHelper[] buttons = {new JButtonHelper(buttonClass, UML_diagrame.diagrameClass), 
                new JButtonHelper(buttonClassEnum, UML_diagrame.diagrameClass),
                new JButtonHelper(buttonClassInterface, UML_diagrame.diagrameClass),
                new JButtonHelper(buttonClassDetail, UML_diagrame.diagrameClass),
                new JButtonHelper(buttonClassAbstract, UML_diagrame.diagrameClass),
                new JButtonHelper(buttonActor, UML_diagrame.diagrameCasUtilisation),
                new JButtonHelper(buttonActor, UML_diagrame.diagrameSequence)};

                for (JButtonHelper button : buttons) {
            if (button.umlDiagrame == currentDiagramme )  {
                add(button.button);
                add(Box.createRigidArea(new Dimension(0, 10)));
            }
        }



         // Add buttons to panel with spacing
        add(Box.createVerticalGlue());

        // Add action listeners
              buttonActor.addActionListener(e -> {
            ActorModel model = new ActorModel("",new String[]{}, new String[]{});
            ActorRender render = new ActorRender(model);
            mainFlow.addElement(new UserAction("Add class", render));
            components.addElement(render);
        });
             buttonClass.addActionListener(e -> {
            ClassModel model = new ClassModel("",new String[]{}, new String[]{});
            ClassRender render = new ClassRender(model);
            mainFlow.addElement(new UserAction("Add class", render));
            components.addElement(render);
        });
          buttonClassEnum.addActionListener(e -> {
            ClassModel model = new ClassModel("",new String[]{}, new String[]{});
            ClassEnumeration render = new ClassEnumeration(model);
            mainFlow.addElement(new UserAction("Add class", render));
            components.addElement(render);
        });

        
          buttonClassAbstract.addActionListener(e -> {
            ClassModel model = new ClassModel("abstract",new String[]{}, new String[]{});
            ClassAbstract render = new ClassAbstract(model);
            mainFlow.addElement(new UserAction("Add class", render));
            components.addElement(render);
        });

        buttonClassInterface.addActionListener(e -> {
            ClassModel model = new ClassModel("",new String[]{}, new String[]{});
            ClassInterface render = new ClassInterface(model);
            mainFlow.addElement(new UserAction("Add class", render));
            components.addElement(render);
        });

         buttonClassDetail.addActionListener(e -> {
            ClassModel model = new ClassModel("class details",new String[]{}, new String[]{});
            ClassDetail render = new ClassDetail(model);
            mainFlow.addElement(new UserAction("Add class", render));
            components.addElement(render);
        });

    }

    private JButton createElementButton(String text, String iconPath, String tooltip) {
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

        // Try to load icon
        try {
            ImageIcon originalIcon = new ImageIcon(iconPath);
            Image scaledImage = originalIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            button.setIcon(new ImageIcon(scaledImage));
            button.setIconTextGap(10);
        } catch (Exception e) {
            // If icon fails to load, continue without it
        }

        // Add hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(Color.WHITE);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(SECONDARY_COLOR);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(HOVER_COLOR);
            }
        });

        return button;
    }
}
