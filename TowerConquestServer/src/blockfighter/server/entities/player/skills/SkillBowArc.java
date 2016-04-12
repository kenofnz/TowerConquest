package blockfighter.server.entities.player.skills;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.proj.ProjBowArc;

/**
 *
 * @author Ken Kwan
 */
public class SkillBowArc extends Skill {

    /**
     * Constructor for Bow Skill Arcshot.
     *
     * @param l Logic(room) this skill owner's belong to
     */
    public SkillBowArc(final LogicModule l) {
        super(l);
        this.skillCode = BOW_ARC;
        this.maxCooldown = 500;
        this.reqWeapon = Globals.ITEM_BOW;
    }

    @Override
    public void updateSkillUse(Player player) {
        final int duration = Globals.nsToMs(this.logic.getTime() - player.getSkillCastTime());
        final int numHits = 3;
        if (player.getSkillCounter() < numHits && Player.hasPastDuration(duration, 100 + player.getSkillCounter() * 50)) {
            player.incrementSkillCounter();
            final ProjBowArc proj = new ProjBowArc(this.logic, this.logic.getNextProjKey(), player, player.getX(), player.getY());
            this.logic.queueAddProj(proj);
            if (player.getSkillCounter() == 1) {
                Player.sendParticle(this.logic.getRoom(), Globals.PARTICLE_BOW_ARC, proj.getHitbox()[0].getX(), proj.getHitbox()[0].getY(),
                        player.getFacing());
                Player.sendSFX(this.logic.getRoom(), Globals.SFX_ARC, player.getX(), player.getY());
            }
        }
        player.updateSkillEnd(duration, 300, false, false);
    }
}
