package org.hcjf.bson;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author javaito
 * @mail javaito@gmail.com
 */
public final class BsonEncoder {

    /**
     *
     * @param document
     * @return
     */
    public static byte[] encode(BsonDocument document) {
        ByteBuffer result = ByteBuffer.allocate(document.getLength());
        result.putInt(document.getLength());
        Object value;
        for(String elementName : document) {
            BsonElement element = document.get(elementName);
            result.put(element.getName().getBytes());
            result.put(BsonType.END.getId());
            if(element.isDocument() || element.isArray()) {
                result.put(encode((BsonDocument) element));
            } else {
                value = element.getValue();
                BsonType type = BsonType.fromValue(value);
                result.put(type.getId());
                switch (type) {
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
                    case DATE: result.putLong(((Long)element.getValue())); break;
                    case DOUBLE: result.putDouble(((Double)element.getValue())); break;
                    case INTEGER: result.putInt(((Integer)element.getValue())); break;
                    case LONG: result.putLong(((Long)element.getValue())); break;
                    case STRING: {
                        String stringValue = (String) element.getValue();
                        result.putInt(stringValue.length() + 1); //Bson specification, the total length is the length of the string + 1 for 0x00
                        result.put(stringValue.getBytes());
                        result.put(BsonType.END.getId());
                        break;
                    }
                    case TIMESTAMP: result.putLong(((Long)element.getValue())); break;
                    case REGEX: {
                        String stringValue = (String) element.getValue();
                        result.putInt(stringValue.length());
                        result.put(stringValue.getBytes());
                        result.put(BsonType.END.getId());
                        break;
                    }
                }
            }
        }
        result.put(BsonType.END.getId());
        result.flip();
        return result.array();
    }
}
