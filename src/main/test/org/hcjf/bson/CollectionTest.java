package org.hcjf.bson;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

}
