/**
* UserDialog.java
* @author Sajjad HTLO
*
* This is a Dialog window to add new users to library. In this class i used some JFrame features to prevent entring invalid input.
*
*/

package Project;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class UserDialog extends JDialog implements ActionListener {

    JButton cancelBtn, okBtn;
    JTextField fNameTf, lNameTf;
    JRadioButton maleRb, femaleRb;
    ButtonGroup group;
    JLabel fNameLbl, fNamePicLbl, lNameLbl, lNamePicLbl, genderLbl, tempBtn, temp3;
    ImageIcon checkImg = new ImageIcon("Check.png");
    ImageIcon errorImg = new ImageIcon("error.png");
    private String fName = null;
    private String lName = null;
    private String gender = null;
    private String date = null;
    private String ID = null;

    public UserDialog() {
    }

    public UserDialog(JFrame owner) {
        super(owner, "New User Information", true);
        add(createForm(), BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(okBtn);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(400, 100);
        pack();
        setVisible(true);
    }

    public UserDialog(JFrame owner, String oldFName, String oldLName,
            String oldGender) {
        super(owner, "Edit User Information", true);
        setLookAndFeel();
        add(createForm(), BorderLayout.CENTER);
        this.getRootPane().setDefaultButton(okBtn);
        fNameTf.setText(oldFName);
        lNameTf.setText(oldLName);
        if (oldGender.equals("Male")) {
            maleRb.setSelected(true);
        } else {
            femaleRb.setSelected(true);
        }

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocation(400, 100);
        setSize(550, 410);
        setVisible(true);
        pack();
    }

    public JPanel createForm() {
        JPanel panel = new JPanel();
        okBtn = new JButton("Ok");
        cancelBtn = new JButton("Cancel");
        tempBtn = new JLabel();
        fNameLbl = new JLabel("First Name");
        fNamePicLbl = new JLabel();
        fNamePicLbl.setVisible(false);
        lNameLbl = new JLabel("Last Name");
        lNamePicLbl = new JLabel();
        lNamePicLbl.setVisible(false);
        genderLbl = new JLabel("Gender");
        maleRb = new JRadioButton("Male");
        femaleRb = new JRadioButton("Female");
        temp3 = new JLabel();
        group = new ButtonGroup();
        group.add(maleRb);
        group.add(femaleRb);

        fNameTf = new JTextField(10);
        fNameTf.setName("FnTF");
        fNameTf.setInputVerifier(new MyVerifier(new JComponent[]{maleRb,
            femaleRb, okBtn}));
        lNameTf = new JTextField(10);
        lNameTf.setName("LnTF");
        lNameTf.setInputVerifier(new MyVerifier(new JComponent[]{maleRb,
            femaleRb, okBtn}));

        panel.add(fNameLbl);
        panel.add(fNameTf);
        panel.add(fNamePicLbl);
        panel.add(lNameLbl);
        panel.add(lNameTf);
        panel.add(lNamePicLbl);
        panel.add(genderLbl);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        radioPanel.add(maleRb);
        radioPanel.add(femaleRb);
        panel.add(radioPanel);
        panel.add(temp3);
        panel.add(okBtn);
        okBtn.addActionListener(this);
        panel.add(cancelBtn);
        cancelBtn.addActionListener(this);
        panel.add(tempBtn);

        panel.setLayout(new SpringLayout());
        SpringUtilities.makeCompactGrid(panel, 4, 3, 50, 10, 80, 60);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cancelBtn) {
            this.dispose();
        } else if (e.getSource() == okBtn) {
            if (okAction()) {
                this.dispose();
            }
        }
    }

    public boolean okAction() {
        if (fNameTf.getText().trim().length() != 0
                && lNameTf.getText().trim().length() != 0) {
            seFfName(fNameTf.getText().trim());
            setLName(lNameTf.getText().trim());
        } else {
            return false;
        }

        if (!genderControl()) {
            return false;
        }
        this.setDate(dateGenerate());
        return true;
    }

    public boolean genderControl() {
        if (maleRb.isSelected()) {
            setGender("Male");
            return true;
        } else if (femaleRb.isSelected()) {
            setGender("Female");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Select Gender");
            return false;
        }
    }

    public String dateGenerate() {
        Calendar todayDate = Calendar.getInstance();
        SimpleDateFormat simpleFormat = new SimpleDateFormat("YYYY/MM/dd");
        String strDate = simpleFormat.format(todayDate.getTime());
        return strDate;
    }

    public String getFName() {
        return fName;
    }

    public void seFfName(String fName) {
        this.fName = fName;
    }

    public String getLName() {
        return lName;
    }

    public void setLName(String lName) {
        this.lName = lName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lafe) {
            lafe.printStackTrace();
        }
    }

    public class MyVerifier extends InputVerifier {

        private JComponent[] component;

        public MyVerifier(JComponent[] components) {
            component = components;
        }

        @Override
        public boolean verify(JComponent input) {
            String componentName = input.getName();

            if (componentName.equals("FnTF")) {
                String text = ((JTextField) input).getText().trim();
                if (text.matches(".*\\d.*") || text.length() == 0) {
                    return false;
                }
            } else if (componentName.equals("LnTF")) {
                String text = ((JTextField) input).getText();
                if (text.matches(".*\\d.*") || text.length() == 0) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean shouldYieldFocus(JComponent input) {
            boolean isValidDate = verify(input);
            if (isValidDate) {
                for (JComponent r : component) {
                    r.setEnabled(true);
                }
            } else {
                for (JComponent r : component) {
                    r.setEnabled(false);
                }
            }
            return isValidDate;
        }
    }
}
