package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import ui.main_app.home_page.Home;
import ui.main_app.main_topmenu.MainTopMenu;
import ui.main_app.main_topmenu.TopToolBar;
import utils.custom_list.ListListener;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Application extends JFrame implements ListListener {
    private static final WatchedList<UMLComponent> components = new WatchedList<>();
    // Piles pour l'historique des actions
    public static final WatchedList<UserAction> mainFlow = new WatchedList<>();
    private static final WatchedList<UserAction> undoFlow = new WatchedList<>();
    private static File currentFile;
    private JPanel main;



    public Application() {
        this.setTitle("UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());
        mainFlow.addListener(this);

        JPanel home = new Home(e->{
            this.remove(main);
            this.setJMenuBar(new MainTopMenu(mainFlow, undoFlow,currentFile));
            var v = new MainPanel(components,mainFlow,undoFlow);
            this.add(v, BorderLayout.CENTER);
            this.add(new TopToolBar(v.getBoard(), mainFlow, undoFlow), BorderLayout.NORTH);
            this.revalidate();
            this.repaint();
        });
        main = home;
        this.add(home);
        this.pack();
        this.setVisible(true);
    }


    private void getComponentFromFile(File file) {
        var interpreter = new MyInterpreter();
        interpreter.interpretFile(file, components.getList());
    }

    @Override
    public void notifyListChanged() {
        if (mainFlow.isNewElementAdded()){
            // when user click redo (the removed item will return
            components.addElement(mainFlow.getList().getLast().getComponent());
        }else{
            // when user click undo remove item
            components.removeElement(components.getList().getLast());
        }

    }
}