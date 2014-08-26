/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.db.exception;

/**
 *
 * @author Luciano
 */
public class TooLongException extends Exception{
    public TooLongException(String s)
    {
        super(s);
    }
    @Override public String toString()
    {
        return "La stringa " + super.toString() + " è troppo lunga.";
    }
}
