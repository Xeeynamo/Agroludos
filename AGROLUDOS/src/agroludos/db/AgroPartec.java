package agroludos.db;

import agroludos.db.*;
import agroludos.db.components.*;
import java.sql.*;

public class AgroPartec extends AgroUser
{
    public AgroPartec(Statement statement, String mail)
    {
        super(statement, mail);
    }
    
    public Competizione [] getCompetizioniDisponibili () throws SQLException
    {
       Competizione [] c= super._getCompetizioni();
       int NComp=0;
       for (int i=0;i<c.length;i++)
       {
           if((super.isScaduto((Date)c[i].getDataComp(),1))||(super.isPrenotato(mail, c[i])))
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
    
    public void setIscrizioneCompetizione(Competizione c, Optional [] opt) throws SQLException
    {
        super._setIscrizioneCompetizione(super.getPartecipante(mail), c, opt);
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
    
    public Optional[] getOptional() throws SQLException
    {
        return super._getOptional();
    }
    public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
}
