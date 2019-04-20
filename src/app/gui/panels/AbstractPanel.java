package app.gui.panels;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;

import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.panels.util.Validator;
import app.gui.Window;

/**
 * Class de base pour la création de panels.
 */
public abstract class AbstractPanel extends JPanel {
	private final JPanel container;
	protected final JPanel content;

	private JLabel titleLabel;
	private CompoundBorder defaultBorder;
	
	private Validator validator;

	public AbstractPanel() {
		setLayout(new BorderLayout());
		setOpaque(false);

		container = new JPanel(new BorderLayout());
		container.setOpaque(false);

		JPanel containerHolder = new JPanel(new BorderLayout());
		containerHolder.add(container);

		Color background = new Color(242, 241, 237);

		JScrollPane sp = new JScrollPane(container);
		sp.setOpaque(false);
		sp.setBorder(null);
		sp.getViewport().setBackground(background);
		sp.setViewportBorder(BorderFactory.createLineBorder(background, 20));
		sp.getVerticalScrollBar().setUnitIncrement(16);
		sp.setWheelScrollingEnabled(true);
		add(sp);

		content = new JPanel();
		content.setOpaque(false);
		container.add(content);

		defaultBorder = BorderFactory.createCompoundBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30),
			BorderFactory.createLineBorder(new Color(191, 194, 196))
		);

		//updateDynamicComponents();
	}

	/**
	 * Change le titre du panel.
	 */
	public void setTitle(String title) {
		if (titleLabel == null) {
			titleLabel = new JLabel(title);
			titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
			titleLabel.setFont(new Font("Without Liberation", Font.BOLD, 28));
			container.add(titleLabel, BorderLayout.NORTH);
		} else {
			titleLabel.setText(title);
		}
	}

	/**
	 * Modifie les propriétés des composants dynamiques en fonction de la taille
	 * de la fenêtre.
	 */
	public void updateDynamicComponents() {
		if (getWidth() < 1000 || getHeight() < 600) {
			setBorder(null);
		} else {
			setBorder(defaultBorder);
		}
	}
	

	/**
	 * Mets à jour les composants lors d'une mise à jour de la fenêtre.
	 */
	public void paintComponent(Graphics g) {
		//updateDynamicComponents();
		super.paintComponent(g);
	}
	
	/**
	 * Appelle un AbstractCallablePanel qui doit s'afficher avant ce panel.
	 */
	public void call(AbstractCallablePanel panel, String message)
	{
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(Window.getInstance(), message, Style.INFO).display();
				Window.switchPanel(panel);
			}
		});
	}
	
	/**
	 * Renvoie le validator associé à ce panel.
	 */
	public Validator getValidator()
	{
		if (validator == null) validator = new Validator();
		return validator;
	}
	
	/**
	 * Modifie le validator associé à ce panel.
	 */
	public void setValidator(Validator validator)
	{
		this.validator = validator;
	}
}
