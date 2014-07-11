/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos;

import agroludos.db.AgroConnect;

/**
 *
 * @author Luciano
 */
public class Agroludos
{
    static AgroConnect agroConnect;
    
    public static void main(String[] args)
    {
        JFrameLogin frame = new JFrameLogin();
        frame.setVisible(true);
    }
}
