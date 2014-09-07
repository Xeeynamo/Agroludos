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
public class DefPassException extends Exception{
    @Override public String toString()
    {
            return "Password inseriti diversi.\n";
    }    
}
