package agroludos.db.request;

public class Join
{
    String table;
    Statement statement;
    
    public Join(String table, Statement statement)
    {
        this.table = table;
        this.statement = statement;
    }
    
    @Override public String toString()
    {
        return " (JOIN" + table + " ON " + statement + ") ";
    }
}
