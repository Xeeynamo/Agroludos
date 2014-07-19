package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.components.*;
import java.sql.*;

public class ManagerSistema extends AgroController
{
    public ManagerSistema(Statement statement, String mail)
    {
        super(statement, mail);
    }

    @Override public TipoCompetizione[] getCompetizioneTipi() throws SQLException
    {
        return super.getCompetizioneTipi();
    }
    @Override public Optional[] getOptional() throws SQLException
    {
        return super.getOptional();
    }
    @Override public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
    @Override public Partecipante[] getPartecipantiMinimal() throws SQLException
    {
        return super.getPartecipantiMinimal();
    }
    @Override public Partecipante getPartecipante(String email) throws SQLException
    {
        return super.getPartecipante(email);
    }
    @Override public Manager[] getManagers() throws SQLException
    {
        return super.getManagers();
    }
}
