package app.gui;

import static app.core.Application.assetsPath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import app.core.Configuration;
import app.core.Main;
import app.gui.components.TransparentRadioButton;
import app.gui.panels.HomePanel;
import app.user.Utilisateur;

/**
 * Menu des boutons sur le côté gauche de la fenêtre.
 * Permet de sélectionner une fonctionnalité de l'application.
 * Contient le panel des boutons (buttonsMenu) et le menu de navigation
 * (NavigationMenu).
 */
class SideMenu extends JPanel
{
    private JPanel buttonsMenu;
    private NavigationMenu navMenu; // menu de navigation (voir NavigationMenu)

    private Dimension defaultSize;
    private Dimension extendedSize;

    private ButtonGroup buttonGroup;
    private TransparentRadioButton selectedButton; // bouton sélectionné

    private static final String[] menuItems = {"home", "user", "medicalRecord", "hospital", "settings"};

    public SideMenu()
    {
        buttonsMenu = new JPanel();
        navMenu = new NavigationMenu(menuItems);

        defaultSize = new Dimension(60, getHeight());
        extendedSize = new Dimension(310, getHeight());

        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(defaultSize));

        buttonsMenu.setPreferredSize(defaultSize);
        buttonsMenu.setBackground(new Color(Configuration.getInt("SideMenu.Background")));
        ((FlowLayout) buttonsMenu.getLayout()).setVgap(0);

        navMenu.setVisible(false);

        addButtons();
        add(buttonsMenu, BorderLayout.WEST);
        add(navMenu, BorderLayout.CENTER);
    }

    /**
     * Initialise et ajoute les boutons de navigation au menu.
     */
    private void addButtons()
    {
    	Utilisateur user = Utilisateur.getDefaultUser();
    	ButtonListener buttonListener = new ButtonListener();
        buttonGroup = new ButtonGroup();
        
        for (int i = 0; i < menuItems.length; i++)
        {
        	if ((menuItems[i].equals("user") || menuItems[i].equals("hospital")) && !user.getRole().equals("Admin")) continue;
        	if (menuItems[i].equals("medicalRecord") && user.getRole().equals("Admin")) continue;
        	
            TransparentRadioButton button = new TransparentRadioButton(
                new ImageIcon(SideMenu.class.getResource(assetsPath + "/icons/buttons/" + menuItems[i] + ".png")),
                new ImageIcon(SideMenu.class.getResource(assetsPath + "/icons/buttons/" + menuItems[i] + "Hover.png"))
            );
            button.setPreferredSize(new Dimension(60,60));
            button.setName(menuItems[i]);
            
            if (i == 0)
            {
            	button.addActionListener(new HomeButtonListener());
            }
            else
            {
            	button.addActionListener(buttonListener);
            	buttonGroup.add(button);
            }

            buttonsMenu.add(button);
        }
    }

    /**
     * Gère les clics sur les boutons du menu.
     */
    class ButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e) {
            TransparentRadioButton pressedButton = (TransparentRadioButton) e.getSource();

            if (pressedButton == selectedButton)
            {
                setPreferredSize(defaultSize);
                navMenu.setVisible(false);
                selectedButton = null;
                buttonGroup.clearSelection();
            }
            else
            {
                setPreferredSize(extendedSize);
                navMenu.setVisible(true);
                selectedButton = pressedButton;
                navMenu.displayPanel(pressedButton.getName());
            }
        }
    }
    
    class HomeButtonListener implements ActionListener
    {
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		((TransparentRadioButton) e.getSource()).setSelected(false);
    		Window.switchPanel(new HomePanel().getPanel());
    	}
    }
}
