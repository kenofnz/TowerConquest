package blockfighter.server.entities.player.skills;

import blockfighter.server.Globals;

/**
 *
 * @author Ken Kwan
 */
public class SkillSwordSlash extends Skill {

    /**
     * Constructor for Sword Skill Defensive Impact.
     */
    public SkillSwordSlash() {
        skillCode = SWORD_SLASH;
        maxCooldown = 1000;
        reqWeapon = Globals.ITEM_WEAPON;
    }

}
