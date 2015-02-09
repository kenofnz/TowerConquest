package blockfighter.client;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 *
 * @author Ken Kwan
 */
public class MouseHandler implements MouseListener, MouseMotionListener {

    private static LogicModule logic;

    public static void setLogic(LogicModule l) {
        logic = l;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        logic.getScreen().mouseClicked(e);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        logic.getScreen().mousePressed(e);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        logic.getScreen().mouseReleased(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        logic.getScreen().mouseEntered(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        logic.getScreen().mouseExited(e);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        logic.getScreen().mouseDragged(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        logic.getScreen().mouseMoved(e);
    }

}
