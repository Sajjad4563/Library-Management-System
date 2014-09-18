/**
* BorrowedModel.java
* @author Sajjad HTLO
*
* This class is to handle all borrow methods. Such as Delete/Insert/Update and Fetch borrowed books into JTable.
*
*/

package Project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.table.DefaultTableModel;
import org.joda.time.Days;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.joda.time.LocalDate;

public class BorrowedModel extends DefaultTableModel {

    private InitialDB initDB = new InitialDB();
    private ResultSet rs = null;
    private final Vector<String> column = new Vector<>();
    private final Vector<Vector<String>> data = new Vector<>();
    private final String dbUrl = "jdbc:mysql://localhost/" + initDB.getDataBaseName();
    private final String query = "select * from Borrowed";

    public BorrowedModel() {
    }

    public BorrowedModel(Vector<Vector<String>> data, Vector<String> column) {
        super(data, column);
    }

    public Vector<String> getColumn() {
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                Statement st = con.createStatement();) {

            rs = st.executeQuery(query);
            int c = rs.getMetaData().getColumnCount();
            for (int i = 1; i <= c; i++) {
                column.add(rs.getMetaData().getColumnName(i));
            }
        } catch (SQLException sqle) {
        }
        return column;
    }

    public Vector<Vector<String>> getData() {
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                Statement st = con.createStatement();) {
            rs = st.executeQuery(query);
            int c = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Vector<String> eachRow = new Vector<>();

                for (int i = 1; i <= c; i++) {
                    eachRow.add(rs.getString(i));
                }
                data.add(eachRow);
            }
        } catch (SQLException sqle) {
        }
        return data;
    }

    public void deleteBookFromBorrowTable(int bookID) {
        String query = "DELETE FROM borrowed WHERE Book_ID = ?";
        try (Connection con = DriverManager
                .getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, bookID);
            ps.executeUpdate();
        } catch (SQLException sqle) {
        }
    }

    public void insertIntoBorrowTable(int UserID, int BookID, String date) {
        String query = "INSERT INTO borrowed(User_ID,Book_ID,Borrow_Date) VALUES(?,?,?)";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, UserID);
            ps.setInt(2, BookID);
            ps.setString(3, date);
            ps.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void insertIntoBorrowQueueTable(int UserID, int BookID, String rDate) {
        String query = "INSERT INTO borrowedQueue(User_ID,Book_ID,Request_Date) VALUES(?,?,?)";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, UserID);
            ps.setInt(2, BookID);
            ps.setString(3, rDate);
            ps.execute();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void deleteFromBorrowQueueTable(int userID, int bookID) {
        String query2 = "DELETE FROM borrowedQueue WHERE User_ID=? AND Book_ID=?";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query2);) {
            ps.setInt(1, userID);
            ps.setInt(2, bookID);
            boolean done = ps.execute();
            if (done) {
                System.out.println("Remove from queue done!");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public boolean User_BorrowCount(int userID) {
        String query = "Select Book_ID from borrowed where User_ID=?";
        int bookCount = 1;
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                rs.getString("Book_ID");
                bookCount++;
            }
            if (bookCount > 3) {
                return false;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return true;
    }

    public boolean userHasBorrowedBook(int userID) {
        String query = "Select User_ID from borrowed where User_ID=?";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) { // With Data
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    public boolean bookBorrowed(int bookID) {
        String query = "Select Book_ID from borrowed where Book_ID=?";

        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, bookID);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) { // With Data
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return false;
    }

    public boolean user_bookValidation(int userID, int bookID) {
        ResultSet rs = null;
        String query = "SELECT * FROM borrowed WHERE User_ID=? AND Book_ID=? ";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {

            ps.setInt(1, userID);
            ps.setInt(2, bookID);
            rs = ps.executeQuery();
            if (rs.isBeforeFirst()) { // If it is with data
                return true;
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean UserHasLatedReturn(int userID) throws NullPointerException {
        LocalDate todayDate = new LocalDate();
        System.out.println("Local Date in UserHasLatedReturn2(): " + todayDate);
        LocalDate userBorrowDate = getUserBorrowDate(userID);
        if (userBorrowDate == null) {
            return false;
        }
        int difference = Days.daysBetween(userBorrowDate, todayDate).getDays();
        if (difference > 10) { // More than 10 days
            JOptionPane.showMessageDialog(null, "You have " + difference + " Days Delay in returning your previous book", "More Than 30 days delay in book return", JOptionPane.ERROR_MESSAGE);
            return true;
        }
        return false;
    }

    public LocalDate getUserBorrowDate(int userID) {
        String query = "SELECT Borrow_Date FROM Borrowed WHERE User_ID=?";
        LocalDate date = null;
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, userID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                date = LocalDate.fromDateFields(rs.getDate("Borrow_Date"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    public String getTodayDate() {
        LocalDate localDate = new LocalDate();
        return localDate.toString();
    }

    public boolean existInBookOrderQueue(int user_id, int book_id) {
        String query = "Select * from BorrowedQueue where User_ID=? and Book_ID=?";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, user_id);
            ps.setInt(2, book_id);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {     // with data
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean existInBookOrderQueue(int book_id) {
        String query = "Select * from BorrowedQueue where Book_ID=?";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setInt(1, book_id);
            ResultSet rs = ps.executeQuery();
            if (rs.isBeforeFirst()) {     // with data
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getOldestWaitedUserID() {
        String query3 = "SELECT User_id FROM borrowedQueue where "
                + "request_Date in (select min(request_Date) from borrowedQueue) ";
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query3);) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt("User_ID");
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return 0;
    }
}
