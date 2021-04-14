/**
 * @author Nicolas Roth
 */

package com.g3.maven.eclipse.port;

import java.util.HashMap;

import org.pcap4j.core.PcapPacket;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;

public class PortWatcher {	
	private HashMap<String, ConnectionState> connections = new HashMap<String, ConnectionState>();
	private HashMap<String, String> udpConnections = new HashMap<String, String>(); 

	public void addTcpCon(PcapPacket pcapPacket, ConnectionState value){
		this.connections.put(
			this.getMapKey(pcapPacket), 
			value);
		;
	}
	
	public void removeTcpCon(PcapPacket pcapPacket) {
		this.connections.remove(this.getMapKey(pcapPacket));
	}
	
	public void addUDP(PcapPacket pcapPacket) {
		this.udpConnections.put(
				this.getMapKey(pcapPacket), 
				"active");
	}
	public void removeUdpCon(PcapPacket pcapPacket) {
		this.udpConnections.remove(this.getMapKey(pcapPacket));
	}
	
	public ConnectionState getState(String key) {		
		return this.connections.get(key);
	}

	public int getConnectionCount() {
		return this.connections.size();
	}
	
	public String getMapKey(PcapPacket pcapPacket) {
		return pcapPacket.get(IpV4Packet.class).getHeader().getDstAddr().toString() +
				":" + 
				pcapPacket.get(TcpPacket.class).getHeader().getDstPort().valueAsString();
	}
}
