package org.hcjf.bson;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.UUID;

/**
 * This class creates a instance of Bson document to a byte array
 * with the bson standard format.
 * @author javaito
 */
public final class BsonEncoder {

    /**
     * Creates a bson representation of the bson document.
     * @param document Bson document instance.
     * @return Bson representation.
     */
    public static byte[] encode(BsonCollection document) {
        ByteBuffer result = ByteBuffer.allocate(document.getLength());
        result.putInt(document.getLength());
        Object value;
        BsonType type;
        BsonElement element;
        for(String elementName : document) {
            element = document.get(elementName);
            value = element.getValue();
            if(element instanceof BsonDocument) {
                type = BsonType.DOCUMENT;
            } else if(element instanceof BsonArray) {
                type = BsonType.ARRAY;
            } else {
                type = BsonType.fromValue(value);
            }

            result.put(type.getId());
            result.put(element.getName().getBytes());
            result.put(BsonType.END.getId());
            switch (type) {
                case DOCUMENT: case ARRAY: result.put(encode((BsonCollection) element)); break;
                case BINARY: {
                    BsonBinarySubType subType = BsonBinarySubType.fromValue(value);
                    switch (subType) {
                        case GENERIC: {
                            byte[] buffer;
                            if(ByteBuffer.class.isAssignableFrom(value.getClass())) {
                                buffer = ((ByteBuffer)value).array();
                            } else if(ByteArrayOutputStream.class.isAssignableFrom(value.getClass())) {
                                buffer = ((ByteArrayOutputStream)value).toByteArray();
                            } else if(byte[].class.isAssignableFrom(value.getClass())) {
                                buffer = (byte[]) value;
                            } else {
                                throw new IllegalArgumentException("");
                            }
                            result.putInt(buffer.length);
                            result.put(BsonBinarySubType.GENERIC.getId());
                            result.put(buffer);
                            break;
                        }
                        case UUID: {
                            result.putInt(16); //Length of the UUID object
                            result.put(BsonBinarySubType.UUID.getId());
                            result.putLong(((UUID)element.getValue()).getMostSignificantBits());
                            result.putLong(((UUID)element.getValue()).getLeastSignificantBits());
                            break;
                        }
                    }
                    break;
                }
                case BOOLEAN: result.put(((Boolean)element.getValue()) ? (byte)0x01 : (byte)0x00); break;
                case DATE: result.putLong(((Date)element.getValue()).getTime()); break;
                case DOUBLE: {
                    Object elementValue = element.getValue();
                    if(elementValue instanceof Double) {
                        result.putDouble((Double) elementValue);
                    } else {
                        result.putDouble(((Number) elementValue).doubleValue());
                    }
                    break;
                }
                case INTEGER: result.putInt(((Integer)element.getValue())); break;
                case LONG:
                case TIMESTAMP:
                    result.putLong(((Long)element.getValue())); break;
                case STRING: {
                    String stringValue = (String) element.getValue();
                    //Bson specification, the total length is the length of the string + 1 for 0x00
                    result.putInt(stringValue.getBytes().length + 1);
                    result.put(stringValue.getBytes());
                    result.put(BsonType.END.getId());
                    break;
                }
                case REGEX: {
                    String stringValue = (String) element.getValue();
                    result.putInt(stringValue.getBytes().length);
                    result.put(stringValue.getBytes());
                    result.put(BsonType.END.getId());
                    break;
                }
            }
        }
        result.put(BsonType.END.getId());
        result.flip();
        return result.array();
    }
}
