package app.gui;

import static app.core.Application.appName;
import static app.core.Application.assetsPath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import app.core.Main;
import app.gui.panels.HomePanel;

/**
 * Fenêtre principale de l'application.
 * Mets en place l'interface générale de l'application.
 */
public class Window extends JFrame
{
    private static Window defaultInstance;

    private JPanel currentPanel;

    private Container contentPane;
    private JPanel glassPane;

    public Window()
    {
        setTitle(appName);
        setSize(java.awt.Toolkit.getDefaultToolkit().getScreenSize());
        setMinimumSize(new Dimension(700, 500));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new CloseWindowListener());

        defaultInstance = this;

        ImageIcon appIcon = new ImageIcon(Window.class.getResource(assetsPath + "/appIcon.png"));
        setIconImage(appIcon.getImage());
        
        PatientSelector.makeInstance();

        TopPanel topPanel = new TopPanel();
        SideMenu sideMenu = new SideMenu();

        currentPanel = new HomePanel().getPanel();

        contentPane = getContentPane();
        contentPane.setBackground(Color.WHITE);
        contentPane.add(topPanel.getSpacer(), BorderLayout.NORTH);
        contentPane.add(sideMenu, BorderLayout.WEST);
        contentPane.add(currentPanel);


        glassPane = new JPanel(new BorderLayout());
        glassPane.setOpaque(false);
        glassPane.add(topPanel, BorderLayout.NORTH);
        setGlassPane(glassPane);

        glassPane.setVisible(true);
        setVisible(true);
    }
    

    public static Window getInstance()
    {
    	return defaultInstance;
    }


    public static int getWindowWidth()
    {
        return defaultInstance.getWidth();
    }


    public static int getWindowHeight()
    {
        return defaultInstance.getHeight();
    }


    public static void switchPanel(JPanel panel)
    {
        defaultInstance.contentPane.remove(defaultInstance.currentPanel);
        defaultInstance.currentPanel = panel;
        defaultInstance.contentPane.add(panel);
        defaultInstance.contentPane.repaint();
        defaultInstance.revalidate();
    }


    class CloseWindowListener extends WindowAdapter
    {
        public void windowClosing(WindowEvent e)
        {
            e.getWindow().dispose();
            Main.exit();
        }
    }
}
