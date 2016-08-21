package blockfighter.server.entities.proj;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.buff.BuffBurn;
import blockfighter.server.entities.buff.BuffKnockback;
import blockfighter.server.entities.damage.Damage;
import blockfighter.server.entities.mob.Mob;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.player.skills.Skill;
import blockfighter.server.net.PacketSender;
import java.awt.geom.Rectangle2D;

public class ProjSwordCinder extends Projectile {

    public ProjSwordCinder(final LogicModule l, final Player o, final double x, final double y) {
        super(l, o, x, y, 300);
        this.screenshake = true;
        this.hitbox = new Rectangle2D.Double[1];
        if (o.getFacing() == Globals.RIGHT) {
            this.hitbox[0] = new Rectangle2D.Double(this.x - 10, this.y - 220, 170, 238);
        } else {
            this.hitbox[0] = new Rectangle2D.Double(this.x - 190 + 10, this.y - 220, 170, 238);
        }
    }

    @Override
    public int calculateDamage(final boolean isCrit) {
        final Player owner = getOwner();
        double damage = owner.rollDamage() * (4.5 + owner.getSkillLevel(Skill.SWORD_CINDER) * .2);
        damage = (isCrit) ? owner.criticalDamage(damage) : damage;
        return (int) damage;
    }

    @Override
    public void applyDamage(Player target) {
        final Player owner = getOwner();
        final boolean isCrit = owner.rollCrit((owner.isSkillMaxed(Skill.SWORD_CINDER)) ? 1 : 0);
        final int damage = calculateDamage(isCrit);
        target.queueDamage(new Damage(damage, true, owner, target, isCrit, this.hitbox[0], target.getHitbox()));
        target.queueBuff(new BuffKnockback(this.logic, 300, (owner.getFacing() == Globals.RIGHT) ? 1 : -1, -4, owner, target));
        target.queueBuff(new BuffBurn(this.logic, 4000, owner.getSkillLevel(Skill.SWORD_CINDER) * 0.01,
                owner.isSkillMaxed(Skill.SWORD_CINDER) ? owner.rollDamage() : 0, owner, target));
        final byte[] bytes = new byte[Globals.PACKET_BYTE * 3];
        bytes[0] = Globals.DATA_PARTICLE_EFFECT;
        bytes[1] = Globals.PARTICLE_BURN;
        bytes[2] = target.getKey();
        PacketSender.sendAll(bytes, this.logic.getRoom().getRoomNumber());
    }

    @Override
    public void applyDamage(Mob target) {
        final Player owner = getOwner();
        final boolean isCrit = owner.rollCrit((owner.isSkillMaxed(Skill.SWORD_CINDER)) ? 1 : 0);
        final int damage = calculateDamage(isCrit);
        target.queueDamage(new Damage(damage, true, owner, target, isCrit, this.hitbox[0], target.getHitbox()));
        target.queueBuff(new BuffBurn(this.logic, 4000, owner.getSkillLevel(Skill.SWORD_CINDER) * 0.01,
                owner.isSkillMaxed(Skill.SWORD_CINDER) ? owner.rollDamage() : 0, owner, target));
    }
}
