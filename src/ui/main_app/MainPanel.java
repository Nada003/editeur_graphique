package ui.main_app;

import ui.custom_graphics.uml_components.UMLComponent;
import ui.main_app.history.UserAction;
import ui.main_app.main_board.MainBoard;
import ui.main_app.main_menu.MainMenu;
import ui.main_app.main_menu.MenuExpandingListener;
import utils.custom_list.WatchedList;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import static ui.main_app.main_menu.MainMenu.DYNAMIC_PANEL_WIDTH;
import static ui.main_app.main_menu.MainMenu.FIXED_PANEL_WIDTH;

public class MainPanel extends JPanel implements MenuExpandingListener {
    private JLayeredPane layeredPane;
    private static JScrollPane scrollPane;
    private static MainBoard board;
    private MainMenu menu;

    public MainPanel(WatchedList<UMLComponent> components, WatchedList<UserAction> mainFlow, WatchedList<UserAction> undoFlow) {
        super(new BorderLayout());

        layeredPane = new JLayeredPane();
        add(layeredPane, BorderLayout.CENTER);

        // Initialize board
        board = new MainBoard(components);
        board.setPreferredSize(new Dimension(1800, 1600));


        // Scroll pane
        scrollPane = new JScrollPane(board,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
                JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setBounds(0, 0, 400, 600);

        layeredPane.add(scrollPane, JLayeredPane.DEFAULT_LAYER);

        // Initialize menu
        menu = new MainMenu(components, mainFlow, undoFlow);
        menu.addListener(this);
        menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), 600);
        layeredPane.add(menu, JLayeredPane.PALETTE_LAYER);

        layeredPane.getParent().addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                int width = layeredPane.getWidth();
                int height = layeredPane.getHeight();

                scrollPane.setBounds(0, 0, width, height); // Board prend toute la place
                menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), height-20); // Menu reste à gauche avec hauteur dynamique
                menu.setHeight(height-20);
            }
        });
        
    }

    public MainBoard getBoard(){
        return board;
    }

    @Override
    public void doAction() {
        menu.setBounds(0, 0, FIXED_PANEL_WIDTH + (menu.isExpanded() ? DYNAMIC_PANEL_WIDTH : 0), menu.getHeight()); // Largeur fixe du menu
    }
}
