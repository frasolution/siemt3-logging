package com.siemt3.watchdog_server.cep.event.apache2Events;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;

public class Apache2ErrorLogEvent {
    private String log;
    private String function;
    private String message;

    Gson gson = new Gson();

    public Apache2ErrorLogEvent(String log) throws IOException {
        this.log = log;

        try {
            JsonElement jsonElement = gson.fromJson(log, JsonElement.class);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            this.function = jsonObject.get("function").getAsString();
            this.message = jsonObject.get("message").getAsString();

        } catch (NullPointerException exception){
            System.out.println("NullPointerException - Apache2ErrorLogEvent");
        }

    }


    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
