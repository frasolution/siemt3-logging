/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortVerticalScanEvent {
	private long arrivalTime;
	private String srcIp;
	private String dstIp;
	private Integer minPort;
	private Integer maxPort;
	
	public PortVerticalScanEvent(long arrivalTime, String srcIp, String dstIp, Integer minPort, Integer maxPort) {
		super();
		this.arrivalTime = arrivalTime;
		this.srcIp = srcIp;
		this.dstIp = dstIp;
		this.minPort = minPort;
		this.maxPort = maxPort;
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

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
