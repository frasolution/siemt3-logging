package com.siemt3.watchdog_server.cep.listener.apache2;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.common.client.PropertyAccessException;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.google.gson.Gson;
import com.siemt3.watchdog_server.model.Alert;

import java.util.ArrayList;

public class Apache2WarnListener implements UpdateListener {

    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement epStatement, EPRuntime epRuntime) {


        EventBean event = newEvents[0];
        String eventType = "";
        String eventName = "";
        long time = System.currentTimeMillis() / 1000;
        int priority = 0;

        try {
            if (event.get("eventName") != null) {
                System.out.println("listener: " + event.get("eventName"));
                eventType = (String) event.get("eventType");
                eventName = (String) event.get("eventName");
                priority = (int) event.get("priority");
            }
        } catch (
                PropertyAccessException exception) {
            System.out.println("Alert listener eventName: PropertyAccessException");
        } catch (NullPointerException exception) {
            System.out.println("Alert listener eventName: NullPointerException");
        }


        ArrayList<String> customData = new ArrayList<String>();


        for (EventBean eventCustom : newEvents) {
            try {
                if (eventCustom.get("customData") != null) {
                    //System.out.println("listener: " + eventCustom.get("eventName") + ", " + eventCustom.get("customData"));
                    customData.add((String) eventCustom.get("customData"));
                }
            } catch (PropertyAccessException exception) {
                System.out.println("Alert listener customData: PropertyAccessException");
            } catch (NullPointerException exception) {
                System.out.println("Alert listener customData: NullPointerException");
            }
        }

        Gson gson = new Gson();
        String customString = gson.toJson(customData);

        System.out.println("customString: " + customString);

        Alert alert = new Alert(eventType, eventName, time, priority, customString);

//        try {
//            DataBase.dbCommit(alert);
//        }catch (SQLException exception){
//            exception.printStackTrace();
//        }
    }
}
