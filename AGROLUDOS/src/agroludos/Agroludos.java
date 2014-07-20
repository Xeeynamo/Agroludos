package agroludos;

import agroludos.db.AgroConnect;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

/**
 *
 * @author Luciano
 */
public class Agroludos
{
    static AgroConnect agroConnect;
    
    public static void CreateAgroConnect()
    {
        if (agroConnect == null)
        {
            try
            {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            }
            catch (Exception e)
            { 
            }
            try
            {
                Agroludos.agroConnect = new AgroConnect("root", "agroludos");
            }
            catch (Exception e)
            {
                JOptionPane.showMessageDialog(null, "Impossibile stabilire una connessione col database\n" +
                        e.toString(), "Errore", JOptionPane.ERROR_MESSAGE);

            }
        }
    }
    public static void main(String[] args)
    {
        CreateAgroConnect();
        JFrameLogin frame = new JFrameLogin();
        frame.setVisible(true);
    }
}
