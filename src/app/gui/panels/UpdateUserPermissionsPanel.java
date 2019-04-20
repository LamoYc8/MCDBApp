package app.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import app.core.Application;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.gui.components.SelectionTable;
import app.patient.AdmData;
import app.patient.Dossier;
import app.user.Utilisateur;

public class UpdateUserPermissionsPanel extends AbstractPanel
{
	private Utilisateur user;
	
	private SelectionTable selectionTable;
	private JButton btnPermission;
	
	public UpdateUserPermissionsPanel(Utilisateur user)
	{
		this.user = user;
		content.setLayout(new BorderLayout());
		
		setTitle("Gérer les permissions de l'utilisateur");
		content.setLayout(new BorderLayout());
		
		PermissionButtonListener permissionButtonListener = new PermissionButtonListener();
		
		JLabel lblPatientList = new JLabel("Liste des patients");
		content.add(lblPatientList);
		
		selectionTable = new SelectionTable(new PatientTableModel(Dossier.loadAll()));
		selectionTable.setActionListener(permissionButtonListener);
		selectionTable.getSelectionModel().addListSelectionListener(new SelectionListener());
		content.add(selectionTable.getTableWithFilters());
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		content.add(panel, BorderLayout.SOUTH);
		
		btnPermission = new JButton("...");
		btnPermission.addActionListener(permissionButtonListener);
		btnPermission.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		btnPermission.setPreferredSize(new Dimension(150, 40));
		btnPermission.setEnabled(false);
		panel.add(btnPermission);
	}
	
	private void updatePermissionButtonText()
	{
		Dossier selectedMedicalRecord = ((PatientTableModel) selectionTable.getModel()).getSelectedMedicalRecord();
		if (selectedMedicalRecord == null)
		{
			btnPermission.setText("...");
			btnPermission.setEnabled(false);
			return;
		}
			
		if (selectedMedicalRecord.isAssignedTo(user))
		{
			btnPermission.setText("Interdire");
		}
		else
		{
			btnPermission.setText("Autoriser");
		}
		btnPermission.setEnabled(true);
	}
	
	class PatientTableModel extends DefaultTableModel
	{
		private final int COLUMN_COUNT = 8;
		
		private Dossier[] medicalRecords;
		
		public PatientTableModel(Dossier rowData[])
		{
			this.medicalRecords = rowData;
			
			for (String columnName : new String[] { "N° Dossier", "Prénom", "Nom", "Date de naissance", "Code postal", "Ville", "Pays", "Permission" })
			{
				addColumn(columnName);
			}
		}
		
		@Override
		public int getRowCount() {
			if (medicalRecords != null) return medicalRecords.length;
			else return 0;
		}
		
		@Override
		public int getColumnCount() {
			return COLUMN_COUNT;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			AdmData rowData = medicalRecords[row].getData();
			switch (column)
			{
				case 0: return rowData.getNumDossier();
				case 1: return rowData.getPrenom();
				case 2: return rowData.getNom();
				case 3: 
					try
					{
						return Application.defaultDateFormat.format(Application.sqlDateFormat.parse(rowData.getDateNaiss()));
					}
					catch (ParseException e)
					{
						
					}
				case 4: return rowData.getCodePostal();
				case 5: return rowData.getVille();
				case 6: return rowData.getPays();
				case 7: return medicalRecords[row].isAssignedTo(user) ? "Autorisée" : "Refusée";
			}
			throw new IndexOutOfBoundsException();
		}
		
		public Dossier getSelectedMedicalRecord() {
			int selectedRow = selectionTable.convertColumnIndexToModel(selectionTable.getSelectedRow());
			return selectedRow != -1 ? medicalRecords[selectedRow] : null;
		}
		
		public int getIndexFromPatientID(String patientID)
		{
			for (int i = 0; i < COLUMN_COUNT; i++)
			{
				if (medicalRecords[i].getNumDossier().equals(new Integer(patientID).toString()))
				{
					return i;
				}
			}
			return -1;
		}
	}
	
	class SelectionListener implements ListSelectionListener
	{
		private boolean isSelected;
		
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if (!e.getValueIsAdjusting())
			{
				updatePermissionButtonText();
			}
		}
	}
	
	class PermissionButtonListener extends AbstractAction
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			Dossier selectedMedicalRecord = ((PatientTableModel) selectionTable.getModel()).getSelectedMedicalRecord();
			if (selectedMedicalRecord == null) {
				Toast.makeText(Window.getInstance(), "Veuillez sélectionner un élément du tableau", Style.ERROR).display();
				return;
			}
			
			try
			{
				if (selectedMedicalRecord.isAssignedTo(user))
				{
					user.deleteDossier(selectedMedicalRecord.getNumDossier());
				}
				else
				{
					user.addDossier(selectedMedicalRecord.getNumDossier());
				}
				selectionTable.repaint();
				updatePermissionButtonText();
				
			}
			catch (SQLException ex)
			{
				ex.printStackTrace();
				Toast.makeText(Window.getInstance(), "Une erreur est survenue lors de la mise à jour des données", Style.ERROR).display();
			}
		}
	}
}
