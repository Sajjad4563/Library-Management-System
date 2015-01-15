/**
* BookPage_User.java
* @author Sajjad HTLO
*
* This class is for user-enrty books management.
* User just can see available books and their informations and submit some borrow requests.
*
*/

package Project;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class BookPage_User extends JFrame implements ActionListener {

    private final int userId;
    private JButton backBtn, exitBtn, borrBtn;
    private JLabel userInfo, searchLbl;
    private JTextField filterTF;
    private final TableRowSorter sorter;
    private final JTable table;
    private final BookModel bookModel, bookModel1;
    private final BorrowedModel borrModel;

    public BookPage_User(String[] enterUserInfo, int userId) {
        setLookAndFeel();
        setTitle("Book Information - User Enter");
        borrModel = new BorrowedModel();
        bookModel1 = new BookModel();
        bookModel = new BookModel(bookModel1.getData(), bookModel1.getColumn());

        sorter = new TableRowSorter<TableModel>(bookModel);
        table = new JTable(bookModel);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(createForm(), BorderLayout.NORTH);
        userInfo.setText("Entered User is:   " + Arrays.deepToString(enterUserInfo));
        this.userId = userId;
        add(new JScrollPane(table), BorderLayout.CENTER);

        RowFilter<BookModel, Object> rf = null;
        rf = RowFilter.regexFilter(filterTF.getText(), 0);
        sorter.setRowFilter(rf);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocation(270, 80);
        setVisible(true);
    }

    public JPanel createForm() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        backBtn = new JButton("Back");
        exitBtn = new JButton("Exit");
        borrBtn = new JButton("Borrow Book");
        userInfo = new JLabel();
        searchLbl = new JLabel("(Search By Book Name)");
        filterTF = new JTextField(10);
        filterTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String key = filterTF.getText().trim();
                if (key.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(key, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String key = filterTF.getText().trim();
                if (key.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(key, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        backBtn.addActionListener(this);
        borrBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        panel.add(userInfo);
        panel.add(filterTF);
        panel.add(searchLbl);
        panel.add(borrBtn);
        panel.add(backBtn);
        panel.add(exitBtn);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.setVisible(false);
            new LoginFrame().setVisible(true);
        } else if (e.getSource() == borrBtn) {
            borrowAction_User();
        } else if (e.getSource() == exitBtn) {
            System.exit(1);
        }
    }

    public int getUserId() {
        return userId;
    }

    private void borrowAction_User() {
        if (table.getSelectedRow() > -1) {
            try {
                if (String.valueOf(table.getValueAt(table.getSelectedRow(), 3)).equalsIgnoreCase("No")) {
                    int rowInTable = table.getSelectedRow();
                    int rowInModel = table.convertRowIndexToModel(rowInTable);
                    if (borrModel.User_BorrowCount(getUserId())) {
                        if (!borrModel.UserHasLatedReturn(getUserId())) {
                            bookModel.booksTableBorrowChanged(rowInModel);
                            int bookId = Integer.parseInt(String.valueOf(table.getValueAt(rowInTable, table.convertColumnIndexToView(0))));
                            String date = borrModel.getTodayDate();
                            borrModel.insertIntoBorrowTable(getUserId(), bookId, date);
                        } else {
                            JOptionPane.showMessageDialog(this, "Error, More Than 30 days delay in book return.");
                        }

                    } else {
                        JOptionPane.showMessageDialog(this, "This user Can't Borrow further more");
                    }

                } else { // User going to waiting queue
                    borrowActionQueue_User();
                }
            } catch (NullPointerException npe) {
                npe.printStackTrace();
                JOptionPane.showMessageDialog(this, "Null pointer exception happend");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a Book and then select Borrow on the top");
        }
    }

    private void borrowActionQueue_User() {
        int rowInTable = table.getSelectedRow();
        int bookId = Integer.parseInt(String.valueOf(table.getValueAt(rowInTable, table.convertColumnIndexToView(0))));
        if (borrModel.User_BorrowCount(getUserId())) {
            if (!borrModel.UserHasLatedReturn(getUserId())) {
                if (!borrModel.existInBookOrderQueue(getUserId(), bookId)) {
                    String rDate = borrModel.getTodayDate();
                    borrModel.insertIntoBorrowQueueTable(getUserId(), bookId, rDate);
                    JOptionPane.showMessageDialog(this, "Your request submitted, Yo can get this book as soon as returned");
                } else {
                    JOptionPane.showMessageDialog(this, "Duplicate Order");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error, More Than 30 days delay in book return.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "This user Can't Borrow further more");
        }
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lfe) {
        }
    }
}
