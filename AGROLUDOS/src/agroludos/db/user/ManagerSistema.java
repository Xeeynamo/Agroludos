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

    /**
     * Ottiene una lista di competizioni gestite da uno specifico manager
     * @param manager mail del manager di competizione
     * @return lista delle competizioni gestite dal manager specificato
     * @throws SQLException 
     */
    public Competizione[] getCompetizioni(String manager) throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    "idCompetizione",
                    "tipo",
                    "data"
                },
                TABLE_COMPETIZIONE,
                new Join[]
                {
                    new Join(TABLE_MAN_COMP,
                            new Condition(
                                    TABLE_MAN_COMP + ".mail",
                                    TABLE_COMPETIZIONE + ".manager",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(TABLE_MAN_COMP + ".mail", "\"" + manager + "\"", Request.Operator.Equal)
        );
        ResultSet rs = sendQuery(q + "ORDER BY data");
        Competizione[] c = new Competizione[getResultSetLength(rs)];
        for (int i = 0; i < c.length; i++, rs.next())
        {
            c[i] = new Competizione(
                rs.getInt("idCompetizione"),
                new TipoCompetizione(rs.getString("tipo")),
                rs.getDate("data"));
        }
        return c;
    }
    
    /**
     * Ottiene una descrizione completa della competizione
     * @param idCompetizione id della competizione da ottenere
     * @return ritorna la competizione
     * @throws SQLException 
     */
    public Competizione getCompetizione(int idCompetizione) throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    "idCompetizione",
                    "tipo",
                    "manager",
                    "data",
                    "partMin",
                    "partMax",
                    "prezzo",
                    "annullata",
                    TABLE_COMPETIZIONE + ".descrizione",
                    TABLE_MAN_COMP + ".mail",
                    TABLE_MAN_COMP + ".nome",
                    TABLE_MAN_COMP + ".cognome",
                },
                TABLE_COMPETIZIONE,
                new Join[]
                {
                    new Join(TABLE_MAN_COMP,
                            new Condition(
                                TABLE_MAN_COMP + ".mail",
                                TABLE_COMPETIZIONE + ".manager",
                                Request.Operator.Equal
                            )),
                    new Join(TABLE_COMP_TYPE,
                            new Condition(
                                TABLE_COMP_TYPE + ".nome",
                                TABLE_COMPETIZIONE + ".tipo",
                                Request.Operator.Equal))
                }
        );
        int nPart = getNPartecipanti(idCompetizione);
        Optional[] opt = getOptional(idCompetizione);
        ResultSet rs = sendQuery(q + "ORDER BY data");
        Competizione c = new Competizione(
                rs.getInt("idCompetizione"),
                rs.getFloat("prezzo"),
                rs.getInt("partMin"),
                rs.getInt("partMax"),
                nPart,
                new TipoCompetizione(rs.getString("tipo"), rs.getString(TABLE_COMPETIZIONE + ".descrizione")),
                new Manager(
                        rs.getString(TABLE_MAN_COMP + ".nome"),
                        rs.getString(TABLE_MAN_COMP + ".cognome"),
                        rs.getString("manager")
                ),
                rs.getDate("data"),
                opt);
        return c;
    }
    
    /*public Optional[] getOptionalFromCompetizione(int idCompetizione) throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    "nome",
                    "descrizione",
                    "prezzo"
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
                }
        );
        ResultSet rs = sendQuery(q + "ORDER BY nome");
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (int i = 0; i < opt.length; i++, rs.next())
        {
            opt[i] = new Optional(
                rs.getString("nome"),
                rs.getString("descrizione"),
                rs.getFloat("prezzo"));
        }
        return opt;
    }*/
}
