package agroludos;

import agroludos.db.user.Anonimo;
import agroludos.gui.JFrameLogin;
import agroludos.gui.Shared;

public class Agroludos
{
    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "agroludos";
    
    public static FrontController fc;
 
    public static void main(String[] args)
    {
        fc = new FrontController();
 
        String[] param;
        if (args == null || args.length != 3)
            param = new String[]{DEFAULT_SERVER, DEFAULT_USERNAME, DEFAULT_PASSWORD};
        else
            param = new String[]{args[0], args[1], args[2]};
        try
        {
            fc.processRequest(FrontController.Request.Initialize, param);
            fc.processRequest(FrontController.Request.FrameLogin, null);
        }
        catch (DeniedRequestException | RequestNotSupportedException | InternalErrorException e)
        {
            agroludos.gui.Shared.showError(null, e.toString());
        }
    }
}
