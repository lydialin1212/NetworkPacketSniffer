package control;

import java.awt.event.*;
import java.io.IOException;
import javax.swing.JFrame;
import jpcap.*;

import model.*;
import view.*;


/**
 * This class is a controller. It controls the captor and the GUI.
 * @author Lydia
 *
 */
public class Controller implements ActionListener, MouseListener, Runnable{
	
	GUI gui;
	Captor captor;
	String[] devices;
	private PacketStorage packetStorage;
	private PacketReceive r;
	private Filter filter;
	Packeter chosenPacket;
	Alert alert;
	StatisticsController statisticsController;
	
	//constructor
	public Controller() throws IOException{
		gui = new GUI();
		
		//Set some default values and actions
		gui.setTitle("Network Packet Sniffer");
		gui.setSize(830, 680);
		gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				
		//show the frame
		gui.setVisible(true);
		
		//create a packet storage
		packetStorage = new PacketStorage();
		
		//create a Captor
		captor = new Captor();
		
		//create a packet receiver and set it 
		//for captor(it is in control packet)
		this.r = new PacketReceive(gui);
		this.r.setController(this);
		this.r.setPacketStorage(packetStorage);
		
		//create a filter for the packet receiver
		filter = new Filter();
		this.r.setFilter(filter);
		captor.setPacketReceiver(r);
		
		gui.setActionListener(this);
		gui.setMouseListener(this);
		
		//search network devices, ask viewer to put them on the list
		devices = captor.getDevices();
		gui.showDevice(devices);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch (e.getActionCommand()) {
		case "chooseDevice":
			int d;
			d = gui.getDeviceIndex();
			if(d<0) {
				d = 0;
				gui.addHistory("Please choose a device");
			}else {
				gui.addHistory("Choose the device  \"" 
							+ devices[d] + "\"");
			}
			captor.setDevice(d);
			captor.createCaptor(d);
			break;
		case "search":
			devices = captor.getDevices();
			gui.showDevice(devices);
			gui.addHistory("Search the devices");
			break;
		case "start":
			gui.changeStartBtn();
			//captor.createCaptor(gui.getDeviceIndex());
			captor.startCapture();
			//gui.showPacket(captor.capture(3));
			//captor.addReceiver(this);
			gui.addHistory("Start capturing the packets");
			break;
		case "stop":
			captor.stopCapture();
			gui.changeStopBtn();
			gui.addHistory("Stop capturing the packets");
			break;
		case "clearPacket":
			gui.clearPacket();
			this.packetStorage.clear();
			gui.addHistory("Clear the packet list");
			break;
		case "clearHistory":
			gui.clearHistory();
			break;
		case "filter":
			filter.setKey(gui.getFilterKey());
			filter.switchOn();
			filter.setKeyWord(gui.getFilterKeyWord());
			break;
		case "showHostName":
			gui.showExtraInfo("Please wait. Finding HostName...");
			Thread thread = new Thread(this);
			thread.start();
			break;
		case "statistics":
			this.statisticsController = new StatisticsController();
			this.statisticsController.setPacketReceiver(r);
			break;
		case "searchSame":
			gui.showExtraInfo("Searching Same...");
			System.out.println("chosenpacket: " + this.chosenPacket.toString());
			Packeter p = this.packetStorage.search(this.chosenPacket);
			if(p == null) {
				gui.showExtraInfo("Not Found");
			}else {
				this.gui.showExtraInfo(p.toString());
			}
			break;
		case "alert":
			this.createAlert();
			break;
		case "clearAlert":
			this.alert = null;
		}
	}
	
	/**
	 * This method is to create an alert when a specific pack shows up
	 */
	public void createAlert() {
		//create an alert
		alert = new Alert();
		alert.setSize(300, 200);
		alert.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

	}
	
	public void showAlert() {
		if(alert == null) {
			return;
		}
		if(alert.isVisible()) {
			return;
		}else{
			alert.setVisible(true);
		}
	}
	
	
	@Override
	public void run() {
		String s = new String();
		if(this.chosenPacket instanceof IPPacketer) {
			IPPacketer iper = (IPPacketer)this.chosenPacket;
			s += "Src HostName:  ";
			s += iper.getSrcHostName() + "\n";
			s += "Dst HostName:  ";
			s += iper.getDstHostName();
		}else {
			s = "Cannot get the HostName";
		}
		gui.showExtraInfo(s);
	}
		
	@Override
	public void mouseClicked(MouseEvent e) {
		this.chosenPacket = this.packetStorage.getPacket(gui.getSelectedPacket());
	}
	
    @Override
    public void mouseReleased(MouseEvent e) {}
 
    @Override
    public void mousePressed(MouseEvent e) {}
 
    @Override
    public void mouseExited(MouseEvent e) {}
 
    @Override
    public void mouseEntered(MouseEvent e) {}
	    
}

	

