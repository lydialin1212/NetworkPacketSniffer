package model;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import jpcap.packet.*;

public class UDPPacketer extends IPPacketer{
	
	public UDPPacketer(UDPPacket udp) {
		super(udp);
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof UDPPacketer)) {
			return false;
		}
		try {
			System.out.println(other.getClass().getMethods().toString());
			Method method = other.getClass().
					getMethod("equalsUDPPacketer", UDPPacketer.class);
			return ((UDPPacketer)other).equalsUDPPacketer(this);
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
	public boolean equalsUDPPacketer(UDPPacketer p) {
		if(this.src.equals(p.src) && (this.dst.equals(p.dst))) {
			return true;
		}
		return false;
	}
}
