package agroludos.db;

import java.sql.*;

public class AgroCompMan extends AgroUser
{
    public AgroCompMan(Statement statement)
    {
        super(statement);
    }
    
    public String[] getOptionalNomi() throws SQLException
    {
        return super._getOptionalNomi();
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
