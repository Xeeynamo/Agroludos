/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.gui;

import agroludos.components.*;
import agroludos.exception.DefPassException;
import agroludos.exception.TooLongException;
import agroludos.server.ApplicationController;
import agroludos.components.TransferObject;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

/**
 *
 * @author Luciano
 */
public class JFrameRegistrazione extends javax.swing.JFrame {
    
    ApplicationController fc;
    public JFrameRegistrazione(ApplicationController fc) {
        this.fc = fc;
        initComponents();
    }

    
    //AGGIUNTA by ROS (13/07/2014)
    private String DefinePass (char [] P1,char [] P2) throws DefPassException
    {
        String x;
        String pass1,pass2;
        if (Arrays.equals(P1,P2))
            return x=new String (P1);
        else
            throw new DefPassException();
    }
    
    void CheckMaximumLength(String s, int maxlength) throws TooLongException
    {
        if (s.length() > maxlength)
            throw new TooLongException(s);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jRegistraCertificatoSrc = new javax.swing.JTextArea();
        jLabel30 = new javax.swing.JLabel();
        jRegistraNome = new javax.swing.JTextField();
        jRegistraCognome = new javax.swing.JTextField();
        jRegistraIndirizzo = new javax.swing.JTextField();
        jRegistraTesserasan = new javax.swing.JTextField();
        jRegistraSesso = new javax.swing.JComboBox();
        jRegistraCodFisc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jRegistraDatanascita = new javax.swing.JFormattedTextField();
        jRegistraDataSrc = new javax.swing.JFormattedTextField();
        jRegistraMail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRegistraConferma = new javax.swing.JButton();
        jRegistraAnnulla = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jRegistratiPwd = new javax.swing.JPasswordField();
        jRegistratiPwd2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Registrazione");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("SIGNUP_USERINFO"));
        jPanel2.setName("SIGNUP_USERINFO"); // NOI18N

        jLabel5.setText("SIGNUP_INFONAME");
        jLabel5.setName("SIGNUP_INFO"); // NOI18N

        jLabel14.setText("SIGNUP_INFOSURNAME");
        jLabel14.setName("SIGNUP_INFOSURNAME"); // NOI18N

        jLabel16.setText("SIGNUP_INFOADDRESS");
        jLabel16.setToolTipText("");
        jLabel16.setName("SIGNUP_INFOADDRESS"); // NOI18N

        jLabel20.setText("Data di nascita");
        jLabel20.setToolTipText("SIGNUP_INFOBIRTHDAY");
        jLabel20.setName("SIGNUP_INFOBIRTHDAY"); // NOI18N

        jLabel22.setText("SIGNUP_INFOSEX");
        jLabel22.setName("SIGNUP_INFOSEX"); // NOI18N

        jLabel25.setText("SIGNUP_INFOTESSAN");
        jLabel25.setName("SIGNUP_INFOTESSAN"); // NOI18N

        jLabel29.setText("SIGNUP_INFOSRCDATE");
        jLabel29.setName("SIGNUP_INFOSRCDATE"); // NOI18N

        jRegistraCertificatoSrc.setColumns(20);
        jRegistraCertificatoSrc.setRows(5);
        jScrollPane4.setViewportView(jRegistraCertificatoSrc);

        jLabel30.setText("SIGNUP_INFOSRCTEXT");
        jLabel30.setName("SIGNUP_INFOSRCTEXT"); // NOI18N

        jRegistraSesso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uomo", "Donna" }));

        jLabel4.setText("SIGNUP_INFOCODE");
        jLabel4.setName("SIGNUP_INFOCODE"); // NOI18N

        jRegistraDatanascita.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("d/M/yyyy"))));

        jRegistraDataSrc.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("d/M/yyyy"))));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel14)
                            .addComponent(jLabel20)
                            .addComponent(jLabel22)
                            .addComponent(jLabel25)
                            .addComponent(jLabel4)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30)
                            .addComponent(jLabel16))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRegistraCognome)
                            .addComponent(jRegistraIndirizzo)
                            .addComponent(jRegistraTesserasan)
                            .addComponent(jRegistraSesso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRegistraCodFisc)
                            .addComponent(jRegistraNome)
                            .addComponent(jRegistraDatanascita)
                            .addComponent(jRegistraDataSrc))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel29)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(13, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraCognome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraIndirizzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(jRegistraDatanascita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jRegistraSesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraCodFisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jRegistraTesserasan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRegistraDataSrc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(15, 15, 15)))
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText("SIGNUP_MAIL");
        jLabel1.setName("SIGNUP_MAIL"); // NOI18N

        jLabel2.setText("SIGNUP_PASSWORD");
        jLabel2.setName("SIGNUP_PASSWORD"); // NOI18N

        jRegistraConferma.setText("SIGNUP_INFOOK");
        jRegistraConferma.setName("SIGNUP_INFOOK"); // NOI18N
        jRegistraConferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistraConfermaActionPerformed(evt);
            }
        });

        jRegistraAnnulla.setText("SIGNUP_INFOCANCEL");
        jRegistraAnnulla.setName("SIGNUP_INFOCANCEL"); // NOI18N
        jRegistraAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistraAnnullaActionPerformed(evt);
            }
        });

        jLabel3.setText("SIGNUP_PASSWORD2");
        jLabel3.setToolTipText("");
        jLabel3.setName("SIGNUP_PASSWORD2"); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)
                            .addComponent(jLabel3))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jRegistratiPwd)
                            .addComponent(jRegistraMail)
                            .addComponent(jRegistratiPwd2))
                        .addGap(10, 10, 10))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jRegistraConferma, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jRegistraAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRegistraMail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jRegistratiPwd, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jRegistratiPwd2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRegistraConferma)
                    .addComponent(jRegistraAnnulla))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jRegistraConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRegistraConfermaActionPerformed

        SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
        String Password;
        try
        {
            CheckMaximumLength(jRegistraMail.getText(), 255);
            CheckMaximumLength(jRegistraNome.getText(), 45);
            CheckMaximumLength(jRegistraCognome.getText(), 45);
            CheckMaximumLength(jRegistraIndirizzo.getText(), 255);
            CheckMaximumLength(jRegistraCodFisc.getText(), 16);
            CheckMaximumLength(jRegistraTesserasan.getText(), 20);
            
            d2.parse(jRegistraDataSrc.getText());
            Password=DefinePass (jRegistratiPwd.getPassword(),jRegistratiPwd2.getPassword());
            Partecipante p = new Partecipante(
                jRegistraMail.getText(),
                jRegistraNome.getText(),
                jRegistraCognome.getText(),
                jRegistraCodFisc.getText(),
                jRegistraIndirizzo.getText(),
                d1.parse(jRegistraDatanascita.getText()),    
                jRegistraSesso.getSelectedIndex() == 0 ? 'M' : 'F',
                jRegistraTesserasan.getText(),
                d2.parse(jRegistraDataSrc.getText()),
                jRegistraCertificatoSrc.getText());
            
            fc.processRequest(ApplicationController.Request.AddPartecipante, new TransferObject(new StringTO(Password), p));
            Shared.showDialog(this, "Registrazione", "Registrazione avvenuta con successo!");
            fc.processRequest(ApplicationController.Request.FrameLogin, null);
        } catch (ParseException e) {
            Shared.showError(this, "Data di nascita o data SRC non riconosciuta. Il formato corretto è DD/MM/YYYY.");
        } catch (DefPassException e) {
            Shared.showError(this, "Password inserite diverse.");
        } catch (TooLongException e) {
            Shared.showError(this, e.toString());
        }
        
    }//GEN-LAST:event_jRegistraConfermaActionPerformed

    private void jRegistraAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRegistraAnnullaActionPerformed
        fc.processRequest(ApplicationController.Request.FrameLogin, null);    
    }//GEN-LAST:event_jRegistraAnnullaActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JButton jRegistraAnnulla;
    private javax.swing.JTextArea jRegistraCertificatoSrc;
    private javax.swing.JTextField jRegistraCodFisc;
    private javax.swing.JTextField jRegistraCognome;
    private javax.swing.JButton jRegistraConferma;
    private javax.swing.JFormattedTextField jRegistraDataSrc;
    private javax.swing.JFormattedTextField jRegistraDatanascita;
    private javax.swing.JTextField jRegistraIndirizzo;
    private javax.swing.JTextField jRegistraMail;
    private javax.swing.JTextField jRegistraNome;
    private javax.swing.JComboBox jRegistraSesso;
    private javax.swing.JTextField jRegistraTesserasan;
    private javax.swing.JPasswordField jRegistratiPwd;
    private javax.swing.JPasswordField jRegistratiPwd2;
    private javax.swing.JScrollPane jScrollPane4;
    // End of variables declaration//GEN-END:variables
}
