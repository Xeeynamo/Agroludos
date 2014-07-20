package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.exception.CampiVuotiException;
import agroludos.db.exception.DefCodFiscException;
import agroludos.db.exception.DefEmailException;
import agroludos.db.components.Partecipante;
import java.sql.*;

public class Anonimo extends AgroController
{
    public Anonimo(Statement statement)
    {
        super(statement, null);
    }
    
    @Override public int getUserType(String mail, String password) throws SQLException
    {
        return super.getUserType(mail, password);
    }
    public void addPartec(String password,Partecipante p) throws SQLException, DefEmailException, DefCodFiscException, CampiVuotiException
    {
        super._addPartec(password,p);
    }
}
