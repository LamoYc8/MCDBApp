package app.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class Toast extends JDialog {
	public enum Style { INFO, SUCCESS, ERROR };
	
	private static Toast previousToast;
	
	public static final int DEFAULT_DURATION = 5000;
	public static final Color ERROR_RED = new Color(190, 17, 0);
	public static final Color SUCCESS_GREEN = new Color(22, 127, 57);
	public static final Color INFO_BLUE = new Color(1, 122, 204);
	
	private final int WIDTH = 600;
	private final int HEIGHT = 48;
	private final int DISTANCE_FROM_PARENT_TOP = 130;
	private final int DISTANCE_FROM_PARENT_RIGHT = 20;
	private final int WINDOW_RADIUS = 4;
	
	private JFrame owner;
	private String text;
	private String styleText;
	private int duration;
	private Color styleColor = new Color(44, 44, 44);
	private Color foregroundColor = new Color(216, 216, 216);
	private Font textFont = new Font("Cantarell", Font.PLAIN, 16);
	private Font styleFont = new Font("Cantarell", Font.BOLD, 14);
    
    public Toast(JFrame owner){
    	super(owner);
    	this.owner = owner;
    }

    private void createGUI(){
        setLayout(new GridLayout());
        
        setAlwaysOnTop(true);
        setUndecorated(true);
        setFocusableWindowState(false);
        setModalityType(ModalityType.MODELESS);
        setSize(WIDTH, HEIGHT);
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), WINDOW_RADIUS, WINDOW_RADIUS));
        setLocation(getToastLocation());
        setBackground(new Color(242, 241, 237));
        
        JPanel contentPane = (JPanel) getContentPane(); 
        contentPane.setBackground(new Color(242, 241, 237));
        
        JPanel content = new JPanel(new BorderLayout());
        content.setBackground(new Color(44, 44, 44));
        //content.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        contentPane.add(content);
        
        JLabel label = new JLabel(text);
        label.setForeground(foregroundColor);
        label.setFont(textFont);
        label.setBackground(new Color(44, 44, 44));
        label.setOpaque(true);
        label.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 10));
        content.add(label, BorderLayout.CENTER);
        
        JPanel panToastStyle = new JPanel(new GridBagLayout());
        panToastStyle.setOpaque(false);
        panToastStyle.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
        content.add(panToastStyle, BorderLayout.WEST);
        
        JLabel lblToastStyle = new JLabel(styleText);
        lblToastStyle.setBackground(styleColor);
        lblToastStyle.setForeground(Color.WHITE);
        lblToastStyle.setFont(styleFont);
        lblToastStyle.setOpaque(true);
        lblToastStyle.setBorder(BorderFactory.createEmptyBorder(3, 4, 3, 4));
        panToastStyle.add(lblToastStyle);
        
        JButton btnClose = new JButton("Close");
        btnClose.setBackground(new Color(13, 99, 156));
        btnClose.setForeground(Color.WHITE);
        btnClose.setFont(textFont);
        btnClose.setOpaque(true);
        btnClose.setBorderPainted(false);
        btnClose.addActionListener((ActionEvent e) -> setVisible(false));
        content.add(btnClose, BorderLayout.EAST);
    }
	
	private Point getToastLocation(){
		Point ownerLoc = owner.getLocation();		
		int x = (int) (ownerLoc.getX() + ((owner.getWidth() - WIDTH - DISTANCE_FROM_PARENT_RIGHT))); 
		int y = (int) (ownerLoc.getY() + DISTANCE_FROM_PARENT_TOP);
		return new Point(x, y);
	}
	
	public void setText(String text){
		this.text = text;
	}
	
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	public static Toast makeText(JFrame owner, String text){
		return makeText(owner, text, DEFAULT_DURATION);
	}
	
	public static Toast makeText(JFrame owner, String text, Style style){
		return makeText(owner, text, DEFAULT_DURATION, style);
	}
    
    public static Toast makeText(JFrame owner, String text, int duration){
    	return makeText(owner, text, duration, Style.INFO);
    }
    
    public static Toast makeText(JFrame owner, String text, int duration, Style style){
    	Toast toast = new Toast(owner);
    	toast.text = "<html>" + text + "</html>";
    	toast.duration = duration;
    	
    	if (previousToast != null) previousToast.setVisible(false);;
    	previousToast = toast;
    	
    	if (style == Style.SUCCESS)
    	{    		
    		toast.styleColor = SUCCESS_GREEN;
    		toast.styleText = "Success";
    	}
    	if (style == Style.ERROR)
    	{    		
    		toast.styleColor = ERROR_RED;
    		toast.styleText = "Error";
    	}
    	if (style == Style.INFO)
    	{    		
    		toast.styleColor = INFO_BLUE;
    		toast.styleText = "Info";
    	}
    	
    	return toast;
    }
        
    public void display(){
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
            	try
            	{
            		createGUI();	
            		setVisible(true);
	                Thread.sleep(duration);
	                setVisible(false);
            	}
            	catch(Exception e)
            	{
            		e.printStackTrace();
            	}
            }
        }).start();
    }
}