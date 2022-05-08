package control;

import java.awt.event.*;
import javax.swing.JFrame;

import jpcap.PacketReceiver;
import view.StatisticsFrame;
import java.util.List;

import model.*;

/**
 * This class is the statistics controller
 * @author Lydia
 *
 */
public class StatisticsController implements ActionListener, MouseListener, Runnable{
	
	StatisticsFrame statisticsFrame;
	Analyser analyser;
	private PacketReceive packetReceive;
	
	//constructor
	public StatisticsController() {
		//create a GUI
		this.statisticsFrame = new StatisticsFrame();
		this.statisticsFrame.setTitle("Statistics");
		this.statisticsFrame.setSize(590,380);
		this.statisticsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.statisticsFrame.setVisible(true);
		this.statisticsFrame.setActionListener(this);
		
		//create a controller
		this.analyser = new Analyser();
		this.analyser.setController(this);
	}
	
	//setters
	public void setPacketReceiver(PacketReceive r) {
		this.packetReceive = r;
		this.packetReceive.setAnalyser(this.analyser);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		switch(e.getActionCommand()) {
		case "start":
			this.analyser.startAnalysis();
			break;
		}
		
	}
	
	@Override
	public void run() {
		while(this.analyser.on) {
			try {
				if(this.analyser.srcAddressBag.getSortedBag() != null) {
					List<AnalyserNode> a = this.analyser.srcAddressBag.getSortedBag();
					List<AnalyserNode> b = this.analyser.dstAddressBag.getSortedBag();
					this.statisticsFrame.showDstStatistics(b);
					this.statisticsFrame.showSrcStatistics(a);
					this.analyser.thread.sleep(3);
					}else {
						this.statisticsFrame.showStatistics();
					}
				
				} catch (InterruptedException e) {
					e.printStackTrace();
					}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {}
	
    @Override
    public void mouseReleased(MouseEvent e) {}
 
    @Override
    public void mousePressed(MouseEvent e) {}
 
    @Override
    public void mouseExited(MouseEvent e) {}
 
    @Override
    public void mouseEntered(MouseEvent e) {}
}
