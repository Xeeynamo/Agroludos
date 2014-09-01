package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.components.*;
import agroludos.db.query.Condition;
import agroludos.db.query.Join;
import agroludos.db.query.Request;
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
    @Override public Competizione[] getCompetizioniMinimal(int filter) throws SQLException
    {
        return super.getCompetizioniMinimal(filter);
    }
    @Override public Competizione[] getCompetizioni(String manager) throws SQLException
    {
        return super.getCompetizioni(manager);
    }
    @Override  public Competizione getCompetizione(int idCompetizione) throws SQLException
    {
        return super.getCompetizione(idCompetizione);
    }
    @Override public Optional[] getCompetizioneOptional(int idCompetizione) throws SQLException
    {
        return super.getCompetizioneOptional(idCompetizione);
    }
    @Override public Partecipante[] getPartecipanti (int idCompetizione) throws SQLException
    {
        return super.getPartecipanti(idCompetizione);
    }
}
