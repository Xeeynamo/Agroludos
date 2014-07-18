package agroludos.db;

import agroludos.db.*;
import agroludos.db.components.*;
import java.sql.*;

public class AgroPartec extends AgroUser
{
    public AgroPartec(Statement statement, String mail)
    {
        super(statement, mail);
    }
    
    public Competizione [] getCompetizioniDisponibili () throws SQLException
    {
       return super._getCompetizioni();
    }
     
    public Competizione getCompetizione (int id) throws SQLException
    {
        int i;
        boolean trovato=false;
        Competizione [] comp=super._getCompetizioni();
        for (i=0;i<comp.length;i++)
        {
            if (comp[i].getId()==id)
            {
                trovato=true;
                break;
            }    
        }
        if (trovato)
            return comp[i];
        else
            throw new SQLException();
}
    
    public Optional[] getOptional() throws SQLException
    {
        return super._getOptional();
    }
    public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
}
