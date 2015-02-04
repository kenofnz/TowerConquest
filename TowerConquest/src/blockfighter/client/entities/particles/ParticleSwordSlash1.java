package blockfighter.client.entities.particles;

import blockfighter.client.Globals;
import blockfighter.client.LogicModule;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ParticleSwordSlash1 extends Particle {

    public ParticleSwordSlash1(LogicModule l, int k, int x, int y, byte f) {
        super(l, k, x, y, f);
        frame = 0;
        frameDuration = 25;
        duration = 200;
    }

    @Override
    public void update() {
        super.update();
        frameDuration -= Globals.LOGIC_UPDATE / 1000000;
        if (frameDuration <= 0) {
            frameDuration = 25;
            if (frame < PARTICLE_SPRITE[Globals.PARTICLE_SWORD_SLASH1].length) {
                frame++;
            }
        }
    }

    @Override
    public void draw(Graphics2D g) {
        if (PARTICLE_SPRITE[Globals.PARTICLE_SWORD_SLASH1] == null) {
            loadParticles();
        }
        if (frame >= PARTICLE_SPRITE[Globals.PARTICLE_SWORD_SLASH1].length) {
            return;
        }
        BufferedImage sprite = PARTICLE_SPRITE[Globals.PARTICLE_SWORD_SLASH1][frame];
        int drawSrcX = x + ((facing == Globals.RIGHT) ? -100 : sprite.getWidth() - 60);
        int drawSrcY = y - 35;
        int drawDscY = drawSrcY + sprite.getHeight();
        int drawDscX = x + ((facing == Globals.RIGHT) ? sprite.getWidth() - 100 : -60);
        g.drawImage(sprite, drawSrcX, drawSrcY, drawDscX, drawDscY, 0, 0, sprite.getWidth(), sprite.getHeight(), null);
    }
}