/*
 * Copyright 2006 (C) Kanishka Weerasekara
 *
 * Created on : 18/11/2018
 * Author     : Kanishka Weerasekara
 *

 */
package commons.util;

import java.nio.ByteBuffer;

public class VInteger {

    private int value;
    private boolean signed;
    private Conversions conversions = new Conversions();

    public VInteger(byte[] bytes, boolean signed){

        if(bytes.length > 4 || bytes.length == 0){
            throw new IllegalArgumentException(String.format(
                        "Number of bytes should be between 1 and 4, but was %d",
                        bytes.length));
        }

        this.signed = signed;
        if(signed == true){
            value = signedBytesToInt(bytes);
        }else{
            value = unsignedBytesToInt(bytes);
        }
    }

    public VInteger(int intValue, boolean signed){
        this.value = intValue;
        this.signed = signed;
    }

    public int getIntValue(){
        return value;
    }

    public boolean isSigned(){
        return signed;
    }

    public byte[] getBytes(int byteCount){

        byte[] bytes = null;
        if(signed == true){
            bytes = signedIntToBytes(byteCount);
        }else{
            bytes = unsignedIntToBytes(byteCount);
        }

        return bytes;
    }

    private byte[] unsignedIntToBytes(int byteCount){

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

    private byte[] signedIntToBytes(int byteSize){
        byte[] output = new byte[byteSize];

        byte[] processedBuffer = null;
        ByteBuffer buffer = ByteBuffer.allocate(4);
        if(value < 0){
            int positiveValue = value * -1;

            buffer.putInt(positiveValue);
            byte[] data = buffer.array();
            byte[] twosComplemented = conversions.fromTwosCompliment(data);

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

    private int unsignedBytesToInt(byte[] bytes){
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

    private int signedBytesToInt(byte[] bytes){
        int val = 0;
        byte mostSignificant = bytes[0];

        //big endian implementation
        if(mostSignificant < (byte) 0) {
            byte[] twosComplimentReversed = conversions.fromTwosCompliment(bytes);
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

}
