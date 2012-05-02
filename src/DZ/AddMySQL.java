package DZ;

import java.sql.*;
import java.sql.SQLException;
import java.sql.Statement;

public class AddMySQL {
	Connection con;
	String AddName;
	String AddPassHash;
	double AddMoney;
	public AddMySQL(Connection c, String s1, String s2, double d){
		this.con = c;
		this.AddName = s1;
		this.AddPassHash = s2;
		this.AddMoney = d;
	}
	public boolean AddClient() throws SQLException{
		Statement stmt = null;
		String AddstrSQL;
		stmt = con.createStatement();
		AddstrSQL = "INSERT INTO accounts VALUES (\"" + AddName + "\", \"" + AddPassHash + "\"," + AddMoney + ");";
		int rowsEffected = stmt.executeUpdate(AddstrSQL);
		stmt.close();
		if (rowsEffected >0) {
			return true;
		}
		else {
			return false;
		}
	}
}
