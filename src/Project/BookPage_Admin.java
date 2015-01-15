/**
* BookPage_Admin.java
* @author Sajjad HTLO
*
* This class is for admin-enrty books management.
* And perform all books methods in swing layer and calls bookModel.java methods to perform in database layer.
*
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

public class BookPage_Admin extends JFrame implements ActionListener {

    private final JTable table;
    private final TableRowSorter sorter;
    private final BookModel bModel, bModel1;
    private final UserModel uModel;
    private final BorrowedModel borrModel;
    private JTextField searchTf;
    private JLabel lbl1;
    private JButton backBtn, exitBtn, addBtn, dellBtn, borrowListBtn, editBtn, borrowBtn, returnBtn;

    public BookPage_Admin() {
        setTitle("Book Page - Administration Enter");
        setLookAndFeel();
        bModel1 = new BookModel();
        bModel = new BookModel(bModel1.getData(), bModel1.getColumn());
        borrModel = new BorrowedModel();
        uModel = new UserModel();

        sorter = new TableRowSorter<TableModel>(bModel);
        table = new JTable(bModel);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(createForm(), BorderLayout.PAGE_START);

        RowFilter<BookModel, Object> rf = null;
        rf = RowFilter.regexFilter(searchTf.getText(), 0);
        sorter.setRowFilter(rf);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1025, 600);
        setLocation(200, 60);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookPage_Admin();
            }
        });
    }

    public JPanel createForm() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchTf = new JTextField(10);
        searchTf.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String key = searchTf.getText().trim();
                if (key.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(key, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String key = searchTf.getText().trim();
                if (key.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(key, 1));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // To change body of implemented methods use File | Settings |
                // File Templates.
            }
        });

        backBtn = new JButton("Back");
        addBtn = new JButton("Add New Book");
        dellBtn = new JButton("Delete Book");
        editBtn = new JButton("Edit Book");
        borrowBtn = new JButton("Borrow Book");
        returnBtn = new JButton("Return Book");
        borrowListBtn = new JButton("Borrow Books list");
        exitBtn = new JButton("Exit");
        lbl1 = new JLabel("(Search By Book Name)");
        backBtn.addActionListener(this);
        addBtn.addActionListener(this);
        dellBtn.addActionListener(this);
        editBtn.addActionListener(this);
        borrowBtn.addActionListener(this);
        returnBtn.addActionListener(this);
        borrowListBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        panel.add(addBtn);
        panel.add(dellBtn);
        panel.add(editBtn);
        panel.add(borrowBtn);
        panel.add(returnBtn);
        panel.add(borrowListBtn);
        panel.add(backBtn);
        panel.add(exitBtn);
        panel.add(searchTf);
        panel.add(lbl1);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitBtn) {
            System.exit(1);
        } else if (e.getSource() == addBtn) {
            addAction();
        } else if (e.getSource() == dellBtn) {
            dellAction();
        } else if (e.getSource() == editBtn) {
            editAction();
        } else if (e.getSource() == borrowBtn) {
            borrowAction();
        } else if (e.getSource() == returnBtn) {
            returnAction();
        } else if (e.getSource() == borrowListBtn) {
            this.setVisible(false);
            new BorrowedPage();
        } else if (e.getSource() == backBtn) {
            this.setVisible(false);
            new MainFrame().setVisible(true);
        }
    }

    public void addAction() {
        BookDialog dialog = new BookDialog(this);
        dialog.setBorrowStatus("No");
        if (dialog.getBookName() != null && dialog.getBookIsbn() != null && dialog.getBorrowStatus() != null && dialog.getDate() != null) {
            dialog.setBookID(bModel.insertIntoBooksTableAndSetID(dialog.getBookName(), dialog.getBookIsbn(), dialog.getBorrowStatus(), dialog.getDate()));
            Object[] addedRow = new Object[]{dialog.getBookID(), dialog.getBookName(), dialog.getBookIsbn(), dialog.getBorrowStatus(), dialog.getDate()};
            bModel.addRow(addedRow);
        }
    }

    public void dellAction() {
        if (table.getSelectedRow() > -1) {
            int n = JOptionPane.showConfirmDialog(null, "Are You Sure To Delete?", "Warning", JOptionPane.OK_CANCEL_OPTION);
            if (n == JOptionPane.OK_OPTION) {
                int rowToTable = table.getSelectedRow();
                int rowToModel = table.convertRowIndexToModel(rowToTable);
                int bookId = Integer.parseInt((String) table.getValueAt(
                        rowToTable, 0));
                if (!borrModel.bookBorrowed(bookId)) {
                    bModel.deleteFromBooksTable(rowToModel);
                    borrModel.deleteBookFromBorrowTable(bookId);
                } else {
                    JOptionPane
                            .showMessageDialog(this,
                                    "Cannot Delete, This Book Has Been Borrowed By A Person");
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select A Row");
        }
    }

    private void borrowAction() {
        if (table.getSelectedRow() > -1) {
            try {
                if (String.valueOf(table.getValueAt(table.getSelectedRow(), 3)).equalsIgnoreCase("No")) {
                    int rowInTable = table.getSelectedRow();
                    int rowInModel = table.convertRowIndexToModel(rowInTable);
                    // determine Which user wants to borrow a book (By user ID)
                    String userId = JOptionPane.showInputDialog("Enter User ID", "").trim();
                    if (uModel.userID_Exists(Integer.parseInt(userId))) {
                        if (borrModel.User_BorrowCount(Integer.parseInt(userId))) {
                            if (!borrModel.UserHasLatedReturn(Integer.parseInt(userId))) {
                                bModel.booksTableBorrowChanged(rowInModel);
                                int bookId = Integer.parseInt(String.valueOf(table.getValueAt(rowInTable, table.convertRowIndexToView(0))));
                                String date = borrModel.getTodayDate();
                                borrModel.insertIntoBorrowTable(Integer.parseInt(userId), bookId, date);
                            } else {
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "This user Can't Borrow further more");
                        }

                    } else {
                        JOptionPane
                                .showMessageDialog(this, "User ID Not Found");
                    }

                } else {
                    JOptionPane.showMessageDialog(this, "This Book has been borrowed by another person");
                }
            } catch (NumberFormatException nfe) {
                JOptionPane.showMessageDialog(null, "Enter Correct Value", "Invalid Entry", JOptionPane.OK_OPTION);
                nfe.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a Record");
        }
    }

    public void editAction() {
        if (table.getSelectedRow() > -1) {
            int rowInTable = table.getSelectedRow();
            int rowInModel = table.convertRowIndexToModel(rowInTable);
            BookDialog dialog = new BookDialog();

            dialog.setName(String.valueOf(table.getValueAt(rowInTable, 1)));
            dialog.setBookIsbn(String.valueOf(table.getValueAt(rowInTable, 2)));
            dialog = new BookDialog(this, dialog.getName(),
                    dialog.getBookIsbn());

            if (dialog.getBookName() != null && dialog.getBookIsbn() != null) {
                bModel.updateBooksTable(rowInModel, dialog.getBookName(),
                        dialog.getBookIsbn());
            } else {
                System.out.println("Some thing is null!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select A Record");
        }
    }

    private void returnAction() {
        System.out.println("in return Action method");
        if (table.getSelectedRow() > -1) {
            if (String.valueOf(table.getValueAt(table.getSelectedRow(), 3)).equalsIgnoreCase("Yes")) { // IF BORROWED IS YES
                int rowInTable = table.getSelectedRow();
                int rowInModel = table.convertRowIndexToModel(rowInTable);
                // determine Which user wants to borrow a book (By user ID)
                String user_id = JOptionPane.showInputDialog("Enter User ID", "");
                if (user_id == null) {
//                    System.out.println("cancel selected");
                } else {
                    try {
//                        System.out.println("ok selected");
                        int user_ID = Integer.parseInt(user_id.trim());
                        int bookId = Integer.parseInt(String.valueOf(bModel.getValueAt(rowInModel, 0)));
                        if (borrModel.user_bookValidation(user_ID, bookId)) {// If this book borrowed by this user

                            bModel.booksTableBorrowChanged(rowInModel);
                            borrModel.deleteBookFromBorrowTable(bookId);
                            // See for waiting order queue
                            if (borrModel.existInBookOrderQueue(bookId)) {
                                JOptionPane.showMessageDialog(null, "Book is Given To Next Person In the Queue");
                                // Give to waiting user in queue
                                int userQid = borrModel.getOldestWaitedUserID();
                                if (userQid != 0) {
                                    if (uModel.userID_Exists(userQid)) {
                                        if (borrModel.User_BorrowCount(userQid)) {
                                            if (!borrModel.UserHasLatedReturn(userQid)) {
                                                bModel.booksTableBorrowChanged(rowInModel);
                                                String date = borrModel.getTodayDate();
                                                borrModel.insertIntoBorrowTable(userQid, bookId, date);
                                                borrModel.deleteFromBorrowQueueTable(userQid, bookId);
                                            } else {
                                                JOptionPane.showMessageDialog(this, "Error, More Than 30 days delay in book return.");
                                            }

                                        } else {
                                            JOptionPane.showMessageDialog(this, "This user Can't Borrow further more");
                                        }
                                    }
                                }

                            } else {
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "This User cannot return this book");
                        }
                    } catch (NumberFormatException nfe) {
                        JOptionPane.showMessageDialog(null, "Enter Correct Value", "Invalid Entry", JOptionPane.OK_OPTION);
                        nfe.printStackTrace();
                    } catch (Exception e) {
                        JOptionPane.showMessageDialog(null, "There is an error");
                        e.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "This Book has been not borrowed yet");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a Record");
        }
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lfe) {
        }
    }
}
