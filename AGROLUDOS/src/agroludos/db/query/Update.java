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
public class Update 
{
    String from;
    String item;
    String value;
    Condition where;
    
    public Update (String from, String item,String value, Condition where)
    {
        this.from=from;
        this.item=item;
        this.value=value;
        this.where=where;
    }
    
    public String toString ()
    {
        return generateFrom(from) +
               generateSet(item,value) +
               generateWhere(where);
    }
    
    private static String generateFrom (String from)
    {
        return "UPDATE "+from;
    }
    
    private static String generateSet (String item,String value)
    {
        return "\nset "+item+"="+value+"\n";
    }
    
    private static String generateWhere (Condition statement)
    {
        if (statement != null)
            return "WHERE " + statement.toString() + "\n";
        return "";
    }        
}
