package app.gui.panels;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import app.core.Application;
import app.gui.PatientSelector;
import app.gui.Window;
import app.gui.components.SelectionTable;
import app.patient.Rapport;

public class SelectMedicalReportPanel extends AbstractPanel
{	
	private ReportTableModel tableModel;
	private SelectionTable selectionTable;
	
	private JButton btnNew;
	private JButton btnInteract;
	private JButton btnRemove;
	
	private Rapport selectedReport;
	private boolean valid;
	
	private final ActionListener editListener = (ActionEvent e) -> Window.switchPanel(new UpdateMedicalReportPanel(selectedReport));
	private final ActionListener viewListener = (ActionEvent e) -> Window.switchPanel(new ViewMedicalReportPanel(selectedReport));
	
	public SelectMedicalReportPanel()
	{
		if (PatientSelector.getInstance().getPatientRecord() == null)
		{
			call(new SelectMedicalRecordPanel(this), "You should select a patient before you process medical reports");
		}
		else
		{
			setTitle("Select a medical report");
		    content.setLayout(new BorderLayout());
		    
		    JLabel lblMedicalRecords = new JLabel("Medical Reports");
		    lblMedicalRecords.setBorder(new EmptyBorder(0, 0, 10, 0));
		    content.add(lblMedicalRecords, BorderLayout.NORTH);
		    
		    ReportTableModel tableModel = new ReportTableModel(PatientSelector.getInstance().getPatientRecord().getRapports());
		    selectionTable = new SelectionTable(tableModel);
		    selectionTable.setFillsViewportHeight(true);
		    selectionTable.getSelectionModel().addListSelectionListener(new SelectionListener());
		    content.add(selectionTable.getTableWithFilters());
		    
		    JPanel panel = new JPanel();
		    panel.setOpaque(false);
		    content.add(panel, BorderLayout.SOUTH);
		    
		    btnNew = new JButton("New");
		    btnNew.setPreferredSize(new Dimension(120, 40));
		    btnNew.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		    btnNew.addActionListener((ActionEvent e) -> Window.switchPanel(new CreateMedicalReportPanel()));
		    panel.add(btnNew);
		    
		    btnInteract = new JButton("...");
		    btnInteract.setPreferredSize(new Dimension(120, 40));
		    btnInteract.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		    btnInteract.setEnabled(false);
		    panel.add(btnInteract);
		    
		    btnRemove = new JButton("Delete");
		    btnRemove.setPreferredSize(new Dimension(120, 40));
		    btnRemove.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		    btnRemove.addActionListener((ActionEvent e) -> {});
		    btnRemove.setEnabled(false);
		    panel.add(btnRemove);
		}
	}
	
	class ReportTableModel extends DefaultTableModel
	{
		private final int COLUMN_COUNT = 5;
		private Rapport[] patientReports;
		
		public ReportTableModel(Rapport rowData[]) {
			this.patientReports = rowData;
			
			for (String columnName : new String[] { "N° Report", "Title", "Date", "Abstract", "Status" })
			{
				addColumn(columnName);
			}
		}
		
		@Override
		public int getRowCount() {
			if (patientReports != null) return patientReports.length;
			else return 0;
		}
		
		@Override
		public int getColumnCount() {
			return COLUMN_COUNT;
		}
		
		@Override
		public Object getValueAt(int row, int column) {
			Rapport patientReport = patientReports[row];
			switch (column)
			{
				case 0: return patientReport.getId();
				case 1: return patientReport.getIntitule();
				case 2:
					try
					{
						return Application.defaultDateFormat.format(Application.sqlDateFormat.parse(patientReport.getDate()));
					}
					catch (ParseException e)
					{
						return null;
					}
				case 3:
				{
					String reportText = patientReport.getRapport().replaceAll("\n", " ");
					if (reportText.length() > 100) reportText = reportText.substring(0, 100) + "..."; 
					return reportText;
				}
				case 4: return (patientReport.isValide()) ? "Effect" : "Draft";
			}
			throw new IndexOutOfBoundsException();
		}
		
		public Rapport getReport(int row) {
			return patientReports[row];
		}
		
		public int getIndexFromPatientID(String patientID)
		{
			for (int i = 0; i < COLUMN_COUNT; i++)
			{
				if (patientReports[i].getNumDossier().equals(new Integer(patientID).toString()))
				{
					return i;
				}
			}
			return -1;
		}
	}
	
	class SelectionListener implements ListSelectionListener
	{
		@Override
		public void valueChanged(ListSelectionEvent e)
		{
			if (!e.getValueIsAdjusting())
			{
				int selectedRow = selectionTable.getSelectedRow();
				if (selectedRow != -1)
				{
					selectedReport = ((ReportTableModel) selectionTable.getModel()).getReport(selectedRow);
					if (selectedReport.isValide())
					{
						btnInteract.setText("Consult");
						if (!valid)
						{
							btnInteract.removeActionListener(editListener);
							btnInteract.addActionListener(viewListener);
						}
						
						btnRemove.setEnabled(false);
						valid = true;
					}
					else
					{
						btnInteract.setText("Modify");
						if (!valid)
						{
							btnInteract.removeActionListener(viewListener);
							btnInteract.addActionListener(editListener);
						}
						btnRemove.setEnabled(true);
						valid = false;
					}
					btnInteract.setEnabled(true);
				}
			}
		}
	}
}
