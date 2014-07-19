package agroludos.db;

//import agroludos.db.components.Partecipante;
import agroludos.db.user.ManagerCompetizione;
import agroludos.db.user.ManagerSistema;
import agroludos.db.user.Utente;
import agroludos.db.user.Anonimo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
    
    public AgroController Login(String mail, String password)
    {
        try
        {
            int tipo = LoginAnonimo().getUserType(mail, password);
            switch (tipo) 
            {
                 case 0:
                     return new Utente(s, mail);
                 case 1:
                     return new ManagerCompetizione(s, mail);
                 case 2:
                     return new ManagerSistema(s, mail);
                 case 3:
                     return new Anonimo(s);
                 default:
                     JOptionPane.showMessageDialog(null, "Indirizzo E-mail o password errati\n",
                             "Errore", JOptionPane.ERROR_MESSAGE);
                     return null;
             }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Impossibile stabilire una connessione col database\n" +
                    e.toString(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
        return null;
    }
    
    public Anonimo LoginAnonimo()
    {
        return new Anonimo(s);
    }
}
