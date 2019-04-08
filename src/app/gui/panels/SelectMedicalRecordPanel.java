package app.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import app.core.Application;
import app.gui.PatientSelector;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.gui.components.SelectionTable;
import app.patient.AdmData;
import app.patient.Dossier;
import app.user.Utilisateur;

public class SelectMedicalRecordPanel extends AbstractCallablePanel
{
	private SelectionTable table;
	private final SelectButtonListener selectionListener = new SelectButtonListener();
	
	public SelectMedicalRecordPanel()
	{
		setTitle("Sélectionner un dossier médical");
	    content.setLayout(new BorderLayout());
	    
	    JLabel lblMedicalRecords = new JLabel("Dossiers médicaux");
	    lblMedicalRecords.setBorder(new EmptyBorder(0, 0, 10, 0));
	    content.add(lblMedicalRecords, BorderLayout.NORTH);
	    
	    PatientTableModel tableModel = new PatientTableModel(Dossier.loadAll());
	    table = new SelectionTable(tableModel);
	    table.setFillsViewportHeight(true);
	    table.setActionListener(selectionListener);
	    
	    SwingUtilities.invokeLater(new Runnable()
	    {
	    	@Override
	    	public void run() {
	    		Dossier selectedPatient = PatientSelector.getInstance().getPatientRecord();
			    if (selectedPatient != null)
			    {
			    	int selectedRow = tableModel.getIndexFromPatientID(selectedPatient.getNumDossier());
			    	if (selectedRow != -1) table.setRowSelectionInterval(selectedRow, selectedRow);
			    }
	    	}
	    });
	    
	    content.add(table.getTableWithFilters());
	    
	    JPanel panel = new JPanel();
	    panel.setOpaque(false);
	    content.add(panel, BorderLayout.SOUTH);
	    
	    JButton btnSelect = new JButton("Sélectionner");
	    btnSelect.setPreferredSize(new Dimension(130, 40));
	    btnSelect.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
	    btnSelect.addActionListener(selectionListener);
	    panel.add(btnSelect);
	    
	    if (Utilisateur.getDefaultUser().getRole().equals("Secretaire")) 
	    {
	    	JButton btnCreate = new JButton("Nouveau dossier");
		    btnCreate.setPreferredSize(new Dimension(170, 40));
		    btnCreate.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		    btnCreate.addActionListener(new CreateButtonListener());
		    panel.add(btnCreate);
	    }
	}
	
	
	public SelectMedicalRecordPanel(AbstractPanel caller)
	{
		this();
		setCaller(caller);
	}
	
	class PatientTableModel extends DefaultTableModel
	{
		private final int COLUMN_COUNT = 7; 
		private Dossier[] medicalRecords;
		
		public PatientTableModel(Dossier rowData[])
		{
			this.medicalRecords = rowData;
			
			for (String columnName : new String[] { "N° Dossier", "Prénom", "Nom", "Date de naissance", "Code postal", "Ville", "Pays" })
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
			}
			throw new IndexOutOfBoundsException();
		}
		
		public Dossier getMedicalRecord(int row) {
			return medicalRecords[row];
		}
		
		public int getIndexFromPatientID(String patientID)
		{
			for (int i = 0; i < COLUMN_COUNT; i++)
			{
				if (medicalRecords[i].getNumDossier().equals(patientID))
				{
					return i;
				}
			}
			return -1;
		}
	}
	
	class SelectButtonListener extends AbstractAction
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int selectedRow = table.getSelectedRow();
			if (selectedRow != -1)
			{
				Dossier medicalRecord = ((PatientTableModel) table.getModel()).getMedicalRecord(table.convertRowIndexToModel(selectedRow));
				PatientSelector.getInstance().setPatient(medicalRecord);
				Toast.makeText(Window.getInstance(), "Le patient <b>" + medicalRecord.getData().getPrenom() + " " + medicalRecord.getData().getNom() + " (" + medicalRecord.getData().getNumDossier() + ")</b> a été sélectionné", Style.SUCCESS).display();
				
				if (getCaller() != null) returnCall();
			}
			else
			{
				Toast.makeText(Window.getInstance(), "Aucun patient sélectionné", Style.ERROR).display();
			}
		}
	}
	
	class CreateButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			Window.switchPanel(new CreateMedicalRecordPanel());
		}
	}
}
