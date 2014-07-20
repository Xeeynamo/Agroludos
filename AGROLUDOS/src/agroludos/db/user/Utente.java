package agroludos.db.user;

import agroludos.db.*;
import agroludos.db.AgroController;
import agroludos.db.components.*;
import java.sql.*;

public class Utente extends AgroController
{
    public Utente(Statement statement, String mail)
    {
        super(statement, mail);
    }
    
    public Competizione [] getCompetizioniDisponibili () throws SQLException
    {
       Competizione [] c= super._getCompetizioni();
       int NComp=0;
       for (int i=0;i<c.length;i++)
       {
           if((super.getNGiorniMancanti((Date)c[i].getDataComp())<=1)||(super.isPrenotato(super.getMail(), c[i].getId())))
               c[i]=null;
           else
                NComp++;
       }
       Competizione [] c1=new Competizione[NComp];
       for (int i=0,j=0;i<c.length;i++)
       {
           if(c[i]!=null)
           {
               c1[j]=c[i];
               j++;
           }
       }
       return c1;        
    }
    
    public Competizione [] getCompetizioniPrenotate () throws SQLException
    {
       Competizione [] c= super._getCompetizioni();
       int NComp=0;
       for (int i=0;i<c.length;i++)
       {
           if((super.getNGiorniMancanti((Date)c[i].getDataComp())==0)||(!super.isPrenotato(super.getMail(), c[i].getId())))
               c[i]=null;
           else
                NComp++;
       }
       Competizione [] c1=new Competizione[NComp];
       for (int i=0,j=0;i<c.length;i++)
       {
           if(c[i]!=null)
           {
               c1[j]=c[i];
               j++;
           }
       }
       return c1;        
    }
    
    public void addIscrizioneCompetizione(Competizione c, Optional [] opt) throws SQLException, SrcScadutaException
    {
        super._addIscrizioneCompetizione(super.getPartecipante(super.getMail()), c, opt);
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
        return super._isOptionalPrenotato(super.getPartecipante(super.getMail()), opt, comp);
    }
    
    @Override public Optional[] getOptional() throws SQLException
    {
        return super.getOptional();
    }
    @Override public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
}
