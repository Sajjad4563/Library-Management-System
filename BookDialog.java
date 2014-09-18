/**
* BookDialog.java
* @author Sajjad HTLO
*
* A child of books that have a form to add new book.
*
*/

package Project;

import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class BookDialog extends JDialog implements ActionListener {

    private javax.swing.JButton cancelBtn;
    private javax.swing.JTextField isbnTf = null;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JTextField nameTf = null;
    private javax.swing.JButton saveBtn;
    private String bookName = null;
    private String bookIsbn = null;
    private String date = null;
    private String borrowStatus = null;
    private String bookID = null;

    public BookDialog(JFrame owner) {
        super(owner, "Add New Book", true);
        setLookAndFeel();
        initComponents();
        this.getRootPane().setDefaultButton(saveBtn);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(400, 100);
        setVisible(true);
    }

    // constructor for Edit 
    public BookDialog(JFrame owner, String oldName, String oldIsbn) {
        super(owner, "Edit Book Information", true);
        setLookAndFeel();
        initComponents();
        this.getRootPane().setDefaultButton(saveBtn);
        nameTf.setText(oldName);
        isbnTf.setText(oldIsbn);

        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(400, 100);
        setVisible(true);
    }

    public BookDialog() { // constructor for instance creation
        setLookAndFeel();
        initComponents();
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocation(400, 100);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BookDialog().setVisible(true);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        saveBtn = new JButton();
        cancelBtn = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameTf = new javax.swing.JTextField();
        nameTf.setName("NTF");
        nameTf.setInputVerifier(new myVerifier(new JComponent[]{saveBtn}));
        isbnTf = new JTextField();
        isbnTf.setName("ISBNTF");
        isbnTf.setInputVerifier(new myVerifier(new JComponent[]{saveBtn}));
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        saveBtn.setText("Save");
        saveBtn.addActionListener(this);

        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(this);

        jLabel1.setText("Book Name");

        jLabel2.setText("ISBN");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(51, 0, 0));
        jLabel3.setText("Enter Book Information");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
                getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        layout.createSequentialGroup()
                        .addGroup(
                                layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(
                                        layout.createSequentialGroup()
                                        .addGap(145,
                                                145,
                                                145)
                                        .addComponent(
                                                jLabel3))
                                .addGroup(
                                        layout.createParallelGroup(
                                                javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                layout.createSequentialGroup()
                                                .addGap(59,
                                                        59,
                                                        59)
                                                .addGroup(
                                                        layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(
                                                                jLabel2)
                                                        .addComponent(
                                                                jLabel1))
                                                .addGap(33,
                                                        33,
                                                        33)
                                                .addGroup(
                                                        layout.createParallelGroup(
                                                                javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(
                                                                nameTf)
                                                        .addComponent(
                                                                isbnTf)))
                                        .addGroup(
                                                javax.swing.GroupLayout.Alignment.LEADING,
                                                layout.createSequentialGroup()
                                                .addGap(97,
                                                        97,
                                                        97)
                                                .addComponent(
                                                        saveBtn)
                                                .addGap(50,
                                                        50,
                                                        50)
                                                .addComponent(
                                                        cancelBtn))))
                        .addGap(130, 130, 130)));
        layout.setVerticalGroup(layout
                .createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(
                        javax.swing.GroupLayout.Alignment.TRAILING,
                        layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel3)
                        .addGap(64, 64, 64)
                        .addGroup(
                                layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel1)
                                .addComponent(
                                        nameTf,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(33, 33, 33)
                        .addGroup(
                                layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel2)
                                .addComponent(
                                        isbnTf,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(
                                javax.swing.LayoutStyle.ComponentPlacement.RELATED,
                                112, Short.MAX_VALUE)
                        .addGroup(
                                layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(saveBtn)
                                .addComponent(cancelBtn))
                        .addGap(31, 31, 31)));

        pack();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            dispose();
        } else if (e.getSource() == saveBtn) {
            saveAction();
        }
    }

    public void saveAction() {
        setBookName(nameTf.getText().trim());
        setBookIsbn(isbnTf.getText().trim());
        if (getBookName() != null && getBookIsbn() != null) {
            setDate(getTodayDate());
            dispose();
        } else {
            JOptionPane.showMessageDialog(null,
                    "Book Name Or ISBN number isn't correct");
        }
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        if (bookName.length() > 0 && !bookName.matches(".*\\d.*")) {
            this.bookName = bookName;
        }
    }

    public String getBookIsbn() {
        return bookIsbn;
    }

    public void setBookIsbn(String bookIsbn) {
        if (bookIsbn.length() > 0 && !bookIsbn.matches(".*[a-zA-Z].*")) {
            this.bookIsbn = bookIsbn;
        }
    }

    public String getTodayDate() {
        Calendar todayDate = Calendar.getInstance();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("YYYY/MM/dd");
        String strDate = simpleFormat.format(todayDate.getTime());
        return strDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getBorrowStatus() {
        return borrowStatus;
    }

    public void setBorrowStatus(String borrowStatus) {
        this.borrowStatus = borrowStatus;
    }

    public String getBookID() {
        return bookID;
    }

    public void setBookID(String bookID) {
        this.bookID = bookID;
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lfe) {
        }
    }

    public class myVerifier extends InputVerifier {

        private final JComponent[] component;

        public myVerifier(JComponent[] components) {
            component = components;
        }

        @Override
        public boolean verify(JComponent input) {

            String componentName = input.getName();

            if (componentName.equals("NTF")) {
                String bookName = ((JTextField) input).getText().trim();
                if (bookName.matches(".*\\d.*") || bookName.length() == 0) {
                    return false;
                }
            } else if (componentName.equals("ISBNTF")) {
                String isbn = ((JTextField) input).getText().trim();
                if (isbn.matches(".*\\d.*") || isbn.length() == 0) {
                    return true;
                }
            }
            return true;
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            boolean hasValidData = verify(input);
            if (hasValidData) {
                for (JComponent r : component) {
                    r.setEnabled(true);
                }
            } else {
                for (JComponent r : component) {
                    r.setEnabled(false);
                }
            }
            return hasValidData;
        }
    }
}
