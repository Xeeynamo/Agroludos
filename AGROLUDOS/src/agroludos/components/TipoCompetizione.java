
package agroludos.components;

public class TipoCompetizione extends ITransferableObject
{
    private String nome;
    private String descrizione;
    
    public TipoCompetizione(String nome)
    {
        this.nome = nome;
    }
    public TipoCompetizione(
        String nome,
        String descrizione)
    {
        this.nome = nome;
        this.descrizione = descrizione;
    }
    
    public String getNome()
    {
        return nome;
    }
    public String getDescrizione()
    {
        return descrizione;
    }
    
    @Override public String toString()
    {
        return getNome();
    }
}
