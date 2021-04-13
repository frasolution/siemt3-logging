/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.listener.port;

import com.espertech.esper.common.client.EventBean;
import com.espertech.esper.runtime.client.EPRuntime;
import com.espertech.esper.runtime.client.EPStatement;
import com.espertech.esper.runtime.client.UpdateListener;
import com.siemt3.watchdog_server.EventName;
import com.siemt3.watchdog_server.EventType;
import com.siemt3.watchdog_server.Severity;
import com.siemt3.watchdog_server.cep.event.portScanEvents.PortDistVerticalScanEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class PortDistVerticalScanListener implements UpdateListener {
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
		Long arrivalTime = (long) newEvents[0].get("arrivalTime");
		String dstIp = (String) newEvents[0].get("dstIp");
		Integer maxPort = (Integer) newEvents[0].get("maxPort");
		Integer minPort = (Integer) newEvents[0].get("minPort");
		Integer srcIpCount = (Integer) newEvents[0].get("srcIpCount");

		PortDistVerticalScanEvent portDistVertical = new PortDistVerticalScanEvent(arrivalTime, dstIp, maxPort, minPort,
				srcIpCount);
		String custom_data = SshCommonMethods.toJson(portDistVertical);

		if (DEBUG_FLAG)
			System.out.println(custom_data);

		Alert alert = new Alert().setEventType(EventType.PORT_SCAN).setEventName(EventName.PORT__DIST_VERTICAL)
				.setUnix_time(arrivalTime).setPriority(Severity.GREEN).setCustomData(custom_data);
		try {
			DataBase.dbCommit(alert);
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

		runtime.getEventService().sendEventBean(portDistVertical, "PortDistVerticalScanEvent");

	}
}
