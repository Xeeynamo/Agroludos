/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.gui;

import agroludos.components.Optional;
import agroludos.components.TipoCompetizione;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.FrontController;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;
import java.util.Date;
import javax.swing.JOptionPane;

public class JFrameCreaComp extends javax.swing.JFrame
{
    //private final ManagerCompetizione agro;
    FrontController fc;
    TipoCompetizione[] listTipoCompetizione;
    Optional[] listOptional;
    boolean[] listOptionalDisponibili;
    
    public JFrameCreaComp(FrontController fc)
    {
        try 
        {
        this.fc=fc;
        Shared.setDefaultLookAndFeel();
        initComponents();
        listOptional=(Optional[])fc.processRequest(FrontController.Request.GetOptional,null);
        Shared.CreateList(jListaOptional, listOptional);
        listOptionalDisponibili = new boolean[listOptional.length];
        listTipoCompetizione=(TipoCompetizione[])fc.processRequest(FrontController.Request.GetCompetizioneTipi,null);
        Shared.CreateList(jListTipoCompetizioni, listTipoCompetizione);
        }
        catch (DeniedRequestException | RequestNotSupportedException | InternalErrorException e)
        {
            Shared.showError(this, e.toString());
        }
    }
    
    /**
     * Crea una lista con soli gli optional disponibili
     * @return 
     */
    Optional[] creaListaOptional()
    {
        int count = 0;
        for (boolean b : listOptionalDisponibili)
        {
            if (b)
                count++;
        }
        Optional[] o = new Optional[count];
        for (int i = 0, j = 0; j < count; i++)
        {
            if (listOptionalDisponibili[i])
            {
                o[j++] = listOptional[i];
            }
        }
        return o;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jListTipoCompetizioni = new javax.swing.JComboBox();
        jPartecipantiMin = new javax.swing.JSpinner();
        jLabel2 = new javax.swing.JLabel();
        jPartecipantiMax = new javax.swing.JSpinner();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jListaOptional = new javax.swing.JComboBox();
        jOptionalSelezionabile = new javax.swing.JCheckBox();
        jOptionalPrezzo = new javax.swing.JSpinner();
        jLabel4 = new javax.swing.JLabel();
        jCompetizioneData = new javax.swing.JFormattedTextField();
        jLabel5 = new javax.swing.JLabel();
        jConferma = new javax.swing.JButton();
        jAnnulla = new javax.swing.JButton();
        jPrezzo = new javax.swing.JSpinner();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Crea competizione");

        jLabel1.setText("Tipologia competizione");

        jListTipoCompetizioni.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jPartecipantiMin.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        jPartecipantiMin.setValue(4);

        jLabel2.setText("Numero minimo di partecipanti");

        jPartecipantiMax.setModel(new javax.swing.SpinnerNumberModel(1, 1, 100, 1));
        jPartecipantiMax.setValue(8);

        jLabel3.setText("Numero massimo di partecipanti");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Scelta degli optional disponibili"));

        jListaOptional.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jListaOptionalActionPerformed(evt);
            }
        });

        jOptionalSelezionabile.setText("Selezionabile");
        jOptionalSelezionabile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jOptionalSelezionabileActionPerformed(evt);
            }
        });

        jOptionalPrezzo.setEnabled(false);

        jLabel4.setText("Prezzo scelto");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jListaOptional, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jOptionalSelezionabile)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jOptionalPrezzo, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jListaOptional, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jOptionalSelezionabile)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jOptionalPrezzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jCompetizioneData.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("d/M/yy"))));
        jCompetizioneData.setValue(new Date());

        jLabel5.setText("Data della competizione");

        jConferma.setText("Conferma");
        jConferma.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jConfermaActionPerformed(evt);
            }
        });

        jAnnulla.setText("Annulla");
        jAnnulla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jAnnullaActionPerformed(evt);
            }
        });

        jPrezzo.setModel(new javax.swing.SpinnerNumberModel(Float.valueOf(0.0f), Float.valueOf(0.0f), Float.valueOf(100.0f), Float.valueOf(0.5f)));
        jPrezzo.setValue(10.0);

        jLabel6.setText("Prezzo della competizione");
        jLabel6.setToolTipText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPartecipantiMax))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(jPartecipantiMin))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jCompetizioneData))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jListTipoCompetizioni, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jConferma, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jAnnulla, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPrezzo, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jListTipoCompetizioni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPartecipantiMin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPartecipantiMax, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCompetizioneData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jPrezzo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jConferma)
                    .addComponent(jAnnulla))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jConfermaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jConfermaActionPerformed
        try
        {
        int indexTipo = jListTipoCompetizioni.getSelectedIndex();
        //Manager manager = new Manager("", "", agro.getMail());
        float prezzo = Float.parseFloat(jPrezzo.getValue().toString());
        int minPart = (int)jPartecipantiMin.getValue();
        int maxPart = (int)jPartecipantiMax.getValue();
        Date data = (Date)jCompetizioneData.getValue();
        
        if (indexTipo < 0)
        {
            JOptionPane.showConfirmDialog(this,
                "Nessun tipo di competizione selezionata.",
                "Errore", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else if (minPart > maxPart)
        {
            JOptionPane.showConfirmDialog(this,
                "Il numero minimo di partecipanti non può essere maggiore del numero massimo di partecipanti.",
                "Errore", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else if (data.before(new Date()))
        {
            JOptionPane.showConfirmDialog(this,
                "La data impostata è precedente a quella attuale.",
                "Errore", JOptionPane.OK_OPTION, JOptionPane.ERROR_MESSAGE);
        }
        else if (JOptionPane.showConfirmDialog(this,
                "Sei sicuro di voler creare questa competizione? I vari campi potranno essere modificati in un secondo momento ma, una volta creata una competizione, questa potrà essere solo annullata ma non eliminata. Continuare?",
                "Conferma creazione competizione",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) ==
                JOptionPane.YES_OPTION)
        {
            TipoCompetizione cTipo = listTipoCompetizione[indexTipo];
            //Competizione competizione = new Competizione(0, prezzo, minPart, maxPart,
            //        0, cTipo, manager, data, creaListaOptional());
            fc.processRequest(FrontController.Request.AddCompetizione,new Object []
            {prezzo,minPart,maxPart,cTipo,data,creaListaOptional()});
            /*
            try
            {
                agro.creaCompetizione(competizione);
                JFrame frame = new JFrameManComp(agro);
                setVisible(false);
                frame.setVisible(true);
                dispose();
            }
                    */
            fc.processRequest(FrontController.Request.FrameManagerCompetizione,null);
        }
        }
        catch (DeniedRequestException | RequestNotSupportedException | InternalErrorException e)
        {
            Shared.showError(this, e.toString());
        }
        
    }//GEN-LAST:event_jConfermaActionPerformed

    private void jAnnullaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jAnnullaActionPerformed
        try
        {
            fc.processRequest(FrontController.Request.FrameManagerCompetizione,null);
            //dispose();
        }
        catch (DeniedRequestException | RequestNotSupportedException | InternalErrorException e)
        {
            Shared.showError(this, e.toString());
        }
    }//GEN-LAST:event_jAnnullaActionPerformed

    private void jListaOptionalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jListaOptionalActionPerformed
        int index = jListaOptional.getSelectedIndex();
        if (index >= 0)
        {
            jOptionalSelezionabile.setEnabled(true);
            jOptionalSelezionabile.setSelected(listOptionalDisponibili[index]);
            jOptionalPrezzo.setValue(listOptional[index].getPrezzo());
        }
        else
        {
            jOptionalSelezionabile.setEnabled(false);
        }
    }//GEN-LAST:event_jListaOptionalActionPerformed

    private void jOptionalSelezionabileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jOptionalSelezionabileActionPerformed
        int index = jListaOptional.getSelectedIndex();
        if (index >= 0)
        {
            listOptionalDisponibili[index] = jOptionalSelezionabile.isSelected();
        }
    }//GEN-LAST:event_jOptionalSelezionabileActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jAnnulla;
    private javax.swing.JFormattedTextField jCompetizioneData;
    private javax.swing.JButton jConferma;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JComboBox jListTipoCompetizioni;
    private javax.swing.JComboBox jListaOptional;
    private javax.swing.JSpinner jOptionalPrezzo;
    private javax.swing.JCheckBox jOptionalSelezionabile;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jPartecipantiMax;
    private javax.swing.JSpinner jPartecipantiMin;
    private javax.swing.JSpinner jPrezzo;
    // End of variables declaration//GEN-END:variables
}
