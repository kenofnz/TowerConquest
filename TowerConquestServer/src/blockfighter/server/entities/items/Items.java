package blockfighter.server.entities.items;

import blockfighter.server.Globals;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;

public class Items {

    public static final HashSet<Integer> ITEM_CODES = new HashSet<>();
    public static final HashSet<Integer> ITEM_UPGRADE_CODES = new HashSet<>();

    static {
        loadItemCodes();
    }

    public static void loadItemCodes() {
        ITEM_UPGRADE_CODES.add(100);
        try {
            InputStream itemFile = Globals.loadResourceAsStream("itemcodes.txt");
            LineIterator it = IOUtils.lineIterator(itemFile, "UTF-8");
            try {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    try {
                        int itemcode = Integer.parseInt(line);
                        ITEM_CODES.add(itemcode);
                    } catch (NumberFormatException e) {
                    }
                }
            } finally {
                LineIterator.closeQuietly(it);
            }
            Globals.log(Items.class, "Item Codes loaded: Equips: " + Arrays.toString(ITEM_CODES.toArray()) + " Upgrades: " + Arrays.toString(ITEM_UPGRADE_CODES.toArray()), Globals.LOG_TYPE_DATA, true);
        } catch (Exception e) {
            Globals.logError("Could not load item codes from data", e, true);
        }
    }

    /**
     * Get the item type of an item code.
     *
     * @param i Item Code
     * @return Byte - Item Type
     */
    public static byte getItemType(final int i) {
        if (i >= 100000 && i <= 109999) {
            // Swords
            return Globals.ITEM_WEAPON;
        } else if (i >= 110000 && i <= 119999) {
            // Shields
            return Globals.ITEM_SHIELD;
        } else if (i >= 120000 && i <= 129999) {
            // Bows
            return Globals.ITEM_BOW;
        } else if (i >= 130000 && i <= 199999) {
            return Globals.ITEM_ARROW;
        } else if (i >= 200000 && i <= 209999) {
            return Globals.ITEM_HEAD;
        } else if (i >= 300000 && i <= 309999) {
            return Globals.ITEM_CHEST;
        } else if (i >= 400000 && i <= 409999) {
            return Globals.ITEM_PANTS;
        } else if (i >= 500000 && i <= 509999) {
            return Globals.ITEM_SHOULDER;
        } else if (i >= 600000 && i <= 609999) {
            return Globals.ITEM_GLOVE;
        } else if (i >= 700000 && i <= 709999) {
            return Globals.ITEM_SHOE;
        } else if (i >= 800000 && i <= 809999) {
            return Globals.ITEM_BELT;
        } else if (i >= 900000 && i <= 909999) {
            return Globals.ITEM_RING;
        } else if (i >= 1000000 && i <= 1009999) {
            return Globals.ITEM_AMULET;
        }
        return -1;
    }
}
