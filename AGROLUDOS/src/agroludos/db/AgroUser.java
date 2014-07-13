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
    
    public AgroUser(Statement statement)
    {
        this.statement = statement;
    }
    
    protected Statement getStatement()
    {
        return statement;
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
    
    protected void _addPartec(Partecipante p) throws SQLException
    {
        //MODIFICA by ROS (13/07/2014)
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
        
        statement.executeUpdate(s1);
        statement.executeUpdate(s2);
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
    protected void _setOptional(Optional optional) throws SQLException
    {
        sendUpdate("UPDATE " + TABLE_OPTIONAL +
                " SET descrizione='" + optional.getDescrizione() + "'" +
                ", prezzo=" + optional.getPrezzo() +
                " WHERE nome='" + optional.getNome() + "'");
    }
    
 
}
