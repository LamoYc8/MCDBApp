package app.dataAcess;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OperationResultSet 
{
	public static boolean contains(String str,String colum, ResultSet rs) throws SQLException{

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
