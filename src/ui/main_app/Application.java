package ui.main_app;

import interpreter.MyInterpreter;
import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import ui.main_app.home_page.Home;
import ui.main_app.main_board.MainBoard;
import ui.main_app.main_menu.MainMenu;
import ui.main_app.main_menu.MenuExpandingListener;
import ui.main_app.main_topmenu.MainTopMenu;
import ui.main_app.main_topmenu.TopToolBar;
import utils.custom_list.ListListener;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import static ui.main_app.main_menu.MainMenu.DYNAMIC_PANEL_WIDTH;
import static ui.main_app.main_menu.MainMenu.FIXED_PANEL_WIDTH;

public class Application extends JFrame implements ListListener, MenuExpandingListener {
    private static final WatchedList<UMLComponent> components = new WatchedList<>();
    // Piles pour l'historique des actions
    private static final WatchedList<UserAction> mainFlow = new WatchedList<>();
    private static final WatchedList<UserAction> undoFlow = new WatchedList<>();
    private static File currentFile;
    private MainBoard board;
    private JPanel main;
    MainMenu menu;
    JLayeredPane layeredPane;



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
            this.add(initMain(), BorderLayout.CENTER);
            this.add(new TopToolBar(board, mainFlow, undoFlow), BorderLayout.NORTH);
            this.revalidate();
            this.repaint();
        });
        main = home;
        this.add(home);
        this.pack();
        this.setVisible(true);
    }

    private JPanel initMain() {
        JPanel main = new JPanel(new BorderLayout());

        layeredPane = new JLayeredPane();
        main.add(layeredPane, BorderLayout.CENTER);

        // Ajout du MainBoard
        board = new MainBoard(components);
        board.setBounds(0, 0, 800, 600); // Valeur initiale, mais sera redimensionnée dynamiquement
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);

        // Ajout du MainMenu
        menu = new MainMenu(components, mainFlow, undoFlow);
        menu.addListener(this);
        menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), 600); // Largeur fixe du menu
        layeredPane.add(menu, JLayeredPane.PALETTE_LAYER);



        // Gestion du redimensionnement automatique
        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = layeredPane.getWidth();
                int height = layeredPane.getHeight();

                board.setBounds(0, 0, width, height); // Board prend toute la place
                menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), height); // Menu reste à gauche avec hauteur dynamique
            }
        });

        main.add(layeredPane, BorderLayout.CENTER);
        return main;
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

    @Override
    public void doAction() {
        menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), 600); // Largeur fixe du menu

        layeredPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = layeredPane.getWidth();
                int height = layeredPane.getHeight();

                board.setBounds(0, 0, width, height); // Board prend toute la place
                menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), height); // Menu reste à gauche avec hauteur dynamique
            }
        });
    }
}