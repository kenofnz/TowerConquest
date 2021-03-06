package blockfighter.client.entities.emotes;

import blockfighter.client.Core;
import blockfighter.client.entities.player.Player;
import blockfighter.shared.Globals;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class EmoteSleep extends Emote {

    public EmoteSleep(Player owner) {
        super(owner);
        this.frame = 0;
        this.frameDuration = 250;
    }

    @Override
    public void update() {
        super.update();
        if (Globals.nsToMs(Core.getLogicModule().getTime() - this.lastFrameTime) >= this.frameDuration) {
            if (Globals.Emotes.SLEEP.getSprite() != null && this.frame <= Globals.Emotes.SLEEP.getSprite().length) {
                if (this.frame >= Globals.Emotes.SLEEP.getSprite().length) {
                    this.frame = 0;
                } else {
                    this.frame++;
                }
            }
            this.lastFrameTime = Core.getLogicModule().getTime();
        }
    }

    @Override
    public void draw(final Graphics2D g) {
        if (Globals.Emotes.SLEEP.getSprite() == null) {
            return;
        }
        if (this.frame >= Globals.Emotes.SLEEP.getSprite().length) {
            return;
        }
        final Point p = this.owner.getPos();
        if (p != null) {
            this.x = p.x + 18;
            this.y = p.y - 130;
        }
        final BufferedImage sprite = Globals.Emotes.SLEEP.getSprite()[this.frame];
        final int drawSrcX = this.x;
        final int drawSrcY = this.y;
        final int drawDscY = drawSrcY + sprite.getHeight();
        final int drawDscX = drawSrcX + sprite.getWidth();
        g.drawImage(sprite, drawSrcX, drawSrcY, drawDscX, drawDscY, 0, 0, sprite.getWidth(), sprite.getHeight(), null);
    }
}
