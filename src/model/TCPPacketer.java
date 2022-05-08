package model;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import jpcap.packet.*;

public class TCPPacketer extends IPPacketer{
	
	
	public TCPPacketer(TCPPacket tcp) {
		super(tcp);
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof TCPPacketer)) {
			return false;
		}
		try {
			Method method = other.getClass().
					getMethod("equalsTCPPacketer", TCPPacketer.class);
			
			return ((TCPPacketer)other).equalsTCPPacketer(this);
		}
		catch(NoSuchMethodException e) {
			return false;
		}
	}
	
	/**
	 * This method is a double dispatch to judge the equality
	 * @param p the packet
	 * @return whether they are equal
	 */
	public boolean equalsTCPPacketer(TCPPacketer p) {
		if(this.src.equals(p.src) && (this.dst.equals(p.dst))) {
			return true;
		}
		return false;
	}
	
	

}
