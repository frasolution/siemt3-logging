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
import com.siemt3.watchdog_server.cep.event.portScanEvents.PortBlockScanEvent;
import com.siemt3.watchdog_server.cep.listener.ssh.lib.SshCommonMethods;
import com.siemt3.watchdog_server.condb.DataBase;
import com.siemt3.watchdog_server.model.Alert;

import java.sql.SQLException;

import static com.siemt3.watchdog_server.GlobalVariables.DEBUG_FLAG;

public class PortBlockScanListener implements UpdateListener {
	@Override
	public void update(EventBean[] newEvents, EventBean[] oldEvents, EPStatement statement, EPRuntime runtime) {

		long arrivalTime = (long) newEvents[0].get("arrivalTime");
		String srcIp = (String) newEvents[0].get("srcIp");
		Integer maxPort = (int) newEvents[0].get("maxPort");
		Integer minPort = (int) newEvents[0].get("minPort");
		Integer dstIpCount = (int) (long) newEvents[0].get("dstIpCount");

		PortBlockScanEvent portBlock = new PortBlockScanEvent(arrivalTime, srcIp, maxPort, minPort, dstIpCount);
		String custom_data = SshCommonMethods.toJson(portBlock);

		if (DEBUG_FLAG)
			System.out.println(custom_data);

		Alert alert = new Alert().setEventType(EventType.PORT_SCAN).setEventName(EventName.PORT_BLOCK)
				.setUnix_time(arrivalTime).setPriority(Severity.RED).setCustomData(custom_data);
		try {
			DataBase.dbCommit(alert);
		} catch (SQLException throwable) {
			throwable.printStackTrace();
		}

		runtime.getEventService().sendEventBean(portBlock, "PortBlockScanEvent");

	}
}
