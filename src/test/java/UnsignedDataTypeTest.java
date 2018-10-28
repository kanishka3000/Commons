import commons.util.Conversions;
import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;

public class UnsignedDataTypeTest {
    //signed byte use native
    //unsigned byte unsignedBytesToInt
    //signed short use native
    //unsigned short unsignedBytesToInt OR char
    //signed int use native
    //unsigned int unsignedBytesToLong
    //special case 3 bytes

    Conversions conversions = new Conversions();
    @Test
    public void unsignedByte_ToPrimitive(){
        //max value 255
        byte[] data = {(byte)0xFF};
        int val = conversions.unsignedBytesToInt(data);

        Assert.assertEquals(255,val);

        //Random value 150
        byte[] data2 = {(byte)0x96};
        val = conversions.unsignedBytesToInt(data2);
        Assert.assertEquals(150,val);
    }

    @Test
    public void unsignedShort_ToPrimitive(){
        //max value
        byte[] data = {(byte)0xFF, (byte)0xFF};
        int val = conversions.unsignedBytesToInt(data);

        Assert.assertEquals(65535,val);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        char c = buffer.getChar();

        Assert.assertEquals(65535, c);

        //Random value 30000
        byte[] data2 = {(byte)0x75, (byte)0x30};
        val = conversions.unsignedBytesToInt(data2);

        Assert.assertEquals(30000,val);
    }

    @Test
    public void unsignedInt_ToPrimitive(){
        //max value
        byte[] data = {(byte)0xFF,
                    (byte)0xFF,
                    (byte)0xFF,
                    (byte)0xFF};

        long val = conversions.unsignedBytesToLong(data);
        Assert.assertEquals(4294967295L,val);

        //Random value 2147483245 -> 7F FF FE 6D
        byte[] data2= {(byte)0x7F,
                (byte)0xFF,
                (byte)0xFE,
                (byte)0x6D};

        val = conversions.unsignedBytesToLong(data2);
        Assert.assertEquals(2147483245,val);
    }

    @Test
    public void threeByteInt_ToPrimitive(){
        //max value
        byte[] data = {(byte)0xFF,
                (byte)0xFF,
                (byte)0xFF};
        long val = conversions.unsignedBytesToLong(data);
        Assert.assertEquals(16777215,val);

        //Random value 14757816 -> E12FB8
        byte[] data2 = {
                (byte)0xE1,
                (byte)0x2F,
                (byte)0xB8,
        };
        val = conversions.unsignedBytesToLong(data2);
        Assert.assertEquals(14757816,val);

    }

    @Test
    public void unsignedByte_ToByte(){
        int input = 255;
        byte[] output = conversions.unsignedIntToBytes(input, 1);

        Assert.assertEquals(1, output.length);
        Assert.assertEquals(-1, output[0]);

        //Random value 150
        input =  150;
        output = conversions.unsignedIntToBytes(input, 1);
        byte b = (byte)0x96;
        Assert.assertEquals(b, output[0]);
    }

    @Test
    public void unsignedShort_ToByte(){
        int input = 65535;
        byte[] output = conversions.unsignedIntToBytes(input, 2);
        Assert.assertEquals(2, output.length);

        Assert.assertEquals(-1, output[0]);
        Assert.assertEquals(-1, output[1]);

        //Random Value 30000
        input = 30000;
        output = conversions.unsignedIntToBytes(input, 2);
        Assert.assertEquals((byte)0x75, output[0]);
        Assert.assertEquals((byte) 0x30, output[1]);
    }

    @Test
    public void unsignedInt_ToByte(){
        long input = 4294967295L;
        byte[] output = conversions.unsignedLongToBytes(input, 4);

        Assert.assertEquals((byte)0xFF, output[0]);
        Assert.assertEquals((byte)0xFF, output[1]);
        Assert.assertEquals((byte)0xFF, output[2]);
        Assert.assertEquals((byte)0xFF, output[3]);

        //Random value 2969652953 -> B1014ED9
        input = 2969652953L;
        output = conversions.unsignedLongToBytes(input, 4);
        Assert.assertEquals((byte)0xB1, output[0]);
        Assert.assertEquals((byte)0x01, output[1]);
        Assert.assertEquals((byte)0x4E, output[2]);
        Assert.assertEquals((byte)0xD9, output[3]);

    }



}
