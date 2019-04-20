package app.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import app.core.Application;
import app.gui.PatientSelector;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.gui.panels.CreateMedicalRecordPanel.CreateButtonListener;
import app.patient.Rapport;

public class CreateMedicalReportPanel extends AbstractMedicalReportPanel{

	public CreateMedicalReportPanel()
	{
		setTitle("Create a medical report");
		createView();
		btnSauveBrouillon.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (getValidator().validateFields())
				{
					saveData();
				}
				else
				{
					Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
				}

				}
			});
		
		btnSauveDef.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (getValidator().validateFields()) 
				{
					saveData();
				}
				else
				{
					Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
				}
			}
		});
	}

	private void saveData()
	{
		Rapport rapport = new Rapport(PatientSelector.getInstance().getPatientRecord().getNumDossier());
		rapport.setIntitule(txtIntitule.getText());
		rapport.setRapport(txtRapport.getText());
		rapport.setPrescription(txtRemarque.getText());
		rapport.setDate(Application.sqlDateFormat.format(datePicker.getDate()));

		try 
		{
			rapport.save();
			PatientSelector.getInstance().getPatientRecord().reload();
			Toast.makeText(Window.getInstance(), "The medical report is successfully saved", Style.SUCCESS).display();
		} 
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			Toast.makeText(Window.getInstance(), "The is an error occurred when you update your data", Style.ERROR).display();
		}
	}	
}