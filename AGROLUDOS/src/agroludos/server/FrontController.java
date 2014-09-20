/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server;

import agroludos.components.TransferObject;
import agroludos.server.exception.DeniedRequestException;
import agroludos.server.exception.InternalErrorException;
import agroludos.server.exception.RequestNotSupportedException;

public class FrontController
{
    private final Dispatcher dispatcher;
    public FrontController() {
        dispatcher = new Dispatcher();
    }
    TransferObject processRequest(String request, TransferObject parameters)
            throws DeniedRequestException, RequestNotSupportedException, InternalErrorException
    {
        return dispatcher.dispatch(request, parameters);
    }
}
