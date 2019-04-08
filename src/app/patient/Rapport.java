package app.patient;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.dataAcess.OperationData;
import app.dataAcess.OperationResultSet;




public class Rapport extends Element
{
    private String valide;
    private String id;
    private String rapport;
    private String intitule;
    private String date;
    private String Prescription;
    
    
    
    public Rapport (String numDossier, String numElement) throws SQLException { 
        super(numDossier, numElement);
		
		ResultSet rs = (ResultSet) OperationData.lireEnBase("select * from RapportMedicaux where numElement=" + numElement);
    	while(rs != null && rs.next())
		{
			id = rs.getString("idRapport");
			rapport = rs.getString("Rapport");
			intitule = rs.getString("Intitule");
			date = rs.getString("Date");
			Prescription = rs.getString("Prescription");
			valide = rs.getString("isValide");
		}
	}
	
	
    public Rapport (String numDossier) { 
        super(numDossier);
		valide= "0";
    } 
    
    public void valider() {
    	valide = "1";
    }
    
    
   public boolean isValide()
    {
        return valide.equals("1");
    }
    
    
    public String getId()
    {
        return id;
    }
    
    
    public String getRapport()
    {
        return rapport;
    }
    
    
    public String getPrescription()
    {
        return Prescription;
    }
    
    
    public String getIntitule()
    {
        return intitule;
    }
    
    
    public String getDate()
    {
        return date;
    }
    
    public void setDate(String date)
    {
        this.date = date;
    }
    
    
    
    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }
    
    
    public void setPrescription(String Prescription)
    {
        this.Prescription = Prescription;
    }
    
    
    public void setRapport(String rapport)
    {
        this.rapport = rapport;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public void setValide(String valide)
    {
        this.valide = valide;
    }
    

    public boolean save() throws SQLException
    {
        ResultSet rss;
        rss = (ResultSet) OperationData.lireEnBase("select numElement from Element;");
        rss.next();
        if (numElement != null && OperationResultSet.contains(numElement,"numElement",rss))
        {
            if (!OperationData.sauverEnBase("UPDATE RapportMedicaux set Rapport='"+ rapport+"', Date='"+ date+"', Prescription='"+ Prescription+"', Intitule='"+ intitule+"', isValide ="+valide + " where idRapport=" + id + ";"))
            {
                return false;
            }
        }
        else 
        {
        	ResultSet rs = (ResultSet) OperationData.lireEnBase("SELECT MAX(numElement)+1 AS id FROM Element;");
        	rs.next();
        	
        	numElement = !rs.getString("id").equals("null") ? rs.getString("id") : "1";
        	
        	if (!OperationData.sauverEnBase("INSERT INTO Element (numDossier, numElement) VALUES ('"+numDossier+"', '"+numElement+"');"))
            {
                return false;
            }
            if (!OperationData.sauverEnBase("INSERT INTO RapportMedicaux (Rapport, numElement, Date, Prescription, Intitule, isValide) VALUES ('"+rapport+"','"+numElement+"','"+date+"','"+Prescription+"','"+intitule+"',"+valide+");"))
            {
                return false;
            }
        }
        return true;
    }
    
}
