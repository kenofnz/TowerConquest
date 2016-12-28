package blockfighter.client.entities.player.skills;

import blockfighter.shared.Globals;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class SkillSwordVorpal extends Skill {

    public static final String CUSTOMHEADER_BASEBONUSCRITDMG = "[basebonuscritdamage]",
            CUSTOMHEADER_MULTBONUSCRITDMG = "[multbonuscritdamage]",
            CUSTOMHEADER_BONUSCRITCHC = "[bonuscritchc]";

    private static final String[] CUSTOM_DATA_HEADERS = {
        CUSTOMHEADER_BASEBONUSCRITDMG,
        CUSTOMHEADER_MULTBONUSCRITDMG,
        CUSTOMHEADER_BONUSCRITCHC
    };

    private static final HashMap<String, Double> CUSTOM_VALUES = new HashMap<>(3);

    private static final byte SKILL_CODE = Globals.SWORD_VORPAL;
    private static final BufferedImage ICON = Globals.SKILL_ICON[SKILL_CODE];

    private static final String SKILL_NAME;
    private static final String[] DESCRIPTION;
    private static final boolean IS_PASSIVE;
    private static final byte REQ_WEAPON;
    private static final long MAX_COOLDOWN;

    private static final double BASE_VALUE, MULT_VALUE;

    static {
        String[] data = Globals.loadSkillData(SKILL_CODE);
        HashMap<String, Integer> dataHeaders = Globals.getDataHeaders(data, CUSTOM_DATA_HEADERS);

        SKILL_NAME = Globals.loadSkillName(data, dataHeaders);
        DESCRIPTION = Globals.loadSkillDesc(data, dataHeaders);
        REQ_WEAPON = Globals.loadReqWeapon(data, dataHeaders);
        MAX_COOLDOWN = (long) Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_MAXCOOLDOWN_HEADER);
        BASE_VALUE = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_BASEVALUE_HEADER) * 100;
        MULT_VALUE = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_MULTVALUE_HEADER) * 100;
        IS_PASSIVE = Globals.loadBooleanValue(data, dataHeaders, Globals.SKILL_PASSIVE_HEADER);

        CUSTOM_VALUES.put(CUSTOMHEADER_BASEBONUSCRITDMG, Globals.loadDoubleValue(data, dataHeaders, CUSTOMHEADER_BASEBONUSCRITDMG) * 100);
        CUSTOM_VALUES.put(CUSTOMHEADER_MULTBONUSCRITDMG, Globals.loadDoubleValue(data, dataHeaders, CUSTOMHEADER_MULTBONUSCRITDMG) * 100);
        CUSTOM_VALUES.put(CUSTOMHEADER_BONUSCRITCHC, Globals.loadDoubleValue(data, dataHeaders, CUSTOMHEADER_BONUSCRITCHC) * 100);
    }

    @Override
    public HashMap<String, Double> getCustomValues() {
        return CUSTOM_VALUES;
    }

    @Override
    public String[] getDesc() {
        return DESCRIPTION;
    }

    @Override
    public BufferedImage getIcon() {
        return ICON;
    }

    @Override
    public long getMaxCooldown() {
        return MAX_COOLDOWN;
    }

    @Override
    public byte getReqWeapon() {
        return REQ_WEAPON;
    }

    @Override
    public byte getSkillCode() {
        return SKILL_CODE;
    }

    @Override
    public String getSkillName() {
        return SKILL_NAME;
    }

    @Override
    public boolean isPassive() {
        return IS_PASSIVE;
    }

    @Override
    public void updateDesc() {
        this.skillCurLevelDesc = new String[]{
            "Deals " + Globals.NUMBER_FORMAT.format(BASE_VALUE + MULT_VALUE * this.level) + "% damage per hit.",
            "Critical Hits deal additional +" + Globals.NUMBER_FORMAT.format(CUSTOM_VALUES.get(CUSTOMHEADER_BASEBONUSCRITDMG) + CUSTOM_VALUES.get(CUSTOMHEADER_MULTBONUSCRITDMG) * this.level) + "% Critical Damage."
        };
        this.skillNextLevelDesc = new String[]{
            "Deals " + Globals.NUMBER_FORMAT.format(BASE_VALUE + MULT_VALUE * (this.level + 1)) + "% damage per hit.",
            "Critical Hits deal additional +" + Globals.NUMBER_FORMAT.format(CUSTOM_VALUES.get(CUSTOMHEADER_BASEBONUSCRITDMG) + CUSTOM_VALUES.get(CUSTOMHEADER_MULTBONUSCRITDMG) * (this.level + 1)) + "% Critical Damage."
        };
        this.maxBonusDesc = new String[]{
            "This attack has +" + Globals.NUMBER_FORMAT.format(CUSTOM_VALUES.get(CUSTOMHEADER_BONUSCRITCHC)) + "% Critical Hit Chance.",
            "Strikes 5 times."
        };
    }
}
