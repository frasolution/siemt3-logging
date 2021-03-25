package com.siemt3.watchdog_server.cep.event.apache2Events;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class Apache2LogEvent {
    private String log;

    private Map<String, String> json;
    private String status;
    private String request;


    public Apache2LogEvent(String log) throws IOException {
        this.setLog(log);

        //this.setJson(log);
        //this.setStatus(json.get("status"));
        //this.setRequest(json.get("request"));
    }


    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }


    public Map<String, String> getJson() {
        return json;
    }

    public void setJson(String json) {

        try {
            InputStream inputStream = new ByteArrayInputStream(log.getBytes(StandardCharsets.UTF_8));
            ObjectMapper mapper = new ObjectMapper();
            JsonParser jsonParser = mapper.getFactory().createParser(inputStream);

            while (jsonParser.nextToken() != null) {
                this.json = mapper.readValue(jsonParser, Map.class);
            }

        } catch (IOException e) {
            System.out.println("try catch, Apache2LogEvent, setJson, IOException");
        }
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        System.out.println("setStatus: " + json.get("status"));
        this.status = status;
    }


    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }




}
