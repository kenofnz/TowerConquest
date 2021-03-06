package blockfighter.shared;

import blockfighter.client.entities.emotes.*;
import blockfighter.client.entities.particles.*;
import blockfighter.client.entities.particles.skills.bow.*;
import blockfighter.client.entities.particles.skills.other.*;
import blockfighter.client.entities.particles.skills.passive.*;
import blockfighter.client.entities.particles.skills.shield.*;
import blockfighter.client.entities.particles.skills.sword.*;
import blockfighter.client.entities.particles.skills.utility.*;
import blockfighter.client.entities.player.Player;
import com.esotericsoftware.minlog.Log;
import static com.esotericsoftware.minlog.Log.*;
import com.esotericsoftware.minlog.Log.Logger;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageProducer;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.JTextArea;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

public class Globals {

    public static DecimalFormat NUMBER_FORMAT = new DecimalFormat();
    public static DecimalFormat TIME_NUMBER_FORMAT = new DecimalFormat();

    public static boolean SKIP_TITLE = false;
    public static String SERVER_ADDRESS;

    public static boolean SERVER_MODE = false, CLIENT_MODE = true;

    public static final String SAVE_FILE_DIRECTORY = System.getProperty("user.home") + File.separator + "K-Games" + File.separator + "Ascension";
    public static final String SETTINGS_FILE = SAVE_FILE_DIRECTORY + File.separator + "settings.properties";

    public static final String DEV_PASSPHRASE = "amFwAkjuy0K/lSvUUyZvdiIFdn/Dzu/OAxStgUEdLKk=";
    public static final String COLON_SPACE_TEXT = ": ";

    public static boolean TEST_MAX_LEVEL = false,
            DEBUG_MODE = false;

    public final static byte GAME_MAJOR_VERSION = 0,
            GAME_MINOR_VERSION = 25,
            GAME_UPDATE_NUMBER = 0;

    private final static String GAME_DEV_STATE = "ALPHA";

    public final static String GAME_RELEASE_VERSION = GAME_DEV_STATE + " " + GAME_MAJOR_VERSION + "." + GAME_MINOR_VERSION + "."
            + GAME_UPDATE_NUMBER;

    public final static String GAME_NAME = "{Soul}Ascension";
    public final static String WINDOW_TITLE = GAME_NAME + " " + GAME_RELEASE_VERSION;
    public static boolean WINDOW_SCALE_ENABLED = true;
    public static double WINDOW_SCALE;
    public final static int WINDOW_WIDTH = 1280;
    public final static int WINDOW_HEIGHT = 720;

    // Render globals
    public final static Font ARIAL_30PT = new Font("Arial", Font.PLAIN, 30);
    public final static Font ARIAL_12PT = new Font("Arial", Font.PLAIN, 12);
    public final static Font ARIAL_15PT = new Font("Arial", Font.BOLD, 15);
    public final static Font ARIAL_15PTITALIC = new Font("Arial", Font.ITALIC, 15);
    public final static Font ARIAL_24PT = new Font("Arial", Font.PLAIN, 24);
    public final static Font ARIAL_24PTBOLD = new Font("Arial", Font.BOLD, 24);
    public final static Font ARIAL_18PT = new Font("Arial", Font.PLAIN, 18);
    public final static Font ARIAL_18PTBOLD = new Font("Arial", Font.BOLD, 18);
    public final static Font ARIAL_19PTBOLD = new Font("Arial", Font.BOLD, 19);
    public final static Font ARIAL_21PTBOLD = new Font("Arial", Font.BOLD, 21);

    public static Font MULAN_24PT;
    public static Font TITLE_FONT;

    public final static byte MAX_NAME_LENGTH = 15;

    private final static Random RNG = new Random();

    // Render 60 fps in microseconds
    public final static int RENDER_FPS = 60;
    public final static long RENDER_UPDATE = 1000000 / RENDER_FPS;

    public final static double CLIENT_LOGIC_TICKS_PER_SEC = 60D;
    public final static double CLIENT_LOGIC_UPDATE = 1000000000D / CLIENT_LOGIC_TICKS_PER_SEC;

    // public final static double SEND_KEYDOWN_PER_SEC = 10D;
    public final static double SEND_KEYDOWN_UPDATE = 100000000D;

    // public final static double REQUESTALL_TICKS_PER_SEC = 1D;
    public final static double REQUESTALL_UPDATE = 10000000000D;

    public final static double PINGS_PER_SEC = 2D;
    public final static double PING_UPDATE = 1000000000D / PINGS_PER_SEC;

    public final static double PROCESS_QUEUES_PER_SEC = 130D;
    public final static double QUEUES_UPDATE = 1000000000D / PROCESS_QUEUES_PER_SEC;

    public final static byte RIGHT = 0, LEFT = 1, DOWN = 2, UP = 3;

    public final static int NUM_SOUND_EFFECTS = 0;

    public final static int DAMAGE_DISPLAY_OFF = 0,
            DAMAGE_DISPLAY_ARC = 1,
            DAMAGE_DISPLAY_ASCEND = 2,
            DAMAGE_DISPLAY_STACK = 3;

    public enum ClientOptions {
        WINDOW_SCALE("windowscale", Integer.class, Integer.valueOf(100), "Window Scale"),
        SOUND_ENABLE("soundenable", Boolean.class, true, "Sound Enabled"),
        VOLUME_LEVEL("volumelevel", Integer.class, Integer.valueOf(50), "Volume Level Control"),
        DAMAGE_FORMAT("damageformat", Integer.class, DAMAGE_DISPLAY_ARC, "Damage Display Format"),
        PERFORMANCE_DISPLAY("performancedisplay", Boolean.class, true, "Show FPS and Ping");

        private final String key;
        private final Class type;
        private final Object defaultValue;
        private final String desc;
        private Object value = null;

        private static final Map<String, ClientOptions> lookup = new HashMap<>();

        ClientOptions(String key, Class type, Object defaultValue, String desc) {
            this.key = key;
            this.type = type;
            this.defaultValue = defaultValue;
            this.desc = desc;
        }

        static {
            for (ClientOptions config : ClientOptions.values()) {
                lookup.put(config.getKey(), config);
            }
        }

        public static ClientOptions get(String key) {
            return lookup.get(key);
        }

        public Class getType() {
            return this.type;
        }

        public String getKey() {
            return this.key;
        }

        public void setValue(String value) {
            if (this.type == Byte.class) {
                this.value = Byte.valueOf(value);
            } else if (this.type == Integer.class) {
                this.value = Integer.valueOf(value);
            } else if (this.type == Double.class) {
                this.value = Double.valueOf(value);
            } else if (this.type == Float.class) {
                this.value = Float.valueOf(value);
            } else if (this.type == Long.class) {
                this.value = Long.valueOf(value);
            } else if (this.type == Boolean.class) {
                this.value = Boolean.valueOf(value);
            } else if (this.type == String.class) {
                this.value = value;
            }
        }

        public Object getValue() {
            return (this.value == null) ? this.defaultValue : this.value;
        }

        @Override
        public String toString() {
            return this.desc + COLON_SPACE_TEXT + this.getValue();
        }

        public String getDesc() {
            return this.desc;
        }
    }

    public static byte getEquipType(final int i) {
        if (i >= 100000 && i <= 109999) {
            // Swords
            return Globals.ITEM_SWORD;
        } else if (i >= 110000 && i <= 119999) {
            // Shields
            return Globals.ITEM_SHIELD;
        } else if (i >= 120000 && i <= 129999) {
            // Bows
            return Globals.ITEM_BOW;
        } else if (i >= 130000 && i <= 199999) {
            // Arrow Enchantments
            return Globals.ITEM_ARROW;
        } else if (i >= 200000 && i <= 209999) {
            return Globals.ITEM_HEAD;
        } else if (i >= 300000 && i <= 309999) {
            return Globals.ITEM_CHEST;
        } else if (i >= 400000 && i <= 409999) {
            return Globals.ITEM_PANTS;
        } else if (i >= 500000 && i <= 509999) {
            return Globals.ITEM_SHOULDER;
        } else if (i >= 600000 && i <= 609999) {
            return Globals.ITEM_GLOVE;
        } else if (i >= 700000 && i <= 709999) {
            return Globals.ITEM_SHOE;
        } else if (i >= 800000 && i <= 809999) {
            return Globals.ITEM_BELT;
        } else if (i >= 900000 && i <= 909999) {
            return Globals.ITEM_RING;
        } else if (i >= 1000000 && i <= 1009999) {
            return Globals.ITEM_AMULET;
        }
        return -1;
    }

    public enum GameMaps {
        FIELD((byte) 0x00, "Field", "GameMapArena"),
        GRAND_LIBRARY((byte) 0x01, "Grand Library", "GameMapAsymArena"),
        DEBUG((byte) 0x02, "Debug Room", "GameMapDebug");

        private final byte mapCode;
        private Class<? extends blockfighter.client.maps.GameMap> clientGameMapClass;
        private Class<? extends blockfighter.server.maps.GameMap> serverGameMapClass;
        private final String mapName;
        private static final Map<Byte, GameMaps> lookup = new HashMap<>();

        static {
            for (GameMaps gameMap : GameMaps.values()) {
                lookup.put(gameMap.getMapCode(), gameMap);
            }
        }

        public static GameMaps get(byte code) {
            return lookup.get(code);
        }

        GameMaps(byte mapCode, String mapName, String className) {
            this.mapCode = mapCode;
            this.mapName = mapName;
            try {
                if (CLIENT_MODE) {
                    this.clientGameMapClass = Class.forName("blockfighter.client.maps." + className).asSubclass(blockfighter.client.maps.GameMap.class);
                }
                if (SERVER_MODE) {
                    this.serverGameMapClass = Class.forName("blockfighter.server.maps." + className).asSubclass(blockfighter.server.maps.GameMap.class);
                }
            } catch (ClassNotFoundException ex) {
                logError("[FATAL] Could not find GameMap class.", ex);
            }
        }

        public byte getMapCode() {
            return this.mapCode;
        }

        public String getMapName() {
            return this.mapName;
        }

        public blockfighter.server.maps.GameMap newServerGameMap() {
            try {
                return this.serverGameMapClass.newInstance();
            } catch (Exception ex) {
                logError(ex.toString(), ex);
            }
            return null;
        }

        public blockfighter.client.maps.GameMap newClientGameMap() {
            try {
                return this.clientGameMapClass.newInstance();
            } catch (Exception ex) {
                logError(ex.toString(), ex);
            }
            return null;
        }
    }

    public enum Emotes {
        ALERT((byte) 0x00, "alert", 1, EmoteAlert.class),
        QUESTION((byte) 0x01, "question", 1, EmoteQuestion.class),
        SWEAT((byte) 0x02, "sweat", 5, EmoteSweat.class),
        SLEEP((byte) 0x03, "sleep", 3, EmoteSleep.class),
        ANGRY((byte) 0x04, "angry", 1, EmoteAngry.class),
        WELL_PLAYED((byte) 0x05, "wp", 1, EmoteWellPlayed.class),
        GOOD_GAME((byte) 0x06, "gg", 1, EmoteGoodGame.class),
        EMOTE8((byte) 0x07, null, 0, null),
        EMOTE9((byte) 0x08, null, 0, null),
        EMOTE10((byte) 0x09, null, 0, null);

        private final byte emoteCode;
        private final Class<? extends Emote> emoteClass;
        private BufferedImage[] sprite;
        private final int numFrames;
        private final String spriteFolder;

        private static final Map<Byte, Emotes> lookup = new HashMap<Byte, Emotes>();
        private static final Class[] EMOTE_PARAMS = {Player.class};

        static {
            for (Emotes emote : Emotes.values()) {
                lookup.put(emote.getEmoteCode(), emote);
            }
        }

        public static Emotes get(byte code) {
            return lookup.get(code);
        }

        Emotes(byte emoteCode, String spriteFolder, int numFrames, Class<? extends Emote> emoteClass) {
            this.emoteCode = emoteCode;
            this.spriteFolder = spriteFolder;
            this.numFrames = numFrames;
            this.emoteClass = emoteClass;
        }

        public byte getEmoteCode() {
            return this.emoteCode;
        }

        public void setSprite(BufferedImage[] sprite) {
            this.sprite = sprite;
        }

        public BufferedImage[] getSprite() {
            return this.sprite;
        }

        public int getNumFrames() {
            return this.numFrames;
        }

        public String getSpriteFolder() {
            return this.spriteFolder;
        }

        public Emote newEmote(Object... parameters) {
            try {
                Constructor<? extends Emote> constructor = this.emoteClass.getDeclaredConstructor(EMOTE_PARAMS);
                return constructor.newInstance(parameters);
            } catch (Exception ex) {
                logError(ex.toString(), ex);
            }
            return null;
        }
    }

    public static final Class[] PARTICLE_PARAM_POS_AND_FACING = {int.class, int.class, byte.class};
    public static final Class[] PARTICLE_PARAM_PLAYER = {Player.class};
    public static final Class[] PARTICLE_PARAM_PLAYER_AND_TARGET = {Player.class, Player.class};
    public static final Class[] PARTICLE_PARAM_FACING_AND_PLAYER = {byte.class, Player.class};
    public static final Class[] PARTICLE_PARAM_POS = {int.class, int.class};

    public enum Particles {
        BLOOD((byte) 0x00, "blood", ParticleBlood.class, PARTICLE_PARAM_POS_AND_FACING),
        BLOOD_DEATH_EMITTER((byte) 0x01, null, ParticleBloodEmitter.class, PARTICLE_PARAM_PLAYER),
        BLOOD_EMITTER((byte) 0x02, null, ParticleBloodEmitter.class, PARTICLE_PARAM_PLAYER_AND_TARGET),
        BOW_ARC((byte) 0x03, "arc", ParticleBowArc.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_FROSTARROW((byte) 0x04, "frostarrow", ParticleBowFrostArrow.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_FROSTARROW_EMITTER((byte) 0x05, null, ParticleBowFrostArrowEmitter.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_POWER((byte) 0x06, "power", ParticleBowPower.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_POWER_CHARGE((byte) 0x07, "powercharge", ParticleBowPowerCharge.class, PARTICLE_PARAM_PLAYER),
        BOW_POWER_PARTICLE((byte) 0x08, "power2", ParticleBowPowerParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_RAPID((byte) 0x09, "rapid", ParticleBowRapid.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_RAPID2((byte) 0x0A, "rapid2", ParticleBowRapid2.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_STORM_ARROW((byte) 0x0B, "stormarrow", ParticleBowStormArrow.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_STORM_EMITTER((byte) 0x0C, null, ParticleBowStormEmitter.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_VOLLEY_BOW((byte) 0x0D, "volleybow", ParticleBowVolleyBow.class, PARTICLE_PARAM_FACING_AND_PLAYER),
        BOW_VOLLEY_ARROW((byte) 0x0E, "volley", ParticleBowVolleyArrow.class, PARTICLE_PARAM_POS_AND_FACING),
        BOW_VOLLEY_BUFF_EMITTER((byte) 0x0F, null, ParticleBowVolleyBuffEmitter.class, PARTICLE_PARAM_PLAYER),
        BOW_VOLLEY_BUFF_PARTICLE((byte) 0x10, "volleybuff", ParticleBowVolleyBuffParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        BURN_BUFF_EMITTER((byte) 0x11, null, ParticleBurnBuffEmitter.class, PARTICLE_PARAM_PLAYER),
        BURN_BUFF_PARTICLE((byte) 0x12, "burn", ParticleBurnBuffParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        PASSIVE_BARRIER((byte) 0x13, "barrier", ParticlePassiveBarrier.class, PARTICLE_PARAM_POS),
        PASSIVE_RESIST((byte) 0x14, "resist", ParticlePassiveResist.class, PARTICLE_PARAM_POS),
        PASSIVE_SHADOWATTACK((byte) 0x15, "shadowattack", ParticlePassiveShadowAttack.class, PARTICLE_PARAM_POS),
        PASSIVE_STATIC((byte) 0x16, null, ParticlePassiveStatic.class, PARTICLE_PARAM_PLAYER_AND_TARGET),
        SHIELD_CHARGE((byte) 0x17, "charge", ParticleShieldCharge.class, PARTICLE_PARAM_FACING_AND_PLAYER),
        SHIELD_CHARGE_PARTICLE((byte) 0x18, "charge2", ParticleShieldChargeParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        SHIELD_MAGNETIZE((byte) 0x19, null, ParticleShieldMagnetize.class, PARTICLE_PARAM_PLAYER_AND_TARGET),
        SHIELD_MAGNETIZE_BURST((byte) 0x1A, "magnetizeburst", ParticleShieldMagnetizeBurst.class, PARTICLE_PARAM_PLAYER),
        SHIELD_MAGNETIZE_START((byte) 0x1B, "magnetizestart", ParticleShieldMagnetizeStart.class, PARTICLE_PARAM_PLAYER),
        SHIELD_REFLECT_BUFF((byte) 0x1C, "reflectbuff", ParticleShieldReflectBuff.class, PARTICLE_PARAM_POS_AND_FACING),
        SHIELD_REFLECT_CAST((byte) 0x1D, "reflectcast", ParticleShieldReflectCast.class, PARTICLE_PARAM_PLAYER),
        SHIELD_REFLECT_EMITTER((byte) 0x1E, null, ParticleShieldReflectEmitter.class, PARTICLE_PARAM_PLAYER),
        SHIELD_REFLECT_HIT((byte) 0x1F, "reflecthit", ParticleShieldReflectHit.class, PARTICLE_PARAM_POS),
        SHIELD_ROAR((byte) 0x20, "roar", ParticleShieldRoar.class, PARTICLE_PARAM_FACING_AND_PLAYER),
        SHIELD_ROARHIT((byte) 0x21, "roarhit", ParticleShieldRoarHit.class, PARTICLE_PARAM_PLAYER),
        SWORD_CINDER((byte) 0x22, "cinder", ParticleSwordCinder.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_GASH1((byte) 0x23, "gash1", ParticleSwordGash.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_GASH2((byte) 0x24, "gash2", ParticleSwordGash2.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_PHANTOM((byte) 0x25, "phantom", ParticleSwordPhantom.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_PHANTOM2((byte) 0x26, "phantomslash", ParticleSwordPhantom2.class, PARTICLE_PARAM_PLAYER),
        SWORD_SLASH1((byte) 0x27, "slash1", ParticleSwordSlash1.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_SLASH2((byte) 0x28, "slash2", ParticleSwordSlash2.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_SLASH3((byte) 0x29, "slash3", ParticleSwordSlash3.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_SLASH_BUFF_EMITTER((byte) 0x2A, null, ParticleSwordSlashBuffEmitter.class, PARTICLE_PARAM_PLAYER),
        SWORD_SLASH_BUFF_PARTICLE((byte) 0x2B, "slashbuff", ParticleSwordSlashBuffParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_TAUNT((byte) 0x2C, "taunt", ParticleSwordTaunt.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_TAUNT_AURA((byte) 0x2D, "tauntaura", ParticleSwordTauntAura.class, PARTICLE_PARAM_PLAYER),
        SWORD_TAUNT_AURA_PARTICLE((byte) 0x2E, "tauntaura2", ParticleSwordTauntAuraParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        SWORD_TAUNT_BUFF_EMITTER((byte) 0x2F, null, ParticleSwordTauntBuffEmitter.class, PARTICLE_PARAM_PLAYER),
        SWORD_VORPAL((byte) 0x30, "vorpal", ParticleSwordVorpal.class, PARTICLE_PARAM_POS_AND_FACING),
        UTILITY_DASH((byte) 0x31, "dash", ParticleUtilityDash.class, PARTICLE_PARAM_FACING_AND_PLAYER),
        UTILITY_DASH_BUFF_EMITTER((byte) 0x32, null, ParticleUtilityDashBuffEmitter.class, PARTICLE_PARAM_PLAYER),
        UTILITY_DASH_BUFF_PARTICLE((byte) 0x33, "dashbuff", ParticleUtilityDashBuffParticle.class, PARTICLE_PARAM_POS_AND_FACING),
        UTILITY_DASH_EMITTER((byte) 0x34, null, ParticleUtilityDashEmitter.class, PARTICLE_PARAM_FACING_AND_PLAYER),
        UTILITY_ADRENALINE((byte) 0x35, null, ParticleUtilityAdrenaline.class, PARTICLE_PARAM_PLAYER),
        UTILITY_ADRENALINE_CLONE_EMITTER((byte) 0x36, null, ParticleUtilityAdrenalineCloneEmitter.class, PARTICLE_PARAM_PLAYER),
        UTILITY_ADRENALINE_CLONE_PARTICLE((byte) 0x37, null, ParticleUtilityAdrenalineCloneParticle.class, null),
        HIT((byte) 0x38, "hit", ParticleHit.class, PARTICLE_PARAM_POS_AND_FACING),
        ITEM_DROP((byte) 0x39, null, ParticleItemDrop.class, PARTICLE_PARAM_PLAYER_AND_TARGET),
        SWORD_VORPAL_DEMISE((byte) 0x3A, "vorpaldemise", ParticleSwordVorpalDemise.class, PARTICLE_PARAM_PLAYER),
        SWORD_TAUNT_SURGE((byte) 0x3B, null, ParticleSwordTauntSurge.class, PARTICLE_PARAM_PLAYER);

        private final byte particleCode;

        private int numFrames;
        private final String spriteFolder;
        private final Class<? extends Particle> particleClass;
        private final Class[] parameterTypes;
        private BufferedImage[] sprite;

        private static final Map<Byte, Particles> lookup = new HashMap<Byte, Particles>();

        static {
            for (Particles particle : Particles.values()) {
                lookup.put(particle.getParticleCode(), particle);
            }
        }

        public static Particles get(byte code) {
            return lookup.get(code);
        }

        Particles(byte particleCode, String spriteFolder, Class<? extends Particle> particleClass, Class[] parameterTypes) {
            this.particleCode = particleCode;
            this.spriteFolder = spriteFolder;
            this.particleClass = particleClass;
            this.parameterTypes = parameterTypes;
            int numFiles = 0;
            if (spriteFolder != null) {
                try {
                    numFiles = (int) Files.list(Paths.get("resources/sprites/particle/" + spriteFolder + "/"))
                            .filter(p -> p.toFile().isFile())
                            .count();
                } catch (IOException ex) {
                }
            }
            this.numFrames = numFiles;
        }

        public void setSprite(BufferedImage[] sprite) {
            this.sprite = sprite;
        }

        public BufferedImage[] getSprites() {
            return this.sprite;
        }

        public byte getParticleCode() {
            return this.particleCode;
        }

        public int getNumFrames() {
            return this.numFrames;
        }

        public String getSpriteFolder() {
            return this.spriteFolder;
        }

        public Class[] getParameterTypes() {
            return this.parameterTypes;
        }

        public Particle newParticle(Object... parameters) {
            if (parameterTypes == null) {
                return null;
            }
            try {
                Constructor<? extends Particle> constructor = this.particleClass.getDeclaredConstructor(parameterTypes);
                return constructor.newInstance(parameters);
            } catch (Exception ex) {
                logError(ex.toString(), ex);
            }
            return null;
        }
    }

    public enum BGMs {
        MENU((byte) 0x00, "hero.ogg"),
        TITLE((byte) 0x01, "Through the Forest in Midwinter.ogg"),
        GRACE_RESIS((byte) 0x02, "bgm/Dibur - Graceful Resistance.ogg"),
        INEV_BLOOD_ROCK((byte) 0x03, "bgm/Dibur - Inevitable Bloodshed (Rock Style).ogg"),
        REDEMP_ORCH((byte) 0x04, "bgm/Dibur - Redemption (Orchestral Style).ogg"),
        BLOOD_STEEL((byte) 0x05, "bgm/Dibur - Blood and Steel.ogg"),
        INEV_BLOOD_DYN((byte) 0x06, "bgm/Dibur - Inevitable Bloodshed (Dynamic Style).ogg");

        private static final Map<Byte, BGMs> lookup = new HashMap<Byte, BGMs>();

        static {
            for (BGMs bgm : BGMs.values()) {
                lookup.put(bgm.getBgmCode(), bgm);
            }
        }

        public static BGMs get(byte code) {
            return lookup.get(code);
        }

        private final byte bgmCode;
        private final String resourcePath;

        BGMs(byte bgmCode, String resourcePath) {
            this.bgmCode = bgmCode;
            this.resourcePath = resourcePath;
        }

        public byte getBgmCode() {
            return this.bgmCode;
        }

        public String getResourcePath() {
            return this.resourcePath;
        }
    }

    public enum SFXs {
        SLASH((byte) 0x00, "sword/slash/0.wav"),
        VOLLEY((byte) 0x01, "bow/volley/0.wav"),
        RAPID((byte) 0x02, "bow/rapid/0.wav"),
        POWER((byte) 0x03, "bow/power/0.wav"),
        POWER2((byte) 0x04, "bow/power/1.wav"),
        FORTIFY((byte) 0x05, "shield/fortify/0.wav"),
        SARC((byte) 0x06, "bow/arc/0.wav"),
        GASH((byte) 0x07, "sword/gash/0.wav");

        private static final Map<Byte, SFXs> lookup = new HashMap<Byte, SFXs>();

        static {
            for (SFXs sfx : SFXs.values()) {
                lookup.put(sfx.getSfxCode(), sfx);
            }
        }

        public static SFXs get(byte code) {
            return lookup.get(code);
        }

        private final byte sfxCode;
        private final String resourcePath;

        SFXs(byte sfxCode, String resourcePath) {
            this.sfxCode = sfxCode;
            this.resourcePath = "sfx/" + resourcePath;
        }

        public byte getSfxCode() {
            return this.sfxCode;
        }

        public String getResourcePath() {
            return this.resourcePath;
        }
    }

    public enum VictoryStatus {
        FIRST((byte) 0x00, "Victory! First Place!", "3 Equipment Drops, 20% EXP towards next level"),
        SECOND((byte) 0x01, "Second Place!", "2 Equipment Drops, 15% EXP towards next level"),
        THIRD((byte) 0x02, "Third Place!", "1 Equipment Drop, 10% EXP towards next level"),
        LAST((byte) 0x03, "Match Completed!", "10% EXP towards next level");

        private final String STATUS_TEXT;
        private final String BONUSES_TEXT;
        private final byte BYTE_CODE;

        VictoryStatus(final byte bytecode, final String victoryText, final String bonuses) {
            this.BYTE_CODE = bytecode;
            this.STATUS_TEXT = victoryText;
            this.BONUSES_TEXT = bonuses;
        }

        private static final Map<Byte, VictoryStatus> lookup = new HashMap<Byte, VictoryStatus>();

        public static VictoryStatus get(byte code) {
            return lookup.get(code);
        }

        static {
            for (VictoryStatus status : VictoryStatus.values()) {
                lookup.put(status.BYTE_CODE, status);
            }
        }

        public byte getByteCode() {
            return this.BYTE_CODE;
        }

        public String getBonusesText() {
            return this.BONUSES_TEXT;
        }

        @Override
        public String toString() {
            return this.STATUS_TEXT;
        }
    }
    public final static int NUM_KEYBINDS = 27,
            KEYBIND_SKILL1 = 0,
            KEYBIND_SKILL2 = 1,
            KEYBIND_SKILL3 = 2,
            KEYBIND_SKILL4 = 3,
            KEYBIND_SKILL5 = 4,
            KEYBIND_SKILL6 = 5,
            KEYBIND_SKILL7 = 6,
            KEYBIND_SKILL8 = 7,
            KEYBIND_SKILL9 = 8,
            KEYBIND_SKILL10 = 9,
            KEYBIND_SKILL11 = 10,
            KEYBIND_SKILL12 = 11,
            KEYBIND_LEFT = 12,
            KEYBIND_RIGHT = 13,
            KEYBIND_JUMP = 14,
            KEYBIND_DOWN = 15,
            KEYBIND_EMOTE1 = 16,
            KEYBIND_EMOTE2 = 17,
            KEYBIND_EMOTE3 = 18,
            KEYBIND_EMOTE4 = 19,
            KEYBIND_EMOTE5 = 20,
            KEYBIND_EMOTE6 = 21,
            KEYBIND_EMOTE7 = 22,
            KEYBIND_EMOTE8 = 23,
            KEYBIND_EMOTE9 = 24,
            KEYBIND_EMOTE10 = 25,
            KEYBIND_SCOREBOARD = 26;

    public final static byte NUM_HOTKEYS = 12;

    public final static byte NUM_EQUIP_TABS = 10,
            EQUIP_TAB_WEAPON = 0,
            EQUIP_TAB_HEAD = 1,
            EQUIP_TAB_CHEST = 2,
            EQUIP_TAB_PANTS = 3,
            EQUIP_TAB_SHOULDER = 4,
            EQUIP_TAB_GLOVE = 5,
            EQUIP_TAB_SHOE = 6,
            EQUIP_TAB_BELT = 7,
            EQUIP_TAB_RING = 8,
            EQUIP_TAB_AMULET = 9;

    public final static byte NUM_EQUIP_TYPES = 13,
            ITEM_SWORD = 0,
            ITEM_HEAD = 1,
            ITEM_CHEST = 2,
            ITEM_PANTS = 3,
            ITEM_SHOULDER = 4,
            ITEM_GLOVE = 5,
            ITEM_SHOE = 6,
            ITEM_BELT = 7,
            ITEM_RING = 8,
            ITEM_AMULET = 9,
            ITEM_SHIELD = 10,
            ITEM_BOW = 11,
            ITEM_ARROW = 12;

    public final static byte NUM_EQUIP_SLOTS = 11,
            EQUIP_WEAPON = 0,
            EQUIP_HEAD = 1,
            EQUIP_CHEST = 2,
            EQUIP_PANTS = 3,
            EQUIP_SHOULDER = 4,
            EQUIP_GLOVE = 5,
            EQUIP_SHOE = 6,
            EQUIP_BELT = 7,
            EQUIP_RING = 8,
            EQUIP_AMULET = 9,
            EQUIP_OFFHAND = 10;

    public final static byte NUM_STATS = 17,
            STAT_POWER = 0,
            STAT_DEFENSE = 1,
            STAT_SPIRIT = 2,
            STAT_MINHP = 3,
            STAT_MAXHP = 4,
            STAT_MINDMG = 5,
            STAT_MAXDMG = 6,
            STAT_CRITCHANCE = 7,
            STAT_CRITDMG = 8,
            STAT_REGEN = 9,
            STAT_ARMOUR = 10,
            STAT_LEVEL = 11,
            STAT_POINTS = 12,
            STAT_EXP = 13,
            STAT_SKILLPOINTS = 14,
            STAT_DAMAGEREDUCT = 15,
            STAT_MAXEXP = 16;

    public final static double HP_BASE = 3000, // PvE = 100
            HP_MULT = 170, // PvE = 30
            REDUCT_CONST = 300,
            ARMOUR_MULT = 6,
            REGEN_HP_PERCENT = 0.02,
            REGEN_MULT = REGEN_HP_PERCENT * HP_MULT,
            REGEN_CONST = REGEN_HP_PERCENT * HP_BASE,
            CRITCHC_BASE = 0,
            CRITCHC_FACT = 10,
            CRITCHC_MULT = 0.01,
            CRITCHC_CONST = 2500,
            CRITDMG_BASE = 0.5,
            CRITDMG_FACT = 5.5,
            CRITDMG_MULT = 0.01,
            MINDMG_MULT = 15,
            MAXDMG_MULT = 17,
            MINDMG_BASE = 20,
            MAXDMG_BASE = 40,
            DMG_MULT = 16,
            DMG_BASE = 30,
            DMG_VARIANCE_PERCENT = 0.015,
            STAT_PER_LEVEL = 15,
            SP_PER_LEVEL = 3;

    public final static int NUM_PLAYER_ANIM_STATE = 9;
    public final static byte PLAYER_ANIM_STATE_STAND = 0x00,
            PLAYER_ANIM_STATE_WALK = 0x01,
            PLAYER_ANIM_STATE_JUMP = 0x02,
            PLAYER_ANIM_STATE_ATTACK = 0x03,
            PLAYER_ANIM_STATE_ATTACKBOW = 0x04,
            PLAYER_ANIM_STATE_BUFF = 0x05,
            PLAYER_ANIM_STATE_DEAD = 0x06,
            PLAYER_ANIM_STATE_INVIS = 0x07,
            PLAYER_ANIM_STATE_ROLL = 0x08;

    // Packet globals
    public final static int PACKET_MAX_SIZE = 8000;
    public final static int PACKET_BYTE = 1;
    public final static int PACKET_INT = 4;
    public final static int PACKET_LONG = 8;
    public final static int PACKET_CHAR = 1;

    public final static byte HUB_DATA_PING = 0x00,
            HUB_DATA_GET_SERVERINFOS = 0x01,
            HUB_DATA_GET_SERVERSTATS = 0x02;

    // Datatypes
    public final static byte DATA_PING = 0x00,
            DATA_PLAYER_LOGIN = 0x01,
            DATA_PLAYER_GET_ALL = 0x02,
            DATA_PLAYER_SET_MOVE = 0x03,
            DATA_PLAYER_SET_POS = 0x04,
            DATA_PLAYER_SET_FACING = 0x05,
            DATA_PLAYER_SET_STATE = 0x06,
            DATA_PLAYER_USESKILL = 0x07,
            DATA_PARTICLE_EFFECT = 0x08,
            DATA_SOUND_EFFECT = 0x09,
            DATA_PLAYER_DISCONNECT = 0x0A,
            DATA_PLAYER_GET_NAME = 0x0B,
            DATA_PLAYER_GET_STAT = 0x0C,
            DATA_PLAYER_GET_EQUIP = 0x0D,
            DATA_PLAYER_SET_COOLDOWN = 0x0E,
            DATA_NUMBER = 0x0F,
            DATA_PLAYER_GIVEEXP = 0x10,
            DATA_PLAYER_GIVEDROP = 0x11,
            DATA_PLAYER_CREATE = 0x12,
            DATA_SCREEN_SHAKE = 0x13,
            DATA_PLAYER_EMOTE = 0x14,
            DATA_NOTIFICATION_KILL = 0x15,
            DATA_PLAYER_SCORE = 0x16,
            DATA_PLAYER_MATCH_RESULT = 0x17;

    public static final byte LOGIN_SUCCESS = 0x00,
            LOGIN_FAIL_UID_IN_ROOM = 0x01,
            LOGIN_FAIL_FULL_ROOM = 0x02,
            LOGIN_FAIL_NO_ROOMS = 0x03;

    public enum SkillClassMap {
        SWORD_VORPAL(Globals.SWORD_VORPAL, "sword.SkillSwordVorpal"),
        SWORD_PHANTOM(Globals.SWORD_PHANTOM, "sword.SkillSwordPhantom"),
        SWORD_CINDER(Globals.SWORD_CINDER, "sword.SkillSwordCinder"),
        SWORD_GASH(Globals.SWORD_GASH, "sword.SkillSwordGash"),
        SWORD_SLASH(Globals.SWORD_SLASH, "sword.SkillSwordSlash"),
        SWORD_TAUNT(Globals.SWORD_TAUNT, "sword.SkillSwordTaunt"),
        BOW_ARC(Globals.BOW_ARC, "bow.SkillBowArc"),
        BOW_POWER(Globals.BOW_POWER, "bow.SkillBowPower"),
        BOW_RAPID(Globals.BOW_RAPID, "bow.SkillBowRapid"),
        BOW_FROST(Globals.BOW_FROST, "bow.SkillBowFrost"),
        BOW_STORM(Globals.BOW_STORM, "bow.SkillBowStorm"),
        BOW_VOLLEY(Globals.BOW_VOLLEY, "bow.SkillBowVolley"),
        UTILITY_ADRENALINE(Globals.UTILITY_ADRENALINE, "utility.SkillUtilityAdrenaline"),
        SHIELD_ROAR(Globals.SHIELD_ROAR, "shield.SkillShieldRoar"),
        SHIELD_CHARGE(Globals.SHIELD_CHARGE, "shield.SkillShieldCharge"),
        SHIELD_REFLECT(Globals.SHIELD_REFLECT, "shield.SkillShieldReflect"),
        SHIELD_MAGNETIZE(Globals.SHIELD_MAGNETIZE, "shield.SkillShieldMagnetize"),
        UTILITY_DASH(Globals.UTILITY_DASH, "utility.SkillUtilityDash"),
        PASSIVE_DUALSWORD(Globals.PASSIVE_DUALSWORD, "passive.SkillPassiveDualSword"),
        PASSIVE_KEENEYE(Globals.PASSIVE_KEENEYE, "passive.SkillPassiveKeenEye"),
        PASSIVE_VITALHIT(Globals.PASSIVE_VITALHIT, "passive.SkillPassiveVitalHit"),
        PASSIVE_SHIELDMASTERY(Globals.PASSIVE_SHIELDMASTERY, "passive.SkillPassiveShieldMastery"),
        PASSIVE_BARRIER(Globals.PASSIVE_BARRIER, "passive.SkillPassiveBarrier"),
        PASSIVE_RESIST(Globals.PASSIVE_RESIST, "passive.SkillPassiveResistance"),
        PASSIVE_BOWMASTERY(Globals.PASSIVE_BOWMASTERY, "passive.SkillPassiveBowMastery"),
        PASSIVE_WILLPOWER(Globals.PASSIVE_WILLPOWER, "passive.SkillPassiveWillpower"),
        PASSIVE_HARMONY(Globals.PASSIVE_HARMONY, "passive.SkillPassiveHarmony"),
        PASSIVE_TOUGH(Globals.PASSIVE_TOUGH, "passive.SkillPassiveTough"),
        PASSIVE_SHADOWATTACK(Globals.PASSIVE_SHADOWATTACK, "passive.SkillPassiveShadowAttack"),
        PASSIVE_STATIC(Globals.PASSIVE_STATIC, "passive.SkillPassiveStatic"),
        SWORD_VORPAL_GHOST(Globals.SWORD_VORPAL_GHOST, "sword.SkillSwordVorpalGhost"),
        SWORD_VORPAL_DEMISE(Globals.SWORD_VORPAL_DEMISE, "sword.SkillSwordVorpalDemise"),
        SWORD_PHANTOM_PASSIVE1(Globals.SWORD_PHANTOM_PASSIVE1, "sword.SkillSwordPhantomPassive1"),
        SWORD_PHANTOM_PASSIVE2(Globals.SWORD_PHANTOM_PASSIVE2, "sword.SkillSwordPhantomPassive2"),
        SWORD_CINDER_PASSIVE1(Globals.SWORD_CINDER_PASSIVE1, "sword.SkillSwordCinderPassive1"),
        SWORD_CINDER_PASSIVE2(Globals.SWORD_CINDER_PASSIVE2, "sword.SkillSwordCinderPassive2"),
        SWORD_TAUNT_CRIPPLE(Globals.SWORD_TAUNT_CRIPPLE, "sword.SkillSwordTauntCripple"),
        SWORD_TAUNT_SURGE(Globals.SWORD_TAUNT_SURGE, "sword.SkillSwordTauntSurge"),
        SWORD_KNOCKUP(Globals.SWORD_KNOCKUP, "sword.SkillSwordKnockup"),
        SWORD_KNOCKUP_PASSIVE1(Globals.SWORD_KNOCKUP_PASSIVE1, "sword.SkillSwordKnockupPassive1"),
        SWORD_KNOCKUP_PASSIVE2(Globals.SWORD_KNOCKUP_PASSIVE2, "sword.SkillSwordKnockupPassive2"),
        SWORD_WHIRLWIND(Globals.SWORD_WHIRLWIND, "sword.SkillSwordWhirlwind"),
        SWORD_WHIRLWIND_PASSIVE1(Globals.SWORD_WHIRLWIND_PASSIVE1, "sword.SkillSwordWhirlwindPassive1"),
        SWORD_WHIRLWIND_PASSIVE2(Globals.SWORD_WHIRLWIND_PASSIVE2, "sword.SkillSwordWhirlwindPassive2"),
        SWORD_WAVE(Globals.SWORD_WAVE, "sword.SkillSwordWave"),
        SWORD_WAVE_PASSIVE1(Globals.SWORD_WAVE_PASSIVE1, "sword.SkillSwordWavePassive1"),
        SWORD_WAVE_PASSIVE2(Globals.SWORD_WAVE_PASSIVE2, "sword.SkillSwordWavePassive2"),
        BOW_POWER_PASSIVE1(Globals.BOW_POWER_PASSIVE1, "bow.SkillBowPowerPassive1"),
        BOW_POWER_PASSIVE2(Globals.BOW_POWER_PASSIVE2, "bow.SkillBowPowerPassive2"),
        BOW_FROST_PASSIVE1(Globals.BOW_FROST_PASSIVE1, "bow.SkillBowFrostPassive1"),
        BOW_FROST_PASSIVE2(Globals.BOW_FROST_PASSIVE2, "bow.SkillBowFrostPassive2"),
        BOW_STORM_PASSIVE1(Globals.BOW_STORM_PASSIVE1, "bow.SkillBowStormPassive1"),
        BOW_STORM_PASSIVE2(Globals.BOW_STORM_PASSIVE2, "bow.SkillBowStormPassive2"),
        BOW_VOLLEY_PASSIVE1(Globals.BOW_VOLLEY_PASSIVE1, "bow.SkillBowVolleyPassive1"),
        BOW_VOLLEY_PASSIVE2(Globals.BOW_VOLLEY_PASSIVE2, "bow.SkillBowVolleyPassive2"),
        BOW_BLOWBACK(Globals.BOW_BLOWBACK, "bow.SkillBowBlowback"),
        BOW_BLOWBACK_PASSIVE1(Globals.BOW_BLOWBACK_PASSIVE1, "bow.SkillBowBlowbackPassive1"),
        BOW_BLOWBACK_PASSIVE2(Globals.BOW_BLOWBACK_PASSIVE2, "bow.SkillBowBlowbackPassive2"),
        BOW_AOE(Globals.BOW_AOE, "bow.SkillBowAoe"),
        BOW_AOE_PASSIVE1(Globals.BOW_AOE_PASSIVE1, "bow.SkillBowAoePassive1"),
        BOW_AOE_PASSIVE2(Globals.BOW_AOE_PASSIVE2, "bow.SkillBowAoePassive2"),
        BOW_BUFF(Globals.BOW_BUFF, "bow.SkillBowBuff"),
        BOW_BUFF_PASSIVE1(Globals.BOW_BUFF_PASSIVE1, "bow.SkillBowBuffPassive1"),
        BOW_BUFF_PASSIVE2(Globals.BOW_BUFF_PASSIVE2, "bow.SkillBowBuffPassive2");

        private Class<? extends blockfighter.client.entities.player.skills.Skill> CLIENT_CLASS;
        private Class<? extends blockfighter.server.entities.player.skills.Skill> SERVER_CLASS;
        private final byte BYTE_CODE;

        SkillClassMap(final byte bytecode, final String className) {
            this.BYTE_CODE = bytecode;
            try {
                if (CLIENT_MODE) {
                    this.CLIENT_CLASS = Class.forName("blockfighter.client.entities.player.skills." + className).asSubclass(blockfighter.client.entities.player.skills.Skill.class);
                }
                if (SERVER_MODE) {
                    this.SERVER_CLASS = Class.forName("blockfighter.server.entities.player.skills." + className).asSubclass(blockfighter.server.entities.player.skills.Skill.class);
                }
            } catch (ClassNotFoundException ex) {
                logError("[FATAL] Could not find skill class.", ex);
            }
        }

        private static final Map<Byte, SkillClassMap> lookup = new HashMap<>();

        public static SkillClassMap get(byte code) {
            return lookup.get(code);
        }

        static {
            for (SkillClassMap skill : SkillClassMap.values()) {
                lookup.put(skill.BYTE_CODE, skill);
            }
        }

        public byte getByteCode() {
            return this.BYTE_CODE;
        }

        public Class<? extends blockfighter.client.entities.player.skills.Skill> getClientClass() {
            return this.CLIENT_CLASS;
        }

        public Class<? extends blockfighter.server.entities.player.skills.Skill> getServerClass() {
            return this.SERVER_CLASS;
        }
    }

    public final static byte NUM_SKILLS = 64,
            SWORD_VORPAL = 0x00,
            SWORD_PHANTOM = 0x01,
            SWORD_CINDER = 0x02,
            SWORD_GASH = 0x03,
            SWORD_SLASH = 0x04,
            SWORD_TAUNT = 0x05,
            BOW_ARC = 0x06,
            BOW_POWER = 0x07,
            BOW_RAPID = 0x08,
            BOW_FROST = 0x09,
            BOW_STORM = 0x0A,
            BOW_VOLLEY = 0x0B,
            UTILITY_ADRENALINE = 0x0C,
            SHIELD_ROAR = 0x0D,
            SHIELD_CHARGE = 0x0E,
            SHIELD_REFLECT = 0x0F,
            SHIELD_MAGNETIZE = 0x10,
            UTILITY_DASH = 0x11,
            PASSIVE_DUALSWORD = 0x12,
            PASSIVE_KEENEYE = 0x13,
            PASSIVE_VITALHIT = 0x14,
            PASSIVE_SHIELDMASTERY = 0x15,
            PASSIVE_BARRIER = 0x16,
            PASSIVE_RESIST = 0x17,
            PASSIVE_BOWMASTERY = 0x18,
            PASSIVE_WILLPOWER = 0x19,
            PASSIVE_HARMONY = 0x1A,
            PASSIVE_TOUGH = 0x1B,
            PASSIVE_SHADOWATTACK = 0x1C,
            PASSIVE_STATIC = 0x1D,
            SWORD_VORPAL_GHOST = 0x1E,
            SWORD_VORPAL_DEMISE = 0x1F,
            SWORD_PHANTOM_PASSIVE1 = 0x20,
            SWORD_PHANTOM_PASSIVE2 = 0x21,
            SWORD_CINDER_PASSIVE1 = 0x22,
            SWORD_CINDER_PASSIVE2 = 0x23,
            SWORD_TAUNT_CRIPPLE = 0x24,
            SWORD_TAUNT_SURGE = 0x25,
            SWORD_KNOCKUP = 0x26,
            SWORD_KNOCKUP_PASSIVE1 = 0x27,
            SWORD_KNOCKUP_PASSIVE2 = 0x28,
            SWORD_WHIRLWIND = 0x29,
            SWORD_WHIRLWIND_PASSIVE1 = 0x2A,
            SWORD_WHIRLWIND_PASSIVE2 = 0x2B,
            SWORD_WAVE = 0x2C,
            SWORD_WAVE_PASSIVE1 = 0x2D,
            SWORD_WAVE_PASSIVE2 = 0x2E,
            BOW_POWER_PASSIVE1 = 0x2F,
            BOW_POWER_PASSIVE2 = 0x30,
            BOW_FROST_PASSIVE1 = 0x31,
            BOW_FROST_PASSIVE2 = 0x32,
            BOW_STORM_PASSIVE1 = 0x33,
            BOW_STORM_PASSIVE2 = 0x34,
            BOW_VOLLEY_PASSIVE1 = 0x35,
            BOW_VOLLEY_PASSIVE2 = 0x36,
            BOW_BLOWBACK = 0x37,
            BOW_BLOWBACK_PASSIVE1 = 0x38,
            BOW_BLOWBACK_PASSIVE2 = 0x39,
            BOW_AOE = 0x3A,
            BOW_AOE_PASSIVE1 = 0x3B,
            BOW_AOE_PASSIVE2 = 0x3C,
            BOW_BUFF = 0x3D,
            BOW_BUFF_PASSIVE1 = 0x3E,
            BOW_BUFF_PASSIVE2 = 0x3F;

    public final static BufferedImage[][] CHAR_SPRITE = new BufferedImage[NUM_PLAYER_ANIM_STATE][];
    public final static BufferedImage[] HUD = new BufferedImage[4];
    public static BufferedImage TITLE;

    public final static BufferedImage[] MENU_BG = new BufferedImage[3];
    public final static BufferedImage[] MENU_SMOKE = new BufferedImage[1];
    public final static BufferedImage[] MENU_UPGRADEPARTICLE = new BufferedImage[4];
    public final static BufferedImage[] MENU_BUTTON = new BufferedImage[17];
    public final static BufferedImage[] MENU_WINDOW = new BufferedImage[2];
    public final static BufferedImage[] MENU_TABPOINTER = new BufferedImage[2];
    public final static BufferedImage[] MENU_ITEMDELETE = new BufferedImage[1];

    // Use Cooper Std Black size 25
    public final static BufferedImage[][] DAMAGE_FONT = new BufferedImage[4][10];

    public final static BufferedImage[] SKILL_ICON = new BufferedImage[NUM_SKILLS];
    public final static BufferedImage[] SKILL_DISABLED_ICON = new BufferedImage[NUM_SKILLS];

    public final static int[] PLAYER_NUM_ANIM_FRAMES = new int[NUM_PLAYER_ANIM_STATE];

    public final static byte BUTTON_BIGRECT = 0,
            BUTTON_SELECTCHAR = 1,
            BUTTON_ADDSTAT = 2,
            BUTTON_SLOT = 3,
            BUTTON_MENUS = 4,
            BUTTON_WEAPONTAB = 5,
            BUTTON_HEADTAB = 6,
            BUTTON_CHESTTAB = 7,
            BUTTON_PANTSTAB = 8,
            BUTTON_SHOULDERTAB = 9,
            BUTTON_GLOVETAB = 10,
            BUTTON_SHOETAB = 11,
            BUTTON_BELTTAB = 12,
            BUTTON_RINGTAB = 13,
            BUTTON_AMULETTAB = 14,
            BUTTON_SMALLRECT = 15,
            BUTTON_RIGHTCLICK = 16;

    public final static byte WINDOW_CREATECHAR = 0,
            WINDOW_DESTROYCONFIRM = 1;

    public static final byte NUMBER_TYPE_PLAYER = 0,
            NUMBER_TYPE_PLAYERCRIT = 1,
            NUMBER_TYPE_ENEMY = 2,
            NUMBER_TYPE_ENEMYCRIT = 3;

    public static final byte NOTIFICATION_EXP = 0,
            NOTIFICATION_ITEM = 1,
            NOTIFICATION_KILL = 2;

    public static boolean LOGGING = false;
    private static Logger logger, ErrorLogger;

    private final static String LOG_DIR = "logs/",
            ERRLOG_FILE = "ErrorLog-" + String.format("%1$td%1$tm%1$tY-%1$tH%1$tM%1$tS", System.currentTimeMillis()) + ".log",
            DATALOG_FILE = "DataLog-" + String.format("%1$td%1$tm%1$tY-%1$tH%1$tM%1$tS", System.currentTimeMillis()) + ".log";
    private static JTextArea dataConsole, errConsole;

    public final static int LOG_TYPE_ERR = Log.LEVEL_ERROR,
            LOG_TYPE_DATA = Log.LEVEL_INFO;

    public static ExecutorService LOG_THREAD = Executors.newSingleThreadExecutor(
            new BasicThreadFactory.Builder()
                    .namingPattern("Logger-%d")
                    .daemon(true)
                    .priority(Thread.MIN_PRIORITY)
                    .build());

    public enum ServerConfig {
        TCP_PORT("tcpport", Integer.class, Integer.valueOf(25565), "Server TCP Port"),
        UDP_PORT("udpport", Integer.class, Integer.valueOf(35565), "Server UDP Port"),
        EXP_MULTIPLIER("expmult", Double.class, Double.valueOf(0.1), "EXP Multiplier"),
        MAX_ROOM_IDLE("maxroomidle", Integer.class, Integer.valueOf(2000), "Max Room Idle(ms)"),
        MAX_PLAYER_IDLE("maxplayeridle", Integer.class, Integer.valueOf(120000), "Max Player Idle(ms)"),
        MAX_ROOMS("maxrooms", Integer.class, Integer.valueOf(10), "Max Rooms"),
        MAX_PLAYERS("maxplayers", Integer.class, Integer.valueOf(10), "Max Players per Room"),
        MAX_NUM_PACKETS("maxpackets", Integer.class, Integer.valueOf(100), "Max Packets Per Connection"),
        NUM_THREADS("numthreads", Byte.class, Byte.valueOf((byte) 3), "Number of threads"),
        NUM_SCHEDULED_THREADS("numscheduledthreads", Byte.class, Byte.valueOf((byte) 2), "Number of Scheduler Threads"),
        UDP_MODE("udpmode", Boolean.class, Boolean.valueOf(true), "UDP Mode"),
        HUB_CONNECT("hubconnect", Boolean.class, Boolean.valueOf(true), "Connect to Hub"),
        HUB_SERVER_ADDRESS("hubaddress", String.class, "ascension-hub.herokuapp.com", "Hub Address"),
        HUB_SERVER_TCP_PORT("hubport", Integer.class, Integer.valueOf(80), "Hub Port"),
        WIN_SCORE_COUNT("winscorecount", Integer.class, Integer.valueOf(30), "Score to Win"),
        MATCH_DURATION("matchduration", Integer.class, Integer.valueOf(300000), "Match Duration(ms)"),
        ROOM_LEVEL_DIFF("leveldiff", Integer.class, Integer.valueOf(2), "Room Level Difference(±)"),
        MATCH_TIME_REMAINING_THRESHOLD("matchtimeremainingthreshold", Integer.class, Integer.valueOf(90000), "Minimum Remaining Time for joinable Room"),
        EQUIP_DROP_SUCCESS_RATE("equipdropsuccessrate", Integer.class, Integer.valueOf(120), "Equip Drop Success Rate"),
        EQUIP_DROP_RATE_ROLL("equipdroprateroll", Integer.class, Integer.valueOf(1000), "Equip Drop Rate Roll"),
        UPGRADE_DROP_SUCCESS_RATE("upgradedropsuccessrate", Integer.class, Integer.valueOf(150), "Infusion Drop Success Rate"),
        UPGRADE_DROP_RATE_ROLL("upgradedroprateroll", Integer.class, Integer.valueOf(1000), "Infusion Drop Rate Roll"),
        GAME_MAPS_LIST("gamemaps", GameMaps[].class, new GameMaps[]{GameMaps.GRAND_LIBRARY}, "Playable Game Maps List");

        private final String key;
        private final Class type;
        private final Object defaultValue;
        private final String desc;
        private Object value = null;

        private static final Map<String, ServerConfig> lookup = new HashMap<String, ServerConfig>();

        ServerConfig(String key, Class type, Object defaultValue, String desc) {
            this.key = key;
            this.type = type;
            this.defaultValue = defaultValue;
            this.desc = desc;
        }

        static {
            for (ServerConfig config : ServerConfig.values()) {
                lookup.put(config.getKey(), config);
            }
        }

        public static ServerConfig get(String key) {
            return lookup.get(key);
        }

        public Class getType() {
            return this.type;
        }

        public String getKey() {
            return this.key;
        }

        public void setValue(String value) {
            if (value == null) {
                this.value = null;
                return;
            }
            if (this.type == Byte.class) {
                this.value = Byte.valueOf(value);
            } else if (this.type == Integer.class) {
                this.value = Integer.valueOf(value);
            } else if (this.type == Double.class) {
                this.value = Double.valueOf(value);
            } else if (this.type == Float.class) {
                this.value = Float.valueOf(value);
            } else if (this.type == Long.class) {
                this.value = Long.valueOf(value);
            } else if (this.type == Boolean.class) {
                this.value = Boolean.valueOf(value);
            } else if (this.type == String.class) {
                this.value = value;
            } else if (this.type == GameMaps[].class) {
                String[] mapString = value.split(",");
                GameMaps[] maps = new GameMaps[mapString.length];
                for (int i = 0; i < maps.length; i++) {
                    maps[i] = GameMaps.get(Byte.decode(mapString[i]));
                }
                this.value = maps;
            }
        }

        public Object getValue() {
            return (this.value == null) ? this.defaultValue : this.value;
        }

        public String getDesc() {
            if (this.type == GameMaps[].class) {
                return this.desc + COLON_SPACE_TEXT + Arrays.toString((GameMaps[]) this.getValue());
            } else {
                return this.desc + COLON_SPACE_TEXT + this.getValue();
            }
        }
    }

    public final static long PROCESS_QUEUE_TICKS_PER_SEC = 100;
    public final static long PROCESS_QUEUE = 1000000000 / PROCESS_QUEUE_TICKS_PER_SEC;

    public final static long SERVER_LOGIC_TICKS_PER_SEC = 100;
    public final static long SERVER_LOGIC_UPDATE = 1000000000 / SERVER_LOGIC_TICKS_PER_SEC;
    public final static long SERVER_LOGIC_BUCKET_CELLSIZE = 300;

    public final static long SENDALL_TICKS_PER_SEC = 80;
    public final static long SENDALL_UPDATE = 1000000000 / SENDALL_TICKS_PER_SEC;

    public final static long REFRESH_ALL_UPDATE = 100;

    public final static byte MAP_LEFT = 0, MAP_RIGHT = 1, MAP_TOP = 2, MAP_BOTTOM = 3;

    public final static double GRAVITY = 0.4, MAX_FALLSPEED = 15, WALK_SPEED = 3.8;

    public final static HashSet<Integer> ITEM_EQUIP_CODES = new HashSet<>();
    public final static HashSet<Integer> ITEM_UPGRADE_CODES = new HashSet<>();
    public final static HashMap<Byte, HashSet<Integer>> ITEM_EQUIP_TYPE_CODES_MAP = new HashMap<>(NUM_EQUIP_TYPES);

    public static final String SKILL_BASEVALUE_HEADER = "[basevalue]",
            SKILL_DESC_HEADER = "[desc]",
            SKILL_MAXCOOLDOWN_HEADER = "[maxcooldown]",
            SKILL_MULTVALUE_HEADER = "[multvalue]",
            SKILL_NAME_HEADER = "[name]",
            SKILL_PASSIVE_HEADER = "[passive]",
            SKILL_REQWEAPON_HEADER = "[reqweapon]",
            SKILL_REQLEVEL_HEADER = "[reqlevel]",
            SKILL_CUSTOM_VALUES_HEADER = "[customvalues]",
            SKILL_CANT_LEVEL_HEADER = "[cantlevel]",
            SKILL_LEVEL_DESC_HEADER = "[leveldesc]",
            SKILL_MAX_BONUS_DESC_HEADER = "[maxbonusdesc]";

    public static final String[] DATA_HEADERS = {
        SKILL_NAME_HEADER,
        SKILL_DESC_HEADER,
        SKILL_REQWEAPON_HEADER,
        SKILL_MAXCOOLDOWN_HEADER,
        SKILL_BASEVALUE_HEADER,
        SKILL_MULTVALUE_HEADER,
        SKILL_PASSIVE_HEADER,
        SKILL_REQLEVEL_HEADER,
        SKILL_CUSTOM_VALUES_HEADER,
        SKILL_CANT_LEVEL_HEADER,
        SKILL_LEVEL_DESC_HEADER,
        SKILL_MAX_BONUS_DESC_HEADER
    };

    public static BufferedImage getDisabledIcon(BufferedImage icon) {
        if (icon == null) {
            return null;
        }
        GrayFilter filter = new GrayFilter(true, 15);
        ImageProducer prod = new FilteredImageSource(icon.getSource(), filter);
        Image disabled = Toolkit.getDefaultToolkit().createImage(prod);
        BufferedImage result = new BufferedImage(disabled.getWidth(null), disabled.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics g = result.getGraphics();
        g.drawImage(disabled, 0, 0, null);
        g.dispose();
        return result;
    }

    public static HashMap<String, Integer> getDataHeaders(final String[] data) {
        HashMap<String, Integer> dataHeader = new HashMap<>();
        for (int i = 0; i < data.length; i++) {
            for (String header : DATA_HEADERS) {
                if (data[i].equalsIgnoreCase(header)) {
                    dataHeader.put(header, i);
                }
            }
        }

        String customHeadersRaw = loadStringValue(data, dataHeader, SKILL_CUSTOM_VALUES_HEADER);
        if (customHeadersRaw != null) {
            String[] customDataHeaders = customHeadersRaw.split(",");
            for (String customDataHeader : customDataHeaders) {
                String formattedHeader = "[" + customDataHeader.trim().toLowerCase() + "]";
                for (int j = 0; j < data.length; j++) {
                    if (data[j].equalsIgnoreCase(formattedHeader)) {
                        dataHeader.put(formattedHeader, j);
                    }
                }
            }

        }
        return dataHeader;
    }

    public static String[] getSkillCustomHeaders(final String[] data, final HashMap<String, Integer> dataHeaders) {
        try {
            String[] value = loadStringValue(data, dataHeaders, SKILL_CUSTOM_VALUES_HEADER).split(",");
            for (int i = 0; i < value.length; i++) {
                value[i] = "[" + value[i].trim().toLowerCase() + "]";
            }
            return value;
        } catch (Exception e) {
        }
        return new String[0];
    }

    public static String loadStringValue(final String[] data, final HashMap<String, Integer> dataHeaders, String header) {
        try {
            return data[dataHeaders.get(header) + 1];
        } catch (Exception e) {
        }
        return null;
    }

    public static boolean loadBooleanValue(final String[] data, final HashMap<String, Integer> dataHeaders, String header) {
        try {
            return Boolean.parseBoolean(data[dataHeaders.get(header) + 1]);
        } catch (Exception e) {
            Globals.logError("Cannot find header " + header, e);
        }
        return false;
    }

    public static double loadDoubleValue(final String[] data, final HashMap<String, Integer> dataHeaders, String header) {
        try {
            return Double.parseDouble(data[dataHeaders.get(header) + 1]);
        } catch (Exception e) {
            Globals.logError(e.toString(), e);
        }
        return 0;
    }

    public static byte loadSkillReqWeapon(final String[] data, final HashMap<String, Integer> dataHeaders) {
        try {
            byte weaponData = Byte.parseByte(data[dataHeaders.get(SKILL_REQWEAPON_HEADER) + 1]);
            return (weaponData >= Globals.NUM_EQUIP_TYPES) ? -1 : weaponData;
        } catch (Exception e) {
            Globals.logError(e.toString(), e);
        }
        return -1;
    }

    public static String[] loadSkillRawData(final byte skillCode) {
        //Globals.log(Globals.class, "Loading Skill " + String.format("0x%02X", skillCode) + " Data...", Globals.LOG_TYPE_DATA);
        try {
            InputStream skillDataFile = Globals.loadResourceAsStream("skilldata/" + String.format("0x%02X", skillCode) + ".txt");
            List<String> fileLines = IOUtils.readLines(skillDataFile, "UTF-8");
            String[] data = fileLines.toArray(new String[fileLines.size()]);
            HashMap<String, Integer> dataHeaders = Globals.getDataHeaders(data);
            String name = Globals.loadSkillName(data, dataHeaders);
            //Globals.log(Globals.class, "Finished loading Skill " + String.format("0x%02X", skillCode) + "(" + name + ") Data...", Globals.LOG_TYPE_DATA);
            return data;
        } catch (IOException | NullPointerException e) {
            Globals.logError("Could not load Skill " + String.format("0x%02X", skillCode) + " Data." + e.toString(), e);
            System.exit(101);
            return null;
        }
    }

    public static String[] loadSkillDesc(final String[] data, final HashMap<String, Integer> dataHeaders, final String descHeader) {
        try {
            int numOfLines = Integer.parseInt(data[dataHeaders.get(descHeader) + 1]);
            String[] description = new String[numOfLines];
            for (int line = 0; line < numOfLines; line++) {
                description[line] = data[dataHeaders.get(descHeader) + 2 + line];
            }
            return description;
        } catch (Exception e) {
            Globals.logError(e.toString(), e);
        }
        return new String[0];
    }

    public static String[] loadSkillDesc(final String[] data, final HashMap<String, Integer> dataHeaders) {
        return loadSkillDesc(data, dataHeaders, SKILL_DESC_HEADER);
    }

    public static String[] loadSkillLevelDesc(final String[] data, final HashMap<String, Integer> dataHeaders) {
        return loadSkillDesc(data, dataHeaders, SKILL_LEVEL_DESC_HEADER);

    }

    public static String[] loadSkillMaxBonusDesc(final String[] data, final HashMap<String, Integer> dataHeaders) {
        return loadSkillDesc(data, dataHeaders, SKILL_MAX_BONUS_DESC_HEADER);
    }

    public static String loadSkillName(final String[] data, final HashMap<String, Integer> dataHeaders) {
        try {
            return data[dataHeaders.get(SKILL_NAME_HEADER) + 1];
        } catch (Exception e) {
            Globals.logError(e.toString(), e);
        }
        return "NO_NAME";
    }

    public static int loadSkillReqLevel(final String[] data, final HashMap<String, Integer> dataHeaders) {
        try {
            return Integer.parseInt(data[dataHeaders.get(SKILL_REQLEVEL_HEADER) + 1]);
        } catch (Exception e) {
            Globals.logError(e.toString(), e);
        }
        return 0;
    }

    public static void loadServer() {
        loadNumberFormats();
        loadItemCodes();
        loadServerSkillClasses();
    }

    public static void loadServerSkillClasses() {
        for (SkillClassMap skillMap : SkillClassMap.values()) {
        }
    }

    public static void loadClient() {
        try {
            MULAN_24PT = Font.createFont(Font.TRUETYPE_FONT, loadResourceAsStream("sprites/mulan.ttf")).deriveFont(Font.PLAIN, 24);
            TITLE_FONT = MULAN_24PT.deriveFont(Font.PLAIN, 100);
        } catch (FontFormatException | IOException ex) {
            logError(ex.toString(), ex);
        }

        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_ATTACK] = 6;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_ATTACKBOW] = 8;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_STAND] = 4;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_WALK] = 8;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_BUFF] = 5;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_DEAD] = 10;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_ROLL] = 10;
        PLAYER_NUM_ANIM_FRAMES[PLAYER_ANIM_STATE_JUMP] = 3;
        loadClientOptions();
        WINDOW_SCALE = (Integer) ClientOptions.WINDOW_SCALE.getValue() / 100D;
        loadGFX();
        loadNumberFormats();
        loadItemCodes();
    }

    public final static void loadClientOptions() {
        InputStream inputStream = null;
        try {
            final Properties prop = new Properties();

            inputStream = new FileInputStream(SETTINGS_FILE);
            prop.load(inputStream);
            for (ClientOptions options : ClientOptions.values()) {
                if (prop.getProperty(options.getKey()) != null) {
                    options.setValue(prop.getProperty(options.getKey()));
                }
            }
        } catch (final FileNotFoundException e) {
            log(Globals.class, "Options", "settings.properties not found in save directory. Using default values.", Globals.LOG_TYPE_DATA);
        } catch (final IOException ex) {
            logError(ex.toString(), ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException ex) {
                    logError(ex.toString(), ex);

                }
            }
            for (ClientOptions options : ClientOptions.values()) {
                log(Globals.class, "Options", options.toString(), Globals.LOG_TYPE_DATA);
            }
        }
    }

    private static void loadNumberFormats() {
        NUMBER_FORMAT.setGroupingSize(3);
        NUMBER_FORMAT.setGroupingUsed(true);
        NUMBER_FORMAT.setDecimalSeparatorAlwaysShown(false);
        NUMBER_FORMAT.setMaximumFractionDigits(2);

        TIME_NUMBER_FORMAT.setDecimalSeparatorAlwaysShown(false);
        TIME_NUMBER_FORMAT.setMaximumFractionDigits(1);
    }

    private static final String STAT_NAME_MAXEXP = "Required EXP";
    private static final String STAT_NAME_DMGREDUCT = "Damage Reduction";
    private static final String STAT_NAME_SKILLPOINTS = "Skill Points";
    private static final String STAT_NAME_EXP = "Experience";
    private static final String STAT_NAME_POINTS = "Stat Points";
    private static final String STAT_NAME_LEVEL = "Level";
    private static final String STAT_NAME_ARMOUR = "Armour";
    private static final String STAT_NAME_REGEN = "Regen(HP/Sec)";
    private static final String STAT_NAME_CRITDMG = "Critical Hit Damage";
    private static final String STAT_NAME_CRITCHC = "Critical Hit Chance";
    private static final String STAT_NAME_MAXDMG = "Maximum Damage";
    private static final String STAT_NAME_MINDMG = "Minimum Damage";
    private static final String STAT_NAME_MAXHP = "Max HP";
    private static final String STAT_NAME_MINHP = "Current HP";
    private static final String STAT_NAME_SPIRIT = "Spirit";
    private static final String STAT_NAME_DEFENSE = "Defense";
    private static final String STAT_NAME_POWER = "Power";

    public static final String getStatName(final byte statID) {
        switch (statID) {
            case STAT_POWER:
                return STAT_NAME_POWER;
            case STAT_DEFENSE:
                return STAT_NAME_DEFENSE;
            case STAT_SPIRIT:
                return STAT_NAME_SPIRIT;
            case STAT_MINHP:
                return STAT_NAME_MINHP;
            case STAT_MAXHP:
                return STAT_NAME_MAXHP;
            case STAT_MINDMG:
                return STAT_NAME_MINDMG;
            case STAT_MAXDMG:
                return STAT_NAME_MAXDMG;
            case STAT_CRITCHANCE:
                return STAT_NAME_CRITCHC;
            case STAT_CRITDMG:
                return STAT_NAME_CRITDMG;
            case STAT_REGEN:
                return STAT_NAME_REGEN;
            case STAT_ARMOUR:
                return STAT_NAME_ARMOUR;
            case STAT_LEVEL:
                return STAT_NAME_LEVEL;
            case STAT_POINTS:
                return STAT_NAME_POINTS;
            case STAT_EXP:
                return STAT_NAME_EXP;
            case STAT_SKILLPOINTS:
                return STAT_NAME_SKILLPOINTS;
            case STAT_DAMAGEREDUCT:
                return STAT_NAME_DMGREDUCT;
            case STAT_MAXEXP:
                return STAT_NAME_MAXEXP;
        }
        return "INVALID STAT";
    }

    public static final double calcArmour(final double defense) {
        return defense * ARMOUR_MULT;
    }

    public static final double calcRegen(final double spirit) {
        return spirit * REGEN_MULT + REGEN_CONST;
    }

    public static final double calcMaxHP(final double defense) {
        return defense * HP_MULT + HP_BASE;
    }

    public static final double calcMinDmg(final double power) {
        return (1 - DMG_VARIANCE_PERCENT) * (power * DMG_MULT + DMG_BASE);
    }

    public static final double calcMaxDmg(final double power) {
        return (1 + DMG_VARIANCE_PERCENT) * (power * DMG_MULT + DMG_BASE);
    }

    public static final double calcCritChance(final double spirit) {
        return spirit / (spirit + CRITCHC_CONST) + CRITCHC_BASE;
    }

    public static final double calcCritDmg(final double spirit) {
        return spirit / CRITDMG_FACT * CRITDMG_MULT + CRITDMG_BASE;
    }

    public static final double calcReduction(final double armour) {
        return 1 - (armour / (armour + REDUCT_CONST));
    }

    public static final double calcEHP(final double reduct, final double maxHP) {
        return maxHP / reduct;
    }

    public static final int calcEXPtoNxtLvl(final double level) {
        return (int) (Math.round(Math.pow(level, 3.75) + 100));
    }

    public static byte[] longToBytes(long input) {
        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
        buffer.putLong(input);
        return buffer.array();
    }

    public static long bytesToLong(byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getLong();
    }

    public static final byte[] intToBytes(final int input) {
        ByteBuffer buffer = ByteBuffer.allocate(Integer.BYTES);
        buffer.putInt(input);
        return buffer.array();
    }

    public static final int bytesToInt(final byte[] bytes) {
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        return buffer.getInt();
    }

    private static void loadGFX() {
        for (int state = 0; state < CHAR_SPRITE.length; state++) {
            if (PLAYER_NUM_ANIM_FRAMES[state] > 0) {
                CHAR_SPRITE[state] = new BufferedImage[PLAYER_NUM_ANIM_FRAMES[state]];
                for (int frames = 0; frames < CHAR_SPRITE[state].length; frames++) {
                    String folder = "";
                    switch (state) {
                        case PLAYER_ANIM_STATE_ATTACK:
                            folder = "attack/mainhand";
                            break;
                        case PLAYER_ANIM_STATE_ATTACKBOW:
                            folder = "attack/bow";
                            break;
                        case PLAYER_ANIM_STATE_STAND:
                            folder = "stand";
                            break;
                        case PLAYER_ANIM_STATE_WALK:
                            folder = "walk";
                            break;
                        case PLAYER_ANIM_STATE_BUFF:
                            folder = "buff";
                            break;
                        case PLAYER_ANIM_STATE_DEAD:
                            folder = "dead";
                            break;
                        case PLAYER_ANIM_STATE_JUMP:
                            folder = "jump";
                            break;
                        case PLAYER_ANIM_STATE_ROLL:
                            folder = "roll";
                            break;
                    }
                    CHAR_SPRITE[state][frames] = Globals.loadTextureResource("sprites/character/" + folder + "/" + frames + ".png");
                }
            }
        }
        HUD[0] = Globals.loadTextureResource("sprites/ui/ingame/hud.png");
        HUD[1] = Globals.loadTextureResource("sprites/ui/ingame/hp.png");
        HUD[2] = Globals.loadTextureResource("sprites/ui/ingame/exphud.png");
        HUD[3] = Globals.loadTextureResource("sprites/ui/ingame/exp.png");

        for (byte i = 0; i < MENU_BG.length; i++) {
            MENU_BG[i] = Globals.loadTextureResource("sprites/ui/menu/bg" + (i + 1) + ".png");
        }

        TITLE = Globals.loadTextureResource("sprites/ui/menu/title.png");

        for (byte i = 0; i < MENU_BUTTON.length; i++) {
            MENU_BUTTON[i] = Globals.loadTextureResource("sprites/ui/menu/button" + (i + 1) + ".png");
        }

        for (byte i = 0; i < MENU_WINDOW.length; i++) {
            MENU_WINDOW[i] = Globals.loadTextureResource("sprites/ui/menu/window" + (i + 1) + ".png");
        }

        for (byte i = 0; i < MENU_UPGRADEPARTICLE.length; i++) {
            MENU_UPGRADEPARTICLE[i] = Globals.loadTextureResource("sprites/ui/menu/particle" + (i + 1) + ".png");
        }

        MENU_TABPOINTER[0] = Globals.loadTextureResource("sprites/ui/menu/pointer.png");
        MENU_TABPOINTER[1] = Globals.loadTextureResource("sprites/ui/menu/pointer2.png");
        MENU_ITEMDELETE[0] = Globals.loadTextureResource("sprites/ui/menu/delete.png");
        MENU_SMOKE[0] = Globals.loadTextureResource("sprites/ui/menu/smoke.png");
        for (byte i = 0; i < NUM_SKILLS; i++) {
            SKILL_ICON[i] = Globals.loadTextureResource("sprites/skillicon/" + String.format("0x%02X", i) + ".png");
            SKILL_DISABLED_ICON[i] = getDisabledIcon(SKILL_ICON[i]);
        }
    }

    public static final long nsToMs(final long time) {
        return TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
    }

    public static final long msToNs(final long time) {
        return TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
    }

    public static final int rng(final int i) {
        if (i > 0) {
            return RNG.nextInt(i);
        }
        return -1;
    }

    public static final BufferedImage loadTextureResource(String path) {
        try {
            InputStream resource = loadResourceAsStream(path);
            if (resource != null) {
                return ImageIO.read(resource);
            }
        } catch (IOException ex) {
            logError("Failed to load texture: " + path, ex);
        }
        return null;
    }

    public static final InputStream loadResourceAsStream(String path) {
        try {
            return FileUtils.openInputStream(new File("resources/" + path));
        } catch (IOException ex) {
            log(Globals.class, "Failed to load resource: " + path, LOG_TYPE_DATA);
        }
        return null;
    }

    public final static void loadServerConfig() {
        InputStream inputStream = null;
        try {
            final Properties prop = new Properties();

            inputStream = new FileInputStream("config.properties");
            prop.load(inputStream);
            for (ServerConfig config : ServerConfig.values()) {
                if (prop.getProperty(config.getKey()) != null) {
                    config.setValue(prop.getProperty(config.getKey()));
                }
            }
        } catch (final FileNotFoundException e) {
            log(Globals.class,
                    "Config", "config.properties not found in root directory. Using default server values.", Globals.LOG_TYPE_DATA);
        } catch (final IOException ex) {
            logError(ex.toString(), ex);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (final IOException ex) {
                    logError(ex.toString(), ex);

                }
            }
            for (ServerConfig config : ServerConfig.values()) {
                log(Globals.class, "Config", config.getDesc(), Globals.LOG_TYPE_DATA);
            }
        }
    }

    public final static void setGUILog(final JTextArea data, final JTextArea err) {
        dataConsole = data;
        errConsole = err;
    }

    public final static void createLogDirectory() {
        try {
            Files.createDirectories(Paths.get(LOG_DIR));
            if (!new File(LOG_DIR + "/" + ERRLOG_FILE).exists()) {
                Files.createFile(Paths.get(LOG_DIR + "/" + ERRLOG_FILE));
            }

            if (!new File(LOG_DIR + "/" + DATALOG_FILE).exists()) {
                Files.createFile(Paths.get(LOG_DIR + "/" + DATALOG_FILE));
            }
        } catch (final IOException e) {
            logError("Couldn't create log file.", e);
        }
    }

    public final static void log(final Class originClass, final String info, final int logType) {
        log(originClass.getSimpleName(), info, logType);
    }

    public final static void log(final Class originClass, final String customClassDesc, final String info, final int logType) {
        log(originClass.getSimpleName() + " " + customClassDesc, info, logType);
    }

    public final static void log(final String className, final String info, final int logType) {
        final Runnable logging = () -> {
            if (logger == null) {
                logger = new Logger() {
                    @Override
                    public void log(int level, String category, String message, Throwable ex) {
                        StringBuilder builder = new StringBuilder(256);
                        builder.append('[');
                        builder.append(String.format("%1$td/%1$tm/%1$tY %1$tT %1$tZ", System.currentTimeMillis()));
                        builder.append(']');

                        builder.append(' ');
                        switch (level) {
                            case LEVEL_ERROR:
                                builder.append(" ERROR: ");
                                break;
                            case LEVEL_WARN:
                                builder.append("  WARN: ");
                                break;
                            case LEVEL_INFO:
                                builder.append("  INFO: ");
                                break;
                            case LEVEL_DEBUG:
                                builder.append(" DEBUG: ");
                                break;
                            case LEVEL_TRACE:
                                builder.append(" TRACE: ");
                                break;
                        }
                        builder.append(' ');

                        builder.append('[');
                        builder.append(category);
                        builder.append("] ");
                        builder.append(message);
                        if (ex != null) {
                            StringWriter writer = new StringWriter(256);
                            ex.printStackTrace(new PrintWriter(writer));
                            builder.append('\n');
                            builder.append(writer.toString().trim());
                        }
                        System.out.println(builder);

                        if (LOGGING) {
                            String logFile;
                            switch (level) {
                                case LEVEL_ERROR:
                                    logFile = ERRLOG_FILE;
                                    break;
                                default:
                                    logFile = DATALOG_FILE;
                                    break;
                            }
                            try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(LOG_DIR, logFile), true)))) {
                                out.println(builder);
                            } catch (final IOException e) {
                                System.err.println(e);
                            }
                        }

                        switch (level) {
                            case LEVEL_ERROR:
                                if (errConsole != null) {
                                    errConsole.append("\n" + builder);
                                    errConsole.setCaretPosition(errConsole.getDocument().getLength());
                                }
                                break;
                            default:
                                if (dataConsole != null) {
                                    dataConsole.append("\n" + builder);
                                    dataConsole.setCaretPosition(dataConsole.getDocument().getLength());
                                }
                                break;
                        }
                    }
                };
                Log.setLogger(logger);
            }

            switch (logType) {
                case LOG_TYPE_ERR:
                    Log.error(className, info);
                    break;
                case LOG_TYPE_DATA:
                    Log.info(className, info);
                    break;
            }
        };
        LOG_THREAD.execute(logging);
    }

    public static final void logError(final String errorMessage, final Exception e) {
        Log.error(errorMessage, e);
    }

    public static boolean hasPastDuration(final long currentDuration, final long durationToPast) {
        if (durationToPast <= 0) {
            return true;
        }
        return currentDuration >= durationToPast;
    }

    public static void loadItemCodes() {
        ITEM_UPGRADE_CODES.add(100);

        try {
            InputStream itemFile = Globals.loadResourceAsStream("itemdata/equip/itemcodes.txt");
            try (LineIterator it = IOUtils.lineIterator(itemFile, "UTF-8")) {
                while (it.hasNext()) {
                    String line = it.nextLine();
                    try {
                        int itemcode = Integer.parseInt(line);
                        ITEM_EQUIP_CODES.add(itemcode);
                    } catch (NumberFormatException e) {
                    }
                }
            }

            for (byte equipType = 0; equipType < NUM_EQUIP_TYPES; equipType++) {
                ITEM_EQUIP_TYPE_CODES_MAP.put(equipType, new HashSet<>(10));
            }

            for (int equipCode : ITEM_EQUIP_CODES) {
                ITEM_EQUIP_TYPE_CODES_MAP.get(getEquipType(equipCode)).add(equipCode);
            }

        } catch (IOException e) {
            Globals.logError("Could not load item codes from data", e);
            System.exit(102);
        }
    }
}
