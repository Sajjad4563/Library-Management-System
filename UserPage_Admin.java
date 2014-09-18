/**
* UserPage_Admin.java
* @author Sajjad HTLO
*
* This class is for management of users section in `admin` mode entry.
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
import java.util.regex.PatternSyntaxException;

public class UserPage_Admin extends JFrame implements ActionListener {

    private final JTable table;
    private final UserModel uModel, uModel1;
    private final BorrowedModel bModel;
    private final TableRowSorter sorter;
    private JTextField filterTF;
    private JLabel filterLbl;
    private JButton addBtn, dellBtn, editBtn, exitBtn, backBtn;

    public UserPage_Admin() {
        setLookAndFeel();
        setTitle("User Information (Admin Enter Mode) ");
        bModel = new BorrowedModel();
        uModel1 = new UserModel();
        uModel = new UserModel(uModel1.getData(), uModel1.getColumn());

        sorter = new TableRowSorter<TableModel>(uModel);
        table = new JTable(uModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSorter(sorter);

        add(new JScrollPane(table), BorderLayout.CENTER);
        add(panelForm(), BorderLayout.PAGE_START);

        RowFilter<UserModel, Object> rf = null;
        try {
            rf = RowFilter.regexFilter(filterTF.getText(), 0);
        } catch (PatternSyntaxException pse) {
            return;
        }
        sorter.setRowFilter(rf);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 600);
        setLocation(270, 80);
        setVisible(true);

    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new UserPage_Admin();
            }
        });
    }

    public JPanel panelForm() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addBtn = new JButton("Add New User");
        dellBtn = new JButton("Delete User");
        editBtn = new JButton("Edit User");
        backBtn = new JButton("Back");
        exitBtn = new JButton("Exit");
        filterTF = new JTextField(10);
        filterLbl = new JLabel("(Search by First Name)");
        filterTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String key = filterTF.getText();
                if (key.length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(key, 1));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String key = filterTF.getText();
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

        addBtn.addActionListener(this);
        dellBtn.addActionListener(this);
        editBtn.addActionListener(this);
        exitBtn.addActionListener(this);
        backBtn.addActionListener(this);

        panel.add(addBtn);
        panel.add(editBtn);
        panel.add(dellBtn);
        panel.add(backBtn);
        panel.add(exitBtn);
        panel.add(filterTF);
        panel.add(filterLbl);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addBtn) {
            userAddAction();
        } else if (e.getSource() == dellBtn) {
            userDellAction();
        } else if (e.getSource() == editBtn) {
            userEditAction();
        } else if (e.getSource() == exitBtn) {
            System.exit(1);
        } else if (e.getSource() == backBtn) {
            this.setVisible(false);
            new MainFrame().setVisible(true);
        }
    }

    public void userAddAction() {
        UserDialog dialog = new UserDialog(this);
        if (dialog.getFName() != null && dialog.getLName() != null
                && dialog.getGender() != null) {
            dialog.setID(uModel.insertIntoUsersTableAndSetID(dialog.getFName(),
                    dialog.getLName(), dialog.getGender(), dialog.getDate()));
            Object[] myRow = new Object[]{dialog.getID(), dialog.getFName(),
                dialog.getLName(), dialog.getGender(), dialog.getDate()};
            uModel.addRow(myRow);
        }
    }

    public void userDellAction() {
        if (table.getSelectedRow() > -1) {
            int n = JOptionPane.showConfirmDialog(null, "Are you sure to delete?", "Warning", JOptionPane.OK_CANCEL_OPTION);
            if (n == JOptionPane.OK_OPTION) {
                int rowToDelete = table.getSelectedRow();
                int rowToModel = table.convertRowIndexToModel(rowToDelete);
                int userID = Integer.parseInt(String.valueOf(table.getValueAt(rowToDelete, 0)));

                // don't delete if user borrowed a book and not return yet
                if (userHasABorrowedBook(userID)) {
                    JOptionPane.showMessageDialog(this, "You Can not Delete This Users, This User Has a Borrowed Book");
                } else {
                    uModel.deleteFromUserTable(rowToModel);
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select A Record");
        }
    }

    public void userEditAction() {
        if (table.getSelectedRow() > -1) {
            String oldFName = table.getValueAt(table.getSelectedRow(), 1).toString();
            String oldLName = table.getValueAt(table.getSelectedRow(), 2).toString();
            String oldGender = table.getValueAt(table.getSelectedRow(), 3).toString();
            UserDialog dialog = new UserDialog();
            dialog.seFfName(oldFName);
            dialog.setLName(oldLName);
            dialog.setGender(oldGender);

            dialog = new UserDialog(this, dialog.getFName(), dialog.getLName(),
                    dialog.getGender());
            if (dialog.getFName() != null && dialog.getLName() != null && dialog.getGender() != null) {
                int rowInTable = table.getSelectedRow();
                int rowInModel = table.convertRowIndexToModel(rowInTable);
                uModel.updateUser(rowInModel, dialog.getFName(),
                        dialog.getLName(), dialog.getGender());
            } else {
//                System.out.println("There is some null!");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Select A Row");
        }
    }

    private boolean userHasABorrowedBook(int userID) {
        if (bModel.userHasBorrowedBook(userID)) {
            return true;
        }
        return false;
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lfe) {
            lfe.printStackTrace();
        }
    }
}
