/**
 * @author Nicolas roth
 */

package com.g3.maven.eclipse;

import org.pcap4j.core.BpfProgram.BpfCompileMode;
import org.pcap4j.core.PcapHandle.PcapDirection;
import org.pcap4j.core.NotOpenException;
import org.pcap4j.core.PacketListener;
import org.pcap4j.core.PcapHandle;
import org.pcap4j.core.PcapNativeException;
import org.pcap4j.core.PcapNetworkInterface;
import org.pcap4j.core.PcapNetworkInterface.PromiscuousMode;
import org.pcap4j.core.PcapPacket;
import org.pcap4j.core.Pcaps;
import org.pcap4j.packet.IpV4Packet;
import org.pcap4j.packet.TcpPacket;
import org.pcap4j.packet.TcpPacket.TcpHeader;

import com.g3.maven.eclipse.model.PortScanLogRequest;
import com.g3.maven.eclipse.port.ConnectionState;
import com.g3.maven.eclipse.port.PortWatcher;
import com.g3.maven.eclipse.web.HttpService;

public class PortScan {
	HttpService httpService;
	final PortWatcher pw; 
	
	public PortScan() {
        pw = new PortWatcher();
		httpService = new HttpService();
	}
	
    public static void main(String[] args) throws PcapNativeException, NotOpenException {
    	PortScan portScan = new PortScan();
    	try {
    		portScan.startIn();
		} catch (Exception e) {
			return;
		} 
    }
    
    public void startIn() throws PcapNativeException, NotOpenException {
              
    	PcapNetworkInterface device = Pcaps.getDevByName("any");
    	
        if (device == null) {
            System.exit(1);
        }

        // Open the device and get a handle
        int snapshotLength = 65536; // in bytes   
        int readTimeout = 1000; // in milliseconds                   
        final PcapHandle handle;
        handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);

        handle.setDirection(PcapDirection.IN);
        
        // Set a filter to only listen for tcp packets on port 80 (HTTP)
        String filter = "tcp";
        handle.setFilter(filter, BpfCompileMode.OPTIMIZE);

        // Create a listener that defines what to do with the received packets
        PacketListener listener = new PacketListener() {

			//@Override
			public void gotPacket(PcapPacket packet) {                                
                TcpHeader tcpHeaderPackage = packet.get(TcpPacket.class).getHeader();
                
                // for local purposes like local nmap scan
                if (tcpHeaderPackage.getAck() && tcpHeaderPackage.getSyn())
                	return;
                
                if (tcpHeaderPackage.getSyn()) {
                	pw.addTcpCon(packet, ConnectionState.SYN);
                	
                } else if (tcpHeaderPackage.getRst()) {
                	if (pw.getState(pw.getMapKey(packet)) == ConnectionState.SYN) {
                		// half open scan
                		sendRawEventToServer("SYN_SCAN", packet);
                		pw.removeTcpCon(packet);
                	}
                	
                } else if (tcpHeaderPackage.getFin()) {
                	if (pw.getState(pw.getMapKey(packet)) == null) {
                		// fin scan
                		sendRawEventToServer("FIN_SCAN", packet);
                	} else {
                		pw.removeTcpCon(packet);
                	}
                	
                } else {
                	pw.addTcpCon(packet, ConnectionState.SYN_ACK);
                	// default communication                 
                }				
			}
        };

        // add listener to handle
        try {
            int maxPackets = -1;
            handle.loop(maxPackets, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        handle.close();
    }
    
    public void startOut() throws PcapNativeException, NotOpenException {
              
    	PcapNetworkInterface device = Pcaps.getDevByName("any");
    	
        if (device == null) {
            System.exit(1);
        }

        // Open the device and get a handle
        int snapshotLength = 65536; // in bytes   
        int readTimeout = 1000; // in milliseconds                   
        final PcapHandle handle;
        handle = device.openLive(snapshotLength, PromiscuousMode.PROMISCUOUS, readTimeout);

        handle.setDirection(PcapDirection.OUT);
        
        // Set a filter to only listen for tcp packets on port 80 (HTTP)
        String filter = "tcp";
        handle.setFilter(filter, BpfCompileMode.OPTIMIZE);

        // Create a listener that defines what to do with the received packets
        PacketListener listener = new PacketListener() {

			//@Override
			public void gotPacket(PcapPacket packet) {                                
                TcpHeader tcpHeaderPackage = packet.get(TcpPacket.class).getHeader();
                
                if (tcpHeaderPackage.getRst()) {
                	if (pw.getState(pw.getMapKey(packet)) == ConnectionState.SYN) {
                		// half open scan
                		sendRawEventToServer("SYN_SCAN", packet);
                		pw.removeTcpCon(packet);
                	}
                	
                } 
			}
		};

        // add listener to handle
        try {
            int maxPackets = -1;
            handle.loop(maxPackets, listener);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        handle.close();
    }
    

    
    public void sendRawEventToServer(String type, PcapPacket pcapPacket) {    	
    	PortScanLogRequest portScanLogRequest = new PortScanLogRequest(
    		type,
    		pcapPacket.get(IpV4Packet.class).getHeader().getSrcAddr().toString(),
    		pcapPacket.get(TcpPacket.class).getHeader().getSrcPort().valueAsInt(),
    		pcapPacket.get(IpV4Packet.class).getHeader().getDstAddr().toString(),
    		pcapPacket.get(TcpPacket.class).getHeader().getDstPort().valueAsInt()
		);
    	
    	
//    	PortScanLogRequest portScanLogRequest2 = new PortScanLogRequest(
//    		type,
//    		pcapPacket.get(IpV4Packet.class).getHeader().getSrcAddr().toString(),
//    		pcapPacket.get(TcpPacket.class).getHeader().getSrcPort().valueAsInt(),
//    		String.valueOf(Math.random()),
////    		pcapPacket.get(IpV4Packet.class).getHeader().getDstAddr().toString(),
//    		pcapPacket.get(TcpPacket.class).getHeader().getDstPort().valueAsInt()
//		);
    	
    	try {
    		httpService.sendPortLog(portScanLogRequest);	
//    		httpService.sendPortLog(portScanLogRequest2);			
		} catch (Exception e) {
		}
    }
}
