/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.exception;

/**
 *
 * @author Luigi Rosini
 */
public class ModificaCompScadutaException extends Exception{
    public String toString ()
    {
        return "Non è possibile effettuare alcuna ulteriore modifica in quanto mancano meno di 2 gg all'avvento della competizione";
    }
}
