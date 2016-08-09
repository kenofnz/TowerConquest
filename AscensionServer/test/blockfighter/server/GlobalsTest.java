package blockfighter.server;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class GlobalsTest {

    @Test
    public void testGetStatNameReturnInvalidStatWhenStatIDIsInvalid() {
        System.out.println("getStatName: Return Invalid Stat When StatID Is Invalid");
        byte statID = 40;
        String expResult = "INVALID STAT";
        String result = Globals.getStatName(statID);
        System.out.println("Expected: <" + expResult + ">, Result: <" + result + ">");
        assertEquals(expResult, result);
    }

    @Test
    public void testGetStatNameReturnCorrectNameWhenStatIDIsValid() {
        System.out.println("getStatName: Return Correct Stat Name When StatID Is Valid");
        String expResult = "INVALID STAT";
        String result;
        for (byte i = 0; i < Globals.NUM_STATS; i++) {
            result = Globals.getStatName(i);
            assertNotEquals(expResult, result);
        }

        byte statID = Globals.STAT_ARMOR;
        expResult = "Armor";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_POWER;
        expResult = "Power";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_DEFENSE;
        expResult = "Defense";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_SPIRIT;
        expResult = "Spirit";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_MINHP;
        expResult = "Current HP";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_MAXHP;
        expResult = "Max HP";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_MINDMG;
        expResult = "Minimum Damage";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_MAXDMG;
        expResult = "Maximum Damage";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_CRITCHANCE;
        expResult = "Critical Hit Chance";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_CRITDMG;
        expResult = "Critical Hit Damage";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_REGEN;
        expResult = "Regen(HP/Sec)";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_LEVEL;
        expResult = "Level";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_POINTS;
        expResult = "Stat Points";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_EXP;
        expResult = "Experience";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_SKILLPOINTS;
        expResult = "Skill Points";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_DAMAGEREDUCT;
        expResult = "Damage Reduction";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);

        statID = Globals.STAT_MAXEXP;
        expResult = "Required EXP";
        result = Globals.getStatName(statID);
        assertEquals(expResult, result);
    }

    @Test
    public void testLongToBytes() {
        System.out.println("longToBytes: Long Max Value convert to correct bytes");
        long input = Long.MAX_VALUE;
        byte[] expResult = {(byte) 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        byte[] result = Globals.longToBytes(input);
        System.out.println("Expected: " + Arrays.toString(expResult));
        System.out.println("Result: " + Arrays.toString(result));
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testBytesToLong() {
        System.out.println("bytesToLong: Bytes convert to Long Max Value");
        byte[] bytes = {(byte) 0x7F, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF};
        long expResult = Long.MAX_VALUE;
        long result = Globals.bytesToLong(bytes);
        System.out.println("Expected: <" + expResult + ">, Result: <" + result + ">");
        assertEquals(expResult, result);
    }

    @Test
    public void testIntToBytes() {
        System.out.println("intToBytes: Integer Min Value convert to correct bytes");
        int input = Integer.MIN_VALUE;
        byte[] expResult = {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        byte[] result = Globals.intToBytes(input);
        System.out.println("Expected: " + Arrays.toString(expResult));
        System.out.println("Result: " + Arrays.toString(result));
        assertArrayEquals(expResult, result);
    }

    @Test
    public void testBytesToInt() {
        System.out.println("bytesToInt: Bytes convert to Integer Min Value");
        byte[] bytes = {(byte) 0x80, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        int expResult = Integer.MIN_VALUE;
        int result = Globals.bytesToInt(bytes);
        System.out.println("Expected: <" + expResult + ">, Result: <" + result + ">");
        assertEquals(expResult, result);
    }

    @Test
    public void testNsToMs() {
        System.out.println("nsToMs: 1,000,000,000 ns converted to 1,000 ms");
        long time = 1000000000L;
        long expResult = TimeUnit.MILLISECONDS.convert(time, TimeUnit.NANOSECONDS);
        long result = Globals.nsToMs(time);
        System.out.println("Expected: <" + expResult + ">, Result: <" + result + ">");
        assertEquals(expResult, result);
    }

    @Test
    public void testMsToNs() {
        System.out.println("msToNs: 1,000 ms converted to 1,000,000,000 ms");
        long time = 1000L;
        long expResult = TimeUnit.NANOSECONDS.convert(time, TimeUnit.MILLISECONDS);
        long result = Globals.msToNs(time);
        System.out.println("Expected: <" + expResult + ">, Result: <" + result + ">");
        assertEquals(expResult, result);
    }

    @Test
    public void testRngReturnInvalidNumberWhenInputIsZero() {
        System.out.println("rng: Return -1 When Input Is 0");
        int i = 0;
        int expResult = -1;
        int result = Globals.rng(i);
        System.out.println("Expected: <" + expResult + ">, Result: <" + result + ">");
        assertEquals(expResult, result);
    }

    @Test
    public void testDurationHasPastFalseWhenDurationHasNotPast() {
        System.out.println("hasPastDuration: False When Duration Has Not Past");
        int currentDuration = 5000;
        int durationToPast = 6000;
        boolean result = Globals.hasPastDuration(currentDuration, durationToPast);
        assertFalse(result);
    }

    @Test
    public void testDurationHasPastTrueWhenDurationHasPast() {
        System.out.println("hasPastDuration: True When Duration Has Past");
        int currentDuration = 5000;
        int durationToPast = 0;

        boolean result = Globals.hasPastDuration(currentDuration, durationToPast);
        assertTrue(result);
    }
}
