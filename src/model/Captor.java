package model;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import jpcap.*;
import jpcap.packet.*;
import java.io.IOException;
import control.Controller;

/**
 * This class is a captor. It will capture the packets.
 * @author Lydia
 *
 */
public class Captor implements Runnable{
	String[] devicesName;
	NetworkInterface[] devices;
	JpcapCaptor captor = null;
	private int chosenDevice = 0;
	
	Thread thread = null;
	private PacketReceiver packetReceive;
	private boolean pause = false;
	
	//constructors
	public Captor() {}
	
	//setters
	public void setDevice(int i) {
		this.chosenDevice = i;
	}

	/**
	 * This method is to create a captor. Without a specific device number, the defaulted device
	 * is 0 device
	 */
	public void createCaptor() {
		try{
			//open the specific device
			captor = JpcapCaptor.openDevice(devices[0], 1514, true, 50);
			System.out.println("\n"+ devicesName[1]);
			}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is to create a captor with a specific device. The captor will be stored in this 
	 * class. Before capturing, user must call a createCaptor.
	 * @param deviceNo the device number
	 */
	public void createCaptor(int deviceNo) {
		try{
			//open the specific device
			captor = JpcapCaptor.openDevice(devices[deviceNo], 1514, true, 50);
			System.out.println("\n"+ devicesName[deviceNo]);
			}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This method is to search devices and store the information of the devices.
	 */
	private void searchDevices() {
		try {
			devices = JpcapCaptor.getDeviceList();
			
			//store the device name into a string
			devicesName = new String[devices.length];
			for(int i = 0; i<devices.length; i++) {
				NetworkInterface nc = devices[i];
				devicesName[i] = nc.description;
				System.out.println(devicesName[i] + "\n");
				}
			
			}catch(Exception ef){
				ef.printStackTrace();
				System.out.println("Search network devices failed:  "+ef);
				}
		}
	
	/**
	 * This method is to get the String of devices' name. It will call the captor to search
	 * devices again so it may take several seconds.
	 * @return a string of devices' name.
	 */
	public String[] getDevices() {
		searchDevices();
		return devicesName;
	}
	
	/**
	 * This method can capture specific IPPackets and return the packets
	 * as a bag.
	 * @param times
	 * @return the IPPacket bag which contains the packets
	 */
	public IPPacket[] capture(int times){
		Packet packet = null;
		IPPacket[] packetBag = new IPPacket[times];

		//start capture
		int i = 0;
		while(i < times) {
			System.out.print(i);
			packet  = captor.getPacket();
			if( packet instanceof IPPacket ) {
				IPPacket ip = (IPPacket)packet;
				packetBag[i] = ip;
				i++;
				}
			}
		return packetBag;
		}
	
	public void addReceiver(PacketReceiver c) {
		captor.loopPacket(10, c); 
	}

	/**
	 * This method is to stop capturing
	 */
	public void stopCapture(){
		this.pause = true;
	}
	
	/**
	 * This method is to create a thread to capture packets
	 */
	public void startCapture() {
		System.out.println("start capture");
		if (thread == null) {
			thread = new Thread(this);
			thread.start();
		}else {
			pause = false;
			thread = new Thread(this);
			thread.start();
		}
	}
	
	public void setPacketReceiver(PacketReceiver r) {
		this.packetReceive = r;
	}
	
	@Override
	public void run() {
		while(pause == false) {
			try {
				this.createCaptor(this.chosenDevice);
				this.addReceiver(this.packetReceive);
				thread.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
			}
		}
	

	
}

