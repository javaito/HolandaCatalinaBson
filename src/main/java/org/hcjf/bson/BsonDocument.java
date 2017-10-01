package org.hcjf.bson;

import java.util.*;

/**
 * Bson document implementation.
 * @author javaito
 */
public class BsonDocument extends BsonCollection {

    private static final String JSON_DOCUMENT_START = "{";
    private static final String JSON_DOCUMENT_END = "}";

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

    /**
     * Verify if the document contains an element with the specific name.
     * @param elementName Element name.
     * @return Returns true if the element is contained into the
     * document and false in the otherwise.
     */
    public final boolean hasElement(String elementName) {
        return getValue().containsKey(elementName);
    }

    /**
     * Returns json representation
     * @return json string representation
     */
    public String toJsonString() {
        StringBuilder r = new StringBuilder();
        r.append(JSON_DOCUMENT_START);
        for(String fieldName : this) {
            try {
                if(r.length()>1) {
                    r.append(JSON_ELEMENT_SEPARATOR);
                }
                r.append(get(fieldName).toJsonString());
            } catch (Exception ex) {}
        }
        r.append(JSON_DOCUMENT_END);
        return r.toString();
    }

    /**
     * Returns all the values into the bson document as map.
     * @return Bson document as map.
     */
    public Map<String,Object> toMap() {
        Map<String,Object> result = new HashMap<>();
        Object object;
        for(String fieldName : this) {
            object = get(fieldName);
            if(object instanceof BsonDocument) {
                object = ((BsonDocument)object).toMap();
            } else if(object instanceof BsonArray) {
                object = ((BsonArray)object).toList();
            } else if(object instanceof BsonPrimitive) {
                object = ((BsonPrimitive)object).get();
            }
            result.put(fieldName, object);
        }
        return result;
    }
}