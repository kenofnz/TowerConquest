package blockfighter.server.entities.player.skills.shield;

import blockfighter.server.LogicModule;
import blockfighter.server.entities.player.Player;
import blockfighter.server.entities.player.skills.Skill;
import blockfighter.server.entities.proj.ProjShieldMagnetize;
import blockfighter.server.net.PacketSender;
import blockfighter.shared.Globals;
import java.util.ArrayList;
import java.util.HashMap;

public class SkillShieldMagnetize extends Skill {

    ArrayList<Player> playersCaught;

    public static final String[] CUSTOM_DATA_HEADERS;
    public static final HashMap<String, Double> CUSTOM_VALUES;

    public static final byte SKILL_CODE = Globals.SHIELD_MAGNETIZE;
    public static final boolean IS_PASSIVE;
    public static final byte REQ_WEAPON;
    public static final long MAX_COOLDOWN;

    public static final double BASE_VALUE, MULT_VALUE;
    public static final int REQ_LEVEL;
    public static final byte REQ_EQUIP_SLOT = Globals.EQUIP_OFFHAND;
    public static final byte PLAYER_STATE = Player.PLAYER_STATE_SHIELD_MAGNETIZE;
    public static final int SKILL_DURATION = 600;

    static {
        String[] data = Globals.loadSkillRawData(SKILL_CODE);
        HashMap<String, Integer> dataHeaders = Globals.getDataHeaders(data);

        CUSTOM_DATA_HEADERS = Globals.getSkillCustomHeaders(data, dataHeaders);
        CUSTOM_VALUES = new HashMap<>(CUSTOM_DATA_HEADERS.length);

        REQ_WEAPON = Globals.loadSkillReqWeapon(data, dataHeaders);
        MAX_COOLDOWN = (long) Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_MAXCOOLDOWN_HEADER);
        BASE_VALUE = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_BASEVALUE_HEADER);
        MULT_VALUE = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_MULTVALUE_HEADER);
        IS_PASSIVE = Globals.loadBooleanValue(data, dataHeaders, Globals.SKILL_PASSIVE_HEADER);
        REQ_LEVEL = Globals.loadSkillReqLevel(data, dataHeaders);

        for (String customHeader : CUSTOM_DATA_HEADERS) {
            CUSTOM_VALUES.put(customHeader, Globals.loadDoubleValue(data, dataHeaders, customHeader));
        }
    }

    public SkillShieldMagnetize(final LogicModule l) {
        super(l);
    }

    @Override
    public void updateSkillUse(Player player) {
        final long duration = Globals.nsToMs(this.logic.getTime() - player.getSkillCastTime());
        final int radius = 400;
        if (player.getSkillCounter() == 0) {
            PacketSender.sendParticle(this.logic, Globals.Particles.SHIELD_MAGNETIZE_START.getParticleCode(), player.getKey());
            player.incrementSkillCounter();
        }
        if (Globals.hasPastDuration(duration, 200) && player.getSkillCounter() == 1) {
            PacketSender.sendParticle(this.logic, Globals.Particles.SHIELD_MAGNETIZE_BURST.getParticleCode(), player.getKey());
            if (this.logic.getRoomData().getMap().isPvP()) {
                this.playersCaught = this.logic.getRoomData().getPlayersInRange(player, radius);
                if (!this.playersCaught.isEmpty()) {
                    this.playersCaught.forEach((p) -> {
                        PacketSender.sendParticle(this.logic, Globals.Particles.SHIELD_MAGNETIZE.getParticleCode(), player.getKey(), p.getKey());
                    });
                }
            }
            player.incrementSkillCounter();
        }

        if (Globals.hasPastDuration(duration, 200) && player.getSkillCounter() == 2) {
            if (this.logic.getRoomData().getMap().isPvP()) {
                if (!this.playersCaught.isEmpty()) {
                    int numOfTicks = (int) ((500 - duration) / Globals.nsToMs(Globals.SERVER_LOGIC_UPDATE));
                    this.playersCaught.forEach((p) -> {
                        if (numOfTicks > 0) {
                            double distanceX = (player.getX() - p.getX()) / numOfTicks;
                            double distanceY = (player.getY() - p.getY()) / numOfTicks;
                            p.queueXChange(distanceX);
                            p.queueYChange(distanceY);
                        }
                    });
                }
            }
        }

        if (Globals.hasPastDuration(duration, 500) && player.getSkillCounter() == 2) {
            final ProjShieldMagnetize proj = new ProjShieldMagnetize(this.logic, player, player.getX(), player.getY());
            this.logic.queueAddProj(proj);
            player.incrementSkillCounter();
        }

        player.updateSkillEnd(duration, getSkillDuration(), false, false);
    }

    @Override
    public void updatePlayerAnimState(Player player) {
        final long frameDuration = Globals.nsToMs(this.logic.getTime() - player.getLastFrameTime());
        player.setAnimState(Globals.PLAYER_ANIM_STATE_BUFF);
        if (player.getSkillCounter() == 1) {
            player.setFrame((byte) 0);
        } else if (frameDuration >= 30 && player.getFrame() < 4) {
            player.setFrame((byte) (player.getFrame() + 1));
            player.setLastFrameTime(this.logic.getTime());
        }
    }
}
