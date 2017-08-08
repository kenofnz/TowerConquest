package blockfighter.client.screen;

import blockfighter.client.Core;
import blockfighter.client.entities.player.skills.Skill;
import blockfighter.client.savedata.SaveData;
import blockfighter.shared.Globals;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.SwingUtilities;

public class ScreenSkills extends ScreenMenu {

    private static final String UNLOCK_SKILL_TEXT1 = "Unlocked at";
    private static final String UNLOCK_SKILL_TEXT2 = "level ";
    private static final String UNLOCK_SKILL_TEXT3 = "Level";
    private static final String RESET_SKILLS_TEXT = "Reset Skills";
    private static final String SKILL_POINTS_TEXT = "Skill Points: ";
    private static final String MAX_BUTTON_TEXT = "Max";
    private static final String ADD_POINT_BUTTON_TEXT = "+";
    private static final String UNKNOWN_KEY_TEXT = "Unknown Key";
    private static final String SKILL_PASSIVE_TEXT = "Passive";
    private static final String SKILL_SHIELD_TEXT = "Shield";
    private static final String SKILL_UTILITY_TEXT = "Utility";
    private static final String SKILL_BOW_TEXT = "Bow";
    private static final String SKILL_SWORD_TEXT = "Sword";

    private final SaveData saveData;
    // Slots(x,y) in the GUI
    private static final Rectangle2D.Double[] HOTKEY_SLOTS = new Rectangle2D.Double[12];
    private static final Rectangle2D.Double[] SKILL_SLOTS = new Rectangle2D.Double[Globals.NUM_SKILLS];
    private static final Rectangle2D.Double[] ADD_SKILL_BOX = new Rectangle2D.Double[Globals.NUM_SKILLS];
    private static final Rectangle2D.Double[] ADD_MAX_SKILL_BOX = new Rectangle2D.Double[Globals.NUM_SKILLS];

    private static final Rectangle2D.Double RESET_BOX;

    private static final int SWORD_BOX_X = 260, SWORD_BOX_Y = 55;
    private static final int BOW_BOX_X = 505, BOW_BOX_Y = 55;
    private static final int UTIL_BOX_X = 770, UTIL_BOX_Y = 75;
    private static final int SHIELD_BOX_X = 770, SHIELD_BOX_Y = 270;
    private static final int PASSIVE_BOX_X = 1020, PASSIVE_BOX_Y = 55;
    private static final int HOTKEY_BOX_X = 240, HOTKEY_BOX_Y = 605;

    // Actual skills stored
    private final Skill[] hotkeyList;
    private final Skill[] skillList;

    private int drawInfoSkill = -1, drawInfoHotkey = -1;
    private int dragSkill = -1, dragHotkey = -1;

    static {

        SKILL_SLOTS[Globals.SWORD_GASH] = new Rectangle2D.Double(SWORD_BOX_X, SWORD_BOX_Y, 60, 60);
        SKILL_SLOTS[Globals.SWORD_SLASH] = new Rectangle2D.Double(SWORD_BOX_X, SWORD_BOX_Y + 90, 60, 60);
        //skillSlots[Globals.SWORD_MULTI] = new Rectangle2D.Double(241, 235, 60, 60);
        SKILL_SLOTS[Globals.SWORD_PHANTOM] = new Rectangle2D.Double(SWORD_BOX_X, SWORD_BOX_Y + 180, 60, 60);
        SKILL_SLOTS[Globals.SWORD_VORPAL] = new Rectangle2D.Double(SWORD_BOX_X, SWORD_BOX_Y + 270, 60, 60);
        SKILL_SLOTS[Globals.SWORD_CINDER] = new Rectangle2D.Double(SWORD_BOX_X, SWORD_BOX_Y + 360, 60, 60);
        SKILL_SLOTS[Globals.SWORD_TAUNT] = new Rectangle2D.Double(SWORD_BOX_X, SWORD_BOX_Y + 450, 60, 60);

        SKILL_SLOTS[Globals.BOW_ARC] = new Rectangle2D.Double(BOW_BOX_X, BOW_BOX_Y, 60, 60);
        SKILL_SLOTS[Globals.BOW_RAPID] = new Rectangle2D.Double(BOW_BOX_X, BOW_BOX_Y + 90, 60, 60);
        SKILL_SLOTS[Globals.BOW_POWER] = new Rectangle2D.Double(BOW_BOX_X, BOW_BOX_Y + 180, 60, 60);
        SKILL_SLOTS[Globals.BOW_VOLLEY] = new Rectangle2D.Double(BOW_BOX_X, BOW_BOX_Y + 270, 60, 60);
        SKILL_SLOTS[Globals.BOW_STORM] = new Rectangle2D.Double(BOW_BOX_X, BOW_BOX_Y + 360, 60, 60);
        SKILL_SLOTS[Globals.BOW_FROST] = new Rectangle2D.Double(BOW_BOX_X, BOW_BOX_Y + 450, 60, 60);

        SKILL_SLOTS[Globals.UTILITY_DASH] = new Rectangle2D.Double(UTIL_BOX_X, UTIL_BOX_Y, 60, 60);
        SKILL_SLOTS[Globals.UTILITY_ADRENALINE] = new Rectangle2D.Double(UTIL_BOX_X, UTIL_BOX_Y + 75, 60, 60);

        SKILL_SLOTS[Globals.SHIELD_ROAR] = new Rectangle2D.Double(SHIELD_BOX_X, SHIELD_BOX_Y, 60, 60);
        SKILL_SLOTS[Globals.SHIELD_CHARGE] = new Rectangle2D.Double(SHIELD_BOX_X, SHIELD_BOX_Y + 75, 60, 60);
        SKILL_SLOTS[Globals.SHIELD_REFLECT] = new Rectangle2D.Double(SHIELD_BOX_X, SHIELD_BOX_Y + 150, 60, 60);
        SKILL_SLOTS[Globals.SHIELD_MAGNETIZE] = new Rectangle2D.Double(SHIELD_BOX_X, SHIELD_BOX_Y + 225, 60, 60);

        //Specialize
        SKILL_SLOTS[Globals.PASSIVE_DUALSWORD] = new Rectangle2D.Double(PASSIVE_BOX_X, PASSIVE_BOX_Y, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_BOWMASTERY] = new Rectangle2D.Double(PASSIVE_BOX_X, PASSIVE_BOX_Y + 85, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_SHIELDMASTERY] = new Rectangle2D.Double(PASSIVE_BOX_X, PASSIVE_BOX_Y + 170, 60, 60);

        //Defense
        SKILL_SLOTS[Globals.PASSIVE_RESIST] = new Rectangle2D.Double(PASSIVE_BOX_X, PASSIVE_BOX_Y + 255, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_BARRIER] = new Rectangle2D.Double(PASSIVE_BOX_X, PASSIVE_BOX_Y + 340, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_TOUGH] = new Rectangle2D.Double(PASSIVE_BOX_X, PASSIVE_BOX_Y + 425, 60, 60);

        //Offense
        SKILL_SLOTS[Globals.PASSIVE_KEENEYE] = new Rectangle2D.Double(PASSIVE_BOX_X + 110, PASSIVE_BOX_Y, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_VITALHIT] = new Rectangle2D.Double(PASSIVE_BOX_X + 110, PASSIVE_BOX_Y + 85, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_SHADOWATTACK] = new Rectangle2D.Double(PASSIVE_BOX_X + 110, PASSIVE_BOX_Y + 170, 60, 60);
        //Hybrid
        SKILL_SLOTS[Globals.PASSIVE_WILLPOWER] = new Rectangle2D.Double(PASSIVE_BOX_X + 110, PASSIVE_BOX_Y + 255, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_HARMONY] = new Rectangle2D.Double(PASSIVE_BOX_X + 110, PASSIVE_BOX_Y + 340, 60, 60);
        SKILL_SLOTS[Globals.PASSIVE_STATIC] = new Rectangle2D.Double(PASSIVE_BOX_X + 110, PASSIVE_BOX_Y + 425, 60, 60);

        for (int i = 0; i < HOTKEY_SLOTS.length; i++) {
            HOTKEY_SLOTS[i] = new Rectangle2D.Double(HOTKEY_BOX_X + (i * 64), HOTKEY_BOX_Y, 60, 60);
        }
        for (int i = 0; i < 18; i++) {
            ADD_SKILL_BOX[i] = new Rectangle2D.Double(SKILL_SLOTS[i].x + 135, SKILL_SLOTS[i].y + 32, 30, 23);
            ADD_MAX_SKILL_BOX[i] = new Rectangle2D.Double(ADD_SKILL_BOX[i].x + ADD_SKILL_BOX[i].width + 3, SKILL_SLOTS[i].y + 32, 30, 23);
        }

        for (int i = 18; i < ADD_SKILL_BOX.length; i++) {
            ADD_SKILL_BOX[i] = new Rectangle2D.Double(SKILL_SLOTS[i].x + 60, SKILL_SLOTS[i].y + 37, 30, 23);
            ADD_MAX_SKILL_BOX[i] = new Rectangle2D.Double(ADD_SKILL_BOX[i].x, ADD_SKILL_BOX[i].y - 28, 30, 23);
        }
        RESET_BOX = new Rectangle2D.Double(1050, 630, 180, 40);
    }

    public ScreenSkills() {
        this.saveData = Core.getLogicModule().getSelectedChar();
        this.hotkeyList = this.saveData.getHotkeys();
        this.skillList = this.saveData.getSkills();
    }

    @Override
    public void draw(final Graphics2D g) {
        final BufferedImage bg = Globals.MENU_BG[1];
        g.drawImage(bg, 0, 0, null);

        g.setFont(Globals.ARIAL_18PT);
        drawStringOutline(g, SKILL_POINTS_TEXT + (int) this.saveData.getBaseStats()[Globals.STAT_SKILLPOINTS], 1080, 620, 1);
        g.setColor(Color.WHITE);
        g.drawString(SKILL_POINTS_TEXT + (int) this.saveData.getBaseStats()[Globals.STAT_SKILLPOINTS], 1080, 620);

        final BufferedImage button = Globals.MENU_BUTTON[Globals.BUTTON_SMALLRECT];
        g.drawImage(button, (int) RESET_BOX.x, (int) RESET_BOX.y, null);
        g.setFont(Globals.ARIAL_18PT);
        drawStringOutline(g, RESET_SKILLS_TEXT, 1090, 657, 1);
        g.setColor(Color.WHITE);
        g.drawString(RESET_SKILLS_TEXT, 1090, 657);
        drawSlots(g);
        drawMenuButton(g);

        if (this.dragSkill != -1) {
            this.skillList[this.dragSkill].draw(g, (int) this.mousePos.x, (int) this.mousePos.y);
        } else if (this.dragHotkey != -1) {
            this.hotkeyList[this.dragHotkey].draw(g, (int) this.mousePos.x, (int) this.mousePos.y);
        }

        super.draw(g);
        drawSkillInfo(g);
    }

    private void drawSkillInfo(final Graphics2D g) {
        if (this.drawInfoSkill != -1) {
            if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[this.drawInfoSkill].getReqLevel()) {
                drawSkillInfo(g, SKILL_SLOTS[this.drawInfoSkill], this.skillList[this.drawInfoSkill]);
            }
        } else if (this.drawInfoHotkey != -1) {
            if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[this.drawInfoHotkey].getReqLevel()) {
                drawSkillInfo(g, HOTKEY_SLOTS[this.drawInfoHotkey], this.hotkeyList[this.drawInfoHotkey]);
            }
        }
    }

    private void drawSlots(final Graphics2D g) {
        BufferedImage button = Globals.MENU_BUTTON[Globals.BUTTON_SLOT];

        g.setColor(SKILL_BOX_BG_COLOR);
        g.fillRoundRect(SWORD_BOX_X - 10, SWORD_BOX_Y - 25, 210, 545, 15, 15);
        g.fillRoundRect(BOW_BOX_X - 10, BOW_BOX_Y - 25, 210, 545, 15, 15);
        g.fillRoundRect(UTIL_BOX_X - 10, UTIL_BOX_Y - 25, 210, 170, 15, 15);
        g.fillRoundRect(SHIELD_BOX_X - 10, SHIELD_BOX_Y - 25, 210, 320, 15, 15);
        g.fillRoundRect(PASSIVE_BOX_X - 10, PASSIVE_BOX_Y - 25, 220, 545, 15, 15);
        g.fillRoundRect(HOTKEY_BOX_X - 10, HOTKEY_BOX_Y - 10, 784, 90, 15, 15);

        g.setFont(Globals.ARIAL_18PT);
        drawStringOutline(g, SKILL_SWORD_TEXT, SWORD_BOX_X + 65, SWORD_BOX_Y - 5, 1);
        g.setColor(Color.WHITE);
        g.drawString(SKILL_SWORD_TEXT, SWORD_BOX_X + 65, SWORD_BOX_Y - 5);

        drawStringOutline(g, SKILL_BOW_TEXT, BOW_BOX_X + 75, BOW_BOX_Y - 5, 1);
        g.setColor(Color.WHITE);
        g.drawString(SKILL_BOW_TEXT, BOW_BOX_X + 75, BOW_BOX_Y - 5);

        drawStringOutline(g, SKILL_UTILITY_TEXT, UTIL_BOX_X + 75, UTIL_BOX_Y - 5, 1);
        g.setColor(Color.WHITE);
        g.drawString(SKILL_UTILITY_TEXT, UTIL_BOX_X + 75, UTIL_BOX_Y - 5);

        drawStringOutline(g, SKILL_SHIELD_TEXT, SHIELD_BOX_X + 65, SHIELD_BOX_Y - 5, 1);
        g.setColor(Color.WHITE);
        g.drawString(SKILL_SHIELD_TEXT, SHIELD_BOX_X + 65, SHIELD_BOX_Y - 5);

        drawStringOutline(g, SKILL_PASSIVE_TEXT, PASSIVE_BOX_X + 60, PASSIVE_BOX_Y - 5, 1);
        g.setColor(Color.WHITE);
        g.drawString(SKILL_PASSIVE_TEXT, PASSIVE_BOX_X + 60, PASSIVE_BOX_Y - 5);
        for (int i = 0; i < HOTKEY_SLOTS.length; i++) {
            g.drawImage(button, (int) HOTKEY_SLOTS[i].x, (int) HOTKEY_SLOTS[i].y, null);
            if (this.hotkeyList[i] != null) {
                this.hotkeyList[i].draw(g, (int) HOTKEY_SLOTS[i].x, (int) HOTKEY_SLOTS[i].y);
            }
            String key = UNKNOWN_KEY_TEXT;
            if (this.saveData.getKeyBind()[i] != -1) {
                key = KeyEvent.getKeyText(this.saveData.getKeyBind()[i]);
            }
            final int width = g.getFontMetrics().stringWidth(key);
            g.setFont(Globals.ARIAL_15PT);
            drawStringOutline(g, key, (int) HOTKEY_SLOTS[i].x + 30 - width / 2, (int) HOTKEY_SLOTS[i].y + 75, 1);
            g.setColor(Color.WHITE);
            g.drawString(key, (int) HOTKEY_SLOTS[i].x + 30 - width / 2, (int) HOTKEY_SLOTS[i].y + 75);
        }

        for (int i = 0; i < 18; i++) {
            g.drawImage(button, (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y, null);
            if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[i].getReqLevel()) {
                this.skillList[i].draw(g, (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y);
                g.setFont(Globals.ARIAL_15PT);
                drawStringOutline(g, this.skillList[i].getSkillName(), (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 20, 1);
                drawStringOutline(g, Globals.getStatName(Globals.STAT_LEVEL) + Globals.COLON_SPACE_TEXT + this.skillList[i].getLevel(), (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 50,
                        1);
                g.setColor(Color.WHITE);
                g.drawString(this.skillList[i].getSkillName(), (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 20);
                g.drawString(Globals.getStatName(Globals.STAT_LEVEL) + Globals.COLON_SPACE_TEXT + this.skillList[i].getLevel(), (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 50);
                drawSkillAddButton(g, i);
            } else {
                this.skillList[i].drawDisabled(g, (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y);
                g.setFont(Globals.ARIAL_15PT);
                drawStringOutline(g, UNLOCK_SKILL_TEXT1, (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 25, 1);
                drawStringOutline(g, UNLOCK_SKILL_TEXT2 + this.skillList[i].getReqLevel(), (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 45, 1);
                g.setColor(Color.WHITE);
                g.drawString(UNLOCK_SKILL_TEXT1, (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 25);
                g.drawString(UNLOCK_SKILL_TEXT2 + this.skillList[i].getReqLevel(), (int) SKILL_SLOTS[i].x + 70, (int) SKILL_SLOTS[i].y + 45);
            }
        }

        for (int i = 18; i < SKILL_SLOTS.length; i++) {
            g.drawImage(button, (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y, null);
            if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[i].getReqLevel()) {
                this.skillList[i].draw(g, (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y);
                g.setFont(Globals.ARIAL_15PT);
                drawStringOutline(g, Globals.getStatName(Globals.STAT_LEVEL) + Globals.COLON_SPACE_TEXT + this.skillList[i].getLevel(), (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y + 80, 1);
                g.setColor(Color.WHITE);
                g.drawString(Globals.getStatName(Globals.STAT_LEVEL) + Globals.COLON_SPACE_TEXT + this.skillList[i].getLevel(), (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y + 80);
                drawSkillAddButton(g, i);
            } else {
                this.skillList[i].drawDisabled(g, (int) SKILL_SLOTS[i].x, (int) SKILL_SLOTS[i].y);
                g.setFont(Globals.ARIAL_15PT);
                int line1X = (int) (SKILL_SLOTS[i].x + SKILL_SLOTS[i].width / 2 - g.getFontMetrics().stringWidth(UNLOCK_SKILL_TEXT3) / 2);
                int line2X = (int) (SKILL_SLOTS[i].x + SKILL_SLOTS[i].width / 2 - g.getFontMetrics().stringWidth(Integer.toString(this.skillList[i].getReqLevel())) / 2);
                drawStringOutline(g, UNLOCK_SKILL_TEXT3, line1X, (int) SKILL_SLOTS[i].y + 25, 1);
                drawStringOutline(g, Integer.toString(this.skillList[i].getReqLevel()), line2X, (int) SKILL_SLOTS[i].y + 45, 1);
                g.setColor(Color.WHITE);
                g.drawString(UNLOCK_SKILL_TEXT3, line1X, (int) SKILL_SLOTS[i].y + 25);
                g.drawString(Integer.toString(this.skillList[i].getReqLevel()), line2X, (int) SKILL_SLOTS[i].y + 45);
            }
        }
    }

    private void drawSkillAddButton(final Graphics2D g, final int skillIndex) {
        if (this.saveData.getBaseStats()[Globals.STAT_SKILLPOINTS] > 0 && !this.skillList[skillIndex].isMaxed()) {
            BufferedImage button = Globals.MENU_BUTTON[Globals.BUTTON_ADDSTAT];
            g.drawImage(button, (int) ADD_SKILL_BOX[skillIndex].x, (int) ADD_SKILL_BOX[skillIndex].y, null);
            g.setFont(Globals.ARIAL_15PT);
            drawStringOutline(g, ADD_POINT_BUTTON_TEXT, (int) ADD_SKILL_BOX[skillIndex].x + 11, (int) ADD_SKILL_BOX[skillIndex].y + 18, 1);
            g.setColor(Color.WHITE);
            g.drawString(ADD_POINT_BUTTON_TEXT, (int) ADD_SKILL_BOX[skillIndex].x + 11, (int) ADD_SKILL_BOX[skillIndex].y + 18);

            button = Globals.MENU_BUTTON[Globals.BUTTON_ADDSTAT];
            g.drawImage(button, (int) ADD_MAX_SKILL_BOX[skillIndex].x, (int) ADD_MAX_SKILL_BOX[skillIndex].y, null);
            g.setFont(Globals.ARIAL_12PT);
            drawStringOutline(g, MAX_BUTTON_TEXT, (int) ADD_MAX_SKILL_BOX[skillIndex].x + 4, (int) ADD_MAX_SKILL_BOX[skillIndex].y + 16, 1);
            g.setColor(Color.WHITE);
            g.drawString(MAX_BUTTON_TEXT, (int) ADD_MAX_SKILL_BOX[skillIndex].x + 4, (int) ADD_MAX_SKILL_BOX[skillIndex].y + 16);
        }
    }

    private void drawSkillInfo(final Graphics2D g, final Rectangle2D.Double box, final Skill skill) {
        skill.drawInfo(g, (int) box.x, (int) box.y);
    }

    @Override
    public void keyTyped(final KeyEvent e) {

    }

    @Override
    public void keyPressed(final KeyEvent e) {

    }

    @Override
    public void keyReleased(final KeyEvent e) {
    }

    @Override
    public void mouseClicked(final MouseEvent e) {

    }

    @Override
    public void mousePressed(final MouseEvent e) {

    }

    @Override
    public void mouseReleased(final MouseEvent e) {
        Point2D.Double scaled;
        if (Globals.WINDOW_SCALE_ENABLED) {
            scaled = new Point2D.Double(e.getX() / Globals.WINDOW_SCALE, e.getY() / Globals.WINDOW_SCALE);
        } else {
            scaled = new Point2D.Double(e.getX(), e.getY());
        }
        final int drSkill = this.dragSkill, drHK = this.dragHotkey;
        this.dragSkill = -1;
        this.dragHotkey = -1;

        super.mouseReleased(e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            for (int i = 0; i < HOTKEY_SLOTS.length; i++) {
                if (HOTKEY_SLOTS[i].contains(scaled)) {
                    if (drSkill != -1) {
                        if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[drSkill].getReqLevel()) {
                            this.hotkeyList[i] = this.skillList[drSkill];
                        }
                        return;
                    }
                    if (drHK != -1) {
                        final Skill temp = this.hotkeyList[i];
                        this.hotkeyList[i] = this.hotkeyList[drHK];
                        this.hotkeyList[drHK] = temp;
                        return;
                    }
                    return;
                }
            }
            if (RESET_BOX.contains(scaled)) {
                this.saveData.resetSkill();
                return;
            }
            for (byte i = 0; i < ADD_SKILL_BOX.length; i++) {
                if (ADD_SKILL_BOX[i].contains(scaled)) {
                    if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[i].getReqLevel()) {
                        this.saveData.addSkill(i, false);
                    }
                    return;

                }
            }

            for (byte i = 0; i < ADD_MAX_SKILL_BOX.length; i++) {
                if (ADD_MAX_SKILL_BOX[i].contains(scaled)) {
                    if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[i].getReqLevel()) {
                        this.saveData.addSkill(i, true);
                    }
                    return;
                }
            }
        }
    }

    @Override
    public void mouseEntered(final MouseEvent e) {

    }

    @Override
    public void mouseExited(final MouseEvent e) {

    }

    @Override
    public void mouseDragged(final MouseEvent e) {
        Point2D.Double scaled;
        if (Globals.WINDOW_SCALE_ENABLED) {
            scaled = new Point2D.Double(e.getX() / Globals.WINDOW_SCALE, e.getY() / Globals.WINDOW_SCALE);
        } else {
            scaled = new Point2D.Double(e.getX(), e.getY());
        }
        mouseMoved(e);
        if (SwingUtilities.isLeftMouseButton(e)) {
            if (this.dragSkill == -1 && this.dragHotkey == -1) {
                for (int i = 0; i < HOTKEY_SLOTS.length; i++) {
                    if (HOTKEY_SLOTS[i].contains(scaled) && this.hotkeyList[i] != null) {
                        this.dragHotkey = i;
                        return;
                    }
                }

                for (byte i = 0; i < SKILL_SLOTS.length; i++) {
                    if (SKILL_SLOTS[i].contains(scaled) && SKILL_SLOTS[i] != null) {
                        if (this.saveData.getTotalStats()[Globals.STAT_LEVEL] >= this.skillList[i].getReqLevel()) {
                            this.dragSkill = i;
                        }
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void mouseMoved(final MouseEvent e) {
        Point2D.Double scaled;
        if (Globals.WINDOW_SCALE_ENABLED) {
            scaled = new Point2D.Double(e.getX() / Globals.WINDOW_SCALE, e.getY() / Globals.WINDOW_SCALE);
        } else {
            scaled = new Point2D.Double(e.getX(), e.getY());
        }
        this.mousePos = scaled;
        this.drawInfoSkill = -1;
        this.drawInfoHotkey = -1;
        for (int i = 0; i < HOTKEY_SLOTS.length; i++) {
            if (HOTKEY_SLOTS[i].contains(scaled) && this.hotkeyList[i] != null) {
                this.drawInfoHotkey = i;
                return;
            }
        }

        for (byte i = 0; i < SKILL_SLOTS.length; i++) {
            if (SKILL_SLOTS[i].contains(scaled) && SKILL_SLOTS[i] != null) {
                this.drawInfoSkill = i;
                return;
            }
        }
    }

    @Override
    public void unload() {
    }

}
