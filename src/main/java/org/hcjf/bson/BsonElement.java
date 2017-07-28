package org.hcjf.bson;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

/**
 * Bson element implementation.
 * @author javaito
 */
public abstract class BsonElement<O extends Object> {

    private String name;
    private final O value;

    /**
     * Constructor by name and value.
     * @param name Name of the element.
     * @param value Value of the element.
     */
    public BsonElement(String name, O value) {

        if(BsonType.fromValue(value) == null && !Map.class.isAssignableFrom(value.getClass())) {
            throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }

        this.name = name;
        this.value = value;
    }

    /**
     * Constructor by value.
     * @param value Value of the
     */
    public BsonElement(O value) {
        this.name = null;
        this.value = value;
    }

    /**
     * Return thr name of the element.
     * @param name Name of the element.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the name of the element.
     * @return Name of the element.
     */
    public final String getName() {
        return name;
    }

    /**
     * Return the value of the element.
     * @return Value of the element.
     */
    public final O getValue() {
        return value;
    }

    /**
     * Return the boolean value to indicate if the element is a document or not.
     * @return True if the element implementation is a {@link BsonDocument} instance or
     * false in the otherwise.
     */
    public final boolean isDocument() {
        return this instanceof BsonDocument;
    }

    /**
     * Return the boolean value to indicate if the element is an array or not.
     * @return True if the element implementation is a {@link BsonArray} instance or
     * false in the otherwise.
     */
    public final boolean isArray() {
        return this instanceof BsonArray;
    }

    /**
     * Return the boolean value to indicate if the element is a primitive value or not.
     * @return True if the element implementation is a {@link BsonPrimitive} instance or
     * false in the otherwise.
     */
    public final boolean isPrimitive() {
        return this instanceof BsonPrimitive;
    }

    /**
     * Return the element value as a string.
     * @return String value.
     */
    public final String getAsString() {
        return (String) value;
    }

    /**
     * Return the element value as a bson document.
     * @return Bson document value.
     */
    public final BsonDocument getAsDocument() {
        return (BsonDocument) this;
    }

    /**
     * Return the element value as a bson array.
     * @return Bson array value.
     */
    public final BsonArray getAsArray() {
        return (BsonArray) this;
    }

    /**
     * Return the element value as a integer.
     * @return Integer value.
     */
    public final Integer getAsInteger() {
        return (Integer) value;
    }

    /**
     * Return the element value as a double.
     * @return Double value.
     */
    public final Double getAsDouble() {
        return (Double) value;
    }

    /**
     * Return the element value as a long.
     * @return Long value.
     */
    public final Long getAsLong() {
        return (Long) value;
    }

    /**
     * Return the element value as a date.
     * @return Date value.
     */
    public final Date getAsDate() {
        Date result;
        if(value instanceof Date) {
            result = (Date) value;
        } else {
            result = new Date((Long) value);
        }
        return result;
    }

    /**
     * Return the element value as a byte array.
     * @return Byte array value.
     */
    public final byte[] getAsBytes() {
        byte[] result;
        if(value instanceof ByteBuffer) {
            result = ((ByteBuffer)value).array();
        } else {
            result = (byte[]) value;
        }
        return result;
    }

    /**
     * Return the element value as a UUID.
     * @return UUID value.
     */
    public final UUID getAsUUID() {
        return (UUID) value;
    }

    /**
     * Return the element value as a boolean.
     * @return Boolean value.
     */
    public final Boolean getAsBoolean() {
        return (Boolean) value;
    }
}
