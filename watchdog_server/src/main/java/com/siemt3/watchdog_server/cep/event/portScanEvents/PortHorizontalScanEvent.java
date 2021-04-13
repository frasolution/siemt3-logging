/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortHorizontalScanEvent {
	private String srcIp;
	private Integer dstPort;
	private Integer dstIpCount;

	public PortHorizontalScanEvent(String srcIp, Integer dstPort, Integer dstIpCount) {
		super();
		this.srcIp = srcIp;
		this.dstPort = dstPort;
		this.dstIpCount = dstIpCount;
	}

	public Integer getDstPort() {
		return dstPort;
	}

	public void setPort(Integer dstPort) {
		this.dstPort = dstPort;
	}

	public Integer getDstAdrCount() {
		return dstIpCount;
	}

	public void setDstIpCount(Integer dstIpCount) {
		this.dstIpCount = dstIpCount;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
}
