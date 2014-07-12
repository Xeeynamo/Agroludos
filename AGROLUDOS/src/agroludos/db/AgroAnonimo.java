package agroludos.db;

import agroludos.db.components.Partecipante;
import java.sql.*;

public class AgroAnonimo extends AgroUser
{
    public AgroAnonimo(Statement statement)
    {
        super(statement);
    }
    
    public void addPartec(Partecipante p)
    {
        super._addPartec(p);
    }
}
