package org.hcjf.bson;

/**
 * Bson element that store a primitive value.
 * @author javaito
 */
public class BsonPrimitive extends BsonElement<Object> {

    private final BsonType type;
    private final BsonBinarySubType binarySubType;

    /**
     * Defautl constructor.
     * @param name Name of the element.
     * @param value Value of the element.
     */
    public BsonPrimitive(String name, Object value) {
        super(name, value);
        this.type = BsonType.fromValue(value);
        if(type == null || type.equals(BsonType.ARRAY) || type.equals(BsonType.DOCUMENT)) {
            throw new IllegalArgumentException("Unable to create a primitive element with value: " + value);
        }

        if(type.equals(BsonType.BINARY)) {
            binarySubType = BsonBinarySubType.fromValue(value);
        } else {
            binarySubType = null;
        }
    }

    /**
     * Returns the value of the element.
     * @return Value of the element.
     */
    public final Object get() {
        return getValue();
    }

    /**
     * Returns the bson type that represent the implementation of the stored value.
     * @return Bson type.
     */
    public BsonType getType() {
        return type;
    }

    /**
     * Returns the sub type of the binary type.
     * @return Binary sub type.
     */
    public BsonBinarySubType getBinarySubType() {
        return binarySubType;
    }
}
