package agroludos.db.user;

import agroludos.db.AgroController;
import agroludos.db.components.*;
import agroludos.db.query.Condition;
import agroludos.db.query.Join;
import agroludos.db.query.Request;
import java.sql.*;

public class ManagerSistema extends AgroController
{
    public ManagerSistema(Statement statement, String mail)
    {
        super(statement, mail);
    }

    @Override public TipoCompetizione[] getCompetizioneTipi() throws SQLException
    {
        return super.getCompetizioneTipi();
    }
    @Override public Optional[] getOptional() throws SQLException
    {
        return super.getOptional();
    }
    @Override public void setOptional(Optional optional) throws SQLException
    {
        super.setOptional(optional);
    }
    @Override public Partecipante[] getPartecipantiMinimal() throws SQLException
    {
        return super.getPartecipantiMinimal();
    }
    @Override public Partecipante getPartecipante(String email) throws SQLException
    {
        return super.getPartecipante(email);
    }
    @Override public Manager[] getManagers() throws SQLException
    {
        return super.getManagers();
    }
    @Override public Competizione[] getCompetizioni(String manager) throws SQLException
    {
        return super.getCompetizioni(manager);
    }
    @Override  public Competizione getCompetizione(int idCompetizione) throws SQLException
    {
        return super.getCompetizione(idCompetizione);
    }
    @Override public int[] getPartecipanteCompetizioni(String mail) throws SQLException
    {
        return super.getPartecipanteCompetizioni(mail);
    }
        
    /**
     * Ottiene una lista di optional usata dalla competizione specificata
     * @param idCompetizione da analizzare
     * @return lista degli optional
     * @throws SQLException 
     */
    public Optional[] getCompetizioneOptional(int idCompetizione) throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    "nome",
                    "descrizione",
                    TABLE_OPTIONAL_COMPETIZIONE + "prezzo",
                },
                TABLE_OPTIONAL_COMPETIZIONE,
                new Join[]
                {
                    new Join(TABLE_COMPETIZIONE,
                            new Condition(
                                TABLE_COMPETIZIONE + ".idCompetizione",
                                TABLE_OPTIONAL_COMPETIZIONE + ".competizione",
                                Request.Operator.Equal
                            )),
                    new Join(TABLE_OPTIONAL,
                            new Condition(
                                TABLE_OPTIONAL + ".nome",
                                TABLE_OPTIONAL_COMPETIZIONE + ".optional",
                                Request.Operator.Equal))
                },
                new Condition("competizione", String.valueOf(idCompetizione), Request.Operator.Equal)
        );
        ResultSet rs = sendQuery(q + "ORDER BY data");
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (Optional o : opt)
        {
            o = new Optional(
                    rs.getString("nome"),
                    rs.getString("descrizione"),
                    rs.getFloat(TABLE_OPTIONAL_COMPETIZIONE + "prezzo")
            );
        }
        return opt;
    }
}
