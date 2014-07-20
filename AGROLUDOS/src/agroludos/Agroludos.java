package agroludos;

import agroludos.db.user.Anonimo;
import agroludos.gui.JFrameLogin;
import agroludos.gui.Shared;

public class Agroludos
{
    public static Anonimo Connect(String[] args)
    {
        try
        {
            if (args == null || args.length != 3)
                return new Anonimo("localhost", "root", "agroludos");
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
