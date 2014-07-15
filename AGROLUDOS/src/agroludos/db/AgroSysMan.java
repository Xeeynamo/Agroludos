package agroludos.db;

import agroludos.db.*;
import agroludos.db.components.*;
import java.sql.*;

public class AgroSysMan extends AgroUser
{
    public AgroSysMan(Statement statement)
    {
        super(statement);
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
