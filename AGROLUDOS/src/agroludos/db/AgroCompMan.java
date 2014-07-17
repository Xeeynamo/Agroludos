package agroludos.db;

import agroludos.db.*;
import agroludos.db.components.*;
import java.sql.*;

public class AgroCompMan extends AgroUser
{
    public AgroCompMan(Statement statement, String mail)
    {
        super(statement, mail);
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
