package agroludos.gui;

import java.awt.Component;
import javax.swing.*;

/**
 * Qui ci va tutta la parte di codice condivisa tra i vari JForm
 */
public class Shared
{
    public static void showError(Component parent, String message)
    {
        JOptionPane.showMessageDialog(parent, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }
    public static void showDialog(Component parent, String title, String message)
    {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Popola la lista del controllo JList.
     * NOTA: Il vecchio contenuto verrà cancellato, venendo sostituito
     * da quello nuovo; questo comporta che getSelectedIndex() diventa
     * pari a -1.
     * @param jList Controllo JList
     * @param list lista di oggetti
     */
    public static void CreateList(JList jList, Object[] list)
    {
        DefaultListModel listModel = new DefaultListModel();
        jList.removeAll();
        for (Object o : list)
        {
            listModel.addElement(o.toString());
        }
        jList.setModel(listModel);
    }
    
    /**
     * Popola la lista del controllo JComboBox.
     * NOTA: Il vecchio contenuto verrà cancellato, venendo sostituito
     * da quello nuovo; questo comporta che getSelectedIndex() diventa
     * pari a -1.
     * @param jList Controllo JComboBox
     * @param list lista di oggetti
     */
    public static void CreateList(JComboBox jList, Object[] list)
    {
        DefaultComboBoxModel listModel = new DefaultComboBoxModel();
        jList.removeAll();
        for (Object o : list)
        {
            listModel.addElement(o.toString());
        }
        jList.setModel(listModel);
    }
}
