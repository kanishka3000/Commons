/*
 * Copyright 2006 (C) Kanishka Weerasekara
 *
 * Created on : 18/11/2018
 * Author     : Kanishka Weerasekara
 *

 */
package commons.util;

import java.nio.ByteBuffer;

public class VLong {
    private long value;
    private boolean signed;

    private Conversions conversions = new Conversions();
    public VLong(byte[] bytes, boolean signed){

        if(bytes.length > 8){
            throw new IllegalArgumentException(String.format(
                    "Byte count should be between 0 and 8 but was ",
                    bytes.length));
        }
        
        this.signed = signed;
        if(signed == true){
            value = signedBytesToLong(bytes);
        }else{
            value = unsignedBytesToLong(bytes);
        }
    }

    public VLong(long longValue, boolean signed){
        this.value = longValue;
        this.signed = signed;
    }

    public long getLongValue(){
        return value;
    }

    public boolean isSigned(){
        return  signed;
    }

    public byte[] getBytes(int byteCount){
        byte[] bytes = null;

        if(signed == true){
            bytes = signedLongToBytes(byteCount);
        }else{
            bytes = unsignedLongToBytes(byteCount);
        }

        return  bytes;
    }

    private long unsignedBytesToLong(byte[] bytes){
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

    private long signedBytesToLong(byte[] bytes){

        long val = 0;
        byte mostSignificant = bytes[0];

        //big endian implementation
        if(mostSignificant < (byte) 0) {
            byte[] twosComplimentReversed = conversions.fromTwosCompliment(bytes);
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

    private byte[] signedLongToBytes(int byteSize) {

        byte[] output = new byte[byteSize];


        byte[] processedBuffer = null;
        ByteBuffer buffer = ByteBuffer.allocate(8);
        if(value < 0){
            long positiveValue = value * -1;

            buffer.putLong(positiveValue);
            byte[] data = buffer.array();
            byte[] twosComplemented = conversions.fromTwosCompliment(data);

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

    private byte[] unsignedLongToBytes(int byteCount){
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

}
