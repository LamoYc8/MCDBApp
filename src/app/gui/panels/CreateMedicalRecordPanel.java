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
		setTitle("Créer un dossier patient");
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
					
					Toast.makeText(Window.getInstance(), "Les patient '<b>" + txtFirstName.getText() + " " + txtLastName.getText() + " (" + txtIdentifier.getText() + ")" + "'</b> à été crée avec succès", Style.SUCCESS).display();
					Utilisateur.getDefaultUser().addDossier(patient.getData().getNumDossier());
					
					Window.switchPanel(new SelectMedicalRecordPanel());
				}
				catch (SQLException ex)
				{
					Toast.makeText(Window.getInstance(), "Une erreur est survenue lors de la création du dossier", Style.ERROR).display();
					ex.printStackTrace();
				}
				catch (IllegalStateException ex)
				{
					Toast.makeText(Window.getInstance(), "L'identifiant utilisé n'est pas disponible", Style.ERROR).display();
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
