package org.hcjf.bson;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonPrimitive extends BsonElement<Object> {

    private final BsonType type;

    public BsonPrimitive(String name, Object value) {
        super(name, value);
        this.type = BsonType.fromValue(value);
    }

    /**
     *
     * @return
     */
    public final Object get() {
        return getValue();
    }

    public BsonType getType() {
        return type;
    }
}
