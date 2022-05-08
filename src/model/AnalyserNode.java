package model;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

/**
 * This class is the analyzer node. Same packets will be gathered as linked list and stored in
 * one node, with the numbers
 * @author Lydia
 *
 */
public class AnalyserNode implements Comparable<AnalyserNode>{
	

	protected int numbers = 0;
	protected LinkedList<Packeter> head;
	protected AnalyserNode next;
	
	//constructor
	public AnalyserNode() {
		this.next = null;
		this.head = new LinkedList<Packeter>();
	}
	
	//getters
	public int getNumbers() {
		return this.numbers;
	}
	
	//recursive
	/**
	 * This method is to put a packeter in this packet list.
	 * If this packet should be put in this packet list, add
	 * the packet to the list. Otherwise go to the next list.
	 * @param p
	 */
	public boolean add(Packeter p) {
		//This node has no packeter
		if(this.numbers == 0) {
			this.createList(p);
			return true;
		}
		//whether this packeter should be put into this node
		if(this.bagOf(p)) {
			this.head.add(p);
			this.numbers += 1;
			return true;
		}else {
			//recursive
			return this.next.add(p);
		}
	}
	
	/**
	 * This method is to add a next node.
	 */
	protected void addNext() {
		this.next = new AnalyserNode();
	}
	
	protected void createList(Packeter p) {
		this.head.add(p);
		this.numbers += 1;
		this.addNext();
	}
	
	/**
	 * This method is to judge whether this packeter should be
	 * put in this list
	 * of this packeter
	 * @param p Packeter put in
	 * @return whether this packet should be put in this bag;
	 */
	protected boolean bagOf(Packeter p) {
		return false;
	}
	
	//descent
	@Override
	public int compareTo(AnalyserNode node) {
		return node.numbers - this.numbers;
	}
	
	
	
}








































