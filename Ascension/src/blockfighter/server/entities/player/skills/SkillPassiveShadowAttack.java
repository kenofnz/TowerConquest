package blockfighter.server.entities.player.skills;

import blockfighter.server.LogicModule;
import blockfighter.server.entities.damage.Damage;
import blockfighter.server.entities.player.Player;
import blockfighter.server.net.PacketSender;
import blockfighter.shared.Globals;
import java.util.HashMap;

public class SkillPassiveShadowAttack extends SkillPassive {

    private static final byte SKILL_CODE = Globals.PASSIVE_SHADOWATTACK;
    private static final boolean IS_PASSIVE;
    private static final byte REQ_WEAPON;
    private static final double MAX_COOLDOWN;

    private static final double BASE_VALUE, MULT_VALUE;

    static {
        String[] data = Globals.loadSkillData(SKILL_CODE);
        HashMap<String, Integer> dataHeaders = Globals.getDataHeaders(data, null);

        REQ_WEAPON = Globals.loadReqWeapon(data, dataHeaders);
        MAX_COOLDOWN = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_MAXCOOLDOWN_HEADER);
        BASE_VALUE = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_BASEVALUE_HEADER);
        MULT_VALUE = Globals.loadDoubleValue(data, dataHeaders, Globals.SKILL_MULTVALUE_HEADER);
        IS_PASSIVE = Globals.loadBooleanValue(data, dataHeaders, Globals.SKILL_PASSIVE_HEADER);
    }

    public SkillPassiveShadowAttack(final LogicModule l) {
        super(l);
    }

    @Override
    public double getBaseValue() {
        return BASE_VALUE;
    }

    @Override
    public double getMaxCooldown() {
        return MAX_COOLDOWN;
    }

    @Override
    public double getMultValue() {
        return MULT_VALUE;
    }

    @Override
    public Byte getReqWeapon() {
        return REQ_WEAPON;
    }

    @Override
    public byte getSkillCode() {
        return SKILL_CODE;
    }

    @Override
    public boolean isPassive() {
        return IS_PASSIVE;
    }

    public void updateSkillUse(final Player player, final Damage dmg) {
        if (Globals.rng(100) + 1 <= 20 + getBaseValue() + getMultValue() * player.getSkillLevel(Globals.PASSIVE_SHADOWATTACK)) {
            player.getSkill(Globals.PASSIVE_SHADOWATTACK).setCooldown();
            player.sendCooldown(Globals.PASSIVE_SHADOWATTACK);
            PacketSender.sendParticle(this.logic.getRoom().getRoomNumber(), Globals.PARTICLE_PASSIVE_SHADOWATTACK, dmg.getDmgPoint().x, dmg.getDmgPoint().y);
            if (dmg.getTarget() != null) {
                final Damage shadow = new Damage((int) (dmg.getDamage() * 0.5D), false, dmg.getOwner(), dmg.getTarget(), false,
                        dmg.getDmgPoint());
                shadow.setHidden(true);
                shadow.setCanReflect(false);
                dmg.getTarget().queueDamage(shadow);
            } else if (dmg.getMobTarget() != null) {
                final Damage shadow = new Damage((int) (dmg.getDamage() * 0.5D), false, dmg.getOwner(), dmg.getMobTarget(), false,
                        dmg.getDmgPoint());
                shadow.setHidden(true);
                shadow.setCanReflect(false);
                dmg.getMobTarget().queueDamage(shadow);
            }
        }
    }
}
