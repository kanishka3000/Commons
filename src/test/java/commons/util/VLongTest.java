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

public class VLongTest {

    //conversion reference https://www.rapidtables.com/convert/number/hex-to-decimal.html
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void unsignedBytesToLong_Max(){

        //Max for 7 bytes 7.2057594e+16
        byte[] data = {(byte)0xFF,//1
                (byte)0xFF,//2
                (byte)0xFF,//3
                (byte)0xFF,//4
                (byte)0xFF,//5
                (byte)0xFF,//6
                (byte)0xFF};//7s

        long  value = new VLong(data, false).getLongValue();
        long exp = 72057594037927935L;
        Assert.assertEquals(exp, value);



        //4bytes unsigned integer max value is 4294967295
        byte[] data2 = {(byte) 0xFF,//1
                (byte) 0xFF,//2
                (byte) 0xFF,//3
                (byte) 0xFF
        };
        value = value = new VLong(data2, false).getLongValue();

        Assert.assertEquals(4294967295L, value);

    }

    @Test
    public void unsignedBytesToLong_MinValue(){
        byte[] data = {(byte)0x00,//1
                (byte)0x00,//2
                (byte)0x00,//3
                (byte)0x00,//4
                (byte)0x00,//5
                (byte)0x00,//6
                (byte)0x01};//7s

        long value = value = new VLong(data, false).getLongValue();
        Assert.assertEquals(1, value);
    }

    @Test
    public void unsignedBytesToLong_MidValues(){

        byte[] data = {(byte)0x00,//1
                (byte)0x00,//2
                (byte)0x00,//3
                (byte)0x00,//4
                (byte)0x8B,//5
                (byte)0x00,//6
                (byte)0x01};//7s
        //should be 597000454145
        long value = value = new VLong(data, false).getLongValue();
        Assert.assertEquals(9109505, value);

        byte[] data2 = {(byte)0x00,//1
                (byte)0x00,//2
                (byte)0xD4,//3
                (byte)0x00,//4
                (byte)0x8B,//5
                (byte)0x00,//6
                (byte)0x01};//7s

        //should be 910542176257
        value = value = new VLong(data2, false).getLongValue();
        Assert.assertEquals(910542176257L, value);



        byte[] data3 = {(byte)0x08,//1
                (byte)0x00,//2
                (byte)0xD4,//3
                (byte)0x00,//4
                (byte)0x8B,//5
                (byte)0x00,//6
                (byte)0x00,//7
                (byte)0x01};//8s

        //should be 576693851100545025
        value = new VLong(data3, false).getLongValue();
        Assert.assertEquals(576693851100545025L, value);
    }

    @Test
    public void signedBytesToLong_Max(){
        //unsigned int max
        byte[] data = {(byte)0x7F,//1
                (byte)0xFF,
                (byte)0xFF,
                (byte)0xFF
        };
        long value = new VLong(data, true).getLongValue();
        //2147483647

        Assert.assertEquals(2147483647L, value);

    }

    @Test
    public void singedByteToLong_Min(){

    }

    @Test
    public void signedByteToLong_Rand(){
        byte[] data = {(byte)0xFF,//1
                (byte)0xFF,
                (byte)0x67,
                (byte)0x7C
        };
        long value = new VLong(data, true).getLongValue();
        //2147483647

        Assert.assertEquals(-39044, value);


    }

    @Test
    public void unsigedLongToBytes_Max(){
        long max4Bytes = 4294967295L;
        byte[] data = new VLong(max4Bytes, false).getBytes(4);

        Assert.assertEquals(4, data.length);

        Assert.assertEquals(-1, data[0]);//255
        Assert.assertEquals(-1, data[1]);//255
        Assert.assertEquals(-1, data[2]);//255
        Assert.assertEquals(-1, data[3]);//255

    }

    @Test
    public void signedLong_FromBytes(){
        long max = 9223372036854775807L;
        ByteBuffer buff = ByteBuffer.allocate(8);
        buff.putLong(max);

        byte[] data = buff.array();

        long output = new VLong(data, true).getLongValue();

        Assert.assertEquals(9223372036854775807L, output);

        long min = -9223372036854775808L;
        ByteBuffer buff2 = ByteBuffer.allocate(8);
        buff2.putLong(min);

        data = buff2.array();

        output = new VLong(data, true).getLongValue();
        Assert.assertEquals(-9223372036854775808L, output);

    }

    @Test
    public void singedLong_FromPrimitive(){
        long max = 9223372036854775807L;
        byte[] data = new VLong(max , true).getBytes(8);

        ByteBuffer buffer = ByteBuffer.wrap(data);

        long value = buffer.getLong();

        Assert.assertEquals(9223372036854775807L, value);

        long min = -9223372036854775808L;
        data = new VLong(min, true).getBytes( 8);
        buffer = ByteBuffer.wrap(data);

        value = buffer.getLong();

        Assert.assertEquals(-9223372036854775808L, value);
    }

    @Test
    public void unsignedInt_ToPrimitive(){
        //max value
        byte[] data = {(byte)0xFF,
                (byte)0xFF,
                (byte)0xFF,
                (byte)0xFF};

        long val = new VLong(data, false).getLongValue();
        Assert.assertEquals(4294967295L,val);

        //Random value 2147483245 -> 7F FF FE 6D
        byte[] data2= {(byte)0x7F,
                (byte)0xFF,
                (byte)0xFE,
                (byte)0x6D};

        val = new VLong(data2, false).getLongValue();
        Assert.assertEquals(2147483245,val);
    }


    @Test
    public void threeByteInt_ToPrimitive(){
        //max value
        byte[] data = {(byte)0xFF,
                (byte)0xFF,
                (byte)0xFF};
        long val = new VLong(data, false).getLongValue();
        Assert.assertEquals(16777215,val);

        //Random value 14757816 -> E12FB8
        byte[] data2 = {
                (byte)0xE1,
                (byte)0x2F,
                (byte)0xB8,
        };
        val = new VLong(data2, false).getLongValue();
        Assert.assertEquals(14757816,val);

    }


    @Test
    public void unsignedInt_ToByte(){
        long input = 4294967295L;
        byte[] output = new VLong(input, false).getBytes( 4);

        Assert.assertEquals((byte)0xFF, output[0]);
        Assert.assertEquals((byte)0xFF, output[1]);
        Assert.assertEquals((byte)0xFF, output[2]);
        Assert.assertEquals((byte)0xFF, output[3]);

        //Random value 2969652953 -> B1014ED9
        input = 2969652953L;
        output = new VLong(input, false).getBytes( 4);
        Assert.assertEquals((byte)0xB1, output[0]);
        Assert.assertEquals((byte)0x01, output[1]);
        Assert.assertEquals((byte)0x4E, output[2]);
        Assert.assertEquals((byte)0xD9, output[3]);

    }

}