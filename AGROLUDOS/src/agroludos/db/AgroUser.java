package agroludos.db;

import agroludos.db.components.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

public class AgroUser
{
    private static final String DB_AGRO = "agroludos";
    private static final String TABLE_PARTECIPANTE = "partecipante";
    private static final String TABLE_OPTIONAL = "optional";
    private static final String TABLE_UTENTE = "utente";
    private static final String TABLE_COMPETIZIONE = "competizione";
    private static final String TABLE_MAN_COMP = "mc";
    
    Statement statement;
    String mail;
    
    public AgroUser(Statement statement, String mail)
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

    protected void _addPartec(String password,Partecipante p) throws SQLException,DefEmailException,DefCodFiscException, CampiVuotiException
    {
        //MODIFICA by ROS (13/07/2014)
        if (!isMailExists(p.getMail()))
            throw new DefEmailException();
        if (!_checkCodFiscExists(p.getCodiceFiscale()))
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

    protected Partecipante _getPartecipante(String email) throws SQLException
    {
        String s1="SELECT * from "+ TABLE_UTENTE +" join "+ TABLE_PARTECIPANTE +" on "+ TABLE_UTENTE +".mail="+ TABLE_PARTECIPANTE +".mail where "+ TABLE_UTENTE +".mail="+email+" and "+ TABLE_UTENTE +" and "+ TABLE_UTENTE +".tipo=0";
        sendQuery(s1);
        ResultSet rs = getStatement().getResultSet();
        if (getResultSetLength(rs)!=1)
            throw new SQLException();
        else
            return new Partecipante(email,rs.getString("nome"),rs.getString("cognome"),rs.getString("codfisc"),rs.getString("indirizzo"),rs.getDate("datanascita"),(char)rs.getInt("sesso"),rs.getString("tes_san"),rs.getDate("data_src"),rs.getString("src"));  
    }
   
    protected Competizione[] _getCompetizioniDisponibili() throws SQLException
    {
        String s1;
        ResultSet rs;
        /*
        s1="Select id from "+ TABLE_COMPETIZIONE;
        sendQuery(s1);
        rs = getStatement().getResultSet();
        int [] id_comp=new int [getResultSetLength(rs)];
        for (int i=0; i<id_comp.length;i++,rs.next())
            id_comp[i]=rs.getInt("id");
        //ResultSet rs1;
                */
        int [] id_comp=new int [2];
        id_comp [0]=0;
        id_comp [1]=1;
        Competizione [] comp=new Competizione [id_comp.length];
        for (int i=0; i<id_comp.length;i++)
        {
            //s1=new String ("Select * from (optional join opt_comp on optional.nome=opt_comp.opt) where comp="+rs.getInt("id"));
            //rs1 = getStatement().getResultSet();
            //Optional [] opt=_getOptional(rs.getInt("id"));
            //int nparts=_getNPartecipanti(rs.getInt("id"));
            Optional [] opt=_getOptional(id_comp[i]);
            int nparts=/*_getNPartecipanti(id_comp[i])*/1;
            s1=new String("Select * from competizione join mc on competizione.manager_comp=mc.id where competizione.id="+id_comp[i]);
            sendQuery(s1);
            rs = getStatement().getResultSet();
            comp[i]=new Competizione (rs.getFloat("competizione.prezzo"),rs.getInt("competizione.nmin"),rs.getInt("competizione.nmax"),nparts,rs.getString("competizione.tipo"),rs.getString("mc.nome"),rs.getString("mc.cognome"),rs.getString("mc.mail"),rs.getDate("competizione.data_comp"),opt);
        }  
        return comp;
    }        

    protected Optional[] _getOptional() throws SQLException
    {
        sendQuery("SELECT * FROM " + TABLE_OPTIONAL);
        ResultSet rs = getStatement().getResultSet();
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (int i = 0; i < opt.length; i++, rs.next())
        {
            opt[i] = new Optional(rs.getString("nome"), rs.getString("descrizione"), rs.getFloat("prezzo"));
        }
        return opt;
    }
    

    protected Optional[] _getOptional(int id) throws  SQLException 
    {
  
        sendQuery("SELECT * FROM " + TABLE_OPTIONAL +" join opt_comp on optional.nome=opt_comp.opt where opt_comp.comp="+id);
        ResultSet rs = getStatement().getResultSet();
        Optional[] opt = new Optional[getResultSetLength(rs)];
        for (int i = 0; i < opt.length; i++, rs.next())
        {
            opt[i] = new Optional(rs.getString("nome"), rs.getString("descrizione"), rs.getFloat("opt_comp.prezzo"));
        }
        return opt;

    }

    protected void _setOptional(Optional optional) throws SQLException
    {
        sendUpdate("UPDATE " + TABLE_OPTIONAL +
                " SET descrizione='" + optional.getDescrizione() + "'" +
                ", prezzo=" + optional.getPrezzo() +
                " WHERE nome='" + optional.getNome() + "'");
    }
 
    protected String[] _getPartecipantiNome() throws SQLException
    {
        String query = "SELECT nome, cognome FROM partecipante";
        ResultSet rs = sendQuery(query);
        String[] str = new String[getResultSetLength(rs)];
        for (int i = 0; i < str.length; i++, rs.next())
        {
            str[i] = rs.getString("nome") + " " + rs.getString("cognome");
        }
        return str;
    }

    protected int _getNPartecipanti (int id) throws SQLException
    {
        sendQuery("select count(*) from competizione join prenotazione on competizione.id=prenotazione.comp where competizione.id="+id);
        ResultSet rs = getStatement().getResultSet();
        return rs.getInt(1);
    }
    
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
    // </editor-fold>
 
}
