import commons.util.Conversions;
import org.junit.*;
import org.junit.Test;

public class ConversionsTest {

    Conversions conversions = new Conversions();
    //conversion reference https://www.rapidtables.com/convert/number/hex-to-decimal.html
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
        int value = conversions.unsignedBytesToInt(data);
        System.out.println(value);
        Assert.assertEquals(16777215, value);

        //Max for 2 bytes is 65535
        byte[] data2 = {(byte)0xFF,(byte)0xFF};
        value = conversions.unsignedBytesToInt(data2);
        System.out.println(value);
        Assert.assertEquals(65535, value);
    }

    @Test
    public void unsignedBytesToInt_One(){
        //check 1 with 3 bytes
        byte[] data = {(byte)0x00, (byte)0x00, (byte)0x01};
        int value = conversions.unsignedBytesToInt(data);
        System.out.println(value);
        Assert.assertEquals(1, value);

        //check 1 with 2 bytes
        byte[] data2 = {(byte)0x00, (byte)0x01};
        value = conversions.unsignedBytesToInt(data2);
        System.out.println(value);
        Assert.assertEquals(1, value);
    }

    @Test
    public void unsignedBytesToInt_MidValue(){
        byte[] data = {(byte)0x80, (byte)0x00, (byte)0x00};
        int value = conversions.unsignedBytesToInt(data);
        System.out.println(value);
        Assert.assertEquals(8388608, value);

        //Mid value for 2 bytes is 32768
        byte[] data2 = {(byte)0x80, (byte)0x00};
        value = conversions.unsignedBytesToInt(data2);
        System.out.println(value);
        Assert.assertEquals(32768, value);
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

        long value = conversions.unsignedBytesToLong(data);
        long exp = 72057594037927935L;
        Assert.assertEquals(exp, value);



        //4bytes unsigned integer max value is 4294967295
        byte[] data2 = {(byte) 0xFF,//1
                (byte) 0xFF,//2
                (byte) 0xFF,//3
                (byte) 0xFF
        };
        value = conversions.unsignedBytesToLong(data2);

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

        long value = conversions.unsignedBytesToLong(data);
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
        long value = conversions.unsignedBytesToLong(data);
        Assert.assertEquals(9109505, value);

        byte[] data2 = {(byte)0x00,//1
                (byte)0x00,//2
                (byte)0xD4,//3
                (byte)0x00,//4
                (byte)0x8B,//5
                (byte)0x00,//6
                (byte)0x01};//7s

        //should be 910542176257
        value = conversions.unsignedBytesToLong(data2);
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
        value = conversions.unsignedBytesToLong(data3);
        Assert.assertEquals(576693851100545025L, value);
    }

    @Test @Ignore
    public void twosCompliment_randomValues(){

        byte[] data = {(byte)0xFF,//1
                (byte)0xFF,//2
                (byte)0xFF,//3
                (byte)0xFF};//4;
        //negative 1
        byte[] compliment = conversions.fromTwosCompliment(data);
        int value = conversions.unsignedBytesToInt(compliment);

        Assert.assertEquals(1, value);

        byte[] data2 = {(byte)0x80,//1
                (byte)0x00,//2
                (byte)0x00,//3
                (byte)0x00};//4;
        compliment = conversions.fromTwosCompliment(data2);
        for(byte b : compliment){
            System.out.println(b);
        }
        value = conversions.unsignedBytesToInt(compliment);
        System.out.println(value);
    }

    @Test
    public void signedBytesToInt_Max(){
        //Max for 3 bytes
        byte[] data = {(byte)0x7F,//1
                (byte)0xFF,
                (byte)0xFF
                };
        int value = conversions.signedBytesToInt(data);
        Assert.assertEquals(8388607, value);

        //Max negative for 3 bytes

        byte[] data2 = {(byte)0x80,//1
                (byte)0x00,
                (byte)0x00
        };
        value = conversions.signedBytesToInt(data2);
        Assert.assertEquals(-8388608, value);
    }

    @Test
    public void signedBytesToInt_RandValue(){
        //-1
        byte[] data = {(byte)0xFF,//1
                (byte)0xFF,//3
                (byte)0xFF};//4;
        int value = conversions.signedBytesToInt(data);
        Assert.assertEquals(-1, value);
        //+1
        byte[] data1 = {
                (byte)0x00,
                (byte)0x00,
                (byte)0x01
        };
        value = conversions.signedBytesToInt(data1);
        Assert.assertEquals(1, value);
        //-2
        byte[] data2 = {
                (byte)0xFF,
                (byte)0xFF,
                (byte)0xFE
        };
        value = conversions.signedBytesToInt(data2);
        Assert.assertEquals(-2, value);

        //2
        byte[] data3 = {
                (byte)0x00,
                (byte)0x00,
                (byte)0x02
        };
        value = conversions.signedBytesToInt(data3);
        Assert.assertEquals(2, value);
    }

    @Test
    public void signedBytesToLong_Max(){
        //unsigned int max
        byte[] data = {(byte)0x7F,//1
                (byte)0xFF,
                (byte)0xFF,
                (byte)0xFF
        };
        long value = conversions.signedBytesToLong(data);
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
        long value = conversions.signedBytesToLong(data);
        //2147483647

        Assert.assertEquals(-39044, value);


    }

    @Test
    public void unsignedIntToBytes_Max(){
        int max3Bytes = 16777215;
        byte[] data = conversions.unsignedIntToBytes(max3Bytes, 3);

        Assert.assertEquals(3, data.length);
        Assert.assertEquals(-1, data[0]);//255
        Assert.assertEquals(-1, data[1]);//255
        Assert.assertEquals(-1, data[2]);//255
    }

    @Test
    public void unsigedLongToBytes_Max(){
        long max4Bytes = 4294967295L;
        byte[] data = conversions.unsignedLongToBytes(max4Bytes, 4);
        Assert.assertEquals(4, data.length);

        Assert.assertEquals(-1, data[0]);//255
        Assert.assertEquals(-1, data[1]);//255
        Assert.assertEquals(-1, data[2]);//255
        Assert.assertEquals(-1, data[3]);//255

    }
}