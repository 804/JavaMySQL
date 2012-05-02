/*На локалхосте создана база данных "test". Есть пользователь "root" с паролем "12345".
  В базе есть таблица "accounts" со столбцами:
                                                - name :      varchar(100);
                                                - password :  varchar(150);
                                                - money:      double(255,2) unsigned. 
                                                
https://github.com/Sujiro/MyCode/tree/master/src/DZ                                                */


package DZ;


import javax.swing.*;

import java.math.*;
import java.sql.*;
import java.security.*;

import junit.textui.TestRunner;
import junit.framework.TestSuite;





public class Work {
	public static void main(String[] args) throws NoSuchAlgorithmException, SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://localhost/test?" + "user=root&password=12345";
			Connection con = DriverManager.getConnection(connectionUrl);
		
			int n = 0;
			
			String AddName;
			String AddPass;
			
			String InfName;
			String InfPass;
			String InfPassHash;
			
			String TransName1;
			String TransPass;
			String TransPassHash;
		
			for (;;) {
				if (n == 1) {
					break;
				}
				int MainComand = Integer.parseInt(JOptionPane.showInputDialog(null,"Вас приветствует система управления счетами.\nПожалуйста, укажите номер команды:\n	1. Добавление нового аккаунта\n	2.Информация о пользователе\n	3.Перевод средств со счёта на счёт\n	4.Завершить работу", "Main Page",JOptionPane.QUESTION_MESSAGE));
				switch (MainComand) {
					case 1:
						AddName = JOptionPane.showInputDialog(null,"Введите фамилию и имя нового клиента","Request",JOptionPane.QUESTION_MESSAGE);
						AddPass = JOptionPane.showInputDialog(null,"Введите пароль для нового клиента","Request",JOptionPane.QUESTION_MESSAGE);
						double AddMoney = Double.parseDouble(JOptionPane.showInputDialog(null,"Введите баланс нового клиента","Request",JOptionPane.QUESTION_MESSAGE));
						String AddPassHash = DigestSHA(AddPass);
						AddMySQL c1 = new AddMySQL(con, AddName, AddPassHash, AddMoney);
						boolean a = c1.AddClient();
						if (a == true) {
							JOptionPane.showMessageDialog(null,"Клиент " + AddName + " добавлен", "Information", JOptionPane.INFORMATION_MESSAGE);
						}
						c1 = null;
						continue;
					case 2:
						InfName = JOptionPane.showInputDialog(null,"Введите фамилию и имя запрашиваемого клиента","Request",JOptionPane.QUESTION_MESSAGE);
						InfPass = JOptionPane.showInputDialog(null,"Введите пароль запрашиваемого клиента","Request",JOptionPane.QUESTION_MESSAGE);
						InfPassHash = DigestSHA(InfPass);
						InfMySQL c2 = new InfMySQL(con, InfName, InfPassHash, true);
						c2.InfClient();
						c2 = null;
						continue;
					case 3:
						TransName1 = JOptionPane.showInputDialog(null,"Введите, пожалуйста, фамилию и имя клиента, \nсредства которого вы хотите перевести","Request",JOptionPane.QUESTION_MESSAGE);
						TransPass = JOptionPane.showInputDialog(null,"Введите пароль запрашиваемого клиента","Request",JOptionPane.QUESTION_MESSAGE);
						TransPassHash = DigestSHA(TransPass);
						TransMySQL c3 = new TransMySQL(con, TransName1, TransPassHash);
						c3.TransMoney();
						c3 = null;
				        continue;
					case 4:
						n = 1;
						JOptionPane.showMessageDialog(null,"Good bye", "Message", JOptionPane.INFORMATION_MESSAGE);	
						//test commands
						TestRunner runner = new TestRunner();
					    TestSuite suite = new TestSuite();
					    suite.addTest(new Test("testAddClientTrue"));
					    suite.addTest(new Test("testInfClientTrue"));
					    suite.addTest(new Test("testTransMoneyTrue"));
					    runner.doRun(suite);
				        DeleteMySQL dmysql = new DeleteMySQL(con,"test", "test");
				        dmysql.DelClient();
				        dmysql = new DeleteMySQL(con,"test1", "test1");
				        dmysql.DelClient();
				        dmysql = null;
						continue;
					default:
						JOptionPane.showMessageDialog(null,"Такой команды нет!", "Error", JOptionPane.ERROR_MESSAGE); 
				}
				

			}
		} catch (SQLException e) {
			System.out.println("SQL Exception: "+ e.toString());
		} catch (ClassNotFoundException cE) {
			System.out.println("Class Not Found Exception: "+ cE.toString());
		}

	}
	
	
	
	
	
	
	
	static String DigestSHA (String input) throws NoSuchAlgorithmException {
		MessageDigest md = MessageDigest.getInstance("SHA-1");
		md.update(input.getBytes(),0,input.length());
		String output = new BigInteger(1,md.digest()).toString(16);
		return output;
	}
}