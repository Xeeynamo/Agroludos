/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos;

import agroludos.db.components.DefPassException;
import agroludos.db.components.Partecipante;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Luciano
 */
public class JFrameRegistrazione extends javax.swing.JFrame {
    private static final String DIR = "C:\\File SRC";
    /**
     * Creates new form JFrameRegistrazione
     */
    public JFrameRegistrazione() {
        initComponents();
    }

    
    //AGGIUNTA by ROS (13/07/2014)
    public String DefinePass (char [] P1,char [] P2) throws DefPassException
    {
        String x;
        String pass1,pass2;
        if (Arrays.equals(P1,P2))
            return x=new String (P1);
        else
            throw new DefPassException();
    }
   public String createFileSrc (String file_name, String text) throws IOException
    {
        File dir=new File (DIR);
        if (!dir.isDirectory())
            dir.mkdir();
        String x=DIR+"\\"+file_name+".txt";
        File f=new File (x);
        FileWriter fr=new FileWriter (f);
        fr.write(text);
        fr.flush();
        fr.close();
        return x;
    }
   
   public void deleteFileSrc (String file_name) throws IOException
   {
       File dir=new File (DIR);
       if (dir.isDirectory())
       {
            String x=DIR+"\\"+file_name+".txt";
            File f=new File (x);
            if (f.isFile())
                f.delete();
       }

       
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
        jRegistraDatanascita = new javax.swing.JTextField();
        jRegistraTesserasan = new javax.swing.JTextField();
        jRegistraDataSrc = new javax.swing.JTextField();
        jRegistraSesso = new javax.swing.JComboBox();
        jRegistraCodFisc = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jRegistraMail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jRegistraConferma = new javax.swing.JButton();
        jRegistraAnnulla = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jRegistratiPwd = new javax.swing.JPasswordField();
        jRegistratiPwd2 = new javax.swing.JPasswordField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Informazioni sul partecipante"));

        jLabel5.setText("Nome");

        jLabel14.setText("Cognome");

        jLabel16.setText("Indirizzo");
        jLabel16.setToolTipText("");

        jLabel20.setText("Data di nascita");

        jLabel22.setText("Sesso");

        jLabel25.setText("Tessera san.");

        jLabel29.setText("Data SRC");

        jRegistraCertificatoSrc.setColumns(20);
        jRegistraCertificatoSrc.setRows(5);
        jScrollPane4.setViewportView(jRegistraCertificatoSrc);

        jLabel30.setText("Certificato SRC");

        jRegistraSesso.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Uomo", "Donna" }));

        jLabel4.setText("Codice Fiscale");

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
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)
                            .addComponent(jLabel30))
                        .addGap(20, 20, 20)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jRegistraDataSrc)
                            .addComponent(jRegistraCognome)
                            .addComponent(jRegistraIndirizzo)
                            .addComponent(jRegistraDatanascita)
                            .addComponent(jRegistraTesserasan)
                            .addComponent(jRegistraSesso, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jRegistraCodFisc)
                            .addComponent(jRegistraNome))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraDataSrc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel29)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap(14, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraCognome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraIndirizzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraDatanascita, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel22)
                            .addComponent(jRegistraSesso, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jRegistraCodFisc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel25)
                            .addComponent(jRegistraTesserasan, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel1.setText("E-mail");

        jLabel2.setText("Password");

        jRegistraConferma.setText("Conferma");
        jRegistraConferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistraConfermaActionPerformed(evt);
            }
        });

        jRegistraAnnulla.setText("Annulla");
        jRegistraAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRegistraAnnullaActionPerformed(evt);
            }
        });

        jLabel3.setText("Ripeti password");

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
        //MODIFICA by ROS (12/7/2014)
        //SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
        //SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d1 = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat d2 = new SimpleDateFormat("dd/MM/yyyy");
        String Password;
        String FileSRC;
        JFrame jFrame;
        try {
            //d1.parse(jRegistraDatanascita.getText());
            d2.parse(jRegistraDataSrc.getText());
            Password=DefinePass (jRegistratiPwd.getPassword(),jRegistratiPwd2.getPassword());
            FileSRC=createFileSrc(jRegistraCodFisc.getText(),jRegistraCertificatoSrc.getText());
            Partecipante p = new Partecipante(
                jRegistraMail.getText(),
                Password,
                jRegistraNome.getText(),
                jRegistraCognome.getText(),
                //"",
                jRegistraCodFisc.getText(),
                jRegistraIndirizzo.getText(),
                //new Date(1, 2, 3),
                //d1,
                d1.parse(jRegistraDatanascita.getText()),    
                jRegistraSesso.getSelectedIndex() == 0 ? 'M' : 'F',
                jRegistraTesserasan.getText(),
                //new Date(1, 2, 3),
                //d2,
                d2.parse(jRegistraDataSrc.getText()),
                //jRegistraCertificatoSrc.getText());
                FileSRC);
            
            Agroludos.agroConnect.LoginAnonimo().addPartec(p);
            jFrame=new JFrameLogin();
            jFrame.pack();
            jFrame.setVisible(true);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(null, "Data di nascita o data SRC non riconosciuta. Il formato corretto è YYYY/MM/DD.\n" + e.toString(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (DefPassException e) {
            JOptionPane.showMessageDialog(null, "Password inseriti diversi.\n" + jRegistratiPwd.getPassword() + "è diverso da " + jRegistratiPwd2.getPassword() +".\n",
                    "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Problemi con il certificato SRC.\n" + e.toString(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Problemi con il mySQL.\n" + e.toString(),
                    "Errore", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_jRegistraConfermaActionPerformed

    private void jRegistraAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRegistraAnnullaActionPerformed
        
    }//GEN-LAST:event_jRegistraAnnullaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(JFrameRegistrazione.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(JFrameRegistrazione.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(JFrameRegistrazione.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(JFrameRegistrazione.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new JFrameRegistrazione().setVisible(true);
            }
        });
    }

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
    private javax.swing.JTextField jRegistraDataSrc;
    private javax.swing.JTextField jRegistraDatanascita;
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
