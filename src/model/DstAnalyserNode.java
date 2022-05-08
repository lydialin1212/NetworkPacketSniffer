package model;

import java.util.LinkedList;
import java.net.*;
import java.util.*;

/**
 * This class is dst analyzer node. The dst ip and packet with same ip will be stored in one node.
 * @author Lydia
 *
 */
public class DstAnalyserNode extends AnalyserNode{
	protected InetAddress inetAddress;
	
	//constructors
	public DstAnalyserNode() {}
	
	public DstAnalyserNode(Packeter p) {
		
	}
	
	/**
	 * This method is to turn a node into a vector.
	 * @return a vector with a dst ip and the number
	 */
	public Vector toVector() {
		Vector v;
		v = new Vector();
		v.add(this.inetAddress.toString().replace("/", ""));
		v.add(this.numbers);
		return v;
		
	}
	
	@Override
	public boolean add(Packeter p) {
		if(p instanceof IPPacketer) {
			IPPacketer iper = (IPPacketer)p;
			if(this.numbers == 0) {
				this.createList(iper);
				return true;
			}
			if(this.bagOf(iper)) {
				this.head.add(p);
				this.numbers += 1;
				return true;
			}else {
				//recursive
				return this.next.add(p);
			}
		}else {
			return false;
		}
	}
	
	@Override
	protected void createList(Packeter p) {
		IPPacketer iper = (IPPacketer)p;
		this.inetAddress = iper.dst;
		super.createList(p);
	}

	
	@Override
	protected boolean bagOf(Packeter p) {
		if(p instanceof IPPacketer) {
			IPPacketer iper = (IPPacketer)p;
			String a = this.inetAddress.toString().replace("/", "");
			String b = iper.dst.toString().replace("/", "");
			if(a.equals(b)) {
				return true;
			}else {
				return false;	
			}
		}else {
			return false;
		}
	}
	
	@Override
	protected void addNext() {
		this.next = new DstAnalyserNode();
	}
	
	@Override
	public String toString() {
		String s = "";
		s += "Dst: " + this.inetAddress.toString().replace("/", "") + "\n";
		s = "numbers: " + this.numbers;
		return s;
	}

}
