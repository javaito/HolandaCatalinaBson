package org.hcjf.bson;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public final class BsonDecoder {

    /**
     *
     * @param documentBytes
     * @return
     */
    public static BsonDocument decode(byte[] documentBytes) {
        return decodeDocument(null, ByteBuffer.wrap(documentBytes));
    }

    /**
     *
     * @param name
     * @param buffer
     * @return
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
     *
     * @param name
     * @param buffer
     * @return
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
     *
     * @param name
     * @param type
     * @param buffer
     * @return
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
     *
     * @param buffer
     * @return
     */
    private static Matcher decodeRegex(ByteBuffer buffer) {
        throw new UnsupportedOperationException();
    }

    /**
     *
     * @param buffer
     * @return
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
                byte[] byteArray = new byte[16]; //UUID length
                buffer.get(byteArray);
                result = UUID.nameUUIDFromBytes(byteArray);
                break;
            }
        }
        return result;
    }

    /**
     *
     * @param buffer
     * @return
     */
    private static String decodeString(ByteBuffer buffer) {
        int size = buffer.getInt();
        byte[] result = new byte[size-1];
        buffer.get(result);
        buffer.get(); //Read the end byte (0x00)
        return new String(result);
    }

    /**
     *
     * @param buffer
     * @return
     */
    private static String decodeName(ByteBuffer buffer) {
        ByteBuffer nameBuffer = ByteBuffer.allocate(128);
        byte b = buffer.get();
        while(b != BsonType.END.getId()) {
            nameBuffer.put(b);
            if(nameBuffer.limit() == nameBuffer.position()) {
                nameBuffer.limit(nameBuffer.limit() + 128);
            }
        }
        nameBuffer.flip();
        return new String(nameBuffer.array());
    }
}
