package view;

import presenter.LoginPresenterI;

public class LoginView extends javax.swing.JFrame implements LoginViewI {

    private LoginPresenterI loginPresenter;

    /**
     * Creates new form MyLoginView
     */
    public LoginView() {
        initComponents();
    }


    @SuppressWarnings("unchecked")
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBounds(new java.awt.Rectangle(0, 0, 0, 0));
        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWidths = new int[] {0, 7, 0};
        layout.rowHeights = new int[] {0, 7, 0, 7, 0};
        getContentPane().setLayout(layout);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setText("Welcome");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        getContentPane().add(jLabel1, gridBagConstraints);

        jTextField1.setColumns(13);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jTextField1, gridBagConstraints);

        jLabel2.setText("User");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        getContentPane().add(jLabel2, gridBagConstraints);

        jButton1.setText("Login");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        getContentPane().add(jButton1, gridBagConstraints);

        pack();
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {
        getPresenter().login();
    }

    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField jTextField1;



    public void updateModelFromView() {
        getPresenter().getModel().setUser(jTextField1.getText());
    }


    public void updateViewFromModel() {
        jTextField1.setText(getPresenter().getModel().getUser());
    }


    public void open() {
        setVisible(true);
        jTextField1.selectAll();
        jTextField1.requestFocus();
    }


    public void close() {
        dispose();
    }


    public void userRejected() {
        jLabel1.setText("Try again!");
        jTextField1.selectAll();
        jTextField1.requestFocus();
    }


    public LoginPresenterI getPresenter() {
        return loginPresenter;
    }


    public void setPresenter(LoginPresenterI loginPresenter) {
        this.loginPresenter = loginPresenter;
    }
}