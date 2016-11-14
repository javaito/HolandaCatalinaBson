package org.hcjf.bson;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public class BsonArray extends BsonCollection {

    public BsonArray(String name) {
        this(name, 5);
    }

    public BsonArray(String name, List<? extends  Object> values) {
        this(name, 5);
        for(Object value : values) {
            add(value);
        }
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

    /**
     *
     * @param value
     */
    public final void add(Object value) {
        String name = Integer.toString(getValue().size());
        if(value instanceof Map) {
            add(new BsonDocument(name, (Map<String,Object>)value));
        } else if(value instanceof List) {
            add(new BsonArray(name, (List<Object>)value));
        } else {
            add(new BsonPrimitive(name, value));
        }
    }
}
