package utils;

import ui.custom_graphics.uml_components.text_and_comments.CommentRender;
import ui.main_app.main_board.MainBoard;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.Serializable;

public class CommentClickedHandel implements Serializable, MouseListener  {
    private MainBoard board;
    private final CommentRender label;

    public CommentClickedHandel(MainBoard board, CommentRender label) {
        this.board = board;
        this.label = label;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        board.setSelectedComment(label);
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }


}
