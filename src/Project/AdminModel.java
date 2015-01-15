/**
* AdminModel.java
* @author Sajjad HTLO
*
* This class handles admin log-in works, such as initial first admin password at first log-in, and checks admin password correctness. 
*
*
*/

package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

public class AdminModel {

    private static final String dbUrl = "jdbc:mysql://localhost/";
    private InitialDB initDB = new InitialDB();

    public void insertIntoAdminLogin(int password) {
        String insertQuery = "INSERT INTO ADMINLOGIN VALUES(?) ";
        String selectDB = "USE " + initDB.getDataBaseName();
        System.out.println("DB name in aminLogin: " + initDB.getDataBaseName());
        try (
                Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                Statement selectST = con.createStatement();
                PreparedStatement insertPS = con.prepareStatement(insertQuery);) {
            selectST.execute(selectDB);

            insertPS.setInt(1, password);
            insertPS.execute();

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public boolean adminEnterCondition(char[] pass) {
        ResultSet rs;
        String selectDB = "USE " + initDB.getDataBaseName();
        String selectQuery = "Select * From adminLogin";
        String password = null;
        PreparedStatement ps2 = null;
        Statement selectDBst = null;
        try (
                Connection con = DriverManager.getConnection(dbUrl + initDB.getDataBaseName(), "root", initDB.getMysqlPassword());) {
            selectDBst = con.createStatement();
            selectDBst.execute(selectDB);
            ps2 = con.prepareStatement(selectQuery);
            rs = ps2.executeQuery();
            while (rs.next()) {
                password = rs.getString("ID");  // Get column value by name column name
                if (password.equalsIgnoreCase(String.valueOf(pass))) {
                    return true;
                }
            }
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "There is an sql exception");
            sqle.printStackTrace();
            return false;
        }
        return false;
    }
}
