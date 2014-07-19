package agroludos.db.request;

/**
 * Crea una singola dichiarazione
 */
public class Statement
{
    String left;
    String right;
    Query.Operator operator;
    
    public Statement(String left, String right, Query.Operator operator)
    {
        this.left = left;
        this.right = right;
        this.operator = operator;
    }
    
    @Override public String toString()
    {
        return " (" + left + " " + operator + " " + right + ") ";
    }
}
