package agroludos.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Partecipante extends TransferableObject
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
        String cognome)
    {
        this.mail = mail.trim();
        this.nome = nome.trim();
        this.cognome = cognome.trim();
        this.codfisc = null;
        this.indirizzo = null;
        this.dataNascita = null;
        this.sesso = '-';
        this.tesseraSan = null;
        this.dataSrc = null;
        this.certSrc = null;
    }
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
        this.mail = mail.trim();
        this.nome = nome.trim();
        this.cognome = cognome.trim();
        this.codfisc = codfisc.trim();
        this.indirizzo = indirizzo.trim();
        this.dataNascita = dataNascita;
        this.sesso = sesso;
        this.tesseraSan = tesseraSan.trim();
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
    public String getSrc()
    {
        return certSrc;
    }
    
    /**
     * Controlla se il partecipante corrente contiene parametri validi.
     * Per fare in modo che ritorni vero, ogni campo deve essere riempito
     * con almeno un campo.
     * @return true se il partecipante è valido, viceversa false
     */
    public boolean isValid()
    {
        if (getCodiceFiscale().length() == 0 ||
                getMail().length() == 0 ||
                getNome().length() == 0 ||
                getCognome().length() == 0 ||
                getDataNascitaString().length() == 0 ||
                getDataSrcString().length() == 0 ||
                getIndirizzo().length() == 0 ||
                getTesseraSan().length() == 0)
            return false;
        return true;
    }
    
    /**
     * Ottiene una stringa contenente cognome e nome del partecipante
     * @return 
     */
    @Override public String toString()
    {
        return getCognome() + " " + getNome();
    }
}
