package org.hcjf.bson;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public enum BsonBinarySubType {

    GENERIC((byte)0x00),

    UUID((byte)0x03);

    private final byte id;

    private BsonBinarySubType(byte id) {
        this.id = id;
    }

    /**
     *
     * @return
     */
    public byte getId() {
        return id;
    }

    /**
     *
     * @param id
     * @return
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
