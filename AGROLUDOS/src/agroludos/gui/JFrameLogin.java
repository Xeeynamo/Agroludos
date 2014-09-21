package agroludos.gui;

import agroludos.server.ApplicationController;
import agroludos.components.TransferObject;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;
/**
 *
 * @author Luciano
 */
public class JFrameLogin extends javax.swing.JFrame {

    ApplicationController fc;
    
    public JFrameLogin(ApplicationController fc)
    {
        this.fc = fc;
        initComponents();
    }
    
    public TransferObject getLoginData()
    {
        return new TransferObject(jTextMail.getText(), jTextPassword.getText());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextMail = new javax.swing.JTextField();
        jLoginMail = new javax.swing.JLabel();
        jLoginPassword = new javax.swing.JLabel();
        jLoginEntra = new javax.swing.JButton();
        jLoginRegistrati = new javax.swing.JButton();
        jTextPassword = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Agroludos");

        jLoginMail.setText("LOGIN_MAIL");
        jLoginMail.setName("LOGIN_MAIL"); // NOI18N

        jLoginPassword.setText("LOGIN_PASSWORD");
        jLoginPassword.setName("LOGIN_PASSWORD"); // NOI18N

        jLoginEntra.setText("LOGIN_LOGIN");
        jLoginEntra.setName("LOGIN_LOGIN"); // NOI18N
        jLoginEntra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoginEntraActionPerformed(evt);
            }
        });

        jLoginRegistrati.setText("LOGIN_SIGNUP");
        jLoginRegistrati.setName("LOGIN_SIGNUP"); // NOI18N
        jLoginRegistrati.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jLoginRegistratiActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLoginMail)
                            .addComponent(jLoginPassword))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextMail, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextPassword)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLoginRegistrati)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLoginEntra, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLoginMail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLoginPassword)
                    .addComponent(jTextPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLoginRegistrati)
                    .addComponent(jLoginEntra))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jLoginEntraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoginEntraActionPerformed
        fc.processRequest(ApplicationController.Request.Login, null);
    }//GEN-LAST:event_jLoginEntraActionPerformed

    private void jLoginRegistratiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jLoginRegistratiActionPerformed
        fc.processRequest(ApplicationController.Request.FrameRegistrazione, null);
    }//GEN-LAST:event_jLoginRegistratiActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jLoginEntra;
    private javax.swing.JLabel jLoginMail;
    private javax.swing.JLabel jLoginPassword;
    private javax.swing.JButton jLoginRegistrati;
    private javax.swing.JTextField jTextMail;
    private javax.swing.JPasswordField jTextPassword;
    // End of variables declaration//GEN-END:variables
}
