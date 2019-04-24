package app.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JPanel;

import app.user.Utilisateur;

import javax.swing.JLabel;

import java.awt.CardLayout;
import javax.swing.BoxLayout;

import javax.swing.BorderFactory;

import java.awt.event.MouseListener;
import java.lang.reflect.InvocationTargetException;
import java.awt.event.MouseEvent;


class NavigationMenu extends JPanel
{
    private final CardLayout layout;
    private final String[] sideMenuItems;

    private NavigationMenuItem[][] navigationMenuItems = {
        {

        },
        {
            new NavigationMenuItem("Create an account", "create a new user", "app.gui.panels.CreateUserPanel", "Administrator"),
            new NavigationMenuItem("Users' behaviors", "Modify or delete existed account", "app.gui.panels.ManageUsersPanel", "Administrator"),
            new NavigationMenuItem("Hospital's service", "Affect hospital's services to a user", null, "Administrator")
        },
        {
            new NavigationMenuItem("Selection a document", "Selection a medical document for start some treatments", "app.gui.panels.SelectMedicalRecordPanel", "Doctor|Secretary"),
            new NavigationMenuItem("Administrator's data", "Consult or modify selected patient's data ", "app.gui.panels.UpdateMedicalRecordPanel", "Secretary"),
            new NavigationMenuItem("Medical Reports", "Consult, add or modify a medical report", "app.gui.panels.SelectMedicalReportPanel", "Doctor"),
            new NavigationMenuItem("Join a exam", "Join a exam of medical document", "app.gui.panels.CreateMedicalExamPanel", "Doctor"),
            new NavigationMenuItem("Hospital's service", "Affect hospital's services to a patient", null, "Doctor|Secretary")
        },
        {
            new NavigationMenuItem("Create a hospital", "Create a new hospital information", null, "Administrator"),
            new NavigationMenuItem("Hospital's behaviors", "Consult, modify or delete data about hospital", null, "Administrator")
        },
        {
            new NavigationMenuItem("Configuration", "Modify the configuration", null),
            new NavigationMenuItem("Icons", "Modify icons", null),
            new NavigationMenuItem("Look and Feel", "Modify application's appearance'", null),
        }
    };

    public NavigationMenu(String[] sideMenuItems)
    {
        layout = new CardLayout();
        this.sideMenuItems = sideMenuItems;

        setLayout(layout);
        setBackground(new Color(243,243,243));
        setBorder(BorderFactory.createMatteBorder(0,0,0,1, new Color(191,194,196)));

        addCardPanels();
    }


    private void addCardPanels()
    {
    	boolean isEmptyMenu;
    	Utilisateur user = Utilisateur.getDefaultUser(); 
    	
        for (int i = 0; i < sideMenuItems.length; i++)
        {
        	isEmptyMenu = true;
        	
            JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
            panel.setBorder(BorderFactory.createEmptyBorder(5,0,5,0));
            panel.setOpaque(false);

            for (NavigationMenuItem item : navigationMenuItems[i])
            {
            	if (item.isAvailable(user))
            	{
            		panel.add(item);
            		isEmptyMenu = false;
            	}
            }
            if (!isEmptyMenu) add(panel, sideMenuItems[i]);
        }
    }


    public void displayPanel(String name)
    {
        layout.show(this, name);
    }


    class NavigationMenuItem extends JPanel implements MouseListener
    {
        private final String title;
        private final String description;
        private final String panelClassname; // panel associé au bouton de menu
        
        private String access;

        private Color backgroundDefault;
        private final Color backgroundSelected;

        public NavigationMenuItem(String title, String description, String panelClassname)
        {
            this.title = title;
            this.description = description;
            this.panelClassname = panelClassname;

            backgroundDefault = new Color(240,240,240);
            backgroundSelected = new Color(220,220,220);

            if (panelClassname == null) backgroundDefault = new Color(248, 194, 185);

            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
            setBackground(backgroundDefault);
            addMouseListener(this);

            setMinimumSize(new Dimension(1000, 60));
            setMaximumSize(new Dimension(1000, 60));

            JLabel titleLabel = new JLabel(title);
            titleLabel.setLayout(new BoxLayout(titleLabel, BoxLayout.LINE_AXIS));
            titleLabel.setBorder(BorderFactory.createEmptyBorder(0,0,3,0));
            titleLabel.setFont(new Font("Liberation Sans", Font.BOLD, 16));

            JLabel descriptionLabel = new JLabel(description);
            descriptionLabel.setLayout(new BoxLayout(descriptionLabel, BoxLayout.X_AXIS));
            descriptionLabel.setFont(new Font("Liberation Sans", Font.PLAIN, 14));
            descriptionLabel.setMinimumSize(new Dimension(310, getHeight()));

            add(titleLabel);
            add(descriptionLabel);
        }
        

        public NavigationMenuItem(String title, String description, String panelClassname, String access)
        {
        	this(title, description, panelClassname);
        	this.access = access;
        }
        

        public boolean isAvailable(Utilisateur user)
        {
        	return access == null || access.contains(user.getRole());
        }

        public void mouseClicked(MouseEvent event)
        {

        }

        public void mouseEntered(MouseEvent event)
        {
            setBackground(backgroundSelected);
        }

        public void mouseExited(MouseEvent event)
        {
            setBackground(backgroundDefault);
        }

        public void mousePressed(MouseEvent event)
        {

        }

        public void mouseReleased(MouseEvent event)
        {
            if (panelClassname != null)
            {
				try {
					Window.switchPanel((JPanel) Class.forName(panelClassname).getConstructor().newInstance());
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException
						| ClassNotFoundException e) {
					e.printStackTrace();
				}
            }
        }
    }
}
