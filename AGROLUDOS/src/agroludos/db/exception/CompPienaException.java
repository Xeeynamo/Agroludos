/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.db.exception;

/**
 *
 * @author Luigi Rosini
 */
public class CompPienaException extends Exception{
    @Override public String toString()
    {
        return "Impossibile prenotarsi alla competizione.\n" +
                "Non ci sono più posti disponibili.";
    } 
    
}
