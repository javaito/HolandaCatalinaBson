package org.hcjf.bson;

/**
 * Bson element that store a primitive value.
 * @author javaito
 */
public class BsonPrimitive extends BsonElement<Object> {

    private final BsonType type;

    /**
     * Defautl constructor.
     * @param name Name of the element.
     * @param value Value of the element.
     */
    public BsonPrimitive(String name, Object value) {
        super(name, value);
        this.type = BsonType.fromValue(value);
    }

    /**
     * Return the value of the element.
     * @return Value of the element.
     */
    public final Object get() {
        return getValue();
    }

    /**
     * Return the bson type that represent the implementation of the stored value.
     * @return Bson type.
     */
    public BsonType getType() {
        return type;
    }
}
