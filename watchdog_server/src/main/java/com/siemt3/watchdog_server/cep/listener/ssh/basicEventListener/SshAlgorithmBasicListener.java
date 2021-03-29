package com.siemt3.watchdog_server.cep.listener.ssh.basicEventListener;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.customObjects.ssh.SshBasicAuth;
import com.siemt3.watchdog_server.cep.event.sshEvents.SshIpFilterEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;
import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

import java.sql.SQLException;

public class SshAlgorithmBasicListener implements UpdateListener {
    @Override
    public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
        String log = (String) newEvents[0].get("log");
        long arrival_time = (long) newEvents[0].get("arrival_time");
        String username, algo_fingerprint, ip ;
        String[] a1, a2, a3, a4, a5;

        //TODO fix when user is called from, ssh2:

        a1 = log.split(" publickey for ");
        a2 = a1[1].split(" from ");
        a3 = a2[1].split(" ssh2: ");
        a5 = a2[1].split(" port ");

        username = a2[0];
        //do not parse further since this part can vary depending on algorithm
        algo_fingerprint = a3[1];
        ip = a5[0];

        if (algo_fingerprint.contains("DSA ")) {
            SshBasicAuth sshBasicAuth = new SshBasicAuth(username, algo_fingerprint, ip);
            String custom_data = SshCommonMethods.toJson(sshBasicAuth);

            if (DEBUG_FLAG) {
                System.out.println(EventName.SSH_Algorithm + " : " + custom_data);
            }

            Alert alert = new Alert()
                    .setEventType(EventType.SSH)
                    .setEventName(EventName.SSH_Algorithm)
                    .setUnix_time(arrival_time)
                    .setPriority(Severity.GREEN)
                    .setCustomData(custom_data);
            try {
                DataBase.dbCommit(alert);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }

        runtime.getEventService().sendEventBean(
                new SshIpFilterEvent(
                        arrival_time,
                        ip,
                        log,
                        username
                ),
                "SshIpFilterEvent"
        );
    }
}
