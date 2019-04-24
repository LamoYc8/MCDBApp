package app.core;

import java.sql.SQLException;

import app.gui.ConnectionWindow;
import app.gui.UIManager;
import app.gui.Window;
import app.user.Utilisateur;


public class Main
{
	private static ConnectionWindow connectWindow;
	private static Window window;

	
    public static void main(String[] args)
    {
    	Configuration.initialize();
        UIManager.initialize();
       
        connectWindow = new ConnectionWindow();
        window = new Window();
    }
    

    public static void exit()
    {
        try
        {
			Utilisateur.disconnectionU();
		}
        catch (SQLException e)
        {
        	e.printStackTrace();
        }
        
        System.exit(0);
    }
    

    public static void restart()
    {
    	try
        {
			Utilisateur.disconnectionU();
		}
        catch (SQLException e)
        {
        	e.printStackTrace();
        }
    	
    	window.setVisible(false);
    	connectWindow = new ConnectionWindow();
        window = new Window();
    }
}
