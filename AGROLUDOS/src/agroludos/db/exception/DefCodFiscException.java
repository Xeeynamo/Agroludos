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
public class DefCodFiscException extends Exception{
    
    @Override public String toString()
    {
            return "Codice fiscale inserito già presente nel sistema.\n";
    }
}
