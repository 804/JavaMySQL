package DZ;

import java.sql.DriverManager;
import java.sql.SQLException;

import junit.framework.TestCase;

public class Test extends TestCase{
    public Test(String testName) {
        super(testName);
    }

    public void testAddClientTrue() throws SQLException {
    	AddMySQL mysql = new AddMySQL((DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=12345")), "test", "test", 1.00);
        assertTrue(mysql.AddClient() == true);
    }

    public void testInfClientTrue() throws SQLException {
    	InfMySQL mysql = new InfMySQL((DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=12345")), "test", "test", false);
        assertTrue(mysql.InfClient() == true);
    }

    public void testTransMoneyTrue() throws SQLException {
    	AddMySQL mysql = new AddMySQL((DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=12345")), "test1", "test1", 0.00);
    	mysql.AddClient();
    	TransMySQL mysql1 = new TransMySQL((DriverManager.getConnection("jdbc:mysql://localhost/test?" + "user=root&password=12345")), "test", "test", "test1", 0.00);
        assertTrue(mysql1.TransMoney() == true);
    }
}