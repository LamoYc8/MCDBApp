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

/**
 * Menu à l'intérieur du SideMenToasu.
 */
class NavigationMenu extends JPanel
{
    private final CardLayout layout;
    private final String[] sideMenuItems;

    private NavigationMenuItem[][] navigationMenuItems = {
        {
            /*
            new NavigationMenuItem("Accueil", "Page d'accueil de l'application", "app.gui.panels.HomePanel"),
            new NavigationMenuItem("À propos", "Informations sur le produit (version, license d'utilisation...)", null),
            new NavigationMenuItem("Contact", "Soumettre une demande spécifique ou un avis de correction de bug", null)
            */
        },
        {
            new NavigationMenuItem("Créer un compte", "Créer un nouveau compte utilisateur", "app.gui.panels.CreateUserPanel", "Admin"),
            new NavigationMenuItem("Gestion des utilisateurs", "Modifier ou supprimer un compte utilisateur existant", "app.gui.panels.ManageUsersPanel", "Admin"),
            new NavigationMenuItem("Service hospitalier", "Affecter un service hospitalier à un utilisateur", null, "Admin")
        },
        {
            new NavigationMenuItem("Sélectionner un dossier", "Sélectionner un dossier médical pour commencer les traitements", "app.gui.panels.SelectMedicalRecordPanel", "Medecin|Secretaire"),
            new NavigationMenuItem("Données administratives", "Consulter ou modifier les données administratives du patient sélectionné", "app.gui.panels.UpdateMedicalRecordPanel", "Secretaire"),
            new NavigationMenuItem("Rapports médicaux", "Consulter, ajouter ou modifier un rapport médical", "app.gui.panels.SelectMedicalReportPanel", "Medecin"),
            new NavigationMenuItem("Joindre un examen", "Joindre un examen au dossier médical", "app.gui.panels.CreateMedicalExamPanel", "Medecin"),
            new NavigationMenuItem("Service hospitalier", "Affecter un service hospitalier à un patient", null, "Medecin|Secretaire")
        },
        {
            new NavigationMenuItem("Créer un hôpital", "Créer un nouvel hôpital dans le logiciel", null, "Admin"),
            new NavigationMenuItem("Gestion des hôpitaux", "Consulter, modifier ou supprimer les données concernant les hôpitaux", null, "Admin")
        },
        {
            new NavigationMenuItem("Configuration", "Modifier la configuration du logiciel", null),
            new NavigationMenuItem("Raccourcis clavier", "Modifier les raccourcis claviers", null),
            new NavigationMenuItem("Look and Feel", "Modifier l'apparence générale de l'application'", null),
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

    /**
     * Ajoute les panels gérés par le CardLayout au NavigationMenu.
     */
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

    /**
     * Change le panel affiché par le CardLayout.
     */
    public void displayPanel(String name)
    {
        layout.show(this, name);
    }

    /**
     * Élément du menu de navigation.
     */
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
        
        /**
         * MenuItem avec restriction d'accès selon le rôle de l'utilisateur.
         * access est un String énumérant les rôles acceptés, séparé par le caractère '|'.
         */
        public NavigationMenuItem(String title, String description, String panelClassname, String access)
        {
        	this(title, description, panelClassname);
        	this.access = access;
        }
        
        /**
         * Retourne true si l'utilisateur a accès au MenuItem.
         */
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
