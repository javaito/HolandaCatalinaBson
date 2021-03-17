package org.hcjf.bson;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DecodeEncodeTest {

    @Test
    public void testEncodeDecode() {
        Map<String,Object> map1 = new HashMap<>();
        map1.put("name", "map1");
        Map<String,Object> map2 = new HashMap<>();
        map2.put("name", "map2");
        map1.put("map2", map2);
        Map<String,Object> innerMap = new HashMap<>();
        innerMap.put("name", "innerMap");
        innerMap.put("value", 1);

        Collection<Map<String,Object>> innerMaps = new ArrayList<>();
        innerMaps.add(innerMap);
        map2.put("innerMaps", innerMaps);

        byte[] array = BsonEncoder.encode(new BsonDocument(map1));
        BsonDocument bsonDocument = BsonDecoder.decode(array);

        Map<String,Object> result = bsonDocument.toMap();
        Map<String,Object> result2 = (Map<String, Object>) result.get("map2");
        Collection<Map<String,Object>> resultInnerMaps = (Collection<Map<String, Object>>) result2.get("innerMaps");
        Map<String,Object> resultInnerMap = resultInnerMaps.stream().findFirst().get();

        String name = (String) resultInnerMap.get("name");
        Integer value = (Integer) resultInnerMap.get("value");

        Assert.assertEquals(name, "innerMap");
        Assert.assertEquals(value, Integer.valueOf(1));
    }

}
