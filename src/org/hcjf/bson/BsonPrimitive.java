package org.hcjf.bson;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonPrimitive extends BsonElement<Object> {

    public BsonPrimitive(String name, Object value) {
        super(name, value);
    }

    /**
     *
     * @return
     */
    public final Object get() {
        return getValue();
    }
}
