package org.hcjf.bson;

import java.nio.ByteBuffer;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public abstract class BsonCollection extends BsonElement<Map<String, BsonElement>>
        implements Iterable<String> {

    private Integer length;

    public BsonCollection() {
        this(null, 0);
    }

    public BsonCollection(String name) {
        this(name, 0);
    }

    public BsonCollection(Integer length) {
        this(null, length);
    }

    public BsonCollection(String name, Integer length) {
        super(name, new TreeMap<>());
        this.length = length;
    }

    public final Integer getLength() {
        return length;
    }

    /**
     * Returns an iterator over elements of type {@code T}.
     *
     * @return an Iterator.
     */
    @Override
    public Iterator<String> iterator() {
        return getValue().keySet().iterator();
    }

    protected void putElement(BsonElement element) {
        getValue().put(element.getName(), element);
        Object value = element.getValue();
        Class clazz = value.getClass();
        length += 1;//Add the length of the type's byte
        length += element.getName().length();
        length += 1;//Add the length of the end byte (0x00)
        if(Double.class.isAssignableFrom(clazz)) {
            length += 8;
        } else if(String.class.isAssignableFrom(clazz)) {
            length += 4;//Bytes reserved for the length of string
            length += ((String)value).length();
            length += 1;//Add the length of the end byte (0x00)
        } else if(ByteBuffer.class.isAssignableFrom(clazz)) {
            length += 4;//Bytes reserved for the length of buffer
            length += 1;//Add the length of the subtype's byte
            length += ((ByteBuffer)value).array().length;
        } else if(Boolean.class.isAssignableFrom(clazz)) {
            length += 1;
        } else if(Date.class.isAssignableFrom(clazz)) {
            length += 8;
        } else if(Integer.class.isAssignableFrom(clazz)) {
            length += 4;
        } else if(Long.class.isAssignableFrom(clazz)) {
            length += 8;
        } else if(BsonCollection.class.isAssignableFrom(element.getClass())) {
            length += ((BsonCollection)element).getLength();
        }

    }

    /**
     *
     * @param name
     * @return
     */
    public final BsonElement get(String name) {
        return getValue().get(name);
    }
}
