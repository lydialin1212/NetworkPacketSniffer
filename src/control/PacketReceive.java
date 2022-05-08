package control;

import java.awt.event.*;
import java.io.IOException;
import java.io.*;

import view.Alert;
import view.GUI;

import javax.swing.JFrame;

import jpcap.*;
import jpcap.packet.*;
import java.net.*;
import model.*;

/**
 * This class is to receive the captor's packets and take some
 * actions.
 * 
 * @author Lydia
 *
 */
public class PacketReceive implements PacketReceiver{
	
	GUI gui;
	static FileOutputStream fos = null;
	private File file = null;
	private PacketStorage packetStorage;
	private Filter filter;
	private Analyser analyser;
	private Controller controller;
	
	//constructor
	public PacketReceive(GUI g) throws IOException{
		gui = g;
		file = new File("./ipdata.txt");
		if(!file.exists()){
            file.createNewFile();
        }
        fos = new FileOutputStream(file);
	}
	
	//setters
	public void setAnalyser(Analyser a) {
		this.analyser = a;
	}
	
	public void setPacketStorage(PacketStorage p) {
		this.packetStorage = p;
	}
	
	public void setFilter(Filter f) {
		this.filter = f;
	}
	
	public void setController(Controller c) {
		this.controller = c;
	}

	@Override
	 public void receivePacket(Packet packet)
	 {
		if(this.analyser != null) {
			if(this.analyser.on) {
				if(packet instanceof IPPacket) {
					IPPacketer per = new IPPacketer((IPPacket)packet);
					this.analyser.add(per);
				}else {
					//Packeter per = new Packeter(packet);
					//this.analyser.add(per);
				}
			}
		}
		
		//TODO: use adapter to change type
		if(this.filter.on) {
			if(!this.filter.filter(packet)) {
				return;
			}
		}

	     if(packet instanceof IPPacket) {
	    	 if(packet instanceof UDPPacket) {
	    		 UDPPacket udp = (UDPPacket)packet;
	    		 UDPPacketer udper = new UDPPacketer(udp);
	    		 gui.showPacket(udper.toVector());
	    		 packetStorage.add(udper);
	    		 
	    		 //set alert
	    		 if(this.controller.alert != null) {
	    			 if(this.controller.chosenPacket.equals(udper)) {
	    				 this.controller.showAlert();
	    			 }
	    		 }
	    	 }
	    	 else if(packet instanceof TCPPacket) {
	    		 TCPPacket tcp = (TCPPacket) packet;
	    		 TCPPacketer tcper = new TCPPacketer(tcp);
	    		 gui.showPacket(tcper.toVector());
	    		 packetStorage.add(tcper);
	    		 
	    		//set alert
	    		 if(this.controller.alert != null) {
	    			 if(this.controller.chosenPacket.equals(tcper)) {
	    				 this.controller.showAlert();
	    			 }
	    		 }
	    	 }else {
		    	 IPPacketer iper = new IPPacketer((IPPacket)packet);
		    	 gui.showPacket(iper.toVector());
		    	 packetStorage.add(iper);
		    	 gui.showPacket(iper);
		    	//set alert
	    		 if(this.controller.alert != null) {
	    			 if(this.controller.chosenPacket.equals(iper)) {
	    				 this.controller.showAlert();
	    			 }
	    		 }
	    		 //write to the file
		    	 try {
		    		 fos.write(iper.toString().getBytes());
		    		 //use bytes to store
		    		 fos.flush();
		    	 }catch (IOException e1) {
	                 e1.printStackTrace();
	             }
	    	 }
	     }else {
	    	 Packeter per = new Packeter(packet);
	    	 gui.showPacket(per.toVector());
	    	 packetStorage.add(per);
	    	//set alert
    		 if(this.controller.alert != null) {
    			 if(this.controller.chosenPacket.equals(per)) {
    				 this.controller.showAlert();
    			 }
    		 }
	    	 }
	     }
	
	

	     
}
