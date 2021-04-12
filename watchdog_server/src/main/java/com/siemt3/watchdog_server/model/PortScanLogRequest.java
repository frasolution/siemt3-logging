/**
 *  @author Nicolas Roth
 */

package com.siemt3.watchdog_server.model;

/*++
Project Name:
watchdog_server

Author:
Maximilian Medlin (Meshstyles)
--*/

import java.io.Serializable;

public class PortScanLogRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String log;

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    /**
     * default constructor for JSON parsing
     */
    public PortScanLogRequest() { }

    public PortScanLogRequest(String log) {
        this.setLog(log);
    }
}
