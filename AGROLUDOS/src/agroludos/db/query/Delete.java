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
public class Delete 
{
    String from;
    Condition where;
    
    public Delete (String from, Condition where)
    {
        this.from=from;
        this.where=where;
    }
    
    public String toString ()
    {
        return generateFrom(from) +
               generateWhere(where);
    }
    
    private static String generateFrom (String from)
    {
        return "DELETE FROM "+from+"\n";
    }
    
    private static String generateWhere (Condition statement)
    {
        return "WHERE " + statement.toString() + "\n";
    }
}
