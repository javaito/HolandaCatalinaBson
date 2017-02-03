package org.hcjf.bson;


import java.sql.Connection;
import java.util.*;

/**
 * @author Javier Quiroga.
 * @email javier.quiroga@sitrack.com
 */
public class Main {

    public static void main(String[] args) {

        String s = "N°166";
        System.out.println(s.length());
        System.out.println(s.getBytes().length);


        LogRecord logRecord = new LogRecord();
        logRecord.setServiceName("test");
        logRecord.setProducerClass("");
        logRecord.setTag("HTTP_CLIENT");
        logRecord.setMessage("2017-01-16 15:44:16,635 [O][HTTP_CLIENT] Http client request\\r\\nPOST " +
                "/Log/1.0/resource/com.sitrack.model.log.log-record HTTP/1.1\\r\\nAccept: application/stk_bson" +
                "\\r\\nUser-Agent: HCJF\\r\\nHost: 172.16.102.124\\r\\nContent-Length: 291\\r\\nContent-Type: " +
                "application/stk_bson\\r\\n\\r\\n  # P    * crud.method    CREATE_RESOURCE_MAP  R    � 0    �" +
                "\\tdate   Y���b group    ERROR host    \\n127.0.0.1 message    <2 ... [163 more]");
        logRecord.setHost("127.0.0.1");
        logRecord.setPort(8080);
        logRecord.setTimeToLive(0);
        logRecord.setSearchKeys(null);
        logRecord.setDate(new Date());
        logRecord.setGroup(LogGroup.GROUP);


        List<Map<String, Object>> collection = new ArrayList<>();
        Map<String, Object> objectMap = new HashMap<>();
        objectMap.put("field", "Fiat Iveco, amarillo, cabina rebatible, sin inscripciones (0 km), despues tendra las de \"Exp El Aguilucho\", guardab negros, parag amarillos. chasis antioxido. visera negra");
        collection.add(objectMap);
        BsonDocument document = new BsonDocument();
        document.put(new BsonArray("fields", collection));


        byte[] bytes = BsonEncoder.encode(document);
        System.out.println();
    }

    public static class LogRecord {
        UUID id;
        UUID sessionId;
        String serviceName;
        String producerClass;
        String tag;
        String message;
        String host;
        int port;
        long timeToLive;
        Map<String, String> searchKeys;
        Date date;
        LogGroup group;

        public UUID getId() {
            return id;
        }

        public void setId(UUID id) {
            this.id = id;
        }

        public UUID getSessionId() {
            return sessionId;
        }

        public void setSessionId(UUID sessionId) {
            this.sessionId = sessionId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public String getProducerClass() {
            return producerClass;
        }

        public void setProducerClass(String producerClass) {
            this.producerClass = producerClass;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public int getPort() {
            return port;
        }

        public void setPort(int port) {
            this.port = port;
        }

        public long getTimeToLive() {
            return timeToLive;
        }

        public void setTimeToLive(long timeToLive) {
            this.timeToLive = timeToLive;
        }

        public Map<String, String> getSearchKeys() {
            return searchKeys;
        }

        public void setSearchKeys(Map<String, String> searchKeys) {
            this.searchKeys = searchKeys;
        }

        public Date getDate() {
            return date;
        }

        public void setDate(Date date) {
            this.date = date;
        }

        public LogGroup getGroup() {
            return group;
        }

        public void setGroup(LogGroup group) {
            this.group = group;
        }

    }

    public enum LogGroup {

        GROUP

    }
}
