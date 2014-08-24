package agroludos.db.user;

import agroludos.db.exception.SrcScadutaException;
import agroludos.db.AgroController;
import agroludos.db.components.*;
import java.sql.*;

public class Utente extends AgroController
{
    public Utente(Statement statement, String mail)
    {
        super(statement, mail);
    }
    
    public void AnnullaPrenotazione (Competizione c) throws SQLException
    {
        annullaPrenotazione(super.getPartecipante(super.getMail()),c);
    }
    
    public void addIscrizioneCompetizione(Competizione c, Optional [] opt) throws SQLException, SrcScadutaException
    {
        super.addIscrizioneCompetizione(super.getPartecipante(super.getMail()), c, opt);
    }
     
    public Competizione getCompetizione (int id) throws SQLException
    {
        int i;
        boolean trovato=false;
        Competizione [] comp=super._getCompetizioni();
        for (i=0;i<comp.length;i++)
        {
            if (comp[i].getId()==id)
            {
                trovato=true;
                break;
            }    
        }
        if (trovato)
            return comp[i];
        else
            throw new SQLException();
    }
    
    public boolean isOptionalSelezionato (Optional opt,Competizione comp) throws SQLException
    {
        return super.isOptionalPrenotato(super.getPartecipante(super.getMail()), opt, comp);
    }
    
    public void setOptionalPrenotazione (Optional opt, Competizione comp,boolean scelto) throws SQLException
    {
        super.setOptionalPrenotazione(opt, super.getPartecipante(super.getMail()), comp, scelto);
    }
    @Override public Optional[] getOptional() throws SQLException
    {
        return super.getOptional();
    }
    @Override public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
    
    public String getMailSys () throws SQLException
    {
        return super.getSysMail();
    }
}
