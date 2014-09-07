/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server.exception;

public class DeniedRequestException extends Exception {
    @Override public String toString()
    {
        return "Richiesta non consentita.";
    }
}
