package app.gui.panels;

import javax.swing.JPanel;

import app.user.Utilisateur;

public class HomePanel {
	public JPanel getPanel()
	{
		switch (Utilisateur.getDefaultUser().getRole())
		{
			case "Medecin":
			case "Secretaire":
				return new SelectMedicalRecordPanel();
			case "Admin":
				return new ManageUsersPanel();
		}
		return new JPanel();
	}
}
