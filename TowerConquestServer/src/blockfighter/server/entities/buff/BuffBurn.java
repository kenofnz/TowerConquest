package blockfighter.server.entities.buff;

import blockfighter.server.Globals;
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
    private long nextDmgTime = 500;

    public BuffBurn(long d, double amp, double dmg, Player o, Player t) {
        super(d);
        setOwner(o);
        setTarget(t);
        dmgAmp = amp;
        dmgPerSec = dmg * 3.75;
    }

    public BuffBurn(long d, double amp, double dmg, Player o, Boss t) {
        super(d);
        setOwner(o);
        setTarget(t);
        dmgAmp = amp;
        dmgPerSec = dmg * 3.75;
    }

    @Override
    public double getDmgTakenAmp() {
        return dmgAmp;
    }

    @Override
    public void update() {
        super.update();
        nextDmgTime -= Globals.LOGIC_UPDATE / 1000000;
        if (dmgPerSec > 0 && nextDmgTime <= 0) {
            nextDmgTime = 500;
            if (getTarget() != null) {
                Point dmgPoint = new Point((int) (getTarget().getHitbox().x), (int) (getTarget().getHitbox().y + getTarget().getHitbox().height / 2));
                getTarget().queueDamage(new Damage((int) (dmgPerSec / 2), false, getOwner(), getTarget(), false, dmgPoint));
            }
            if (getBossTarget() != null) {
                Point dmgPoint = new Point((int) (getBossTarget().getHitbox().x), (int) (getBossTarget().getHitbox().y + getBossTarget().getHitbox().height / 2));
                getBossTarget().queueDamage(new Damage((int) (dmgPerSec / 2), false, getOwner(), getBossTarget(), false, dmgPoint));
            }
        }
    }
}
