package app.gui.panels;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import org.jdesktop.swingx.JXDatePicker;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;

public class CreateMedicalExamPanel extends AbstractPanel
{
	private static final JFileChooser fileChooser = new JFileChooser();
	private JTextField txtFile;
	
	public CreateMedicalExamPanel()
	{
		setTitle("Join a exam");
		
		content.setLayout(new MigLayout("", "[100px:n:250px,grow][]2%[300:n:600px,grow]", "[][32px,fill][][32px,fill][40px][][100px:n:300px,grow][32px,grow][40px]"));
		
		JLabel lblTitle = new JLabel("Title");
		content.add(lblTitle, "cell 0 0");
		
		JLabel lblDate = new JLabel("Date");
		content.add(lblDate, "cell 2 0");
		
		JTextField txtTitle = new JTextField();
		content.add(txtTitle, "cell 0 1 2 1,growx");
		txtTitle.setColumns(10);
		
		JXDatePicker datePicker = new JXDatePicker();
		content.add(datePicker, "flowx,cell 2 1");
		
		JLabel lblFile = new JLabel("File");
		content.add(lblFile, "cell 0 2");
		
		JLabel lblType = new JLabel("Type");
		content.add(lblType, "cell 2 2");
		
		txtFile = new JTextField();
		content.add(txtFile, "cell 0 3,growx");
		txtFile.setColumns(10);
		
		JButton btnFile = new JButton("...");
		btnFile.addActionListener(new FileButtonListener());
		content.add(btnFile, "flowx,cell 1 3");
		
		JComboBox<String> cmbType = new JComboBox<String>();
		cmbType.addItem("Medical image");
		cmbType.addItem("Exam of lab");
		cmbType.addItem("Data of treatment");
		cmbType.addItem("Other");
		cmbType.setMaximumSize(new Dimension(200, 32767));
		content.add(cmbType, "cell 2 3,growx");
		
		JLabel lblDescription = new JLabel("Description");
		content.add(lblDescription, "cell 0 5 3 1");
		
		JTextArea textArea = new JTextArea();
		content.add(textArea, "cell 0 6 3 1,grow");
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		content.add(panel, "cell 0 8 3 1,grow");
		
		JButton btnSave = new JButton("Registration");
		btnSave.setPreferredSize(new Dimension(120, 40));
		btnSave.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		panel.add(btnSave);
		
		JLabel lblFonctionnalitNonFinalise = new JLabel("Functions for future work");
		lblFonctionnalitNonFinalise.setForeground(Color.RED);
		content.add(lblFonctionnalitNonFinalise, "cell 2 1");
		
		SwingUtilities.invokeLater(()->txtTitle.requestFocus());
	}
	
	class FileButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			int returnVal = fileChooser.showOpenDialog(CreateMedicalExamPanel.this);
			
			 if (returnVal == JFileChooser.APPROVE_OPTION) {
	            //txtFile.setText(fileChooser.getFileName());
	        } else {
	            
	        }
		}
	}

	class SaveButtonListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			
		}
	}
}
