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
    public TransferObject(Boolean ... integer)
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
    
    public Integer[] toIntegerArray()
    {
        Integer[] integer = new Integer[to.length];
        for (int i = 0; i < integer.length; i++)
            integer[i] = to[i].toValue();
        return integer;
    }
    @Override public Competizione toCompetizione()
    {
        return getIndex(0).toCompetizione();
    }
    public Competizione[] toCompetizioneArray()
    {
        return (Competizione[])to;
    }
    @Override public Manager toManager()
    {
        return getIndex(0).toManager();
    }
    public Manager[] toManagerArray()
    {
        return (Manager[])to;
    }
    @Override public Optional toOptional()
    {
        return getIndex(0).toOptional();
    }
    public Optional[] toOptionalArray()
    {
        return (Optional[])to;
    }
    @Override public Partecipante toPartecipante()
    {
        return getIndex(0).toPartecipante();
    }
    public Partecipante[] toPartecipanteArray()
    {
        return (Partecipante[])to;
    }
    @Override public TipoCompetizione toTipoCompetizione()
    {
        return getIndex(0).toTipoCompetizione();
    }
    public TipoCompetizione[] toTipoCompetizioneArray()
    {
        return (TipoCompetizione[])to;
    }
}
