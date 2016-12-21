package blockfighter.client.entities.notification;

import blockfighter.client.AscensionClient;
import blockfighter.client.LogicModule;
import blockfighter.client.entities.items.Item;
import blockfighter.shared.Globals;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.concurrent.Callable;

public class Notification implements Callable<Notification> {

    private static LogicModule logic;
    private final byte type;
    private final int exp;
    private long startTime = 0;
    private final int duration = 5000;
    private Color colour, border;
    private Item item;

    public Notification(final int EXP) {
        this.startTime = logic.getTime();
        this.exp = EXP;
        this.type = Globals.NOTIFICATION_EXP;
    }

    public Notification(final Item i) {
        this.startTime = logic.getTime();
        this.exp = 0;
        this.item = i;
        this.type = Globals.NOTIFICATION_ITEM;
    }

    public static void init() {
        logic = AscensionClient.getLogicModule();
    }

    @Override
    public Notification call() {
        if (!isExpired()) {
            float transparency = 1f - Globals.nsToMs(logic.getTime() - this.startTime) * 1f / this.duration;
            this.colour = new Color(255, 255, 255, (int) (transparency * 255));
        }
        return this;
    }

    public boolean isExpired() {
        return Globals.nsToMs(logic.getTime() - this.startTime) >= this.duration;
    }

    public void draw(final Graphics2D g, final int x, final int y) {
        g.setFont(Globals.ARIAL_15PT);

        String output = "";
        switch (this.type) {
            case Globals.NOTIFICATION_EXP:
                output = "Gained " + Integer.toString(this.exp) + " EXP";
                break;
            case Globals.NOTIFICATION_ITEM:
                output = "Received " + item.getItemName();
                break;
        }
        g.setColor(this.colour);
        g.drawString(output, (float) x, (float) y);
    }
}
