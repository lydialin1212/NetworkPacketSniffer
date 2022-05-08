package view;

import javax.swing.*;
import java.awt.TextArea;
import java.awt.Color;
import java.awt.Font;

/**
 * This class is a alert GUI.
 * @author Lydia
 *
 */
public class Alert extends JFrame{
	private JTextField txtSame;
	public Alert() {
		getContentPane().setLayout(null);
		
		txtSame = new JTextField();
		txtSame.setFont(new Font("Arial", Font.BOLD, 16));
		txtSame.setText("Same Src Packet Alert!");
		txtSame.setHorizontalAlignment(SwingConstants.CENTER);
		txtSame.setForeground(Color.BLACK);
		txtSame.setBackground(UIManager.getColor("Button.background"));
		txtSame.setBounds(10, 10, 250, 80);
		getContentPane().add(txtSame);
		txtSame.setColumns(10);
	}
}
