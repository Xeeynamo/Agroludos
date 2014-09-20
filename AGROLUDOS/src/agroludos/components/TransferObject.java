/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.components;

import agroludos.components.*;

/**
 *
 * @author Luciano
 */
public class TransferObject extends TransferableObject {
    
    TransferableObject[] to;
    
    public TransferObject(TransferableObject ... to)
    {
        this.to = to;
    }
    public TransferObject(String ... str)
    {
        to = new StringTO[str.length];
        for (int i = 0; i < str.length; i++)
            to[i] = new StringTO(str[i]);
    }
    public TransferObject(Integer ... integer)
    {
        to = new IntegerTO[integer.length];
        for (int i = 0; i < integer.length; i++)
            to[i] = new IntegerTO(integer[i]);
    }
    public int getLength()
    {
        return to.length;
    }
    public TransferableObject[] getArray()
    {
        return to;
    }
    public TransferableObject getIndex(int index)
    {
        return to[index];
    }
}
