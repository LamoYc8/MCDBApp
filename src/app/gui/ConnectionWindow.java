package app.gui;

import static app.core.Application.appName;
import static app.core.Application.assetsPath;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import app.user.Utilisateur;

public class ConnectionWindow extends JFrame
{
	private boolean connectionGranted;
	
	private JTextField txtLogin;
	private JPasswordField txtPassword;
	
	private JLabel lblStatus;
	private JLabel lblStatus2;
	
	public ConnectionWindow()
	{	
		setTitle(appName + " - Connexion");
		setSize(500, 350);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ImageIcon appIcon = new ImageIcon(assetsPath + "/appIcon.png");
        setIconImage(appIcon.getImage());
		
		JPanel contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitle = new JLabel("Patient Resources Everywhere");
		lblTitle.setFont(new Font("Dialog", Font.BOLD, 18));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setBounds(12, 12, 476, 32);
		contentPane.add(lblTitle);
		
		JLabel lblConnection = new JLabel("Connection");
		lblConnection.setFont(new Font("Dialog", Font.BOLD, 14));
		lblConnection.setHorizontalAlignment(SwingConstants.CENTER);
		lblConnection.setBounds(12, 44, 476, 24);
		contentPane.add(lblConnection);
		ConnectButtonListener connectionListener = new ConnectButtonListener();

		txtLogin = new JTextField("gjack@mail.fr");
		txtLogin.setBounds(110, 100, 280, 32);
		contentPane.add(txtLogin);
		txtLogin.setColumns(10);
		txtLogin.addActionListener(connectionListener);
		
		txtPassword = new JPasswordField("gjack");
		txtPassword.setColumns(10);
		txtPassword.setBounds(110, 151, 280, 32);
		contentPane.add(txtPassword);
		txtPassword.addActionListener(connectionListener);
		
		lblStatus = new JLabel("...");
		lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus.setFont(new Font("Dialog", Font.BOLD, 14));
		lblStatus.setBounds(12, 199, 476, 24);
		contentPane.add(lblStatus);
		
		lblStatus2 = new JLabel("");
		lblStatus2.setHorizontalAlignment(SwingConstants.CENTER);
		lblStatus2.setFont(new Font("Dialog", Font.BOLD, 14));
		lblStatus2.setBounds(12, 224, 476, 24);
		contentPane.add(lblStatus2);
		
		JButton btnConnection = new JButton("Connection");
		btnConnection.addActionListener(connectionListener);
		
		btnConnection.setFont(new Font("Dialog", Font.BOLD, 14));
		btnConnection.setBounds(184, 260, 130, 39);
		contentPane.add(btnConnection);
		
		setVisible(true);
		
		while (!connectionGranted)
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	class ConnectButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent arg0) {
			try {
				if (Utilisateur.getDefaultUser().connexionU(txtLogin.getText(), String.valueOf(txtPassword.getPassword())))
				{
					setVisible(false);
					connectionGranted = true;
				}
				else
				{
					lblStatus.setText("connection impossible");
					lblStatus2.setText("Usernamer or Password is not correct");
					txtLogin.requestFocus();
				}
			} catch (SQLException | NullPointerException e) {
				lblStatus.setText("connection error");
				lblStatus2.setText("database error");
			}
		}
	}
}
