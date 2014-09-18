/**
* InitialDB.java
* @author Sajjad HTLO
*
* This class is for initial database, such as connect to MySQL DBMS with received password from users,
* Creates database, And tables.
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

public class InitialDB {

    private static final String dbUrl = "jdbc:mysql://localhost/";
    private static String DbName = "lib";
    private static String mysqlPassword = null;

    public InitialDB() {
    }

    public void createDataBase(String mysqlPass) {
        mysqlPassword = mysqlPass;
        String createDBQuery = "CREATE DATABASE IF NOT EXISTS " + DbName;
        Statement st = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection(dbUrl, "root", mysqlPassword);
            st = con.createStatement();
            st.execute(createDBQuery);
        } catch (SQLException sqle) {
            JOptionPane.showMessageDialog(null, "Error on connecting to Database");
            sqle.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createTables() {
        Statement st = null;
        String selectDB = "USE " + DbName;
        String booksTable = "CREATE TABLE IF NOT EXISTS BOOKS ("
                + "BOOK_ID INT PRIMARY KEY AUTO_INCREMENT, BOOK_NAME VARCHAR(20) NOT NULL, BOOK_ISBN INT(20) NOT NULL,"
                + "BORROW_STATUS VARCHAR(5) NOT NULL, BUY_DATE DATE )";
        String usersTable = "CREATE TABLE IF NOT EXISTS USERS ("
                + "USER_ID INT PRIMARY KEY AUTO_INCREMENT, FIRST_NAME VARCHAR(64) NOT NULL, LAST_NAME VARCHAR(64) NOT NULL,"
                + "GENDER VARCHAR (10) NOT NULL , REG_DATE DATE NOT NULL )";
        String borrowedTable = "CREATE TABLE IF NOT EXISTS BORROWED ("
                + "USER_ID INT , BOOK_ID INT, BORROW_DATE DATE NOT NULL, PRIMARY KEY(USER_ID,BOOK_ID) )";
        String borrowedQueueTable = "CREATE TABLE IF NOT EXISTS BORROWEDQUEUE ("
                + "USER_ID INT, BOOK_ID INT, REQUEST_DATE DATE NOT NULL, PRIMARY KEY(USER_ID,BOOK_ID) )";
        String adminLoginTable = "CREATE TABLE IF NOT EXISTS ADMINLOGIN ("
                + "ID INT PRIMARY KEY NOT NULL )";
        try (
                Connection con = DriverManager.getConnection(dbUrl, "root", mysqlPassword);) {

            st = con.createStatement();
            st.execute(selectDB);
            st.execute(booksTable);
            st.execute(usersTable);
            st.execute(borrowedTable);
            st.execute(borrowedQueueTable);
            st.execute(adminLoginTable);

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public String getDataBaseName() {
        return DbName;
    }

    public String getMysqlPassword() {
        return mysqlPassword;
    }

    public boolean adminLoginIsEmpty() {
        String query = "select * from adminLogin";
        try (
                Connection con = DriverManager.getConnection(dbUrl + getDataBaseName(), "root", mysqlPassword);
                PreparedStatement ps = con.prepareStatement(query);) {
            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {           // Is empty
                return true;
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }
}
