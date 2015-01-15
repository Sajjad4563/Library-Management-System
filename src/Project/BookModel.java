/**
* BookModel.java
* @author Sajjad HTLO
*
* A child of books that handles all book methods, such as Insert.Delete,Update, and Fetch data into JTable.
*
*/

package Project;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

public class BookModel extends DefaultTableModel {

    private InitialDB initDB = new InitialDB();

    private final String dbUrl = "jdbc:mysql://localhost/" + initDB.getDataBaseName();
    private final String BooksQuery = "SELECT * FROM books";
    private Vector<String> column = new Vector<>();
    private Vector<Vector<String>> data = new Vector<>();
    private ResultSet rs = null;

    public BookModel() {
    }

    public BookModel(Vector<Vector<String>> data, Vector<String> columns) {
        super(data, columns);
    }

    public Vector<String> getColumn() {
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                Statement st = con.createStatement();) {
            rs = st.executeQuery(BooksQuery);
            int c = rs.getMetaData().getColumnCount();

            for (int i = 1; i <= c; i++) {
                column.add(rs.getMetaData().getColumnName(i));
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return column;
    }

    public Vector<Vector<String>> getData() {
        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                Statement st = con.createStatement();) {

            rs = st.executeQuery(BooksQuery);
            int c = rs.getMetaData().getColumnCount();

            while (rs.next()) {
                Vector<String> eachRow = new Vector<>(c);
                for (int i = 1; i <= c; i++) {
                    eachRow.add(rs.getString(i));
                }
                data.add(eachRow);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return data;
    }

    public void deleteFromBooksTable(int rowInModel) {
        Object id = this.getValueAt(rowInModel, 0);
        String query = "DELETE FROM Books WHERE Book_ID = ?";

        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query);) {
            ps.setObject(1, id);
            if (ps.executeUpdate() == 1) {
                this.removeRow(rowInModel);
            }

        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public String insertIntoBooksTableAndSetID(String name, String isbn, String borrow, String date) {
        String id = null;
        ResultSet rs = null;
        String query = "INSERT INTO Books(Book_Name,Book_ISBN,Borrow_Status,BUY_DATE)"
                + " values(?,?,?,?) ";

        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query,
                        java.sql.Statement.RETURN_GENERATED_KEYS);) {

            ps.setString(1, name);
            ps.setString(2, isbn);
            ps.setString(3, borrow);
            ps.setString(4, date);
            ps.execute();
            rs = ps.getGeneratedKeys();

            while (rs.next()) {
                id = String.valueOf(rs.getInt(1));
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return id;
    }

    public void updateBooksTable(int rowInTable, String newName, String newIsbn) {

        String query = "UPDATE books SET Book_Name=? , Book_ISBN=? where Book_ID=? ";
        Object id = this.getValueAt(rowInTable, 0);

        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps = con.prepareStatement(query)) {
            ps.setObject(1, newName);
            ps.setObject(2, newIsbn);
            ps.setObject(3, id);
            ps.addBatch();
            if (ps.executeUpdate() == 1) {
                this.setValueAt(newName, rowInTable, 1);
                this.setValueAt(newIsbn, rowInTable, 2);
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }

    public void booksTableBorrowChanged(int rowInModel) {
        Object bookId = this.getValueAt(rowInModel, 0);
        String query1 = "SELECT borrow_Status FROM books WHERE Book_ID=" + bookId;
        String query2 = "UPDATE books SET borrow_Status=? WHERE Book_ID=?";

        try (Connection con = DriverManager.getConnection(dbUrl, "root", initDB.getMysqlPassword());
                PreparedStatement ps1 = con.prepareStatement(query1);
                PreparedStatement ps2 = con.prepareStatement(query2);
                ResultSet rs = ps1.executeQuery();) {

            while (rs.next()) {
                if (String.valueOf(rs.getString("Borrow_Status")).equalsIgnoreCase("No")) {
                    ps2.setString(1, "Yes");
                    ps2.setObject(2, bookId);
                    if (ps2.executeUpdate() == 1) {
                        this.setValueAt("Yes", rowInModel, 3);
                    }
                } // This section of code is for return Button.
                else {
                    ps2.setString(1, "No");
                    ps2.setObject(2, bookId);
                    if (ps2.executeUpdate() == 1) {
                        this.setValueAt("No", rowInModel, 3);
                    }
                }
            }
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
    }
}
