package DZ;

import java.sql.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class InfMySQL {
	Connection con;
	String InfName;
	String InfPassHash;
	boolean inv;
	public InfMySQL(Connection c, String s1, String s2, boolean a) {
		this.con = c;
		this.InfName = s1;
		this.InfPassHash = s2;
		this.inv = a;
	}
	public boolean InfClient() throws SQLException {
		Statement stmt = null;
		stmt = con.createStatement();
		ResultSet rs = null;
		String InfstrSQL;
		int k = 0;
		InfstrSQL = "SELECT * FROM accounts WHERE name = \"" + InfName + "\" AND password = \"" + InfPassHash + "\";";
		rs = stmt.executeQuery(InfstrSQL);
		if (rs != null) {
			k = 1;
		}
		else {
			k = -1;
		}
		rs.next();
		if (inv == true) {
			JOptionPane.showMessageDialog(null,"	Имя клиента: " + rs.getString("name") + "\n	Баланс: " + rs.getString("money"), "Information", JOptionPane.INFORMATION_MESSAGE);
		}
		stmt.close();
		rs.close();
		if (k == 1) {
			return true;
		}
		else {
			return false;
		}
	}
}
