package app.gui.components;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TitleLabel extends JPanel {
	public static final Color backgroundColor = new Color(231, 230, 225);
	
	public TitleLabel(String text)
	{
		setOpaque(false);
		setLayout(new BorderLayout());
		setBorder(BorderFactory.createEmptyBorder(20,0,10,0));
		
		JLabel label = new JLabel(text);
		label.setPreferredSize(getSize());
		label.setBackground(backgroundColor);
		label.setOpaque(true);
		label.setBorder(BorderFactory.createEmptyBorder(5,10,5,10));
		add(label);
	}
}
