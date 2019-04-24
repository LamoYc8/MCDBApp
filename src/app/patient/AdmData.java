package app.patient;

import java.sql.ResultSet;
import java.sql.SQLException;

import app.dataAcess.OperationData;


public class AdmData
{
    private String nom;
    private String prenom;// first name
    private String dateNaiss;//出生日期
    private String aContacter;//
    private String numDossier; //
    private String codePostal;
    private String ville;//城市
    private String pays; //国家
    private String lieuNaiss;// date birth place
    private String num;
    private String mail;
    private String civ;//户籍地址
    private String adresse;//地址


    public AdmData () { 
    } 
    
    
    public boolean load(String numDossier) throws SQLException
    {
    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select * from DonneeAdministrative where numDossier=" + numDossier);
    	rs.next();
    	if ((nom = rs.getString("NomDonne")) == null) {
    	    return false;
    	}
    	if ((prenom = rs.getString("PrenomDonne")) == null) {
    	    return false;
    	}
    	if ((dateNaiss = rs.getString("DateNaissance")) == null) {
    	    return false;
    	}
    	if ((aContacter = rs.getString("PersonneAcontacter")) == null) {
    	    return false;
    	}
    	if ((codePostal = rs.getString("CodePostal")) == null) {
    	    return false;
    	}
    	if ((ville = rs.getString("Ville")) == null) {
    	    return false;
    	}
    	if ((pays = rs.getString("Pays")) == null) {
    	    return false;
    	}
    	if ((civ = rs.getString("Civilite")) == null) {
    	    return false;
    	}
    	if ((lieuNaiss = rs.getString("LieuNaissance")) == null) {
    	    return false;
    	}
    	if ((num = rs.getString("NumTel")) == null) {
    	    return false;
    	}
    	if ((mail = rs.getString("Mail")) == null) {
    	    return false;
    	}
    	if ((adresse = rs.getString("Adresse")) == null) {
    	    return false;
    	}
    	this.numDossier = numDossier;
    	
        return true;
    }
    
    
    
    public boolean clear() throws SQLException
    {
        nom = null;
        prenom = null;
        dateNaiss = null;
        aContacter = null;
        numDossier = null;
        codePostal = null;
        ville = null;
        pays = null;
        civ = null;
        lieuNaiss = null;
        num = null;
        mail = null;
        adresse = null;
    
        return true;
    }
    
    public boolean save() throws SQLException 
    {
    	if (!OperationData.sauverEnBase("update DonneeAdministrative set numDossier='"+numDossier+"', NomDonne='"+nom+"', PrenomDonne='"+prenom+"', DateNaissance='"+dateNaiss+"', PersonneAcontacter='"+aContacter+"', CodePostal='"+codePostal+"', Ville='"+ville+"', Pays='"+pays+"', Civilite='"+civ+"', LieuNaissance='"+lieuNaiss+"', NumTel='"+num+"', Mail='"+mail+"' where numDossier=" + numDossier + ";"))
    	{
			return false;
		}
    	
        return true;
	}
    
    public String getNom()
    {
        return nom;
    }
    
    public String getPrenom()
    {
        return prenom;
    }
    
    public String getDateNaiss()
    {
        return dateNaiss;
    }
    
    public String getAContacter()
    {
        return aContacter;
    }
    
    public String getNumDossier()
    {
        return numDossier;
    }
    
    public String getCodePostal()
    {
        return codePostal;
    }
    
    
    public String getVille()
    {
        return ville;
    }
    
    
    public String getPays()
    {
        return pays;
    }
    
    
    public String getCiv()
    {
        return civ;
    }
    
    
    public String getLieuNaiss()
    {
        return lieuNaiss;
    }
    
    
    public String getNum()
    {
        return num;
    }
    
    
    public String getMail()
    {
        return mail;
    }
    
    
    public String getAdresse()
    {
        return adresse;
    }
    
    
    public void setAdresse(String adresse)
    {
        this.adresse = adresse;
    }
    
    
    
    public void setMail(String mail)
    {
        this.mail = mail;
    }
    
    
    public void setNum(String num)
    {
        this.num = num;
    }
    
    
    public void setLieuNaiss(String lieuNaiss)
    {
        this.lieuNaiss = lieuNaiss;
    }
    
    
    public void setCiv(String civ)
    {
        this.civ = civ;
    }
    
    
    public void setPays(String pays)
    {
        this.pays = pays;
    }
    
    
    public void setVille(String ville)
    {
        this.ville = ville;
    }
    
    
    public void setCodePostal(String codePostal)
    {
        this.codePostal = codePostal;
    }
    
    public void setNom(String s)
    {
        nom=s;
    }
    
    public void setPrenom(String s)
    {
        prenom=s;
    }
    
    public void setDateNaiss(String s)
    {
        dateNaiss=s;
    }
    
    public void setAContacter(String s)
    {
        aContacter =s;
    }
    
    public void setNumDossier(String s)
    {
        numDossier =s;
    }

    
}
