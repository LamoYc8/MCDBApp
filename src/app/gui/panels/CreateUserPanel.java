package app.gui.panels;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.gui.panels.util.Validator;
import app.user.Utilisateur;
import net.miginfocom.swing.MigLayout;

public class CreateUserPanel extends AbstractPanel
{
	private JTextField txtIdentifier;
	private JTextField txtFirstName;
	private JTextField txtLastName;
	private JButton btnSubmit;
	private JPanel panel;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordConfirm;
	private JComboBox<String> cmbRole;
	
	public CreateUserPanel()
	{
		setTitle("Create a user's account");
		content.setLayout(new MigLayout("", "[100px:n:250px,grow,fill]2%[100px:n:250px,grow,fill]2%[100px:n:250px,grow]", "[][32px,fill]2%[][32px,fill][][32px,fill][32,grow][40px,fill]"));
		
		JLabel lblFirstName = new JLabel("Firstname");
		content.add(lblFirstName, "cell 0 0");
		
		JLabel lblLastName = new JLabel("Lastname");
		content.add(lblLastName, "cell 1 0");
		
		JLabel lblRole = new JLabel("Role");
		content.add(lblRole, "cell 2 0");
		
		txtFirstName = new JTextField();
		lblFirstName.setLabelFor(txtFirstName);
		content.add(txtFirstName, "cell 0 1");
		
		txtLastName = new JTextField();
		lblLastName.setLabelFor(txtLastName);
		content.add(txtLastName, "cell 1 1");
		
		cmbRole = new JComboBox<String>();
		cmbRole.setPreferredSize(new Dimension(180, 32));
		lblRole.setLabelFor(cmbRole);
		cmbRole.addItem("Secretary");
		cmbRole.addItem("Doctor");
		cmbRole.addItem("Administrator");
		content.add(cmbRole, "cell 2 1");
		
		JLabel lblIdentifier = new JLabel("Address mail");
		content.add(lblIdentifier, "cell 0 2");
		
		JLabel lblPassword = new JLabel("Password");
		content.add(lblPassword, "cell 1 2");
		
		JLabel lblPasswordConfirm = new JLabel("Confirmation");
		content.add(lblPasswordConfirm, "cell 2 2");
		
		txtIdentifier = new JTextField();
		lblIdentifier.setLabelFor(txtIdentifier);
		content.add(txtIdentifier, "cell 0 3");
		
		txtPassword = new JPasswordField();
		lblPassword.setLabelFor(txtPassword);
		content.add(txtPassword, "cell 1 3");
		
		txtPasswordConfirm = new JPasswordField();
		lblPasswordConfirm.setLabelFor(txtPasswordConfirm);
		content.add(txtPasswordConfirm, "cell 2 3,growx");
		
		panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		content.add(panel, "cell 0 7 2 1,grow");
		
		btnSubmit = new JButton("Submit");
		btnSubmit.setPreferredSize(new Dimension(120, 40));
		btnSubmit.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		panel.add(btnSubmit);
		btnSubmit.addActionListener(new SubmitButtonListener());
		
		setValidator(new CustomValidator());
		getValidator().setRequiredFieldsAuto(this);
		
		SwingUtilities.invokeLater(()->txtFirstName.requestFocus()); 
	}
	
	class SubmitButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (getValidator().validateFields())
			{
				try {
					Utilisateur.createUtil(txtLastName.getText(), txtFirstName.getText(), txtIdentifier.getText(), String.valueOf(txtPassword.getPassword()), (String) cmbRole.getSelectedItem(), 1);
					Toast.makeText(Window.getInstance(), "User's account is created", Style.SUCCESS).display();
					Window.switchPanel(new ManageUsersPanel());
				} catch (SQLException ex) {
					ex.printStackTrace();
					Toast.makeText(Window.getInstance(), "The is an error occurred when you update your data", Style.ERROR).display();
				} catch (IllegalArgumentException ex) {
					Toast.makeText(Window.getInstance(), "User's name is not available", Style.ERROR).display();
				}
			}
			else
			{
				Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
			}
		}
	}
	
	class CustomValidator extends Validator
	{
		@Override
		public boolean validateFields() {
			if (!super.validateFields()) return false;
			
			if (!String.valueOf(txtPassword.getPassword()).equals(String.valueOf(txtPasswordConfirm.getPassword())))
			{
				errorMessage = "Password that you entered is not correct";
				return false;
			}
				
			return true;
		}
	}
}