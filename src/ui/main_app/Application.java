package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassModel;
import ui.custom_graphics.uml_components.class_diagram.classes.ClassRender;
import ui.custom_graphics.uml_components.class_diagram.interfaces.InterfaceModel;
import ui.custom_graphics.uml_components.class_diagram.interfaces.InterfaceRender;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationModel;
import ui.custom_graphics.uml_components.connect_components.associations.AssociationRender;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationModel;
import ui.custom_graphics.uml_components.connect_components.generalization.GeneralizationRender;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseModel;
import ui.custom_graphics.uml_components.use_case_diagrame.use_case.UseCaseRender;
import ui.main_app.main_board.MainBoard;
import ui.main_app.main_menu.MainMenu;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.LinkedList;

public class Application extends JFrame {
    private static final LinkedList<UMLComponent> components = new LinkedList<>();
    static JPanel main;

    public Application(){
        this.setTitle("UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(400,400));
        this.add(initMain());
    }

    private static JPanel initMain() {
        main = new JPanel();
        main.setLayout(new BoxLayout(main,BoxLayout.X_AXIS));
        main.add(new MainMenu());

        /* todo: remove the test */
            var v = new ClassRender(
                    new ClassModel(
                            "Test",
                            new String[]{"att : int"},
                            new String[]{"func: tst"}
                    )
            );
            var h = new InterfaceRender(
                    new InterfaceModel(
                            "test2",
                            new String[]{"att:"},
                            new String[]{"func"}
                    )
            );
        var k = new UseCaseRender(
                new UseCaseModel(
                        "test3"

                )
        );

        var l = new AssociationRender(
                new AssociationModel(
                        "test4"
                )
        );

        var a = new GeneralizationRender(
                new GeneralizationModel(
                        "test5"
                )
        );

            v.setPositionX(300);

            k.setPositionX(600);
            k.setPositionY(300);
            l.setPositionX(100);
            l.setPositionY(500);
            a.setPositionX(200);
            a.setPositionY(700);




            //components.add(v);
            //components.add(h);
            components.add(k);
            components.add(l);
            components.add(a);
        /* end test */

        main.add( new MainBoard(components));
    
        return main;
    }

    private void getComponentFromFile(File file){
        var interpreter = new MyInterpreter();
        interpreter.interpretFile(file,components);
    }

    public static void getModelsFromMainMenu(UMLComponent model) {
        main.remove(1);
        components.add(model);
        main.add( new MainBoard(components));
        main.repaint();

    }
}
