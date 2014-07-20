package agroludos.db.user;

import agroludos.db.*;
import agroludos.db.AgroController;
import agroludos.db.components.*;
import java.sql.*;

public class ManagerCompetizione extends AgroController
{
    public ManagerCompetizione(Statement statement, String mail)
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
    public Competizione[] getCompetizioni() throws SQLException
    {
        return super.getCompetizioni(getMail());
    }
    @Override public Partecipante[] getPartecipanti (int idCompetizione) throws SQLException
    {
        return super.getPartecipanti(idCompetizione);
    }
    @Override public void annullaCompetizione(int idCompetizione) throws SQLException
    {
        super.annullaCompetizione(idCompetizione);
    }
    
    public void setNMax (int idCompetizione,int nmax) throws SQLException
    {
        super.setNPartMax(idCompetizione,nmax);
    }
    
    public void setNMin (int idCompetizione,int nmin) throws SQLException
    {
        super.setNPartMin(idCompetizione,nmin);
    }
    public void setPrezzoComp (int idCompetizione,int prezzo) throws SQLException
    {
        super.setPrezzoComp(idCompetizione,(float)prezzo);
    }
}
