package com.siemt3.watchdog_server.cep.event.apache2Events;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.Random;

public class Apache2AccessLogEvent {
    private String log;

    private String status;
    private String request;
    private String ip;
    private String filename;
    private String host;
    private String query;
    private String method;
    private String referer;
    private String time;

    Gson gson = new Gson();

    Random random = new Random();

    public Apache2AccessLogEvent(String log) throws IOException {
        this.log = log;

        try {
            JsonElement jsonElement = gson.fromJson(log, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();

            this.status = jsonObject.get("status").getAsString();

            //for testing
//            if(random.nextInt(100) > 50){
//              this.status = "401";
//            }else {
//                this.status = "403";
//            }

            this.request = jsonObject.get("request").getAsString();
            this.ip = jsonObject.get("remoteIP").getAsString();
            this.filename  = jsonObject.get("filename").getAsString();
            this.host = jsonObject.get("host").getAsString();
            this.query = jsonObject.get("query").getAsString();
            this.method = jsonObject.get("method").getAsString();
            this.referer = jsonObject.get("referer").getAsString();
            this.time = jsonObject.get("time").getAsString();

        } catch (NullPointerException exception){
            System.out.println("NullPointerException - Apache2AccessLogEvent");
        }

    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
