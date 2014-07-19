package agroludos.db.query;

/**
 * Crea una singola dichiarazione
 */
public class Condition
{
    String left;
    String right;
    Request.Operator operator;
    
    public Condition(String left, String right, Request.Operator operator)
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
