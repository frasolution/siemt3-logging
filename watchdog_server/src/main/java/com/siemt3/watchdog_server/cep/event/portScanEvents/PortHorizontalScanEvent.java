/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortHorizontalScanEvent {
	private long arrivalTime;
	private String srcIp;
	private Integer dstPort;
	private Integer dstIpCount;

	public PortHorizontalScanEvent(long arrivalTime, String srcIp, Integer dstPort, Integer dstIpCount) {
		super();
		this.arrivalTime = arrivalTime;
		this.srcIp = srcIp;
		this.dstPort = dstPort;
		this.dstIpCount = dstIpCount;
	}
	
	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public String getSrcIp() {
		return srcIp;
	}

	public void setSrcIp(String srcIp) {
		this.srcIp = srcIp;
	}

	public Integer getDstPort() {
		return dstPort;
	}

	public void setDstPort(Integer dstPort) {
		this.dstPort = dstPort;
	}

	public Integer getDstIpCount() {
		return dstIpCount;
	}

	public void setDstIpCount(Integer dstIpCount) {
		this.dstIpCount = dstIpCount;
	}
}
