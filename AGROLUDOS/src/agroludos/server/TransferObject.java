/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server;

import agroludos.components.*;

/**
 *
 * @author Luciano
 */
public class TransferObject {
    
    ITransferableObject[] to;
    
    public TransferObject(ITransferableObject ... to)
    {
        this.to = to;
    }
    public TransferObject(String ... str)
    {
        to = new StringTO[str.length];
        for (int i = 0; i < str.length; i++)
            to[i] = new StringTO(str[i]);
    }
    int getLength()
    {
        return to.length;
    }
    ITransferableObject getIndex(int index)
    {
        return to[index];
    }
}
