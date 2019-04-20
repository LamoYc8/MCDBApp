package app.gui;

import java.util.Observable;

import app.patient.Dossier;

public class PatientSelector extends Observable {
	private static PatientSelector instance;
	
	private Dossier selectedPatientRecord;
	
	private PatientSelector()
	{}
	
	public static PatientSelector getInstance()
	{
		if (instance == null)
		{
			instance = new PatientSelector();
		}
		return instance;
	}
	
	public static void makeInstance()
	{
		instance = new PatientSelector();
	}
	
	public Dossier getPatientRecord()
	{
		return selectedPatientRecord;
	}
	
	public void setPatient(Dossier patientRecord)
	{
		selectedPatientRecord = patientRecord;
		setChanged();
		notifyObservers();
	}
}
