# Supporting Non Standard Length Integral Values

For very specific applications there may be a requirement to support integral values that differ from the standard byte counts. For example integrals with 3 bytes or 5 bytes. The use case becomes complex when the requirement is to support signed integers with non standard byte counts as signed byte is not in a location expected.

Examples

From byte array

```
byte[] data2 = {
        (byte)0xFF,
        (byte)0xFF,
        (byte)0xFE};
//second parameter says this is signed (signed == true)
int value = new VInteger(data2, true).getIntValue();
Assert.assertEquals(-2, value);
```

To Bytes

```
int max3Bytes = 16777215;
byte[] data = new VInteger(max3Bytes, false).getBytes( 3);

Assert.assertEquals(3, data.length);
Assert.assertEquals(-1, data[0]);//255Assert.assertEquals(-1, data[1]);//255
Assert.assertEquals(-1, data[2]);//255
```