package model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Collections;

/**
 * This class is a analyzer Bag. In this bag, different kinds of
 * packets will be stored into different lists.
 * @author Lydia
 *
 */
public class AnalyserBag{
	
	private AnalyserNode head;
	private int total;
	private List<AnalyserNode> list;
	
	//constructor
	public AnalyserBag(AnalyserKey key) {
		switch (key) {
		case src:
			this.head = new SrcAnalyserNode();
			break;
		case dst:
			this.head = new DstAnalyserNode();
			break;
		}
		this.total = 0;
	}
	
	/**
	 * This method is to add packet into the bag.
	 * @param p the packet
	 */
	public void add(Packeter p) {
		if(head.add(p)) {
			this.total++;
		}
	}
	
	/**
	 * This method is sort the packet list and return the packets list
	 * @return the sorted packets list
	 */
	public List<AnalyserNode> getSortedBag(){
		this.list = new ArrayList<>();
		AnalyserNode a = this.head;
		if(this.head.numbers == 0) {
			return null;
		}
		while(a.numbers != 0) {
			System.out.println(a);
			list.add(a);
			a = a.next;
		}
		Collections.sort(list);
		return this.list;
	}
	
	
}
