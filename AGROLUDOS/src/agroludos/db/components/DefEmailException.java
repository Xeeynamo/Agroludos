/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.db.components;

/**
 *
 * @author Luigi Rosini
 */
public class DefEmailException extends Exception{

    public String toString()
    {
            return  "Email inserita già presente nel sistema";
    }
}
