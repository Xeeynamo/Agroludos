package agroludos.db;

import java.sql.*;

public class AgroUser
{
    private static final String DB_AGRO = "agroludos";
    private static final String TABLE_OPTIONAL = "optional";
    
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
    
    protected String[] _getOptionalNomi() throws SQLException
    {
        sendQuery("SELECT nome FROM " + TABLE_OPTIONAL);
        ResultSet rs = getStatement().getResultSet();
        String[] str = new String[getResultSetLength(rs)];
        for (int i = 0; i < str.length; i++, rs.next())
        {
            str[i] = rs.getString("nome");
        }
        return str;
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
