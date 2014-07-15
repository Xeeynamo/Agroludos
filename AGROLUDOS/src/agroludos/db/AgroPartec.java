package agroludos.db;

import agroludos.db.components.CampiVuotiException;
import agroludos.db.components.Competizione;
import agroludos.db.components.Optional;
import agroludos.db.components.Partecipante;
import java.sql.*;

public class AgroPartec extends AgroUser
{
    
    public AgroPartec(Statement statement)
    {
        super(statement);
    }
    
    public Competizione [] getCompetizioniDisponibili () throws SQLException, CampiVuotiException
    {
        return super._getCompetizioniDisponibili();
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
