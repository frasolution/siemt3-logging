/**
 *  @author Nicolas Roth
 */

package com.siemt3.watchdog_server.model;

import java.io.Serializable;

public class PortScanLogRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	private String type;
	private String srcIp;
	private String srcPort;
	private String dstIp;
	private String dstPort;
    /**
     * default constructor for JSON parsing
     */
    public PortScanLogRequest() { }
	
	public PortScanLogRequest(String type, String srcIp, String srcPort,
			String dstIp, String dstPort) {
		this.type = type;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.srcPort = srcPort;
		this.dstPort = dstPort;		
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public String getSrcPort() {
		return srcPort;
	}

	public void setSrcPort(String srcPort) {
		this.srcPort = srcPort;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public String getDstPort() {
		return dstPort;
	}

	public void setDstPort(String dstPort) {
		this.dstPort = dstPort;
	}
    
    
}
