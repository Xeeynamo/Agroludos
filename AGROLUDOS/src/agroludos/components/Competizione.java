/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package agroludos.components;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Questa classe rappresenta una competizione di sola lettura
 */
public class Competizione extends ITransferableObject
{
    private int id;
    private float prezzo;
    private int nmin;
    private int nmax;
    private int npart;
    private TipoCompetizione tipo;
    private Manager manager;
    private Date data_comp;
    private Optional [] opt_comp;
    
    public Competizione(
        int id,
        TipoCompetizione tipo,
        Date data_comp)
    {
        this.id = id;
        this.prezzo = 0;
        this.nmin = 0;
        this.nmax = 0;
        this.npart = 0;
        this.tipo = tipo;
        this.manager = null;
        this.data_comp = data_comp;
        this.opt_comp = null;
    }
    public Competizione(
        int id,
        float prezzo,
        int nmin,
        int nmax,
        int npart,
        TipoCompetizione tipo,
        Manager manager,
        Date data_comp,
        Optional [] opt_comp)
    {
        this.id = id;
        this.prezzo = prezzo;
        this.nmin = nmin;
        this.nmax = nmax;
        this.npart = npart;
        this.tipo = tipo;
        this.manager = manager;
        this.data_comp = data_comp;
        this.opt_comp = opt_comp;
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
    public TipoCompetizione getTipoCompetizione()
    {
        return this.tipo;
    }
    public Manager getManager()
    {
        return manager;
    }
    public Date getDataComp ()
    {
        return this.data_comp;
    }
    public String getDataCompString ()
    {
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        return d.format(getDataComp());
    }
    public Optional [] getOptional ()
    {
        return this.opt_comp;
    }
    
    @Override public String toString()
    {
        return getTipoCompetizione().getNome() + " (" + getDataCompString() + ")";
    }        
}
