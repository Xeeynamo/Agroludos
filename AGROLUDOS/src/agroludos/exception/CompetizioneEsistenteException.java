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
public class CompetizioneEsistenteException extends Exception {
    @Override public String toString()
    {
        return "Impossibile creare una competizione dato il giorno specificato.\n" +
                "Esiste già una competizione.";
    }  
}
