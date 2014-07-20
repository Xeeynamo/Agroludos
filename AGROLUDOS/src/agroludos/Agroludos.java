package agroludos;

import agroludos.db.user.Anonimo;
import agroludos.gui.JFrameLogin;
import agroludos.gui.Shared;

public class Agroludos
{
    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "agroludos";
    
    /**
     * Connette ad un server
     * @param args argomenti per la connessione
     * Se null o la lunghezza è diversa da 3, connette al server di default.
     * I parametri sono nome del server, username e password.
     * @return 
     */
    public static Anonimo Connect(String[] args)
    {
        try
        {
            if (args == null || args.length != 3)
                return new Anonimo(DEFAULT_SERVER, DEFAULT_USERNAME, DEFAULT_PASSWORD);
            else
                return new Anonimo(args[0], args[1], args[2]);
        }
        catch (Exception ex)
        {
            Shared.showError("Impossibile stabilire una connessione col database.\nIl programma sarà terminato." + ex.toString());
        }
        return null;
    }
    
    public static void main(String[] args)
    {
        Anonimo user = Connect(args);
        if (user != null)
        {
            JFrameLogin frame = new JFrameLogin(user);
            frame.setVisible(true);
        }
    }
}
