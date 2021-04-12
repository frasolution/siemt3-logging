package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortVerticalScanEvent {
	private String maxPort;
	private String minPort;
	
	public PortVerticalScanEvent(String maxPort, String minPort) {
		this.maxPort = maxPort;
		this.minPort = minPort;
	}
	public String getMinPort() {
		return minPort;
	}
	public void setMinPort(String minPort) {
		this.minPort = minPort;
	}
	public String getMaxPort() {
		return maxPort;
	}
	public void setMaxPort(String maxPort) {
		this.maxPort = maxPort;
	}

}
