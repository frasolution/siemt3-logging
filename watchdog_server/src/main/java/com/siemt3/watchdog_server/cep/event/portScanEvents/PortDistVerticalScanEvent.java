/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortDistVerticalScanEvent {
	private String dstIp;
	private Integer maxPort;
	private Integer minPort;
	private Integer srcIpCount;

	public PortDistVerticalScanEvent(String dstIp, Integer maxPort, Integer minPort, Integer srcIpCount) {
		super();
		this.dstIp = dstIp;
		this.maxPort = maxPort;
		this.minPort = minPort;
		this.srcIpCount = srcIpCount;
	}

	public Integer getMinPort() {
		return minPort;
	}

	public void setMinPort(Integer minPort) {
		this.minPort = minPort;
	}

	public Integer getMaxPort() {
		return maxPort;
	}

	public void setMaxPort(Integer maxPort) {
		this.maxPort = maxPort;
	}

	public String getDstIp() {
		return dstIp;
	}

	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

	public Integer getSrcIpCount() {
		return srcIpCount;
	}

	public void setSrcIpCount(Integer srcIpCount) {
		this.srcIpCount = srcIpCount;
	}
}
