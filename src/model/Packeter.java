package model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.reflect.Method;

import jpcap.packet.*;

public class Packeter {
	
	//private Packet p;
	protected int length;
	protected String time;
	protected byte[] data;
	protected byte[] header;
	
	//contructors
	public Packeter() {}
	
	public Packeter(Packet p) {
		//this.p = p;
		this.data = p.data;
		this.header = p.header;
		this.length = p.len;
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		this.time = formatter.format(date);
	}
	
	/**
	 * This method is to turn this packet into a vector.
	 */
	public Vector toVector() {
		Vector row = new Vector();
		
		//get the id
		row.add(0);
				
		//get the time
		row.add(this.time);
		
		//get the source
		row.add("");
		
		//get the destination
		row.add("");
		
		//get the protocol
		row.add("");
		
		//get the length
		row.add(this.length);
		
		//get the info
		row.add(this.toString());
		
		return row;
		
	}
	
	@Override
	public String toString() {
		String s = "";
		
		//add time
		s += this.time;
		s += "\n";
		
		//add source and destination
		s += "header: " + this.header;
		
		//add the length
		s += "\nLength: " + this.length;
		return s;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof Packeter)) {
			return false;
		}
		try {
			Method method = other.getClass().
					getMethod("equalsPacketer", 
							Packeter.class);
			return ((Packeter)other).equalsPacketer(this);
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
	public boolean equalsPacketer(Packeter p) {
		if(this.header.toString().equals(p.header.toString())) {
			return true;
		}
		return false;
	}

}
