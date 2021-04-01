


package com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicIp;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshIpEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class SshIpBasicListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        String ip = (String) newEvents[0].get("ip");
        String username = (String) newEvents[0].get("username");

        String hostname = SshCommonMethods.getHostname(log);

        SshBasicIp sshBasicIp = new SshBasicIp(hostname, username, ip);
        String custom_data = SshCommonMethods.toJson(sshBasicIp);

        if (DEBUG_FLAG) {
            System.out.println(EventName.SSH_Ip + " : " + custom_data);
        }

        Alert alert = new Alert()
                .setEventType(EventType.SSH)
                .setEventName(EventName.SSH_Ip)
                .setUnix_time(arrival_time)
                .setPriority(Severity.YELLOW)
                .setCustomData(custom_data);
        try {
            DataBase.dbCommit(alert);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        runtime.getEventService().sendEventBean(
                new SshIpEvent(arrival_time,ip,hostname,username),
                "SshIpEvent"
        );

    }
}
