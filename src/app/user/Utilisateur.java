package app.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.dataAcess.OperationData;
import app.dataAcess.OperationResultSet;
import app.patient.Dossier;


public class Utilisateur
{

	private static Utilisateur defaultUser;
	

	private String prenom_Uti;
	

	private String nom_Uti;
	

    private String id_Uti;
    

    private String role_Uti;
    

    private String login_Uti;
    

    private String password_Uti;
    
    
    public static Utilisateur getDefaultUser()
    {
    	if (defaultUser == null) {
    		defaultUser = new Utilisateur();
    	}
    	return defaultUser;
    }
    
    public static Utilisateur createUti(String id) throws SQLException
    {
    	Utilisateur ut = new Utilisateur();
    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select * from Utilisateur where id='"+id+"';");
    	String role="";
    	
    	rs.next();
    	ut.setLogin(rs.getString("login"));
    	ut.setPrenom(rs.getString("Prenom"));
    	ut.setNom(rs.getString("Nom"));
    	ut.setId(rs.getString("id"));
    	ut.setPassword(rs.getString("password"));
    	
    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Secretaire");

    	if(OperationResultSet.contains(id,"utilisateur_id",rs))
    	{
    		role="Secretary";
    	}
    	
    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Admin");

    	if(OperationResultSet.contains(id,"utilisateur_id",rs))
    	{
    		role="Administrator";
    	}
    	

    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Medecin");

    	if(OperationResultSet.contains(id,"utilisateur_id",rs))
    	{
    		role="Doctor";
    	}
    	ut.setRole(role);
    	return ut;
    }
    
    public boolean connexionU(String login, String mdp) throws SQLException
    {

    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select login from Utilisateur");
    	
    	
		if(!OperationResultSet.contains(login,"login",rs))
		{
			System.out.println("erreur log");
			return false;
		}
	

    	rs = (ResultSet) OperationData.lireEnBase("select password from Utilisateur where login='"+login+"';");
    	rs.next();
    	

    	if(!this.tcheckMDP(mdp, rs.getString("password")))
    	{
    		System.out.println("erreur mdp");
    		return false;
    	}
        

    	password_Uti = rs.getString("password");
    	

    	login_Uti =login;
    	
    	rs = (ResultSet) OperationData.lireEnBase("select id, Nom, Prenom from Utilisateur where login='"+login+"';");
    	rs.next();
    	
    	prenom_Uti=rs.getString("prenom");
    	nom_Uti=rs.getString("nom");
    	String identifiant = rs.getString("id");
    	

    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Secretaire");

    	if(OperationResultSet.contains(identifiant,"utilisateur_id",rs))
    	{
    		id_Uti= identifiant;
    		role_Uti = "Secretary";
    		return true;
    	}
    	
    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Admin");

    	if(OperationResultSet.contains(identifiant,"utilisateur_id",rs))
    	{
    		id_Uti = identifiant;
    		role_Uti = "Administrator";
    		return true;
    	}
    	
    	

    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Medecin");

    	if(OperationResultSet.contains(identifiant,"utilisateur_id",rs))
    	{
    		id_Uti = identifiant;
    		role_Uti = "Doctor";
    		return true;
    	}
    	

    	
    	role_Uti=null;
    	id_Uti=null;
    	password_Uti=null;
    	prenom_Uti=null;
    	nom_Uti=null;
		System.out.println("erreur log fin");

        return false;
    }
    
    public static boolean disconnectionU() throws SQLException{
    	if(OperationData.closeData()){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public boolean updateUtil() throws SQLException
    {

    	OperationData.sauverEnBase("UPDATE Utilisateur SET password='"+password_Uti+"',login='"+login_Uti+"',Nom='"+nom_Uti+"',Prenom='"+prenom_Uti+"' WHERE id='"+id_Uti+"';");
    	OperationData.sauverEnBase("DELETE FROM Admin WHERE utilisateur_id="+id_Uti+";");
    	OperationData.sauverEnBase("DELETE FROM Secretaire WHERE utilisateur_id="+id_Uti+";");
    	OperationData.sauverEnBase("DELETE FROM Medecin WHERE utilisateur_id="+id_Uti+";");
    	
    	if(role_Uti.equals("Administrator")){
    		OperationData.sauverEnBase("INSERT INTO Admin(utilisateur_id) VALUES ('"+id_Uti+"');");
    	}else{
    		OperationData.sauverEnBase("INSERT INTO "+role_Uti+" (utilisateur_id,numPole) VALUES ('"+id_Uti+"','"+1+"');");
    	}

    	return true;
    }
    
    public static boolean createUtil(String name, String forename, String login, String password, String role, int pole)throws SQLException, IllegalArgumentException
    {

    	if(!defaultUser.role_Uti.equals("Administrator")){
    		return false;
    	}
    	

    	ResultSet rs;
    	rs = (ResultSet) OperationData.lireEnBase("select login from Utilisateur where login='" + login + "';");


    	if (rs.next())
    	{
    		throw new IllegalArgumentException("login is not available");
    	}
    	

    	OperationData.sauverEnBase("INSERT INTO Utilisateur (Nom, Prenom, login, password) VALUES ('"+name+"','"+ forename +"','"+login+"','"+password+"');");
    	

    	rs = (ResultSet) OperationData.lireEnBase("select id from Utilisateur where login ='"+ login+"';");
    	rs.next();
    	String id = rs.getString("id");
    	
	    if(role.equals("Doctor"))
	    {

	    	OperationData.sauverEnBase("INSERT INTO Medecin (utilisateur_id,numPole) VALUES ('"+id+"','"+pole+"');");
	    }
	    else if(role.equals("Secretary"))
	    {


	    	OperationData.sauverEnBase("INSERT INTO Secretaire (utilisateur_id,numPole) VALUES ('"+id+"','"+pole+"');");
	    }
	    else if(role.equals("Administrator"))
	    {

	    	
	    	OperationData.sauverEnBase("INSERT INTO Admin(utilisateur_id) VALUES ('"+id+"');");
	    }
	    else
	    {
	    	
	    	return false;
	    }
    	
    	return true;
    }
    
    public Dossier[] allDossier()
    {
    	ArrayList<Dossier> arrDos = new ArrayList<Dossier>();
    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select numDossier from Soigner WHERE id='"+id_Uti+"';");
    	try {
			while(rs.next())
			{
				Dossier d = new Dossier();
				d.load(rs.getString("id"));
				arrDos.add(d);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
    	return arrDos.toArray(new Dossier[arrDos.size()]);
    }
    
    public boolean addDossier(String numDossier) throws SQLException
    {
    	if(OperationData.sauverEnBase("INSERT INTO Soigner (id,numDossier) VALUES ('"+id_Uti+"','"+numDossier+"');")){
    		return true;
    	}else{
    		return false;
    	}
    	
    }
    
    public boolean deleteDossier(String numDossier) throws SQLException
    {
    	if(OperationData.sauverEnBase("DELETE FROM Soigner WHERE numDossier='"+numDossier+"' and id=" + id_Uti + ";")){
    		return true;
    	}else{
    		return false;
    	}
    }
    
    public static Utilisateur[] allUti()
    {
    	ArrayList<Utilisateur> arrUti = new ArrayList<Utilisateur>();
    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select id from Utilisateur");
    	try {
			while(rs.next())
			{
				Utilisateur ut = Utilisateur.createUti(rs.getString("id"));
				arrUti.add(ut);
			}
		} catch (SQLException e){
			e.printStackTrace();
		}
    	return arrUti.toArray(new Utilisateur[arrUti.size()]);
    }
    
    public static boolean deleteUtil(String id) throws SQLException
    {
    	if(!defaultUser.role_Uti.equals("Administrator")){
    		return false;
    	}
    	
    	
		OperationData.sauverEnBase("DELETE FROM Secretaire WHERE utilisateur_id='"+id+"';");
		OperationData.sauverEnBase("DELETE FROM Medecin WHERE utilisateur_id='"+id+"';");
		OperationData.sauverEnBase("DELETE FROM Admin WHERE utilisateur_id='"+id+"';");
		
		OperationData.sauverEnBase("DELETE FROM Utilisateur WHERE id='"+id+"';");
    	return true;
    }
    

    public void setId(String str){
    	this.id_Uti=str;
    }
    
    public String getId()
    {

        return id_Uti;
    }
    
    public void setPrenom(String str){
    	this.prenom_Uti=str;
    }
    
    public String getPrenom()
    {

        return prenom_Uti;
    }
    
    public void setNom(String str){
    	this.nom_Uti=str;
    }
    
    public String getNom()
    {

        return nom_Uti;
    }
    
    public void setLogin(String str)
    {
    	this.login_Uti=str;
    }
    
    public String getLogin()
    {

        return login_Uti;
    }
    
    public void setPassword(String str)
    {
    	this.password_Uti=str;
    }
    
    public String getPassword()
    {

    	
        return password_Uti;
    }
    
    public void setRole(String str)
    {
    	this.role_Uti=str;
    }
    
    public String getRole()
    {

    	
        return role_Uti;
    }

    private boolean tcheckMDP(String mdp, String mdpInData)
    {

    	
    	if(mdp.equals(mdpInData))
    	{

    		return true;
    		
    	}
    	else
    	{

    		return false;
    		
    	}
    }
}