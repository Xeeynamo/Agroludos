package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.exception.CampiVuotiException;
import agroludos.db.exception.DefCodFiscException;
import agroludos.db.exception.DefEmailException;
import agroludos.db.components.Partecipante;
import java.sql.*;

/**
 * Utente anonimo.
 * Qui vengono gestite tutte quelle operazioni che può fare un qualsiasi utente non registrato.
 * Le operazioni permesse, ovviamente, si limitano al login e alla registrazione
 */
public class Anonimo extends AgroController
{
    public Anonimo(String database, String username, String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        super(database, username, password);
    }
    
    @Override public AgroController Login(String mail, String password) throws SQLException
    {
        return super.Login(mail, password);
    }
    public void addPartec(String password,Partecipante p) throws SQLException, DefEmailException, DefCodFiscException, CampiVuotiException
    {
        super.addPartecipante(password,p);
    }
}
