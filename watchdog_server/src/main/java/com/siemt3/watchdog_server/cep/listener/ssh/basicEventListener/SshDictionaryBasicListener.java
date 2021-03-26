package com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicPassword;
import com.siemt3.watchdog_server.cep.event.sshEvents.elevated.SshDictionaryElevatedEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

public class SshDictionaryBasicListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        long arrival_time   =   DataBase.current_time();
        String username     =   (String)    newEvents[0].get("username");
        String ip           =   (String)    newEvents[0].get("ip");

        System.out.println(arrival_time + username + ip);
        //TODO get old data to have an ip collection

        SshBasicPassword sshBasicPassword = new SshBasicPassword(username, ip);
        String custom_data = SshCommonMethods.toJson(sshBasicPassword);
        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_Dictionary)
                .setUnix_time(arrival_time)
                .setPriority(Severity.YELLOW)
                .setCustomData(custom_data);

        try{
            DataBase.dbCommit(alert);
        }catch (SQLException throwables){
            throwables.printStackTrace();
        }

        runtime.getEventService().sendEventBean(
                new SshDictionaryElevatedEvent(arrival_time, username),
                "SshDictionaryElevatedEvent"
        );

    }
}
