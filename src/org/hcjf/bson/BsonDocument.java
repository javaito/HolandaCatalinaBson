package org.hcjf.bson;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonDocument extends BsonCollection {

    public BsonDocument() {
        //4 bytes to length and 1 byte for the separator 0x00
        this(null, DEFAULT_COLLECTION_SIZE);
    }

    public BsonDocument(Map<String, Object> values) {
        this(null, values);
    }

    public BsonDocument(String name) {
        this(name, DEFAULT_COLLECTION_SIZE);
    }

    public BsonDocument(String name, Map<String, Object> values) {
        this(name, DEFAULT_COLLECTION_SIZE);

        if(values == null) {
            throw new IllegalArgumentException("");
        }

        Object value;
        for(String key : values.keySet()) {
            value = values.get(key);
            if(value != null) {
                put(key, value);
            }
        }
    }

    public BsonDocument(String name, Integer length) {
        super(name, length);
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public final Iterator<String> iterator() {
        return getValue().keySet().iterator();
    }

    /**
     *
     * @param element
     */
    public final void put(BsonElement element) {
        putElement(element);
    }

    /**
     *
     * @param elementName
     * @param value
     */
    public final void put(String elementName, Object value) {
        if(value instanceof Map) {
            put(new BsonDocument(elementName, (Map<String,Object>)value));
        } else if(value instanceof List) {
            put(new BsonArray(elementName, (List<Object>)value));
        } else if(value instanceof Set) {
            put(new BsonArray(elementName, (Set<Object>)value));
        } else {
            put(new BsonPrimitive(elementName, value));
        }
    }
}
