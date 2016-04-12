package blockfighter.server.entities.mob.boss.Lightning;

import blockfighter.server.LogicModule;
import blockfighter.server.entities.player.skills.Skill;

/**
 *
 * @author Ken Kwan
 */
public class SkillAttack2 extends Skill {

    public SkillAttack2(final LogicModule l) {
        super(l);
        this.skillCode = BossLightning.SKILL_ATT2;
        this.maxCooldown = 2000;
    }

}