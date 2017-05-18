package org.hcjf.bson;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * Enum all the sub types of the binary type.
 * @author javaito
 */
public enum BsonBinarySubType {

    GENERIC((byte)0x00),

    UUID((byte)0x03);

    private final byte id;

    /**
     * Constructor method.
     * @param id Sub type id.
     */
    private BsonBinarySubType(byte id) {
        this.id = id;
    }

    /**
     * Return the id of the sub type.
     * @return Id of the sub type.
     */
    public byte getId() {
        return id;
    }

    /**
     * Find a sub type from id.
     * @param id Id of the sub type.
     * @return Sub type instance or null if not exist.
     */
    public static BsonBinarySubType fromId(byte id) {
        BsonBinarySubType result = null;

        for(BsonBinarySubType type : BsonBinarySubType.values()) {
            if(type.getId() == id) {
                result = type;
                break;
            }
        }

        return result;
    }

    /**
     * Return the sub type instance that corresponds with the value class.
     * @param value Value.
     * @return Sub type instance.
     */
    public static BsonBinarySubType fromValue(Object value) {
        BsonBinarySubType subType = null;

        if(ByteBuffer.class.isAssignableFrom(value.getClass())) {
            subType = GENERIC;
        } else if(ByteArrayOutputStream.class.isAssignableFrom(value.getClass())) {
            subType = GENERIC;
        } else if(byte[].class.isAssignableFrom(value.getClass())) {
            subType = GENERIC;
        } else if(java.util.UUID.class.isAssignableFrom(value.getClass())) {
            subType = UUID;
        }

        return subType;
    }

}
