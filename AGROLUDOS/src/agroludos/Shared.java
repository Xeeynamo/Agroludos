package agroludos;

import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JList;

/**
 * Qui ci va tutta la parte di codice condivisa tra le varie finestre
 */
public class Shared
{
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
