package agroludos.db;

import agroludos.db.*;
import agroludos.db.components.*;
import java.sql.*;

public class AgroSysMan extends AgroUser
{
    public AgroSysMan(Statement statement, String mail)
    {
        super(statement, mail);
    }
    
    public Optional[] getOptional() throws SQLException
    {
        return super._getOptional();
    }
    public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
    public Partecipante[] getPartecipantiMinimal() throws SQLException
    {
        return super.getPartecipantiMinimal();
    }
    public Partecipante getPartecipante(String email) throws SQLException
    {
        return super.getPartecipante(email);
    }
    public Manager[] getManagers() throws SQLException
    {
        return super.getManagers();
    }
}
