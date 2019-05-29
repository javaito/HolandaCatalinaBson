package org.hcjf.bson;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * Enum with all the supported type for the bson definition.
 * @author javaito
 */
public enum BsonType {

    END((byte)0x00),

    DOUBLE((byte)0x01),

    STRING((byte)0x02),

    DOCUMENT((byte)0x03),

    ARRAY((byte)0x04),

    BINARY((byte)0x05),

    BOOLEAN((byte)0x08),

    DATE((byte)0x09),

    NULL((byte)0x0A),

    REGEX((byte)0x0B),

    INTEGER((byte)0x10),

    TIMESTAMP((byte)0x11),

    LONG((byte)0x12);

    private final byte id;

    private BsonType(byte id) {
        this.id = id;
    }

    /**
     * Return the id of the bson tipe.
     * @return Id the of the bson type.
     */
    public byte getId() {
        return id;
    }

    /**
     * Return a bson type by id.
     * @param id Id of the bson type.
     * @return Bson type.
     */
    public static BsonType fromId(byte id) {
        BsonType result = null;

        for(BsonType type : BsonType.values()) {
            if(type.getId() == id) {
                result = type;
                break;
            }
        }

        return result;
    }

    /**
     * Return the bson type that match with the java class of the object.
     * @param value Object to match.
     * @return Return the bson type that match of null if none bson type match
     * with the java class of the object.
     */
    public static BsonType fromValue(Object value) {
        BsonType type = null;

        if(value == null) {
            type = NULL;
        }else {
            Class clazz = value.getClass();
            if (Double.class.isAssignableFrom(clazz) || Float.class.isAssignableFrom(clazz)) {
                type = DOUBLE;
            } else if (String.class.isAssignableFrom(clazz)) {
                type = STRING;
            } else if (ByteBuffer.class.isAssignableFrom(clazz)) {
                type = BINARY;
            } else if (Boolean.class.isAssignableFrom(clazz)) {
                type = BOOLEAN;
            } else if (Date.class.isAssignableFrom(clazz)) {
                type = DATE;
            } else if (Integer.class.isAssignableFrom(clazz) || Short.class.isAssignableFrom(clazz) || Byte.class.isAssignableFrom(clazz)) {
                type = INTEGER;
            } else if (Long.class.isAssignableFrom(clazz)) {
                type = LONG;
            } else if (ByteArrayOutputStream.class.isAssignableFrom(clazz)) {
                type = BINARY;
            } else if (byte[].class.isAssignableFrom(clazz)) {
                type = BINARY;
            } else if (UUID.class.isAssignableFrom(clazz)) {
                type = BINARY;
            } else if (BsonDocument.class.isAssignableFrom(clazz)) {
                type = DOCUMENT;
            } else if (BsonArray.class.isAssignableFrom(clazz)) {
                type = ARRAY;
            } else if (Number.class.isAssignableFrom(clazz)) {
                type = DOUBLE;
            }
        }

        return type;
    }
}
