package app.dataAcess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import app.core.Configuration;

/*
 * Cette class a pour but de centraliser les accès a la base de donnée.
 * A chaque opération effectué il faut faire appel a closeData qui 
 * permet de fermer la resource utilisé
 */

public class OperationData
{
	
	/*
	 * Variable globale permettant d'initialiser la connexion et statement qui permet d'executer une
	 * Requete SQL. Ces variable son en  globale pour pouvoir utiliser le resultSet 
	 * Dans d'autre Class comme les classes dossier ou Utilisateur
	 */
	
	//Initialise la connexion

	private static java.sql.Connection cn = null;
	
	//Initialise l'execution SQL
	private static java.sql.Statement st = null;

	
	/*
	 * Cette methode permet de sauvegarder dans la base de donnée
	 * grace a la requete SQL passé en argument.
	 * Elle peut renvoyer plusieur erreur comme SQLException, ou ClassNotFoundException
	 */
	
	public static boolean sauverEnBase(String sql) throws SQLException
	{
		try
		{
			//Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");
			if(cn == null){
				//Recuperation de la connexion
				cn = DriverManager.getConnection("jdbc:mysql://" + Configuration.get("DataBase.HostRoot") + "/" + Configuration.get("DataBase.Name"), Configuration.get("DataBase.Login"), Configuration.get("DataBase.Password"));
			}
			
			
			
			//Creation d'un statement
			st = cn.createStatement();
			
			//Execution requete sql
			st.executeUpdate(sql);
			
			return true;
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
		}
		return false;
	}
	
	/*
	 * Cette méthode permet de lire dans la base de donnee
	 * grace a la requete SQL passé en argument elle renvois ensuite
	 * un objet ResultSet qui seras utilisable tant que nous avons 
	 * pas fermé ct et cn
	 */
	
	public static ResultSet lireEnBase(String sql)
	{
		
		ResultSet rs=null;
		try
		{
			//Chargement du driver
			Class.forName("com.mysql.jdbc.Driver");
			
			if(cn == null){
				//Recuperation de la connexion
				cn = DriverManager.getConnection("jdbc:mysql://" + Configuration.get("DataBase.HostRoot") + "/" + Configuration.get("DataBase.Name"), Configuration.get("DataBase.Login"), Configuration.get("DataBase.Password"));
				
			}
			//Creation d'un statement
			st = cn.createStatement();
			
			//Execution requete sql
			rs = st.executeQuery(sql);
			
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
			
		}
		catch(ClassNotFoundException e)
		{
			
			e.printStackTrace();
			
		}
			
		return rs;
			
		
	}
	
	public static boolean closeData() throws SQLException
	{

		try{
			/*
			 * Ici l'on ferme la ressource Connexion cn et l'initialise a null
			 */
			if (cn != null) {
				cn.close();
				
				cn=null;
				
				/*
				 * Ici l'on ferme la ressource Statement st et l'initialise a null
				 */
				st.close();
				st=null;
				
				return true;
			} else {
				return false;
			}
			
		}
		catch(SQLException e)
		{
			
			e.printStackTrace();
			
			return false;
		}

	}
}
