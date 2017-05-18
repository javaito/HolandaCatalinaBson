package org.hcjf.bson;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * This class implements the bson decoder.
 * @author javaito
 */
public final class BsonDecoder {

    /**
     * This method create a bson document from a byte array as imput.
     * @param documentBytes Input byte array.
     * @return Bson document decoded.
     */
    public static BsonDocument decode(byte[] documentBytes) {
        return decodeDocument(null, ByteBuffer.wrap(documentBytes));
    }

    /**
     * Recursive method to decode a bson document.
     * @param name Name of the bson document.
     * @param buffer Buffer with the byte representation fo the document.
     * @return Bson document decoded.
     */
    private static BsonDocument decodeDocument(String name, ByteBuffer buffer) {
        Integer documentSize = buffer.getInt();
        BsonDocument result = new BsonDocument(name, documentSize);

        BsonType type = BsonType.fromId(buffer.get());
        String elementName;
        while(type != BsonType.END) {
            elementName = decodeName(buffer);
            switch (type) {
                case ARRAY: {
                    result.put(decodeArray(elementName, buffer));
                    break;
                }
                case DOCUMENT: {
                    result.put(decodeDocument(elementName, buffer));
                    break;
                }
                default: {
                    result.put(decodePrimitive(elementName, type, buffer));
                }
            }
            type = BsonType.fromId(buffer.get());
        }

        return result;
    }

    /**
     * Recursive method to decode a bson array.
     * @param name Name of the bson array.
     * @param buffer Buffer with the byte representation fo the array.
     * @return Bson array decode.
     */
    private static BsonArray decodeArray(String name, ByteBuffer buffer) {
        Integer documentSize = buffer.getInt();
        BsonArray result = new BsonArray(name, documentSize);

        BsonType type = BsonType.fromId(buffer.get());
        String elementName; //In this case the name is ignored
        while(type != BsonType.END) {
            elementName = decodeName(buffer);
            switch (type) {
                case ARRAY: {
                    result.add(decodeArray(elementName, buffer));
                    break;
                }
                case DOCUMENT: {
                    result.add(decodeDocument(elementName, buffer));
                    break;
                }
                default: {
                    result.add(decodePrimitive(elementName, type, buffer));
                }
            }
            type = BsonType.fromId(buffer.get());
        }

        return result;
    }

    /**
     * This method create a decoded bson primitive.
     * @param name Name of the primitive.
     * @param type Type of the primitive.
     * @param buffer Buffer with the byte representation fo the primitive.
     * @return Decode bson primitive.
     */
    private static BsonPrimitive decodePrimitive(String name, BsonType type, ByteBuffer buffer) {
        BsonPrimitive result = null;
        switch (type) {
            case BINARY: result = new BsonPrimitive(name, decodeBinary(buffer)); break;
            case BOOLEAN: result = new BsonPrimitive(name, buffer.get() == (byte) 0x01); break;
            case DATE: result = new BsonPrimitive(name, new Date(buffer.getLong())); break;
            case DOUBLE: result = new BsonPrimitive(name, buffer.getDouble()); break;
            case INTEGER: result = new BsonPrimitive(name, buffer.getInt()); break;
            case LONG: result = new BsonPrimitive(name, buffer.getLong()); break;
            case NULL: result = new BsonPrimitive(name, null); break;
            case STRING: result = new BsonPrimitive(name, decodeString(buffer)); break;
            case TIMESTAMP: result = new BsonPrimitive(name, new Date(buffer.getLong())); break;
            case REGEX: result = new BsonPrimitive(name, decodeRegex(buffer)); break;
        }
        return result;
    }

    /**
     * Decode the body as a regex matcher instance.
     * @param buffer Body of the regex.
     * @return Regex matcher instance.
     */
    private static Matcher decodeRegex(ByteBuffer buffer) {
        throw new UnsupportedOperationException();
    }

    /**
     * Decode the different sub-types of the binary types.
     * @param buffer Buffer with the byte representation fo the binary object.
     * @return Decoded binary sub-type.
     */
    private static Object decodeBinary(ByteBuffer buffer) {
        Object result = null;
        Integer size = buffer.getInt();
        BsonBinarySubType subType = BsonBinarySubType.fromId(buffer.get());
        switch (subType) {
            case GENERIC: {
                byte[] byteArray = new byte[size];
                buffer.get(byteArray);
                result = ByteBuffer.wrap(byteArray);
                break;
            }
            case UUID: {
                result = new UUID(buffer.getLong(), buffer.getLong());
                break;
            }
        }
        return result;
    }

    /**
     * Decode string object from a buffer.
     * @param buffer Buffer with the byte representation of string object.
     * @return Decoded string object.
     */
    private static String decodeString(ByteBuffer buffer) {
        int size = buffer.getInt();
        byte[] result = new byte[size-1];
        buffer.get(result);
        buffer.get(); //Read the end byte (0x00)
        return new String(result);
    }

    /**
     * Decode name from the bson document.
     * @param buffer Buffer with the byte representation of the field name.
     * @return Decoded field name.
     */
    private static String decodeName(ByteBuffer buffer) {
        ByteBuffer nameBuffer = ByteBuffer.allocate(256);
        byte b = buffer.get();
        while(b != BsonType.END.getId()) {
            nameBuffer.put(b);
            if(nameBuffer.limit() == nameBuffer.position()) {
                break;
            }
            b = buffer.get();
        }
        nameBuffer.flip();
        byte[] result = new byte[nameBuffer.limit()];
        nameBuffer.get(result);
        return new String(result);
    }
}
