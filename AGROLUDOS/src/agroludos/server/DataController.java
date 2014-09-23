/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.server;

import agroludos.components.*;
import static agroludos.server.AgroController.TABLE_PARTECIPANTE;
import static agroludos.server.AgroController.TABLE_UTENTE;
import agroludos.server.db.Insert;
import agroludos.server.db.Request;
import agroludos.server.exception.InternalErrorException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Luciano
 */
public class DataController {
    
    private java.sql.Statement statement;
    
    DataController(String database, String username, String password) throws InternalErrorException
    {
        try
        {
            // Carico il driver JDBC
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            // mi connetto al database
            Connection db = DriverManager.getConnection("jdbc:mysql://" + database + "/" + "agroludos?" +
                    "user="+ username + "&password="+ password);

            // creo lo statement per l'invio della query
            statement = (java.sql.Statement)db.createStatement();
        }
        catch (ClassNotFoundException | InstantiationException | IllegalAccessException | SQLException e)
        {
            throw new InternalErrorException(e.toString());
        }
    }
    
    /**
     * Ottiene la data del server MySQL
     * @return oggetto Date se chiamata con successo, altrimenti torna null
     * @throws InternalError 
     */
    Date getDbDate() throws InternalErrorException
    {
        try
        {
            ResultSet rs = sendQuery("SELECT CURRENT_DATE");
            if (rs.next())
                return rs.getDate("CURRENT_DATE");
            return null;
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e.toString());
        }
    }
    
    /**
     * Controlla se l'indirizzo mail specificato è presente nel sistema
     * @param email indirizzo e-mail da verificare
     * @return true se esiste, viceversa false
     * @throws InternalError 
     */
    boolean isMailExists(String email) throws InternalErrorException
    {
        Request q = new Request (
                    new String [] {"mail"},
                    TABLE_UTENTE);
        try
        {
            ResultSet rs=sendQuery(q.toString());
            String[] mailList = new String[getResultSetLength(rs)];
            for (int i = 0; i < mailList.length; i++, rs.next())
                mailList [i] = rs.getString("mail");
            for (String s : mailList)
                if (s.compareTo(email) == 0)
                    return true;
            return false;
        }
        catch (SQLException e)
        {
            throw new InternalErrorException(e.toString());
        }
    }
    
    /**
     * Controlla se il codice fiscale specificato è presente nel sistema
     * @param codfisc codice fiscale da verificare
     * @return true se esiste, viceversa false
     * @throws InternalError 
     */
    boolean checkCodFiscExists(String codfisc) throws InternalErrorException
    {
        Request q = new Request (
                    new String [] {"codicefiscale"},
                    TABLE_PARTECIPANTE);
        ResultSet rs = sendQuery(q.toString());
        try
        {
            String [] cfList = new String[getResultSetLength(rs)];
            for (int i = 0; i < cfList.length; i++, rs.next())
                cfList[i] = rs.getString("codicefiscale");
            for (String s : cfList)
                if (s.compareTo(codfisc) == 0)
                    return true;
            return false;
        }
        catch (SQLException ex)
        {
            throw new InternalErrorException(ex.toString());
        }
    }
    
    private java.sql.Statement getStatement()
    {
        return statement;
    }
    
    /**
     * Dato un ResultSet, controlla quanti risultati sono presenti
     * @param rs ResulSet da controllare
     * @return numero di risultati
     * @throws SQLException 
     */
    private int getResultSetLength(ResultSet rs) throws SQLException
    {
        if (rs != null)
        {
            int size;
            rs.beforeFirst();
            rs.last();
            size = rs.getRow();
            rs.first();
            return size;
        }
        else
            return 0;
    }
    
    /**
     * Manda una query di interrogazione al database MySQL
     * @param query da eseguire
     * @return ritorna il set di risultati
     * @throws InternalError 
     */
    /*private*/ ResultSet sendQuery(String query) throws InternalErrorException
    {
        try {
            getStatement().executeQuery(query);
            return getStatement().getResultSet();
        } catch (SQLException ex) {
            throw new InternalErrorException(ex.toString());
        }
    }
    
    /**
     * Manda una query di modifica al database MySQL
     * @param query da eseguire
     * @throws InternalError 
     */
    private void sendUpdate(String query) throws InternalErrorException
    {
        try {
            getStatement().executeUpdate(query);
        } catch (SQLException ex) {
            throw new InternalErrorException(ex.toString());
        }
    }
    
    protected void addPartecipante(Partecipante p, String password)
            throws InternalErrorException
    {
        Insert insert;
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String date=d.format(p.getDataNascita());
        String dateSrc=d.format(p.getDataSrc());
        insert= new Insert (
                TABLE_UTENTE,
                new String [] {
                "\""+p.getMail()+"\"",
                "PASSWORD(\""+password+"\")",
                "0"});
        sendUpdate(insert.toString());
        insert= new Insert (
                TABLE_PARTECIPANTE,
                new String [] {
                "\""+p.getMail()+"\"",
                "\""+p.getNome()+"\"",
                "\""+p.getCognome()+"\"",
                "\""+p.getIndirizzo()+"\"",
                "'"+date+"'",
                "\""+p.getCodiceFiscale()+"\"",
                "\""+p.getSesso()+"\"",
                "\""+p.getTesseraSan()+"\"",
                "'"+dateSrc+"'",
                "\""+p.getSrc()+"\""});
        sendUpdate(insert.toString());
    }
}
