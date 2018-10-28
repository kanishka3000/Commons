package commons.util;

import java.nio.ByteBuffer;

public class Conversions {
    //signed byte use native
    //unsigned byte unsignedBytesToInt
    //signed short use native
    //unsigned short unsignedBytesToInt
    //signed int use native
    //unsigned int unsignedBytesToLong
    //special case 3 bytes
    public int unsignedBytesToInt(byte[] bytes){
        //Big endian implementation
        int value = 0;
        int offset = 0;
        for(int i = bytes.length -1; i >= 0;i--){
            int val = (bytes[i] & 0xFF) << offset;
            value+=val;
            offset+=8;
        }
        return value;

    }

    public byte[] unsignedIntToBytes(int value, int byteCount){

        byte[] output = new byte[byteCount];
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(value);
        byte[] input = buffer.array();
        int i = 3;
        int j = byteCount  -1;
        for(; i >=0 && j >=0; i--,j--){
            byte bIn = input[i];
            output[j] = bIn;
        }

        return output;
    }

    public long unsignedBytesToLong(byte[] bytes){
        //Big endian implementation
        long value = 0;
        long offset = 0;
        for(int i = bytes.length -1; i >= 0;i--){
            long val = ((long) bytes[i] & 0x00FF) << offset;
            value = value+val;
            offset+=8;
        }
        return value;

    }

    public byte[] unsignedLongToBytes(long value, int byteCount){
        byte[] output = new byte[byteCount];
        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(value);
        byte[] input = buffer.array();
        int i = 7;
        int j = byteCount  -1;
        for(; i >=0 && j >=0; i--,j--){
            byte bIn = input[i];
            output[j] = bIn;
        }

        return output;
    }



    public int signedBytesToInt(byte[] bytes){
        int val = 0;
        byte mostSignificant = bytes[0];

        //big endian implementation
        if(mostSignificant < (byte) 0) {
            byte[] twosComplimentReversed = fromTwosCompliment(bytes);
            val = unsignedBytesToInt(twosComplimentReversed);
            val=val+1;
            val = val * -1;
        }
        else
        {
            val = unsignedBytesToInt(bytes);
        }

        return val;
    }

    public byte[] signedIntToBytes(int value, int byteSize){
        byte[] output = new byte[byteSize];

        byte[] processedBuffer = null;
        ByteBuffer buffer = ByteBuffer.allocate(4);
        if(value < 0){
            int positiveValue = value * -1;

            buffer.putInt(positiveValue);
            byte[] data = buffer.array();
            byte[] twosComplemented = fromTwosCompliment(data);

            ByteBuffer buffer1 = ByteBuffer.wrap(twosComplemented);
            int val = buffer1.getInt();
            val =  val+1;

            ByteBuffer buffer2 = ByteBuffer.allocate(4);
            buffer2.putInt(val);
            processedBuffer = buffer2.array();
        }
        else {
            buffer.putInt(value);
            processedBuffer = buffer.array();
        }
        int i = 3;
        int j = byteSize  -1;
        for(; i >=0 && j >=0; i--,j--){
            byte bIn = processedBuffer[i];
            output[j] = bIn;
        }
        return output;
    }

    public byte[] signedLongToBytes(long value, int byteSize) {

        byte[] output = new byte[byteSize];


        byte[] processedBuffer = null;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        if(value < 0){
            long positiveValue = value * -1;

            buffer.putLong(positiveValue);
            byte[] data = buffer.array();
            byte[] twosComplemented = fromTwosCompliment(data);

            ByteBuffer buffer1 = ByteBuffer.wrap(twosComplemented);
            long val = buffer1.getLong();
            val =  val+1;

            ByteBuffer buffer2 = ByteBuffer.allocate(8);
            buffer2.putLong(val);
            processedBuffer = buffer2.array();
        }
        else {
            buffer.putLong(value);
            processedBuffer = buffer.array();
        }
        int i = 7;
        int j = byteSize  -1;
        for(; i >=0 && j >=0; i--,j--){
            byte bIn = processedBuffer[i];
            output[j] = bIn;
        }
        return output;
    }


    public long signedBytesToLong(byte[] bytes){
        long val = 0;
        byte mostSignificant = bytes[0];

        //big endian implementation
        if(mostSignificant < (byte) 0) {
            byte[] twosComplimentReversed = fromTwosCompliment(bytes);
            val = unsignedBytesToLong(twosComplimentReversed);
            val = val + 1;
            val = val * -1;
        }
        else
        {
            val = unsignedBytesToLong(bytes);
        }
        return val;
    }

    public byte[] fromTwosCompliment(byte[] bytes){

        byte[] converted = new byte[bytes.length];
        //get compliment of all
        for(int i = 0 ;i < bytes.length; i++){
            byte input = bytes[i];
            byte output = (byte) ~input;
            converted[i] = output;
        }
        //add one
        byte leastSignificant = converted[converted.length -1];
        //leastSignificant += 1;
        converted[converted.length -1] = leastSignificant;

        return converted;
    }
}
