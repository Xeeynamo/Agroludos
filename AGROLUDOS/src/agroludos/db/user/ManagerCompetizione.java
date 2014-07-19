package agroludos.db.user;

import agroludos.db.*;
import agroludos.db.AgroController;
import agroludos.db.components.*;
import java.sql.*;

public class ManagerCompetizione extends AgroController
{
    public ManagerCompetizione(Statement statement, String mail)
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
