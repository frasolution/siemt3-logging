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
import com.siemt3.watchdog_server.cep.event.portScanEvents.PortDistHorizontalScanEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class PortDistHorizontalScanListener implements UpdateListener {
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {
		long arrivalTime = (long) newEvents[0].get("arrivalTime");
		Integer dstPort = (Integer) newEvents[0].get("dstPort");
		Integer srcIpCount = (Integer) newEvents[0].get("srcIpCount");
		Integer dstIpCount = (Integer) newEvents[0].get("dstIpCount");

		PortDistHorizontalScanEvent portDistHorizontal = new PortDistHorizontalScanEvent(arrivalTime, dstPort, srcIpCount,
				dstIpCount);
		String custom_data = SshCommonMethods.toJson(portDistHorizontal);

		if (DEBUG_FLAG)
			System.out.println(custom_data);

		Alert alert = new Alert().setEventType(EventType.PORT_SCAN).setEventName(EventName.PORT_DIST_HORIZONTAL)
				.setUnix_time(arrivalTime).setPriority(Severity.YELLOW).setCustomData(custom_data);
		try {
			DataBase.dbCommit(alert);
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

		runtime.getEventService().sendEventBean(portDistHorizontal, "PortDistHorizontalScanEvent");

	}
}
