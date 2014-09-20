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
public class IntegerTO extends ITransferableObject {
    Integer value;
    public IntegerTO(int i) {
        value = i;
    }
    public IntegerTO(boolean b) {
        value = b == true ? 1 : 0;
    }
    public IntegerTO(Boolean b) {
        value = b == true ? 1 : 0;
    }
    public IntegerTO(Integer i) {
        value = i;
    }
    @Override public int toValue()
    {
        return value;
    }
}
