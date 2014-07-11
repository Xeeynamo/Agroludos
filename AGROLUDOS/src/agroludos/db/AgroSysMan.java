/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.db;

import java.sql.*;

public class AgroSysMan extends AgroUser
{
    public AgroSysMan(Statement statement)
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
