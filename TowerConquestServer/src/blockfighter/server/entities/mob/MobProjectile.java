package blockfighter.server.entities.mob;

import blockfighter.server.LogicModule;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.proj.Projectile;
import java.util.Map;

/**
 *
 * @author Ken Kwan
 */
public abstract class MobProjectile extends Projectile {

    public MobProjectile(final LogicModule l) {
        super(l);
    }

    public MobProjectile(final LogicModule l, final Mob o, final double x, final double y, final int duration) {
        super(l, o, x, y, duration);
    }

    public MobProjectile(final LogicModule l, final Mob o) {
        super(l, o, 0, 0, 0);
    }

    @Override
    public void update() {
        if (this.hitbox[0] == null) {
            return;
        }

        for (final Map.Entry<Byte, Player> pEntry : this.logic.getPlayers().entrySet()) {
            final Player p = pEntry.getValue();
            if (p != getOwner() && !this.pHit.contains(p) && !p.isInvulnerable() && p.intersectHitbox(this.hitbox[0])) {
                this.playerQueue.add(p);
                this.pHit.add(p);
                queueEffect(this);
            }
        }
    }
}