package agroludos;

import agroludos.server.FrontController;

public class Agroludos
{
    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "agroludos";
    
    public static void main(String[] args)
    {
        if (args == null || args.length != 3)
            FrontController.Initialize(DEFAULT_SERVER, DEFAULT_USERNAME, DEFAULT_PASSWORD);
        else
            FrontController.Initialize(args[0], args[1], args[2]);
        FrontController.processRequest("FrameLogin");
    }
}
