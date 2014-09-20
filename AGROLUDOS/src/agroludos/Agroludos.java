package agroludos;

import agroludos.components.StringTO;
import agroludos.server.ApplicationController;
import agroludos.components.TransferObject;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;

public class Agroludos
{
    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "agroludos";
    
    public static ApplicationController fc;
 
    public static void main(String[] args)
    {
        fc = new ApplicationController();
 
        TransferObject to;
        if (args == null || args.length != 3)
            to = new TransferObject(new StringTO(DEFAULT_SERVER), new StringTO(DEFAULT_USERNAME), new StringTO(DEFAULT_PASSWORD));
        else
            to = new TransferObject(new StringTO(args[0]), new StringTO(args[1]), new StringTO(args[2]));
        try
        {
            fc.processRequest(ApplicationController.Request.Initialize, to);
            fc.processRequest(ApplicationController.Request.FrameLogin, null);
        }
        catch (DeniedRequestException | RequestNotSupportedException | InternalErrorException e)
        {
            agroludos.gui.Shared.showError(null, e.toString());
        }
    }
}
