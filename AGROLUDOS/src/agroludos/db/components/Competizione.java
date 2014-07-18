/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.db.components;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Luigi Rosini
 */
public class Competizione 
{
    private int id;
    private float prezzo;
    private int nmin;
    private int nmax;
    private int npart;
    private String tipo;
    private String nome_mc;
    private String cognome_mc;
    private String email_mc;
    private Date data_comp;
    private Optional [] opt_comp;
    
    public Competizione(
        int id,
        float prezzo,
        int nmin,
        int nmax,
        int npart,
        String tipo,
        String nome_mc,
        String cognome_mc,
        String email_mc,
        Date data_comp,
        Optional [] opt_comp)
    {
        this.id=id;
        this.prezzo=prezzo;
        this.nmin=nmin;
        this.nmax=nmax;
        this.npart=npart;
        this.tipo=tipo;
        this.nome_mc=nome_mc;
        this.cognome_mc=cognome_mc;
        this.email_mc=email_mc;
        this.data_comp=data_comp;
        this.opt_comp=opt_comp;
    }
    
    public int getId()
    {
        return this.id;
    }
    
    public float getPrezzo()
    {
        return this.prezzo;
    }
    
    public int getNMin()
    {
        return this.nmin;
    }
    
    public int getNMax()
    {
        return this.nmax;
    }
    
    public int getNPart()
    {
        return this.npart;
    }
    
    public String getTipoComp()
    {
        return this.tipo;
    }
    
    public String getNomeMC()
    {
        return this.nome_mc;
    }
    
    public String getCognomeMC()
    {
        return this.cognome_mc;
    }
    
    public String getEmailMC()
    {
        return this.email_mc;
    }
    
    public Date getDataComp ()
    {
        return this.data_comp;
    }
    
    public String getDataCompString ()
    {
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        return d.format(getDataComp());
    }
    
    public Optional [] getOptComp ()
    {
        return this.opt_comp;
    }
    
    public String toString()
    {
        return getTipoComp()+"   ("+getDataCompString()+")";
    }        
}
