package blockfighter.server.entities.player.skills;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.items.Items;
import blockfighter.server.entities.player.Player;
import java.util.HashMap;

/**
 *
 * @author Ken Kwan
 */
public abstract class Skill {

    protected byte reqWeapon = -1;
    protected byte skillCode;
    protected byte level;
    protected long skillCastTime;
    protected int maxCooldown;
    protected LogicModule logic;
    protected boolean isPassive = false;

    public final static byte NUM_SKILLS = 30,
            SWORD_VORPAL = 0x00,
            SWORD_PHANTOM = 0x01,
            SWORD_CINDER = 0x02,
            SWORD_GASH = 0x03,
            SWORD_SLASH = 0x04,
            SWORD_TAUNT = 0x05,
            BOW_ARC = 0x06,
            BOW_POWER = 0x07,
            BOW_RAPID = 0x08,
            BOW_FROST = 0x09,
            BOW_STORM = 0x0A,
            BOW_VOLLEY = 0x0B,
            SHIELD_FORTIFY = 0x0C,
            SHIELD_IRON = 0x0D,
            SHIELD_CHARGE = 0x0E,
            SHIELD_REFLECT = 0x0F,
            SHIELD_TOSS = 0x10,
            SHIELD_DASH = 0x11,
            PASSIVE_DUALSWORD = 0x12,
            PASSIVE_KEENEYE = 0x13,
            PASSIVE_VITALHIT = 0x14,
            PASSIVE_SHIELDMASTERY = 0x15,
            PASSIVE_BARRIER = 0x16,
            PASSIVE_RESIST = 0x17,
            PASSIVE_BOWMASTERY = 0x18,
            PASSIVE_WILLPOWER = 0x19,
            PASSIVE_HARMONY = 0x1A,
            PASSIVE_TOUGH = 0x1B,
            PASSIVE_SHADOWATTACK = 0x1C,
            PASSIVE_STATIC = 0x1D;

    public static final HashMap<Byte, Byte> SKILL_REQSLOT = new HashMap<>(NUM_SKILLS);
    public static final HashMap<Byte, Byte> SKILL_PLAYER_STATE = new HashMap<>(NUM_SKILLS);

    static {
        initializeSkillReq();
        initializeSkillPlayerState();
    }

    public static void initializeSkillReq() {
        SKILL_REQSLOT.put(SWORD_VORPAL, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(SWORD_PHANTOM, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(SWORD_CINDER, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(SWORD_GASH, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(SWORD_SLASH, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(SWORD_TAUNT, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(BOW_ARC, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(BOW_POWER, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(BOW_RAPID, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(BOW_FROST, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(BOW_STORM, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(BOW_VOLLEY, Globals.ITEM_WEAPON);
        SKILL_REQSLOT.put(SHIELD_FORTIFY, Globals.ITEM_OFFHAND);
        SKILL_REQSLOT.put(SHIELD_IRON, Globals.ITEM_OFFHAND);
        SKILL_REQSLOT.put(SHIELD_CHARGE, Globals.ITEM_OFFHAND);
        SKILL_REQSLOT.put(SHIELD_REFLECT, Globals.ITEM_OFFHAND);
        SKILL_REQSLOT.put(SHIELD_TOSS, Globals.ITEM_OFFHAND);
    }

    public static void initializeSkillPlayerState() {
        SKILL_PLAYER_STATE.put(SWORD_VORPAL, Player.PLAYER_STATE_SWORD_VORPAL);
        SKILL_PLAYER_STATE.put(SWORD_PHANTOM, Player.PLAYER_STATE_SWORD_PHANTOM);
        SKILL_PLAYER_STATE.put(SWORD_CINDER, Player.PLAYER_STATE_SWORD_CINDER);
        SKILL_PLAYER_STATE.put(SWORD_GASH, Player.PLAYER_STATE_SWORD_GASH);
        SKILL_PLAYER_STATE.put(SWORD_SLASH, Player.PLAYER_STATE_SWORD_SLASH);
        SKILL_PLAYER_STATE.put(SWORD_TAUNT, Player.PLAYER_STATE_SWORD_TAUNT);
        SKILL_PLAYER_STATE.put(BOW_ARC, Player.PLAYER_STATE_BOW_ARC);
        SKILL_PLAYER_STATE.put(BOW_POWER, Player.PLAYER_STATE_BOW_POWER);
        SKILL_PLAYER_STATE.put(BOW_RAPID, Player.PLAYER_STATE_BOW_RAPID);
        SKILL_PLAYER_STATE.put(BOW_FROST, Player.PLAYER_STATE_BOW_FROST);
        SKILL_PLAYER_STATE.put(BOW_STORM, Player.PLAYER_STATE_BOW_STORM);
        SKILL_PLAYER_STATE.put(BOW_VOLLEY, Player.PLAYER_STATE_BOW_VOLLEY);
        SKILL_PLAYER_STATE.put(SHIELD_FORTIFY, Player.PLAYER_STATE_SHIELD_FORTIFY);
        SKILL_PLAYER_STATE.put(SHIELD_IRON, Player.PLAYER_STATE_SHIELD_IRON);
        SKILL_PLAYER_STATE.put(SHIELD_CHARGE, Player.PLAYER_STATE_SHIELD_CHARGE);
        SKILL_PLAYER_STATE.put(SHIELD_REFLECT, Player.PLAYER_STATE_SHIELD_REFLECT);
        SKILL_PLAYER_STATE.put(SHIELD_TOSS, Player.PLAYER_STATE_SHIELD_TOSS);
        SKILL_PLAYER_STATE.put(SHIELD_DASH, Player.PLAYER_STATE_SHIELD_DASH);

    }

    public Skill(final LogicModule l) {
        this.logic = l;
    }

    /**
     * Get this skill code of this skill.
     *
     * @return
     */
    public byte getSkillCode() {
        return this.skillCode;
    }

    /**
     * Reduce the cooldown timer of this skill in milliseconds.
     *
     * @param ms Amount of milliseconds to reduce.
     */
    public void reduceCooldown(final int ms) {
        this.skillCastTime -= Globals.msToNs(ms);
    }

    /**
     * Set the cooldown of this skill to it's maximum.
     */
    public void setCooldown() {
        this.skillCastTime = this.logic.getTime();
    }

    /**
     * Get the current cooldown time of this skill.
     *
     * @return Cooldown in milliseconds
     */
    public int getCooldown() {
        return Globals.nsToMs(this.logic.getTime() - this.skillCastTime);
    }

    /**
     * Get the maximum cooldown of this skill.
     *
     * @return Maximum cooldown in milliseconds.
     */
    public int getMaxCooldown() {
        return this.maxCooldown;
    }

    /**
     * Set the level of this skill.
     *
     * @param lvl Skill level
     */
    public void setLevel(final byte lvl) {
        this.level = lvl;
    }

    /**
     * Get the skill level
     *
     * @return Byte - Skill level
     */
    public byte getLevel() {
        return this.level;
    }

    /**
     * Check if this skill is level 30
     *
     * @return True if skill is level 30
     */
    public boolean isMaxed() {
        return this.level == 30;
    }

    /**
     * Check if this skill can be cast with this weapon type and is off cooldown.
     *
     * @param player The casting player.
     * @return True if weapon is same as required weapon and cooldown is <= 0
     */
    public boolean canCast(final Player player) {
        return !isPassive && (this.reqWeapon == -1 || Items.getItemType(player.getEquips()[SKILL_REQSLOT.get(this.skillCode)]) == this.reqWeapon)
                && canCast();
    }

    public boolean canCast() {
        return Globals.DEBUG_MODE || this.getCooldown() >= this.getMaxCooldown();
    }

    public byte castPlayerState() {
        return SKILL_PLAYER_STATE.get(this.skillCode);
    }

    public void updateSkillUse(Player player) {

    }
}
