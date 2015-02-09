package blockfighter.client.screen;

import blockfighter.client.Globals;
import blockfighter.client.SaveData;
import blockfighter.client.entities.particles.Particle;
import blockfighter.client.entities.particles.ParticleMenuSmoke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ken Kwan
 */
public abstract class ScreenMenu extends Screen {

    protected double lastUpdateTime = System.nanoTime();
    protected static ConcurrentHashMap<Integer, Particle> particles = new ConcurrentHashMap<>(20);
    private Rectangle2D.Double[] menuBox = new Rectangle2D.Double[7];
    
    protected DecimalFormat df = new DecimalFormat("###,###,##0.##");

    public ScreenMenu() {
        if (!particles.containsKey(0)) {
            particles.put(0, new ParticleMenuSmoke( 0, 0, 0));
            particles.put(1, new ParticleMenuSmoke(1, 1280, 0));
        }
        for (int i = 0; i < menuBox.length; i++) {
            menuBox[i] = new Rectangle2D.Double(20, 27 + 50 * i, 180, 50);
        }
    }

    @Override
    public ConcurrentHashMap<Integer, Particle> getParticles() {
        return particles;
    }

    @Override
    public void update() {
        double now = System.nanoTime(); //Get time now
        if (now - lastUpdateTime >= Globals.LOGIC_UPDATE) {
            updateParticles(particles);
            lastUpdateTime = now;
        }
        try {
            Thread.sleep(0, 1);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScreenInventory.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void draw(Graphics2D g) {
        for (Map.Entry<Integer, Particle> pEntry : particles.entrySet()) {
            pEntry.getValue().draw(g);
        }
    }

    public void drawMenuButton(Graphics2D g) {
        g.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);

        BufferedImage button = Globals.MENU_BUTTON[Globals.BUTTON_MENUS];
        for (Rectangle2D.Double menuBox1 : menuBox) {
            g.drawImage(button, (int) menuBox1.x, (int) menuBox1.y, null);
        }
        g.setFont(Globals.ARIAL_24PT);
        drawStringOutline(g, "Stats", 40, 62, 2);
        drawStringOutline(g, "Inventory", 40, 112, 2);
        drawStringOutline(g, "Upgrades", 40, 162, 2);
        drawStringOutline(g, "Skills", 40, 212, 2);
        drawStringOutline(g, "Server List", 40, 262, 2);
        drawStringOutline(g, "Key Bindings", 40, 312, 2);
        drawStringOutline(g, "Characters", 40, 362, 2);

        g.setColor(Color.WHITE);
        g.drawString("Stats", 40, 62);
        g.drawString("Inventory", 40, 112);
        g.drawString("Upgrades", 40, 162);
        g.drawString("Skills", 40, 212);
        g.drawString("Server List", 40, 262);
        g.drawString("Key Bindings", 40, 312);
        g.drawString("Characters", 40, 362);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            for (int i = 0; i < menuBox.length; i++) {
                if (menuBox[i].contains(e.getPoint())) {
                    SaveData.saveData(logic.getSelectedChar().getSaveNum(), logic.getSelectedChar());
                    switch (i) {
                        case 0:
                            logic.setScreen(new ScreenStats());
                            break;
                        case 1:
                            logic.setScreen(new ScreenInventory());
                            break;
                        case 2:
                            logic.setScreen(new ScreenUpgrade());
                            break;
                        case 3:
                            logic.setScreen(new ScreenSkills());
                            break;
                        case 4:
                            logic.sendLogin();
                            break;
                        case 5:
                            logic.setScreen(new ScreenKeyBind());
                            break;
                        case 6:
                            logic.setSelectedChar(null);
                            logic.setScreen(new ScreenSelectChar());
                            break;
                    }
                }
            }
        }
    }
}
