/*
 * Copyright 2006 (C) Kanishka Weerasekara
 *
 * Created on : 18/11/2018
 * Author     : Kanishka Weerasekara
 *

 */
package commons.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.nio.ByteBuffer;

import static org.junit.Assert.*;

public class VIntegerTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void unsignedBytesToInt_Max() {
        //Max for 3 bytes is 16777215
        byte[] data = {(byte)0xFF,(byte)0xFF, (byte)0xFF};
        int value = new VInteger(data, false).getIntValue();
        System.out.println(value);
        Assert.assertEquals(16777215, value);

        //Max for 2 bytes is 65535
        byte[] data2 = {(byte)0xFF,(byte)0xFF};
        value = new VInteger(data2, false).getIntValue();
        System.out.println(value);
        Assert.assertEquals(65535, value);
    }

    @Test
    public void unsignedBytesToInt_One(){
        //check 1 with 3 bytes
        byte[] data = {(byte)0x00, (byte)0x00, (byte)0x01};
        int value = new VInteger(data, false).getIntValue();
        System.out.println(value);
        Assert.assertEquals(1, value);

        //check 1 with 2 bytes
        byte[] data2 = {(byte)0x00, (byte)0x01};
        value = new VInteger(data2, false).getIntValue();
        System.out.println(value);
        Assert.assertEquals(1, value);
    }

    @Test
    public void unsignedBytesToInt_MidValue(){
        byte[] data = {(byte)0x80, (byte)0x00, (byte)0x00};
        int value = new VInteger(data, false).getIntValue();
        System.out.println(value);
        Assert.assertEquals(8388608, value);

        //Mid value for 2 bytes is 32768
        byte[] data2 = {(byte)0x80, (byte)0x00};
        value = new VInteger(data2, false).getIntValue();
        System.out.println(value);
        Assert.assertEquals(32768, value);
    }

    @Test
    public void signedBytesToInt_Max(){
        //Max for 3 bytes
        byte[] data = {(byte)0x7F,//1
                (byte)0xFF,
                (byte)0xFF
        };
        int value = new VInteger(data, true).getIntValue();
        Assert.assertEquals(8388607, value);

        //Max negative for 3 bytes

        byte[] data2 = {(byte)0x80,//1
                (byte)0x00,
                (byte)0x00
        };
        value = new VInteger(data2, true).getIntValue();
        Assert.assertEquals(-8388608, value);
    }

    @Test
    public void signedBytesToInt_RandValue(){
        //-1
        byte[] data = {(byte)0xFF,//1
                (byte)0xFF,//3
                (byte)0xFF};//4;
        int value = new VInteger(data, true).getIntValue();
        Assert.assertEquals(-1, value);
        //+1
        byte[] data1 = {
                (byte)0x00,
                (byte)0x00,
                (byte)0x01
        };
        value = new VInteger(data1, true).getIntValue();
        Assert.assertEquals(1, value);
        //-2
        byte[] data2 = {
                (byte)0xFF,
                (byte)0xFF,
                (byte)0xFE
        };
        value = new VInteger(data2, true).getIntValue();
        Assert.assertEquals(-2, value);

        //2
        byte[] data3 = {
                (byte)0x00,
                (byte)0x00,
                (byte)0x02
        };
        value = new VInteger(data3, true).getIntValue();
        Assert.assertEquals(2, value);
    }


    @Test
    public void unsignedIntToBytes_Max(){
        int max3Bytes = 16777215;
        byte[] data = new VInteger(max3Bytes, false).getBytes( 3);

        Assert.assertEquals(3, data.length);
        Assert.assertEquals(-1, data[0]);//255
        Assert.assertEquals(-1, data[1]);//255
        Assert.assertEquals(-1, data[2]);//255
    }

    @Test
    public void signedInteger_FromBytes(){
        int max = 2147483647;
        ByteBuffer buff = ByteBuffer.allocate(4);
        buff.putInt(max);

        byte[] data = buff.array();
        int output = new VInteger(data, true).getIntValue();

        Assert.assertEquals(2147483647, output);

        int min = -2147483648;
        ByteBuffer buff2 = ByteBuffer.allocate(4);
        buff2.putInt(min);
        data = buff2.array();

        output = new VInteger(data, true).getIntValue();

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

        int output = new VInteger(data, true).getIntValue();

        Assert.assertEquals(tm, output);
    }

    @Test
    public void signedInt_FromPrimitive(){
        int max = 2147483647;
        byte[] data = new VInteger(max,true).getBytes(4);

        ByteBuffer buffer = ByteBuffer.wrap(data);

        int value = buffer.getInt();

        Assert.assertEquals(2147483647, value);

        int min = -2147483648;
        data = new VInteger(min, true).getBytes( 4);

        buffer = ByteBuffer.wrap(data);

        value = buffer.getInt();

        Assert.assertEquals(-2147483648, value);

    }

    @Test
    public void unsignedByte_ToPrimitive(){
        //max value 255
        byte[] data = {(byte)0xFF};
        int val = new VInteger(data, false).getIntValue();

        Assert.assertEquals(255,val);

        //Random value 150
        byte[] data2 = {(byte)0x96};
        val = new VInteger(data2, false).getIntValue();
        Assert.assertEquals(150,val);
    }

    @Test
    public void unsignedShort_ToPrimitive(){
        //max value
        byte[] data = {(byte)0xFF, (byte)0xFF};
        int val = new VInteger(data, false).getIntValue();

        Assert.assertEquals(65535,val);

        ByteBuffer buffer = ByteBuffer.wrap(data);
        char c = buffer.getChar();

        Assert.assertEquals(65535, c);

        //Random value 30000
        byte[] data2 = {(byte)0x75, (byte)0x30};
        val = new VInteger(data2, false).getIntValue();

        Assert.assertEquals(30000,val);
    }



    @Test
    public void unsignedByte_ToByte(){
        int input = 255;
        byte[] output = new VInteger(input, false).getBytes(1);

        Assert.assertEquals(1, output.length);
        Assert.assertEquals(-1, output[0]);

        //Random value 150
        input =  150;
        output = new VInteger(input,false).getBytes( 1);
        byte b = (byte)0x96;
        Assert.assertEquals(b, output[0]);
    }

    @Test
    public void unsignedShort_ToByte(){
        int input = 65535;
        byte[] output = new VInteger(input, false).getBytes( 2);
        Assert.assertEquals(2, output.length);

        Assert.assertEquals(-1, output[0]);
        Assert.assertEquals(-1, output[1]);

        //Random Value 30000
        input = 30000;
        output = new VInteger(input, false).getBytes( 2);
        Assert.assertEquals((byte)0x75, output[0]);
        Assert.assertEquals((byte) 0x30, output[1]);
    }

}