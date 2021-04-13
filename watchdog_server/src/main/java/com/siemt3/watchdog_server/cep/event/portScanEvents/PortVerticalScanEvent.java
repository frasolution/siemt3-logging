/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortVerticalScanEvent {
	private String srcIp;
	private String dstIp;
	private Integer maxPort;
	private Integer minPort;
		
	public PortVerticalScanEvent(String srcIp, String dstIp, Integer maxPort, Integer minPort) {
		super();
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.maxPort = maxPort;
		this.minPort = minPort;
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
	public String getSrcIp() {
		return srcIp;
	}
	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
	public String getDstIp() {
		return dstIp;
	}
	public void setDstIp(String dstIp) {
		this.dstIp = dstIp;
	}

}
