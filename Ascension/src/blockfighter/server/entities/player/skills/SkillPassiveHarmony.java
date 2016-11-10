package blockfighter.server.entities.player.skills;

import blockfighter.server.LogicModule;

public class SkillPassiveHarmony extends Skill {

    public SkillPassiveHarmony(final LogicModule l) {
        super(l);
        this.skillCode = PASSIVE_HARMONY;
        this.isPassive = true;
    }

}