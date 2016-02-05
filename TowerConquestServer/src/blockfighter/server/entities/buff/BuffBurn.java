package blockfighter.server.entities.buff;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.boss.Boss;
import blockfighter.server.entities.damage.Damage;
import blockfighter.server.entities.player.Player;
import java.awt.Point;

/**
 *
 * @author Ken Kwan
 */
public class BuffBurn extends Buff implements BuffDmgTakenAmp {

    private final double dmgAmp, dmgPerSec;
    private long lastDmgTime;

    public BuffBurn(final LogicModule l, final int d, final double amp, final double dmg, final Player o, final Player t) {
        super(l, d, o, t);
        this.dmgAmp = amp;
        this.lastDmgTime = l.getTime();
        this.dmgPerSec = dmg * 3.75;
    }

    public BuffBurn(final LogicModule l, final int d, final double amp, final double dmg, final Player o, final Boss t) {
        super(l, d, o, t);
        this.dmgAmp = amp;
        this.lastDmgTime = l.getTime();
        this.dmgPerSec = dmg * 3.75;
    }

    @Override
    public double getDmgTakenAmp() {
        return this.dmgAmp;
    }

    @Override
    public void update() {
        super.update();
        int sinceLastDamage = Globals.nsToMs(this.logic.getTime() - this.lastDmgTime);
        if (this.dmgPerSec > 0 && sinceLastDamage >= 500) {
            this.lastDmgTime = this.logic.getTime();
            if (getTarget() != null) {
                final Point dmgPoint = new Point((int) (getTarget().getHitbox().x),
                        (int) (getTarget().getHitbox().y + getTarget().getHitbox().height / 2));
                getTarget().queueDamage(new Damage((int) (this.dmgPerSec / 2), false, getOwner(), getTarget(), false, dmgPoint));
            }
            if (getBossTarget() != null) {
                final Point dmgPoint = new Point((int) (getBossTarget().getHitbox().x),
                        (int) (getBossTarget().getHitbox().y + getBossTarget().getHitbox().height / 2));
                getBossTarget().queueDamage(new Damage((int) (this.dmgPerSec / 2), false, getOwner(), getBossTarget(), false, dmgPoint));
            }
        }
    }
}
