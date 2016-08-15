package blockfighter.client.entities.emotes;

import blockfighter.client.Globals;
import blockfighter.client.entities.player.Player;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;

public class EmoteSweat extends Emote {

    public EmoteSweat(Player owner) {
        super(owner);
        this.frame = 0;
        this.frameDuration = 75;
    }

    @Override
    public void update() {
        super.update();
        if (Globals.nsToMs(logic.getTime() - this.lastFrameTime) >= this.frameDuration) {
            if (EMOTE_SPRITE != null && this.frame < EMOTE_SPRITE[Globals.EMOTE_SWEAT].length - 1) {
                this.frame++;
            }
            this.lastFrameTime = logic.getTime();
        }
    }

    @Override
    public void draw(final Graphics2D g) {
        if (EMOTE_SPRITE[Globals.EMOTE_SWEAT] == null) {
            return;
        }
        if (this.frame >= EMOTE_SPRITE[Globals.EMOTE_SWEAT].length) {
            return;
        }
        final Point p = this.owner.getPos();
        if (p != null) {
            this.x = p.x - 22;
            this.y = p.y - 118;
        }
        final BufferedImage sprite = EMOTE_SPRITE[Globals.EMOTE_SWEAT][this.frame];
        final int drawSrcX = this.x;
        final int drawSrcY = this.y;
        final int drawDscY = drawSrcY + sprite.getHeight();
        final int drawDscX = drawSrcX + sprite.getWidth();
        g.drawImage(sprite, drawSrcX, drawSrcY, drawDscX, drawDscY, 0, 0, sprite.getWidth(), sprite.getHeight(), null);
    }
}