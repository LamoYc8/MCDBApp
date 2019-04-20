package app.gui.panels;
import java.awt.Component;
import java.text.ParseException;

import javax.swing.JPanel;

import app.core.Application;
import app.gui.PatientSelector;
import app.patient.Rapport;

public class ViewMedicalReportPanel extends AbstractMedicalReportPanel {
	public ViewMedicalReportPanel(Rapport rapport)
	{
		if (PatientSelector.getInstance().getPatientRecord() == null)
		{
			call(new SelectMedicalRecordPanel(this), "You should select a patient before modify administrative data");
		}
		else
		{
			setTitle("Consult a medical report");
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
			
			txtIntitule.setEditable(false);
			txtRapport.setEditable(false);
			txtRemarque.setEditable(false);
			datePicker.setEditable(false);
			
			for (Component component : content.getComponents())
			{
				if (component instanceof JPanel)
				{
					content.remove(component);
				}
			}
		}
		
	}
}
