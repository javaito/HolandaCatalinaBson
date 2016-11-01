package org.hcjf.bson;

import java.util.Date;
import java.util.UUID;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public abstract class BsonElement<O extends Object> {

    private String name;
    private final O value;

    public BsonElement(String name, O value) {
        this.name = name;
        this.value = value;
    }

    public BsonElement(O value) {
        this.name = null;
        this.value = value;
    }

    /**
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     */
    public final String getName() {
        return name;
    }

    /**
     *
     * @return
     */
    protected final O getValue() {
        return value;
    }

    /**
     *
     * @return
     */
    public final boolean isDocument() {
        return this instanceof BsonDocument;
    }

    /**
     *
     * @return
     */
    public final boolean isArray() {
        return this instanceof BsonArray;
    }

    /**
     *
     * @return
     */
    public final boolean isPrimitive() {
        return this instanceof BsonPrimitive;
    }

    public final String getAsString() {
        return (String) value;
    }

    public final BsonDocument getAsDocument() {
        return (BsonDocument) value;
    }

    public final BsonArray getAsArray() {
        return (BsonArray) value;
    }

    public final Integer getAsInteger() {
        return (Integer) value;
    }

    public final Double getAsDouble() {
        return (Double) value;
    }

    public final Long getAsLong() {
        return (Long) value;
    }

    public final Date getAsDate() {
        return new Date((Long)value);
    }

    public final byte[] getAsBytes() {
        return (byte[]) value;
    }

    public final UUID getAsUUID() {
        return (UUID) value;
    }
}
