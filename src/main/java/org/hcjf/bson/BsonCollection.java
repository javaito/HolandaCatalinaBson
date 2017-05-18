package org.hcjf.bson;

import java.nio.ByteBuffer;
import java.util.*;

/**
 * Collection of the bson elements.
 * @author javaito
 */
public abstract class BsonCollection extends BsonElement<Map<String, BsonElement>>
        implements Iterable<String> {

    protected static final Integer DEFAULT_COLLECTION_SIZE = 5;

    private Integer length;

    /**
     * Default constructor.
     */
    public BsonCollection() {
        this(null, DEFAULT_COLLECTION_SIZE);
    }

    /**
     * Constroctor by name.
     * @param name Name of the collection.
     */
    public BsonCollection(String name) {
        this(name, DEFAULT_COLLECTION_SIZE);
    }

    /**
     * Collection by length.
     * @param length Length of the collection.
     */
    public BsonCollection(Integer length) {
        this(null, length);
    }

    /**
     * Constructor by name and length.
     * @param name Name of the collection.
     * @param length Length of the collection.
     */
    public BsonCollection(String name, Integer length) {
        super(name, new TreeMap<>());
        this.length = length;
    }

    /**
     * Return thr length of the collection.
     * @return Length of the collection.
     */
    public final Integer getLength() {
        return length;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return getValue().keySet().iterator();
    }

    protected void putElement(BsonElement element) {
        getValue().put(element.getName(), element);
        Object value = element.getValue();

        length += 1;//Add the length of the type's byte
        length += element.getName().length();
        length += 1;//Add the length of the end byte (0x00)
        if(value != null) {
            Class clazz = value.getClass();
            if (Double.class.isAssignableFrom(clazz)) {
                length += 8;
            } else if (String.class.isAssignableFrom(clazz)) {
                length += 4;//Bytes reserved for the length of string
                length += ((String) value).getBytes().length;
                length += 1;//Add the length of the end byte (0x00)
            } else if (ByteBuffer.class.isAssignableFrom(clazz)) {
                length += 4;//Bytes reserved for the length of buffer
                length += 1;//Add the length of the subtype's byte
                length += ((ByteBuffer) value).limit();
            } else if (byte[].class.isAssignableFrom(clazz)) {
                length += 4;//Bytes reserved for the length of buffer
                length += 1;//Add the length of the subtype's byte
                length += ((byte[]) value).length;
            } else if (Boolean.class.isAssignableFrom(clazz)) {
                length += 1;
            } else if (Date.class.isAssignableFrom(clazz)) {
                length += 8;
            } else if (Integer.class.isAssignableFrom(clazz)) {
                length += 4;
            } else if (Long.class.isAssignableFrom(clazz)) {
                length += 8;
            } else if (UUID.class.isAssignableFrom(clazz)) {
                length += 4;//Bytes reserved for the length of buffer
                length += 1;//Add the length of the subtype's byte
                length += 16;
            } else if (BsonCollection.class.isAssignableFrom(element.getClass())) {
                length += ((BsonCollection) element).getLength();
            }
        }

    }

    /**
     * Return the bson element associated to the name.
     * @param name Name of the bson element.
     * @return Return the element.
     */
    public final BsonElement get(String name) {
        return getValue().get(name);
    }

    /**
     * Return the size of the bson collection.
     * @return Size of the collection.
     */
    public final int size() {
        return getValue().size();
    }
}
