/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos;

/**
 *
 * @author Luciano
 */
public class RequestNotSupportedException extends Exception {
    @Override public String toString()
    {
        return "Richiesta non convalidata perché non esiste o non ancora supportata.";
    }
}
