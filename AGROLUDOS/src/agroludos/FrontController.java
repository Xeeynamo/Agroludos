package agroludos;

import agroludos.db.AgroController;
import agroludos.db.components.*;
import agroludos.db.exception.*;
import agroludos.db.user.*;
import agroludos.gui.*;
import java.util.Date;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class FrontController
{
    /**
     * Descrive una coppia tra una richiesta ed una lista di utenti la quale
     * gli è permesso di eseguirla.
     */
    static class Pair
    {
        Request r;
        UserType[] t;
        
        /**
         * Crea una coppia
         * @param r tipo di richiesta
         * @param t lista di utenti che possono eseguire tale richiesta
         */
        public Pair(Request r, UserType[] t)
        {
            this.r = r;
            this.t = t;
        }
        
        public Request getRequest()
        {
            return r;
        }
        
        /**
         * Verifica se la richiesta può essere apportata da un determinato utente
         * @param type tipo di utente che deve eseguire la richiesta
         * @return true se la richiesta è permessa, altrimenti false
         */
        public boolean validateRequest(UserType type)
        {
            for (UserType _type : t)
            {
                if (type == _type)
                    return true;
            }
            return false;
        }
    }
    
    /**
     * Tipologie di utenti possibili
     */
    enum UserType
    {
        Anonimo,
        Partecipante,
        ManagerCompetizione,
        ManagerSistema
    }
    
    /**
     * Richieste possibili
     */
    public enum Request
    {
        Initialize,
        /**
         * restituisce la finestra dedicata all'utente appena loggato. Se ci 
         * sono dei dati che non corrispondono ad un particolare utente presente
         * nel sistema o non indicano nessun utente in generale non apre alcuna 
         * finestra
         * 
         * @param
         *      Email;
         *      Password;
         * @return
         *      Successo: presenta una nuova finestra dedicata in base
         *                 alla tipologia dell'utente loggato (se l'utente è
         *                 un partecipante alle competizioni, allora aprirà la
         *                 finestra per abilitare il partecipante alle funzioni
         *                 a lui desiderate);
         *      
         *      Fallimento: non cambia nulla e rimane nella stessa finestra
         *                   cui si stava operando
         */
        Login,
        /**
         * Aggiunge un nuovo utente di tipo "Partecipante" nel sistema. In caso
         * però di dati ridondanti (un altro utente con la stessa email o con
         * lo stesso codice fiscale) o in presenza di campi lasciati vuoti
         * nell'atto della registrazione dei dati allora il sistema non
         * aggiungerà il nuovo utente
         * 
         * @param
         *      Password;
         *      Partecipante
         *
         * @return
         *      Successo: il nuovo utente di tipo "Partecipante" è stato
         *                registrato nel sistema e quindi può accedervi;
         *      
         *      Fallimento: il nuovo utente non è stato registrato nel sistema
         *                  quindi non può accedervi
         */
        AddPartecipante,
        SendMail,
        SendMailToSys,
        /**
         * Restituisce tutti i manager di competizioni presenti nel sistema.
         * 
         * @param
         * 
         * @return lista dei Manager di Competizioni presenti nel sistema
         */
        GetManagers,
        /**
         * Restituisce tutte le competizioni a cui un dato partecipante può 
         * prenotarsi
         * 
         * @param
         * 
         * @return lista delle Competizioni disponibili
         */
        GetCompetizioniDisponibili,
        /**
         * Restituisce tutte le competizioni a cui un dato partecipante si è
         * già prenotato e che devono essere ancora svolte
         * 
         * @param
         * 
         * @return lista delle Competizioni prenotate
         */
        GetCompetizioniPrenotate,
        /**
         * Restituisce una data competizione passando come parametro il suo:
         *      -ID, oppure;
         *      -la mail del Manager competizione e data cui dovrebbe svolgersi tale
         *      competizione.
         * 
         * @param SOLO ID,oppure; Email Manager Competizione e data.
         * 
         * @return la Competizione desiderata, oppure null
         */
        GetCompetizione,
    /**
     * Ottiene una lista di competizioni gestite da uno specifico manager.
     * Le competizioni annullate non saranno incluse nella lista.
     * 
     * @param 
     * usata da Manager Sistema ->  mail del manager di competizione;
     * usata da Manager Competizione -> nulla.
     * 
     * @return lista delle competizioni gestite dal manager specificato
     */
        GetCompetizioni,
      /**
     * Ottiene una lista di optional usata dalla competizione specificata
     * 
     * @param idCompetizione da analizzare
     * 
     * @return lista degli optional
     */
        GetCompetizioneOptionals,
        /**
         * Annulla la prenotazione effettuata da un dato partecipante per
         * una data competizione
         * 
         * @param
         * usata da Partecipante -> Competizione da cui annullare la prenotazione;
         * usata da Manager Competizione -> partecipante,Competizione per 
         * annullare la prenotazione.
         * 
         * @return
         */
        AnnullaPrenotazione,
        /**
         * Effettua la prenotazione di un partecipante ad una data competizione,
         * tranne nel caso in cui il certificato SRC del suddetto partecipante
         * non sia valida per la data di quella competizione
         * 
         * @param
         * Competizione su cui effettuare la prenotazione;
         * Lista di Optional richiesti dal partecipante
         * 
         * @return
         * Successo: la prenotazione è registrata nel sistema;
         * Fallimento: non viene segnalata alcuna prenotazione;

         */
        AddIscrizioneCompetizione,
        
     /**
     * Restituisce una data competizione tramite il suo ID
     * 
     * @param idCompetizione da analizzare
     * 
     * @return Competizione associata all'ID
     */
        GetCompetizioneFromId,
        /**
         * Ritorna true se nella prenotazione effettuata da un partecipante
         * ad una data competizione ha indicato di usufruire di un dato 
         * optional, altrimenti false (potrebbe anche significare che il part.
         * non abbia effettuato alcuna prenotazione per tale competizione)
         * 
         * 
         * @param 
         * 
         * usata da Partecipante->l'Optional da controllare se è stato 
         * selezionato o meno,
         * la Competizione su cui è stata effettuata la prenotazione da controllare
         * 
         * @return true,false
         */
        IsOptionalSelezionato,
        /**
         * Data una prenotazione, fa si che un dato optional possa risultare
         * richiesto o meno dal partecipante
         * 
         * @param
         * usata da Partecipante->l'Optional quale si desidera far risultare
         * richiesto/non richiesto dal partecipante,
         * la Competizione su cui è stata effetuata la prenotazione,
         * true/false per indicare se tale Optional deve risultare;
         * richiesto/non richiesto
         * 
         * @return
         */
        SetOptionalPrenotazione,
            /**
     * Ottiene una lista di tutti gli optional presenti nel database
     * @return lista degli optional
     */
        GetOptional,
        /**
         * Modifica del prezzo e/o delle descrizioni di un'optional da
         * effetuare nel sistema
         * 
         *  @param Optional modificato
         * 
         * @return
         */
        SetOptional,
    /**
     * Restituisce un  partecipante tramite la sua email
     * 
     * @param email da analizzare
     * 
     * @return Partecipante associato all'email
     */
        GetPartecipante,
        /**
         * Restituisce la lista dei partecipanti che si sono prenotati ad
         * una data competizione
         * 
         * @param id della competizione da analizzare
         * 
         * @return lista di partecipanti
         */
        GetPartecipantiCompetizione,
    /**
     * Ottiene le informazioni base di un partecipante, quali mail nome e cognome.
     * Questa funzione serve per permettere di restituire una lista di partecipanti,
     * ma senza appesantire troppo le query ed il client. Infatti non vi sarà
     * bisogno di dover caricare tutti i partecipanti insieme, ma solo caricare le
     * informazioni strettamente necessarie per poi entrare nel dettaglio di un
     * partecipante uno alla volta. Si noti che ogni partecipante restituito da
     * questa funzione, presenterà isValid() sempre falso poiché gli altri campi
     * vengono lasciati vuoti. Si noti come la lista sia ordinata per cognome.
     * 
     * @return lista dei partecipanti
     */
        GetPartecipantiMinimal,
    /**
     * Ottiene una lista di id delle competizioni a cui un partecipante si è iscritto
     * 
     * @param mail email del partecipante 
     * 
     * @return lista di id di competizioni
     *  
     */
        GetPartecipanteCompetizioni,
        /**
         * Restituisce l'ID associata ad una data Competizione
         * 
         *  @param Competizione da analizzare
         * 
         * @return ID associata alla competizione
         */
        GetIdFromCompetizione,
        /**
         * Restituisce l'email associata ad un dato partecipante
         * 
         *  @param Partecipante da analizzare
         * 
         * @return Mail associata al partecipante
         */
        GetMailFromPartecipante,
    /**
     * Ottiene una lista dei vari tipi di competizione presenti
     *
     * @return lista di tipi di competizione
     */
        GetCompetizioneTipi,
        /**
         * Crea e registra una nuova competizione gestita dal manager
         * di competizione che l'ha creato
         * 
         * @param prezzo, numero minimo di partecipanti, numero massimo di
         * partecipanti, tipologia di competizione, data svolgimento, lista
         * degli optional messi a disposizione per la competizione
         */
        AddCompetizione,
        /**
         * Annulla la competizione desiderata, facendola risultare al sistema,
         * appunto, come annullata
         * 
         * @param int ID della competizione da annullare
         */
        AnnullaCompetizione,
        /**
         * Visualizza la finestra del Login
         */
        FrameLogin,
        /**
         * Visualizza la finestra della registrazione
         */
        FrameRegistrazione,
        /**
         * Visualizza la finestra dedicata all'utente di tipo "Partecipante"
         */
        FrameHome,
        /**
         * Visualizza la finestra dedicata all'utente di tipo "Manager
         * Competizioni"
         */
        FrameManagerCompetizione,
        /**
         * Visualizza la finestra dedicata alla creazione di una competizione
         * da parte di un utente di tipo "Manager Competizioni"
         */
        FrameCreaCompetizione,
        /**
         * Visualizza la finestra dedicata all'utente di tipo "Manager
         * Sistema"
         */
        FrameManagerSistema,
    }
    private static final Pair[] pair =
    {
        new Pair(Request.Initialize, new UserType[]{UserType.Anonimo}),
        new Pair(Request.Login, new UserType[]{UserType.Anonimo}),
        new Pair(Request.AddPartecipante, new UserType[]{UserType.Anonimo}),
        new Pair(Request.SendMail, new UserType[]{UserType.Anonimo, UserType.Partecipante, UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.SendMailToSys, new UserType[]{UserType.Partecipante, UserType.ManagerCompetizione}),
        
        new Pair(Request.GetManagers, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioniDisponibili, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizioniPrenotate, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizione, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioni, new UserType[]{UserType.ManagerCompetizione,UserType.ManagerSistema}),
        new Pair(Request.GetCompetizioneOptionals, new UserType[]{UserType.Partecipante,UserType.ManagerCompetizione,UserType.ManagerSistema}),
        new Pair(Request.AnnullaPrenotazione, new UserType[]{UserType.Partecipante,UserType.ManagerCompetizione}),
        new Pair(Request.AddIscrizioneCompetizione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetCompetizioneFromId, new UserType[]{UserType.Partecipante,UserType.ManagerCompetizione}),
        new Pair(Request.IsOptionalSelezionato, new UserType[]{UserType.Partecipante}),
        new Pair(Request.SetOptionalPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetOptional, new UserType[]{UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.SetOptional, new UserType[]{UserType.ManagerSistema}),        new Pair(Request.SetOptionalPrenotazione, new UserType[]{UserType.Partecipante}),
        new Pair(Request.GetPartecipante, new UserType[]{UserType.Partecipante, UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.GetPartecipantiCompetizione, new UserType[]{UserType.ManagerCompetizione, UserType.ManagerSistema}),
        new Pair(Request.GetPartecipantiMinimal, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetPartecipanteCompetizioni, new UserType[]{UserType.ManagerSistema}),
        new Pair(Request.GetIdFromCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.GetMailFromPartecipante, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.GetCompetizioneTipi, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.AnnullaCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.AddCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.FrameLogin, new UserType[]{UserType.Anonimo}),
        new Pair(Request.FrameRegistrazione, new UserType[]{UserType.Anonimo}),
        new Pair(Request.FrameHome, new UserType[]{UserType.Partecipante}),
        new Pair(Request.FrameManagerCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.FrameCreaCompetizione, new UserType[]{UserType.ManagerCompetizione}),
        new Pair(Request.FrameManagerSistema, new UserType[]{UserType.ManagerSistema}),
    };
    
    AgroController user;
    
    UserType type;
    JFrame currentFrame;
    
    
    public FrontController()
    {
        currentFrame = null;
        type = UserType.Anonimo;
    }
    
    private boolean validateRequest(Request request) throws RequestNotSupportedException
    {
        for (Pair p : pair)
        {
            if (p.getRequest() == request)
                return p.validateRequest(type);
        }
        throw new RequestNotSupportedException();
    }
    
    public Object[] processRequest(Request request, Object ... param) throws DeniedRequestException, RequestNotSupportedException, InternalErrorException
    {
        if (!validateRequest(request))
            throw new DeniedRequestException();
        try
        {
            switch (request)
            {
                case Initialize:
                    user = new Anonimo((String)param[0], (String)param[1], (String)param[2]);
                    break;
                case Login:
                    user = user.Login((String)param[0], (String)param[1]);
                    if (user instanceof agroludos.db.user.Utente)
                    {
                        type = UserType.Partecipante;
                        processRequest(Request.FrameHome, (Object[])null);
                    }
                    else if (user instanceof ManagerCompetizione)
                    {
                        type = UserType.ManagerCompetizione;
                        processRequest(Request.FrameManagerCompetizione, (Object[])null);
                    }
                    else if (user instanceof ManagerSistema)
                    {
                        type = UserType.ManagerSistema;
                        processRequest(Request.FrameManagerSistema, (Object[])null);
                    }
                    break;
                case AddPartecipante:
                    user.addPartecipante((String)param[0], (Partecipante)param[1]);
                    break;
                case SendMail:
                    user.sendMail((String)param[0], (String)param[1], (String)param[2]);
                    break;
                case SendMailToSys:
                    user.sendMail(user.getSysMail(), (String)param[0], (String)param[1]);
                    break;
                    
                case GetManagers:
                    return user.getManagers();
                case GetCompetizioniDisponibili:
                    return user.getCompetizioniDisponibili();
                case GetCompetizioniPrenotate:
                    return user.getCompetizioniPrenotate();
                case GetCompetizione:
                    return new Object[]
                    {
                        user.getCompetizione((Integer)param[0])
                    };
                case GetCompetizioni:
                    if (user instanceof ManagerCompetizione)
                        return user.getCompetizioni(user.getMail());
                    else if (user instanceof ManagerSistema)
                        return user.getCompetizioni((String)param[0]);
                    break;
                case GetCompetizioneOptionals:
                    return user.getCompetizioneOptional((Integer)param[0]);
                case AnnullaPrenotazione:
                    if (user instanceof agroludos.db.user.Utente)
                        user.annullaPrenotazione
                            ((Partecipante)processRequest(Request.GetPartecipante,
                                new Object[] { user.getMail() })[0],
                                (Competizione)param[0]);
                    else if (user instanceof ManagerCompetizione)
                        user.annullaPrenotazione
                            ((Partecipante)param[0],
                                (Competizione)param[1]);
                    break;
                case GetPartecipante:
                    return new Object[]
                    {
                        user.getPartecipante((String)param[0])
                    };
                case AddIscrizioneCompetizione:
                    user.addIscrizioneCompetizione(
                            (Partecipante)processRequest(Request.GetPartecipante,
                                    new Object[] { user.getMail() })[0],
                            (Competizione)param[0],
                            (Optional[])param[1]);
                    break;
                case GetCompetizioneFromId:
                    return new Object[]
                    {
                        user.getCompetizione((int)param[0])
                    };
                case IsOptionalSelezionato:
                    if (user instanceof agroludos.db.user.Utente)
                        return new Object[]
                        {
                            user.isOptionalPrenotato(
                                (Partecipante)processRequest(Request.GetPartecipante,
                                        new Object[] { user.getMail() })[0],
                                (Optional)param[0],
                                (Competizione)param[1])
                        };
                case SetOptionalPrenotazione:
                    if (user instanceof agroludos.db.user.Utente)
                        user.setOptionalPrenotazione(
                                (Optional)param[0],
                                (Partecipante)processRequest(Request.GetPartecipante,
                                        new Object[] { user.getMail() })[0],
                                (Competizione)param[1],
                                (boolean)param[2]);
                    break;
                case GetOptional:
                    return user.getOptional();
                case SetOptional:
                    user.setOptional((Optional)param[0]);
                    break;
                case GetPartecipantiCompetizione:
                    return user.getPartecipanti((int)param[0]);
                case GetPartecipantiMinimal:
                    return user.getPartecipantiMinimal();
                case GetPartecipanteCompetizioni:
                    return user.getPartecipanteCompetizioni((String)param[0]);
                case GetIdFromCompetizione:
                    return new Object[]{ user.getIdFromCompetizione((Competizione)param[0])};
                case GetMailFromPartecipante:
                    return new Object []{user.getMailFromPartecipante((Partecipante) param[0])};
                case GetCompetizioneTipi:
                    return user.getCompetizioneTipi();
                case AddCompetizione:
                    Competizione c=new Competizione (0,(float)param[0],(int)param[1],
                    (int)param[2],0,(TipoCompetizione)param[3],
                    new Manager ("","",user.getMail()),(Date)param[4],(Optional[])param[5]);
                    user.creaCompetizione(c);
                    break;
                case AnnullaCompetizione:
                    user.annullaCompetizione((int)param[0]);
                    break;
                case FrameLogin:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameLogin(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameRegistrazione:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameRegistrazione(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameHome:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameHomePartec(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameManagerCompetizione:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameManComp(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameCreaCompetizione:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameCreaComp(this);
                    currentFrame.setVisible(true);
                    break;
                case FrameManagerSistema:
                    if (currentFrame != null)
                    {
                        currentFrame.setVisible(false);
                        currentFrame.dispose();
                    }
                    currentFrame = new JFrameMainSystem(this);
                    currentFrame.setVisible(true);
                    break;
                   
            }
        }
        catch (MessagingException ex)
        {
            Shared.showError(currentFrame, "Impossibile invire la mail.\n" + ex.toString());
        }
        catch (WrongLoginException | CampiVuotiException | TipoCompetizioneInvalidException |
                DefCodFiscException | DefEmailException | SrcScadutaException 
                |MinMaxException |DatePriorException ex)
        {
            throw new InternalErrorException(ex.toString());
        }
        catch (ClassNotFoundException ex)
        {
            
        }
        catch (IllegalAccessException ex)
        {
            
        }
        catch (InstantiationException ex)
        {
            
        }
        catch (SQLException ex)
        {
            throw new InternalErrorException("Impossibile stabilire una connessione col database.\n" +
                    ex.toString());
        }
        return null;
    }
}
