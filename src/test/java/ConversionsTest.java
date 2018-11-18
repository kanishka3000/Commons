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





}