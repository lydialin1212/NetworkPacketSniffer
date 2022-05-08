package model;

import java.text.SimpleDateFormat;
import java.util.*;
import java.lang.reflect.Method;
import java.net.*;

import jpcap.packet.*;

/**
 * This class is a data type of IPPacket
 * @author 71041
 *
 */
public class IPPacketer extends Packeter{
	
	protected InetAddress src;
	protected InetAddress dst;
	protected String srcHostName = "";
	protected String dstHostName = "";
	protected String protocol;
	
	//constructors
	public IPPacketer() {}
	
	public IPPacketer(IPPacket ip) {
		super(ip);
		this.src = ip.src_ip;
		this.dst = ip.dst_ip;

		String protocol ="";
		switch(ip.protocol)
		{
		case 1:protocol = "ICMP"; break;
		case 2:protocol = "IGMP"; break;
		case 6:protocol = "TCP"; break;
		case 8:protocol = "EGP"; break;
		case 9:protocol = "IGP"; break;
		case 17:protocol = "UDP"; break;
		case 41:protocol = "IPv6"; break;
		case 89:protocol = "OSPF"; break;
		default : break;
		}
		this.protocol = protocol;	
	}
	
	//getters
	public String getProtocol() {
		return this.protocol;
	}
	
	public String getSrc() {
		return this.src.toString().replace("/", "");
	}
	
	public String getDst() {
		return this.dst.toString().replace("/", "");
	}
	
	//this method takes too much time
	public String getSrcHostName(){
		if(this.srcHostName == "") {
			this.srcHostName = this.src.getHostName();
		}
		return this.srcHostName;
	}
	
	public String getDstHostName(){
		if(this.dstHostName == "") {
			this.dstHostName = this.dst.getHostName();
		}
		return this.dstHostName;
	}

	@Override
	public String toString() {
		String s = "";
		
		//add time
		s += this.time;
		s += "\n";
		
		//add source and destination
		s += "Src: " + this.src.toString().replace("/", "")
				+ ", " + "Dst: " + this.dst.toString().replace("/", "");
		
		//add the protocol
		s += "\nProtocol: " + this.protocol;
		
		//add the length
		s += "\nLength: " + this.length;
		
		
		return s;
	}
	
	@Override
	public Vector toVector() {
		Vector row = new Vector();
		
		//get the id
		row.add(0);
				
		//get the time
		row.add(this.time);
		
		//get the source
		row.add(this.src.toString().replace("/", ""));
		
		//get the destination
		row.add(this.dst.toString().replace("/", ""));
		
		//get the protocol
		row.add(this.protocol);
		
		//get the length
		row.add(this.length);
		
		//get the info
		row.add(this.toString());
		
		return row;
	}
	
	//double dispatch
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof IPPacketer)) {
			return false;
		}
		try {
			Method method = other.getClass().
					getMethod("equalsIPPacketer", 
							IPPacketer.class);
			System.out.println("object class" + Object.class);
			return ((IPPacketer)other).equalsIPPacketer(this);
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
	public boolean equalsIPPacketer(IPPacketer p) {
		if(this.src.toString().equals(p.src.toString()) && (this.dst.toString().equals(p.dst.toString()))) {
			return true;
		}
		return false;
	}

}
