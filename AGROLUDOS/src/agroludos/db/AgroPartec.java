package agroludos.db;

<<<<<<< HEAD
import agroludos.db.components.CampiVuotiException;
import agroludos.db.components.Competizione;
import agroludos.db.components.Optional;
import agroludos.db.components.Partecipante;
=======
import agroludos.db.*;
import agroludos.db.components.*;
>>>>>>> origin/master
import java.sql.*;

public class AgroPartec extends AgroUser
{
<<<<<<< HEAD
    
    public AgroPartec(Statement statement)
=======
    public AgroPartec(Statement statement, String mail)
>>>>>>> origin/master
    {
        super(statement, mail);
    }
    
    public Competizione [] getCompetizioniDisponibili () throws SQLException, CampiVuotiException
    {
        return super._getCompetizioniDisponibili();
    }

    
    public Optional[] getOptional() throws SQLException
    {
        return super._getOptional();
    }
    public void setOptional(Optional optional) throws SQLException
    {
        super._setOptional(optional);
    }
}
