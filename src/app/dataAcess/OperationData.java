package app.dataAcess;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import app.core.Configuration;



public class OperationData
{
	private static java.sql.Connection cn = null;

	private static java.sql.Statement st = null;

	public static boolean sauverEnBase(String sql) throws SQLException
	{
		try
		{

			Class.forName("com.mysql.jdbc.Driver");
			if(cn == null){

				cn = DriverManager.getConnection("jdbc:mysql://" + Configuration.get("DataBase.HostRoot") + "/" + Configuration.get("DataBase.Name"), Configuration.get("DataBase.Login"), Configuration.get("DataBase.Password"));
			}

			st = cn.createStatement();
			

			st.executeUpdate(sql);
			
			return true;
			
		}
		catch(ClassNotFoundException e)
		{
			e.printStackTrace();
			
		}
		return false;
	}
	

	
	public static ResultSet lireEnBase(String sql)
	{
		
		ResultSet rs=null;
		try
		{

			Class.forName("com.mysql.jdbc.Driver");
			
			if(cn == null){

				cn = DriverManager.getConnection("jdbc:mysql://" + Configuration.get("DataBase.HostRoot") + "/" + Configuration.get("DataBase.Name"), Configuration.get("DataBase.Login"), Configuration.get("DataBase.Password"));
				
			}

			st = cn.createStatement();
			

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

			if (cn != null) {
				cn.close();
				
				cn=null;
				

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
