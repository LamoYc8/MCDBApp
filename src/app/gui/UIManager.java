package app.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.UnsupportedLookAndFeelException;

import org.jdesktop.swingx.plaf.basic.CalendarHeaderHandler;
import org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler;

import app.core.Configuration;

/**
 * Classe pour gérer l'apparence générale de l'application.
 */
public class UIManager
{
	/**
	 * Initialise l'apparence générale de l'application.
	 */
	public static void initialize()
	{
		System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext", "true");
        
        javax.swing.UIManager.put("Label.foreground", new Color(76, 80, 90));
        javax.swing.UIManager.put("Label.font", new Font("DejaVu Sans", Font.BOLD, 16));
        javax.swing.UIManager.put("TextField.font", new Font("Liberation Sans", Font.PLAIN, 16));
        
        javax.swing.UIManager.put(CalendarHeaderHandler.uiControllerID, "org.jdesktop.swingx.plaf.basic.SpinningCalendarHeaderHandler");
        javax.swing.UIManager.put(SpinningCalendarHeaderHandler.ARROWS_SURROUND_MONTH, Boolean.TRUE);
        
        try {
        	javax.swing.UIManager.setLookAndFeel(Configuration.get("LookAndFeel"));
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
}
