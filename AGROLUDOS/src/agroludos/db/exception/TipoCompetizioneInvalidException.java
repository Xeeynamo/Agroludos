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
public class TipoCompetizioneInvalidException extends Exception {
    @Override public String toString()
    {
        return "Tipo competizione non valido.";
    }
}
