package model;

import java.net.*;
import java.util.*;
import java.util.stream.Stream;

import control.*;

/**
 * This class is the analyser. It can analysis the packet and provide a 
 * statistic result.
 * @author Lydia
 *
 */
public class Analyser{
	
	public boolean on = false;
	public AnalyserBag srcAddressBag;
	public AnalyserBag dstAddressBag;
	// wrapper classes but gets the primitive data type.
	public AnalyserBag lengthBag;
	StatisticsController statisticsController;
	public Thread thread = null;
	
	//constructor
	public Analyser() {
		this.srcAddressBag = new AnalyserBag(AnalyserKey.src);
		this.dstAddressBag = new AnalyserBag(AnalyserKey.dst);
	}

	//setters
	public void switchOn() {
		this.on = true;
	}
	
	public void setController(StatisticsController s) {
		this.statisticsController = s;
	}
	
	/**
	 * This method is to store a packet into the packet statistics bag.
	 * @param p the packet
	 */
	public void add(Packeter p) {
		srcAddressBag.add(p);
		dstAddressBag.add(p);
		//lengthBag.add(p);
	}
	
	/**
	 * This method is to turn off the analyzer
	 */
	public void switchOff() {
		this.on = false;
	}
	
	/**
	 * This method is to start the analyzer. It will start
	 * a new thread
	 */
	public void startAnalysis() {
		this.on = true;
		System.out.println("start Analysis");
		if (this.thread == null) {
			this.switchOn();
			thread = new Thread(this.statisticsController);
			thread.start();
		}else {
			//thread.start();
		}
	}
	


}




