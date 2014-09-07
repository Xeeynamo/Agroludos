package agroludos.server.db;

public class Join
{
    String table;
    Condition statement;
    
    public Join(String table, Condition statement)
    {
        this.table = table;
        this.statement = statement;
    }
    
    @Override public String toString()
    {
        return " JOIN " + table + " ON " + statement + " ";
    }
}
