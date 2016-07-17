package org.hcjf.bson;

import java.util.Iterator;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonDocument extends BsonCollection {

    public BsonDocument() {
        this(null, 0);
    }

    public BsonDocument(String name) {
        this(name, 0);
    }

    public BsonDocument(Integer length) {
        super(null, length);
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
     * @param name
     * @return
     */
    public final BsonElement get(String name) {
        return getValue().get(name);
    }

    /**
     *
     * @param element
     */
    public final void put(BsonElement element) {
        putElement(element);
    }
}
