package blockfighter.client;

import blockfighter.client.entities.items.ItemEquip;
import blockfighter.client.entities.items.ItemUpgrade;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Ken
 */
public class SaveData {

    private double[] baseStats = new double[Globals.NUM_STATS],
            totalStats = new double[Globals.NUM_STATS],
            bonusStats = new double[Globals.NUM_STATS];

    private int uniqueID;
    private String name;
    private byte saveNum;

    private ItemEquip[][] inventory = new ItemEquip[Globals.NUM_ITEM_TYPES][];

    private ItemUpgrade[] upgrades = new ItemUpgrade[100];

    private ItemEquip[] equipment = new ItemEquip[Globals.NUM_EQUIP_SLOTS];

    public SaveData(String n) {
        name = n;
        Random rng = new Random();
        uniqueID = rng.nextInt(Integer.MAX_VALUE);
        baseStats[Globals.STAT_LEVEL] = 99;
        baseStats[Globals.STAT_POWER] = 0;
        baseStats[Globals.STAT_DEFENSE] = 0;
        baseStats[Globals.STAT_SPIRIT] = 0;
        baseStats[Globals.STAT_EXP] = 1546730;
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = new ItemEquip[100];
        }

        for (int i = 0; i < upgrades.length; i++) {
            upgrades[i] = new ItemUpgrade(1, 100);
        }

        for (int i = 0; i < equipment.length; i++) {
            double[] bs = new double[Globals.NUM_STATS];
            bs[Globals.STAT_LEVEL] = 100;
            bs[Globals.STAT_POWER] = 100D * .9 + new Random().nextInt(45) - 25;
            bs[Globals.STAT_DEFENSE] = 100D * .9 + new Random().nextInt(45) - 25;
            bs[Globals.STAT_SPIRIT] = 100D * .9 + new Random().nextInt(45) - 25;
            bs[Globals.STAT_ARMOR] = new Random().nextInt((int) (100 * ItemEquip.UPGRADE_ARMOR));
            bs[Globals.STAT_CRITCHANCE] = new Random().nextInt(500) / 10000D;
            bs[Globals.STAT_CRITDMG] = new Random().nextInt(100) / 100D;
            bs[Globals.STAT_REGEN] = new Random().nextInt(1000) / 100D;

            if (i == 10) {
                equipment[i] = new ItemEquip(bs, i * 2, i * .1, 100001);
            } else {
                equipment[i] = new ItemEquip(bs, i * 2, i * .1, (i + 1) * 100000);
            }
        }
    }

    public static void saveData(byte saveNum, SaveData c) {
        byte[] data = new byte[45319];
        byte[] temp = c.name.getBytes(StandardCharsets.UTF_8);

        int pos = 0;
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += Globals.MAX_NAME_LENGTH;

        temp = Globals.intToByte(c.uniqueID);
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += temp.length;

        temp = Globals.intToByte((int) c.baseStats[Globals.STAT_LEVEL]);
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += temp.length;

        temp = Globals.intToByte((int) c.baseStats[Globals.STAT_POWER]);
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += temp.length;

        temp = Globals.intToByte((int) c.baseStats[Globals.STAT_DEFENSE]);
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += temp.length;

        temp = Globals.intToByte((int) c.baseStats[Globals.STAT_SPIRIT]);
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += temp.length;

        pos = saveItems(data, c.equipment, pos);
        for (ItemEquip[] e : c.inventory) {
            pos = saveItems(data, e, pos);
        }
        saveItems(data, c.upgrades, pos);
        try {
            FileUtils.writeByteArrayToFile(new File(saveNum + ".tcdat"), data);
        } catch (IOException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int saveItems(byte[] data, ItemUpgrade[] e, int pos) {
        for (ItemUpgrade item : e) {
            if (item == null) {
                pos += 2 * 4;
                continue;
            }
            byte[] temp;

            temp = Globals.intToByte(item.getItemCode());
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) item.getLevel());
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
        }
        return pos;
    }

    private static int saveItems(byte[] data, ItemEquip[] e, int pos) {
        for (ItemEquip item : e) {
            if (item == null) {
                pos += 11 * 4;
                continue;
            }
            byte[] temp;
            temp = Globals.intToByte(item.getItemCode());
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) item.getBaseStats()[Globals.STAT_LEVEL]);
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) item.getBaseStats()[Globals.STAT_POWER]);
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) item.getBaseStats()[Globals.STAT_DEFENSE]);
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) item.getBaseStats()[Globals.STAT_SPIRIT]);
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) item.getBaseStats()[Globals.STAT_ARMOR]);
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) (item.getBaseStats()[Globals.STAT_REGEN] * 10));
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) (item.getBaseStats()[Globals.STAT_CRITDMG] * 10000));
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) (item.getBaseStats()[Globals.STAT_CRITCHANCE] * 10000));
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte(item.getUpgrades());
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
            temp = Globals.intToByte((int) (item.getBonusMult() * 100));
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
        }
        return pos;
    }

    public static SaveData readData(byte saveNum) {
        SaveData c = new SaveData("");
        c.saveNum = saveNum;
        byte[] data, temp = new byte[Globals.MAX_NAME_LENGTH];

        try {
            data = FileUtils.readFileToByteArray(new File(saveNum + ".tcdat"));
        } catch (IOException ex) {
            return null;
        }

        int pos = 0;
        System.arraycopy(data, pos, temp, 0, temp.length);
        c.name = new String(temp, StandardCharsets.UTF_8).trim();
        pos += Globals.MAX_NAME_LENGTH;

        temp = new byte[4];
        System.arraycopy(data, pos, temp, 0, temp.length);
        c.uniqueID = Globals.bytesToInt(temp);
        pos += temp.length;

        System.arraycopy(data, pos, temp, 0, temp.length);
        c.baseStats[Globals.STAT_LEVEL] = Globals.bytesToInt(temp);
        pos += temp.length;

        System.arraycopy(data, pos, temp, 0, temp.length);
        c.baseStats[Globals.STAT_POWER] = Globals.bytesToInt(temp);
        pos += temp.length;

        System.arraycopy(data, pos, temp, 0, temp.length);
        c.baseStats[Globals.STAT_DEFENSE] = Globals.bytesToInt(temp);
        pos += temp.length;

        System.arraycopy(data, pos, temp, 0, temp.length);
        c.baseStats[Globals.STAT_SPIRIT] = Globals.bytesToInt(temp);
        pos += temp.length;

        pos = readItems(data, c.equipment, pos);
        for (ItemEquip[] e : c.inventory) {
            pos = readItems(data, e, pos);
        }
        readItems(data, c.upgrades, pos);
        c.calcStats();
        return c;
    }

    private static int readItems(byte[] data, ItemUpgrade[] e, int pos) {
        for (int i = 0; i < e.length; i++) {
            byte[] temp = new byte[4];
            int itemCode;
            int level;

            System.arraycopy(data, pos, temp, 0, temp.length);
            itemCode = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            level = Globals.bytesToInt(temp);
            pos += temp.length;

            if (!ItemUpgrade.isValidItem(itemCode)) {
                e[i] = null;
            } else {
                e[i] = new ItemUpgrade(itemCode, level);
            }
        }
        return pos;
    }

    private static int readItems(byte[] data, ItemEquip[] e, int pos) {
        for (int i = 0; i < e.length; i++) {
            double[] bs = new double[Globals.NUM_STATS];
            byte[] temp = new byte[4];
            int itemCode;
            int upgrades;
            double bMult;

            System.arraycopy(data, pos, temp, 0, temp.length);
            itemCode = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_LEVEL] = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_POWER] = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_DEFENSE] = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_SPIRIT] = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_ARMOR] = Globals.bytesToInt(temp);
            pos += temp.length;
            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_REGEN] = Globals.bytesToInt(temp) / 10D;
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_CRITDMG] = Globals.bytesToInt(temp) / 10000D;
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bs[Globals.STAT_CRITCHANCE] = Globals.bytesToInt(temp) / 10000D;
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            upgrades = Globals.bytesToInt(temp);
            pos += temp.length;

            System.arraycopy(data, pos, temp, 0, temp.length);
            bMult = Globals.bytesToInt(temp) / 100D;
            pos += temp.length;

            if (!ItemEquip.isValidItem(itemCode)) {
                e[i] = null;
            } else {
                e[i] = new ItemEquip(bs, upgrades, bMult, itemCode);
            }

        }
        return pos;
    }

    public String getPlayerName() {
        return name;
    }

    public double[] getBaseStats() {
        return baseStats;
    }

    public double[] getStats() {
        return totalStats;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    private void calcStats() {
        for (int i = 0; i < bonusStats.length; i++) {
            bonusStats[i] = 0;
            for (ItemEquip e : equipment) {
                if (i != Globals.STAT_LEVEL && e != null) {
                    bonusStats[i] += e.getStats()[i];
                }
            }
        }

        baseStats[Globals.STAT_POINTS] = baseStats[Globals.STAT_LEVEL] * Globals.STAT_PER_LEVEL
                - (baseStats[Globals.STAT_POWER]
                + baseStats[Globals.STAT_DEFENSE]
                + baseStats[Globals.STAT_SPIRIT]);

        System.arraycopy(baseStats, 0, totalStats, 0, baseStats.length);

        totalStats[Globals.STAT_POWER] = (int) (baseStats[Globals.STAT_POWER] + bonusStats[Globals.STAT_POWER]);
        totalStats[Globals.STAT_DEFENSE] = (int) (baseStats[Globals.STAT_DEFENSE] + bonusStats[Globals.STAT_DEFENSE]);
        totalStats[Globals.STAT_SPIRIT] = (int) (baseStats[Globals.STAT_SPIRIT] + bonusStats[Globals.STAT_SPIRIT]);

        totalStats[Globals.STAT_MAXHP] = Globals.calcMaxHP(totalStats[Globals.STAT_DEFENSE]);
        totalStats[Globals.STAT_MINHP] = baseStats[Globals.STAT_MAXHP];

        totalStats[Globals.STAT_MINDMG] = Globals.calcMinDmg(totalStats[Globals.STAT_POWER]);
        totalStats[Globals.STAT_MAXDMG] = Globals.calcMaxDmg(totalStats[Globals.STAT_POWER]);

        baseStats[Globals.STAT_ARMOR] = Globals.calcArmor(totalStats[Globals.STAT_DEFENSE]);
        baseStats[Globals.STAT_REGEN] = Globals.calcRegen(totalStats[Globals.STAT_SPIRIT]);
        baseStats[Globals.STAT_CRITCHANCE] = Globals.calcCritChance(totalStats[Globals.STAT_SPIRIT]);
        baseStats[Globals.STAT_CRITDMG] = Globals.calcCritDmg(totalStats[Globals.STAT_SPIRIT]);

        totalStats[Globals.STAT_ARMOR] = baseStats[Globals.STAT_ARMOR] + bonusStats[Globals.STAT_ARMOR];
        totalStats[Globals.STAT_REGEN] = baseStats[Globals.STAT_REGEN] + bonusStats[Globals.STAT_REGEN];
        totalStats[Globals.STAT_CRITCHANCE] = baseStats[Globals.STAT_CRITCHANCE] + bonusStats[Globals.STAT_CRITCHANCE];
        totalStats[Globals.STAT_CRITDMG] = baseStats[Globals.STAT_CRITDMG] + bonusStats[Globals.STAT_CRITDMG];
    }

    public ItemEquip[] getInventory(byte type) {
        return inventory[type];
    }

    public ItemEquip[] getEquip() {
        return equipment;
    }

    public ItemUpgrade[] getUpgrades() {
        return upgrades;
    }

    public double[] getBonusStats() {
        return bonusStats;
    }

    public void addStat(byte stat, int amount) {
        if (baseStats[Globals.STAT_POINTS] < amount) {
            return;
        }
        baseStats[Globals.STAT_POINTS] -= amount;
        baseStats[stat] += amount;

        calcStats();
        saveData(saveNum, this);
    }

    public void unequipItem(int type) {
        boolean offhand = false;
        if (type == Globals.ITEM_OFFHAND) {
            type = Globals.ITEM_WEAPON;
            offhand = true;
        }

        for (int i = 0; i < inventory[type].length; i++) {
            if (inventory[type][i] == null) {
                inventory[type][i] = equipment[(offhand) ? Globals.ITEM_OFFHAND : type];
                equipment[(offhand) ? Globals.ITEM_OFFHAND : type] = null;
                break;
            }
        }
        calcStats();
    }

    public void equipItem(int slot, int inventorySlot) {
        int itemType = slot;
        if (slot == Globals.ITEM_OFFHAND) {
            itemType = Globals.ITEM_WEAPON;
        }

        ItemEquip temp = inventory[itemType][inventorySlot];
        if (equipment[slot] == null) {
            inventory[itemType][inventorySlot] = null;
        } else {
            inventory[itemType][inventorySlot] = equipment[slot];
        }
        equipment[slot] = temp;
        calcStats();
    }

    public void destroyItem(int type, int slot) {
        inventory[type][slot] = null;
    }

    public void destroyItem(int slot) {
        upgrades[slot] = null;
    }

    public void destroyAll(int type) {
        for (int i = 0; i < inventory[type].length; i++) {
            inventory[type][i] = null;
        }
    }
    
    public void destroyAllUpgrade() {
        for (int i = 0; i < upgrades.length; i++) {
            upgrades[i] = null;
        }
    }
    
    public void addItem(int type, ItemEquip e) {
        for (int i = 0; i < inventory[type].length; i++) {
            if (inventory[type][i] == null) {
                inventory[type][i] = e;
                break;
            }
        }
    }

    public void addItem(int type, ItemUpgrade e) {
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == null) {
                upgrades[i] = e;
                break;
            }
        }
    }
}