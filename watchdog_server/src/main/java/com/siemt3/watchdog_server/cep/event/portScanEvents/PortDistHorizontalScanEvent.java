/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortDistHorizontalScanEvent {
	private long arrivalTime;
	private Integer dstPort;
	private Integer srcIpCount;
	private Integer dstIpCount;

	public PortDistHorizontalScanEvent(long arrivalTime, Integer dstPort, Integer srcIpCount, Integer dstIpCount) {
		super();
		this.arrivalTime = arrivalTime;
		this.dstPort = dstPort;
		this.srcIpCount = srcIpCount;
		this.dstIpCount = dstIpCount;
	}

	public Integer getDstIpCount() {
		return dstIpCount;
	}

	public void setDstIpCount(Integer dstIpCount) {
		this.dstIpCount = dstIpCount;
	}

	public Integer getSrcIpCount() {
		return srcIpCount;
	}

	public void setSrcIpCount(Integer srcIpCount) {
		this.srcIpCount = srcIpCount;
	}

	public Integer getDstPort() {
		return dstPort;
	}

	public void setDstPort(Integer dstPort) {
		this.dstPort = dstPort;
	}

	public long getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(long arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

}
