package app.gui.panels;

import javax.swing.JPanel;

import app.user.Utilisateur;

public class HomePanel {
	public JPanel getPanel()
	{
		switch (Utilisateur.getDefaultUser().getRole())
		{
			case "Doctor":
			case "Secretary":
				return new SelectMedicalRecordPanel();
			case "Administrator":
				return new ManageUsersPanel();
		}
		return new JPanel();
	}
}
