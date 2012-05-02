package DZ;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class DeleteMySQL {
	Connection con;
	String DelName;
	String DelPass;
	public DeleteMySQL(Connection c, String s1, String s2) {
		this.con = c;
		this.DelName = s1;
		this.DelPass = s2;
	}
	public boolean DelClient() throws SQLException {
		Statement stmt = null;
		stmt = con.createStatement();
		String DelstrSQL;
		int k = 0;
		DelstrSQL = "DELETE FROM accounts WHERE name = \"" + DelName + "\";";
		int rowsEffected = stmt.executeUpdate(DelstrSQL);
		if (rowsEffected > 0) {
			k = 1;
		}
		else {
			k = -1;
		}
		stmt.close();
		if (k == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
