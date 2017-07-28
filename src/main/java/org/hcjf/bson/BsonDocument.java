package org.hcjf.bson;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Bson document implementation.
 * @author javaito
 */
public class BsonDocument extends BsonCollection {

    /**
     * Default constructor.
     */
    public BsonDocument() {
        //4 bytes to length and 1 byte for the separator 0x00
        this(null, DEFAULT_COLLECTION_SIZE);
    }

    /**
     * Constructor by map.
     * @param values Map with the document fields and values.
     */
    public BsonDocument(Map<String, Object> values) {
        this(null, values);
    }

    /**
     * Constructor by name.
     * @param name Name of the document.
     */
    public BsonDocument(String name) {
        this(name, DEFAULT_COLLECTION_SIZE);
    }

    /**
     * Constructor by name and map.
     * @param name Name of the document.
     * @param values Map with the document fields and values.
     */
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

    /**
     * Constructor by name and length.
     * @param name Name of the document.
     * @param length Length of the document.
     */
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
     * Add an element into the document.
     * @param element Bson element.
     */
    public final void put(BsonElement element) {
        putElement(element);
    }

    /**
     * Add an element into the document.
     * @param elementName Element name.
     * @param value Element value.
     */
    public final void put(String elementName, Object value) {
        if(value instanceof Map) {
            put(new BsonDocument(elementName, (Map<String,Object>)value));
        } else if(value instanceof List) {
            put(new BsonArray(elementName, (List<Object>)value));
        } else if(value instanceof Set) {
            put(new BsonArray(elementName, (Set<Object>) value));
        } else if(value instanceof BsonElement) {
            ((BsonElement)value).setName(elementName);
            put(((BsonElement)value));
        } else {
            put(new BsonPrimitive(elementName, value));
        }
    }
}
