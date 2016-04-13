package blockfighter.server.entities.player.skills;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.proj.ProjBowRapid;

/**
 *
 * @author Ken Kwan
 */
public class SkillBowRapid extends Skill {

    /**
     * Constructor for Bow Skill Rapid Fire.
     *
     * @param l
     */
    public SkillBowRapid(final LogicModule l) {
        super(l);
        this.skillCode = BOW_RAPID;
        this.maxCooldown = 700;
        this.reqWeapon = Globals.ITEM_BOW;
    }

    @Override
    public void updateSkillUse(Player player) {
        final int duration = Globals.nsToMs(this.logic.getTime() - player.getSkillCastTime());
        final int numHits = 3;

        if (Globals.hasPastDuration(duration, 150 + player.getSkillCounter() * 150) && player.getSkillCounter() < numHits) {
            if (player.getSkillCounter() != 0) {
                player.setFrame((byte) 2);
            }
            player.incrementSkillCounter();
            double projY = player.getY();
            if (player.getSkillCounter() == 1) {
                projY = player.getY() - 20;
            } else if (player.getSkillCounter() == 3) {
                projY = player.getY() + 20;
            }
            final ProjBowRapid proj = new ProjBowRapid(this.logic, player, player.getX(), projY);
            this.logic.queueAddProj(proj);
            Player.sendParticle(this.logic.getRoom(), Globals.PARTICLE_BOW_RAPID, proj.getHitbox()[0].getX(), proj.getHitbox()[0].getY(),
                    player.getFacing());
            Player.sendParticle(this.logic.getRoom(), Globals.PARTICLE_BOW_RAPID2, (player.getFacing() == Globals.LEFT) ? player.getX() - 20 : player.getX() - 40, proj.getHitbox()[0].getY() - 40,
                    player.getFacing());
            Player.sendSFX(this.logic.getRoom(), Globals.SFX_RAPID, player.getX(), player.getY());
        }
        player.updateSkillEnd(duration, 550, true, false);
    }

}
