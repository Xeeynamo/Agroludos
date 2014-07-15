package agroludos.db;

import agroludos.db.components.CampiVuotiException;
import agroludos.db.components.DefCodFiscException;
import agroludos.db.components.DefEmailException;
import agroludos.db.components.Partecipante;
import java.sql.*;

public class AgroAnonimo extends AgroUser
{
    public AgroAnonimo(Statement statement)
    {
        super(statement, null);
    }
    
    public void addPartec(Partecipante p) throws SQLException, DefEmailException, DefCodFiscException, CampiVuotiException
    {
        super._addPartec(p);
    }
}
