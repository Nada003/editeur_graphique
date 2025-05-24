package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import ui.main_app.home_page.Home;
import ui.main_app.main_topmenu.MainTopMenu;
import ui.main_app.main_topmenu.TopToolBar;
import utils.custom_list.ListListener;
import utils.custom_list.WatchedList;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Application extends JFrame implements ListListener {
    private static final WatchedList<UMLComponent> components = new WatchedList<>();
    // Piles pour l'historique des actions
    public static final WatchedList<UserAction> mainFlow = new WatchedList<>();
    private static final WatchedList<UserAction> undoFlow = new WatchedList<>();
    private static File currentFile;
    private JPanel main;
    private final JPanel home;



    public Application() {
        this.setTitle("UMLdesign");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout());

        try {
            BufferedImage originalImage = ImageIO.read(new File("src/assets/logo.jpg"));
            Image scaledImage = originalImage.getScaledInstance(300, 300, Image.SCALE_SMOOTH);
            this.setIconImage(scaledImage);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mainFlow.addListener(this);
        home = new Home(this::navigateToMainBord);

        main = home;
        this.add(home);
        this.pack();
        this.setVisible(true);
    }

    private void navigateToMainBord(ActionEvent actionEvent) {
        this.remove(main);
        var v = new MainPanel(components,mainFlow,undoFlow,Home.getCurrentDiagrame());
        var topToolBar = new TopToolBar(v.getBoard(), mainFlow, undoFlow);

        JFrame instance = this;
        this.setJMenuBar(new MainTopMenu(mainFlow, undoFlow,currentFile,new MenuListener() {
            @Override
            public void menuSelected(MenuEvent e) {
                instance.setJMenuBar(null);
                instance.remove(topToolBar);
                instance.remove(v);
                instance.add(home,BorderLayout.CENTER);
                instance.revalidate();
                instance.repaint();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        }));
        this.add(v, BorderLayout.CENTER);
        this.add(topToolBar, BorderLayout.NORTH);
        this.revalidate();
        this.repaint();
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