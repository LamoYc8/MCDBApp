package app.gui;

import java.awt.Component;

import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.text.JTextComponent;

import org.jdesktop.swingx.JXDatePicker;


public class GUIUtility {
	public static boolean isEmpty(Component component)
	{
		if (component instanceof JTextComponent)
		{
			return ((JTextComponent) component).getText().trim().equals("");
		}
		if (component instanceof JComboBox)
		{
			return false;
		}
		if (component instanceof JXDatePicker)
		{
			return ((JXDatePicker) component).getDate() == null;
		}
		throw new IllegalArgumentException("unsupported component for method GUIUtility.isEmpty() : " + component.getClass().getSimpleName());
	}
}
