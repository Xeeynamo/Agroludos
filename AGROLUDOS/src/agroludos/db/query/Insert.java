/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.db.query;

/**
 *
 * @author Luigi Rosini
 */
public class Insert 
{
    String from;
    String [] values;
    String [] items;
    
    public Insert (String from, String [] values)
    {
        this.from=from;
        this.values=values;
        this.items=null;
    }
    
    public Insert (String from,String [] items, String [] values)
    {
        this.from=from;
        this.values=values;
        this.items=items;
    }
    
    /**
     * Ottiene l'inserimento dei dati sotto forma di strina SQL eseguibile
     * @return stringa eseguibile
     */
    public String toString()
    {
        return generateFrom(from)+
               generateItems(items)+
               generateValues(values);
    }
    
    private static String generateFrom(String from)
    {
        return "INSERT INTO "+from;
    }
    
    private static String generateItems(String [] items)
    {
        String s="\n";
        if (items!=null)
        {
            s+="(";
            for (int i=0;i<items.length;i++)
            {
                s+=items[i];
                if (i+1<items.length)
                    s+=",";
            }
            s+=")\n";
        }
        return s;
    }
    
    
    private static String generateValues(String [] values)
    {
        String s=" values (";
        for (int i=0;i<values.length;i++)
        {
            s+=values[i];
            if (i+1<values.length)
                s+=",";
        }
        s+=")";
        return s;
    }
}
