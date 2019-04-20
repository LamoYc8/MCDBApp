package app.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import app.dataAcess.OperationData;
import app.dataAcess.OperationResultSet;
import app.patient.Dossier;

/*
 * Cette class permet la gestion des utilisateur, elle contiens l'identifiant qui le correspond
 * dans la base de donnée, et trouve le role qui lui ai associé.
 * Elle a une methode pour permet a un utilisateur de ce connecter.
 * 
 * Elle a une Methode createUtil qui est utilisable que par un Administrateur qui permet de créer
 * un Utilisateur dans la base de donnée. 
 * 
 * Elle a une Methode ModifUtil qui est utilisable que pas un administrateur qui permet de 
 * modifier l'Utilisateur dans la base donnée
 */

public class Utilisateur
{
	/*
	 * Methode du singleton pour avoir un seul Utilisateur
	 */
	private static Utilisateur defaultUser;
	
	//Prénom Utilisateur
	private String prenom_Uti;
	
	//Nom Utilisateur
	private String nom_Uti;
	
	//id de l'utilisateur
    private String id_Uti;
    
    /*
     * role de l'utilisateur :
     * 	- Secretaire
     *  - Admin
     *  - Medecin
     */
    private String role_Uti;
    
    //Login de l'utilisateur
    private String login_Uti;
    
    //Mot de passe de l'utilisateur
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
    	
    	//Si dans Medecin
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
    	/*
    	 * Cette Methode permet de connecter un Utilisateur depuis la base de donnée.
    	 * Les arguments : 
    	 * - login represente l'identifiant de l'utilisateur pour se connecter
    	 * - mdp represente le mot de passe pour identifier l'utilisateur
    	 * Renvois True si l'utilisateur c'est bien connecté, false sinon
    	 */
    	ResultSet rs = (ResultSet) OperationData.lireEnBase("select login from Utilisateur");
    	
    	
		if(!OperationResultSet.contains(login,"login",rs))
		{
			System.out.println("erreur log");
			return false;
		}
	
    	//Recupere tout le mot de passe aved l'identifiant choisi
    	rs = (ResultSet) OperationData.lireEnBase("select password from Utilisateur where login='"+login+"';");
    	rs.next();
    	
    	//Test si le mot de passe est le bon
    	if(!this.tcheckMDP(mdp, rs.getString("password")))
    	{
    		System.out.println("erreur mdp");
    		return false;
    	}
        
    	//Mot de passe de l'utilisateur initialiser dans les champs
    	password_Uti = rs.getString("password");
    	
    	//Login de l'utilisateur initialiser dans les champs
    	login_Uti =login;
    	
    	rs = (ResultSet) OperationData.lireEnBase("select id, Nom, Prenom from Utilisateur where login='"+login+"';");
    	rs.next();
    	
    	prenom_Uti=rs.getString("prenom");
    	nom_Uti=rs.getString("nom");
    	String identifiant = rs.getString("id");
    	
    	//Si Dans Secretaire
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
    	
    	
    	//Si dans Medecin
    	rs = (ResultSet) OperationData.lireEnBase("select utilisateur_id from Medecin");

    	if(OperationResultSet.contains(identifiant,"utilisateur_id",rs))
    	{
    		id_Uti = identifiant;
    		role_Uti = "Doctor";
    		return true;
    	}
    	
    	//Si Non on actualise identifiant et on renvois false
    	
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
    	/*
    	 * Cette Methode permet de créer un Utilisateur dans la base de donnée.
    	 * Les arguments : 
    	 * - name represente le Nom de l'utilisateur
    	 * - forename represente le prenom de l'utilisateur
    	 * - password represente le mot de passe de l'utilisateur
    	 * - role represente le role de l'utilisateur vaut Admin, Secretaire et Medecin
    	 * - pole represente le pole de l'utilisateur (vaut 0 pour l'admin)
    	 * Renvois true si l'utilisateur est crée correctement, false sinon
    	 */
    	//Si l'utilisateur n'est pas un admin
    	if(!defaultUser.role_Uti.equals("Administrator")){
    		return false;
    	}
    	
    	//Recherche de tout les logins possibles
    	ResultSet rs;
    	rs = (ResultSet) OperationData.lireEnBase("select login from Utilisateur where login='" + login + "';");

    	//Recherche si un login existe déja
    	if (rs.next())
    	{
    		throw new IllegalArgumentException("login is not available");
    	}
    	
    	/*
    	 * Creation dans la base de donnée de l'utilisateur
    	 */
    	OperationData.sauverEnBase("INSERT INTO Utilisateur (Nom, Prenom, login, password) VALUES ('"+name+"','"+ forename +"','"+login+"','"+password+"');");
    	
    	/*
    	 * Recuperer l'id de lutilisateur
    	 */
    	rs = (ResultSet) OperationData.lireEnBase("select id from Utilisateur where login ='"+ login+"';");
    	rs.next();
    	String id = rs.getString("id");
    	
	    if(role.equals("Doctor"))
	    {
	    	//Creation du médecin dans la base de donnée
	    	OperationData.sauverEnBase("INSERT INTO Medecin (utilisateur_id,numPole) VALUES ('"+id+"','"+pole+"');");
	    }
	    else if(role.equals("Secretary"))
	    {
	    	//Creation du médecin dans la base de donnée

	    	OperationData.sauverEnBase("INSERT INTO Secretaire (utilisateur_id,numPole) VALUES ('"+id+"','"+pole+"');");
	    }
	    else if(role.equals("Administrator"))
	    {
	    	//Creation du médecin dans la base de donnée
	    	
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
    
    //Getter
    public void setId(String str){
    	this.id_Uti=str;
    }
    
    public String getId()
    {
    	/*
    	 * Permet de recupere l'identifiant de l'utilisateur
    	 */
        return id_Uti;
    }
    
    public void setPrenom(String str){
    	this.prenom_Uti=str;
    }
    
    public String getPrenom()
    {
    	/*
    	 * Permet de recupere l'identifiant de l'utilisateur
    	 */
        return prenom_Uti;
    }
    
    public void setNom(String str){
    	this.nom_Uti=str;
    }
    
    public String getNom()
    {
    	/*
    	 * Permet de recupere l'identifiant de l'utilisateur
    	 */
        return nom_Uti;
    }
    
    public void setLogin(String str)
    {
    	this.login_Uti=str;
    }
    
    public String getLogin()
    {
    	/*
    	 * Permet de recupere le login de l'utilisateur
    	 */
        return login_Uti;
    }
    
    public void setPassword(String str)
    {
    	this.password_Uti=str;
    }
    
    public String getPassword()
    {
    	/*
    	 * Permet de recuperer le mot de passe de l'utilisateur
    	 */
    	
        return password_Uti;
    }
    
    public void setRole(String str)
    {
    	this.role_Uti=str;
    }
    
    public String getRole()
    {
    	/*
    	 * Permet de recupere role de l'utilisateur
    	 */
    	
        return role_Uti;
    }

    private boolean tcheckMDP(String mdp, String mdpInData)
    {
    	/*
    	 * Permet de verifier si le mdp et le mdpInData son les meme
    	 * mdp est le mot de passe saisie et mdpInData est le mot de 
    	 * passe dans la base de donnée
    	 */
    	
    	if(mdp.equals(mdpInData))
    	{
    		//Mot de passe correct
    		return true;
    		
    	}
    	else
    	{
    		//Mot de passe incorrect
    		return false;
    		
    	}
    }
}