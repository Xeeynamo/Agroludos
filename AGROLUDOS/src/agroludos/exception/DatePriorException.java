/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.exception;

/**
 *
 * @author Luciano
 */
public class DatePriorException extends Exception {
    @Override public String toString()
    {
        return "La data impostata è precedente alla data corrente.";
    }  
}
