/**
* MainFrame.java
* @author Sajjad HTLO
*
* This is main page of application, You can see `Books` and `User` sections and some menus.
*
*/

package Project;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public final class MainFrame extends JFrame implements ActionListener {

    SimpleDateFormat date_Format = new SimpleDateFormat("MMM dd, yyyy HH:mm:ss");
    private javax.swing.JMenuItem AboutMenuItem;
    private javax.swing.JMenuItem AllBooksMenu;
    private javax.swing.JMenuItem AllUserMenu;
    private javax.swing.JMenu bookMenu;
    private javax.swing.JButton exitBtn;
    private javax.swing.JMenuItem ExitMenuItem;
    private javax.swing.JMenu FileMenu;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JButton bookPageBtn;
    private javax.swing.JLabel mainLbl;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JButton userPageBtn;
    private javax.swing.JMenu UserMenu;
    private javax.swing.JLabel bookCounterLbl;
    private javax.swing.JButton switchLoginBtn;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel timeLbl;
    private javax.swing.JLabel userCounterLbl;

    public MainFrame() {
        setLookAndFeel();
        setTitle("Library Management System (Main Page)");
        setPic(this);
        initComponents();
        bookPageBtn.addActionListener(this);
        userPageBtn.addActionListener(this);
        setLocation(300, 50);
        setSize(731, 550);
        showTime();
        this.setResizable(false);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    public void showTime() {
        Timer timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Calendar time = Calendar.getInstance();
                timeLbl.setText(date_Format.format(time.getTime()));
            }
        });
        timer.setRepeats(true);
        timer.start();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu4 = new javax.swing.JMenu();
        jMenu5 = new javax.swing.JMenu();
        bookPageBtn = new javax.swing.JButton();
        bookCounterLbl = new javax.swing.JLabel();
        userCounterLbl = new javax.swing.JLabel();
        mainLbl = new javax.swing.JLabel();
        userPageBtn = new javax.swing.JButton();
        exitBtn = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        timeLbl = new javax.swing.JLabel();
        switchLoginBtn = new javax.swing.JButton();
        mainMenuBar = new javax.swing.JMenuBar();
        FileMenu = new javax.swing.JMenu();
        ExitMenuItem = new javax.swing.JMenuItem();
        UserMenu = new javax.swing.JMenu();
        AllUserMenu = new javax.swing.JMenuItem();
        bookMenu = new javax.swing.JMenu();
        AllBooksMenu = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        AboutMenuItem = new javax.swing.JMenuItem();

        jMenu4.setText("File");
        jMenuBar1.add(jMenu4);

        jMenu5.setText("Edit");
        jMenuBar1.add(jMenu5);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(730, 516));
        setResizable(false);

        bookPageBtn.setText("Book Page");

        bookCounterLbl.setText("         ");

        userCounterLbl.setText("        ");

        mainLbl.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        mainLbl.setForeground(new java.awt.Color(102, 0, 0));
        mainLbl.setText("Library Management System");

        userPageBtn.setText("User Page");

        exitBtn.setText("Exit");
        exitBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitButtonActionPerformed(evt);
            }
        });

        timeLbl.setText("  ");

        switchLoginBtn.setText("Switch Login");
        switchLoginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchButtonActionPerformed(e);
            }
        });

        FileMenu.setText("File");

        ExitMenuItem.setText("Exit");
        ExitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ExitMenuItemActionPerformed(evt);
            }
        });
        FileMenu.add(ExitMenuItem);

        mainMenuBar.add(FileMenu);

        UserMenu.setText("User");

        AllUserMenu.setText("All Users");
        AllUserMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllUserMenuActionPerformed(evt);
            }
        });
        UserMenu.add(AllUserMenu);

        mainMenuBar.add(UserMenu);

        bookMenu.setText("Book");

        AllBooksMenu.setText("All Books");
        AllBooksMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AllBooksMenuActionPerformed(evt);
            }
        });
        bookMenu.add(AllBooksMenu);

        mainMenuBar.add(bookMenu);

        helpMenu.setText("Help");

        AboutMenuItem.setText("About");
        AboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(AboutMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(59, 59, 59)
                                                .addComponent(mainLbl))
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 379, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(layout.createSequentialGroup()
                                .addGap(54, 54, 54)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(timeLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 190, Short.MAX_VALUE)
                                                .addComponent(switchLoginBtn)
                                                .addGap(18, 18, 18)
                                                .addComponent(exitBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(bookCounterLbl)
                                                        .addComponent(bookPageBtn))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(userCounterLbl)
                                                        .addComponent(userPageBtn))))
                                .addGap(56, 56, 56))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(mainLbl)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(43, 43, 43)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(userCounterLbl)
                                        .addComponent(bookCounterLbl))
                                .addGap(37, 37, 37)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(userPageBtn)
                                        .addComponent(bookPageBtn))
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                                .addGap(144, 350, 400)
                                                .addComponent(timeLbl)
                                                .addContainerGap(32, Short.MAX_VALUE))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                        .addComponent(exitBtn)
                                                        .addComponent(switchLoginBtn))
                                                .addContainerGap())))
        );

        pack();
    }

    private void ExitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(1);
    }

    private void AllUserMenuActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new UserPage_Admin();
    }

    private void AllBooksMenuActionPerformed(java.awt.event.ActionEvent evt) {
        this.setVisible(false);
        new BookPage_Admin();
    }

    private void ExitButtonActionPerformed(java.awt.event.ActionEvent evt) {
        System.exit(1);
    }

    private void AboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {
        new AboutFrame(this).setVisible(true);
    }

    private void switchButtonActionPerformed(ActionEvent evt) {
        this.setVisible(false);
        new LoginFrame().setVisible(true);
    }

    public final void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(new NimbusLookAndFeel());
        } catch (UnsupportedLookAndFeelException lfe) {
        }
    }

    public void setPic(JFrame jframe) {
        try {
            File f = new File("pics\\Abstract Blue Wave Line.jpg");
            BufferedImage img = ImageIO.read(f);
            jframe.setContentPane(new SetMyImage(img));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == bookPageBtn) {
            this.setVisible(false);
            new BookPage_Admin();
        } else if (e.getSource() == userPageBtn) {
            this.setVisible(false);
            new UserPage_Admin();
        }
    }
}
