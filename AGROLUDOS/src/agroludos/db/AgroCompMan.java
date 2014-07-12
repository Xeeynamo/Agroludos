package agroludos.db;

import agroludos.db.components.Optional;
import java.sql.*;

public class AgroCompMan extends AgroUser
{
    public AgroCompMan(Statement statement)
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
