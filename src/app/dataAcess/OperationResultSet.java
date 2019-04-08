package app.dataAcess;

import java.sql.ResultSet;
import java.sql.SQLException;
/*
 * Cette class permet de rassembler les operations relative a la class ResultSet
 * 
 */
public class OperationResultSet 
{
	public static boolean contains(String str,String colum, ResultSet rs) throws SQLException{
		/*
		 * Cette fonction permet de savoir si le str donné, est bien present dans le resultSet
		 * dans la colonne colum
		 */
		while(rs != null && rs.next())
		{
			if(str.equals(rs.getString(colum)))
			{
				return true;
			}	
		}
		return false;
	}
	
}
