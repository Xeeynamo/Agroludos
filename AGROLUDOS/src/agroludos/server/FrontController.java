/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server;

import agroludos.components.TransferObject;

public class FrontController
{
    private static Dispatcher dispatcher ;
    public static void Initialize(String server, String username, String password)
    {
        dispatcher = new Dispatcher(server, username, password);
    }
    public static void processRequest(String request)
    {
        dispatcher.dispatch(request);
    }
}
