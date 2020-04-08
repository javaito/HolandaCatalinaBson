package org.hcjf.bson;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * @author javaito
 */
public class CollectionTest {

    @Test
    public void documentTest() {
        String uuidFieldName = "UuidField";
        UUID uuidValue = UUID.randomUUID();
        String stringFieldName = "StringField";
        String stringValue = "Hello world!!";

        BsonDocument document = new BsonDocument();
        document.put(uuidFieldName, uuidValue);
        document.put(stringFieldName, stringValue);

        Map<String,Object> map = document.toMap();

        Assert.assertTrue(map.containsKey(uuidFieldName));
        Assert.assertTrue(map.containsKey(stringFieldName));

        Assert.assertEquals(map.get(uuidFieldName), document.get(uuidFieldName).getAsUUID());
        Assert.assertEquals(map.get(stringFieldName), document.get(stringFieldName).getAsString());

        BsonDocument fromMapDocument = new BsonDocument(map);
        Assert.assertTrue(fromMapDocument.hasElement(uuidFieldName));
        Assert.assertTrue(fromMapDocument.hasElement(stringFieldName));

        String insideDocumentName = "InsideDocument";
        String dateFieldName = "DateField";
        Date dateFieldValue = new Date();
        BsonDocument insideDocument = new BsonDocument(insideDocumentName);
        insideDocument.put(dateFieldName, dateFieldValue);
        fromMapDocument.put(insideDocument);

        map = fromMapDocument.toMap();
        Assert.assertTrue(map.containsKey(insideDocumentName));
        Map insideMap = (Map) map.get(insideDocumentName);
        Assert.assertTrue(insideMap.containsKey(dateFieldName));
        Assert.assertEquals(insideMap.get(dateFieldName), dateFieldValue);

        byte[] bson = BsonEncoder.encode(fromMapDocument);
        fromMapDocument = BsonDecoder.decode(bson);

        map = fromMapDocument.toMap();
        Assert.assertTrue(map.containsKey(insideDocumentName));
        insideMap = (Map) map.get(insideDocumentName);
        Assert.assertTrue(insideMap.containsKey(dateFieldName));
        Assert.assertEquals(insideMap.get(dateFieldName), dateFieldValue);
    }

    @Test
    public void arrayTest() {
        String bsonArrayName = "bsonArray";
        UUID uuidValue = UUID.randomUUID();
        String stringValue = "Hello World!";

        BsonArray bsonArray = new BsonArray(bsonArrayName);
        bsonArray.add(uuidValue);
        bsonArray.add(stringValue);

        List list = bsonArray.toList();
        Assert.assertEquals(bsonArray.size(), list.size());
    }

    @Test
    public void longArrayTest() {
        List<UUID> list1 = new ArrayList<>();
        BsonArray bsonArray = new BsonArray("array");
        for (int i = 0; i < 50; i++) {
            list1.add(UUID.randomUUID());
            bsonArray.add(list1.get(i));
        }

        List<Object> bsonList = bsonArray.toList();
        for (int i = 0; i < 50; i++) {
            Assert.assertEquals(list1.get(i), bsonList.get(i));
        }

        BsonDocument document = new BsonDocument();
        document.put(bsonArray);
        byte[] serializedDocument = BsonEncoder.encode(document);
        BsonDocument document1 = BsonDecoder.decode(serializedDocument);

        BsonArray bsonArray1 = document1.get("array").getAsArray();
        List<Object> bsonList1 = bsonArray1.toList();
        for (int i = 0; i < 50; i++) {
            Assert.assertEquals(list1.get(i), bsonList1.get(i));
        }

    }

}
