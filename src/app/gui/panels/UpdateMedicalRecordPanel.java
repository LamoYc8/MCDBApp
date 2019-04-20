package app.gui.panels;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

import javax.swing.SwingUtilities;

import app.core.Application;
import app.gui.PatientSelector;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.patient.AdmData;
import app.patient.Dossier;
import app.user.Utilisateur;

public class UpdateMedicalRecordPanel extends AbstractMedicalRecordPanel
{	
	public UpdateMedicalRecordPanel()
	{	
		if (PatientSelector.getInstance().getPatientRecord() == null)
		{
			call(new SelectMedicalRecordPanel(this), "Vous devez sélectionner un patient avant de pouvoir modifier ses données administratives");
		}
		else
		{
			AdmData admData = PatientSelector.getInstance().getPatientRecord().getData();
			setTitle("Données administratives");
			createView();
			
			txtIdentifier.setEditable(false);
			
			txtIdentifier.setText(admData.getNumDossier());
			cmbCivility.setSelectedItem(admData.getCiv());
			txtFirstName.setText(admData.getPrenom());
			txtLastName.setText(admData.getNom());
			txtBirthPlace.setText(admData.getLieuNaiss());
			try {
			    dteBirthDate.setDate(Application.sqlDateFormat.parse(admData.getDateNaiss()));
			} catch (ParseException e) {
			    e.printStackTrace();
			}
			txtAddress.setText(admData.getAdresse());
			txtZipCode.setText(admData.getCodePostal());
			txtCity.setText(admData.getVille());
			cmbCountry.setSelectedItem(admData.getPays());
			txtPhoneNumber.setText(admData.getNum());
			txtMailAddress.setText(admData.getMail());
			
			btnSubmit.addActionListener(new SaveButtonListener());
			
			SwingUtilities.invokeLater(()->cmbCivility.requestFocus());
		}
	}
	
	class SaveButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (getValidator().validateFields())
			{
				try {
					Dossier patient = getFormData();
					
					patient.getData().save();
					patient.load(patient.getData().getNumDossier());
					PatientSelector.getInstance().setPatient(patient);
					
					Toast.makeText(Window.getInstance(), "Les données du patient ont été mises à jour", Style.SUCCESS).display();
				} catch (SQLException ex) {
					ex.printStackTrace();
					Toast.makeText(Window.getInstance(), "Une erreur est survenue lors de la mise à jour des données", Style.ERROR).display();
				}
			}
			else
			{
				Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
			}
		}
	}
}
