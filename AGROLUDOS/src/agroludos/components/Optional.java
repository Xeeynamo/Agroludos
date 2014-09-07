package agroludos.components;

public class Optional
{
    String name;
    String description;
    float price;

    public Optional(String name, String description, float price)
    {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public String getNome()
    {
        return name;
    }
    public String getDescrizione()
    {
        return description;
    }
    public float getPrezzo()
    {
        return price;
    }
    
    @Override public String toString()
    {
        return getNome();
    }
}