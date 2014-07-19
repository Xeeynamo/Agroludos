package agroludos.db.query;

/**
 * Libreria che gestisce le richieste per il databse
 * @author Luciano
 */
public class Request
{
    /**
     * Lista di operatori usate nelle condizioni
     */
    public enum Operator
    {
        Equal,
        NotEqual,
        Greater,
        GreaterEqual,
        Less,
        LessEqual,
        Or,
        And,
        Not;
        
        @Override public String toString()
        {
            switch (this)
            {
                case Equal:
                    return "=";
                case NotEqual:
                    return "<>";
                case Greater:
                    return ">";
                case GreaterEqual:
                    return ">=";
                case Less:
                    return "<";
                case LessEqual:
                    return "<=";
                case Or:
                    return "OR";
                case And:
                    return "AND";
                case Not:
                    return "NOT";
            }
            return "";
        }
    }
        
    String[] select;
    String from;
    Join[] join;
    Condition where;
    
    public Request(String[] select,
            String from)
    {
        this.select = select;
        this.from = from;
        this.join = null;
        this.where = null;
    }
    public Request(String[] select,
            String from,
            Join[] join)
    {
        this.select = select;
        this.from = from;
        this.join = join;
        this.where = null;
    }
    public Request(String[] select,
            String from,
            Condition where)
    {
        this.select = select;
        this.from = from;
        this.join = null;
        this.where = where;
    }
    
    /**
     * Crea una richiesta
     * @param select lista di campi da ritirare
     * @param from tabella dal quale richiedere i campi
     * @param join lista di join
     * @param where statement per il where
     */
    public Request(String[] select,
            String from,
            Join[] join,
            Condition where)
    {
        this.select = select;
        this.from = from;
        this.join = join;
        this.where = where;
    }
    
    /**
     * Ottiene la richiesta sotto forma di stringa SQL eseguibile
     * @return stringa eseguibile
     */
    @Override public String toString()
    {
        return generateSelect(select) +
                generateFrom(from, join) +
                generateWhere(where);
    }
    
    /**
     * Genera una SELECT
     * @param campi da selezionare
     */
    private static String generateSelect(String[] campi)
    {
        String s = "SELECT ";
        if (campi == null)
            s += "*\n";
        else
        {
            for (int i = 0; i < campi.length; i++)
            {
                s += campi[i];
                if (i + 1 < campi.length)
                {
                    s += ", ";
                }
            }
            s += '\n';
        }
        return s;
    }
    
    /**
     * Genera una FROM con i vari JOIN
     * @param from tabella da selezionare
     * @param join le varie unioni
     */
    private static String generateFrom(String from, Join[] join)
    {
        String s = "FROM " + from;
        if (join != null)
        {
            for (Join j : join)
            {
                s += j;
            }
        }
        return s + "\n";
    }
    
    private static String generateWhere(Condition statement)
    {
        if (statement != null)
            return "WHERE " + statement.toString() + "\n";
        return "";
    }
}
