package app.gui.components;

import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JRadioButton;

public class TransparentRadioButton extends JRadioButton
{

    public TransparentRadioButton()
    {
        super();
        initButton();
    }

    public TransparentRadioButton(String text)
    {
        super(text);
        initButton();
    }

    public TransparentRadioButton(ImageIcon icon)
    {
        super(icon);

        initButton();
    }

    public TransparentRadioButton(ImageIcon defaultIcon, ImageIcon secondaryIcon)
    {
        this(defaultIcon);

        setRolloverIcon(secondaryIcon);
        setPressedIcon(secondaryIcon);
        setSelectedIcon(secondaryIcon);
    }
    
    private void initButton()
    {
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusable(false);
        setFocusPainted(false);
        setOpaque(false);
        setBorder(null);

        setMargin(new Insets(0, -2, 0, 0));
    }
}
