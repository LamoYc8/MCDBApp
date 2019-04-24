package app.gui.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.text.ParseException;
import java.util.regex.Pattern;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.MaskFormatter;

import org.jdesktop.swingx.JXDatePicker;

import app.core.Application;
import app.gui.PatientSelector;
import app.gui.components.TitleLabel;
import app.gui.panels.util.Validator;
import app.patient.AdmData;
import app.patient.Dossier;
import net.miginfocom.swing.MigLayout;

public abstract class AbstractMedicalRecordPanel extends AbstractPanel
{
	protected JTextField txtIdentifier;
	protected JComboBox<String> cmbCivility;
	protected JTextField txtFirstName;
	protected JTextField txtLastName;
	protected JTextField txtBirthPlace;
	protected JXDatePicker dteBirthDate;
	protected JTextField txtAddress;
	protected JFormattedTextField txtZipCode;
	protected JTextField txtCity;
	protected JComboBox<String> cmbCountry;
	protected JFormattedTextField txtPhoneNumber;
	protected JTextField txtMailAddress;
	protected JButton btnSubmit;
	
	public void createView()
	{
		content.setLayout(new MigLayout("", "[100px:n:250px]2%[100px:n:250px]2%[100px:n:250px,grow]", "[][32px,fill][][][32px,fill][][32px,fill][][32px,fill][][][32px,fill][][32px,fill][][][32px,fill][32px,grow][40px,fill]"));
		
		JLabel lblIdentifier = new JLabel("Identification");
		content.add(lblIdentifier, "cell 0 0 2 1");
		
		txtIdentifier = new JTextField();
		lblIdentifier.setLabelFor(txtIdentifier);
		content.add(txtIdentifier, "cell 0 1 2 1,growx");
		
		TitleLabel titleCivility = new TitleLabel("Citizen");
		content.add(titleCivility, "cell 0 2 3 1,growx");
		
		JLabel lblCivility = new JLabel("Citizen");
		content.add(lblCivility, "cell 0 3");
		
		cmbCivility = new JComboBox<String>();
		lblCivility.setLabelFor(cmbCivility);
		cmbCivility.setMaximumSize(new Dimension(200, 32767));
		cmbCivility.addItem("Female");
		cmbCivility.addItem("Male");
		content.add(cmbCivility, "cell 0 4,growx");
		
		JLabel lblLastName = new JLabel("First_Name");
		content.add(lblLastName, "cell 0 5");
		
		JLabel lblFirstName = new JLabel("Last_name");
		content.add(lblFirstName, "cell 1 5");
		
		txtFirstName = new JTextField();
		lblLastName.setLabelFor(txtFirstName);
		content.add(txtFirstName, "cell 0 6,pushx ,growx 150");
		
		txtLastName = new JTextField();
		lblFirstName.setLabelFor(txtLastName);
		content.add(txtLastName, "cell 1 6,pushx ,growx");
		
		JLabel lblBirthPlace = new JLabel("Birth place");
		content.add(lblBirthPlace, "cell 0 7");
		
		JLabel lblBirthDate = new JLabel("Birthday");
		content.add(lblBirthDate, "cell 1 7");
		
		txtBirthPlace = new JTextField();
		lblBirthPlace.setLabelFor(txtBirthPlace);
		content.add(txtBirthPlace, "cell 0 8,pushx ,growx");
		
		dteBirthDate = new JXDatePicker();
		dteBirthDate.getMonthView().setZoomable(true);
		lblBirthDate.setLabelFor(dteBirthDate);
		content.add(dteBirthDate, "cell 1 8");
		
		TitleLabel titleAddress = new TitleLabel("ADDRESS");
		content.add(titleAddress, "cell 0 9 3 1,growx");
		
		JLabel lblAddress = new JLabel("Address");
		content.add(lblAddress, "cell 0 10 2 1");
		
		txtAddress = new JTextField();
		lblAddress.setLabelFor(txtAddress);
		content.add(txtAddress, "cell 0 11 2 1,growx");
		
		JLabel lblZipCode = new JLabel("Zip-Code");
		content.add(lblZipCode, "cell 0 12");
		
		JLabel lblCity = new JLabel("City");
		content.add(lblCity, "cell 1 12");
		
		JLabel lblCountry = new JLabel("Country");
		content.add(lblCountry, "cell 2 12");
		
		try {
			txtZipCode = new JFormattedTextField(new MaskFormatter("## ###"));
			lblZipCode.setLabelFor(txtZipCode);
			content.add(txtZipCode, "cell 0 13,growx");
		} catch (ParseException e) {
			
		}
		
		txtCity = new JTextField();
		lblCity.setLabelFor(txtCity);
		content.add(txtCity, "cell 1 13,pushx ,growx 150");
		
		cmbCountry = new JComboBox<String>();
		lblCountry.setLabelFor(cmbCountry);
		cmbCountry.setMaximumSize(new Dimension(200, 32767));
		cmbCountry.addItem("America");
		cmbCountry.addItem("China");
		content.add(cmbCountry, "cell 2 13,growx");
		
		TitleLabel titleContact = new TitleLabel("Contact Information");
		content.add(titleContact, "cell 0 14 3 1,growx");
		
		JLabel lblPhoneNumber = new JLabel("Telephone");
		content.add(lblPhoneNumber, "cell 0 15");
		
		JLabel lblMailAddress = new JLabel("mail_Address");
		content.add(lblMailAddress, "cell 1 15");
		try
		{
			txtPhoneNumber = new JFormattedTextField(new MaskFormatter("## ## ## ## ##"));
			lblPhoneNumber.setLabelFor(txtPhoneNumber);
			content.add(txtPhoneNumber, "cell 0 16,growx");
		} catch (ParseException e) {
			
		}
		
		txtMailAddress = new JTextField();
		lblMailAddress.setLabelFor(txtMailAddress);
		content.add(txtMailAddress, "cell 1 16,pushx ,growx 150");
		
		JPanel panel = new JPanel();
		panel.setOpaque(false);
		FlowLayout flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setVgap(0);
		content.add(panel, "cell 0 18 3 1,grow");
		
		btnSubmit = new JButton("Register");
		btnSubmit.setPreferredSize(new Dimension(120, 40));
		btnSubmit.setFont(new Font("DejaVu Sans", Font.BOLD, 14));
		panel.add(btnSubmit);
		
		setValidator(new CustomValidator());
		getValidator().setRequiredFieldsAuto(this);
	}
	
	class CustomValidator extends Validator
	{
		private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
				+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
		
		@Override
		public boolean validateFields() {
			if (!super.validateFields()) return false;
			
			try
			{
				Integer.parseInt(txtIdentifier.getText());
			}
			catch (NumberFormatException e)
			{
				errorMessage = "This area <b>'Identifier'</b> doesn't include a number of entities ";
				return false;
			}
			
			if (!Pattern.compile(EMAIL_REGEX).matcher(txtMailAddress.getText()).matches())
			{
				errorMessage = "This area <b>'mail_Address'</b> is not validate";
				return false;
			}
			return true;
		}
	}
	
	protected Dossier getFormData()
	{
		AdmData admData = new AdmData();
		admData.setNumDossier(txtIdentifier.getText());
		admData.setCiv((String) cmbCivility.getSelectedItem());
		admData.setPrenom(txtFirstName.getText());
		admData.setNom(txtLastName.getText());
		admData.setLieuNaiss(txtBirthPlace.getText());
		admData.setDateNaiss(Application.sqlDateFormat.format(dteBirthDate.getDate()));
		admData.setAdresse(txtAddress.getText());
		admData.setCodePostal(txtZipCode.getText().replaceAll(" ", ""));
		admData.setVille(txtCity.getText());
		admData.setPays((String) cmbCountry.getSelectedItem());
		admData.setNum(txtPhoneNumber.getText().replaceAll(" ", ""));
		admData.setMail(txtMailAddress.getText());
		
		Dossier patient = new Dossier();
		patient.setDataAdm(admData);
		patient.setHopital("1");
		
		return patient;
	}
}
