package DZ;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import java.sql.*;

public class TransMySQL {
	Connection con;
	String TransName1;
	String TransPassHash;
	String TransName2;
	double TransMoney;
	boolean inv;
	public TransMySQL(Connection c, String s1, String s2) {
		this.con = c;
		this.TransName1 = s1;
		this.TransPassHash = s2;
		inv = true;
	}
	public TransMySQL(Connection c, String s1, String s2, String s3, double d) {
		this.con = c;
		this.TransName1 = s1;
		this.TransPassHash = s2;
		this.TransName2 = s3;
		this.TransMoney = d;
		inv = false;
	}
	public boolean TransMoney() throws NumberFormatException, SQLException {
		Statement stmt = null;
		ResultSet rs = null;
		ResultSet rs1 = null;
		String Control1strSQL;
		String Control2strSQL;
		String TransstrSQL1;
		String TransstrSQL2;
		int rowsEffected1 = 0;
		int rowsEffected2 = 0;
		stmt = con.createStatement();
		Control1strSQL = "SELECT * FROM accounts WHERE name = \"" + TransName1 + "\";";
		rs = stmt.executeQuery(Control1strSQL);
		rs.next();
        double m1 = Double.parseDouble(rs.getString("money"));
        if (rs.getString("password").equals(TransPassHash)) {
        	if (inv == true) {
        		TransName2 = JOptionPane.showInputDialog(null,"Введите, пожалуйста, фамилию и имя клиента, \nкоторому хотите перевести средства","Request",JOptionPane.QUESTION_MESSAGE);
        	}
        	Control2strSQL = "SELECT * FROM accounts WHERE name = \"" + TransName2 + "\";";
        	rs1 = stmt.executeQuery(Control2strSQL);
			rs1.next();
	        double m2 = Double.parseDouble(rs1.getString("money"));
	        if (inv == true) {
	        TransMoney = Double.parseDouble(JOptionPane.showInputDialog(null,"Введите пожалуйста перечисляемую сумму","Request",JOptionPane.QUESTION_MESSAGE));
	        }
	        if (m1 > TransMoney) {
	        	TransstrSQL1 = "UPDATE accounts SET money = " + (m1 - TransMoney) + " WHERE name  = '" + TransName1 + "';";
	        	TransstrSQL2 = "UPDATE accounts SET money = " + (m2 + TransMoney) + " WHERE name  = '" + TransName2 + "';";
	        	rowsEffected1 = stmt.executeUpdate(TransstrSQL1);
			    rowsEffected2 = stmt.executeUpdate(TransstrSQL2);
	        	if (inv == true) {
	        		JOptionPane.showMessageDialog(null,"Перевод средств проведен успешно", "Information", JOptionPane.INFORMATION_MESSAGE);	
	        	}
	        }
	        else {
	        	JOptionPane.showMessageDialog(null,"Не хватает средств на счету!", "Error", JOptionPane.ERROR_MESSAGE); 
	        }				         
        }
        else {
        	JOptionPane.showMessageDialog(null,"Неверный пароль!", "Error", JOptionPane.ERROR_MESSAGE); 
        }
        stmt.close();
        rs.close();
        rs1.close();
        if ((rowsEffected1 > 0) && (rowsEffected2 > 0)) {
        	return true;
        }
        else {
        	return false;
        }
	}
}
