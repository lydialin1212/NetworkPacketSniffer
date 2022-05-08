package model;

import java.util.*;

import control.*;

public class PacketStorage {
	private LinkedList<Packeter> head;

	//constructors
	public PacketStorage() {
		this.head = new LinkedList();
	}
	
	//setters
	public void add(Packeter p) {
		head.add(p);
	}
	
	//getters
	public Packeter getPacket(int i) {
		return head.get(i);
	}
	
	/**
	 * This method is to clear the storage
	 */
	public void clear() {
		head = new LinkedList();
	}
	
	/**
	 * This method is to search a same packet in the storage
	 * @param p the packet
	 * @return the same packet found in the storage
	 */
	public Packeter search(Packeter p) {
		Packeter can = this.head.getFirst();
		for(Packeter tmp: this.head) {
			System.out.println("temp: " + tmp.toString());
			if(p.equals(tmp)) {
				return tmp;
			}
		}
		return null;
	}

}
