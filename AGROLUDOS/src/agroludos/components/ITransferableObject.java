package agroludos.components;

/**
 * Interfaccia che indica se il componente è trasferibile tramite
 * l'oggetto TransferObject.
 */
public class ITransferableObject {
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
}
