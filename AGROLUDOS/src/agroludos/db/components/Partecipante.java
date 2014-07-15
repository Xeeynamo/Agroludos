package agroludos.db.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Partecipante
{
    //MODIFICA by ROS (12/07/2014) 
    private String mail;
    private String nome;
    private String cognome;
    private String codfisc;
    private String indirizzo;
    private Date dataNascita;
    private char sesso;
    private String tesseraSan;
    private Date dataSrc;
    private String certSrc;
    
    public Partecipante(
            String mail,
            String nome,
            String cognome,
            String codfisc,
            String indirizzo,
            Date dataNascita,
            char sesso,
            String tesseraSan,
            Date dataSrc,
            String certSrc)
    {
        this.mail = mail;
        this.nome = nome;
        this.cognome = cognome;
        this.codfisc = codfisc;
        this.indirizzo = indirizzo;
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.tesseraSan = tesseraSan;
        this.dataSrc = dataSrc;
        this.certSrc = certSrc;
    }
    
    public String getMail()
    {
        return mail;
    }
    public String getNome()
    {
        return nome;
    }
    public String getCognome()
    {
        return cognome;
    }
    public String getCodiceFiscale()
    {
        return codfisc;
    }
    public String getIndirizzo()
    {
        return indirizzo;
    }
    public Date getDataNascita()
    {
        return dataNascita;
    }
    public String getDataNascitaString()
    {
        //MODIFICA by ROS (12/07/2014)
        /*
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d.parse(getDataNascita().toString());
        } catch (ParseException ex) {
            return "";
        }
        d.applyPattern("yyyy/MM/dd");
                
        return d.toString();
        */
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        return d.format(getDataNascita());
        //return getDataNascita().toString();
    }
    public char getSesso()
    {
        return sesso;
    }
    public String getTesseraSan()
    {
        return tesseraSan;
    }
    public Date getDataSrc()
    //public SimpleDateFormat getDataSrc()
    {
        return dataSrc;
    }
    public String getDataSrcString()
    {
        //MODIFICA by ROS (12/07/2014)
        /*
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d.parse(getDataSrc().toString());
        } catch (ParseException ex) {
            return "";
        }
        d.applyPattern("yyyy/MM/dd");
        return d.toString();
        */
        
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        return d.format(getDataSrc());
        
        //return getDataSrc().toString();
    }
    public String getDirSrc()
    {
        return certSrc;
    }
}
