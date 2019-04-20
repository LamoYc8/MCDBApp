package app.gui.panels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import app.core.Application;
import app.gui.PatientSelector;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.patient.Rapport;

public class UpdateMedicalReportPanel extends AbstractMedicalReportPanel {
	public UpdateMedicalReportPanel(Rapport rapport)
	{
		if (PatientSelector.getInstance().getPatientRecord() == null)
		{
			call(new SelectMedicalRecordPanel(this), "You should select a patient before modify administrative data");
		}
		else
		{
			setTitle("Modify a medical report");
			createView();
			
			txtIntitule.setText(rapport.getIntitule());
			txtRapport.setText(rapport.getRapport());
			txtRemarque.setText(rapport.getPrescription());
			try
			{
			    datePicker.setDate(Application.sqlDateFormat.parse(rapport.getDate()));
			}
			catch (ParseException e) {
			    e.printStackTrace();
			}
			
			btnSauveBrouillon.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					rapport.setValide("0");
					saveData(rapport);
				}
			});
			
			btnSauveDef.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					if (getValidator().validateFields()) 
					{
						rapport.setValide("1");
						saveData(rapport);
						Window.switchPanel(new ViewMedicalReportPanel(rapport));
					}
					else
					{
						Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
					}
				}
			});
		}
		
	}
	
	private void saveData(Rapport rapport)
	{
		rapport.setIntitule(txtIntitule.getText());
		rapport.setRapport(txtRapport.getText());
		rapport.setPrescription(txtRemarque.getText());
		if (datePicker.getDate() != null) rapport.setDate(Application.sqlDateFormat.format(datePicker.getDate()));
		
		try 
		{
			rapport.save();
			PatientSelector.getInstance().getPatientRecord().reload();
			Toast.makeText(Window.getInstance(), "The medical report has been saved", Style.SUCCESS).display();
		} 
		catch (SQLException ex) 
		{
			ex.printStackTrace();
			Toast.makeText(Window.getInstance(), "There is an error when you update your data", Style.ERROR).display();
		}
	}	
}
