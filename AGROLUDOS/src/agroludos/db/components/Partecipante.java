package agroludos.db.components;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Partecipante
{
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
        return "";
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
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d.parse(getDataNascita().toString());
        } catch (ParseException ex) {
            return "";
        }
        d.applyPattern("yyyy/MM/dd");
        return d.toString();
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
    {
        return dataSrc;
    }
    public String getDataSrcString()
    {
        SimpleDateFormat d = new SimpleDateFormat("dd/MM/yyyy");
        try {
            d.parse(getDataSrc().toString());
        } catch (ParseException ex) {
            return "";
        }
        d.applyPattern("yyyy/MM/dd");
        return d.toString();
    }
    public String getCertSrc()
    {
        return certSrc;
    }
}
