/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.components;

public class FloatTO extends TransferableObject {
    float f;
    public FloatTO(float f)
    {
        this.f = f;
    }
    @Override public float toValueF()
    {
        return f;
    }
}
