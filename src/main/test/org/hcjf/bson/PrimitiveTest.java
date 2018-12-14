package org.hcjf.bson;

import org.junit.Assert;
import org.junit.Test;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * Contains some test cases using primitive values into a bson document.
 * @author javaito
 */
public class PrimitiveTest {

    private static final String STRING_FIELD = "stringField";
    private static final String INTEGER_FIELD = "integerField";
    private static final String SHORT_FIELD = "shortField";
    private static final String BYTE_FIELD = "byteField";
    private static final String LONG_FIELD = "longField";
    private static final String UUID_FIELD = "uuidField";
    private static final String BYTE_ARRAY_FIELD = "byteArrayField";
    private static final String BYTE_BUFFER_FIELD = "byteBufferField";
    private static final String DATE_FIELD = "dateField";

    @Test
    public void stringTest() {
        BsonDocument document = new BsonDocument();
        document.put(STRING_FIELD, "Hello world!");
        byte[] bosn = BsonEncoder.encode(document);
        BsonDocument decodedDocument = BsonDecoder.decode(bosn);
        Assert.assertEquals(document.get(STRING_FIELD).getAsString(), decodedDocument.get(STRING_FIELD).getAsString());
        Assert.assertTrue(decodedDocument.get(STRING_FIELD).getValue() instanceof String);
    }

    @Test
    public void integerTest() {
        BsonDocument document = new BsonDocument();
        document.put(BYTE_FIELD, (byte)3);
        document.put(SHORT_FIELD, (short)2);
        document.put(INTEGER_FIELD, 4);
        document.put(LONG_FIELD, Long.MAX_VALUE);
        byte[] bosn = BsonEncoder.encode(document);
        BsonDocument decodedDocument = BsonDecoder.decode(bosn);
        Assert.assertEquals(document.get(INTEGER_FIELD).getAsInteger(), decodedDocument.get(INTEGER_FIELD).getAsInteger());
        Assert.assertEquals(decodedDocument.get(INTEGER_FIELD).getAsInteger().intValue(), 4);
        Assert.assertEquals(document.get(SHORT_FIELD).getAsShort(), decodedDocument.get(SHORT_FIELD).getAsShort());
        Assert.assertEquals(decodedDocument.get(SHORT_FIELD).getAsShort().shortValue(), (short) 2);
        Assert.assertEquals(document.get(BYTE_FIELD).getAsByte(), decodedDocument.get(BYTE_FIELD).getAsByte());
        Assert.assertEquals(decodedDocument.get(BYTE_FIELD).getAsByte().byteValue(), (byte) 3);
        Assert.assertEquals(document.get(LONG_FIELD).getAsLong(), decodedDocument.get(LONG_FIELD).getAsLong());
        Assert.assertEquals(decodedDocument.get(LONG_FIELD).getAsLong().longValue(), Long.MAX_VALUE);
    }

    @Test
    public void uuidTest() {
        UUID id = UUID.randomUUID();
        BsonDocument document = new BsonDocument();
        document.put(UUID_FIELD, id);
        byte[] bosn = BsonEncoder.encode(document);
        BsonDocument decodedDocument = BsonDecoder.decode(bosn);
        Assert.assertEquals(document.get(UUID_FIELD).getAsUUID(), decodedDocument.get(UUID_FIELD).getAsUUID());
        Assert.assertEquals(decodedDocument.get(UUID_FIELD).getAsUUID(), id);
    }

    @Test
    public void byteArrayTest() {
        byte[] byteArray = "Hello world!".getBytes();
        BsonDocument document = new BsonDocument();
        document.put(BYTE_ARRAY_FIELD, byteArray);
        document.put(BYTE_BUFFER_FIELD, ByteBuffer.wrap(byteArray));
        byte[] bosn = BsonEncoder.encode(document);
        BsonDocument decodedDocument = BsonDecoder.decode(bosn);
        Assert.assertArrayEquals(document.get(BYTE_ARRAY_FIELD).getAsBytes(), decodedDocument.get(BYTE_ARRAY_FIELD).getAsBytes());
        Assert.assertArrayEquals(document.get(BYTE_BUFFER_FIELD).getAsBytes(), decodedDocument.get(BYTE_BUFFER_FIELD).getAsBytes());
    }

    @Test
    public void dateTest() {
        Date date = new Date();
        BsonDocument document = new BsonDocument();
        document.put(DATE_FIELD, date);
        byte[] bosn = BsonEncoder.encode(document);
        BsonDocument decodedDocument = BsonDecoder.decode(bosn);
        Assert.assertEquals(document.get(DATE_FIELD).getAsDate(), decodedDocument.get(DATE_FIELD).getAsDate());
    }
}
