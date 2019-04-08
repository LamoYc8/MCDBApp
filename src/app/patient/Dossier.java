package app.patient;


import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.dataAcess.OperationData;
import app.user.Utilisateur;


 

public class Dossier
{
    
    protected String numDossier;
    private String numHopital;
    private AdmData data;
    private ArrayList<Exam> examens;
    private ArrayList<Rapport> rapports;
    
    
    public Dossier () { 
        numDossier = null;
        numHopital = null;
        data = new AdmData();
        examens = new ArrayList<Exam>();
        rapports = new ArrayList<Rapport>();
    }
    
    
    
    public boolean load(String numDossier) throws SQLException
    {
    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select * from Dossier where numDossier=" + numDossier);
    	rs.next();
    	if ((numHopital = rs.getString("NumHopital")) == null) 
    	{
    	    return false;
    	}
    	this.numDossier = numDossier;

    	
    	if ((data.load(numDossier)) == false) 
    	{
    	    return false;
    	}
    	
    	examens.clear();
    	rapports.clear();
		
		rs = (ResultSet) OperationData.lireEnBase("select numElement from Examen where numElement in ( select numElement from Element where numDossier=" + numDossier + " )");
    	while(rs != null && rs.next())
		{
			examens.add(new Exam(rs.getString("numElement")));	
		}
		
		rs = (ResultSet) OperationData.lireEnBase("select numElement from RapportMedicaux where numElement in ( select numElement from Element where numDossier=" + numDossier + " )");
    	while(rs.next())
		{
			rapports.add(new Rapport(numDossier, rs.getString("numElement")));	
		}
    	
    	
        return true;
    }
    
    public boolean reload() throws SQLException
    {
    	return load(numDossier);
    }
    
    
    public boolean loadFiles() 
    {
		for (Exam e : examens) 
		{
			if (!(e.loadFile()))
			{
				return false;
			}
		}
        return true;
    }
    
	
	public boolean saveDos() throws SQLException 
	{
		
    	if (!OperationData.sauverEnBase("update Dossier set NumHopital="+numHopital+" where numDossier=" + numDossier))
    	{
			return false;
		}
		
		
    	if (!OperationData.sauverEnBase("UPDATE DonneeAdministrative set NomDonne='"+data.getNom()+"', PrenomDonne='"+data.getPrenom()+"', DateNaissance='"+data.getDateNaiss()+"', PersonneAcontacter='"+data.getAContacter()+"', CodePostal='"+data.getCodePostal()+"', Ville='"+data.getVille()+"', Adresse='"+data.getAdresse()+"', Pays='"+data.getPays()+"', Civilite='"+data.getCiv()+"', LieuNaissance='"+data.getLieuNaiss()+"', NumTel='"+data.getNum()+"', Mail='"+data.getMail()+"' WHERE numDossier="+numDossier))
    	{
    	    return false;
    	}
        
        for (Rapport r : rapports) 
        {
            r.save();
        }
        
        for (Exam e : examens) 
        {
            e.save();
        }
    	
        return true;
	}
    
    
    public boolean clear() throws SQLException
    {
    	numDossier = null;
        numHopital = null;
    	
    	data.clear();
    	
    	examens.clear();
    	rapports.clear();
    
        return true;
    }
    
    
    public boolean setDataAdm(AdmData a) 
    {
    	data = a;
    
        return true;
    }
    
    
    public boolean setHopital(String nh) {
		numHopital =nh;
		return true;
	}
    
    
    public boolean addExam (Exam e, File f) 
    {
    	examens.add(e);
    
        return true;
    }
    
    public boolean addRapport (Rapport e, File f) 
    {
    	rapports.add(e);
    
        return true;
    }
    
    
    
    
    public String getNumDossier()
    {
        return numDossier;
    }
    
    public String getNumHopital()
    {
        return numHopital;
    }
    
    public AdmData getData()
    {
        return data;
    }
    
    public boolean isAssignedTo(Utilisateur utilisateur)
    {
    	try
    	{
    		ResultSet rs = (ResultSet) OperationData.lireEnBase("select 1 from Soigner where numDossier=" + numDossier + " and id=" + utilisateur.getId());
        	
        	if (rs.next())
        	{
        		return true;
        	}
    	}
    	catch (SQLException e)
    	{}
    	return false;
    }
    
    public Element getExamen(String numElem)
    {
		for (Exam e : examens) {
			if (e.getNumElement() == numElem) {
				return e;
			}
		}
        return null;
    }
    
    public ArrayList<Exam> getExamens()
    {
        return examens;
    }
    
    public Rapport[] getRapports()
    {
        return rapports.toArray(new Rapport[rapports.size()]);
    }
    
    
    public static Dossier[] loadAll()
    {
    	ArrayList<Dossier> listeDossiers = new ArrayList<Dossier>();
    	String numDossier = "null";
    	
    	ResultSet rs;
    	if (!Utilisateur.getDefaultUser().getRole().equals("Admin"))
    	{
    		rs = (ResultSet) OperationData.lireEnBase("select numDossier from Dossier natural join Soigner s where s.id = " + Utilisateur.getDefaultUser().getId());
    	}
    	else
    	{
    		rs = (ResultSet) OperationData.lireEnBase("select numDossier from Dossier;");
    	}
    	
    	try
    	{
    		while(rs != null && rs.next())
    		{
        		Dossier dossier = new Dossier();
        		numDossier = rs.getString("numDossier");
    			dossier.load(rs.getString("numDossier"));
    			listeDossiers.add(dossier);
    		}
    	}
    	catch (SQLException e)
    	{
    		e.printStackTrace();
    	}
    	return listeDossiers.toArray(new Dossier[listeDossiers.size()]);
    }
    
    
    public boolean create() throws SQLException
    {
    	//Recherche de tout les logins possibles
    	ResultSet rs;
    	rs = (ResultSet) OperationData.lireEnBase("select numDossier from Dossier where numDossier='"+data.getNumDossier()+"';");
    	
    	//Recherche si un login existe déja
    	if (rs.next())
    	{
    		throw new IllegalStateException("the medical records's  ID is already taken");
    	}
    	
    	/*
    	 * Creation dans la base de donnée de l'utilisateur
    	 */
    	
    	if (!OperationData.sauverEnBase("INSERT INTO DonneeAdministrative (numDossier, NomDonne, PrenomDonne, DateNaissance, PersonneAcontacter, CodePostal, Ville, Adresse, Pays, Civilite, LieuNaissance, NumTel, Mail) VALUES ('"+data.getNumDossier()+"','"+ data.getNom()+"','"+data.getPrenom()+"','"+ data.getDateNaiss()+"','"+data.getAContacter()+"','"+data.getCodePostal()+"','"+data.getVille()+"','"+ data.getAdresse()+"','"+data.getPays()+"','"+ data.getCiv()+"','"+data.getLieuNaiss()+"','"+data.getNum()+"','"+data.getMail() +"');"))
    	{
    	    return false;
    	}
    	
    	if (!OperationData.sauverEnBase("INSERT INTO Dossier (numDossier, numHopital) VALUES ('"+data.getNumDossier()+"','"+ numHopital +"');")) 
    	{
    	    return false;
    	}
        
        for (Rapport r : rapports) 
        {
            r.save();
        }
        
        for (Exam e : examens) 
        {
            e.save();
        }
    	
    	
    	
    	return true;
    }
    
}
