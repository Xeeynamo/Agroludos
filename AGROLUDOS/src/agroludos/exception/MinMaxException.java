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
public class MinMaxException extends Exception {
    public String toString()
    {
        return "Il numero minimo è maggiore del numero massimo.";
    }  
}
