package com.siemt3.watchdog_server.cep.event.portScanEvents;

public class PortHorizontalScanEvent {
	private String Port;

	public PortHorizontalScanEvent(String port) {
		super();
		Port = port;
	}

	public String getPort() {
		return Port;
	}

	public void setPort(String port) {
		Port = port;
	}	
}
