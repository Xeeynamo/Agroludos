package agroludos.server;

import agroludos.components.Competizione;
import agroludos.components.Manager;
import agroludos.components.Optional;
import agroludos.components.Partecipante;
import agroludos.components.TipoCompetizione;
import agroludos.exception.CampiVuotiException;
import agroludos.exception.CompPienaException;
import agroludos.exception.CompetizioneEsistenteException;
import agroludos.exception.DatePriorException;
import agroludos.exception.DefCodFiscException;
import agroludos.exception.DefEmailException;
import agroludos.exception.MinMaxException;
import agroludos.exception.SrcScadutaException;
import agroludos.exception.TipoCompetizioneInvalidException;
import agroludos.exception.WrongLoginException;
import agroludos.server.db.Condition;
import agroludos.server.db.Delete;
import agroludos.server.db.Insert;
import agroludos.server.db.Join;
import agroludos.server.db.Request;
import agroludos.server.db.Update;
import agroludos.server.lang.LangManager;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.mail.MessagingException;
import javax.swing.JFrame;

/**
 * Front controller del progetto.
 * Separa l'interfaccia con il database, gestendo i dialoghi tra le due parti.
 * @author Luciano
 */
public final class AgroController
{
    public static final String DB_AGRO = "agroludos";
    public static final String TABLE_PARTECIPANTE = "partecipante";
    public static final String TABLE_PRENOTAZIONE = "prenotazione";
    public static final String TABLE_OPTIONAL = "optional";
    public static final String TABLE_OPTIONAL_COMPETIZIONE = "optional_competizione";
    public static final String TABLE_OPTIONAL_PRENOTAZIONE = "optional_prenotazione";
    public static final String TABLE_UTENTE = "utente";
    public static final String TABLE_COMPETIZIONE = "competizione";
    public static final String TABLE_MAN_COMP = "manager";
    private static final String TABLE_COMP_TYPE = "tipocompetizione";
    
    public enum UserType
    {
        Anonimo, Partecipante, ManagerCompetizione, ManagerSistema
    }
    
    private Statement statement;
    private String mail;
    private UserType type;
    static private JFrame currentFrame;
    LangManager lang;
    
    /**
     * Stabilisce una connessione col database.
     * @param database host che ospita il database MySQL
     * @param username nome utente MySQL
     * @param password password MySQL
     * @throws ClassNotFoundException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws SQLException 
     */
    public AgroController(String database, String username, String password) throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException
    {
        // Carico il driver JDBC
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        // mi connetto al database
        Connection db = DriverManager.getConnection("jdbc:mysql://" + database + "/" + "agroludos?" +
                "user="+ username + "&password="+ password);
        
        // creo lo statement per l'invio della query
        statement = (Statement)db.createStatement();
        mail = "";
        type = UserType.Anonimo;
        LoadLanguage();
    }
    
    /**
     * Inizializza la classe utente che può interagire col sistema.
     * @param statement parte che gestisce lo scambio di info tra la classe ed il DB MySQL
     * @param mail chi ha effettuato l'accesso
     */
    public AgroController(Statement statement, String mail, UserType type)
    {
        this.statement = statement;
        this.mail = mail;
        this.type = type;
        LoadLanguage();
    }
    
    void LoadLanguage()
    {
        lang = new LangManager();
    }
    
    void setCurrentFrame(JFrame frame)
    {
        if (currentFrame != null)
        {
            currentFrame.setVisible(false);
            currentFrame.dispose();
        }
        currentFrame = frame;
        currentFrame.setVisible(true);
        lang.ApplyLanguage(currentFrame);
    }
    
    /**
     * Istanzia un utente
     * @param mail indirizzo mail dell'utente
     * @param password la sua password
     * @return utente sotto forma di oggetto:
     * Le possibili classe restituite sono Utente, ManagerCompetizione e ManagerSistema
     * @throws WrongLoginException quando mail o password specificati sono errati
     * @throws SQLException 
     */
    public AgroController Login(String mail, String password)
            throws SQLException, WrongLoginException
    {
        int idType = getUserType(mail, password);
        UserType usertype;
        switch (idType) 
        {
             case 0:
                 usertype = UserType.Partecipante;
                 break;
             case 1:
                 usertype = UserType.ManagerCompetizione;
                 break;
             case 2:
                 usertype = UserType.ManagerSistema;
                 break;
             default:
                 throw new WrongLoginException();
         }
        return new AgroController(getStatement(), mail, usertype);
    }
    public String getMail()
    {
        return mail;
    }
    public UserType getType()
    {
        return type;
    }
    
    /**
     * Ottiene l'oggetto utile a processare l query
     * @return oggetto statement
     */
    private Statement getStatement()
    {
        return statement;
    }
    
    /**
     * Ottiene la data del server MySQL
     * @return oggetto Date se chiamata con successo, altrimenti torna null
     * @throws SQLException 
     */
    private Date getDbDate() throws SQLException
    {
        ResultSet rs = sendQuery("SELECT CURRENT_DATE");
        if (rs.next())
            return rs.getDate("CURRENT_DATE");
        return null;
    }
    
    /**
     * Dato un ResultSet, controlla quanti risultati sono presenti
     * @param rs ResulSet da controllare
     * @return numero di risultati
     * @throws SQLException 
     */
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
    
    /**
     * Manda una query di interrogazione al database MySQL
     * @param query da eseguire
     * @return ritorna il set di risultati
     * @throws SQLException 
     */
    private ResultSet sendQuery(String query) throws SQLException
    {
        getStatement().executeQuery(query);
        return getStatement().getResultSet();
    }
    
    /**
     * Manda una query di modifica al database MySQL
     * @param query da eseguire
     * @throws SQLException 
     */
    private void sendUpdate(String query) throws SQLException
    {
        getStatement().executeUpdate(query);
    }
    
    /**
     * Ottiene il tipo di utente, utile per il login
     * @param mail dell'utente da verificare
     * @param password dell'utente da verificare
     * @return tipo dell'utente:
     * 0 = Partecipante
     * 1 = Manager di competizione
     * 2 = Manager di sistema
     * @throws SQLException 
     */
    private int getUserType(String mail, String password) throws SQLException
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
    
    public String getSysMail () throws SQLException
    {
        Request q= new Request(
                    new String []{"mail"},
                   TABLE_UTENTE,
                    new Condition ("tipo",String.valueOf(2),Request.Operator.Equal));
        ResultSet rs=sendQuery(q.toString());
        if (rs.next())
            return rs.getString(1);
        else
            throw new SQLException();
    }        

    // <editor-fold defaultstate="collapsed" desc="Parte dedicata alle competizioni">
    
    
    public Competizione [] getCompetizioniDisponibili () throws SQLException
    {
       //Competizione [] c= _getCompetizioni();
       Competizione [] c=getCompetizioniMinimal(1);
       int NComp=0;
       for (int i=0;i<c.length;i++)
       {
           if((getNGiorniMancanti((java.sql.Date)c[i].getDataComp())<=1)||(isPrenotato(getMail(), c[i].getId())))
               c[i]=null;
           else
                NComp++;
       }
       Competizione [] c1=new Competizione[NComp];
       for (int i=0,j=0;i<c.length;i++)
       {
           if(c[i]!=null)
           {
               c1[j]=getCompetizione(c[i].getId());
               //c1[j]=c[i];
               j++;
           }
       }
       return c1;        
    }
    
    public Competizione [] getCompetizioniPrenotate () throws SQLException
    {
       Competizione [] c = _getCompetizioni();
       int NComp=0;
       for (int i=0;i<c.length;i++)
       {
           if((getNGiorniMancanti((java.sql.Date)c[i].getDataComp())==0)||(!isPrenotato(getMail(), c[i].getId())))
               c[i]=null;
           else
                NComp++;
       }
       Competizione [] c1=new Competizione[NComp];
       for (int i=0,j=0;i<c.length;i++)
       {
           if(c[i]!=null)
           {
               c1[j]=c[i];
               j++;
           }
       }
       return c1;        
    }
    /**
     * Crea una competizione
     * @param c competizione contenente tutti i dati necessari per crearne una
     * @throws SQLException 
     * lanciato quando vi è un problema col database
     * @throws agroludos.db.exception.TipoCompetizioneInvalidException 
     * lanciato quando il tipo di competizione impostata non è presente nel sistema
     * @throws agroludos.db.exception.MinMaxException 
     * lanciato quando il minimo numero di partecipanti è maggiore al numero massimo
     * @throws agroludos.db.exception.DatePriorException 
     * lanciato quando la data della competizione è precedente alla data impostata nel DB
     * @throws agroludos.db.exception.CompetizioneEsistenteException
     * lanciato quando esiste già una competizione per quel manager di competizione nel
     * giorno specificato
     */
    public void creaCompetizione(Competizione c) throws SQLException,
            TipoCompetizioneInvalidException,
            MinMaxException,
            DatePriorException,
            CompetizioneEsistenteException
    {
        if (checkTipoCompetizione(c.getTipoCompetizione()) == false)
            throw new TipoCompetizioneInvalidException();
        if (c.getNMin() > c.getNMax())
            throw new MinMaxException();
        if (c.getDataComp().before(getDbDate()))
            throw new DatePriorException();
        if (getCompetizione(c.getManager().getMail(), c.getDataCompString()) != null)
            throw new CompetizioneEsistenteException();
        
        Insert insert = new Insert(TABLE_COMPETIZIONE,
                new String[]
                {
                    "DEFAULT",
                    "\"" + c.getTipoCompetizione().getNome() + "\"",
                    "\"" + c.getManager().getMail() + "\"",
                    "'" + c.getDataCompString() + "'",
                    String.valueOf(c.getNMin()),
                    String.valueOf(c.getNMax()),
                    String.valueOf(c.getPrezzo()),
                    "0",
                    
                }
        );
        String s = insert.toString();
        sendUpdate(insert.toString());
        Optional[] opt = c.getOptional();
        c = getCompetizione(c.getManager().getMail(), c.getDataCompString());
        if (c == null)
            throw new SQLException();
        for (Optional o : opt)
        {
            insert = new Insert(TABLE_OPTIONAL_COMPETIZIONE,
                    new String[]
                    {
                        "DEFAULT",
                        "\"" + o.getNome() + "\"",
                        String.valueOf(c.getId()),
                        String.valueOf(o.getPrezzo())
                    }
            );
            sendUpdate(insert.toString());
        }
    }
    
    public int getIdFromCompetizione(Competizione c)
    {
        return c.getId();
    }
    /**
     * Ottiene una lista minimale delle competizioni a parte da un filtro
     * @param filter è un numero che, con un OR tra bit, crea un filtro:
     * 1 = competizioni in corso
     * 2 = competizioni già passate
     * 4 = competizioni annullate
     * @return
     * @throws SQLException 
     */
    public Competizione[] getCompetizioniMinimal(int filter) throws SQLException
    {
        Condition condition;
        
        switch (filter % 8)
        {
            case 0:
                return new Competizione[0];
            case 1:
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.GreaterEqual).toString(),
                        new Condition("annullata", "false", Request.Operator.Equal).toString(),
                        Request.Operator.And);
                break;
            case 2:
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.LessEqual).toString(),
                        new Condition("annullata", "false", Request.Operator.Equal).toString(),
                        Request.Operator.And);
                break;
            case 3:
                condition = new Condition("annullata", "false", Request.Operator.Equal);
                break;
            case 4:
                condition = new Condition("annullata", "true", Request.Operator.Equal);
                break;
            case 5:
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.GreaterEqual).toString(),
                        new Condition("annullata", "true", Request.Operator.Equal).toString(),
                        Request.Operator.Or);
                break;
            case 6:
                condition = new Condition(
                        new Condition("data", "'" + getDbDate().toString() + "'", Request.Operator.LessEqual).toString(),
                        new Condition("annullata", "true", Request.Operator.Equal).toString(),
                        Request.Operator.Or);
                break;
            case 7:
                condition = new Condition("true", "true", Request.Operator.Equal);
                break;
            default:
                return new Competizione[0];
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
    public Competizione[] _getCompetizioni() throws SQLException
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
    
    /**
     * Ottiene una lista dei vari tipi di competizione presenti
     * @return lista di tipi di competizione
     * @throws SQLException 
     */
    public TipoCompetizione[] getCompetizioneTipi() throws SQLException
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
     * Ottiene una lista di competizioni gestite da uno specifico manager.
     * Le competizioni annullate non saranno incluse nella lista.
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
                new Condition(
                    new Condition(TABLE_MAN_COMP + ".mail", "\"" + manager + "\"", Request.Operator.Equal).toString(),
                    new Condition("annullata", "0", Request.Operator.Equal).toString(),
                    Request.Operator.And
                )
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
     * Ottiene la prima competizione trovata partendo dal manager e dalla data.
     * Le competizioni annullate non saranno incluse nella lista.
     * @param manager mail del manager di competizione
     * @param date data della competizione
     * @return la prima competizione trovata che soddisfa i requisiti chiesti
     * @throws SQLException 
     */
    public Competizione getCompetizione(String manager, String date) throws SQLException
    {
        Request q = new Request(
                new String[] { "idCompetizione", },
                TABLE_COMPETIZIONE,
                new Condition(
                    new Condition(
                        new Condition("manager", "\"" + manager + "\"", Request.Operator.Equal).toString(),
                        new Condition("data", "'" + date + "'", Request.Operator.Equal).toString(),
                        Request.Operator.And
                    ).toString(),
                    new Condition("annullata","0",Request.Operator.Equal).toString(),
                    Request.Operator.And
                )
        );
        ResultSet rs = sendQuery(q.toString());
        if (rs.next())
            return getCompetizione(rs.getInt("idCompetizione"));
        return null;
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
                },
                new Condition("idCompetizione", String.valueOf(idCompetizione), Request.Operator.Equal)
        );
        int nPart = getNPartecipanti(idCompetizione);
        Optional[] opt = getOptional(idCompetizione);
        ResultSet rs = sendQuery(q + "ORDER BY data");
        if (rs.next())
        {
            return new Competizione(
                rs.getInt("idCompetizione"),
                rs.getFloat("prezzo"),
                rs.getInt("partMin"),
                rs.getInt("partMax"),
                nPart,
                new TipoCompetizione(rs.getString("tipo"), ""),
                new Manager(
                        rs.getString(TABLE_MAN_COMP + ".nome"),
                        rs.getString(TABLE_MAN_COMP + ".cognome"),
                        rs.getString("manager")
                ),
                rs.getDate("data"),
                opt);
        }
        return null;
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
    public Integer[] getPartecipanteCompetizioni(String mail) throws SQLException
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
        Integer[] id = new Integer[getResultSetLength(rs)];
        for (int i = 0; i < id.length; i++, rs.next())
        {
            id[i] = rs.getInt("competizione");
        }
        return id;
    }
    
    /**
     * Annulla la competizione specificata, flaggandola come annullata
     * @param idCompetizione id della competizione da eliminare
     * @throws SQLException 
     */
    public void annullaCompetizione(int idCompetizione) throws SQLException
    {
        Update q = new Update
        (
            TABLE_COMPETIZIONE,
            "annullata",
            "1",
            new Condition ("idCompetizione", String.valueOf(idCompetizione), Request.Operator.Equal)
        );
        sendUpdate(q.toString());
    }
     
    /**
     * Imposta, per la competizione specificata, il numero massimo di partecipanti.
     * @param idCompetizione id della competizione da modificare
     * @param nmax nuovo numero massimo di utenti da impostare
     * @throws SQLException 
     */
    public void setNPartMax (int idCompetizione,int nmax) throws SQLException
    {
        int nPart=getNPartecipanti(idCompetizione);
        if (nPart>nmax)
        {
            for (int i=nPart-nmax;i>0;i--)
            {
                Request q1=new Request 
                        (new String [] {"max(idPrenotazione)"},
                         TABLE_PRENOTAZIONE);
                Request q2=new Request
                        (new String [] {"partecipante"},
                         TABLE_PRENOTAZIONE,
                         new Condition ("idPrenotazione","("+q1.toString()+")",Request.Operator.Equal));
                ResultSet rs=sendQuery(q2.toString());
                if (rs.next())
                    annullaPrenotazione(getPartecipante(rs.getString(1)),getCompetizione(idCompetizione));
            }    
        }
        Update q=new Update 
                (TABLE_COMPETIZIONE,
                 "partMax",String.valueOf(nmax),
                 new Condition("idCompetizione",String.valueOf(idCompetizione),Request.Operator.Equal));
        sendUpdate(q.toString());
    }
    
    /**
     * Imposta, per la competizione specificata, il numero minimo di partecipanti.
     * @param idCompetizione id della competizione da modificare
     * @param nmax nuovo numero minimo di utenti da impostare
     * @throws SQLException 
     */
    public void setNPartMin (int idCompetizione,int nmin) throws SQLException
    {
        Update q=new Update 
                (TABLE_COMPETIZIONE,
                 "partMin",String.valueOf(nmin),
                 new Condition("idCompetizione",String.valueOf(idCompetizione),Request.Operator.Equal));
        sendUpdate(q.toString());   
    }
    
    /**
     * Imposta, per la competizione specificata, il suo prezzo per i partecipanti.
     * @param idCompetizione id della competizione da modificare
     * @param nmax nuovo prezzo della competizione da impostare
     * @throws SQLException 
     */
    public void setPrezzoComp (int idCompetizione,float prezzo) throws SQLException
    {
        Update q=new Update 
                (TABLE_COMPETIZIONE,
                 "prezzo",String.valueOf(prezzo),
                 new Condition("idCompetizione",String.valueOf(idCompetizione),Request.Operator.Equal));
        sendUpdate(q.toString());   
    }
    /**
     * Verifica se la competizione indicata come parametro è stata già prenotata dal partecipante
     * @param mail email del partecipante 
     * @param idCompetizione id competizione su cui si vuole controllare se è stata fatta una prenotazione
     * @return true se il partecipante si è già prenotato a quella competizione, altrimenti false
     * @throws SQLException 
     */
    public boolean isPrenotato(String mail, int idCompetizione) throws SQLException
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
    /**
     * Ottiene una lista di tutti gli optional presenti nel database
     * @return lista degli optional
     * @throws SQLException 
     */
    public Optional[] getOptional() throws SQLException
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
     * Ottiene le informazioni riguardo un singolo optional a partire dal suo nome
     * @param nome dell'optional
     * @return optional con tutte le sue proprietà
     * @throws SQLException 
     */
    public Optional getOptional (String nome) throws SQLException
    {
        Request q=new Request
                (null,TABLE_OPTIONAL,
                 new Condition ("nome","\""+nome+"\"",Request.Operator.Equal));
        ResultSet rs=sendQuery(q.toString());
        Optional opt=null;
        if (rs.next())
            opt= new Optional(rs.getString("nome"), rs.getString("descrizione"), rs.getFloat("prezzo"));
        return opt;
    }
    
    /**
     * Ottiene la lista degli optional di una determinata competizione
     * @param competizioneId id della competizione scelta
     * @return lista degli optional selezionabili
     * @throws SQLException 
     */
    public Optional[] getOptional(int competizioneId) throws  SQLException 
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
     * @param c competizione
     * @return true se il partecipante ha selezionato quell'optiona,
     * altrimenti false
     * @throws SQLException 
     */
    public boolean isOptionalPrenotato (Partecipante p, Optional o, Competizione c) throws SQLException
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

    public int getIndexOptionalCompetizione(Optional opt,Competizione comp) throws SQLException
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
   
    public void setOptional(Optional optional) throws SQLException
    {
        sendUpdate("UPDATE " + TABLE_OPTIONAL +
                " SET descrizione='" + optional.getDescrizione() + "'" +
                ", prezzo=" + optional.getPrezzo() +
                " WHERE nome='" + optional.getNome() + "'");
    }
    
    public void setOptionalPrenotazione (Optional o, Partecipante p, Competizione c, boolean scelto) throws SQLException
    {
        String selezione=null;
        if ((isOptionalPrenotato(p,o,c))&&(!scelto))
            selezione=String.valueOf(0);
        else if ((!isOptionalPrenotato(p,o,c))&&(scelto))
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
    
    public boolean setOptionalCompetizione (Competizione c,Optional o) throws SQLException
    {
        Partecipante [] p= null;
        boolean trovato=false;
        Optional [] opt_c=getOptional(c.getId());
        for (int i=0;i<opt_c.length;i++)
        {
            if (trovato=o.getNome().compareTo(opt_c[i].getNome())==0)
                break;
        }   
        if (!trovato)
        {
            Insert q= new Insert (
                    TABLE_OPTIONAL_COMPETIZIONE,
                    new String []
                    {
                    "DEFAULT","\""+o.getNome()+"\"",String.valueOf(c.getId()),String.valueOf(o.getPrezzo())
                    });
            System.out.println(q.toString()+"\n");
            sendUpdate(q.toString());
            p=getPartecipanti(c.getId());
            for (int i=0;i<p.length;i++)
                setOptionalPrenotazione(o,p[i],c,false);
            return true;
        }
        else 
            return false;
    }
    
    public boolean dropOptionalCompetizione (Competizione c,Optional o) throws SQLException
    {
        Partecipante []p=null;
        boolean trovato=false;
        Optional [] opt_c=getOptional(c.getId());
        for (int i=0;i<opt_c.length;i++)
        {
            if (trovato=o.getNome().compareTo(opt_c[i].getNome())==0)
                break;
        }
        if (trovato)
        {
            if (getNPartecipanti(c.getId())!=0)
            {
                p=getPartecipanti(c.getId());
                for (int i=0;i<p.length;i++)
                    dropOptionalPrenotazione(o,p[i],c);
            }
            Delete q=new Delete (
                    TABLE_OPTIONAL_COMPETIZIONE,
                    new Condition ("idOptionalCompetizione",String.valueOf(getIndexOptionalCompetizione(o,c)),Request.Operator.Equal));
            sendUpdate(q.toString());
            return true;
        }
        else
            return false;
    }
    
    public void dropOptionalPrenotazione (Optional o, Partecipante p, Competizione c) throws SQLException
    {
        Delete q=new Delete (
                TABLE_OPTIONAL_PRENOTAZIONE,
                new Condition (
                new Condition("optional",String.valueOf(getIndexOptionalCompetizione(o,c)),Request.Operator.Equal).toString(),
                new Condition ("prenotazione",String.valueOf(getIndexPrenotazione(p,c)),Request.Operator.Equal).toString(),
                Request.Operator.And));
        sendUpdate(q.toString());
    }
    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Parte dedicata ai partecipanti"> 
    public String getMailFromPartecipante (Partecipante p)
    {
        return p.getMail();
    }
    public void addPartecipante(String password, Partecipante p) throws SQLException,DefEmailException,DefCodFiscException, CampiVuotiException
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
    public Partecipante[] getPartecipantiMinimal() throws SQLException
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
    public Partecipante getPartecipante(String email) throws SQLException
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
        if (rs.next())
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
        else
            return null;
    }
    
    /**
     * Ottiene il numero di partecipanti di una determinata competizione
     * @param competizioneId id della competizione da verificare
     * @return numero di partecipanti
     * @throws SQLException 
     */
    public int getNPartecipanti (int competizioneId) throws SQLException
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
    
    /**
     * Ottiene i partecipanti di una determinata competizione
     * @param competizioneId id della competizione da verificare
     * @return numero di partecipanti
     * @throws SQLException 
     */
    public Partecipante[] getPartecipanti (int competizioneId) throws SQLException
    {
        // "select count(*) from competizione join prenotazione on competizione.id=prenotazione.comp where competizione.id="+id;
        Request q = new Request(
                new String[]
                {
                    "idCompetizione",
                    "email",
                    "nome",
                    "cognome"
                },
                TABLE_COMPETIZIONE,
                new Join[]
                {
                    new Join(TABLE_PRENOTAZIONE,
                            new Condition(
                                    TABLE_PRENOTAZIONE + ".competizione",
                                    TABLE_COMPETIZIONE + ".idCompetizione",
                                    Request.Operator.Equal
                            )),
                    new Join(TABLE_PARTECIPANTE,
                            new Condition(
                                    TABLE_PARTECIPANTE + ".email",
                                    TABLE_PRENOTAZIONE + ".partecipante",
                                    Request.Operator.Equal
                            ))
                },
                new Condition("idCompetizione", Integer.toString(competizioneId), Request.Operator.Equal)
        );
        ResultSet rs = sendQuery(q.toString() + "ORDER BY cognome");
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
    
    public int getIndexPrenotazione (Partecipante p, Competizione c) throws SQLException
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
    
    public void addIscrizioneCompetizione(Partecipante p, Competizione c,Optional [] opt) throws SQLException, SrcScadutaException, CompPienaException
    {
        if(c.getNPart()==c.getNMax())
            throw new CompPienaException();
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
    
    public void annullaPrenotazione (Partecipante p, Competizione c) throws SQLException
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
    public Manager[] getManagers() throws SQLException
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

    /**
     * Invia una mail da parte dell'utente corrente
     * @param receiver indirizzo mail del destinatario
     * @param subject oggetto della mail
     * @param text testo all'interno della mail
     * @throws MessagingException 
     */
    public void sendMail(String receiver, String subject, String text) throws MessagingException
    {
        new AgroMail(getMail(), receiver, subject, text).send();
    }
    
    // <editor-fold defaultstate="collapsed" desc="Parte dedicata ai controlli sui campi">
    
    /**
     * Controlla se il tipo di competizione specificata è già presente nel sistema
     */
    private boolean checkTipoCompetizione(TipoCompetizione tipo) throws SQLException
    {
        TipoCompetizione[] tipoCompetizioni = getCompetizioneTipi();
        for (TipoCompetizione t : tipoCompetizioni)
        {
            if (tipo.getNome().compareTo(t.getNome()) == 0)
                return true;
        }
        return false;
    }
    
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
    public boolean isCampiVuoti (String password, Partecipante p)
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
    public int getNGiorniMancanti(Date data)
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
    public int getNGiorniMancanti(Date data1,Date data2)
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
