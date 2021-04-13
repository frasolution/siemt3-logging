/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortBlockScanEvent {
	private long arrivalTime;
	private String srcIp;
	private Integer maxPort;
	private Integer minPort;
	private Integer dstIpCount;
	
	public PortBlockScanEvent(long arrivalTime, String srcIp, Integer maxPort, Integer minPort, Integer dstIpCount) {
		super();
		this.arrivalTime = arrivalTime;
		this.srcIp = srcIp;
		this.maxPort = maxPort;
		this.minPort = minPort;
		this.dstIpCount = dstIpCount;
	}
	
	public String getSrcIp() {
		return srcIp;
	}
	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}
	public Integer getMaxPort() {
		return maxPort;
	}
	public void setMaxPort(Integer maxPort) {
		this.maxPort = maxPort;
	}
	public Integer getMinPort() {
		return minPort;
	}
	public void setMinPort(Integer minPort) {
		this.minPort = minPort;
	}
	public Integer getDstIpCount() {
		return dstIpCount;
	}
	public void setDstIpCount(Integer dstIpCount) {
		this.dstIpCount = dstIpCount;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
}
