package agroludos.components;

public class Manager
{
    private String nome;
    private String cognome;
    private String mail;
    
    public Manager(String nome,
            String cognome,
            String mail)
    {
        this.nome = nome;
        this.cognome = cognome;
        this.mail = mail;
    }
    
    public String getNome()
    {
        return nome;
    }
    public String getCognome()
    {
        return cognome;
    }
    public String getMail()
    {
        return mail;
    }
    
    @Override public String toString()
    {
        return getCognome() + " " + getNome();
    }
}
