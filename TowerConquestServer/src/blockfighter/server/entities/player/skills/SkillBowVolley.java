package blockfighter.server.entities.player.skills;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.proj.ProjBowVolley;
import blockfighter.server.net.PacketSender;

public class SkillBowVolley extends Skill {

    /**
     * Constructor for Bow Skill Volley.
     *
     * @param l
     */
    public SkillBowVolley(final LogicModule l) {
        super(l);
        this.skillCode = BOW_VOLLEY;
        this.maxCooldown = 17000;
        this.reqWeapon = Globals.ITEM_BOW;
        this.endDuration = 1900;
        this.playerState = Player.PLAYER_STATE_BOW_VOLLEY;
        this.reqEquipSlot = Globals.ITEM_WEAPON;
    }

    @Override
    public void updateSkillUse(Player player) {
        final int duration = Globals.nsToMs(this.logic.getTime() - player.getSkillCastTime());
        final int numHits = 20;
        if (Globals.hasPastDuration(duration, player.getSkillCounter() * 100) && player.getSkillCounter() < numHits) {
            final ProjBowVolley proj = new ProjBowVolley(this.logic, player, player.getX(),
                    player.getY());
            this.logic.queueAddProj(proj);
            PacketSender.sendParticle(this.logic.getRoom(), Globals.PARTICLE_BOW_VOLLEYARROW, proj.getHitbox()[0].getX(), proj.getHitbox()[0].getY(),
                    player.getFacing());
            PacketSender.sendParticle(this.logic.getRoom(), Globals.PARTICLE_BOW_VOLLEYBOW, player.getX(), player.getY() - 75, player.getFacing());
            player.incrementSkillCounter();
            PacketSender.sendSFX(this.logic.getRoom(), Globals.SFX_VOLLEY, player.getX(), player.getY());
        }
        player.updateSkillEnd(duration, this.endDuration, true, true);
    }
}
