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
    protected static final String DB_AGRO = "agroludos";
    protected static final String TABLE_PARTECIPANTE = "partecipante";
    protected static final String TABLE_PRENOTAZIONE = "prenotazione";
    protected static final String TABLE_OPTIONAL = "optional";
    protected static final String TABLE_OPTIONAL_COMPETIZIONE = "optional_competizione";
    protected static final String TABLE_OPTIONAL_PRENOTAZIONE = "optional_prenotazione";
    protected static final String TABLE_UTENTE = "utente";
    protected static final String TABLE_COMPETIZIONE = "competizione";
    protected static final String TABLE_MAN_COMP = "manager";
    private static final String TABLE_COMP_TYPE = "tipocompetizione";
    protected
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
    private Date getDbDate() throws SQLException
    {
        return sendQuery("SELECT CURRENT_DATE").getDate("CURRENT_DATE");
    }
    private int getResultSetLength(ResultSet rs) throws SQLException
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
    

    protected ResultSet sendQuery(String query) throws SQLException
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

    // <editor-fold defaultstate="collapsed" desc="Parte dedicata alle competizioni">
    /**
     * Ottiene una lista minimale delle competizioni a parte da un filtro
     * @param filter è un numero che, con un OR tra bit, crea un filtro:
     * 1 = competizioni in corso
     * 2 = competizioni già passate
     * 4 = competizioni annullate
     * @return
     * @throws SQLException 
     */
    protected Competizione[] getCompetizioniMinimal(int filter) throws SQLException
    {
        Condition condition;
        
        if (filter < 4)
        {
            if (filter == 0)
                return new Competizione[0];
            if ((filter & 3) == 1)
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.GreaterEqual).toString(),
                        new Condition("annullata", "false", Request.Operator.Equal).toString(),
                        Request.Operator.And);
            else if ((filter & 3) == 2)
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.LessEqual).toString(),
                        new Condition("annullata", "false", Request.Operator.Equal).toString(),
                        Request.Operator.And);
            else
                condition = new Condition("annullata", "false", Request.Operator.Equal);
        }
        else
        {
            filter -= 4;
            if (filter == 0)
                return new Competizione[0];
            if ((filter & 3) == 1)
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.GreaterEqual).toString(),
                        new Condition("annullata", "true", Request.Operator.Equal).toString(),
                        Request.Operator.And);
            else if ((filter & 3) == 2)
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.LessEqual).toString(),
                        new Condition("annullata", "true", Request.Operator.Equal).toString(),
                        Request.Operator.And);
            else
                condition = new Condition("annullata", "true", Request.Operator.Equal);
        }
            
        Request q = new Request(
                new String[]
                {
                    "idCompetizione",
                    "tipo",
                    "data",
                },
                TABLE_COMPETIZIONE,
                condition
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
     * riporta la lista di TUTTE le competizioni presenti nel sistema
     * @return Lista delle competizioni se c'è almeno una competizione nel sistema, altrimenti lancia l'eccezione
     * @throws SQLException 
     */
    protected Competizione[] _getCompetizioni() throws SQLException
    {
        Manager man=null;
        TipoCompetizione tipo=null;
        Request q= new Request
                (new String [] {"idCompetizione","manager","tipo"},
                 TABLE_COMPETIZIONE
                );
        ResultSet rs=sendQuery(q.toString());
        int NRis=getResultSetLength(rs);
        if (NRis!=0)
        {
            Competizione [] comp=new Competizione[NRis];
            int [] id_c=new int [NRis];
            String [] mail_mc=new String [NRis];
            String [] tipo_comp=new String [NRis];
            if (!rs.isFirst())
                rs.first();
            for (int i=0;i<NRis;i++,rs.next())
            {
                id_c[i]=rs.getInt("idCompetizione");
                mail_mc[i]=rs.getString("manager");
                tipo_comp[i]=rs.getString("tipo");
            }    
            for (int i=0;i<id_c.length;i++)
            {
                Optional [] opt=AgroController.this.getOptional(id_c[i]);
                Manager M[]=getManagers();
                for (Manager m:M)
                    if(m.getMail().compareTo(mail_mc[i])==0)
                    {
                        man=m;
                        break;
                    }    
                TipoCompetizione T[]=getCompetizioneTipi();
                for (TipoCompetizione t:T)
                    if (t.getNome().compareTo(tipo_comp[i])==0)
                    {
                        tipo=t;
                        break;
                    }
                int nPart=getNPartecipanti(id_c[i]);
                Condition c1=new Condition ("idCompetizione",String.valueOf(id_c[i]),Request.Operator.Equal);
                Condition c2=new Condition ("manager","\""+mail_mc[i]+"\"",Request.Operator.Equal);
                Condition c3=new Condition ("tipo","\""+tipo_comp[i]+"\"",Request.Operator.Equal);
                Condition c4=new Condition (c1.toString(),c2.toString(),Request.Operator.And);
                Condition c5=new Condition (c4.toString(),c3.toString(),Request.Operator.And);
                q=new Request (
                      null,
                      TABLE_COMPETIZIONE,
                      c5);
                rs=sendQuery(q.toString());
                rs.next();
                comp[i]=new Competizione (rs.getInt("idCompetizione"),rs.getFloat("prezzo"),rs.getInt("partMin"),rs.getInt("partMax"),nPart,tipo,man,rs.getDate("data"),opt);
            }
            return comp;
        }
            
        else
            throw new SQLException();
    }  
    
    
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
                    TABLE_OPTIONAL_COMPETIZIONE + ".prezzo",
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
        Optional [] opt=new Optional[getResultSetLength(rs)];
            for (int i=0;i<opt.length;i++,rs.next())
            {
                opt[i] = new Optional(
                    rs.getString("nome"),
                    rs.getString("descrizione"),
                    rs.getFloat(TABLE_OPTIONAL_COMPETIZIONE + ".prezzo")
                );
            }
        return opt;
    }
    
    /**
     * Ottiene una lista di id delle competizioni a cui un partecipante si è iscritto
     * @param mail email del partecipante 
     * @return lista di id di competizioni
     * @throws SQLException 
     */
    protected int[] getPartecipanteCompetizioni(String mail) throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    "competizione",
                },
                TABLE_PRENOTAZIONE,
                new Condition("partecipante", "\"" + mail + "\"", Request.Operator.Equal)
        );
        ResultSet rs = sendQuery(q.toString());
        int[] id = new int[getResultSetLength(rs)];
        for (int i = 0; i < id.length; i++, rs.next())
        {
            id[i] = rs.getInt("competizione");
        }
        return id;
    }
    
    /**
     * Verifica se la competizione indicata come parametro è stata già prenotata dal partecipante
     * @param mail email del partecipante 
     * @param idCompetizione id competizione su cui si vuole controllare se è stata fatta una prenotazione
     * @return true se il partecipante si è già prenotato a quella competizione, altrimenti false
     * @throws SQLException 
     */
    protected boolean isPrenotato(String mail, int idCompetizione) throws SQLException
    {
        Request q = new Request(
                new String[]
                {
                    "competizione",
                },
                TABLE_PRENOTAZIONE,
                new Condition(
                    new Condition("partecipante", "\"" + mail + "\"", Request.Operator.Equal).toString(),
                    new Condition("competizione", String.valueOf(idCompetizione), Request.Operator.Equal).toString(),
                        Request.Operator.And)
        );
        ResultSet rs = sendQuery(q.toString());
        return rs.next();
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
                                    TABLE_OPTIONAL_COMPETIZIONE + ".optional",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(TABLE_OPTIONAL_COMPETIZIONE + ".competizione", Integer.toString(competizioneId), Request.Operator.Equal)
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
    
    /**
     * Indica se il partecipante, per quella particolare competizione,
     * ha selezionato o meno l'optional riportato nel parametro
     * @param p partecipante    
     * @param o optional    
     * @param competizione
     * @return true se il partecipante ha selezionato quell'optiona,
     * altrimenti false
     * @throws SQLException 
     */
    protected boolean _isOptionalPrenotato (Partecipante p, Optional o, Competizione c) throws SQLException
    {
        Request q = new Request (
                    new String [] {"selezionato"},
                    TABLE_OPTIONAL_PRENOTAZIONE,
                    new Condition (
                    new Condition ("prenotazione",String.valueOf(getIndexPrenotazione(p,c)),Request.Operator.Equal).toString(),
                    new Condition ("optional",String.valueOf(getIndexOptionalCompetizione(o,c)),Request.Operator.Equal).toString(),
                    Request.Operator.And));
        ResultSet rs=sendQuery(q.toString());
        if (rs.next())
            return (rs.getInt(1)==1);
        else
            return false;
    }

    protected int getIndexOptionalCompetizione(Optional opt,Competizione comp) throws SQLException
    {
        Request q= new Request (
                    new String [] {"idOptionalCompetizione"},
                    TABLE_OPTIONAL_COMPETIZIONE,
                    new Condition (
                    new Condition ("optional","\""+opt.getNome()+"\"",Request.Operator.Equal).toString(),
                    new Condition ("competizione",String.valueOf(comp.getId()),Request.Operator.Equal).toString(),
                    Request.Operator.And));
        ResultSet rs = sendQuery(q.toString());
        if (rs.next())
            return rs.getInt(1);
        else
            throw new SQLException();
    } 
   
    protected void setOptional(Optional optional) throws SQLException
    {
        sendUpdate("UPDATE " + TABLE_OPTIONAL +
                " SET descrizione='" + optional.getDescrizione() + "'" +
                ", prezzo=" + optional.getPrezzo() +
                " WHERE nome='" + optional.getNome() + "'");
    }
    
    protected void _setOptionalPrenotazione (Optional o, Partecipante p, Competizione c, boolean scelto) throws SQLException
    {
        String selezione=null;
        if ((_isOptionalPrenotato(p,o,c))&&(!scelto))
            selezione=String.valueOf(0);
        else if ((!_isOptionalPrenotato(p,o,c))&&(scelto))
            selezione=String.valueOf(1);
        if (selezione!=null)
        {
            Update q=new Update (
            TABLE_OPTIONAL_PRENOTAZIONE,
            "selezionato",selezione,
            new Condition(
            new Condition ("optional",String.valueOf(getIndexOptionalCompetizione(o,c)),Request.Operator.Equal).toString(),
            new Condition ("prenotazione",String.valueOf(getIndexPrenotazione(p,c)),Request.Operator.Equal).toString(),
            Request.Operator.And));
            sendUpdate(q.toString());
        }
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Parte dedicata ai partecipanti"> 
    protected void _addPartec(String password,Partecipante p) throws SQLException,DefEmailException,DefCodFiscException, CampiVuotiException
    {
        
        if (isMailExists(p.getMail()))
            throw new DefEmailException();
        if (_checkCodFiscExists(p.getCodiceFiscale()))
            throw new DefCodFiscException();
        if (isCampiVuoti(password,p))
            throw new CampiVuotiException();
        Insert I;
        SimpleDateFormat d = new SimpleDateFormat("yyyy-MM-dd");
        String date=d.format(p.getDataNascita());
        String dateSrc=d.format(p.getDataSrc());
        I= new Insert (
                TABLE_UTENTE,
                new String [] {
                "\""+p.getMail()+"\"",
                "PASSWORD(\""+password+"\")",
                "0"});
        sendUpdate(I.toString());
        I= new Insert (
                TABLE_PARTECIPANTE,
                new String [] {
                "\""+p.getMail()+"\"",
                "\""+p.getNome()+"\"",
                "\""+p.getCognome()+"\"",
                "\""+p.getIndirizzo()+"\"",
                "'"+date+"'",
                "\""+p.getCodiceFiscale()+"\"",
                "\""+p.getSesso()+"\"",
                "\""+p.getTesseraSan()+"\"",
                "'"+dateSrc+"'",
                "\""+p.getSrc()+"\""});
        sendUpdate(I.toString());
    }
    
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
                    TABLE_PARTECIPANTE + ".email",
                    "nome",
                    "cognome"
                },
                TABLE_UTENTE,
                new Join[]
                {
                    new Join(TABLE_PARTECIPANTE,
                            new Condition(
                                    TABLE_UTENTE + ".mail",
                                    TABLE_PARTECIPANTE + ".email",
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
                rs.getString("email"),
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
                                    TABLE_PARTECIPANTE + ".email",
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
                    rs.getString("codicefiscale"),
                    rs.getString("indirizzo"),
                    rs.getDate("datanascita"),
                    (char)rs.getString("sesso").charAt(0),
                    rs.getString("tesserasan"),
                    rs.getDate("datasrc"),
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
                                    TABLE_COMPETIZIONE + ".idCompetizione",
                                    TABLE_PRENOTAZIONE + ".competizione",
                                    Request.Operator.Equal
                            ))
                },
                new Condition(TABLE_COMPETIZIONE + ".idCompetizione", Integer.toString(competizioneId), Request.Operator.Equal)
        );
        ResultSet rs=sendQuery(q.toString());
        if (rs.next())
            return rs.getInt(1);
        else
            throw new SQLException();
    }
    
    protected int getIndexPrenotazione (Partecipante p, Competizione c) throws SQLException
    {
        Request q= new Request (
                    new String [] {"idPrenotazione"},
                    TABLE_PRENOTAZIONE,
                    new Condition (
                    new Condition ("partecipante","\""+p.getMail()+"\"",Request.Operator.Equal).toString(),
                    new Condition ("competizione",String.valueOf(c.getId()),Request.Operator.Equal).toString(),
                    Request.Operator.And));
        ResultSet rs = sendQuery(q.toString());
        if (rs.next())
            return rs.getInt(1);
        else
            throw new SQLException();
    }
    
    protected void _addIscrizioneCompetizione(Partecipante p, Competizione c,Optional [] opt) throws SQLException, SrcScadutaException
    {
        if(getNGiorniMancanti(c.getDataComp(),p.getDataSrc())>=365)
            throw new SrcScadutaException();
        //System.out.println("insert into prenotazione values (\""+p.getCodiceFiscale()+"\","+c.getId()+");");
        Insert I=new Insert (
                TABLE_PRENOTAZIONE,
                new String [] {"0","\""+p.getMail()+"\"","\""+c.getId()+"\""});
        sendUpdate(I.toString());
        if (opt!=null)
        {
            Optional [] opt_comp=getOptional(c.getId());
            for (int i=0;i<opt_comp.length;i++)
            {    
                int trovato=0;
                for (int j=0;j<opt.length;j++)
                { 
                    if (opt[j].getNome().compareTo(opt_comp[i].getNome())==0)
                    {
                        trovato=1;
                        break;
                    }
                }
                I= new Insert (
                        TABLE_OPTIONAL_PRENOTAZIONE,
                        new String [] 
                        {
                            String.valueOf(getIndexOptionalCompetizione(opt_comp[i],c)),
                            String.valueOf(getIndexPrenotazione(p,c)),
                            String.valueOf(trovato)
                        } );
                sendUpdate(I.toString());
            } 
        }   
    }
    
    protected void dropPrenotazione (Partecipante p, Competizione c) throws SQLException
    {
        if (isPrenotato(p.getMail(),c.getId()))
        {
            Delete q;
            int id_pren=getIndexPrenotazione (p,c);
            Optional [] opt =getCompetizioneOptional(c.getId());
            for (int i=0;i<opt.length;i++)
            {
                int id_opt_comp=getIndexOptionalCompetizione(opt[i],c);
                q=new Delete
                    (TABLE_OPTIONAL_PRENOTAZIONE,
                     new Condition 
                    (new Condition ("optional",String.valueOf(id_opt_comp),Request.Operator.Equal).toString(),
                     new Condition ("prenotazione",String.valueOf(id_pren),Request.Operator.Equal).toString(),
                    Request.Operator.And));
                sendUpdate(q.toString());
            }
            q=new Delete
                (TABLE_PRENOTAZIONE,
                new Condition("idPrenotazione",String.valueOf(id_pren),Request.Operator.Equal));
            sendUpdate(q.toString());
        }
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
        Request q = new Request (
                    new String [] {"mail"},
                    TABLE_UTENTE);
        ResultSet rs=sendQuery(q.toString());
        String[] mailList = new String[getResultSetLength(rs)];
        for (int i = 0; i < mailList.length; i++, rs.next())
            mailList [i] = new String (rs.getString("mail"));
        for (String s : mailList)
            if (s.compareTo(email) == 0)
                return true;
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
        Request q = new Request (
                    new String [] {"codicefiscale"},
                    TABLE_PARTECIPANTE);
        ResultSet rs=sendQuery(q.toString());
        String [] cfList = new String[getResultSetLength(rs)];
        for (int i = 0; i < cfList.length; i++, rs.next())
            cfList[i]=new String (rs.getString("codicefiscale"));
        for (String s : cfList)
            if (s.compareTo(codfisc) == 0)
                return true;
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
