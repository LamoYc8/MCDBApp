package app.gui;

import static app.core.Application.appAbbreviation;
import static app.core.Application.appName;
import static app.core.Application.assetsPath;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import org.jdesktop.swingx.border.DropShadowBorder;

import app.core.Configuration;
import app.core.Main;

/**
 * Panel d'en-tête de l'application.
 */
class TopPanel extends JPanel implements Observer
{
    private static TopPanel defaultInstance;

    private String title, shortTitle;
    private JLabel titleLabel;

    private JPanel rightPanel;
    private JPanel spacer;
    private Dimension defaultSize, smallSize, contentDefaultSize, contentSmallSize;

    private Border rightPanelBorder, smallRightPanelBorder;
    
    private JTextField patientIdTextField;

    public TopPanel()
    {
        this.title = appName;
        this.shortTitle = appAbbreviation;

        defaultSize = new Dimension(getWidth(), 88);
        smallSize = new Dimension(getWidth(), 65);
        contentDefaultSize = new Dimension(getWidth(), 82);
        contentSmallSize = new Dimension(getWidth(), 60);

        defaultInstance = this;
        spacer = new JPanel();

        setLayout(new BorderLayout());
        setOpaque(false); // transparence de l'ombre
        
        addContent();
        addShadow();
        updateDynamicComponents();
        
        PatientSelector.getInstance().addObserver(this);
    }

     public static void setTitle(String title, String shortTitle)
     {
         Objects.requireNonNull(title);

         defaultInstance.title = title;
         defaultInstance.shortTitle = shortTitle;
     }

     public static void setTitle(String title)
     {
         setTitle(title, null);
     }


    private void addContent()
    {
        JPanel content = new JPanel();

        content.setLayout(new BorderLayout());
        content.setPreferredSize(contentDefaultSize);
        content.setBackground(new Color(Configuration.getInt("TopPanel.Background")));
        content.setBorder(BorderFactory.createMatteBorder(0,0,2,0, new Color(Configuration.getInt("TopPanel.Border"))));

        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Liberation Sans", Font.BOLD, 30));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0,25,0,0));
        titleLabel.setForeground(new Color(Configuration.getInt("TopPanel.Foreground")));

        initRightPanel();

        content.add(titleLabel, BorderLayout.WEST);
        content.add(rightPanel, BorderLayout.EAST);

        add(content);
    }


    private void initRightPanel()
    {
        rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(320, 32));
        rightPanel.setOpaque(false);
        rightPanelBorder = BorderFactory.createEmptyBorder(18,0,0,0);
        smallRightPanelBorder = BorderFactory.createEmptyBorder(8,0,0,0);

        Color rightPanelComponentsBackgroundColor = new Color(243,243,248);

        patientIdTextField = new JTextField();
        patientIdTextField.setPreferredSize(new Dimension(200,32));
        patientIdTextField.setFont(new Font("Liberation Sans", Font.PLAIN, 18));
        patientIdTextField.setBackground(rightPanelComponentsBackgroundColor);
        patientIdTextField.setEditable(false);
        patientIdTextField.setFocusable(false);

        rightPanel.add(patientIdTextField);

        JButton switchUserButton = new JButton(new ImageIcon(TopPanel.class.getResource(assetsPath + "/icons/buttons/switchUser.png")));
        switchUserButton.addActionListener(new SwitchUserButtonListener());
        switchUserButton.setFocusable(false);
        
        JButton exitButton = new JButton(new ImageIcon(TopPanel.class.getResource(assetsPath + "/icons/buttons/exit.png")));
        exitButton.addActionListener(new ExitButtonListener());
        exitButton.setFocusable(false);

        for (JButton button : new JButton[] {switchUserButton, exitButton})
        {
            button.setPreferredSize(new Dimension(32, 32));
            button.setBackground(rightPanelComponentsBackgroundColor);

            rightPanel.add(button);
        }
    }


    private void addShadow()
    {
        DropShadowBorder shadow = new DropShadowBorder();
        shadow.setShadowColor(new Color(240,240,240));
        shadow.setShadowSize(6);
        shadow.setShowTopShadow(false);
        shadow.setShowLeftShadow(false);
        shadow.setShowRightShadow(false);

        setBorder(shadow);
    }


    public JPanel getSpacer()
    {
        return spacer;
    }


    private void updateDynamicComponents()
    {
        titleLabel.setPreferredSize(new Dimension(getWidth()-310, 80));
        if (getWidth() < 500 && shortTitle != null)
        {
            titleLabel.setText(shortTitle);
        }
        else
        {
            titleLabel.setText(title);
        }
        if (Window.getWindowHeight() < 600)
        {
            setPreferredSize(smallSize);
            spacer.setPreferredSize(contentSmallSize);
            spacer.setSize(contentSmallSize); // on utilise setPreferredSize ET setSize pour corriger bug d'affichage (maximisation de la fenêtre)
            rightPanel.setBorder(smallRightPanelBorder);
        }
        else
        {
            setPreferredSize(defaultSize);
            spacer.setPreferredSize(contentDefaultSize);
            spacer.setSize(contentDefaultSize);
            rightPanel.setBorder(rightPanelBorder);
        }
    }


    public void paintComponent(Graphics g)
    {
        updateDynamicComponents();
        super.paintComponent(g);
    }
    
    @Override
    public void update(Observable o, Object arg)
    {
    	patientIdTextField.setText(((PatientSelector) o).getPatientRecord().getData().getNumDossier());
    }

    class SwitchUserButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0) {
        	new Thread() {
        		public void run() {
        			Main.restart();
        		};
        	}.start();
        }
    }

    class ExitButtonListener implements ActionListener
    {
        public void actionPerformed(ActionEvent arg0) {
            Main.exit();
        }
    }
}
