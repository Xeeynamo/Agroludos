package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.components.*;
import agroludos.db.exception.*;
import java.sql.*;

public class ManagerCompetizione extends AgroController
{
    public ManagerCompetizione(Statement statement, String mail)
    {
        super(statement, mail);
    }
    
    @Override public String getMail()
    {
        return super.getMail();
    }
    @Override public TipoCompetizione[] getCompetizioneTipi() throws SQLException
    {
        return super.getCompetizioneTipi();
    }
    @Override public Optional[] getOptional() throws SQLException
    {
        return super.getOptional();
    }
    @Override public Optional getOptional(String nome) throws SQLException
    {
        return super.getOptional(nome);
    }
    @Override public Optional[] getOptional(int idCompetizione) throws SQLException
    {
        return super.getOptional(idCompetizione);
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
    @Override public Partecipante getPartecipante(String email) throws SQLException
    {
        return super.getPartecipante(email);
    }
    @Override public void annullaCompetizione(int idCompetizione) throws SQLException
    {
        super.annullaCompetizione(idCompetizione);
    }
    @Override public void creaCompetizione(Competizione c) throws SQLException,
            TipoCompetizioneInvalidException,
            MinMaxException,
            DatePriorException
    {
        super.creaCompetizione(c);
    }
    
    public void setNMax (int idCompetizione,int nmax) throws SQLException
    {
        super.setNPartMax(idCompetizione,nmax);
    }
    
    public void setNMin (int idCompetizione,int nmin) throws SQLException
    {
        super.setNPartMin(idCompetizione,nmin);
    }
    public void setPrezzoComp (int idCompetizione,float prezzo) throws SQLException
    {
        super.setPrezzoComp(idCompetizione,prezzo);
    }
    public void setOptionalCompetizione (Competizione c, Optional o) throws SQLException
    {
        super.setOptionalCompetizione(c, o);
    }
    
    public void dropOptionalCompetizione (Competizione c, Optional o) throws SQLException
    {
        super.dropOptionalCompetizione(c, o);
    }
    
    public void dropPrenotazione (Competizione c, Partecipante p) throws SQLException
    {
        super.dropPrenotazione(p, c);
    }
}
