package agroludos.components;

/**
 * Interfaccia che indica se il componente è trasferibile tramite
 * l'oggetto TransferObject.
 */
public class TransferableObject
{
    public int toValue()
    {
        return 0;
    }
    public float toValueF()
    {
        return 0.0f;
    }
    @Override public String toString()
    {
        return "TransferableObject";
    }
    
    public Competizione toCompetizione()
    {
        if (this instanceof Competizione)
            return (Competizione)this;
        return null;
    }
    public Manager toManager()
    {
        if (this instanceof Manager)
            return (Manager)this;
        return null;
    }
    public Optional toOptional()
    {
        if (this instanceof Optional)
            return (Optional)this;
        return null;
    }
    public Partecipante toPartecipante()
    {
        if (this instanceof Partecipante)
            return (Partecipante)this;
        return null;
    }
    public TipoCompetizione toTipoCompetizione()
    {
        if (this instanceof TipoCompetizione)
            return (TipoCompetizione)this;
        return null;
    }
}
