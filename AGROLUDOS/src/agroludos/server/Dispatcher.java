/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server;

import agroludos.components.TransferObject;
import agroludos.server.ApplicationController.Request;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;

/**
 *
 * @author Luciano
 */
public class Dispatcher
{
    private final ApplicationController appCtrl;
    public Dispatcher()
    {
        appCtrl = new ApplicationController();
    }
    public TransferObject dispatch(String request, TransferObject parameter)
            throws DeniedRequestException, RequestNotSupportedException, InternalErrorException
    {
        Request r = null;
        return appCtrl.processRequest(r, parameter);
    }
}
