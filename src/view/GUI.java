package view;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import java.awt.event.*;
import java.awt.Scrollbar;
import javax.swing.table.*;
import java.util.*;
import jpcap.packet.*;
import model.FilterKey;
import model.IPPacketer;

import java.text.*;
import java.awt.*;


public class GUI extends JFrame implements ActionListener {
	
	//GUI frame component
	private JList deviceText;
	private JTable table;
	private JButton searchDeviceBtn;
	private JButton chooseDeviceBtn;
	private JButton filterBtn;
	private JScrollPane scrollPane;
	private JButton startBtn;
	private JButton packetClear;
	private JTextPane history;
	private String historyText;
	private JButton historyClear;
	private JTextPane informationPane;
	private JComboBox filterChoose;
	private JButton statisticBtn;
	private JButton showHostName;
	private JTextPane extraInfo;
	private JTextPane filterPane;
	private JButton setAlert;
	private JButton searchSamePacket;
	
	private boolean choosePacket = false;
	
	private Vector names;
	private Vector row;
	private Vector data;
	private DefaultTableModel model;
	
	//packet numbers
	private int id = 1;
	private JButton clearAlert;

	//constructor
	public GUI() {
		getContentPane().setLayout(null);
		
		//device list
		deviceText = new JList();
		deviceText.setBounds(10, 25, 346, 93);
		getContentPane().add(deviceText);
		deviceText.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		deviceText.setLayoutOrientation(JList.VERTICAL);
		deviceText.setVisibleRowCount(-1);
		
		//search device button
		searchDeviceBtn = new JButton("Search again");
		searchDeviceBtn.setActionCommand("search");
		searchDeviceBtn.setBounds(36, 154, 166, 23);
		getContentPane().add(searchDeviceBtn);
		
		//choose the device list button
		chooseDeviceBtn = new JButton("Choose");
		chooseDeviceBtn.setActionCommand("chooseDevice");
		chooseDeviceBtn.setBounds(212, 154, 93, 23);
		getContentPane().add(chooseDeviceBtn);
	
		//filter words list
		filterPane = new JTextPane();
		filterPane.setBounds(654, 58, 149, 86);
		getContentPane().add(filterPane);
		
		//filter button
		filterBtn = new JButton("Filter");
		filterBtn.setActionCommand("filter");
		filterBtn.setBounds(679, 154, 93, 23);
		getContentPane().add(filterBtn);
	
		//packet content table
		model = new DefaultTableModel();
		table = new JTable(model);
		names = new Vector();
		String[] name = {"No.", "Time", "Source", 
				"Destination", "Protocol", "Length", "Info"};
		for(int i = 0; i < name.length; i++) {
			names.add(name[i]);
		}
		row = new Vector();
		data = new Vector();
		model.setDataVector(data, names);
	
		//add a scroll bar to the content area
		scrollPane = new JScrollPane(table);
		scrollPane.setBounds(13, 190, 793, 225);
		getContentPane().add(scrollPane);

		//add a scroll pane to the device list area
		JScrollPane scrollPane_1 = new JScrollPane(deviceText);
		scrollPane_1.setBounds(10, 25, 357, 119);
		getContentPane().add(scrollPane_1);
		
		//start button
		startBtn = new JButton("Start");
		startBtn.setActionCommand("start");
		startBtn.setBounds(212, 427, 93, 23);
		getContentPane().add(startBtn);
		
		history = new JTextPane();
		history.setBounds(377, 25, 146, 119);
		getContentPane().add(history);
		
		packetClear = new JButton("Clear");
		packetClear.setActionCommand("clearPacket");
		packetClear.setBounds(343, 427, 93, 23);
		getContentPane().add(packetClear);
		
		historyClear = new JButton("Clear");
		historyClear.setActionCommand("clearHistory");
		historyClear.setBounds(473, 154, 93, 23);
		getContentPane().add(historyClear);
		
		informationPane = new JTextPane();
		informationPane.setBounds(10, 460, 327, 119);
		getContentPane().add(informationPane);
		
		JScrollPane scrollPane_2 = new JScrollPane(history);
		scrollPane_2.setBounds(377, 25, 267, 119);
		getContentPane().add(scrollPane_2);
		
		statisticBtn = new JButton("Statistics");
		this.statisticBtn.setActionCommand("statistics");
		statisticBtn.setBounds(710, 611, 93, 23);
		getContentPane().add(statisticBtn);
		
		JButton cancelChoose = new JButton("Cancel Choose");
		cancelChoose.setActionCommand("cancelChoose");
		cancelChoose.addActionListener(this);
		cancelChoose.setBounds(463, 427, 129, 23);
		getContentPane().add(cancelChoose);
		
		filterChoose = new JComboBox();
		filterChoose.addItem(FilterKey.Protocol);
		filterChoose.addItem(FilterKey.Dst);
		filterChoose.addItem(FilterKey.Src);
		filterChoose.setBounds(654, 25, 149, 23);
		getContentPane().add(filterChoose);
		
		showHostName = new JButton("Show HostName");
		showHostName.setActionCommand("showHostName");
		showHostName.setBounds(347, 475, 141, 23);
		getContentPane().add(showHostName);
		
		extraInfo = new JTextPane();
		extraInfo.setBounds(498, 460, 303, 117);
		getContentPane().add(extraInfo);
		
		setAlert = new JButton("Set Alert");
		this.setAlert.setActionCommand("alert");
		setAlert.setBounds(347, 530, 141, 23);
		getContentPane().add(setAlert);
		
		searchSamePacket = new JButton("Search Same");
		this.searchSamePacket.setActionCommand("searchSame");
		searchSamePacket.setBounds(348, 500, 140, 23);
		getContentPane().add(searchSamePacket);
		
		clearAlert = new JButton("Clear Alert");
		this.clearAlert.setActionCommand("clearAlert");
		clearAlert.setBounds(347, 556, 141, 23);
		getContentPane().add(clearAlert);
	}
	
	//setters
	//set button listeners
	/**
	 * This method is to add listeners for the components in the GUI
	 * @param listener the listener class
	 */
	public void setActionListener(ActionListener listener) {
		searchDeviceBtn.addActionListener(listener);
		chooseDeviceBtn.addActionListener(listener);
		filterBtn.addActionListener(listener);
		startBtn.addActionListener(listener);
		packetClear.addActionListener(listener);
		historyClear.addActionListener(listener);
		this.showHostName.addActionListener(listener);
		this.statisticBtn.addActionListener(listener);
		this.searchSamePacket.addActionListener(listener);
		this.setAlert.addActionListener(listener);
		this.clearAlert.addActionListener(listener);
	}
	
	//getters
	public int getDeviceIndex() {
		return deviceText.getSelectedIndex();
	}
	
	public FilterKey getFilterKey() {
		return (FilterKey)this.filterChoose.getSelectedItem();
	}
	
	public int getSelectedPacket() {
		this.choosePacket = true;
		int r= table.getSelectedRow();
		informationPane.setText((String)table.getValueAt(r, 6));
		return r;
	}
	
	/**
	 * This method is to get the filter key word
	 * @return the filter key word.
	 */
	public String getFilterKeyWord() {
		return this.filterPane.getText();
	}
	
	/**
	 * This method is to show the devices' name in the GUI
	 * @param devices the list of devices' name
	 */
	public void showDevice(String[] devices) {
		deviceText.setSelectedIndex(-1);
		for (int i = 0; i < devices.length; i++) {
			deviceText.setListData(devices);
		}
	}
	
	/**
	 * This method is to show packets in the table.
	 * @param packet packets list
	 */
	public void showPacket(IPPacket[] packet) {
		for(IPPacket p: packet) {
			this.showPacket(p);
		}
	}
	
	/**
	 * This method is to show packets in the table.
	 * @param p packet
	 */
	public void showPacket(IPPacket p) {
		//get the id
		row.add(this.id++);
		//get the time
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss"); 
		row.add(formatter.format(date));
		//get the source
		row.add(p.src_ip.toString().replace("/", ""));
		//get the destination
		row.add(p.dst_ip.toString().replace("/", ""));
		//get the protocol
		String protocol ="";
		switch(p.protocol)
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
		row.add(protocol);
		//get the length
		row.add(p.length);
		//get the info
		row.add(p.toString());
		model.addRow(row);
		this.row = new Vector();
		//make sure the scroll get to the bottom
		scrollToBottom(table);
	}
	
	/**
	 * This method is to show packets in the table.
	 * @param ip the packet
	 */
	public void showPacket(IPPacketer ip) {
		Vector v = ip.toVector();
		v.set(0, this.id++);
		model.addRow(v);
		scrollToBottom(table);
	}
	
	
	/**
	 * This method is to show packets in the table.
	 * @param v packet information as a vector
	 */
	public void showPacket(Vector v) {
		v.set(0, this.id++);
		model.addRow(v);
		scrollToBottom(table);
	}
	
	public void showExtraInfo(String s) {
		this.extraInfo.setText(s);
	}
	
	/**
	 * This method is to make the scrollpane's scroll to the bottom.
	 * @param listener
	 */
	private void scrollToBottom(JTable table) {
		if(!this.choosePacket) {
			//make sure the scroll get to the bottom
			int rowCount = table.getRowCount();
		    table.getSelectionModel().setSelectionInterval(rowCount - 1, rowCount - 1);
		    Rectangle rect = table.getCellRect(rowCount - 1, 0, true);
		    table.updateUI();
		    table.scrollRectToVisible(rect);
		}
	}
	
	/**
	 * This method is to add a mouse listener for the 
	 * packet table so that when a packet is chosen, the information
	 * will show in the information area.
	 * @param l the mouse listener class
	 */
	public void setMouseListener(MouseListener l){
		//add a listener to packet table, so that
		//when a packet is chosen, its information
		//will be shown in the information area
		table.addMouseListener(l);
	}
	
	/**
	 * This method is to change the start button status so that after
	 * click the start button, it will turn into a stop button.
	 */
	public void changeStartBtn() {
		startBtn.setText("Stop");
		startBtn.setActionCommand("stop");
	}
	
	/**
	 * This method is to change the stop button status so that after
	 * click the stop button, it will turn into a stop button.
	 */
	public void changeStopBtn() {
		startBtn.setText("Start");
		startBtn.setActionCommand("start");
	}
	
	/**
	 * This method is to clear the packet table
	 */
	public void clearPacket() {
		System.out.println("clear packet");
		model.setRowCount(0);
	}
	
	/**
	 * This method is to clear the history field
	 */
	public void clearHistory() {
		System.out.println("clear the history");
		historyText = null;
		history.setText(historyText);
	}
	
	/**
	 * This method is to add a histroy action
	 * @param s the string that will be added to the history
	 */
	public void addHistory(String s) {
		if(historyText == null) {
			historyText = s;
		}else{
			historyText = historyText + "\n" + s;
		}
		history.setText(historyText);
	}
	
    @Override
    public void actionPerformed(ActionEvent e) {
    	this.choosePacket = false;
    }
}