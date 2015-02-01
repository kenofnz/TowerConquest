package blockfighter.client;

import blockfighter.client.entities.items.ItemEquip;
import blockfighter.client.entities.items.ItemUpgrade;

import blockfighter.client.entities.skills.*;
import java.awt.event.KeyEvent;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Ken Kwan
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

    private Skill[] hotkeys = new Skill[12];
    private Skill[] skills = new Skill[Skill.NUM_SKILLS];
    private int[] keybinds = new int[Globals.NUM_KEYBINDS];

    public SaveData(String n) {
        name = n;
        Random rng = new Random();
        uniqueID = rng.nextInt(Integer.MAX_VALUE);
        baseStats[Globals.STAT_LEVEL] = 100;
        baseStats[Globals.STAT_POWER] = 0;
        baseStats[Globals.STAT_DEFENSE] = 0;
        baseStats[Globals.STAT_SPIRIT] = 0;
        baseStats[Globals.STAT_EXP] = 0;
        baseStats[Globals.STAT_SKILLPOINTS] = 3 * baseStats[Globals.STAT_LEVEL];
        for (int i = 0; i < inventory.length; i++) {
            inventory[i] = new ItemEquip[100];
        }
        double[] bs = new double[Globals.NUM_STATS];
        bs[Globals.STAT_LEVEL] = 99999;
        bs[Globals.STAT_POWER] = 10000;

        equipment[Globals.ITEM_WEAPON] = new ItemEquip(bs, 0, 3, ItemEquip.TEMP_SWORD);
        upgrades[0] = new ItemUpgrade(1,100000);
        //initalize skill list
        skills[Skill.SWORD_CINDER] = new SkillSwordCinder();
        skills[Skill.SWORD_DRIVE] = new SkillSwordDrive();
        skills[Skill.SWORD_MULTI] = new SkillSwordMulti();
        skills[Skill.SWORD_SLASH] = new SkillSwordSlash();
        skills[Skill.SWORD_TAUNT] = new SkillSwordTaunt();
        skills[Skill.SWORD_VORPAL] = new SkillSwordVorpal();

        skills[Skill.BOW_ARC] = new SkillBowArc();
        skills[Skill.BOW_FROST] = new SkillBowFrost();
        skills[Skill.BOW_POWER] = new SkillBowPower();
        skills[Skill.BOW_RAPID] = new SkillBowRapid();
        skills[Skill.BOW_STORM] = new SkillBowStorm();
        skills[Skill.BOW_VOLLEY] = new SkillBowVolley();

        skills[Skill.SHIELD_FORTIFY] = new SkillShieldFortify();
        skills[Skill.SHIELD_IRONFORT] = new SkillShieldIron();
        skills[Skill.SHIELD_3] = new SkillShield3();
        skills[Skill.SHIELD_4] = new SkillShield4();
        skills[Skill.SHIELD_5] = new SkillShield5();
        skills[Skill.SHIELD_6] = new SkillShield6();

        skills[Skill.PASSIVE_1] = new SkillPassive1();
        skills[Skill.PASSIVE_2] = new SkillPassive2();
        skills[Skill.PASSIVE_3] = new SkillPassive3();
        skills[Skill.PASSIVE_4] = new SkillPassive4();
        skills[Skill.PASSIVE_5] = new SkillPassive5();
        skills[Skill.PASSIVE_6] = new SkillPassive6();
        skills[Skill.PASSIVE_7] = new SkillPassive7();
        skills[Skill.PASSIVE_8] = new SkillPassive8();
        skills[Skill.PASSIVE_9] = new SkillPassive9();
        skills[Skill.PASSIVE_10] = new SkillPassive10();
        skills[Skill.PASSIVE_11] = new SkillPassive11();
        skills[Skill.PASSIVE_12] = new SkillPassive12();

        keybinds[Globals.KEYBIND_SKILL1] = KeyEvent.VK_Q;
        keybinds[Globals.KEYBIND_SKILL2] = KeyEvent.VK_W;
        keybinds[Globals.KEYBIND_SKILL3] = KeyEvent.VK_E;
        keybinds[Globals.KEYBIND_SKILL4] = KeyEvent.VK_R;
        keybinds[Globals.KEYBIND_SKILL5] = KeyEvent.VK_T;
        keybinds[Globals.KEYBIND_SKILL6] = KeyEvent.VK_Y;
        keybinds[Globals.KEYBIND_SKILL7] = KeyEvent.VK_A;
        keybinds[Globals.KEYBIND_SKILL8] = KeyEvent.VK_S;
        keybinds[Globals.KEYBIND_SKILL9] = KeyEvent.VK_D;
        keybinds[Globals.KEYBIND_SKILL10] = KeyEvent.VK_F;
        keybinds[Globals.KEYBIND_SKILL11] = KeyEvent.VK_G;
        keybinds[Globals.KEYBIND_SKILL12] = KeyEvent.VK_H;

        keybinds[Globals.KEYBIND_LEFT] = KeyEvent.VK_LEFT;
        keybinds[Globals.KEYBIND_RIGHT] = KeyEvent.VK_RIGHT;
        keybinds[Globals.KEYBIND_JUMP] = KeyEvent.VK_UP;
        keybinds[Globals.KEYBIND_DOWN] = KeyEvent.VK_DOWN;
    }

    public static void saveData(byte saveNum, SaveData c) {
        byte[] data = new byte[46413];
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

        temp = Globals.intToByte((int) c.baseStats[Globals.STAT_SKILLPOINTS]);
        System.arraycopy(temp, 0, data, pos, temp.length);
        pos += temp.length;

        pos = saveItems(data, c.equipment, pos);
        for (ItemEquip[] e : c.inventory) {
            pos = saveItems(data, e, pos);
        }
        pos = saveItems(data, c.upgrades, pos);
        pos = saveSkills(data, c, pos);
        pos = saveHotkeys(data, c, pos);
        saveKeyBind(data, c.getKeyBind(), pos);

        try {
            FileUtils.writeByteArrayToFile(new File(saveNum + ".tcdat"), data);
        } catch (IOException ex) {
            Logger.getLogger(SaveData.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static int saveKeyBind(byte[] data, int[] keybind, int pos) {
        for (int i = 0; i < keybind.length; i++) {
            byte[] temp;
            temp = Globals.intToByte(keybind[i]);
            System.arraycopy(temp, 0, data, pos, temp.length);
            pos += temp.length;
        }
        return pos;
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

    private static int saveSkills(byte[] data, SaveData c, int pos) {
        for (int i = 0; i < Skill.NUM_SKILLS; i++) {
            data[pos] = c.getSkills()[i].getLevel();
            pos += 1;
        }
        return pos;
    }

    private static int saveHotkeys(byte[] data, SaveData c, int pos) {
        for (Skill hotkey : c.getHotkeys()) {
            if (hotkey == null) {
                data[pos] = -1;
            } else {
                data[pos] = hotkey.getSkillCode();
            }
            pos += 1;
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

        System.arraycopy(data, pos, temp, 0, temp.length);
        c.baseStats[Globals.STAT_SKILLPOINTS] = Globals.bytesToInt(temp);
        pos += temp.length;

        pos = readItems(data, c.equipment, pos);
        for (ItemEquip[] e : c.inventory) {
            pos = readItems(data, e, pos);
        }
        pos = readItems(data, c.upgrades, pos);
        pos = readSkills(data, c, pos);
        pos = readHotkeys(data, c, pos);
        readKeyBind(data, c.getKeyBind(), pos);

        c.calcStats();
        return c;
    }

    private static int readKeyBind(byte[] data, int[] keybind, int pos) {
        for (int i = 0; i < keybind.length; i++) {
            byte[] temp = new byte[4];
            System.arraycopy(data, pos, temp, 0, temp.length);
            keybind[i] = Globals.bytesToInt(temp);
            pos += temp.length;
        }
        return pos;
    }

    private static int readSkills(byte[] data, SaveData c, int pos) {
        for (int i = 0; i < Skill.NUM_SKILLS; i++) {
            c.getSkills()[i].setLevel(data[pos]);
            pos += 1;
        }
        return pos;
    }

    private static int readHotkeys(byte[] data, SaveData c, int pos) {
        Skill[] e = c.getHotkeys();
        for (int i = 0; i < e.length; i++) {
            byte skillCode = data[pos];
            if (skillCode != -1) {
                e[i] = c.getSkills()[skillCode];
            }
            pos += 1;
        }
        return pos;
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

    public Skill[] getHotkeys() {
        return hotkeys;
    }

    public Skill[] getSkills() {
        return skills;
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

    public void calcStats() {
        for (int i = 0; i < bonusStats.length; i++) {
            bonusStats[i] = 0;
            for (ItemEquip e : equipment) {
                if (i != Globals.STAT_LEVEL && e != null) {
                    bonusStats[i] += e.getTotalStats()[i];
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

    public int[] getKeyBind() {
        return keybinds;
    }

    public double[] getBonusStats() {
        return bonusStats;
    }

    public byte getSaveNum() {
        return saveNum;
    }

    public void resetStat() {
        baseStats[Globals.STAT_POWER] = 0;
        baseStats[Globals.STAT_DEFENSE] = 0;
        baseStats[Globals.STAT_SPIRIT] = 0;
        calcStats();
        saveData(saveNum, this);
    }

    public void resetSkill() {
        for (Skill skill : skills) {
            skill.setLevel((byte) 0);
        }
        baseStats[Globals.STAT_SKILLPOINTS] = 3 * baseStats[Globals.STAT_LEVEL];
        saveData(saveNum, this);
    }

    public void addSkill(byte skillCode) {
        if (baseStats[Globals.STAT_SKILLPOINTS] <= 0 || skills[skillCode].getLevel() >= 30) {
            return;
        }
        baseStats[Globals.STAT_SKILLPOINTS]--;
        skills[skillCode].addLevel((byte) 1);

        saveData(saveNum, this);
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
        saveData(saveNum, this);
    }

    public void equipItem(int slot, int inventorySlot) {
        int itemType = slot;
        if (slot == Globals.ITEM_OFFHAND) {
            itemType = Globals.ITEM_WEAPON;
        }

        ItemEquip temp = inventory[itemType][inventorySlot];
        if (temp != null) {
            switch (ItemEquip.getSlot(temp.getItemCode())) {
                case Globals.ITEM_OFFHAND:
                    slot = Globals.ITEM_OFFHAND;
                    break;
                case Globals.ITEM_BOW:
                    slot = Globals.ITEM_WEAPON;
                    break;
            }
        }
        inventory[itemType][inventorySlot] = equipment[slot];
        equipment[slot] = temp;
        calcStats();
        saveData(saveNum, this);
    }

    public void destroyItem(int type, int slot) {
        inventory[type][slot] = null;
        saveData(saveNum, this);
    }

    public void destroyItem(int slot) {
        upgrades[slot] = null;
        saveData(saveNum, this);
    }

    public void destroyAll(int type) {
        for (int i = 0; i < inventory[type].length; i++) {
            inventory[type][i] = null;
        }
        saveData(saveNum, this);
    }

    public void destroyAllUpgrade() {
        for (int i = 0; i < upgrades.length; i++) {
            upgrades[i] = null;
        }
        saveData(saveNum, this);
    }

    public void addItem(int type, ItemEquip e) {
        for (int i = 0; i < inventory[type].length; i++) {
            if (inventory[type][i] == null) {
                inventory[type][i] = e;
                break;
            }
        }
        saveData(saveNum, this);
    }

    public void addItem(ItemUpgrade e) {
        for (int i = 0; i < upgrades.length; i++) {
            if (upgrades[i] == null) {
                upgrades[i] = e;
                break;
            }
        }
        saveData(saveNum, this);
    }

    public void setKeyBind(int k, int keycode) {
        keybinds[k] = keycode;
        for (int i = 0; i < keybinds.length; i++) {
            if (i != k && keybinds[i] == keycode) {
                keybinds[i] = -1;
            }
        }
    }
}
