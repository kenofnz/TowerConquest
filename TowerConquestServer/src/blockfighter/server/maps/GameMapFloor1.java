package blockfighter.server.maps;

import blockfighter.server.Globals;
import blockfighter.server.LogicModule;
import blockfighter.server.entities.boss.Boss;
import blockfighter.server.entities.boss.Lightning.BossLightning;
import java.awt.geom.Rectangle2D;

public class GameMapFloor1 extends GameMap {

    public GameMapFloor1() {
        mapID = 1;
        isPvP = false;
        platforms = new Rectangle2D.Double[8];
        platforms[0] = new Rectangle2D.Double(-50, 600, 3750, 30);
        platforms[1] = new Rectangle2D.Double(200, 350, 300, 30);
        platforms[2] = new Rectangle2D.Double(700, 100, 300, 30);
        platforms[3] = new Rectangle2D.Double(1200, -150, 300, 30);
        platforms[4] = new Rectangle2D.Double(1700, -150, 300, 30);
        platforms[5] = new Rectangle2D.Double(2200, -150, 300, 30);
        platforms[6] = new Rectangle2D.Double(2700, 100, 300, 30);
        platforms[7] = new Rectangle2D.Double(3200, 350, 300, 30);
        boundary[Globals.MAP_LEFT] = 0.0;
        boundary[Globals.MAP_RIGHT] = 3700.0;
    }

    @Override
    public Boss[] getBosses(LogicModule l) {
        Boss[] bosses = new Boss[1];
        bosses[0] = new BossLightning(l, (byte) 0, this, 1500, 500);
        return bosses;
    }
}
