package app.gui.panels;

import java.awt.Component;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import app.gui.GUIUtility;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.gui.components.SelectionTable;
import app.gui.components.TitleLabel;
import app.gui.panels.util.Validator;
import app.user.Utilisateur;
import net.miginfocom.swing.MigLayout;

public class ManageUsersPanel extends AbstractPanel
{
	private SelectionTable selectionTable;
	private JTextField txtFirstName;
	private JComboBox<String> cmbRole;
	private JTextField txtLastName;
	private JTextField txtIdentifier;
	private JPasswordField txtPassword;
	private JPasswordField txtPasswordConfirm;
	private JButton btnNew;
	private JButton btnEdit;
	private JButton btnRemove;
	private JButton btnPermission;
	
	private Utilisateur selectedUser;
	
	public ManageUsersPanel()
	{
		setTitle("User's interface");
		content.setLayout(new MigLayout("", "[100px:n:250px,grow,fill]2%[100px:n:250px,grow,fill]2%[100px:n:200px,grow,fill][0px:n:300px,grow,fill]", "[][]20px[][][32px,fill][][32px,fill][32px,grow][40px,fill]"));
		
		JLabel lblTable = new JLabel("Select a user");
		content.add(lblTable, "cell 0 0");
		
		selectionTable = new SelectionTable(new UserTableModel(Utilisateur.allUti()));
	    selectionTable.setFillsViewportHeight(true);
	    selectionTable.getSelectionModel().addListSelectionListener(new SelectionListener());
		content.add(selectionTable.getTableWithFilters(), "cell 0 1 4 1");
		
		TitleLabel titleCivility = new TitleLabel("MODIFY USER'S INFORMATION");
		content.add(titleCivility, "cell 0 2 3 1");
		
		JLabel lblFirstName = new JLabel("Firstname");
		content.add(lblFirstName, "cell 0 3");
		
		JLabel lblLastName = new JLabel("Lastname");
		content.add(lblLastName, "flowy,cell 1 3");
		
		JLabel lblRole = new JLabel("Role");
		content.add(lblRole, "cell 2 3");
		
		txtFirstName = new JTextField();
		lblFirstName.setLabelFor(txtFirstName);
		txtFirstName.setEnabled(false);
		content.add(txtFirstName, "cell 0 4");
		
		txtLastName = new JTextField();
		lblLastName.setLabelFor(txtLastName);
		txtLastName.setEnabled(false);
		content.add(txtLastName, "cell 1 4");
		
		cmbRole = new JComboBox<String>();
		lblRole.setLabelFor(cmbRole);
		cmbRole.addItem("Secretary");
		cmbRole.addItem("Doctor");
		cmbRole.addItem("Administrator");
		cmbRole.setEnabled(false);
		content.add(cmbRole, "cell 2 4");
		
		JLabel lblIdentifier = new JLabel("Address mail");
		content.add(lblIdentifier, "cell 0 5");
		
		JLabel lblPassword = new JLabel("Password");
		content.add(lblPassword, "cell 1 5");
		
		JLabel lblPasswordConfirm = new JLabel("Confirmation");
		content.add(lblPasswordConfirm, "cell 2 5");
		
		txtIdentifier = new JTextField();
		lblIdentifier.setLabelFor(txtIdentifier);
		txtIdentifier.setEnabled(false);
		content.add(txtIdentifier, "cell 0 6");
		
		txtPassword = new JPasswordField();
		lblPassword.setLabelFor(txtPassword);
		txtPassword.setEnabled(false);
		content.add(txtPassword, "cell 1 6");
		
		txtPasswordConfirm = new JPasswordField();
		lblPasswordConfirm.setLabelFor(txtPasswordConfirm);
		txtPasswordConfirm.setEnabled(false);
		content.add(txtPasswordConfirm, "cell 2 6");
		
		JPanel panButtons = new JPanel();
		panButtons.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panButtons.getLayout();
		flowLayout.setVgap(0);
		content.add(panButtons, "cell 0 8 3 1,grow");
		
		btnNew = new JButton("New");
		btnNew.setPreferredSize(new Dimension(120, 40));
		btnNew.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		btnNew.addActionListener((ActionEvent e)->Window.switchPanel(new CreateUserPanel()));
		panButtons.add(btnNew);
		
		btnEdit = new JButton("Modify");
		btnEdit.setPreferredSize(new Dimension(120, 40));
		btnEdit.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		btnEdit.setEnabled(false);
		btnEdit.addActionListener(new EditButtonListener());
		panButtons.add(btnEdit);
		
		btnRemove = new JButton("Delete");
		btnRemove.setPreferredSize(new Dimension(120, 40));
		btnRemove.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		btnRemove.setEnabled(false);
		btnRemove.addActionListener(new RemoveButtonListener());
		panButtons.add(btnRemove);
		
		btnPermission = new JButton("Access");
		btnPermission.setPreferredSize(new Dimension(120, 40));
		btnPermission.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		btnPermission.setEnabled(false);
		btnPermission.addActionListener((ActionEvent e) -> Window.switchPanel(new UpdateUserPermissionsPanel(((UserTableModel) (selectionTable.getModel())).getUser(selectionTable.convertRowIndexToModel(selectionTable.getSelectedRow())))));
		
		content.add(btnPermission, "cell 3 8");
		
		setValidator(new CustomValidator());
		getValidator().setRequiredFields(new Component[] { lblFirstName, lblLastName, lblRole, lblIdentifier });
	}
	
	class UserTableModel extends DefaultTableModel
	{
		private static final int COLUMN_COUNT = 4;
		private Utilisateur[] users;
		
		public UserTableModel(Utilisateur[] users)
		{
			this.users = users;
			for (String columnName : new String[] { "Address mail", "Firstname", "Lastname", "Role", "" })
			{
				addColumn(columnName);
			}
		}
		
		@Override
		public int getRowCount() {
			if (users != null) return users.length;
			else return 0;
		}
		
		@Override
		public int getColumnCount() {
			return COLUMN_COUNT;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			Utilisateur user = users[row];
			switch (column)
			{
				case 0: return user.getLogin();
				case 1: return user.getPrenom();
				case 2: return user.getNom();
				case 3: return user.getRole();
			}
			throw new ArrayIndexOutOfBoundsException();
		}
		
		private Utilisateur getUser(int row)
		{
			return users[row];
		}
		
		private Utilisateur setUser(int row, Utilisateur user)
		{
			return users[row];
		}
	}
	
	class SelectionListener implements ListSelectionListener
	{
		private boolean isSelected;
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if (!isSelected)
			{
				txtFirstName.setEnabled(true);
				txtLastName.setEnabled(true);
				cmbRole.setEnabled(true);
				txtIdentifier.setEnabled(true);
				txtPassword.setEnabled(true);
				txtPasswordConfirm.setEnabled(true);
				btnEdit.setEnabled(true);
				btnRemove.setEnabled(true);
				btnPermission.setEnabled(true);
			}
			
			if (!e.getValueIsAdjusting())
			{
				isSelected = true;
				
				int selectedRow = selectionTable.getSelectedRow();
				if (selectedRow != -1)
				{
					selectedUser = ((UserTableModel) selectionTable.getModel()).getUser(selectedRow);
				
					txtFirstName.setText(selectedUser.getPrenom());
					txtLastName.setText(selectedUser.getNom());
					txtIdentifier.setText(selectedUser.getLogin());
					cmbRole.setSelectedItem(selectedUser.getRole());
				}
			}
		}
	}
	
	class EditButtonListener implements ActionListener
	{	
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (getValidator().validateFields())
			{
				selectedUser.setPrenom(txtFirstName.getText());
				selectedUser.setNom(txtLastName.getText());
				selectedUser.setRole((String) cmbRole.getSelectedItem());
				selectedUser.setLogin((String) txtIdentifier.getText());
				if (!GUIUtility.isEmpty(txtPassword)) selectedUser.setPassword(String.valueOf(txtPassword.getPassword()));
				
				try
				{
					selectedUser.updateUtil();
					selectionTable.repaint();
					
					Toast.makeText(Window.getInstance(), "User's data has been updated", Style.SUCCESS).display();
				}
				catch (SQLException ex)
				{
					Toast.makeText(Window.getInstance(), "There is an error occurred when you update your data", Style.ERROR).display();
				}
			}
			else
			{
				Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
			}
		}
	}
	
	class RemoveButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try {
				int selectedRow = selectionTable.convertRowIndexToModel(selectionTable.getSelectedRow());
				UserTableModel tableModel = (UserTableModel) selectionTable.getModel();
				
				Utilisateur.deleteUtil(tableModel.getUser(selectedRow).getId());
				tableModel.removeRow(selectedRow);
				Window.switchPanel(new ManageUsersPanel()); // plus le temps...
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	class CustomValidator extends Validator
	{	
		@Override
		public boolean validateFields() {
			if (!super.validateFields()) return false;
			
			String newPassword = String.valueOf(txtPassword.getPassword()), newPasswordConfirm = String.valueOf(txtPasswordConfirm.getPassword());
			
			if (newPassword == null && newPasswordConfirm != null)
			{
				errorMessage = getMissingFieldErrorMessage("Password");
				return false;
			}
			if (newPassword != null && newPasswordConfirm == null)
			{
				errorMessage = getMissingFieldErrorMessage("Confirmation");
				return false;
			}
			if (!newPassword.equals(newPasswordConfirm))
			{
				errorMessage = "The password is not correct";
				return false;
			}
			return true;
		}
	}
}
