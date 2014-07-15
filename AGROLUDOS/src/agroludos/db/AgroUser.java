package agroludos.db;

import agroludos.db.components.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AgroUser
{
    private static final String DB_AGRO = "agroludos";
    private static final String TABLE_PARTECIPANTE = "partecipante";
    private static final String TABLE_OPTIONAL = "optional";
    private static final String TABLE_UTENTE = "utente";
    
    
    Statement statement;
    String mail;
    
    public AgroUser(Statement statement, String mail)
    {
        this.statement = statement;
        this.mail = mail;
    }
    
    protected Statement getStatement()
    {
        return statement;
    }
    protected String getMail()
    {
        return mail;
    }
    protected int getResultSetLength(ResultSet rs) throws SQLException
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
    
    private void sendQuery(String query) throws SQLException
    {
        getStatement().executeQuery(query);
    }
    private void sendUpdate(String query) throws SQLException
    {
        getStatement().executeUpdate(query);
    }
    
    protected void _addPartec(Partecipante p) throws SQLException,DefEmailException,DefCodFiscException, CampiVuotiException
    {
        //MODIFICA by ROS (13/07/2014)
        if (!_checkMailExists(p.getMail()))
            throw new DefEmailException();
        if (!_checkCodFiscExists(p.getCodiceFiscale()))
            throw new DefCodFiscException();
        if (_checkCampiVuotiPart(p))
            throw new CampiVuotiException();
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String date=d.format(p.getDataNascita());
        String dateSrc=d.format(p.getDataSrc());
        String s1 = "INSERT INTO " + TABLE_UTENTE + " VALUES (" +
                "\"" + p.getMail()+ "\"," +
                "PASSWORD(\"" + p.getPassword()+ "\")," +
                0 + ");";
        String s2 = "INSERT INTO " + TABLE_PARTECIPANTE + " VALUES (" +
                "\"" + p.getCodiceFiscale()+ "\"," +
                "\"" + p.getNome() + "\"," +
                "\"" + p.getCognome() + "\"," +
                "\"" + p.getIndirizzo() + "\"," +
                //"'" + p.getDataNascita()+ "'" +
                //"'" + p.getDataNascitaString()+ "'," +
                "'" +date+ "'," +
                "'" + p.getSesso()+ "'," +
                "\"" + p.getTesseraSan()+ "\"," +
                //"\"" + p.getCertSrc()+ "\"" +
                //"'" + p.getDataSrc()+ "'";
                //"'"+p.getDataSrcString()+"'," +
                "'" + dateSrc+ "'," +
                "'" + p.getMail()+ "'," +
                "'" + p.getDirSrc()+ "');";
        
        //statement.executeUpdate(s1);
        //statement.executeUpdate(s2);
        sendUpdate(s1);
        sendUpdate(s2);
    }
    
    protected Optional[] _getOptional() throws SQLException
    {
        sendQuery("SELECT * FROM " + TABLE_OPTIONAL);
        ResultSet rs = getStatement().getResultSet();
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (int i = 0; i < opt.length; i++, rs.next())
        {
            opt[i] = new Optional(rs.getString("nome"), rs.getString("descrizione"), rs.getFloat("prezzo"));
        }
        return opt;
    }
    
    private boolean _checkMailExists(String email) throws SQLException
    {
        sendQuery("SELECT mail FROM " + TABLE_UTENTE );
        ResultSet rs = getStatement().getResultSet();
        String [] emailAccs= new String[getResultSetLength(rs)];
        for (int i = 0; i < emailAccs.length; i++, rs.next())
        {
            emailAccs[i]=new String (rs.getString("mail"));
        }
        int i=0;
        while (i<emailAccs.length)
        {
            if (email.compareTo(emailAccs[i])==0)
                break;
            i++;
        }
        if (i!=emailAccs.length)
            return false;
        else
            return true;
        
    }
    
    private boolean _checkCodFiscExists(String codfisc) throws SQLException
    {
        sendQuery("SELECT codfisc FROM " + TABLE_PARTECIPANTE );
        ResultSet rs = getStatement().getResultSet();
        String [] codfiscParts= new String[getResultSetLength(rs)];
        for (int i = 0; i < codfiscParts.length; i++, rs.next())
        {
            codfiscParts[i]=new String (rs.getString("codfisc"));
        }
        int i=0;
        while (i<codfiscParts.length)
        {
            if (codfisc.compareTo(codfiscParts[i])==0)
                break;
            i++;
        }
        if (i!=codfiscParts.length)
            return false;
        else
            return true;
        
    }
    
    protected boolean _checkCampiVuotiPart (Partecipante p)
    {
        boolean x=false;
        if (p.getCodiceFiscale().trim().length()==0)
            x=true;
        if (p.getMail().trim().length()==0)
            x=true;
        if (p.getCognome().trim().length()==0)
            x=true;
        if (p.getDataNascitaString().trim().length()==0)
            x=true;
        if (p.getDataSrcString().trim().length()==0)
            x=true;
        if (p.getIndirizzo().trim().length()==0)
            x=true;
        if (p.getNome().trim().length()==0)
            x=true;
        if (p.getPassword().trim().length()==0)
            x=true;
        if (p.getTesseraSan().trim().length()==0)
            x=true;
        return x;
    }        
    
    protected void _setOptional(Optional optional) throws SQLException
    {
        sendUpdate("UPDATE " + TABLE_OPTIONAL +
                " SET descrizione='" + optional.getDescrizione() + "'" +
                ", prezzo=" + optional.getPrezzo() +
                " WHERE nome='" + optional.getNome() + "'");
    }
    
 
}
