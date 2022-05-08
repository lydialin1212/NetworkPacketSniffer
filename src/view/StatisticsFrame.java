package view;

import javax.swing.*;

import java.awt.event.ActionListener;
import java.util.List;
import java.util.Vector;
import javax.swing.table.*;

import model.*;
import java.awt.Component;

/**
 * This class is the statistics GUI
 * @author Lydia
 *
 */
public class StatisticsFrame extends JFrame{
	private JTable srcTable;
	private JTable packetTable;
	
	private Vector srcNames;
	private Vector srcRow;
	private Vector srcData;
	
	private Vector dstNames;
	private Vector dstRow;
	private Vector dstData;
	
	private Vector lengthNames;
	private Vector lengthData;
	
	private DefaultTableModel srcModel;
	private DefaultTableModel dstModel;
	private DefaultTableModel lengthModel;
	private JButton Start;
	private JScrollPane dstPane;
	private JScrollPane lengthPane;
	private JTable dstTable;
	private JTable lengthTable;
	
	//constructor
	/**
	 * Constructor
	 */
	public StatisticsFrame() {
		getContentPane().setLayout(null);
		
		this.srcModel = new DefaultTableModel();
		String[] name = {"Src IP", "Numbers"};
		this.srcNames = new Vector();
		for(String s: name) {
			this.srcNames.add(s);
		}
		this.srcModel.setDataVector(new Vector(), this.srcNames);
		srcTable = new JTable(this.srcModel);
		srcTable.setBounds(65, 32, 453, 118);
		
		getContentPane().add(srcTable);
		
		packetTable = new JTable();
		packetTable.setBounds(10, 289, 508, 127);
		getContentPane().add(packetTable);
		
		JScrollPane packetPane = new JScrollPane(this.packetTable);
		packetPane.setBounds(21, 296, 707, 127);
		//getContentPane().add(packetPane);
		
		JScrollPane srcPane = new JScrollPane(this.srcTable);
		srcPane.setBounds(21, 22, 262, 231);
		getContentPane().add(srcPane);
		
		Start = new JButton("Start Analysis");
		this.Start.setActionCommand("start");
		Start.setBounds(21, 263, 133, 23);
		getContentPane().add(Start);
		
		this.dstModel = new DefaultTableModel();
		String[] name2 = {"Dst IP", "Numbers"};
		this.dstNames = new Vector();
		for(String s: name2) {
			this.dstNames.add(s);
		}
		this.dstModel.setDataVector(new Vector(), this.dstNames);
		
		dstTable = new JTable(this.dstModel);
		
		dstPane = new JScrollPane(this.dstTable);
		dstPane.setBounds(293, 22, 262, 231);
		getContentPane().add(dstPane);
		
		this.lengthModel = new DefaultTableModel();
		String[] name3 = {"Length", "Numbers"};
		this.lengthNames = new Vector();
		for(String s: name3) {
			this.lengthNames.add(s);
		}
		this.lengthModel.setDataVector(new Vector(), this.lengthNames);
		
		lengthTable = new JTable(this.lengthModel);
		
		lengthPane = new JScrollPane(this.lengthTable);
		lengthPane.setBounds(563, 22, 165, 231);
		//getContentPane().add(lengthPane);
	}
	
	/**
	 * This class is to show the packets source statistics in the GUI.
	 * @param l the source packets list
	 */
	public void showSrcStatistics(List<AnalyserNode> l) {
		this.srcData = new Vector();
		for(AnalyserNode node: l) {
			SrcAnalyserNode n = (SrcAnalyserNode)node;
			this.srcData.add(n.toVector());
		}
		this.srcModel.setDataVector(this.srcData, this.srcNames);
	}
	
	/**
	 * This class is to show the packets dst statistics in the GUI.
	 * @param l2 the dst packet list
	 */
	public void showDstStatistics(List<AnalyserNode> l2) {
		this.dstData = new Vector();
		for(AnalyserNode node: l2) {
			DstAnalyserNode n2 = (DstAnalyserNode)node;
			this.dstData.add(n2.toVector());
		}
		this.dstModel.setDataVector(this.dstData, this.dstNames);
	}
	
	//for test
	/**
	 * This class is to test whether the show method works
	 */
	public void showStatistics() {
		//this.srcModel.addRow(new Vector());
	}
	
	/**
	 * This class is to set the statistics GUI listener
	 * @param listener the listener class
	 */
	public void setActionListener(ActionListener listener) {
		this.Start.addActionListener(listener);
	}
}
