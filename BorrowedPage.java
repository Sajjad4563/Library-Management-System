/**
* BorrowedPage.java
* @author Sajjad HTLO
*
* This class caontains a table which showns borrowes events.
* Actually `which user(s) has been borrowed which book(s)` .
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

public class BorrowedPage extends JFrame implements ActionListener {

    private JButton backBtn;
    private JTextField filterTF;
    private JLabel lbl1;
    private final TableRowSorter sorter;
    private final JTable table;
    private final BorrowedModel model, model1;

    public BorrowedPage() {
        setTitle("User - Book Information");
        setLookAndFeel();
        model1 = new BorrowedModel();
        model = new BorrowedModel(model1.getData(), model1.getColumn());

        sorter = new TableRowSorter<TableModel>(model);
        table = new JTable(model);
        table.setRowSorter(sorter);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        add(createPanel(), BorderLayout.NORTH);
        add(new JScrollPane(table), BorderLayout.CENTER);

        RowFilter<BorrowedModel, Object> rf = null;
        rf = RowFilter.regexFilter(filterTF.getText(), 0);
        sorter.setRowFilter(rf);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocation(250, 70);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BorrowedPage();
            }
        });
    }

    public JPanel createPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        filterTF = new JTextField(10);
        filterTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if (filterTF.getText().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(filterTF.getText().trim(), 0));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if (filterTF.getText().length() == 0) {
                    sorter.setRowFilter(null);
                } else {
                    sorter.setRowFilter(RowFilter.regexFilter(filterTF.getText().trim(), 0));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        backBtn = new JButton("Back");
        backBtn.addActionListener(this);
        lbl1 = new JLabel("(Search By User ID)");
        panel.add(backBtn);
        panel.add(filterTF);
        panel.add(lbl1);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backBtn) {
            this.setVisible(false);
            new BookPage_Admin();
        }
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lfe) {
        }
    }
}
