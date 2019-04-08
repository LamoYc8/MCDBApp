package app.gui.panels.util;

import java.awt.Component;
import java.awt.Container;
import java.util.ArrayList;

import javax.swing.JLabel;

import app.gui.GUIUtility;

public class Validator
{
	private Component[] requiredFields;
	protected String errorMessage;
	
	/**
	 * Retourne le message d'erreur éventuel à la suite d'un appel à la méthode validateFields().
	 */
	public String getErrorMessage()
	{
		return errorMessage;
	}
	
	/**
	 * Définis les champs de texte obligatoires.
	 */
	public void setRequiredFields(Component[] components) {
		for (Component component : components)
		{
			if (component instanceof JLabel)
			{
				((JLabel) component).setText(((JLabel) component).getText() + "*");
			}
		}
		requiredFields = components;
	}
	
	/**
	 * Définis les champs de texte obligatoires automatiquement (tous).
	 */
	public void setRequiredFieldsAuto(Container caller)
	{
		ArrayList<Component> components = new ArrayList<Component>();
		setRequiredFieldsAuto(caller, components);
		
		Component[] componentsArray = new Component[components.size()];
		componentsArray = components.toArray(componentsArray);
		setRequiredFields(componentsArray);
	}
	
	/**
	 * Définis les champs de texte obligatoires automatiquement (tous les JLabel avec labelFor).
	 */
	private void setRequiredFieldsAuto(Container caller, ArrayList<Component> components)
	{
		for (Component component : caller.getComponents())
		{
			if (component instanceof Container)
			{
				setRequiredFieldsAuto((Container) component, components);
			}
			if (component instanceof JLabel && ((JLabel) component).getLabelFor() != null)
			{
				components.add(component);
			}
		}
	}
	
	/**
	 * Vérifie que l'ensemble des champs requis sont remplis.
	 */
	public boolean validateFields() {
		errorMessage = null;
		if (requiredFields != null)
		{
			for (Component component : requiredFields)
			{
				String componentName = null;
				if (component instanceof JLabel)
				{
					componentName = ((JLabel) component).getText();
					componentName = (componentName.substring(0, componentName.length() - 1));
					
					component = ((JLabel) component).getLabelFor();
					if (component == null) throw new IllegalStateException("label '" + componentName + "' has no component associated");
				}
				else if (component.getName() != null)
				{
					componentName = component.getName();
				}
				if (GUIUtility.isEmpty(component))
				{
					if (componentName != null)
					{
						errorMessage = getMissingFieldErrorMessage(componentName);
					}
					else
					{
						errorMessage = "Veuillez remplir tous les champs requis";
					}
					return false;
				}
			}
		}
		return true;
	}
	
	public static String getMissingFieldErrorMessage(String fieldName)
	{
		return "Le champ <b>'" + fieldName + "'</b> est requis";
	}
}
