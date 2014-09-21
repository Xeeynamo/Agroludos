package agroludos;

import agroludos.server.FrontController;

public class Agroludos
{
    private static final String DEFAULT_SERVER = "localhost";
    private static final String DEFAULT_USERNAME = "root";
    private static final String DEFAULT_PASSWORD = "agroludos";
    
    public static void main(String[] args)
    {
        String server;
        String username;
        String password;
        if (args == null || args.length != 3)
        {
            server = DEFAULT_SERVER;
            username = DEFAULT_USERNAME;
            password = DEFAULT_PASSWORD;
        }
        else
        {
            server = args[0];
            username = args[1];
            password = args[2];
        }
        FrontController.Initialize(server, username, password);
        FrontController.processRequest("FrameLogin");
    }
}
