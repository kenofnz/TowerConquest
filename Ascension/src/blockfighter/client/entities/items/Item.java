package blockfighter.client.entities.items;

import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public interface Item {

    public void drawIcon(Graphics2D g, int x, int y);

    public int getItemCode();

    public String getItemName();

    public void drawInfo(final Graphics2D g, final Rectangle2D.Double box);
}
