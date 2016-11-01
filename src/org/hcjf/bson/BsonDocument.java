package org.hcjf.bson;

import java.util.Iterator;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonDocument extends BsonCollection {

    public BsonDocument() {
        //4 bytes to length and 1 byte for the separator 0x00
        this(null, 5);
    }

    public BsonDocument(String name) {
        this(name, 5);
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
}
