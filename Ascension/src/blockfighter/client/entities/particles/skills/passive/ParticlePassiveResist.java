package blockfighter.client.entities.particles.skills.passive;

import blockfighter.client.Core;
import blockfighter.client.entities.particles.Particle;
import blockfighter.shared.Globals;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ParticlePassiveResist extends Particle {

    public ParticlePassiveResist(final int x, final int y) {
        super(x, y, Globals.RIGHT);
        this.frame = 0;
        this.frameDuration = 25;
        this.duration = 300;
    }

    @Override
    public void update() {
        super.update();

        if (Globals.nsToMs(Core.getLogicModule().getTime() - this.lastFrameTime) >= this.frameDuration) {
            if (Globals.Particles.PASSIVE_RESIST.getSprite() != null && this.frame < Globals.Particles.PASSIVE_RESIST.getSprite().length - 1) {
                this.frame++;
            }
            this.lastFrameTime = Core.getLogicModule().getTime();
        }
    }

    @Override
    public void draw(final Graphics2D g) {
        if (Globals.Particles.PASSIVE_RESIST.getSprite() == null) {
            return;
        }
        if (this.frame >= Globals.Particles.PASSIVE_RESIST.getSprite().length) {
            return;
        }
        final BufferedImage sprite = Globals.Particles.PASSIVE_RESIST.getSprite()[this.frame];
        final int drawSrcX = this.x - sprite.getWidth() / 2;
        final int drawSrcY = this.y - sprite.getHeight() + 50;
        final int drawDscY = drawSrcY + sprite.getHeight();
        final int drawDscX = drawSrcX + sprite.getWidth();
        g.drawImage(sprite, drawSrcX, drawSrcY, drawDscX, drawDscY, 0, 0, sprite.getWidth(), sprite.getHeight(), null);
        g.setColor(Color.WHITE);
    }
}