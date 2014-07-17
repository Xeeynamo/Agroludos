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

    
    @Override public Optional[] getOptional() throws SQLException
    {
        return super.getOptional();
    }
    @Override public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
}
