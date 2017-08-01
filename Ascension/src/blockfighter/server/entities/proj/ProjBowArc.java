package blockfighter.server.entities.proj;

import blockfighter.server.LogicModule;
import blockfighter.server.entities.buff.BuffKnockback;
import blockfighter.server.entities.damage.Damage;
import blockfighter.server.entities.mob.Mob;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.player.skills.SkillBowArc;
import blockfighter.shared.Globals;
import java.awt.geom.Rectangle2D;

public class ProjBowArc extends Projectile {

    public ProjBowArc(final LogicModule l, final Player o, final double x, final double y) {
        super(l, o, x, y, 100);
        this.screenshake = true;
        this.hitbox = new Rectangle2D.Double[1];
        if (o.getFacing() == Globals.RIGHT) {
            this.hitbox[0] = new Rectangle2D.Double(this.x + 35, this.y - 125, 445, 108);
        } else {
            this.hitbox[0] = new Rectangle2D.Double(this.x - 445 - 35, this.y - 125, 445, 108);
        }
    }

    @Override
    public int calculateDamage(final boolean isCrit) {
        final Player owner = getOwner();
        double baseValue = owner.getSkill(Globals.BOW_ARC).getBaseValue();
        double multValue = owner.getSkill(Globals.BOW_ARC).getMultValue();
        double damage = owner.rollDamage() * (baseValue + multValue * owner.getSkillLevel(Globals.BOW_ARC));
        damage = (isCrit) ? owner.criticalDamage(damage) : damage;
        return (int) damage;
    }

    @Override
    public void applyDamage(Player target) {
        final Player owner = getOwner();
        final boolean isCrit = owner.rollCrit();
        final int damage = calculateDamage(isCrit);
        target.queueDamage(new Damage(damage, true, owner, target, isCrit));
        if (owner.isSkillMaxed(Globals.BOW_ARC)) {
            double lifesteal = owner.getSkill(Globals.BOW_ARC).getCustomValue(SkillBowArc.CUSTOMHEADER_LIFESTEAL) / 3;
            double maxLifesteal = owner.getSkill(Globals.BOW_ARC).getCustomValue(SkillBowArc.CUSTOMHEADER_MAXLIFESTEAL) / 3;
            double heal = damage * lifesteal;
            if (heal > owner.getStats()[Globals.STAT_MAXHP] * maxLifesteal) {
                heal = owner.getStats()[Globals.STAT_MAXHP] * maxLifesteal;
            }
            owner.queueHeal((int) heal);
        }
        target.queueBuff(new BuffKnockback(this.logic, 20, (owner.getFacing() == Globals.RIGHT) ? 3 : -3, 0, owner, target));
    }

    @Override
    public void applyDamage(Mob target) {
        final Player owner = getOwner();
        final boolean isCrit = owner.rollCrit();
        final int damage = calculateDamage(isCrit);
        target.queueDamage(new Damage(damage, true, owner, target, isCrit));
        if (owner.isSkillMaxed(Globals.BOW_ARC)) {
            double lifesteal = owner.getSkill(Globals.BOW_ARC).getCustomValue(SkillBowArc.CUSTOMHEADER_LIFESTEAL) / 3;
            double maxLifesteal = owner.getSkill(Globals.BOW_ARC).getCustomValue(SkillBowArc.CUSTOMHEADER_MAXLIFESTEAL) / 3;
            double heal = damage * lifesteal;
            if (heal > owner.getStats()[Globals.STAT_MAXHP] * maxLifesteal) {
                heal = owner.getStats()[Globals.STAT_MAXHP] * maxLifesteal;
            }
            owner.queueHeal((int) heal);
        }
    }
}
