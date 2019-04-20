package app.gui.panels;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.jdesktop.swingx.JXDatePicker;

import app.gui.PatientSelector;
import app.gui.Toast;
import app.gui.Toast.Style;
import app.gui.Window;
import net.miginfocom.swing.MigLayout;
import java.awt.Dimension;

public abstract class AbstractMedicalReportPanel extends AbstractPanel {
	protected JTextField txtIntitule;
	protected JTextArea txtRapport;
	protected JTextArea txtRemarque;
	protected JXDatePicker datePicker;
	protected JButton btnSauveBrouillon;
	protected JButton btnSauveDef;

	public void createView()
	{
		setTitle("Consult a medical report");
		content.setLayout(new MigLayout("", "[100px:n:250px,grow][100px:n:400px,grow]", "[][32px,fill][][100px:n:300px,fill][][100px:n:200px,grow,fill][32px,grow][40px,fill]"));
		
		JLabel lblIntitule = new JLabel("Title");
		content.add(lblIntitule, "cell 0 0");
			
		JLabel lblDate = new JLabel("Date");
		content.add(lblDate, "cell 1 0");
			
		txtIntitule = new JTextField();
		content.add(txtIntitule, "cell 0 1,growx,aligny top");
		txtIntitule.setColumns(10);
		lblIntitule.setLabelFor(txtIntitule);
			
		datePicker = new JXDatePicker();
		content.add(datePicker, "cell 1 1");
		lblDate.setLabelFor(datePicker);
			
		JLabel lblRapport = new JLabel("Report");
		content.add(lblRapport, "cell 0 2");
			
		txtRapport = new JTextArea();
		content.add(txtRapport, "cell 0 3 2 1,growx,aligny top");
		lblRapport.setLabelFor(txtRapport);
			
		JLabel lblRemarque = new JLabel("Prescription");
		content.add(lblRemarque, "cell 0 4");
			
		txtRemarque = new JTextArea();
		content.add(txtRemarque, "cell 0 5 2 1,growx,aligny top");
			
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		content.add(panel, "cell 0 7 2 1,grow");
			
		btnSauveBrouillon = new JButton("Save as draft");
		btnSauveBrouillon.setPreferredSize(new Dimension(304, 40));
		btnSauveBrouillon.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		panel.add(btnSauveBrouillon);
			
		btnSauveDef = new JButton("Save for sure");
		btnSauveDef.setPreferredSize(new Dimension(260, 40));
		btnSauveDef.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		panel.add(btnSauveDef);
			
		getValidator().setRequiredFieldsAuto(this);
	}
}
