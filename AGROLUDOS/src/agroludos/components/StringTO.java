/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.components;

/**
 *
 * @author Luciano
 */
public class StringTO extends TransferableObject {
    private final String str;
    public StringTO(String str) {
        this.str = str;
    }
    @Override public String toString()
    {
        return str;
    }
}
