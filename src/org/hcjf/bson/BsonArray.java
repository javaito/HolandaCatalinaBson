package org.hcjf.bson;

import java.util.Iterator;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonArray extends BsonCollection {

    public BsonArray(String name) {
        this(name, 5);
    }

    public BsonArray(String name, Integer length) {
        super(name, length);
    }

    /**
     *
     * @param index
     * @return
     */
    public final BsonElement get(Integer index) {
        return getValue().get(index.toString());
    }

    /**
     *
     * @param element
     */
    public final void add(BsonElement element) {
        Integer currentSize = getValue().size();
        element.setName(currentSize.toString());
        putElement(element);
    }
}
