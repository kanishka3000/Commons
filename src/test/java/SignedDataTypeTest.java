import commons.util.Conversions;
import org.apache.commons.lang3.Conversion;
import org.junit.Assert;
import org.junit.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class SignedDataTypeTest {

    Conversions conversions = new Conversions();
    @Test
    public void signedInteger_FromBytes(){
        int max = 2147483647;
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.putInt(max);

        byte[] data = buff.array();
        int output = conversions.signedBytesToInt(data);

        Assert.assertEquals(2147483647, output);

        int min = -2147483648;
        ByteBuffer buff2 = ByteBuffer.allocate(4);
        buff2.putInt(min);
        data = buff2.array();

        output = conversions.signedBytesToInt(data);

        Assert.assertEquals(-2147483648, output);


    }
    @Test
    public void randomIntTest(){
        long time = System.currentTimeMillis();
        int tm = (int)time;
        tm = tm * -1;
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.putInt(tm);
        byte[] data = buff.array();

        int output = conversions.signedBytesToInt(data);

        Assert.assertEquals(tm, output);
    }
    @Test
    public void signedLong_FromBytes(){
        long max = 9223372036854775807L;
        ByteBuffer buff = ByteBuffer.allocate(8);
        buff.putLong(max);

        byte[] data = buff.array();

        long output = conversions.signedBytesToLong(data);

        Assert.assertEquals(9223372036854775807L, output);

        long min = -9223372036854775808L;
        ByteBuffer buff2 = ByteBuffer.allocate(8);
        buff2.putLong(min);

        data = buff2.array();

        output = conversions.signedBytesToLong(data);
        Assert.assertEquals(-9223372036854775808L, output);

    }

    @Test
    public void signedInt_FromPrimitive(){
        int max = 2147483647;
        byte[] data = conversions.signedIntToBytes(max, 4);

        ByteBuffer buffer = ByteBuffer.wrap(data);

        int value = buffer.getInt();

        Assert.assertEquals(2147483647, value);

        int min = -2147483648;
        data = conversions.signedIntToBytes(min, 4);

        buffer = ByteBuffer.wrap(data);

        value = buffer.getInt();

        Assert.assertEquals(-2147483648, value);

    }

    @Test
    public void singedLong_FromPrimitive(){
        long max = 9223372036854775807L;
        byte[] data = conversions.signedLongToBytes(max , 8);

        ByteBuffer buffer = ByteBuffer.wrap(data);

        long value = buffer.getLong();

        Assert.assertEquals(9223372036854775807L, value);

        long min = -9223372036854775808L;
        data = conversions.signedLongToBytes(min, 8);
        buffer = ByteBuffer.wrap(data);

        value = buffer.getLong();

        Assert.assertEquals(-9223372036854775808L, value);
    }
}
