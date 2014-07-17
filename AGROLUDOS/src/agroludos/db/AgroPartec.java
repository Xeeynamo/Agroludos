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
       return super._getCompetizioniDisponibili(); 
    }
     
    public Competizione getCompetizione (int id) throws SQLException
    {
        return super._getCompetizione(id);
    }
    
    public Optional[] getOptional() throws SQLException
    {
        return super._getOptional();
    }
    public void setOptional(Optional optional) throws SQLException
    {
        super._setOptional(optional);
    }
}
