package app.gui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.SwingUtilities;

import app.core.Application;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import app.patient.AdmData;
import app.patient.Dossier;
import app.user.Utilisateur;

public class CreateMedicalRecordPanel extends AbstractMedicalRecordPanel
{
	public CreateMedicalRecordPanel()
	{
		setTitle("Create a patient document");
		createView();
		btnSubmit.addActionListener(new CreateButtonListener());
		
		SwingUtilities.invokeLater(()->txtIdentifier.requestFocus());
	}
	
	class CreateButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if (getValidator().validateFields())
			{	
				Dossier patient = getFormData();
				
				try
				{
					patient.create();
					
					Toast.makeText(Window.getInstance(), "The patient '<b>" + txtFirstName.getText() + " " + txtLastName.getText() + " (" + txtIdentifier.getText() + ")" + "'</b> à été crée avec succès", Style.SUCCESS).display();
					Utilisateur.getDefaultUser().addDossier(patient.getData().getNumDossier());
					
					Window.switchPanel(new SelectMedicalRecordPanel());
				}
				catch (SQLException ex)
				{
					Toast.makeText(Window.getInstance(), "There is an error occurred when you create your document", Style.ERROR).display();
					ex.printStackTrace();
				}
				catch (IllegalStateException ex)
				{
					Toast.makeText(Window.getInstance(), "The identifier is not available", Style.ERROR).display();
					ex.printStackTrace();
				}
			}
			else
			{
				Toast.makeText(Window.getInstance(), getValidator().getErrorMessage(), Style.ERROR).display();
			}
		}
	}
}
