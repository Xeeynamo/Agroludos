package agroludos.db;

import java.io.IOException;
import java.sql.*;

public class AgroConnect
{
    Connection db;
    Statement s;
    
    public AgroConnect(String user, String password) throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException, IOException
    {
        // Carico il driver JDBC
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // mi connetto al database
        db = DriverManager.getConnection("jdbc:mysql://localhost/" + "agroludos?" +
                "user="+user + "&password="+password);
        
        // creo lo statement per l'invio della query
        s = (Statement)db.createStatement();
    }
    
    public AgroUser Login(String user, String password)
    {
        return new AgroSysMan(s);
    }
    
    // PARTE DEDICATA AL MANAGER DI SISTEMA
    
    
    public int getPartecipanteId()
    {
        return 0;
    }
    public int getCostoCompetizione()
    {
        return 0;
    }
    public int getOptionalPrezzo(int index)
    {
        return 0;
    }
    public boolean getOptionalScelti(int index)
    {
        return false;
    }
    public String getManagerNome()
    {
        return "-";
    }
    public String getManagerMail()
    {
        return "-";
    }
}
