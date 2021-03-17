package org.hcjf.bson;

import java.util.*;

/**
 * Bson array implementation.
 * @author javaito
 */
public class BsonArray extends BsonCollection {

    private static final String JSON_ARRAY_START = "[";
    private static final String JSON_ARRAY_END = "]";

    public BsonArray(String name) {
        this(name, DEFAULT_COLLECTION_SIZE);
    }

    public BsonArray(String name, Collection<? extends  Object> values) {
        this(name, DEFAULT_COLLECTION_SIZE);
        for(Object value : values) {
            if(value != null) {
                add(value);
            }
        }
    }

    public BsonArray(String name, Integer length) {
        super(name, length);
    }

    /**
     * Return the element of the position.
     * @param index Position index.
     * @return Element founded or null.
     */
    public final BsonElement get(Integer index) {
        return getValue().get(index.toString());
    }

    /**
     * Add the element to the array.
     * @param element Element to add.
     * @throws IllegalArgumentException if the element is null.
     */
    public final void add(BsonElement element) {
        if(element == null) {
            throw new IllegalArgumentException("Bson array not support null elements.");
        }

        Integer currentSize = getValue().size();
        element.setName(currentSize.toString());
        putElement(element);
    }

    /**
     * Add a new value to the array.
     * @param value Array's value.
     */
    public final void add(Object value) {
        String name = Integer.toString(getValue().size());
        if(value instanceof Map) {
            add(new BsonDocument(name, (Map<String,Object>)value));
        } else if(value instanceof List) {
            add(new BsonArray(name, (List<Object>)value));
        } else if(value instanceof Set) {
            add(new BsonArray(name, (Set<Object>) value));
        } else if(value instanceof BsonElement) {
            ((BsonElement)value).setName(name);
            add(((BsonElement)value));
        } else {
            add(new BsonPrimitive(name, value));
        }
    }

    /**
     * Return json representation
     * @return json string representation
     */
    public String toJsonString() {
        StringBuilder r = new StringBuilder();
        r.append(JSON_ARRAY_START);
        for(String fieldName : this) {
            try {
                if(r.length()>1) {
                    r.append(JSON_ELEMENT_SEPARATOR);
                }
                r.append(get(fieldName).toJsonString());
            } catch (Exception ex) {}
        }
        r.append(JSON_ARRAY_END);
        return r.toString();
    }

    /**
     * Returns all the values into the bson array as list.
     * @return Bson document as map.
     */
    public List<Object> toList() {
        List<Object> result = new ArrayList<>();
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
            result.add(object);
        }
        return result;
    }

    /**
     * Override the iterator method in order to sort the keys as integer index.
     * @return Iterator instance.
     */
    @Override
    public Iterator<String> iterator() {
        TreeSet<String> sortedSet = new TreeSet<>(Comparator.comparingInt(Integer::parseInt));
        sortedSet.addAll(getValue().keySet());
        return sortedSet.iterator();
    }
}
