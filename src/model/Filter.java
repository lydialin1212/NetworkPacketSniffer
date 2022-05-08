package model;

import control.*;
import jpcap.packet.*;

public class Filter {
	
	private String keyWord;
	private FilterKey key;
	public boolean on = false;
	
	//constructor
	public Filter() {}
	
	//setters
	public void setKey(FilterKey k) {
		this.key = k;
	}
	
	public void setKeyWord(String s) {
		this.keyWord = s;
	}
	
	/**
	 * This method is to filter a packet and judge whether
	 * it should be captured
	 * @param p the packet
	 * @return whether this packet should be captured
	 */
	public boolean filter(Packet p) {
		boolean b = false;
		//whether the filter is on
		if(!this.on) {
			return b;
		}
		//if the filter is on, the packet must be a IPPacketer
		if(!(p instanceof IPPacket)) {
			b = false;
			return b;
		}

		IPPacketer iper = new IPPacketer((IPPacket)p);
		switch (key){
		case Protocol:
			if(iper.getProtocol().toLowerCase().contains(keyWord.toLowerCase())) {
				b = true;
			}
			break;
		case Src:
			if(iper.getSrc().toLowerCase().contains(keyWord.toLowerCase())) {
				b = true;
			}
			break;
		case Dst:
			if(iper.getDst().toLowerCase().contains(keyWord.toLowerCase())) {
				b = true;
			}
			break;
			}
		return b;
	}
	
	/**
	 * This method is to filter a packet and judge whether
	 * it should be captured
	 * @param p the packet
	 * @return whether this packet should be captured
	 */
	public boolean filter(Packeter p) {
		boolean b = false;
		//whether the filter is on
		if(!this.on) {
			return b;
		}
		//if the filter is on, the packet must be a IPPacketer
		if(!(p instanceof IPPacketer)) {
			b = false;
			return b;
		}

		IPPacketer iper = (IPPacketer)p;
		switch (key){
		case Protocol:
			if(iper.getProtocol().toLowerCase().contains(keyWord.toLowerCase())) {
				b = true;
			}
			break;
		case Src:
			if(iper.getSrc().toLowerCase().contains(keyWord.toLowerCase()))
			break;
		case Dst:
			if(iper.getDst().toLowerCase().contains(keyWord.toLowerCase()))
			break;
			}
		return b;
	}
	
	/**
	 * This method is to turn on the filter
	 */
	public void switchOn() {
		this.on = true;
	}
	
	/**
	 * This method is to turn off the filter
	 */
	public void switchOff() {
		this.on = false;
	}


}
