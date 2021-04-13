/**
 * @author Nicolas Roth
 */

package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortDistBlockScanEvent {
	private Integer maxPort;
	private Integer minPort;
	private Integer dstIpCount;
	private Integer srcIpCount;
	
	public PortDistBlockScanEvent(Integer maxPort, Integer minPort, Integer dstIpCount, Integer srcIpCount) {
		super();
		this.maxPort = maxPort;
		this.minPort = minPort;
		this.dstIpCount = dstIpCount;
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
}
