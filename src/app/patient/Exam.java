package app.patient;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.dataAcess.OperationData;
import app.dataAcess.OperationResultSet;

 

public class Exam extends Element
{
    private String typeExam;
    private String intitule;
    private String chemin;
    private String description;
    private String dateExam;
    
    
    
    
    
    public Exam (String numDossier, String numElement) throws SQLException { 
        super(numDossier, numElement);
        
        ResultSet rs = (ResultSet) OperationData.lireEnBase("select * from Examen where numElement=" + numElement);
    	while(rs.next())
		{
			intitule = rs.getString("intitule");
			typeExam = rs.getString("typeExam");
			description = rs.getString("description");
			dateExam = rs.getString("dateExam");
		}
    	
    	chemin = "assets/files/"+numDossier+"/"+numElement+".pdf";


    }
    
    public Exam (String numDossier) { 
        super(numDossier);
        typeExam = null;
		intitule = null;
    	chemin = "assets/files/"+numDossier+"/"+numElement+".pdf";
    } 
    
    
    
    public String getTypeExam()
    {
        return typeExam;
    }
    
    public String getIntitule()
    {
        return intitule;
    }
    
    
    public String getDescription()
    {
        return description;
    }
    
    
    public String getDateExam()
    {
        return dateExam;
    }
    
    
    public void setDateExam(String dateExam)
    {
        this.dateExam = dateExam;
    }
    
    
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
    
    public void setTypeExam(String typeExam)
    {
        this.typeExam = typeExam;
    }
    
    public void setIntitule(String intitule)
    {
        this.intitule = intitule;
    }
    


    
    
    public boolean loadFile() 
    {
        return true;
    }
    
    
    public boolean save() throws SQLException
    {
        ResultSet rss;
        rss = (ResultSet) OperationData.lireEnBase("select numElement from Element;");
        rss.next();
        if (OperationResultSet.contains(numElement,"numElement",rss))
        {
            if (!OperationData.sauverEnBase("UPDATE Examen set typeExam='"+typeExam+"', intitule='"+intitule+"', description='"+description+"', dateExam='"+dateExam+"'"))
            {
                return false;
            }
        }
        else 
        {
            if (!OperationData.sauverEnBase("INSERT INTO Element (numDossier, numElement) VALUES ('"+numDossier+"', '"+numElement+"');"))
            {
                return false;
            }
            if (!OperationData.sauverEnBase("INSERT INTO Examen (numElement, typeExam, intitule, description, dateExam) VALUES ('"+numElement+"','"+typeExam+"','"+intitule+"','"+description+"','"+dateExam+"');")) 
            {
                return false;
            }
    	}
        return true;
    }
    
    
   

    
}
