package agroludos.db;

import agroludos.db.components.*;
import agroludos.db.query.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class AgroController
{
    private static final String DB_AGRO = "agroludos";
    private static final String TABLE_PARTECIPANTE = "partecipante";
    private static final String TABLE_PRENOTAZIONE = "prenotazione";
    private static final String TABLE_OPTIONAL = "optional";
    private static final String TABLE_OPTIONAL_COMPETIZIONE = "optional_competizione";
    private static final String TABLE_OPTIONAL_PRENOTAZIONE = "optional_prenotazione";
    private static final String TABLE_UTENTE = "utente";
    private static final String TABLE_COMPETIZIONE = "competizione";
    private static final String TABLE_MAN_COMP = "manager";
    private static final String TABLE_COMP_TYPE = "tipocompetizione";
    
    Statement statement;
    String mail;
    
    public AgroController(Statement statement, String mail)
    {
        this.statement = statement;
        this.mail = mail;
    }
    
    protected Statement getStatement()
    {
        return statement;
    }
    protected String getMail()
    {
        return mail;
    }
    protected int getResultSetLength(ResultSet rs) throws SQLException
    {
        if (rs != null)
        {
            int size;
            rs.beforeFirst();
            rs.last();
            size = rs.getRow();
            rs.first();
            return size;
        }
        else
            return 0;
    }
    

    private ResultSet sendQuery(String query) throws SQLException
    {
        getStatement().executeQuery(query);
        return getStatement().getResultSet();
    }
    private void sendUpdate(String query) throws SQLException
    {
        getStatement().executeUpdate(query);
    }
    
    protected int getUserType(String mail, String password) throws SQLException
    {
        Request q = new Request(
                new String[] { "tipo" },
                TABLE_UTENTE,
                new Condition(
                    new Condition("mail", "\"" + mail + "\"", Request.Operator.Equal).toString(),
                    new Condition("password", "PASSWORD('" + password + "')", Request.Operator.Equal).toString(),
                        Request.Operator.And)
        );
        ResultSet rs = sendQuery(q.toString());
        if (rs.next())
            return rs.getInt("tipo");
        return -1;
    }
    
    protected void _addPartec(String password,Partecipante p) throws SQLException,DefEmailException,DefCodFiscException, CampiVuotiException
    {
        //MODIFICA by ROS (13/07/2014)
        if (isMailExists(p.getMail()))
            throw new DefEmailException();
        if (_checkCodFiscExists(p.getCodiceFiscale()))
            throw new DefCodFiscException();
        if (isCampiVuoti(password,p))
            throw new CampiVuotiException();
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String date=d.format(p.getDataNascita());
        String dateSrc=d.format(p.getDataSrc());
        String s1 = "INSERT INTO " + TABLE_UTENTE + " VALUES (" +
                "\"" + p.getMail()+ "\"," +
                "PASSWORD(\"" + password+ "\")," +
                0 + ");";
        String s2 = "INSERT INTO " + TABLE_PARTECIPANTE + " VALUES (" +
                "\"" + p.getCodiceFiscale()+ "\"," +
                "\"" + p.getNome() + "\"," +
                "\"" + p.getCognome() + "\"," +
                "\"" + p.getIndirizzo() + "\"," +
                //"'" + p.getDataNascita()+ "'" +
                //"'" + p.getDataNascitaString()+ "'," +
                "'" +date+ "'," +
                "'" + p.getSesso()+ "'," +
                "\"" + p.getTesseraSan()+ "\"," +
                //"\"" + p.getCertSrc()+ "\"" +
                //"'" + p.getDataSrc()+ "'";
                //"'"+p.getDataSrcString()+"'," +
                "'" + dateSrc+ "'," +
                "'" + p.getMail()+ "'," +
                "'" + p.getDirSrc()+ "');";
        
        //statement.executeUpdate(s1);
        //statement.executeUpdate(s2);
        sendUpdate(s1);
        sendUpdate(s2);
    }
   
    /**
     * riporta la lista di TUTTE le competizioni presenti nel sistema
     * @return Lista delle competizioni se c'è almeno una competizione nel sistema, altrimenti lancia l'eccezione
     * @throws SQLException 
     */
    protected Competizione[] _getCompetizioni() throws SQLException
    {
        String s1="drop view if exists n_partecipanti,optional_competizione,manager_competizioni;";
        sendUpdate(s1);
        s1=new String("create view n_partecipanti as "
                + "select competizione.id as id_comp, count(prenotazione.part) as n_part "
                + "from competizione join prenotazione on competizione.id=prenotazione.comp "
                + "group by competizione.id;");
        sendUpdate(s1);
        s1=new String("create view optional_competizione as "
                +"select competizione.id as id_comp,optional.nome as optional,optional.descrizione as descrizione,opt_comp.prezzo as prezzo "
                +"from (competizione left join opt_comp on competizione.id=opt_comp.comp) "
                +"left join optional on opt_comp.opt=optional.nome "
                +"order by competizione.id;");
        sendUpdate(s1);
        s1=new String ("create view manager_competizioni as "
                +"select competizione.id as id_comp, mc.cognome as cognome_mc, mc.nome as nome_mc, mc.mail as mail_mc "
                +"from competizione join mc on competizione.manager_comp=mc.id "
                +"order by mc.cognome, mc.nome;");
        sendUpdate(s1);
        s1=new String ("select competizione.id,competizione.prezzo,competizione.nmin,competizione.nmax,n_partecipanti.n_part,competizione.tipo, "
                +"manager_competizioni.nome_mc,manager_competizioni.cognome_mc,manager_competizioni.mail_mc,competizione.data_comp, "
                +"optional_competizione.optional,optional_competizione.descrizione,optional_competizione.prezzo,tipo_comp.descrizione "
                +"from (((competizione left join n_partecipanti on competizione.id=n_partecipanti.id_comp) "
                +"join manager_competizioni on competizione.id=manager_competizioni.id_comp) "
                +"join optional_competizione on competizione.id=optional_competizione.id_comp) "
                +"join tipo_comp on tipo_comp.nome=competizione.tipo "
                +"group by competizione.id,optional_competizione.optional "
                +"order by competizione.id;");
        ResultSet rs=sendQuery(s1);
        if (getResultSetLength(rs)!=0)
        {
            int nRis=1;
            int id=rs.getInt("competizione.id");
            while(!rs.isLast())
            {
                rs.next();
                if(id!=rs.getInt("competizione.id"))
                {
                    nRis++;
                    id=rs.getInt("competizione.id");
                }    
            }
            while (!rs.isFirst())
                rs.previous();
            Competizione [] comp=new Competizione[nRis];
            for (int i=0;i<nRis;i++,rs.next())
            {
                Optional [] opt=AgroController.this.getOptional(rs,rs.getInt("competizione.id"));
                Manager m=new Manager(rs.getString(7),rs.getString(8),rs.getString(9));
                TipoCompetizione t=new TipoCompetizione(rs.getString(6),rs.getString("tipo_comp.descrizione"));
                comp[i]=new Competizione (rs.getInt(1),rs.getFloat(2),rs.getInt(3),rs.getInt(4),rs.getInt(5),t,m,rs.getDate(10),opt);
            }
            return comp;
        }
            
        else
            throw new SQLException();

    }       

    // <editor-fold defaultstate="collapsed" desc="Parte dedicata alle competizioni">
    protected TipoCompetizione[] getCompetizioneTipi() throws SQLException
    {
        ResultSet rs = sendQuery("SELECT * FROM " + TABLE_COMP_TYPE);
        TipoCompetizione[] tcomp = new TipoCompetizione[getResultSetLength(rs)];
        for (int i = 0; i < tcomp.length; i++, rs.next())
        {
            tcomp[i] = new TipoCompetizione(rs.getString("nome"), rs.getString("descrizione"));
        }
        return tcomp;
    }
    /**
     * Verifica se la competizione indicata come parametro
     * è stato già prenotato dal partecipante
     * @param mail email del partecipante 
     * @param c competizione su cui si vuole controllare se è stata fatta una prenotazione
     * @return true se il partecipante si è già prenotato a quella competizione, altrimenti false
     * @throws SQLException 
     */
    protected boolean isPrenotato(String mail,Competizione c) throws SQLException
    {
        boolean trovato=false;
        Partecipante p=getPartecipante(mail);
        ResultSet rs=sendQuery("select comp from prenotazione where part=\""+p.getCodiceFiscale()+"\";");
        if (rs!=null)
        {
            while(rs.next())
            {
                if(rs.getInt(1)==c.getId())
                {
                    trovato=true;
                    break;
                }
            }
        }
        return trovato;
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Parte dedicata agli optional">
    protected Optional[] getOptional() throws SQLException
    {
        ResultSet rs = sendQuery(new Request(null, TABLE_OPTIONAL).toString());
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (int i = 0; i < opt.length; i++, rs.next())
        {
            opt[i] = new Optional(rs.getString("nome"), rs.getString("descrizione"), rs.getFloat("prezzo"));
        }
        return opt;
    }


    private Optional[] getOptional(ResultSet rs, int id) throws SQLException
    {
        boolean End;
        int NOpt;
        Optional [] opt=new Optional [3];
        String optional=rs.getString("optional_competizione.optional");
        if (optional!=null)
        {
            NOpt=1;
            opt[NOpt-1]=new Optional(rs.getString("optional_competizione.optional"),rs.getString("optional_competizione.descrizione"),rs.getFloat("optional_competizione.prezzo"));
            End=false;
            while ((!rs.isLast())&&(!End))
            {
                rs.next();
                if(rs.getInt("competizione.id")==id)
                {
                    NOpt++;
                    if (NOpt<=3)
                        opt[NOpt-1]=new Optional(rs.getString("optional_competizione.optional"),rs.getString("optional_competizione.descrizione"),rs.getFloat("optional_competizione.prezzo"));
                }
                else
                {
                    rs.previous();
                    End=true;
                }   
                
            }
            Optional[] opt_ris=new Optional [NOpt];
            for (int i=0;i<NOpt;i++)
                opt_ris[i]=opt[i];
            return opt_ris;
        }
        else
            return null;
    }
    

    /**
     * Ottiene la lista degli optional di una determinata competizione
     * @param competizioneId id della competizione scelta
     * @return lista degli optional selezionabili
     * @throws SQLException 
     */
    protected Optional[] getOptional(int competizioneId) throws  SQLException 
    {
        // "SELECT * FROM " + TABLE_OPTIONAL +" join opt_comp on optional.nome=opt_comp.opt where opt_comp.comp="+id
        Request q = new Request(
                (String[])null,
                TABLE_OPTIONAL,
                new Join[]
                {
                    new Join(TABLE_OPTIONAL_COMPETIZIONE,
                            new Condition(
                                    TABLE_OPTIONAL + ".nome",
                                    TABLE_OPTIONAL_COMPETIZIONE + ".opt",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(TABLE_OPTIONAL_COMPETIZIONE + ".comp", Integer.toString(competizioneId), Request.Operator.Equal)
        );
        
        ResultSet rs = sendQuery(q.toString());
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (int i = 0; i < opt.length; i++, rs.next())
        {
            opt[i] = new Optional(
                    rs.getString("nome"),
                    rs.getString("descrizione"),
                    rs.getFloat(TABLE_OPTIONAL_COMPETIZIONE + ".prezzo"));
        }
        return opt;

    }

    protected void setOptional(Optional optional) throws SQLException
    {
        sendUpdate("UPDATE " + TABLE_OPTIONAL +
                " SET descrizione='" + optional.getDescrizione() + "'" +
                ", prezzo=" + optional.getPrezzo() +
                " WHERE nome='" + optional.getNome() + "'");
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Parte dedicata ai partecipanti">
    /**
     * Ottiene le informazioni base di un partecipante, quali mail nome e cognome.
     * Questa funzione serve per permettere di restituire una lista di partecipanti,
     * ma senza appesantire troppo le query ed il client. Infatti non vi sarà
     * bisogno di dover caricare tutti i partecipanti insieme, ma solo caricare le
     * informazioni strettamente necessarie per poi entrare nel dettaglio di un
     * partecipante uno alla volta. Si noti che ogni partecipante restituito da
     * questa funzione, presenterà isValid() sempre falso poiché gli altri campi
     * vengono lasciati vuoti. Si noti come la lista sia ordinata per cognome.
     * @return lista dei partecipanti
     * @throws SQLException 
     */
    protected Partecipante[] getPartecipantiMinimal() throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    TABLE_PARTECIPANTE + ".mail",
                    "nome",
                    "cognome"
                },
                TABLE_UTENTE,
                new Join[]
                {
                    new Join(TABLE_PARTECIPANTE,
                            new Condition(
                                    TABLE_UTENTE + ".mail",
                                    TABLE_PARTECIPANTE + ".mail",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(TABLE_UTENTE + ".tipo", "0", Request.Operator.Equal)
        );
        ResultSet rs = sendQuery(q + "ORDER BY cognome");
        Partecipante[] p = new Partecipante[getResultSetLength(rs)];
        for (int i = 0; i < p.length; i++, rs.next())
        {
            p[i] = new Partecipante(
                rs.getString("mail"),
                rs.getString("nome"),
                rs.getString("cognome"));
        }
        return p;
    }

    /**
     * Ottiene un partecipante a partire dal suo indirizzo mail
     * @param email indirizzo mail specificato
     * @return struttura del partecipante
     * @throws SQLException 
     */
    protected Partecipante getPartecipante(String email) throws SQLException
    {
        Request q = new Request(
                (String[])null,
                TABLE_UTENTE,
                new Join[]
                {
                    new Join(TABLE_PARTECIPANTE,
                            new Condition(
                                    TABLE_UTENTE + ".mail",
                                    TABLE_PARTECIPANTE + ".mail",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(
                        new Condition(TABLE_UTENTE + ".mail", "\"" + email + "\"", Request.Operator.Equal).toString(),
                        new Condition(TABLE_UTENTE + ".tipo", "0", Request.Operator.Equal).toString(),
                        Request.Operator.And)
        );
        ResultSet rs = sendQuery(q.toString());
        if (getResultSetLength(rs) != 1)
            throw new SQLException();
        else
            return new Partecipante(email,
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("codfisc"),
                    rs.getString("indirizzo"),
                    rs.getDate("datanascita"),
                    (char)rs.getString("sesso").charAt(0),
                    rs.getString("tes_san"),
                    rs.getDate("data_src"),
                    rs.getString("src"));  
    }
    
    /**
     * Ottiene il numero di partecipanti di una determinata competizione
     * @param competizioneId id della competizione da verificare
     * @return numero di partecipanti
     * @throws SQLException 
     */
    protected int getNPartecipanti (int competizioneId) throws SQLException
    {
        // "select count(*) from competizione join prenotazione on competizione.id=prenotazione.comp where competizione.id="+id;
        Request q = new Request(
                new String[]{ "count(*)" },
                TABLE_COMPETIZIONE,
                new Join[]
                {
                    new Join(TABLE_PRENOTAZIONE,
                            new Condition(
                                    TABLE_COMPETIZIONE + ".id",
                                    TABLE_PRENOTAZIONE + ".id",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(TABLE_COMPETIZIONE + ".id", Integer.toString(competizioneId), Request.Operator.Equal)
        );
        return sendQuery(q.toString()).getInt(1);
    }
    
    protected void _addIscrizioneCompetizione(Partecipante p, Competizione c,Optional [] opt) throws SQLException, SrcScadutaException
    {
        if(getNGiorniMancanti(c.getDataComp(),p.getDataSrc())>=365)
            throw new SrcScadutaException();
        System.out.println("insert into prenotazione values (\""+p.getCodiceFiscale()+"\","+c.getId()+");");
        
        sendUpdate("insert into prenotazione values (\""+p.getCodiceFiscale()+"\","+c.getId()+");");
        if (opt!=null)
        {
            for (int i=0;i<opt.length;i++)
            {
                System.out.println("insert into opt_pren values (\""+opt[i].getNome()+"\",\""+p.getCodiceFiscale()+"\","+c.getId()+");");
                sendUpdate("insert into opt_pren values (\""+opt[i].getNome()+"\",\""+p.getCodiceFiscale()+"\","+c.getId()+");");
        }   }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Parte dedicata ai manager di competizione">
    protected Manager[] getManagers() throws SQLException
    {
        String query = "SELECT *\n" +
                "FROM " + TABLE_UTENTE + " JOIN " + TABLE_MAN_COMP + " on " + TABLE_UTENTE + ".mail=" + TABLE_MAN_COMP + ".mail\n" +
                "WHERE tipo=1\n" +
                "ORDER BY cognome\n";
        ResultSet rs = sendQuery(query);
        Manager [] man = new Manager[getResultSetLength(rs)];
        for (int i = 0; i < man.length; i++, rs.next())
        {
            man[i] = new Manager(
                    rs.getString("nome"),
                    rs.getString("cognome"),
                    rs.getString("mail"));
        }
        return man;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Parte dedicata ai controlli sui campi">
    /**
     * Controlla se l'indirizzo mail specificato è presente nel sistema
     * @param email indirizzo e-mail da verificare
     * @return true se esiste, viceversa false
     * @throws SQLException 
     */
    private boolean isMailExists(String email) throws SQLException
    {
        ResultSet rs = sendQuery("SELECT mail FROM " + TABLE_UTENTE);
        String[] mailList = new String[getResultSetLength(rs)];
        for (int i = 0; i < mailList.length; i++, rs.next())
        {
            mailList [i] = new String (rs.getString("mail"));
        }
        for (String s : mailList)
        {
            System.out.println("email 1= "+s+" email 2= "+email);
            if (s.compareTo(email) == 0)
                return true;
        }
        return false;
    }
    
    /**
     * Controlla se il codice fiscale specificato è presente nel sistema
     * @param codfisc codice fiscale da verificare
     * @return true se esiste, viceversa false
     * @throws SQLException 
     */
    private boolean _checkCodFiscExists(String codfisc) throws SQLException
    {
        ResultSet rs = sendQuery("SELECT codfisc FROM " + TABLE_PARTECIPANTE );
        String [] cfList = new String[getResultSetLength(rs)];
        for (int i = 0; i < cfList.length; i++, rs.next())
        {
            cfList[i]=new String (rs.getString("codfisc"));
        }
        for (String s : cfList)
        {
            if (s.compareTo(codfisc) == 0)
                return true;
        }
        return false;
    }
    
    /**
     * Controlla se sono stati inseriti dei campi vuoti
     * @param password password inserita
     * @param p partecipante compilato
     * @return true se almeno uno dei campi è vuoto
     */
    protected boolean isCampiVuoti (String password, Partecipante p)
    {
        return password.trim().length() == 0 || !p.isValid();
    }
    
    
    /**
     * Riporta il numero di giorni che mancano dalla data attuale a
     * quella indicata nel parametro
     * @param data la data cui si desidera verificare i giorni rimanenti prima dell'avvento
     * @return gg_ris, sarà 0 se le due date corrispondono o la data attuale ha
     * superato quella nel parametro; altrimenti avrà valore positivo
     */
    protected int getNGiorniMancanti(Date data)
    {
        int gg_ris=0;
        Date gc=Calendar.getInstance().getTime();
        if (data.after(gc))
        {
            gg_ris+=365*(data.getYear()-gc.getYear());
            gg_ris+=30*(data.getMonth()-gc.getMonth());
            gg_ris+=data.getDate()-gc.getDate();
        }
        return gg_ris;
    }
    /**
     * Riporta il numero di giorni che mancano dalla data 'data2'
     * alla data 'data1'
     * @param data1 la data cui si desidera verificare i giorni rimanenti prima dell'avvento
     * @param data2 la data da cui si desidera cominciare il conteggio dei giorni
     * @return gg_ris, sarà 0 se le due date corrispondono o la data attuale ha
     * superato quella nel parametro; altrimenti avrà valore positivo
     */
    protected int getNGiorniMancanti(Date data1,Date data2)
    {
        int gg_ris=0;
        if (data1.after(data2))
        {
            gg_ris+=365*(data1.getYear()-data2.getYear());
            gg_ris+=30*(data1.getMonth()-data2.getMonth());
            gg_ris+=data1.getDate()-data2.getDate();
        }
        return gg_ris;
    }
    // </editor-fold>
 
}
