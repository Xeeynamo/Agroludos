package agroludos.db.request;

/**
 * Libreria che gestisce le richieste per il databse
 * @author Luciano
 */
public class Query
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
        LessEqual;
        
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
            }
            return "";
        }
    }
        
    String[] select;
    String from;
    Join[] join;
    Statement where;
        
    /**
     * Crea una richiesta
     * @param select lista di campi da ritirare
     * @param from tabella dal quale richiedere i campi
     * @param join lista di join
     * @param where statement per il where
     */
    public Query(String[] select,
            String from,
            Join[] join,
            Statement where)
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
            for (int i = 0; i < s.length(); i++)
            {
                s += campi[i];
                if (i + 1 < s.length())
                {
                    s += ", ";
                }
            }
            s += '\n';
        }
        return "";
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
    
    private static String generateWhere(Statement statement)
    {
        if (statement != null)
            return statement.toString();
        return "";
    }
}
