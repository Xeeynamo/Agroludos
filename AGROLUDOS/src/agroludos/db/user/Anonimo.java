package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.exception.*;
import agroludos.db.components.Partecipante;
import agroludos.db.exception.WrongLoginException;
import java.sql.*;

/**
 * Utente anonimo.
 * Qui vengono gestite tutte quelle operazioni che può fare un qualsiasi utente non registrato.
 * Le operazioni permesse, ovviamente, si limitano al login e alla registrazione
 */
public class Anonimo extends AgroController
{
    /**
     * Stabilisce la connessione col database come utente anonimo
     * @param database indirizzo del database
     * @param username nome utente
     * @param password password
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException 
     */
    public Anonimo(String database, String username, String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        super(database, username, password);
    }
    
    @Override public AgroController Login(String mail, String password) throws SQLException, WrongLoginException
    {
        return super.Login(mail, password);
    }
    @Override public void addPartecipante(String password,Partecipante p) throws SQLException, DefEmailException, DefCodFiscException, CampiVuotiException
    {
        super.addPartecipante(password,p);
    }
}
